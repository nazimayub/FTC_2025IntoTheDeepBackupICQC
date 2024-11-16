package org.firstinspires.ftc.teamcode.tuning;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;

public class EncoderTuning extends LinearOpMode {
    DcMotorEx fl, fr, bl, br;


    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotorEx.class, Constants.fl);
        bl = hardwareMap.get(DcMotorEx.class, Constants.bl);
        fr = hardwareMap.get(DcMotorEx.class, Constants.fr);
        br = hardwareMap.get(DcMotorEx.class, Constants.br);
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
