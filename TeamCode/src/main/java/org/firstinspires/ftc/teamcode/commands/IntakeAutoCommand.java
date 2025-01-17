package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeAutoSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

/**
 * A command to run the intake at a specified speed for a given number of rotations.
 */
public class IntakeAutoCommand extends CommandBase {

    private final IntakeAutoSubsystem intake;
    private final double speed;
    private final double targetRotations;

    public IntakeAutoCommand(IntakeAutoSubsystem intake, double speed, int rots) {
        this.intake = intake;
        this.speed = speed;
        this.targetRotations = rots;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.resetEncoder();
    }

    @Override
    public void execute() {
        intake.set(speed);
    }

    @Override
    public boolean isFinished() {
        return intake.getCurrentRotations() >= targetRotations;
    }

    @Override
    public void end(boolean interrupted) {
        intake.set(0);
    }
}
