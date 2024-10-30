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
    public static final String fR = "fR", fL = "fL", bR = "bR", bL = "bL", vLSlide = "motor1", vRSlide = "motor2", hSlide = "motor3", intakeClaw = "servo2", intakeClawRot = "servo1", intakeClawDist = "servo2", outtakeClaw = "servo3", outtakeClawDist = "servo5", imu = "imu";

    //Intake Pos
    public static final double grabInt = 0.67, relInt = 0.43, clawIn = .97, clawOut = .65;
    //Outtake Pos
    public static final double grabOut = 0.3, relOut = 0.6;
    //Stow Pos
    public static final double intStowIn = 0.17, intStowOut = 0.65, outStowIn = 0.17, outStowOut = 1.0;

    public static final String GROUP_ANDROID = "";
}
