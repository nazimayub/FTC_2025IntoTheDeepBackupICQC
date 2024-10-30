package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

@Config
public class Constants {
    public static final IMU.Parameters IMU_ORIENTATION = new IMU.Parameters(new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.UP,
            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));

    //Names for motors and servos
    public static final String arm = "arm", transfer = "transfer", drone = "droneLauncher", rSlide = "rightSlide", lSlide = "leftSlide", fr = "rightFront", fl = "leftFront", br = "rightBack", bl = "leftBack", hand = "hand", intake = "intake", imu = "imu";

    //Intake Pos
    public static final double grabInt = 0.0, relInt = 0.0;
    //Outtake Pos
    public static final double grabOut = 0.0, relOut = 0.0;
    //Stow Pos
    public static final double intStowIn = 0.0, intStowOut = 0.0, outStowIn = 0.0, outStowOut = 0.0;

    public static final String GROUP_ANDROID = "";
}