package org.firstinspires.ftc.teamcode.BackupBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOpMain", group = "TeleOp")

public class CraneTest extends OpMode {

    private DcMotor m1;

    @Override
    public void init() {
        m1 = hardwareMap.get(DcMotor.class, "m1");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        m1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        m1.setPower(gamepad1.left_stick_y);
    }
}
