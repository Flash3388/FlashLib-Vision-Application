package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.visionapp.vision.pipelines.AnalysisSink;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionAnalyzer;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionDetector;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionProcessor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;

import java.util.ArrayList;
import java.util.List;

public class PipelineConfiguration extends ConfigurationBase {

    PipelineConfiguration(Config config) {
        super(config);
    }

    public List<VisionProcessor> getProcessors(StoredObject object) {
        return parseProcessors(
                mConfig.getObject("processors"),
                object.getChild("processors"));
    }

    public VisionDetector getDetector(StoredObject object) {
        return parseDetector(
                mConfig.getConfig("detector"),
                object.getChild("detector"));
    }

    public VisionAnalyzer getAnalyser(StoredObject object) {
        return parseAnalyser(
                mConfig.getConfig("analyser"),
                object.getChild("analyser"));
    }

    public AnalysisSink getSink(StoredObject object) {
        return parseSink(
                mConfig.getConfig("sink"),
                object.getChild("sink"));
    }

    private static List<VisionProcessor> parseProcessors(ConfigObject configObject, StoredObject object) {
        List<VisionProcessor> processors = new ArrayList<>();
        for (String name : configObject.keySet()) {
            Config config = configObject.toConfig().getConfig(name);

            String typeName = config.getString("type");
            ProcessorType type = ProcessorType.fromConfigName(typeName);

            StoredObject childObject = object.getChild(name);
            childObject.getEntry("type").setString(typeName);

            VisionProcessor processor = type.createFromConfig(config, childObject);
            processors.add(processor);
        }

        return processors;
    }

    private static VisionDetector parseDetector(Config config, StoredObject object) {
        String typeName = config.getString("type");
        DetectorType type = DetectorType.fromConfigName(typeName);

        object.getEntry("type").setString(typeName);
        return type.createFromConfig(config, object);
    }

    private static VisionAnalyzer parseAnalyser(Config config, StoredObject object) {
        String typeName = config.getString("type");
        AnalyserType type = AnalyserType.fromConfigName(typeName);

        object.getEntry("type").setString(typeName);
        return type.createFromConfig(config, object);
    }

    private static AnalysisSink parseSink(Config config, StoredObject object) {
        String typeName = config.getString("type");
        SinkType type = SinkType.fromConfigName(typeName);

        object.getEntry("type").setString(typeName);
        return type.createFromConfig(config, object);
    }
}
