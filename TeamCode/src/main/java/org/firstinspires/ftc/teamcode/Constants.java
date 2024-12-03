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
    public static final String fr = "fr", fl = "fl", br = "br", bl = "bl",
            lSlide= "motor1", rSlide = "motor2", hSlide = "motor3", intake = "motor4",
            intakeRot = "servo1", intakeDist = "servo0", outtakeClaw = "servo3", outtakeRot = "servo5", outtakeDist = "servo4",
            imu = "imu", otos = "sensor_otos";

    //Intake
    public static double intakeInitTransferPos = .3, intakeFinalTransferPos = .19, intakeDownPos = 0.53, intakeSecondFinalTransferPos = .35,
            block = 0.03, unblock = 0.12;

    //Outtake
    public static double outtakeClawDistTempTransfer = 0.7, outtakeClawDistInitTransfer = .93, outtakeClawDistFinalTransfer = .6, outtakeClawRotTransfer = 0.175,
            grab = 0.22, release = 0.5,
            distBasketPos = .619, rotBasketPos = .194,
            distSpecimenGrab = 0.34, rotSpecimenGrab = .715, distSpecimenGrabFinal = 0.6,
            distSpecimenScorePos = .45, rotSpecimenScorePos = .55, distFinalSpecimenScorePos = .4;


    public static String direction = "forward";
    public static final double tickInInch = 590/24.0, lateralTickInInch = 540/24.0, tickInDeg = 1600/360.0;
    public static final String GROUP_ANDROID = "";
}