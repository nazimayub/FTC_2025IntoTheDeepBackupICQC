package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class WaitSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final ElapsedTime t;
    public WaitSubsystem() {
        t = new ElapsedTime();
    }

    public void start(){
        t.reset();
    }
    public double elapesd(){
        return t.milliseconds();
    }
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //telemetry.addData("transfer ", transfer.getPower());
        //telemetry.update();
    }
}