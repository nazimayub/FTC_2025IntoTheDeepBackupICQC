package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

public class DriveCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    ElapsedTime timer = new ElapsedTime();

    private final Drive drive;
    double x,y,theta,tolerance,power;
    boolean move = false;
    long timeout = -1;
    int tickNum = 1;


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


    // this runs over the lifetime of the command so it will loop as long as the command is alive/scheduled
    @Override
    public void execute(){
        if(move){
            drive.driveToPosition(x,y,theta,power,tolerance);
        }else{ // stop and wait for a timeout s in the constructor
            drive.setCompletionStatus(false); // tell ftclib we are not done
            drive.stopRobot(); // stops the robot
            if(tickNum==1){ // if its the first time running this loop we are gonna reset otherwise we wont
                timer.reset();
            }
            if(timer.seconds()>timeout){
                drive.setCompletionStatus(true);
                timer.reset();
            }
        }
        tickNum=tickNum+1; // increases the tick number so we wont reset the timer every time...
    }
    @Override
    public boolean isFinished(){
        if(drive.isCompleted()){
            drive.reset(); // resets pid and other values...
            drive.stopRobot();
        }
        return drive.isCompleted();
    }

}
