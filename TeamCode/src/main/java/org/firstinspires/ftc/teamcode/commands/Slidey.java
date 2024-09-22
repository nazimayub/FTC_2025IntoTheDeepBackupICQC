package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Slides;


import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class Slidey extends CommandBase {
    //Declare Subsystems
    private final Slides slide;
    //Declare any other variables
    private final double power;

    public Slidey(Slides slide, double power) {
        //Use dependancy injection to initialize previously declared subsystems and variables
        this.slide = slide;
        this.power = power;
        //Add requirement for all subsystems used in command
        addRequirements(slide);
    }
    //Runs on the initialization of the command
    @Override
    public void initialize(){

    }
    //Runs during the lifetime of the command
    @Override
    public void execute() {
        slide.set(power);
    }
    //Conditions for when a command will expire
    @Override
    public boolean isFinished(){
        return false;
    }

}
