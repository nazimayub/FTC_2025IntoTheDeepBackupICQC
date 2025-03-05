package org.firstinspires.ftc.teamcode.base.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.base.subsystems.PIDFSingleSlideSubsystem;
import org.firstinspires.ftc.teamcode.base.subsystems.SingleSlideSubsystem;
import org.firstinspires.ftc.teamcode.base.subsystems.SlideSubsystem;


import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class SlideArmCommand extends CommandBase {

    private SlideSubsystem slide;
    private ArmSubsystem arm;
    private SingleSlideSubsystem sSlide;
    private PIDFSingleSlideSubsystem pSlide;

    private final GamepadEx gamepad;

    public SlideArmCommand(SlideSubsystem slide, GamepadEx gamepad) {
        this.slide=slide;
        this.gamepad = gamepad;
        addRequirements(slide);
    }
    public SlideArmCommand(ArmSubsystem arm, GamepadEx gamepad) {
        this.arm=arm;
        this.gamepad = gamepad;
        addRequirements(arm);
    }

    public SlideArmCommand(SingleSlideSubsystem sSlide, GamepadEx gamepad) {
        this.sSlide = sSlide;
        this.gamepad = gamepad;
        addRequirements(sSlide);
    }
    public SlideArmCommand(PIDFSingleSlideSubsystem pSlide, GamepadEx gamepad) {
        this.pSlide = pSlide;
        this.gamepad = gamepad;
        addRequirements(pSlide);
    }
    @Override
    public void execute() {
        if (slide != null) {
            slide.set(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
        }
        else if (sSlide != null){
            sSlide.set(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
        }
        else if (pSlide != null){
            if(pSlide.getPID()){
                pSlide.set(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER), 0);

            }
            //pSlide.set(-1*pSlide.getController().calculate(pSlide.getTick(), pSlide.getTick()-(int)(1000* gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER))+(int)(1000* gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER))) + pSlide.getF(), 0);
        }
        else {
            arm.set(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
        }

    }
    @Override
    public void end(boolean inturrupted){
        if(pSlide != null){
            pSlide.set(0, 0);
            pSlide.change(pSlide.getTick());
        }


    }

}
