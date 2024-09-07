package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceSensorSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final DistanceSensor sensor;
    public DistanceSensorSubsystem(HardwareMap h, String name) {
        this.sensor = h.get(DistanceSensor.class, name);
    }
    public double get() {
        return sensor.getDistance(DistanceUnit.MM);
    }
    public double get(DistanceUnit unit) {
        return sensor.getDistance(unit);
    }
    public boolean less(double pos){
        return get()<pos;
    }
    public boolean less(DistanceUnit unit, double pos){
        return get(unit)<pos;
    }
    public boolean more(double pos){
        return get()>pos;
    }
    public boolean more(DistanceUnit unit, double pos){
        return get(unit)>pos;
    }
    public boolean equals(double pos){
        return get()==pos;
    }
    public boolean equals(DistanceUnit unit, double pos){
        return get(unit)==pos;
    }
    public boolean equals(double pos, double tol){
        return Math.abs(get()-pos)<tol;
    }
    public boolean equals(DistanceUnit unit, double pos, double tol){
        return  Math.abs(get(unit)-pos)<tol;
    }

    @Override
    public void periodic() {
        //telemetry.addData("(MM) Distance " + sensor.getDeviceName(), sensor.getDistance(DistanceUnit.MM));
        //telemetry.update();
        // This method will be called once per scheduler run
    }
}