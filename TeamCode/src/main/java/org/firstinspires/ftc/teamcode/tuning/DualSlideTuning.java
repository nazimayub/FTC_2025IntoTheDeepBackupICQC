package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class DualSlideTuning extends OpMode {

    public static double p = 0.005, i = 0, d = 0.0;
    public static double f = 0.1;
    public static double target = 500;

    private PIDController controller1, controller2;
    private int pos1, pos2;
    private DcMotorEx motor1, motor2;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        motor1 = hardwareMap.get(DcMotorEx.class, "lSlide");
        motor2 = hardwareMap.get(DcMotorEx.class, "rSlide");

        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);

        motor1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        controller1 = new PIDController(p, i, d);
        controller2 = new PIDController(p, i, d);
    }

    @Override
    public void loop() {
        controller1.setPID(p, i, d);
        pos1 = motor1.getCurrentPosition();
        double pid1 = controller1.calculate(pos1, target);
        double power1 = pid1 + f;
        motor1.setPower(power1);

        controller2.setPID(p, i, d);
        pos2 = motor2.getCurrentPosition();
        double pid2 = controller2.calculate(pos2, target);
        double power2 = pid2 + f;
        motor2.setPower(power2);

        telemetry.addData("pos1", pos1);
        telemetry.addData("target1", target);
        telemetry.addData("pos2", pos2);
        telemetry.addData("target2", target);
        telemetry.update();

        // Target Adjustment (using gamepad1)
        if (-gamepad1.left_stick_y > 0) {
            target += 0.2;
            target += 0.2;
        } else if (-gamepad1.left_stick_y < 0) {
            target -= 0.2;
            target -= 0.2;
        }
    }
}