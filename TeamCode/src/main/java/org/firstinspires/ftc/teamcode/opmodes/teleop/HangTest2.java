package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class HangTest2 extends OpMode {

    public static double p = 0, i = 0, d = 0.0;
    public static double f = 0;
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
        motor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);


        motor1.setTargetPosition(30000);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setTargetPosition(30000);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void loop() {
        motor1.setPower(0.5);
        motor2.setPower(0.5);
    }
}