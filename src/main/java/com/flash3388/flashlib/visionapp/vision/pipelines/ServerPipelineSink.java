package com.flash3388.flashlib.visionapp.vision.pipelines;

import com.flash3388.flashlib.vision.DoubleBufferHolder;
import com.flash3388.flashlib.vision.jpeg.JpegImage;
import com.flash3388.flashlib.vision.jpeg.server.MjpegServer;
import com.flash3388.flashlib.visionapp.vision.pipelines.PipelineImageSink;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ServerPipelineSink implements PipelineImageSink {

    private final DoubleBufferHolder<JpegImage> mPreProcess;
    private final DoubleBufferHolder<JpegImage> mPostProcess;
    private final DoubleBufferHolder<JpegImage> mPostDetector;

    public ServerPipelineSink(String name, MjpegServer server) {
        mPreProcess = new DoubleBufferHolder<>();
        mPostProcess = new DoubleBufferHolder<>();
        mPostDetector = new DoubleBufferHolder<>();

        server.setCamera(name.concat("/").concat("preprocess"), mPreProcess, 10);
        server.setCamera(name.concat("/").concat("postprocess"), mPostProcess, 10);
        server.setCamera(name.concat("/").concat("postdetector"), mPostDetector, 10);
    }

    @Override
    public void handlePreProcessImage(Mat image) {
        BufferedImage bufferedImage = imageFromMat(image);
        mPreProcess.process(new JpegImage(bufferedImage));
    }

    @Override
    public void handlePostProcessImage(Mat image) {
        BufferedImage bufferedImage = imageFromMat(image);
        mPostProcess.process(new JpegImage(bufferedImage));
    }

    @Override
    public void handlePostDetectionImage(Mat image) {
        BufferedImage bufferedImage = imageFromMat(image);
        mPostDetector.process(new JpegImage(bufferedImage));
    }

    private static BufferedImage imageFromMat(Mat mat) {
        try {
            MatOfByte matOfByte = new MatOfByte();
            Imgcodecs.imencode(".jpg", mat, matOfByte);
            byte[] byteArray = matOfByte.toArray();
            InputStream in = new ByteArrayInputStream(byteArray);
            return ImageIO.read(in);
        } catch (IOException e) {
            return new BufferedImage(0, 0, BufferedImage.TYPE_3BYTE_BGR);
        }
    }
}
