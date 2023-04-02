package com.flash3388.flashlib.visionapp.vision;

import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import com.flash3388.flashlib.vision.Pipeline;
import com.flash3388.flashlib.vision.Source;
import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.visionapp.vision.obsr.PipelineStateTracker;
import org.slf4j.Logger;

public class VisionRunner implements Runnable {

    private static final Time RUN_PERIOD = Time.milliseconds(100);

    private final Source<InstanceData> mSource;
    private final Pipeline<VisionData> mPipeline;
    private final Clock mClock;
    private final Logger mLogger;

    private final PipelineStateTracker mStateTracker;

    public VisionRunner(Source<InstanceData> source,
                        Pipeline<VisionData> pipeline,
                        Clock clock,
                        Logger logger,
                        StoredObject object) {
        mSource = source;
        mPipeline = pipeline;
        mClock = clock;
        mLogger = logger;

        mStateTracker = new PipelineStateTracker(object);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Time startTime = mClock.currentTime();
                Throwable error = null;

                try {
                    InstanceData data = mSource.get();
                    mPipeline.process(data.getVisionData());
                } catch (VisionException e) {
                    mLogger.error("Error in vision processing", e);
                    error = e;
                } catch (Throwable t) {
                    mLogger.error("Unknown error in vision processing", t);
                    error = t;
                }

                Time endTime = mClock.currentTime();
                Time runTime = endTime.sub(startTime);
                mStateTracker.setPipelineIterationFinished(runTime, error);

                if (runTime.before(RUN_PERIOD)) {
                    Time sleepTime = RUN_PERIOD.sub(runTime);
                    //noinspection BusyWait
                    Thread.sleep(sleepTime.valueAsMillis());
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
