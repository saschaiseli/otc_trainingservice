package ch.opentrainingcenter.otc.training.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
@Slf4j
public class LogProducer {
    @Produces
    public Logger getLogger() {
        return log;
    }
}
