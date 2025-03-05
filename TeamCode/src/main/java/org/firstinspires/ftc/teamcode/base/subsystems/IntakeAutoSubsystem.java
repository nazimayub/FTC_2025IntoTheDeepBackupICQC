package org.firstinspires.ftc.teamcode.base.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class IntakeAutoSubsystem extends SubsystemBase {

    private final DcMotorEx intake;
    private final ElapsedTime elapsedTime;

    public IntakeAutoSubsystem(HardwareMap h, String name, ElapsedTime elapsedTime) {
        this.intake = h.get(DcMotorEx.class, name);
        this.intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.elapsedTime = elapsedTime;
    }

    public void set(double speed) {
        this.intake.setPower(speed);
    }

    public double getElapsedTime() {
        return this.elapsedTime.seconds();
    }

    public void resetElapsedTime() {
        this.elapsedTime.reset();
    }
}