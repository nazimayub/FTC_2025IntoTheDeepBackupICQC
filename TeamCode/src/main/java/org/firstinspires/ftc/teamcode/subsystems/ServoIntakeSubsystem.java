package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ServoIntakeSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final CRServo intake;
    public ServoIntakeSubsystem(HardwareMap h, String name) {
        this.intake = h.get(CRServo.class, name);
    }
    public void set(double speed) {
        this.intake.setPower(speed);
    }

    @Override
    public void periodic() {
    }
}