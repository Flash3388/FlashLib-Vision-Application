package com.flash3388.flashlib.visionapp.vision.sources;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.visionapp.vision.VisionData;
import com.flash3388.flashlib.visionapp.vision.color.ColorSpace;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.io.IOException;

public class CvCameraSource implements VisionSource {

    private final VideoCapture mVideoCapture;

    public CvCameraSource(VideoCapture videoCapture) {
        mVideoCapture = videoCapture;
    }

    @Override
    public VisionData get() throws VisionException {
        Mat mat = new Mat();
        if (!mVideoCapture.read(mat)) {
            mat.release();
            throw new SourceReadFailedException();
        }

        if (mat.empty()) {
            mat.release();
            throw new ImageIsEmptyException();
        }

        return new VisionData(mat, ColorSpace.BGR);
    }

    @Override
    public void close() throws IOException {
        mVideoCapture.release();
    }
}
