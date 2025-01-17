package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeAutoSubsystem;

/**
 * A command to control the intake subsystem at a specified speed.
 */
public class IntakeAutoCommand extends CommandBase {

    private final IntakeAutoSubsystem intake;
    private final double speed;
    private final int time;

    public IntakeAutoCommand(IntakeAutoSubsystem intake, double speed, int time) {
        this.intake = intake;
        this.speed = speed;
        this.time = time;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.resetElapsedTime();
    }

    @Override
    public void execute() {
        if (intake.getElapsedTime() < time) {
            intake.set(speed);
        } else {
            intake.set(0);
        }
    }

    @Override
    public boolean isFinished() {
        return intake.getElapsedTime() >= time;
    }

    @Override
    public void end(boolean interrupted) {
        intake.set(0);
    }
}
