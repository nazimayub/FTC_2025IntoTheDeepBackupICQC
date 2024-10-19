package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drive;

public class RedClose extends CommandOpMode {
    Drive drive = new Drive(hardwareMap);
    @Override
    public void initialize() {
        schedule(new DriveCommand(drive,10,10,0,0.5));
    }
}
