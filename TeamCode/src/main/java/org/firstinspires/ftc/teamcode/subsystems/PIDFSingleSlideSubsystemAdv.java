package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PIDFSingleSlideSubsystemAdv extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final DcMotorEx slide;
    private final double p, i, d, f;
    private int pos = 0;
    private double target = 0;
    private PIDController controller;
    public PIDFSingleSlideSubsystemAdv(HardwareMap h, String slide, double p, double i, double d, double f) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
        this.slide = h.get(DcMotorImplEx.class, slide);
        this.slide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.slide.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        controller = new PIDController(p, i, d);
    }
    public void set(double target) {
        this.target = target;
    }
    public void change(double amount){this.target+=amount;}

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        controller.setPID(p, i, d);
        pos = slide.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double ff = f * slide.getCurrentPosition();
        double power = pid + ff;
        slide.setPower(power);
        //telemetry.addData("pos", pos);
        //telemetry.addData("target", target);
        //telemetry.update();
    }
}