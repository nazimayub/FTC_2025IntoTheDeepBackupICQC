package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

public class DriveCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    double x,y,theta,tolerance,power;
    boolean move = false;
    long timeout = -1;


    public DriveCommand(Drive drive,double x,double y,double theta,double power,double tolerance) {
        move = true; // move the robot...
        this.drive=drive;
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.tolerance = tolerance;
        this.power = power;


        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drive);
    }
    public DriveCommand(Drive drive, long s){
        move = false; // dont move the robot..
        this.drive = drive;
        timeout = s;
        addRequirements(drive);
    }



    @Override
    public void execute(){
        drive.driveToPosition(x,y,theta,power,tolerance);
    }
    @Override
    public boolean isFinished(){
        if(drive.isCompleted()){
            drive.reset();
        }
        return drive.isCompleted();
    }

}
