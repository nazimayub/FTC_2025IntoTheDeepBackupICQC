package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoTesting extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo;


        servo = hardwareMap.get(Servo.class, "servo");


        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a){
                servo.setPosition(1);
            }
            else if (gamepad1.b) {
                servo.setPosition(0);
            }
            telemetry.addData("Servo Position ", servo.getPosition());
            telemetry.update();
        }
    }
}
