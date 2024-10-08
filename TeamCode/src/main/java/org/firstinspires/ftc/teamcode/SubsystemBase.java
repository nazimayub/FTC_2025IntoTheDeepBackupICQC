package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SubsystemBase {

    public DcMotorEx initDcMotor(HardwareMap hardwareMap,
                                 String name) {
        return initDcMotor(hardwareMap, name, DcMotorSimple.Direction.FORWARD);
    }

    public DcMotorEx initDcMotor(HardwareMap hardwareMap,
                                 String name,
                                 DcMotor.Direction dir) {
        return initDcMotor(hardwareMap, name, dir, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public DcMotorEx initDcMotor(HardwareMap hardwareMap,
                                 String name,
                                 DcMotor.Direction dir,
                                 DcMotor.RunMode mode) {
        DcMotorEx m = hardwareMap.get(DcMotorEx.class, name);
        m.setDirection(dir);
        m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m.setMode(mode);
        return m;
    }
}
