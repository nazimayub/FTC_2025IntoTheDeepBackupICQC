package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.*;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class ServoIntakeCommand extends CommandBase {

    private final ServoIntakeSubsystem intake;
    private final double speed;

    public ServoIntakeCommand(ServoIntakeSubsystem intake, double speed) {
        this.intake=intake;
        this.speed=speed;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.set(speed);
    }

}
