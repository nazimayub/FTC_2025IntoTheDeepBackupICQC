package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@TeleOp
public class ServoTuning extends OpMode {
    double s1, s3, s4, s5;
    Servo servo1, servo3, servo4, servo5;
    double s6, s7, s8, s9, s10;
    Servo servo6, servo7, servo8, servo9, servo10;

    @Override
    public void init() {
        s1 = 0.2;
        s3 = 0.07;
        //s4 = 0;
        s5 = 0;

        servo1 = hardwareMap.get(Servo.class, "servo5");
        servo3 = hardwareMap.get(Servo.class, "servo0");
        servo4 = hardwareMap.get(Servo.class, "servo1");
        servo5 = hardwareMap.get(Servo.class, "servo3");

        s6 = 0.45;
        s7 = 0.45;
        s8 = 0;
        s9 = 0;
        s10 = .2;

        servo6 = hardwareMap.get(Servo.class, "servo4");
        servo7 = hardwareMap.get(Servo.class, "servo2");
        //servo8 = hardwareMap.get(Servo.class, "servo8");
        //servo9 = hardwareMap.get(Servo.class, "servo7");
        servo10 = hardwareMap.get(Servo.class, "servo6");

        ((ServoImplEx)(servo10)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        ((ServoImplEx)(servo1)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        ((ServoImplEx)(servo3)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        ((ServoImplEx)(servo4)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        ((ServoImplEx)(servo5)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        ((ServoImplEx)(servo6)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        ((ServoImplEx)(servo7)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        //((ServoImplEx)(servo8)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        //((ServoImplEx)(servo9)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));

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
        if(gamepad2.y){
            s4+=0.001;
        }
        else if (gamepad2.b){
            s4-=0.001;
        }
        if(gamepad2.x){
            s5+=0.001;
        }
        else if (gamepad2.a){
            s5-=0.001;
        }

        s1 = Math.max(Math.min(1, s1), 0);
        s3 = Math.max(Math.min(1, s3), 0);
        //s4 = Math.max(Math.min(1, s4), 0);
        s5 = Math.max(Math.min(1, s5), 0);

        servo1.setPosition(s1);
        servo3.setPosition(s3);
        //servo4.setPosition((1-s3));
        servo5.setPosition(s5);

        telemetry.addLine("Servo 1 (intake arm) value " + s1);
        telemetry.addLine("Servo 3 (outtake dist 1) value " + s3);
        telemetry.addLine("Servo 4 (outtake dist 2) value " + (1-s3));
        telemetry.addLine("Servo 5 (outtake twist) value " + s5);

        if(gamepad1.dpad_up){
            s6+=0.001;
        }
        else if (gamepad1.dpad_right){
            s6-=0.001;
        }
        if(gamepad1.dpad_left){
            s7+=0.001;
        }
        else if (gamepad1.dpad_down){
            s7-=0.001;
        }
        if(gamepad2.dpad_up){
            s8+=0.001;
        }
        else if (gamepad2.dpad_right){
            s8-=0.001;
        }
        if(gamepad2.dpad_left){
            s9+=0.001;
        }
        else if (gamepad2.dpad_down){
            s9-=0.001;
        }
        else if (gamepad1.right_bumper){
            s10+=0.001;
        }
        else if (gamepad1.left_bumper) {
            s10-=0.001;
        }

        s6 = Math.max(Math.min(1, s6), 0);
        s7 = Math.max(Math.min(1, s7), 0);
        //s8 = Math.max(Math.min(1, s8), 0);
        //s9 = Math.max(Math.min(1, s9), 0);
        s10 = Math.max(Math.min(1, s10), 0);

        servo6.setPosition(s6);
        servo7.setPosition(s7);
        //servo8.setPosition(s8);
        //servo9.setPosition(s9);
        servo10.setPosition(s10);

        telemetry.addLine("Servo 6 (outtake claw) value " + s6);
        telemetry.addLine("Servo 7 (outtake rot) value " + s7);
        //telemetry.addLine("Servo 8 (left hang) value " + s8);
        //telemetry.addLine("Servo 9 (right hang) value " + s9);
        telemetry.addLine("Servo 10 (gear shifter) value " + s10);
        telemetry.update();
    }
}