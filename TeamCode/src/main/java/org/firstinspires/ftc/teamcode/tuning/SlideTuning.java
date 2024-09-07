package org.firstinspires.ftc.teamcode.tuning;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

@Config
@TeleOp
public class SlideTuning extends OpMode {
    private PIDController controller, controller1;

    public static double p = 0, i=0, d=0, p1=0, i1=0, d1=0;
    public static double f = 0, f1=0;

    public static double target = 0, target1=0;

    private final double ticks_in_degree = 3225.6/180;
    private int pos, pos1;

    private DcMotorEx right, left;
    private HardwareMap h = hardwareMap;

    @Override
    public void init() {
        this.right = h.get(DcMotorEx.class, Constants.rSlide);
        this.left = h.get(DcMotorEx.class, Constants.lSlide);
        this.right.setDirection(DcMotorSimple.Direction.REVERSE);
        this.right.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.right.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        this.left.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.left.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        controller = new PIDController(p, i, d);
        controller1 = new PIDController(p1, i1, d1);
    }
    @Override
    public void loop(){
        controller.setPIDF(p, i, d, f);
        pos = right.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid+f;

        controller1.setPIDF(p1, i1, d1, f1);
        pos1 = left.getCurrentPosition();
        double pid1 = controller.calculate(pos1, target1);
        double power1 = pid1+f1;

        right.setPower(power);
        left.setPower(power1);

        telemetry.addData("pos", pos);
        telemetry.addData("target", target);
        telemetry.addData("pos1", pos1);
        telemetry.addData("target1", target1);
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