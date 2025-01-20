package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@TeleOp
public class ServoTesting extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo;


        servo = hardwareMap.get(Servo.class, "servo0");
        Servo servo1 = hardwareMap.get(Servo.class, "servo1");
        ((ServoImplEx)(servo)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        ((ServoImplEx)(servo1)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));


        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a){
                servo.setPosition(1);
                servo1.setPosition(0);
            }
            else if (gamepad1.b) {
                servo.setPosition(0);
                servo1.setPosition(1);
            }
            telemetry.addData("Servo Position ", servo.getPosition());
            telemetry.update();
        }
    }
}
