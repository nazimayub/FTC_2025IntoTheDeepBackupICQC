package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

@Config
public class Constants {
    public static final IMU.Parameters IMU_ORIENTATION = new IMU.Parameters(new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
            RevHubOrientationOnRobot.UsbFacingDirection.UP));

    //Names for motors and servos
    public static final String outtakeClawRot = "servo5", fr = "fr", fl = "fl", br = "br", bl = "bl",
            lSlide= "motor1", rSlide = "motor2", hSlide = "motor3",
            intake = "motor4", intakeClawRot = "servo1", intakeClawDist = "servo0", outtakeClaw = "servo3", outtakeClawDist = "servo4",
            imu = "imu";

    //Intake
    public static double intakeInitTransferPos = 0, intakeFinalTransferPos = 0, intakeDownPos = 0;
    //Outtake
    public static double outtakeClawDistInitTransfer = 0, outtakeClawDistFinalTransfer = 0, outtakeClawRotTransfer = 0,
            grab = 0, release = 0,
            distBasketPos = 0, rotBasketPos = 0,
            distSpecimenGrab = 0, rotSpecimenGrab = 0,
            distInitSpecimenScorePos = 0, rotSpecimenScorePos = 0, distFinalSpecimenScorePos = 0;


    public static String direction = "forward";
    public static final double tickInInch = 590/24.0, lateralTickInInch = 540/24.0, tickInDeg = 1600/360.0;
    public static final String GROUP_ANDROID = "";
}