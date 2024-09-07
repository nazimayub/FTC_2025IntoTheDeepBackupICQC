package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PIDFArmSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final DcMotorEx arm;
    private final double p, i, d, f;
    private int pos = 0;
    private double target = 0;
    private final double ticksInDegrees;
    private PIDController controller;
    public PIDFArmSubsystem(HardwareMap h, String arm, double p, double i, double d, double f, double ticksInDegrees) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
        this.arm = h.get(DcMotorImplEx.class, arm);
        this.arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.arm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        controller = new PIDController(p, i, d);
        this.ticksInDegrees = ticksInDegrees;
    }
    public void set(double target) {
        this.target = target;
    }
    public void change(double amount){this.target+=amount;}

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        controller.setPID(p, i, d);
        pos = arm.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double ff = Math.cos(Math.toRadians(target/ticksInDegrees)) * f;
        double power = pid + ff;
        arm.setPower(power);
        //telemetry.addData("pos", pos);
        //telemetry.addData("target", target);
        //telemetry.update();
    }
}