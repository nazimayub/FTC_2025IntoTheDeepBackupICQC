package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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

    @Override
    public void init() {
        vSlideRight = hardwareMap.get(DcMotorEx.class, Const.rSlide);
        vSlideLeft = hardwareMap.get(DcMotorEx.class, Const.lSlide);
        hSlide = hardwareMap.get(DcMotorEx.class, Const.hSlide);

        outtakeRot = hardwareMap.get(Servo.class, Const.outtakeRot);
        outtakeDistRight = hardwareMap.get(Servo.class, Const.outtakeDistRight);
        outtakeDistLeft = hardwareMap.get(Servo.class, Const.outtakeDistLeft);
        intakeRot = hardwareMap.get(Servo.class, Const.intakeRot);
        outtakeClaw = hardwareMap.get(Servo.class, Const.outtakeClaw);
        outtakeTwist = hardwareMap.get(Servo.class, Const.outtakeTwist);

        vSlideRight.setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotorEx motor : new DcMotorEx[]{vSlideRight, vSlideLeft, hSlide}) {
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        }

        // Set PWM Range for Servos
        Servo[] servos = {outtakeRot, outtakeDistRight, outtakeDistLeft, intakeRot, outtakeClaw, outtakeTwist};
        for (Servo servo : servos) {
            ((ServoImplEx) servo).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
        }
    }

    @Override
    public void loop() {
        vSlideRight.setTargetPosition(vSlidePos);
        vSlideLeft.setTargetPosition(vSlidePos);
        hSlide.setTargetPosition(hSlidePos);

        for (DcMotorEx motor : new DcMotorEx[]{vSlideRight, vSlideLeft, hSlide}) {
            motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            motor.setPower(1.0);
        }

        outtakeRot.setPosition(Math.max(0, Math.min(1, outtakeRotPos)));
        outtakeDistRight.setPosition(Math.max(0, Math.min(1, outtakeDistRightPos)));
        outtakeDistLeft.setPosition(Math.max(0, Math.min(1, outtakeDistLeftPos)));
        intakeRot.setPosition(Math.max(0, Math.min(1, intakeRotPos)));
        outtakeClaw.setPosition(Math.max(0, Math.min(1, outtakeClawPos)));
        outtakeTwist.setPosition(Math.max(0, Math.min(1, outtakeTwistPos)));

        telemetry.addData("VSlide Position", vSlidePos);
        telemetry.addData("HSlide Position", hSlidePos);
        telemetry.addData("Outtake Rot Position", outtakeRot.getPosition());
        telemetry.addData("Outtake Dist Right Position", outtakeDistRight.getPosition());
        telemetry.addData("Outtake Dist Left Position", outtakeDistLeft.getPosition());
        telemetry.addData("Intake Rot Position", intakeRot.getPosition());
        telemetry.addData("Outtake Claw Position", outtakeClaw.getPosition());
        telemetry.addData("Outtake Twist Position", outtakeTwist.getPosition());

        telemetry.update();
    }
}
