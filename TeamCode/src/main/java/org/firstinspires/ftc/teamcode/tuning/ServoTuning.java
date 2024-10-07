package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class ServoTuning extends OpMode {
    double s, s1;
    Servo servo, servo1;



    @Override
    public void init() {
        s1 = 0;
        s = 0;

        servo = hardwareMap.get(Servo.class, "servo");
        servo1 = hardwareMap.get(Servo.class, "servo1");
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_up){
            s+=0.001;
        }
        else if (gamepad1.dpad_down){
            s-=0.001;
        }
        if(gamepad1.y){
            s1+=0.001;
        }
        else if (gamepad1.a){
            s1-=0.001;
        }
        if(s1<0){
            s1 = 0;
        }
        else if (s1 > 1){
            s1 = 1;
        }
        if(s<0){
            s = 0;
        }
        else if (s>1){
            s = 1;
        }
        servo.setPosition(s);
        servo1.setPosition(s1);
        telemetry.addLine("Servo 1 value " + s);
        telemetry.addLine("Servo 2 value " + s1);
        telemetry.update();
    }
}
