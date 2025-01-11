package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;

@TeleOp
public class DriveTest2 extends LinearOpMode {
    DcMotorEx fl, fr, bl, br;


    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotorEx.class, Constants.fl);
        bl = hardwareMap.get(DcMotorEx.class, Constants.bl);
        fr = hardwareMap.get(DcMotorEx.class, Constants.fr);
        br = hardwareMap.get(DcMotorEx.class, Constants.br);
        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.a){
                fl.setPower(1);
                fr.setPower(1);
                bl.setPower(1);
                br.setPower(1);
            }
            else{
                fl.setPower(0);
                fr.setPower(0);
                bl.setPower(0);
                br.setPower(0);
            }
            if(gamepad1.b){
                fl.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            if(gamepad1.y){
                fr.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            if(gamepad1.x){
                bl.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            if(gamepad1.dpad_up){
                br.setDirection(DcMotorSimple.Direction.REVERSE);
            }
        }
    }
}
