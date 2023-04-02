package com.flash3388.flashlib.visionapp.vision;

import com.castle.concurrent.service.SingleUseService;
import com.castle.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class InstanceManager extends SingleUseService {

    private final ExecutorService mExecutorService;
    private final Collection<VisionInstance> mInstances;

    private final List<Future<?>> mFutures;

    public InstanceManager(ExecutorService executorService, Collection<VisionInstance> instances) {
        mExecutorService = executorService;
        mInstances = instances;

        mFutures = new ArrayList<>();
    }

    @Override
    protected void startRunning() throws ServiceException {
        for (VisionInstance instance : mInstances) {
            Runnable task = instance.createRunTask();
            Future<?> future = mExecutorService.submit(task);
            mFutures.add(future);
        }
    }

    @Override
    protected void stopRunning() {
        for (Future<?> future : mFutures) {
            future.cancel(true);
        }
    }
}
