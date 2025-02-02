package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Const;

@Config
@TeleOp
public class ServoTuningDash extends OpMode {
    public static Servo outtakeRot, outtakeDistRight, outtakeDistLeft, intakeRot, outtakeClaw, outtakeTwist, gearShifter;

    public static double outtakeRotPos = 0.5, outtakeDistRightPos = 0.5, outtakeDistLeftPos = 0.5, intakeRotPos = 0.5, outtakeClawPos = 0.5, outtakeTwistPos = 0.5, s7 = 0.5;

    @Override
    public void init() {
        outtakeRot = hardwareMap.get(Servo.class, Const.outtakeRot);
        outtakeDistRight = hardwareMap.get(Servo.class, Const.outtakeDistRight);
        outtakeDistLeft = hardwareMap.get(Servo.class, Const.outtakeDistLeft);
        intakeRot = hardwareMap.get(Servo.class, Const.intakeRot);
        outtakeClaw = hardwareMap.get(Servo.class, Const.outtakeClaw);
        outtakeTwist = hardwareMap.get(Servo.class, Const.outtakeTwist);
        //gearShifter = hardwareMap.get(Servo.class, Const.gearShifter);

        outtakeRotPos = 0.5;
        outtakeDistRightPos = 0.5;
        outtakeDistLeftPos = 0.5;
        intakeRotPos = 0.5;
        outtakeClawPos = 0.5;
        outtakeTwistPos = 0.5;
        //s7 = 0.5;
    }

    @Override
    public void loop() {
        outtakeRot.setPosition(outtakeRotPos);
        outtakeDistRight.setPosition(outtakeDistRightPos);
        outtakeDistLeft.setPosition(outtakeDistLeftPos);
        intakeRot.setPosition(intakeRotPos);
        outtakeClaw.setPosition(outtakeClawPos);
        outtakeTwist.setPosition(outtakeTwistPos);
        //gearShifter.setPosition(s7);

        telemetry.addData("Outtake Rot Position", outtakeRotPos);
        telemetry.addData("Outtake Dist Right Position", outtakeDistRightPos);
        telemetry.addData("Outtake Dist Left Position", outtakeDistLeftPos);
        telemetry.addData("Intake Rot Position", intakeRotPos);
        telemetry.addData("Outtake Claw Position", outtakeClaw);
        telemetry.addData("Outtake Twist Position", outtakeTwist);
        //telemetry.addData("Gear Shifter Position", s7);

        telemetry.update();
    }
}
