package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="MyMotorTest", group="Linear Opcode")
public class motortest extends LinearOpMode {

    @Override
    public void runOpMode() {

        DcMotor myMotor = hardwareMap.get(DcMotor.class, "test");

        myMotor.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        telemetry.addData("Status", "Running");
        telemetry.update();

        while (opModeIsActive()) {

            double motorPower = gamepad1.left_stick_y;
            myMotor.setPower(motorPower);

            telemetry.addData("Motor Power", myMotor.getPower());
            telemetry.update();
        }
    }
}