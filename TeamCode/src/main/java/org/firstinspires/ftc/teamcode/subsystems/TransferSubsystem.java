package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TransferSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final CRServo transfer;
    public TransferSubsystem(HardwareMap h, String name) {
        this.transfer = h.get(CRServo.class, name);
    }
    public void set(double speed) {
        this.transfer.setPower(speed);
    }
    public double get(){
        return this.transfer.getPower();
    }
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //telemetry.addData("transfer ", transfer.getPower());
        //telemetry.update();
    }
}