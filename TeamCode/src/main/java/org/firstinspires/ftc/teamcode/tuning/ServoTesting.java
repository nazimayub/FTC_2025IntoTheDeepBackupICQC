package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoTesting extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo;
        CRServo crservo;

        servo = hardwareMap.get(Servo.class, "servo");
        crservo = hardwareMap.get(CRServo.class, "crservo");

        waitForStart();

        while(opModeIsActive()){
            crservo.setPower(gamepad1.left_stick_y);
            servo.setPosition(0.5*gamepad1.right_stick_y+0.5);

            telemetry.addData("CRServo Power ", crservo.getPower());
            telemetry.addData("Servo Position ", servo.getPosition());
            telemetry.update();
        }
    }
}
