package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeAutoSubsystem extends SubsystemBase {

    private final DcMotorEx intake;
    private final int ticksPerRevolution = 28;

    public IntakeAutoSubsystem(HardwareMap h, String name) {
        this.intake = h.get(DcMotorEx.class, name);
        this.intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        this.intake.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void set(double speed) {
        this.intake.setPower(speed);
    }

    public void resetEncoder() {
        intake.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public double getCurrentRotations() {
        return (double) intake.getCurrentPosition() / ticksPerRevolution;
    }

    public void stop() {
        this.intake.setPower(0);
    }
}
