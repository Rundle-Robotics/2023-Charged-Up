package frc.robot.subsystems;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Arrays;
import java.util.List;

import javax.swing.text.StyleConstants.FontConstants;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class AprilTagSubsystem extends SubsystemBase {

    private Mat mat;
    private CvSink cvSink;
    private CvSource outputStream;
    private AprilTagDetector detector;

    public AprilTagSubsystem() {
        mat = new Mat();
        cvSink = CameraServer.getVideo();
        outputStream = CameraServer.putVideo("AprilTags", 640, 480);
        detector = new AprilTagDetector();
        detector.addFamily("16h5");
    }

    @Override
    public void periodic() {
        // Ensure no error
        if (cvSink.grabFrame(mat) == 0) { // Returns 0 if error
            outputStream.notifyError(cvSink.getError());
            return;
        }

        // Detect all any AprilTags and display their ID on a video feed
        List<AprilTagDetection> detections = Arrays.asList(detector.detect(mat));
        for (AprilTagDetection detection : detections) {
            Imgproc.putText(mat,
                    Integer.toString(detection.getId()),
                    new Point(detection.getCenterX(), detection.getCenterY()),
                    Imgproc.FONT_HERSHEY_DUPLEX,
                    1,
                    new Scalar(0, 255, 0));
        }
        outputStream.putFrame(mat);
    }

}