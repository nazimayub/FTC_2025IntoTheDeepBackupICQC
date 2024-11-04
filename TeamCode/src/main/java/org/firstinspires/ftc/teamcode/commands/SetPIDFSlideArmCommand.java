package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.*;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class SetPIDFSlideArmCommand extends CommandBase {

    private PIDFSlideSubsystem PIDFSlide;
    private PIDFSlideSubsystemAdv PIDFSlideAdv;
    private PIDFArmSubsystem arm;
    private PIDFSingleSlideSubsystemAdv PIDFSingleSlideAdv;
    private PIDFSingleSlideSubsystem PIDFSingleSlide;
    private final double change;

    public SetPIDFSlideArmCommand(PIDFSingleSlideSubsystem PIDFSingleSlide, double change) {
        this.PIDFSingleSlide=PIDFSingleSlide;
        this.change = change;
        addRequirements(PIDFSingleSlide);
    }
    public SetPIDFSlideArmCommand(PIDFSingleSlideSubsystemAdv PIDFSingleSlideAdv, double change) {
        this.PIDFSingleSlideAdv=PIDFSingleSlideAdv;
        this.change = change;
        addRequirements(PIDFSingleSlideAdv);
    }
    public SetPIDFSlideArmCommand(PIDFSlideSubsystem PIDFSlide, double change) {
        this.PIDFSlide=PIDFSlide;
        this.change = change;
        this.PIDFSlide.usePID(true);
        addRequirements(PIDFSlide);
    }
    public SetPIDFSlideArmCommand(PIDFSlideSubsystemAdv PIDFSlideAdv, double change) {
        this.PIDFSlideAdv=PIDFSlideAdv;
        this.change = change;
        addRequirements(PIDFSlideAdv);
    }
    public SetPIDFSlideArmCommand(PIDFArmSubsystem arm, double change) {
        this.arm=arm;
        this.change = change;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        if(PIDFSlide!=null){
            PIDFSlide.set(PIDFSlide.getController().calculate(PIDFSlide.getTick(), change) + PIDFSlide.getF(), PIDFSlide.getController().calculate(PIDFSlide.getTick(), change) + PIDFSlide.getF());
        }
        else if(PIDFSlideAdv != null){
            PIDFSlideAdv.set(change);
        }
        else if(PIDFSingleSlideAdv != null){
            PIDFSingleSlideAdv.set(change);
        }
        else if(PIDFSingleSlide != null){
            PIDFSingleSlide.set(-1*PIDFSingleSlide.getController().calculate(PIDFSingleSlide.getTick(), change) + PIDFSingleSlide.getF(), 0);
        }
        else{
            arm.set(change);
        }
    }
    @Override
    public boolean isFinished(){
        if(PIDFSlide!=null){
            return PIDFSlide.getTick()<change+5&&PIDFSlide.getTick()>change-5;
        }
        else if(PIDFSlideAdv != null){
            return PIDFSlideAdv.getTick()<change+5&&PIDFSlideAdv.getTick()>change-5;
        }
        else if(PIDFSingleSlideAdv != null){
            return PIDFSingleSlideAdv.getTick()<change+5&&PIDFSingleSlideAdv.getTick()>change-5;
        }
        else if(PIDFSingleSlide != null){
            return PIDFSingleSlide.getTick()<change+5&&PIDFSingleSlide.getTick()>change-5;
        }
        else{
            return arm.getTick()<change+5&&arm.getTick()>change-5;
        }
    }
    @Override
    public void end(boolean inturrupted){
        if(PIDFSingleSlide != null){
            PIDFSingleSlide.set(0, 0);
            PIDFSingleSlide.change(PIDFSingleSlide.getTick());
        }

    }

}
