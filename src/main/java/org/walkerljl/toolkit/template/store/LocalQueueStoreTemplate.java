package org.walkerljl.toolkit.template.store;

import java.util.concurrent.BlockingQueue;

import org.walkerljl.toolkit.standard.Machine;
import org.walkerljl.toolkit.standard.abstracts.AbstractMachine;

import org.walkerljl.toolkit.standard.exception.AppException;
import org.walkerljl.toolkit.standard.exception.machine.CannotStartMachineException;
import org.walkerljl.toolkit.standard.exception.machine.CannotStopMachineException;
import org.walkerljl.toolkit.standard.exception.machine.MachineException;

/**
 * LocalQueueStoreTemplate
 *
 * @author lijunlin
 * @Date 2016/12/19
 */
public class LocalQueueStoreTemplate<T> extends AbstractMachine implements Machine {

    private String serviceGroup;
    private String serviceId;
    private BlockingQueue<T> blockingQueue;
    private Storer storer;

    public LocalQueueStoreTemplate(String serviceGroup, String serviceId, BlockingQueue<T> blockingQueue, Storer<T> storer) {
        this.serviceGroup = serviceGroup;
        this.serviceId = serviceId;
        this.blockingQueue = blockingQueue;
        this.storer = storer;
    }

    @Override
    public String getId() {
        return serviceId;
    }

    @Override
    public String getGroup() {
        return serviceGroup;
    }

    @Override
    public void processStart() throws CannotStartMachineException {

    }

    @Override
    protected void processRun() throws MachineException {
        while (isRunning() || (!isRunning() && blockingQueue.size() > 0)) {
            T element = null;
            try {
                element = blockingQueue.take();
                storer.store(element);
            } catch (Throwable e) {
                //TraceUtils.traceExceptionQuietly(getServerName() + " occurs error, skip and continue.",
                //        new Object[]{element}, e);
                throw new AppException(e);
            }
        }
    }

    @Override
    public void processStop() throws CannotStopMachineException {

    }

    @Override
    public String getInstanceId() {
        return Thread.currentThread().getId() + "";
    }
}
