package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver;

public class Drive extends SubsystemBase {
    DcMotor fr,fl,br,bl;
    GoBildaPinpointDriver odo;
    public static boolean isFinished = false;
    double currentX,currentY,currentTheta,deltaX,deltaY,deltaTheta,kP,powerX,powerY,powerTheta;
    public Drive(HardwareMap hardwareMap){
        isFinished = false;
        fr = hardwareMap.get(DcMotor.class,"rf");
        fl = hardwareMap.get(DcMotor.class,"lf");
        br = hardwareMap.get(DcMotor.class,"rb");
        bl = hardwareMap.get(DcMotor.class,"lb");
        //fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
//        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        odo.setOffsets(Constants.ODOOFFSETX, Constants.ODOOFFSETY); //these are tuned for 3110-0002-0001 Product Insight #1
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odo.resetPosAndIMU(); // reset

    }
    public void reset(){
        currentX =0;
        currentTheta = 0;
        currentY = 0;
        deltaTheta =0;
        deltaX = 0;
        deltaY=0;
        kP =0;
        powerX=0;
        powerY=0;
        powerTheta=0;
    }
    public void driveToPosition(double x, double y, double theta, double power, double tolerance) {
        isFinished = false;
        // Ensure a minimum tolerance (in inches)
        tolerance = Math.max(tolerance, 0.1);  // Set minimum tolerance to 0.5 inches

        // Loop until the robot reaches the desired position within tolerance or op mode stops
        //while (true) { // TODO: may need opmodeisactive() for later -_-
            // Update the odometry values
            odo.update();

            // Get the current position in inches from odometry (Pose2D object)
             currentX = odo.getPosition().getX(DistanceUnit.INCH);
             currentY = odo.getPosition().getY(DistanceUnit.INCH);
             currentTheta = odo.getPosition().getHeading(AngleUnit.DEGREES);

            // Calculate the distance from the target
             deltaX = x - currentX;
             deltaY = y - currentY;
             deltaTheta = theta - currentTheta;

            // If within tolerance, stop the loop
            if (Math.abs(deltaX) < tolerance && Math.abs(deltaY) < tolerance && Math.abs(deltaTheta) < tolerance+5) {
                stopRobot();
                isFinished = true;// Implement your own stop function to stop motors
                //break;
            }

            // Use basic proportional control to adjust movement (consider adding PID here)
             kP = 0.1;  // Proportional constant for power adjustment
             powerX = kP * deltaX;
             powerY = kP * deltaY;
             powerTheta = (kP+0.05) * deltaTheta; // delta here is the error

            // Limit power values to the max power
            powerX = Math.min(power, Math.max(-power, powerX));
            powerY = Math.min(power, Math.max(-power, powerY));
            powerTheta = Math.min(power, Math.max(-power, powerTheta));

            // Drive the robot (implement your own drive method that takes x, y, and rotation power)
            drive(powerX, powerY, powerTheta);
        //}

    }
    // Example of a drive method that applies power to motors
    private void drive(double xPower, double yPower, double thetaPower) {

        // Use xPower, yPower, and thetaPower to drive your robot's motors
        // Example for mecanum wheels or holonomic drive:
        fl.setPower(-yPower + xPower + thetaPower);
        fr.setPower(-yPower - xPower - thetaPower);
        bl.setPower(-yPower - xPower + thetaPower);
        br.setPower(-yPower + xPower - thetaPower);
    }

    // Stop method to halt the motors
    public void stopRobot() {
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }
    public Pose2D getPosition(){
        return odo.getPosition();
    }
    public double getX(){
        return odo.getPosition().getX(DistanceUnit.INCH);
    }
    public double getY(){
        return odo.getPosition().getY(DistanceUnit.INCH);
    }
    public double getHeading(){
        return odo.getPosition().getHeading(AngleUnit.DEGREES);
    }
    public boolean isCompleted(){
        return isFinished;
    }
    public void setCompletionStatus(boolean b){
        isFinished = b;
    }
}
