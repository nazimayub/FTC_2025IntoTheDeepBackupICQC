package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class ServoTuning extends OpMode {
    double s, s1, s2, s3, s4, s5;
    Servo servo, servo1, servo2, servo3, servo4, servo5;

    @Override
    public void init() {
        s = 0;
        s1 = .19;
        s2 = 0;
        s3 = .5;
        s4 = 0.7;
        s5 = .025;

        servo = hardwareMap.get(Servo.class, "servo0");
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo3 = hardwareMap.get(Servo.class, "servo3");
        servo4 = hardwareMap.get(Servo.class, "servo4");
        servo5 = hardwareMap.get(Servo.class, "servo5");
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_left){
            s+=0.0001;
        }
        else if (gamepad1.dpad_up){
            s-=0.0001;
        }
        if(gamepad1.y){
            s1+=0.0001;
        }
        else if (gamepad1.b){
            s1-=0.0001;
        }
        if(gamepad1.dpad_right){
            s2+=0.0001;
        }
        else if (gamepad1.dpad_down){
            s2-=0.0001;
        }
        if(gamepad1.x){
            s3+=0.0001;
        }
        else if (gamepad1.a){
            s3-=0.0001;
        }
        if(gamepad1.right_bumper){
            s4+=0.0001;
        }
        else if (gamepad1.left_bumper){
            s4-=0.0001;
        }
        if(gamepad1.right_stick_button){
            s5+=0.0001;
        }
        else if (gamepad1.left_stick_button){
            s5-=0.0001;
        }

        s = Math.max(Math.min(1, s), 0);
        s1 = Math.max(Math.min(1, s1), 0);
        s2 = Math.max(Math.min(1, s2), 0);
        s3 = Math.max(Math.min(1, s3), 0);
        s4 = Math.max(Math.min(1, s4), 0);
        s5 = Math.max(Math.min(1, s5), 0);

        servo.setPosition(s);
        servo1.setPosition(s1);
          servo2.setPosition(s2);
        servo3.setPosition(s3);
        servo4.setPosition(s4);
        servo5.setPosition(s5);

        telemetry.addLine("Servo 1 value " + s);
        telemetry.addLine("Servo 2 value " + s1);
        telemetry.addLine("Servo 3 value " + s2);
        telemetry.addLine("Servo 4 value " + s3);
        telemetry.addLine("Servo 5 value " + s4);
        telemetry.addLine("Servo 6 value " + s5);
        telemetry.update();
    }
}