package org.firstinspires.ftc.teamcode.BackupBot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.BackupBot.Drivetrain;

public class Robot {
    public Drivetrain drivetrain = new Drivetrain();

    public void init(HardwareMap hardwareMap) {
        drivetrain.init(hardwareMap);
    }

    public void update() {
        drivetrain.update();
    }
}
