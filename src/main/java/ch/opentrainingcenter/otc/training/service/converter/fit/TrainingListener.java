package ch.opentrainingcenter.otc.training.service.converter.fit;

import ch.opentrainingcenter.otc.training.entity.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.entity.HeartRate;
import ch.opentrainingcenter.otc.training.entity.raw.Sport;
import ch.opentrainingcenter.otc.training.entity.raw.*;
import ch.opentrainingcenter.otc.training.service.converter.util.DistanceHelper;
import com.garmin.fit.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TrainingListener implements MesgListener {

    private static final String RECORD = "record"; //$NON-NLS-1$
    private static final String SESSION = "session"; //$NON-NLS-1$
    private static final String LAP = "lap"; //$NON-NLS-1$

    private final List<Tracktrainingproperty> trackpoints = new ArrayList<>();
    private final List<LapInfo> lapInfos = new ArrayList<>();
    private SessionMesg sessionMesg;
    private LapInfo lapInfo = null;
    private int error = 0;
    private int valid = 0;

    @Override
    public void onMesg(final Mesg mesg) {
        final String messageName = mesg.getName();
        if (RECORD.equals(messageName)) {
            trackpoints.add(convertTrackPoint(new RecordMesg(mesg)));
        } else if (SESSION.equals(messageName)) {
            sessionMesg = new SessionMesg(mesg);
        } else if (LAP.equals(messageName)) {
            final LapMesg lapMesg = new LapMesg(mesg);
            final int end = lapMesg.getTotalDistance().intValue();
            final int timeInSekunden = lapMesg.getTotalTimerTime().intValue();
            final int timeInMillis = lapMesg.getTotalTimerTime().intValue() * 1000;
            final int heartRate = lapMesg.getAvgHeartRate() != null ? lapMesg.getAvgHeartRate().shortValue() : 0;
            final String pace = DistanceHelper.calculatePace(end, timeInSekunden, Sport.RUNNING);
            final String speed = DistanceHelper.calculatePace(end, timeInSekunden, Sport.BIKING);
            if (lapInfo == null) {
                // erste Runde
                lapInfo = CommonTransferFactory.createLapInfo(0, 0, end, timeInMillis, heartRate, pace, speed);
            } else {
                final int lapNeu = lapInfo.getLap() + 1;
                final int startNeu = lapInfo.getEnd();
                lapInfo = CommonTransferFactory.createLapInfo(lapNeu, startNeu, startNeu + end, timeInMillis, heartRate,
                        pace, speed);
            }
            lapInfos.add(lapInfo);
        }
    }

    private Tracktrainingproperty convertTrackPoint(final RecordMesg record) {
        final double distance = record.getDistance();
        final Integer positionLong = record.getPositionLong();
        final Integer positionLat = record.getPositionLat();
        Double longDms = null;
        Double latDms = null;
        if (positionLong != null || positionLat != null) {
            longDms = ConvertGarminSemicircles.convertSemicircleToDms(positionLong);
            latDms = ConvertGarminSemicircles.convertSemicircleToDms(positionLat);
            valid++;
        } else {
            error++;
        }

        final int heartbeat = record.getHeartRate() != null ? record.getHeartRate() : -1;
        final int altitude = record.getAltitude() != null ? record.getAltitude().intValue() : -1;
        final long time = record.getTimestamp().getDate().getTime();
        return CommonTransferFactory.createTrackPointProperty(distance, heartbeat, altitude, time, 0, longDms, latDms);
    }

    public Training getTraining() {
        final long dateOfStart = sessionMesg.getStartTime().getDate().getTime();
        final long timeInSeconds = sessionMesg.getTotalTimerTime().longValue();
        final long distanceInMeter = sessionMesg.getTotalDistance().longValue();
        final double maxSpeed = sessionMesg.getMaxSpeed();
        final RunData runData = new RunData(new Date(dateOfStart), timeInSeconds, distanceInMeter, maxSpeed);
        final int average = sessionMesg.getAvgHeartRate() != null ? sessionMesg.getAvgHeartRate().intValue() : -1;
        final int max = sessionMesg.getMaxHeartRate() != null ? sessionMesg.getMaxHeartRate().intValue() : -1;
        final HeartRate heart = new HeartRate(average, max);
        final Training training = CommonTransferFactory.createTraining(runData, heart);
        training.setTrackPoints(trackpoints);
        training.setGeoJSON(convertGeoJSON(trackpoints));
        training.setDownMeter(sessionMesg.getTotalDescent() != null ? sessionMesg.getTotalDescent() : 0);
        training.setUpMeter(sessionMesg.getTotalAscent() != null ? sessionMesg.getTotalAscent() : 0);
        final com.garmin.fit.Sport sport = sessionMesg.getSport();
        if (com.garmin.fit.Sport.RUNNING.equals(sport)) {
            training.setSport(Sport.RUNNING);
        } else if (com.garmin.fit.Sport.CYCLING.equals(sport)) {
            training.setSport(Sport.BIKING);
        } else {
            training.setSport(Sport.OTHER);
        }
        final Float totalTrainingEffect = sessionMesg.getTotalTrainingEffect();
        if (totalTrainingEffect != null) {
            training.setTrainingEffect((int) (10 * totalTrainingEffect.floatValue()));
        }
        final Float totalAnaerobicTrainingEffect = sessionMesg.getTotalAnaerobicTrainingEffect();
        if (totalAnaerobicTrainingEffect != null) {
            training.setAnaerobicTrainingEffect((int) (10 * totalAnaerobicTrainingEffect.floatValue()));
        }
        training.setLapInfos(lapInfos);
        final int total = error + valid;
        final int fehlerInProzent = (int) (100 * (error / (float) total));
        training.setGeoQuality(fehlerInProzent);
        return training;
    }

    private String convertGeoJSON(final List<Tracktrainingproperty> points) {
        final StringBuilder str = new StringBuilder();
        str.append(" { type\": \"LineString\",\"coordinates\": [");

        final Iterator<Tracktrainingproperty> iterator = points.iterator();
        while (iterator.hasNext()) {
            final Tracktrainingproperty p = iterator.next();
            if (isValidCoordinate(p)) {
                str.append("[").append(p.getLongitude()).append(',').append(p.getLatitude()).append("]");
                if (iterator.hasNext()) {
                    str.append(",");
                }
            }
        }
        str.append("]}");
        return str.toString();
    }

    private boolean isValidCoordinate(final Tracktrainingproperty p) {
        return p.getLatitude() != null && p.getLongitude() != null;
    }
}
