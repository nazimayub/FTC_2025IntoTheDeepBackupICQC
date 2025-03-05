package org.firstinspires.ftc.teamcode.base.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class VisionSubSystem extends SubsystemBase {

    OpenCvCamera camera;
    private HardwareMap h;
    Mat hsvMat;
    public static String detectedColor = " ";
    private static final int CAMERA_WIDTH = 1280;
    private static final int CAMERA_HEIGHT = 720;

    public VisionSubSystem(HardwareMap h) {
        this.h = h;
        initCam();
    }

    public void initCam() {
        try {
            int cameraMonitorViewId = h.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", h.appContext.getPackageName());
            camera = OpenCvCameraFactory.getInstance().createWebcam(h.get(WebcamName.class, "webcam1"), cameraMonitorViewId);
            camera.setPipeline(new VisionSubSystem.ColorDetectionPipeline());
            camera.openCameraDevice();
            camera.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPSIDE_DOWN);
        } catch (Exception e) {
            telemetry.addData("Error", e.getMessage());
            telemetry.update();
        }
    }

    public static class ColorDetectionPipeline extends OpenCvPipeline {

        @Override
        public Mat processFrame(Mat input) {
            Mat hsvMat = new Mat();
            Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

            Scalar lowerRed = new Scalar(0, 100, 100);
            Scalar upperRed = new Scalar(10, 255, 255);
            Scalar lowerBlue = new Scalar(110, 100, 100);
            Scalar upperBlue = new Scalar(130, 255, 255);
            Scalar lowerYellow = new Scalar(20, 100, 100);
            Scalar upperYellow = new Scalar(30, 255, 255);

            Mat roiMat = hsvMat.submat(new Rect(100, 100, 120, 120));

            Mat maskRed = new Mat();
            Mat maskBlue = new Mat();
            Mat maskYellow = new Mat();

            Core.inRange(roiMat, lowerRed, upperRed, maskRed);
            Core.inRange(roiMat, lowerBlue, upperBlue, maskBlue);
            Core.inRange(roiMat, lowerYellow, upperYellow, maskYellow);

            int red = Core.countNonZero(maskRed);
            int blue = Core.countNonZero(maskBlue);
            int yellow = Core.countNonZero(maskYellow);

            if (red > blue && red > yellow) {
                detectedColor = "Red";
            } else if (blue > red && blue > yellow) {
                detectedColor = "Blue";
            } else if (yellow > red && yellow > blue) {
                detectedColor = "Yellow";
            } else {
                detectedColor = "Unknown";
            }

            return input;
        }
    }

    public String getDetectedColor() {
        return detectedColor;
    }
}
