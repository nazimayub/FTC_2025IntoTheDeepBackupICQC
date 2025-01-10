package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Const;

@TeleOp
public class EncoderTuning extends LinearOpMode {
    DcMotorEx fl, fr, bl, br;


    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotorEx.class, Const.fl);
        bl = hardwareMap.get(DcMotorEx.class, Const.bl);
        fr = hardwareMap.get(DcMotorEx.class, Const.fr);
        br = hardwareMap.get(DcMotorEx.class, Const.br);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("Front Left", fl.getCurrentPosition());
            telemetry.addData("Front Right", fr.getCurrentPosition());
            telemetry.addData("Back Left", bl.getCurrentPosition());
            telemetry.addData("Back Right", br.getCurrentPosition());
            telemetry.update();

            if(gamepad1.a){
                fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
        }
    }
}
