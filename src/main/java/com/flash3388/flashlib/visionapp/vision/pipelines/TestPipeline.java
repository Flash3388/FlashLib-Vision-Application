package com.flash3388.flashlib.visionapp.vision.pipelines;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.visionapp.vision.VisionData;

public class TestPipeline implements VisionPipeline {

    @Override
    public void process(VisionData input) throws VisionException {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new VisionException(e);
        }
    }
}
