package org.firstinspires.ftc.teamcode.base.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoArmSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final Servo arm;
    public ServoArmSubsystem(HardwareMap h, String arm) {
        this.arm = h.get(Servo.class, arm);
    }
    public void set(double pos) {
        this.arm.setPosition(pos);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //telemetry.addData("Arm ", arm.getCurrentPosition());
        //telemetry.update();
    }
}