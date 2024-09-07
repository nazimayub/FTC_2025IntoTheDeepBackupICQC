package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.HandSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class HandCommand extends CommandBase {

    private final HandSubsystem hand;
    private final double pos;

    public HandCommand(HandSubsystem hand, double pos) {
        this.hand=hand;
        this.pos=pos;
        addRequirements(hand);
    }

    @Override
    public void execute() {
        hand.set(pos);
    }

}
