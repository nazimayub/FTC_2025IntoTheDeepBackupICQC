package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.pedropathing.util.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.PwmControl;

import org.firstinspires.ftc.teamcode.Const;

@Config
@TeleOp
public class Tuning extends OpMode {
    public static DcMotorEx vSlideRight, vSlideLeft, hSlide;
    public static Servo outtakeRot, outtakeDistRight, outtakeDistLeft, intakeRot, outtakeClaw, outtakeTwist;

    public static int vSlidePos = 0, hSlidePos = 0;
    public static double outtakeRotPos = 0.5, outtakeDistRightPos = 0.5, outtakeDistLeftPos = 0.5;
    public static double intakeRotPos = 0.5, outtakeClawPos = 0.5, outtakeTwistPos = 0.5;

    public static PIDController vSlideController;
    public static PIDController hSlideController;


    @Override
    public void init() {
        vSlideRight = hardwareMap.get(DcMotorEx.class, Const.rSlide);
        vSlideLeft = hardwareMap.get(DcMotorEx.class, Const.lSlide);
        hSlide = hardwareMap.get(DcMotorEx.class, Const.hSlide);

        vSlideRight.setDirection(DcMotorSimple.Direction.REVERSE);

        vSlideController = new PIDController(.001, 0, 0);
        hSlideController = new PIDController(-.02, 0, 0);

        // Reset encoders
        for (DcMotorEx motor : new DcMotorEx[]{vSlideRight, vSlideLeft, hSlide}) {
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        }

        outtakeRot = hardwareMap.get(Servo.class, Const.outtakeRot);
        outtakeDistRight = hardwareMap.get(Servo.class, Const.outtakeDistRight);
        outtakeDistLeft = hardwareMap.get(Servo.class, Const.outtakeDistLeft);
        intakeRot = hardwareMap.get(Servo.class, Const.intakeRot);
        outtakeClaw = hardwareMap.get(Servo.class, Const.outtakeClaw);
        outtakeTwist = hardwareMap.get(Servo.class, Const.outtakeTwist);

        // Set PWM Range for Servos
        Servo[] servos = {outtakeRot, outtakeDistRight, outtakeDistLeft, intakeRot, outtakeClaw, outtakeTwist};
        for (Servo servo : servos) {
            ((ServoImplEx) servo).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        }
    }

    @Override
    public void loop() {
        // Update PID coefficients in real-time
        vSlideController.setPID(.001, 0, 0);
        hSlideController.setPID(-.02, 0, 0);

        // Get current positions
        int vSlideCurrent = (vSlideRight.getCurrentPosition() + vSlideLeft.getCurrentPosition()) / 2;
        int hSlideCurrent = hSlide.getCurrentPosition();

        // Compute PID output and add feedforward term
        double vSlidePID = vSlideController.calculate(vSlideCurrent, vSlidePos);
        double vSlidePower = vSlidePID + .05;

        double hSlidePID = hSlideController.calculate(hSlideCurrent, hSlidePos);
        double hSlidePower = hSlidePID + 0;

        // Apply power
        vSlideRight.setPower(vSlidePower);
        vSlideLeft.setPower(vSlidePower);
        hSlide.setPower(hSlidePower);

        // Ensure servo positions are within range
        outtakeRot.setPosition(Math.max(0, Math.min(1, outtakeRotPos)));
        outtakeDistRight.setPosition(Math.max(0, Math.min(1, outtakeDistRightPos)));
        outtakeDistLeft.setPosition(Math.max(0, Math.min(1, outtakeDistLeftPos)));
        intakeRot.setPosition(Math.max(0, Math.min(1, intakeRotPos)));
        outtakeClaw.setPosition(Math.max(0, Math.min(1, outtakeClawPos)));
        outtakeTwist.setPosition(Math.max(0, Math.min(1, outtakeTwistPos)));

        // Telemetry output
        telemetry.addData("VSlide Target", vSlidePos);
        telemetry.addData("VSlide Current", vSlideCurrent);
        telemetry.addData("VSlide Power", vSlidePower);
        telemetry.addData("HSlide Target", hSlidePos);
        telemetry.addData("HSlide Current", hSlideCurrent);
        telemetry.addData("HSlide Power", hSlidePower);
        telemetry.addData("Outtake Rot Position", outtakeRot.getPosition());
        telemetry.addData("Outtake Dist Right Position", outtakeDistRight.getPosition());
        telemetry.addData("Outtake Dist Left Position", outtakeDistLeft.getPosition());
        telemetry.addData("Intake Rot Position", intakeRot.getPosition());
        telemetry.addData("Outtake Claw Position", outtakeClaw.getPosition());
        telemetry.addData("Outtake Twist Position", outtakeTwist.getPosition());

        telemetry.update();
    }
}
