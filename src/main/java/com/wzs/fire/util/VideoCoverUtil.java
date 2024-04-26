package com.wzs.fire.util;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.File;

public class VideoCoverUtil {
    public static void extractFirstFrameAsThumbnail(String videoFilePath, String outputImagePath) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath)) {
            grabber.start();

            Frame frame = grabber.grabImage(); // 获取第一帧
            if (frame == null) {
                throw new RuntimeException("Could not get a frame from the video!");
            }

            Java2DFrameConverter converter = new Java2DFrameConverter();
            javax.imageio.ImageIO.write((BufferedImage) converter.convert(frame), "jpg", new File(outputImagePath));

            grabber.stop();
        } catch (Exception e) {
            throw new RuntimeException("Error extracting thumbnail from video", e);
        }
    }
}
