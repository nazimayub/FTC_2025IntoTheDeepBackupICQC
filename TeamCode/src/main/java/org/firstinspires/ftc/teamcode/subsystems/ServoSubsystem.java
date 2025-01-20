package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImpl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class ServoSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final Servo hand;
    public ServoSubsystem(HardwareMap h, String name) {
        this.hand = h.get(Servo.class, name);
        ((ServoImplEx)(hand)).setPwmRange(new PwmControl.PwmRange(500, 2500, 5000));
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