package com.flash3388.flashlib.visionapp.vision;

import com.flash3388.flashlib.app.FlashLibInstance;
import com.flash3388.flashlib.app.net.NetworkInterface;
import com.flash3388.flashlib.net.messaging.KnownMessageTypes;
import com.flash3388.flashlib.net.messaging.Messenger;
import com.flash3388.flashlib.net.obsr.StoredEntry;
import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import com.flash3388.flashlib.vision.Pipeline;
import com.flash3388.flashlib.vision.SourcePollingObserver;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.control.Helper;
import com.flash3388.flashlib.vision.control.KnownVisionOptions;
import com.flash3388.flashlib.vision.control.VisionOption;
import com.flash3388.flashlib.vision.control.VisionServer;
import com.flash3388.flashlib.vision.processing.Processor;
import com.flash3388.flashlib.visionapp.Globals;
import com.flash3388.flashlib.visionapp.config.VisionOptionConfiguration;
import com.flash3388.flashlib.visionapp.vision.pipelines.AnalysisSink;
import com.flash3388.flashlib.visionapp.vision.pipelines.BasePipeline;
import com.flash3388.flashlib.visionapp.vision.pipelines.PipelineImageSink;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionAnalyzer;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionDetector;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionProcessor;
import com.flash3388.flashlib.visionapp.vision.sources.VisionImage;
import com.flash3388.flashlib.visionapp.vision.sources.VisionSource;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class VisionInstance {

    private final InstanceInfo mInfo;
    private final Clock mClock;
    private final StoredObject mObject;

    private final VisionSource mSource;
    private final Processor<VisionData, Optional<Analysis>> mPipeline;

    private final Logger mLogger;
    private final VisionServer mServer;

    public VisionInstance(InstanceInfo info,
                          Clock clock,
                          StoredObject object,
                          VisionSource source,
                          List<VisionProcessor> processors,
                          VisionDetector detector,
                          VisionAnalyzer analyser,
                          AnalysisSink sink,
                          Collection<PipelineImageSink> imageSinks,
                          VisionOptionConfiguration visionOptionConfiguration) {
        mInfo = info;
        mClock = clock;
        mObject = object;
        mSource = source;

        mPipeline = new BasePipeline(processors, detector, analyser, sink, imageSinks);
        mLogger = Globals.getVisionInstanceLogger(info.getName());

        KnownVisionOptions options = Helper.getOptions();
        KnownMessageTypes messageTypes = Helper.getMessageTypes();
        Messenger messenger = FlashLibInstance.getControl().getNetworkInterface()
                .newMessenger(
                        messageTypes,
                        NetworkInterface.MessengerConfiguration.serverMode(5010));
        mServer = new VisionServer(
                messenger,
                this::createTask,
                options
        );

        Map<VisionOption<?>, Object> optionValues = visionOptionConfiguration.getAll(options);
        for (Map.Entry<VisionOption<?>, Object> entry : optionValues.entrySet()) {
            //noinspection unchecked,rawtypes
            mServer.setOption((VisionOption) entry.getKey(), entry.getValue());
        }
    }

    public void start() {
        mServer.start();
    }

    public void stop() {
        mServer.stop();
    }

    private AutoCloseable createTask(Consumer<Analysis> analysis) {
        Clock clock = FlashLibInstance.getControl().getClock();
        SourcePollingObserver observer = new SourceObserver(mObject.getChild("runner"));
        Time frameTime = Time.milliseconds(50);

        Pipeline<VisionImage> pipeline = (data) -> {
            Optional<Analysis> optional = mPipeline.process(new VisionData(
                    data.getImage(),
                    data.getColorSpace(),
                    mServer
            ));
            optional.ifPresent(analysis);
        };

        Thread thread = mSource.asyncPoll(pipeline, observer, clock, frameTime);
        return thread::interrupt;
    }

    private static class SourceObserver implements SourcePollingObserver {

        private final StoredEntry mRunTime;
        private final StoredEntry mLastError;

        private SourceObserver(StoredObject object) {
            mRunTime = object.getEntry("RunTime");
            mLastError = object.getEntry("LastError");
        }

        @Override
        public void onStartProcess() {

        }

        @Override
        public void onEndProcess(Time runTime) {
            mRunTime.setLong(runTime.valueAsMillis());
        }

        @Override
        public void onErroredProcess(Throwable t) {
            mLastError.setString(t.getClass().getName());
        }
    }
}
