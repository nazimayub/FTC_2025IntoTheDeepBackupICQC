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
<<<<<<< HEAD
    public static final String fr = "fr", fl = "fl", br = "br", bl = "bl", lSlide= "motor1", rSlide = "motor2", hSlide = "motor3", intakeClaw = "servo2", intakeClawRot = "servo1", intakeClawDist = "servo0", outtakeClaw = "servo3", outtakeClawDist = "servo5", imu = "imu";    //Hand Pos
    public static final double out = 0.9534, in = 0.165;
    //Arm Pos
    //Drone Pos
    public static final double launch = 1, load = 0;

    //Intake Pos
    public static final double intClawGrab = 0.67 /*grabs sample, servo 0*/, intClawRel = 0.43 /*releases sample, servo 0*/, intClawIn = .227 /*Rotates claw to the robot servo 1*/, intClawOut = .171 /*Rotates claw out servo 1*/, intMove = .65 /*sets intake arm to outtake servo 2*/;
    //Stow Pos
    public static final double intStow = .16 /*brings intake arm in, servo 2 */, outStowClaw = .3/*stows outtake servo 3 */, outStow = .13/*brings outtake arm in servo 5 */;
=======
    public static final String fR = "fR", fL = "fL", bR = "bR", bL = "bL", vLSlide = "motor1", vRSlide = "motor2", hSlide = "motor3", intakeClaw = "servo2", intakeClawRot = "servo1", intakeClawDist = "servo0", outtakeClaw = "servo3", outtakeClawDist = "servo5", imu = "imu";

    //Intake Pos
    public static final double intClawGrab = 0.67, intClawRel = 0.43, intClawIn = .227, intClawOut = .171, intMove = .65;
    //Outtake Pos
    public static final double outClawGrab = 0.3, outClawRel = 0.6, outtakeIn = .3, outtakeOut = 1;
    //Stow Pos
    public static final double intStow = .16, intStowClaw = .62, outStowClaw = .3, outStow = .13;
>>>>>>> 433915418a97519355275460e5ac59bc847839c4

    public static final String GROUP_ANDROID = "";
}