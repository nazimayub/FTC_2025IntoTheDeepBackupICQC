package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp
public class SingleSlideTuning extends OpMode {
    private PIDController controller, controller1;
    private Servo intake;

    public static double p = 0.1, i=0, d=0.01;
    public static double f = 0.01;

    public static double target = 0;
    private int pos, pos1;

    private DcMotorEx motor3, left;
    private HardwareMap h = hardwareMap;

    @Override
    public void init() {
        intake = hardwareMap.get(Servo.class, "servo4");
        intake.setPosition(.5);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        h = this.hardwareMap;
        this.motor3 = h.get(DcMotorEx.class, "horizontalSlide");
        //this.left = h.get(DcMotorEx.class, "motor2");
        this.motor3.setDirection(DcMotorSimple.Direction.FORWARD);
        //this.left.setDirection(DcMotorSimple.Direction.FORWARD);
        this.motor3.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.motor3.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        //this.left.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        //this.left.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        controller = new PIDController(p, i, d);
        //controller1 = new PIDController(p, i, d);
    }
    @Override
    public void loop(){
        controller.setPID(p, i, d);
        pos = motor3.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid+f;

        //controller1.setPID(p, i, d);
        //pos1 = left.getCurrentPosition();
        //double pid1 = controller.calculate(pos, target);
        //double power1 = pid1+f;
        intake.setPosition(.2);
        motor3.setPower(power);
        //left.setPower(power1);

        telemetry.addData("pos", pos);
        telemetry.addData("target", target);
        //telemetry.addData("pos1", pos1);
        telemetry.update();
        if(-gamepad1.left_stick_y>0){
            target+=.2;
        }
        else if(-gamepad1.left_stick_y<0){
            target-=.2;
        }
    }


    /*ArmPIDF(int target){
        controller.setPID(p, i, d);
        int armPos = arm_motor.getCurrentPosition();
        double pid = controller.calculate(armPos, target);

        double power = pid;

        arm_motor.setPower(power);

        telemetry.addData("pos", armPos);
        telemetry.addData("target", target);
        telemetry.update();
        if(-gamepad1.left_stick_y>0){
            target+=.2;
        }
        else if(-gamepad1.left_stick_y<0){
           target-=.2;
        }
    }

     */



}