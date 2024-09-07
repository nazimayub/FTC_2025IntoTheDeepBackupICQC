package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.HandSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSubsystem;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class OuttakeCommand extends CommandBase {

    private final OuttakeSubsystem outtake;
    private final double rPos, lPos;

    public OuttakeCommand(OuttakeSubsystem outtake, double rPos, double lPos) {
        this.outtake=outtake;
        this.rPos = rPos;
        this.lPos = lPos;
        addRequirements(outtake);
    }

    @Override
    public void execute() {
        outtake.set(rPos, lPos);
    }

}
