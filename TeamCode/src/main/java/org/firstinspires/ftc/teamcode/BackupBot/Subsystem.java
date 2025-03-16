package org.firstinspires.ftc.teamcode.BackupBot;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Subsystem {
    public abstract void init(HardwareMap hardwareMap);
    public abstract void update();
}
