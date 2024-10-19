package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

public class DriveCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;

    public DriveCommand(Drive drive) {
        this.drive=drive;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drive);
    }
    @Override
    public void execute(){

    }


}
