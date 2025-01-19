package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

@Config
public class Const {
    public static final IMU.Parameters IMU_ORIENTATION = new IMU.Parameters(new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
            RevHubOrientationOnRobot.UsbFacingDirection.UP));

    //Names for motors and servos
    public static final String fr = "fr", fl = "fl", br = "br", bl = "bl",
            lSlide= "lSlide", rSlide = "rSlide", hSlide = "hSlide", intake = "intakeMotor", vLimit = "vertLimitSwitch", hLimit = "horizontalLimitSwitch",
            intakeRot = "servo5", intakeDist = "servo0", outtakeClaw = "servo4", outtakeRot = "servo2", outtakeTwist = "servo3", outtakeDistRight = "servo1", outtakeDistLeft = "servo0", blocker = "servo6",
            imu = "imu", odo = "odo", rightHang = "servo7", leftHang = "servo8";

    //Intake
    public static double intakeInitTransferPos = .569, intakeFinalTransferPos = .839, intakeDownPos = 0.204, intakeSecondFinalTransferPos = .569,
            block = 0.03, unblock = 0.12;

    //Outtake
    public static double outtakeClawDistTempTransfer = 0.74, outtakeClawDistInitTransfer = 1, outtakeClawDistNew = 0.7, outtakeClawDistFinalTransfer = .605, outtakeClawRotTransfer = .768,
            grab = 0, release = 0.235,
            distBasketPos = .615, rotBasketPos = .448,
            distSpecimenGrab = .321, rotSpecimenGrab = .753, distSpecimenGrabFinal = 0.6;

    //Slides
    public static double hSlideExtend = 400, vSlideBasket = 2750, vSlideBar = 675;

    //Misc
    public static long pause = 300;

    public static String direction = "forward";
    public static final double tickInInch = 590/24.0, lateralTickInInch = 540/24.0, tickInDeg = 1600/360.0;
    public static final String GROUP_ANDROID = "";
}