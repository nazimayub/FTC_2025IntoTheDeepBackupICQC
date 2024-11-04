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
public class SlideResetCommand extends CommandBase {

    private PIDFSlideSubsystem slide;
    private SingleSlideSubsystem sSlide;
    private LimitSwitchSubsystem l;

    public SlideResetCommand(PIDFSlideSubsystem slide, LimitSwitchSubsystem l) {
        this.slide=slide;
        this.l = l;
        addRequirements(slide);
    }

    public SlideResetCommand(SingleSlideSubsystem sSlide, LimitSwitchSubsystem l) {
        this.sSlide = sSlide;
        this.l = l;
        addRequirements(sSlide);
    }
    @Override
    public void execute() {
        if (slide != null) {
            slide.set(-1, -1);
            slide.usePID(false);
        }
        else if (sSlide != null){
            sSlide.set(-1);
        }

    }
    @Override
    public boolean isFinished(){
        return l.get();
    }
    @Override
    public void end(boolean interrupted){
        if (slide != null) {
            slide.set(0, 0);
            slide.reset();
            slide.usePID(true);
        }
        else if (sSlide != null){
            sSlide.set(0);
        }
    }

}
