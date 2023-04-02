package com.flash3388.flashlib.visionapp.vision.sources;

import com.flash3388.flashlib.util.logging.Logging;
import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.visionapp.config.CameraConfiguration;
import com.flash3388.flashlib.visionapp.config.KnownCameraFormat;
import com.flash3388.flashlib.visionapp.vision.VisionData;
import com.flash3388.flashlib.visionapp.vision.color.ColorSpace;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.slf4j.Logger;

import java.io.IOException;

public class CvCameraSource implements VisionSource {

    private static final Logger LOGGER = Logging.getLogger("CvCameraSource");

    private final CameraConfiguration mConfiguration;

    private final VideoCapture mVideoCapture;
    private boolean mIsCameraConfigured;

    public CvCameraSource(CameraConfiguration configuration) {
        mConfiguration = configuration;
        mVideoCapture = new VideoCapture();
        mIsCameraConfigured = false;
    }

    @Override
    public VisionData get() throws VisionException {
        if (!configureCamera()) {
            throw new SourceReadFailedException();
        }

        Mat mat = new Mat();
        if (!mVideoCapture.read(mat)) {
            throw new SourceReadFailedException();
        }

        if (mat.empty()) {
            throw new ImageIsEmptyException();
        }

        return new VisionData(mat, ColorSpace.BGR);
    }

    @Override
    public void close() throws IOException {
        mVideoCapture.release();
    }

    private boolean configureCamera() {
        if (mIsCameraConfigured) {
            return true;
        }

        if (!mVideoCapture.isOpened()) {
            LOGGER.debug("Trying to open camera {} with backend {}",
                    mConfiguration.getDev(),
                    mConfiguration.getBackend().getOpencvBackendCode());
            if (!mVideoCapture.open(
                    mConfiguration.getDev(),
                    mConfiguration.getBackend().getOpencvBackendCode())) {
                // failed to open
                LOGGER.warn("Failed to open camera");
                return false;
            }
        }

        KnownCameraFormat format = mConfiguration.getFormat();
        if (format != null) {
            mVideoCapture.set(Videoio.CAP_PROP_FOURCC, format.getOpencvCode());
        }

        mVideoCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, mConfiguration.getWidth());
        mVideoCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, mConfiguration.getHeight());
        mVideoCapture.set(Videoio.CAP_PROP_FPS, mConfiguration.getFps());

        mIsCameraConfigured = true;
        return true;
    }
}
