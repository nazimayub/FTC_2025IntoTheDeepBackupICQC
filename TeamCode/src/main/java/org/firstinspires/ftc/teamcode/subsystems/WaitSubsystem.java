package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class WaitSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private long last;
    public WaitSubsystem() {
        last = System.currentTimeMillis();
    }

    public void start(){
        last = System.currentTimeMillis();
    }
    public long elapesd(){
        return System.currentTimeMillis()-last;
    }
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //telemetry.addData("transfer ", transfer.getPower());
        //telemetry.update();
    }
}