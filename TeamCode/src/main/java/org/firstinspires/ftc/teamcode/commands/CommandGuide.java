package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.*;


import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class CommandGuide extends CommandBase {
    //Declare Subsystems
    private final SubsystemGuide guide;
    //Declare any other variables
    private final double speed;

    public CommandGuide(SubsystemGuide guide, double speed) {
        //Use dependancy injection to initialize previously declared subsystems and variables
        this.guide = guide;
        this.speed = speed;
        //Add requirement for all subsystems used in command
        addRequirements(guide);
    }
    //Runs on the initialization of the command
    @Override
    public void initialize(){

    }
    //Runs during the lifetime of the command
    @Override
    public void execute() {
        guide.set(speed);
    }
    //Conditions for when a command will expire
    @Override
    public boolean isFinished(){
        return false;
    }

}
