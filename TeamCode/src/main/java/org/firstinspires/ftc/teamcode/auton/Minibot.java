package org.firstinspires.ftc.teamcode.auton;

import static java.lang.Thread.sleep;

import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.auton.RobotDrive;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Minibot extends RobotDrive { //pull in everything from RobotDrive

    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap); //init robot drive
    }
    public static void grabPos(){
        outtakeClaw.setPosition(0.5);
        outtakeClawDist.setPosition(.34);
        outtakeClawRot.setPosition(.715);
    }

    public static void grab(){
        outtakeClaw.setPosition(.25);
    }
    public static void scoreBasket(){
        setPos(1500);
        outtakeClawDist.setPosition(.6);
        outtakeClawRot.setPosition(.175);
        ElapsedTime t = new ElapsedTime();
        while(t.milliseconds()<500){

        }
        outtakeClaw.setPosition(.5);

    }
    public void scorePos(){
        outtakeClaw.setPosition(.22);
        outtakeClawDist.setPosition(.625);
        outtakeClawRot.setPosition(.424);
        set(50);
        //while(Math.abs(250-lSlide.getCurrentPosition())>3 || Math.abs(250-rSlide.getCurrentPosition())>3){
        //    lSlide.setPower(new PIDController(0.06, 0, 0.0004).calculate(250, lSlide.getCurrentPosition()) + 0.2);
        //    rSlide.setPower(new PIDController(0.06, 0, 0.0004).calculate(250, rSlide.getCurrentPosition()) + 0.2);
        //}
        //lSlide.setPower(0.2);
        //rSlide.setPower(0.2);
    }

    public void resetEncoders(){
        super.resetEncoders();
    }

    public static void score(){
        outtakeClaw.setPosition(.22);
        outtakeClawDist.setPosition(.45);
        outtakeClawRot.setPosition(.55);
        ElapsedTime t = new ElapsedTime();
        while((Math.abs(0-lSlide.getCurrentPosition())>3 || Math.abs(0-lSlide.getCurrentPosition())>3) && t.milliseconds()<2000){
            lSlide.setPower(-1);
            rSlide.setPower(-1);
        }
        lSlide.setPower(0);
        rSlide.setPower(0);
    }

    public static void setPos(int t){
        while(Math.abs(t+lSlide.getCurrentPosition())>3 || Math.abs(t-rSlide.getCurrentPosition())>3){
            lSlide.setPower(new PIDController(0.06, 0, 0.0004).calculate(t, -1*lSlide.getCurrentPosition()) + 0.2);
            rSlide.setPower(new PIDController(0.06, 0, 0.0004).calculate(t, rSlide.getCurrentPosition()) + 0.2);
        }
        lSlide.setPower(0.2);
        rSlide.setPower(0.2);
    }
    public static void set(int t){
        while(Math.abs(t+lSlide.getCurrentPosition())>3 && Math.abs(t-rSlide.getCurrentPosition())>3){
            lSlide.setPower(.75);
            rSlide.setPower(.75);
        }
        lSlide.setPower(0.2);
        rSlide.setPower(0.2);
    }
}