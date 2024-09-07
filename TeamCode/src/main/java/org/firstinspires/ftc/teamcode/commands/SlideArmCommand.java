package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SlideSubsystem;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class SlideArmCommand extends CommandBase {

    private SlideSubsystem slide;
    private ArmSubsystem arm;

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

    @Override
    public void execute() {
        if (slide != null) {
            slide.set(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
        }
        else {
            arm.set(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
        }

    }

}
