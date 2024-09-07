package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class IntakeCommand extends CommandBase {

    private final IntakeSubsystem intake;
    private final double speed;

    public IntakeCommand(IntakeSubsystem intake, double speed) {
        this.intake=intake;
        this.speed=speed;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.set(speed);
    }

}
