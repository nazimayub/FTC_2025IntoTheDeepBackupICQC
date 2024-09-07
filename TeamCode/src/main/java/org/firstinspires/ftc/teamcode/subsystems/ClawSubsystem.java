package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final Servo left;
    private final Servo right;
    public ClawSubsystem(HardwareMap h, String left, String right) {
        this.right = h.get(Servo.class, right);
        this.left = h.get(Servo.class, left);
    }
    public void set(double rPos, double lPos){
        this.right.setPosition(rPos);
        this.left.setPosition(lPos);
    }
    @Override
    public void periodic() {
        //telemetry.addData("Right ", right.getPosition());
        //telemetry.addData("Left ", left.getPosition());
        //telemetry.update();
        // This method will be called once per scheduler run
    }
}