package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem extends SubsystemBase {
    DcMotorEx intake;
    ServoEx finger;

    public IntakeSubsystem(HardwareMap hMap) {}

    public double getVelocity() {
        return intake.getVelocity();
    }
}
