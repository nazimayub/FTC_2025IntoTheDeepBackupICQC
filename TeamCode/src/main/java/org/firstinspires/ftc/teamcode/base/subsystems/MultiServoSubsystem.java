package org.firstinspires.ftc.teamcode.base.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class MultiServoSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final Servo[] servos;
    public MultiServoSubsystem(HardwareMap h, int num, String... names ) {
        servos = new Servo[num];
        int q = 0;
        for(String name : names){
            servos[q] = h.get(Servo.class, name);
            q++;
        }
    }
    public void set(int n, double pos) {
        this.servos[n].setPosition(pos);
    }
    public double get(int n){
        return this.servos[n].getPosition();
    }

    @Override
    public void periodic() {
        //telemetry.addData("Hand ", hand.getPosition());
        //telemetry.update();
        // This method will be called once per scheduler run
    }
}