package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive extends SubsystemBase {
    DcMotor fr,fl,br,bl;
    public Drive(HardwareMap hardwareMap){
        fr = hardwareMap.get(DcMotor.class,"rf");
        fl = hardwareMap.get(DcMotor.class,"lf");
        br = hardwareMap.get(DcMotor.class,"rb");
        bl = hardwareMap.get(DcMotor.class,"lb");

    }
}
