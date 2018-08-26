package ch.opentrainingcenter.otc.training.service;

import javax.enterprise.context.RequestScoped;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class WorkerBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkerBean.class);

	@Counted(monotonic = true, absolute = true)
	public void doCount() {
		LOGGER.info("Work Done");
	}
}
