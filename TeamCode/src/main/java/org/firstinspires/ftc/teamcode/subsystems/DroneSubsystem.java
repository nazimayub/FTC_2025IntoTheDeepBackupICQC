package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DroneSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final Servo drone;
    public DroneSubsystem(HardwareMap h, String name) {
        this.drone = h.get(Servo.class, name);
    }
    public void set(double pos) {
        this.drone.setPosition(pos);
    }
    public double get(){
        return this.drone.getPosition();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //telemetry.addData("Drone ", drone.getPosition());
        //telemetry.update();
    }
}