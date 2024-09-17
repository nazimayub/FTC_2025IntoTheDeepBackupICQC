package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final DcMotorEx arm;
    public ArmSubsystem(HardwareMap h, String arm) {
        this.arm = h.get(DcMotorEx.class, arm);
    }
    public void set(double speed) {
        this.arm.setPower(speed);
    }

    public void setBrakeMode(boolean brake) {
        if (brake){
            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //telemetry.addData("Arm ", arm.getCurrentPosition());
        //telemetry.update();
    }
}