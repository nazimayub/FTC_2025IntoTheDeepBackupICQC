package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

public class DriveCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drive drive;
    double x,y,theta,tolerance,power;
    boolean done = false;
    boolean move = false;
    long timeout = 0;


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
        addRequirements(drive);
    }



    @Override
    public void execute(){
        done = false;
        if(move){
            drive.driveToPosition(x,y,theta,power,tolerance); // here we just move...
        }else{
            drive.stopRobotTime(timeout);
        }
        done = true;
    }
    @Override
    public boolean isFinished(){
        return done;
    }


}
