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
            lSlide= "motor1", rSlide = "motor2", hSlide = "motor3", intake = "motor4",
            intakeClawRot = "servo1", intakeClawDist = "servo0", outtakeClaw = "servo3", outtakeClawDist = "servo4",
            imu = "imu";

    //Intake
    public static double intakeInitTransferPos = .3, intakeFinalTransferPos = .19, intakeDownPos = 0.536, intakeSecondFinalTransferPos = .35;
    //Outtake
    public static double outtakeClawDistTempTransfer = 0.7, outtakeClawDistInitTransfer = .93, outtakeClawDistFinalTransfer = .6, outtakeClawRotTransfer = 0.025,
            grab = 0.28, release = 0.85,
            distBasketPos = .619, rotBasketPos = .194,
            distSpecimenGrab = 0.32, rotSpecimenGrab = 0.91,
            distInitSpecimenScorePos = 0.52, rotSpecimenScorePos = 0.515, distFinalSpecimenScorePos = .5;


    public static String direction = "forward";
    public static final double tickInInch = 590/24.0, lateralTickInInch = 540/24.0, tickInDeg = 1600/360.0;
    public static final String GROUP_ANDROID = "";
}