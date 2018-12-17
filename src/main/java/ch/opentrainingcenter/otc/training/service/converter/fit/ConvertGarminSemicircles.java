package ch.opentrainingcenter.otc.training.service.converter.fit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * http://www.gps-forums.net/accuracy-converting-semicircles-degrees-t31488.html
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertGarminSemicircles {

	private static final double MULTIPLIKATOR = 180.0d / Math.pow(2L, 31L);

	/**
	 * Konvertiert Garmin semicircles nach WGS84 Grad
	 *
	 * http://www.gps-forums.net/accuracy-converting-semicircles-degrees-t31488.
	 * html
	 */
	public static double convertSemicircleToDms(final int semicircle) {
		return MULTIPLIKATOR * semicircle;
	}
}
