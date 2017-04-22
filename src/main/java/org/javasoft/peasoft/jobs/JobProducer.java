/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.jobs;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import org.apache.deltaspike.scheduler.spi.Scheduler;
import org.quartz.Job;

/**
 *
 * @author ayojava
 */
@Dependent
public class JobProducer {
    
    @Produces
    @ApplicationScoped
    protected Scheduler<Job> produceScheduler(Scheduler scheduler) {
        return scheduler;
    }
}
