package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver;

public class Drive extends SubsystemBase {
    DcMotor fr,fl,br,bl;
    GoBildaPinpointDriver odo;
    public Drive(HardwareMap hardwareMap){
        fr = hardwareMap.get(DcMotor.class,"rf");
        fl = hardwareMap.get(DcMotor.class,"lf");
        br = hardwareMap.get(DcMotor.class,"rb");
        bl = hardwareMap.get(DcMotor.class,"lb");

        odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        odo.setOffsets(Constants.ODOOFFSETX, Constants.ODOOFFSETY); //these are tuned for 3110-0002-0001 Product Insight #1
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odo.resetPosAndIMU(); // reset
    }

    public void driveToPosition(double x, double y, double theta, double power, double tolerance) {
        // Ensure a minimum tolerance (in inches)
        tolerance = Math.max(tolerance, 0.5);  // Set minimum tolerance to 0.5 inches

        // Loop until the robot reaches the desired position within tolerance or op mode stops
        while (true) { // TODO: may need opmodeisactive() for later -_-
            // Update the odometry values
            odo.update();

            // Get the current position in inches from odometry (Pose2D object)
            double currentX = odo.getPosition().getX(DistanceUnit.INCH);
            double currentY = odo.getPosition().getY(DistanceUnit.INCH);
            double currentTheta = odo.getPosition().getHeading(AngleUnit.DEGREES);

            // Calculate the distance from the target
            double deltaX = x - currentX;
            double deltaY = y - currentY;
            double deltaTheta = theta - currentTheta;

            // If within tolerance, stop the loop
            if (Math.abs(deltaX) < tolerance && Math.abs(deltaY) < tolerance && Math.abs(deltaTheta) < tolerance) {
                stopRobot();  // Implement your own stop function to stop motors
                break;
            }

            // Use basic proportional control to adjust movement (consider adding PID here)
            double kP = 0.01;  // Proportional constant for power adjustment
            double powerX = kP * deltaX;
            double powerY = kP * deltaY;
            double powerTheta = kP * deltaTheta;

            // Limit power values to the max power
            powerX = Math.min(power, Math.max(-power, powerX));
            powerY = Math.min(power, Math.max(-power, powerY));
            powerTheta = Math.min(power, Math.max(-power, powerTheta));

            // Drive the robot (implement your own drive method that takes x, y, and rotation power)
            drive(powerX, powerY, powerTheta);
        }
    }

    // Example of a drive method that applies power to motors
    private void drive(double xPower, double yPower, double thetaPower) {
        // Use xPower, yPower, and thetaPower to drive your robot's motors
        // Example for mecanum wheels or holonomic drive:
        fl.setPower(yPower + xPower + thetaPower);
        fr.setPower(yPower - xPower - thetaPower);
        bl.setPower(yPower - xPower + thetaPower);
        br.setPower(yPower + xPower - thetaPower);
    }

    // Stop method to halt the motors
    private void stopRobot() {
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }
}
