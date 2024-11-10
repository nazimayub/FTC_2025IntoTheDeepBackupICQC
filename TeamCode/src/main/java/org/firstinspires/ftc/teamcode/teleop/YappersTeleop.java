package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class YappersTeleop extends LinearOpMode{
    private Blinker control_Hub;
    private Blinker expansion_Hub_2;
    private DcMotor arm;
    private Servo hand;
    private Gyroscope imu;
    private CRServo intake;
    private DcMotor bL;
    private DcMotor fL;
    private DcMotor leftSlide;
    private DcMotor bR;
    private DcMotor fR;
    private DcMotor rightSlide;

    // todo: write your code here
    @Override
    public void runOpMode(){
        bL = hardwareMap.dcMotor.get("backLeft");
        fL = hardwareMap.dcMotor.get("frontLeft");
        bR = hardwareMap.dcMotor.get("backRight");
        fR = hardwareMap.dcMotor.get("frontRight");

        bR.setDirection(DcMotor.Direction.REVERSE);
        fR.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        while(opModeIsActive()){

            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.right_stick_x;
            double turn = gamepad1.left_stick_x;
            fR.setPower(drive-turn-strafe);
            fL.setPower(drive+turn+strafe);
            bR.setPower(drive-turn+strafe);
            bL.setPower(drive+turn-strafe);

            fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }




    }
}




























