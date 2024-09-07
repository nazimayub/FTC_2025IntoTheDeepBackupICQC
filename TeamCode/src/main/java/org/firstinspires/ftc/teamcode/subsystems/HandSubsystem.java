package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HandSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final Servo hand;
    public HandSubsystem(HardwareMap h, String name) {
        this.hand = h.get(Servo.class, name);
    }
    public void set(double pos) {
        this.hand.setPosition(pos);
    }
    public double get(){
        return this.hand.getPosition();
    }

    @Override
    public void periodic() {
        //telemetry.addData("Hand ", hand.getPosition());
        //telemetry.update();
        // This method will be called once per scheduler run
    }
}