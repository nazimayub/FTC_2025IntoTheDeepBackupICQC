package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class ServoTuning extends OpMode {
    double s1, s3, s4, s5;
    Servo servo1, servo3, servo4, servo5;

    @Override
    public void init() {
        s1 = 0;
        s3 = 0;
        s4 = 0;
        s5 = 0;

        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo3 = hardwareMap.get(Servo.class, "servo3");
        servo4 = hardwareMap.get(Servo.class, "servo4");
        servo5 = hardwareMap.get(Servo.class, "servo5");
    }

    @Override
    public void loop() {
        if(gamepad1.y){
            s1+=0.001;
        }
        else if (gamepad1.b){
            s1-=0.001;
        }
        if(gamepad1.x){
            s3+=0.001;
        }
        else if (gamepad1.a){
            s3-=0.001;
        }
        if(gamepad1.right_bumper){
            s4+=0.001;
        }
        else if (gamepad1.left_bumper){
            s4-=0.001;
        }
        if(gamepad1.right_stick_button){
            s5+=0.001;
        }
        else if (gamepad1.left_stick_button){
            s5-=0.001;
        }

        s1 = Math.max(Math.min(1, s1), 0);
        s3 = Math.max(Math.min(1, s3), 0);
        s4 = Math.max(Math.min(1, s4), 0);
        s5 = Math.max(Math.min(1, s5), 0);

        servo1.setPosition(s1);
        servo3.setPosition(s3);
        servo4.setPosition(s4);
        servo5.setPosition(s5);

        telemetry.addLine("Servo 1 (intake arm) value " + s1);
        telemetry.addLine("Servo 3 (outtake claw) value " + s3);
        telemetry.addLine("Servo 4 (outtake dist) value " + s4);
        telemetry.addLine("Servo 5 (outtake rot) value " + s5);
        telemetry.update();
    }
}