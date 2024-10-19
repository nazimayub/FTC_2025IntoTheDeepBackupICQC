package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

public class DriveCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    double x,y,theta,tolerance,power;
    boolean done = false;

    public DriveCommand(Drive drive,double x,double y,double theta,double tolerance) {
        this.drive=drive;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drive);
    }
    @Override
    public void execute(){
        done = false;
        drive.driveToPosition(x,y,theta,power,tolerance); // here we just move...
        done = true;
    }
    @Override
    public boolean isFinished(){
        return done;
    }


}
