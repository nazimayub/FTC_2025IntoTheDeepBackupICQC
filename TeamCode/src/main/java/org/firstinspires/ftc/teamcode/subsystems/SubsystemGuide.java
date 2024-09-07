package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SubsystemGuide extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    //Declare all motors, servos, and sensors before the constructor.
    private final DcMotorEx motor;
    public SubsystemGuide(HardwareMap h, String motor, Direction d) {
        //Initialize motors, servos, and sensors
        this.motor = h.get(DcMotorEx.class, motor);
        //Set motor direction
        this.motor.setDirection(d);
    }
    //Create subsystem methods
    public void set(double speed) {
        this.motor.setPower(speed);
    }
    public double get(){
        return this.motor.getVelocity();
    }

    //This method runs constantly on the subsystem, will usually only be used for PID
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //telemetry.addData("Right ", right.getCurrentPosition());
        //telemetry.addData("Left ", left.getCurrentPosition());
        //telemetry.update();
    }
}