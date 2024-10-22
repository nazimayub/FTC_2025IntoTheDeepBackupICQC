package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver;

@Config
public class Drive extends SubsystemBase {
    DcMotor fr, fl, br, bl;
    GoBildaPinpointDriver odo;
    public static boolean isFinished = false;
    public double currentX, currentY, currentTheta, deltaX, deltaY, deltaTheta, powerX, powerY, powerTheta;
    public double flpower, frpower, blpower, brpower;

    public static PIDController xController = new PIDController(0.125, 0, 0.01);
    public static PIDController yController = new PIDController(0.125, 0, 0.01);
    public static PIDController thetaController = new PIDController(0.1, 0, 0.01);

    public Drive(HardwareMap hardwareMap) {
        isFinished = false;
        fr = hardwareMap.get(DcMotor.class, "rf");
        fl = hardwareMap.get(DcMotor.class, "lf");
        br = hardwareMap.get(DcMotor.class, "rb");
        bl = hardwareMap.get(DcMotor.class, "lb");

        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");
        odo.setOffsets(Constants.ODOOFFSETX, Constants.ODOOFFSETY);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odo.resetPosAndIMU();
    }

    public void reset() {
        currentX = 0;
        currentTheta = 0;
        currentY = 0;
        deltaTheta = 0;
        deltaX = 0;
        deltaY = 0;
        powerX = 0;
        powerY = 0;
        powerTheta = 0;
    }

    public void driveToPosition(double x, double y, double theta, double power, double tolerance) {
        isFinished = false;
        // Ensure a minimum tolerance (in inches)
        tolerance = Math.max(tolerance, 0.1);  // Set minimum tolerance to 0.1 inches

        // Update the odometry values
        odo.update();

        // Get the current position in inches from odometry (Pose2D object)
        currentX = odo.getPosition().getX(DistanceUnit.INCH);
        currentY = odo.getPosition().getY(DistanceUnit.INCH);
        currentTheta = odo.getPosition().getHeading(AngleUnit.DEGREES);

        // Calculate the distance from the target in field-centric coordinates
        deltaX = x - currentX;
        deltaY = y - currentY;
        deltaTheta = AngleUnit.normalizeDegrees(theta - currentTheta);

        // Convert field-centric coordinates to robot-centric
        double robotX = deltaX * Math.cos(Math.toRadians(currentTheta)) + deltaY * Math.sin(Math.toRadians(currentTheta));
        double robotY = -deltaX * Math.sin(Math.toRadians(currentTheta)) + deltaY * Math.cos(Math.toRadians(currentTheta));

        // If within tolerance, stop the robot
        if (Math.abs(robotX) < tolerance && Math.abs(robotY) < tolerance && Math.abs(deltaTheta) < tolerance) {
            stopRobot();
            isFinished = true;
            return;
        }

        // Use PID controllers to calculate power
        powerX = xController.calculate(currentX, robotX);
        powerY = yController.calculate(currentY, robotY);
        powerTheta = thetaController.calculate(currentTheta, deltaTheta);

        // Limit power values to the max power
        powerX = Math.min(power, Math.max(-power, powerX));
        powerY = Math.min(power, Math.max(-power, powerY));
        powerTheta = Math.min(power, Math.max(-power, powerTheta));

        // Drive the robot
        drive(powerX, powerY, powerTheta);
    }
    // Example of a drive method that applies power to motors
    private void drive(double xPower, double yPower, double thetaPower) {

        // Use xPower, yPower, and thetaPower to drive your robot's motors
        // Example for mecanum wheels or holonomic drive:
        fl.setPower(-yPower + xPower + 2*thetaPower);
        fr.setPower(-yPower - xPower - 2*thetaPower);
        bl.setPower(-yPower - xPower + 2*thetaPower);
        br.setPower(-yPower + xPower - 2*thetaPower);

        flpower = fl.getPower();
        frpower = fr.getPower();
        brpower = br.getPower();
        blpower = bl.getPower();
    }

    // Stop method to halt the motors
    public void stopRobot() {
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }

    public Pose2D getPosition() {
        return odo.getPosition();
    }

    public double getX() {
        return odo.getPosition().getX(DistanceUnit.INCH);
    }

    public double getY() {
        return odo.getPosition().getY(DistanceUnit.INCH);
    }

    public double getHeading() {
        return odo.getPosition().getHeading(AngleUnit.DEGREES);
    }

    public boolean isCompleted() {
        return isFinished;
    }

    public void setCompletionStatus(boolean status) {
        isFinished = status;
    }
}