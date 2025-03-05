package org.firstinspires.ftc.teamcode.base.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.base.subsystems.ClawSubsystem;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class ClawCommand extends CommandBase {

    private final ClawSubsystem claw;
    private final double rPos, lPos;

    public ClawCommand(ClawSubsystem claw, double rPos, double lPos) {
        this.claw=claw;
        this.rPos = rPos;
        this.lPos = lPos;
        addRequirements(claw);
    }

    @Override
    public void execute() {
        claw.set(rPos, lPos);
    }

}
