package ch.opentrainingcenter.otc.training.service;

import javax.enterprise.context.RequestScoped;

import org.eclipse.microprofile.metrics.annotation.Counted;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestScoped
public class WorkerBean {
	@Counted(monotonic=true, absolute=true)
	public void doCount() {
		log.info("Work Done");
	}
}
