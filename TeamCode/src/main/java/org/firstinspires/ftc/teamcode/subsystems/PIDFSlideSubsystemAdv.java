package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PIDFSlideSubsystemAdv extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final DcMotorEx right, left;
    private final double p, i, d, f;
    private final double p1, i1, d1, f1;
    private int pos = 0, pos1 = 0;
    private double target = 0, target1=0;
    private PIDController controller;
    private PIDController controller1;
    public PIDFSlideSubsystemAdv(HardwareMap h, String right, String left, Direction rightD, Direction leftD, double p, double i, double d, double f, double p1, double i1, double d1, double f1) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
        this.p1 = p1;
        this.i1 = i1;
        this.d1 = d1;
        this.f1 = f1;
        this.right = h.get(DcMotorEx.class, right);
        this.left = h.get(DcMotorEx.class, left);
        this.right.setDirection(rightD);
        this.left.setDirection(leftD);
        this.right.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.right.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        this.left.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.left.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        controller = new PIDController(p, i, d);
        controller1 = new PIDController(p1, i1, d1);

    }
    public void set(double target) {
        this.target = target;
    }
    public void change(double amount){this.target+=amount;}

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        controller.setPID(p, i, d);
        pos = right.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double ff = f*right.getCurrentPosition();
        double power = pid+ff;

        controller1.setPIDF(p1, i1, d1, f1);
        pos1 = left.getCurrentPosition();
        double pid1 = controller.calculate(pos1, target1);
        double ff1 = left.getCurrentPosition();
        double power1 = pid1+ff1;

        right.setPower(power);
        left.setPower(power1);

        //telemetry.addData("pos", pos);
        //telemetry.addData("target", target);
        //telemetry.addData("pos1", pos1);
        //telemetry.addData("target1", target1);
        //telemetry.update();
    }
}