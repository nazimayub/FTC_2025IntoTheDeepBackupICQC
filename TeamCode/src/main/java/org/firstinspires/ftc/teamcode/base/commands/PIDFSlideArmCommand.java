package org.firstinspires.ftc.teamcode.base.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.base.subsystems.*;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class PIDFSlideArmCommand extends CommandBase {

    private PIDFSlideSubsystem PIDFSlide;
    private PIDFSlideSubsystemAdv PIDFSlideAdv;
    private PIDFArmSubsystem arm;
    private PIDFSingleSlideSubsystemAdv PIDFSingleSlideAdv;
    private PIDFSingleSlideSubsystem PIDFSingleSlide;
    private final double change;

    public PIDFSlideArmCommand(PIDFSingleSlideSubsystem PIDFSingleSlide, double change) {
        this.PIDFSingleSlide=PIDFSingleSlide;
        this.change = change;
        addRequirements(PIDFSingleSlide);
    }
    public PIDFSlideArmCommand(PIDFSingleSlideSubsystemAdv PIDFSingleSlideAdv, double change) {
        this.PIDFSingleSlideAdv=PIDFSingleSlideAdv;
        this.change = change;
        addRequirements(PIDFSingleSlideAdv);
    }
    public PIDFSlideArmCommand(PIDFSlideSubsystem PIDFSlide, double change) {
        this.PIDFSlide=PIDFSlide;
        this.change = change;
        this.PIDFSlide.usePID(true);
        addRequirements(PIDFSlide);
    }
    public PIDFSlideArmCommand(PIDFSlideSubsystemAdv PIDFSlideAdv, double change) {
        this.PIDFSlideAdv=PIDFSlideAdv;
        this.change = change;
        addRequirements(PIDFSlideAdv);
    }
    public PIDFSlideArmCommand(PIDFArmSubsystem arm, double change) {
        this.arm=arm;
        this.change = change;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        if(PIDFSlide!=null){
            PIDFSlide.change(change);
        }
        else if(PIDFSlideAdv != null){
            PIDFSlideAdv.change(change);
        }
        else if(PIDFSingleSlideAdv != null){
            PIDFSingleSlideAdv.change(change);
        }
        else if(PIDFSingleSlide != null){
            PIDFSingleSlide.change(change);
        }
        else{
            arm.change(change);
        }
    }

}
