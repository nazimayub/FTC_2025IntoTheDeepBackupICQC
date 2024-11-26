package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class ExplainHowServoWorkToIrfan extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo rot = hardwareMap.get(Servo.class, "servo5");
        Servo dist = hardwareMap.get(Servo.class, "servo4");
        waitForStart();
        while(opModeIsActive()){
            rot.setPosition(.5);
            dist.setPosition(.5);
        }
    }
}
