package org.firstinspires.ftc.teamcode.BackupBot;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

@Autonomous(name = "April Tag Follower", group = ".Auton")
public class AprilTagFollower extends LinearOpMode {

    private Limelight3A limelight;

    // Hardware components
    private DcMotor leftFrontDrive;
    private DcMotor rightFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;

    // PID Controller variables - not final to allow tuning
    private double kpDrive = 0.05;  // Proportional gain for driving toward/away from tag
    private double kiDrive = 0.0;   // Integral gain for driving
    private double kdDrive = 0.01;  // Derivative gain for driving

    private double kpSteer = 0.05;  // Proportional gain for steering
    private double kiSteer = 0.0;   // Integral gain for steering
    private double kdSteer = 0.01;  // Derivative gain for steering

    // PID state variables
    private double driveIntegral = 0;
    private double steerIntegral = 0;
    private double lastDriveError = 0;
    private double lastSteerError = 0;

    // Target values
    private double targetDistance = 0.5; // meters - adjust based on desired following distance
    private ElapsedTime pidTimer = new ElapsedTime();

    // Tag detection thresholds
    private boolean tagVisible = false;
    private List<LLResultTypes.FiducialResult> lastSeenTagId = -1;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize hardware
        initializeHardware();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Configure Limelight for AprilTag detection
        limelight.pipelineSwitch(0); // Make sure this is the correct pipeline for AprilTags
        limelight.start();

        waitForStart();
        pidTimer.reset();

        while (opModeIsActive()) {
            // Get the latest data from Limelight
            LLResult result = limelight.getLatestResult();

            // Check if any AprilTags are visible - using valid result instead of hasTargets()
            if (result != null && result.isValid() && result.getTa() > 0.01) {
                tagVisible = true;

                // Get the primary AprilTag data
                double tx = result.getTx(); // Horizontal offset from crosshair to target
                double ty = result.getTy(); // Vertical offset from crosshair to target
                double ta = result.getTa(); // Target area

                // Using updated approach to get tag ID if available
                try {
                    // The actual method to get tag ID may vary based on the Limelight API
                    // This is a placeholder - check Limelight3A documentation for accurate method
                    List<LLResultTypes.FiducialResult> fiducialId = result.getFiducialResults();
                    lastSeenTagId = fiducialId;
                    telemetry.addData("Tag ID", lastSeenTagId);
                } catch (Exception e) {
                    // If the method doesn't exist or fails, catch the exception
                    telemetry.addData("Tag ID", "Unknown");
                }

                // Calculate the distance (this is an approximation)
                // You may need to calibrate this based on your specific setup
                double distance = 5.0 / Math.tan(Math.toRadians(ty + 10)); // Example formula

                // Run PID controllers
                double dt = pidTimer.seconds();
                pidTimer.reset();

                // Steering PID (left/right)
                double steerError = -tx; // Negate because positive tx means tag is to the right
                steerIntegral += steerError * dt;
                double steerDerivative = (steerError - lastSteerError) / dt;
                double steerOutput = kpSteer * steerError + kiSteer * steerIntegral + kdSteer * steerDerivative;
                lastSteerError = steerError;

                // Drive PID (forward/backward)
                double driveError = distance - targetDistance;
                driveIntegral += driveError * dt;
                double driveDerivative = (driveError - lastDriveError) / dt;
                double driveOutput = kpDrive * driveError + kiDrive * driveIntegral + kdDrive * driveDerivative;
                lastDriveError = driveError;

                // Apply motor power with limits
                driveOutput = Range.clip(driveOutput, -0.5, 0.5);
                steerOutput = Range.clip(steerOutput, -0.3, 0.3);

                // Move the robot based on PID outputs
                double leftFrontPower = driveOutput - steerOutput;
                double rightFrontPower = driveOutput + steerOutput;
                double leftBackPower = driveOutput - steerOutput;
                double rightBackPower = driveOutput + steerOutput;

                // Normalize wheel powers if any exceed +/- 1.0
                double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
                max = Math.max(max, Math.abs(leftBackPower));
                max = Math.max(max, Math.abs(rightBackPower));

                if (max > 1.0) {
                    leftFrontPower /= max;
                    rightFrontPower /= max;
                    leftBackPower /= max;
                    rightBackPower /= max;
                }

                // Set motor powers
                leftFrontDrive.setPower(leftFrontPower);
                rightFrontDrive.setPower(rightFrontPower);
                leftBackDrive.setPower(leftBackPower);
                rightBackDrive.setPower(rightBackPower);

                // Display information for debugging
                telemetry.addData("Tag Visible", "Yes");
                telemetry.addData("tx", tx);
                telemetry.addData("ty", ty);
                telemetry.addData("Distance (approx)", "%.2f m", distance);
                telemetry.addData("Steer Output", "%.2f", steerOutput);
                telemetry.addData("Drive Output", "%.2f", driveOutput);
            } else {
                // No tag detected, stop motors
                tagVisible = false;
                stopRobot();
                telemetry.addData("Tag Visible", "No");
            }

            telemetry.update();
        }

        // Stop the robot when done
        stopRobot();
    }

    private void initializeHardware() {
        // Initialize Limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        // Initialize motors
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFront");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBack");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBack");

        // Set motor directions (adjust based on your robot's configuration)
        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set zero power behavior
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private void stopRobot() {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
}