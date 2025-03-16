package org.firstinspires.ftc.teamcode.BackupBot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class Drivetrain extends Subsystem {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;

    @Override
    public void init(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        setBrakeMode(true);
    }

    public void drive(double drive, double strafe, double turn) {
        double flPower = drive + strafe + turn;
        double frPower = drive - strafe - turn;
        double blPower = drive - strafe + turn;
        double brPower = drive + strafe - turn;

        double max = Math.max(1.0, Math.abs(flPower));
        max = Math.max(max, Math.abs(frPower));
        max = Math.max(max, Math.abs(blPower));
        max = Math.max(max, Math.abs(brPower));

        frontLeft.setPower(Range.clip(flPower / max, -1, 1));
        frontRight.setPower(Range.clip(frPower / max, -1, 1));
        backLeft.setPower(Range.clip(blPower / max, -1, 1));
        backRight.setPower(Range.clip(brPower / max, -1, 1));
    }

    public void setBrakeMode(boolean enabled) {
        DcMotor.ZeroPowerBehavior behavior = enabled ?
                DcMotor.ZeroPowerBehavior.BRAKE :
                DcMotor.ZeroPowerBehavior.FLOAT;
        frontLeft.setZeroPowerBehavior(behavior);
        frontRight.setZeroPowerBehavior(behavior);
        backLeft.setZeroPowerBehavior(behavior);
        backRight.setZeroPowerBehavior(behavior);
    }

    @Override
    public void update() {
    }
}
