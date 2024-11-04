package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SingleSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SlideSubsystem;

public class SetSlideArmCommand extends CommandBase {

    private SlideSubsystem slide;
    private ArmSubsystem arm;
    private SingleSlideSubsystem sSlide;

    private final double speed;

    public SetSlideArmCommand(SlideSubsystem slide, double speed) {
        this.slide=slide;
        this.speed = speed;
        addRequirements(slide);
    }
    public SetSlideArmCommand(ArmSubsystem arm, double speed) {
        this.arm=arm;
        this.speed = speed;
        addRequirements(arm);
    }

    public SetSlideArmCommand(SingleSlideSubsystem sSlide, double speed) {
        this.sSlide = sSlide;
        this.speed = speed;
        addRequirements(sSlide);
    }
    @Override
    public void execute() {
        if (slide != null) {
            slide.set(speed);
        }
        else if (sSlide != null){
            sSlide.set(speed);
        }
        else {
            arm.set(speed);
        }

    }
    @Override
    public boolean isFinished(){
        return true;
    }

}
