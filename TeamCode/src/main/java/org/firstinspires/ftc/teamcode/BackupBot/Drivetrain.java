package org.firstinspires.ftc.teamcode.BackupBot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class Drivetrain extends Subsystem {
    private DcMotorEx fL, fR, bL, bR;

    @Override
    public void init(HardwareMap hardwareMap) {
        fL = hardwareMap.get(DcMotorEx.class, "fL");
        fR = hardwareMap.get(DcMotorEx.class, "fR");
        bL = hardwareMap.get(DcMotorEx.class, "bL");
        bR = hardwareMap.get(DcMotorEx.class, "bR");

        fR.setDirection(DcMotorSimple.Direction.REVERSE);
        bR.setDirection(DcMotorSimple.Direction.REVERSE);

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

        fL.setPower(Range.clip(flPower / max, -.5, .5));
        fR.setPower(Range.clip(frPower / max, -.5, .5));
        bL.setPower(Range.clip(blPower / max, -.5, .5));
        bR.setPower(Range.clip(brPower / max, -.5, .5));
    }

    public void setBrakeMode(boolean enabled) {
        DcMotor.ZeroPowerBehavior behavior = enabled ?
                DcMotor.ZeroPowerBehavior.BRAKE :
                DcMotor.ZeroPowerBehavior.FLOAT;
        fL.setZeroPowerBehavior(behavior);
        fR.setZeroPowerBehavior(behavior);
        bL.setZeroPowerBehavior(behavior);
        bR.setZeroPowerBehavior(behavior);
    }

    @Override
    public void update() {
    }
}
