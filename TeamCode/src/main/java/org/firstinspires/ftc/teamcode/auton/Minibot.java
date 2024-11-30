package org.firstinspires.ftc.teamcode.auton;

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



public class Minibot extends RobotDrive { //pull in everything from RobotDrive

    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap); //init robot drive
    }
    public static void grabPos(){
        outtakeClaw.setPosition(0.54);
        outtakeClawDist.setPosition(.34);
        outtakeClawRot.setPosition(0.565);
    }

    public static void grab(){
        outtakeClaw.setPosition(.25);
    }

    public static void scorePos(){
        outtakeClaw.setPosition(.25);
        outtakeClawDist.setPosition(.619);
        outtakeClawRot.setPosition(0.025);
        setPos(250);
    }

    public static void score(){
        outtakeClaw.setPosition(.25);
        outtakeClawDist.setPosition(.45);
        outtakeClawRot.setPosition(0.4);
        setPos(0);
    }

    public static void setPos(int t){
        while(Math.abs(t-lSlide.getCurrentPosition())>3 || Math.abs(t-lSlide.getCurrentPosition())>3){
            lSlide.setPower(new PIDController(0.06, 0, 0.0004).calculate(t, lSlide.getCurrentPosition()) + 0.2);
            rSlide.setPower(new PIDController(0.06, 0, 0.0004).calculate(t, rSlide.getCurrentPosition()) + 0.2);
        }
        lSlide.setPower(0.2);
        rSlide.setPower(0.2);
    }
}