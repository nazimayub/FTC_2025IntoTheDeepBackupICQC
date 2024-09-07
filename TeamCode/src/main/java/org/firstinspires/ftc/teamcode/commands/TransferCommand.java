package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TransferSubsystem;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class TransferCommand extends CommandBase {

    private final TransferSubsystem transfer;
    private final double speed;

    public TransferCommand(TransferSubsystem transfer, double speed) {
        this.transfer=transfer;
        this.speed=speed;
        addRequirements(transfer);
    }

    @Override
    public void execute() {
        transfer.set(speed);
    }

}
