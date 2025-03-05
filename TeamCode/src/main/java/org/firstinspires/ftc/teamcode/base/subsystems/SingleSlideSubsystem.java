package org.firstinspires.ftc.teamcode.base.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SingleSlideSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final DcMotorEx right;
    public SingleSlideSubsystem(HardwareMap h, String right, Direction rightD) {
        this.right = h.get(DcMotorEx.class, right);
        this.right.setDirection(rightD);
        this.right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void set(double speed) {
        this.right.setPower(speed);
    }
    public double get(){
        return this.right.getVelocity();
    }



    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //telemetry.addData("Right ", right.getCurrentPosition());
        //telemetry.addData("Left ", left.getCurrentPosition());
        //telemetry.update();
    }
}