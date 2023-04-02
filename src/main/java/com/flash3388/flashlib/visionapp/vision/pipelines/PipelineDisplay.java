package com.flash3388.flashlib.visionapp.vision.pipelines;

import com.flash3388.flashlib.vision.cv.CvImage;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PipelineDisplay implements PipelineImageSink {

    private final JFrame mWindow;
    private final JLabel mPreProcess;
    private final JLabel mPostProcess;
    private final JLabel mPostDetection;

    public PipelineDisplay(String name) {
        mWindow = createWindow(name);
        mPreProcess = createImageHolder(mWindow);
        mPostProcess = createImageHolder(mWindow);
        mPostDetection = createImageHolder(mWindow);

        SwingUtilities.invokeLater(()-> {
            mWindow.setVisible(true);
        });
    }

    @Override
    public void handlePreProcessImage(Mat image) {
        setImageOnLbl(mPreProcess, image);
    }

    @Override
    public void handlePostProcessImage(Mat image) {
        setImageOnLbl(mPostProcess, image);
    }

    @Override
    public void handlePostDetectionImage(Mat image) {
        setImageOnLbl(mPostDetection, image);
    }

    private static JFrame createWindow(String title) {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(500, 1000);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    private static JLabel createImageHolder(JFrame frame) {
        JLabel lbl = new JLabel();
        lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        lbl.setSize(500, 350);
        lbl.setVisible(true);

        frame.add(lbl);

        return lbl;
    }

    private static void setImageOnLbl(JLabel lbl, Mat image) {
        Image awtImage = imageFromMat(image);
        lbl.setIcon(new ImageIcon(awtImage));
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
