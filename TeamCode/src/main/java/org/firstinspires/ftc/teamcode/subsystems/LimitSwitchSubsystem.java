package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class LimitSwitchSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final TouchSensor s;
    public LimitSwitchSubsystem(HardwareMap h, String n) {
        this.s = h.get(TouchSensor.class, n);
    }
    public boolean get() {
        return this.s.isPressed();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //telemetry.addData("Arm ", arm.getCurrentPosition());
        //telemetry.update();
    }
}