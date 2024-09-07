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
    public static final String transfer = "transfer", drone = "droneLauncher", rSlide = "rightSlide", lSlide = "leftSlide", fr = "rightFront", fl = "leftFront", br = "rightBack", bl = "leftBack", hand = "rightHandServo", intake = "intake", imu = "imu";
    //Hand Pos
    public static final double out = 0.9534, in = 0.165;
    //Drone Pos
    public static final double launch = 1, load = 0;
    //Outtake Positions
    public static final double lScore = 0.5, rScore = 0.5, lStow = 0.5, rStow = 0.5;
    //Claw Position
    public static final double lDrop = 0.5, rDrop = 0.5, lGrab = 0.5, rGrab = 0.5;

}
