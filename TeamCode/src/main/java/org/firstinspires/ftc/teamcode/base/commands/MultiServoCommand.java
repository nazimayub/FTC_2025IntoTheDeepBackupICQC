package org.firstinspires.ftc.teamcode.base.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.base.subsystems.MultiServoSubsystem;



import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class MultiServoCommand extends CommandBase {

    private final MultiServoSubsystem arm;
    private final double pos;
    private final int n;

    public MultiServoCommand(MultiServoSubsystem arm, int n, double pos) {
        this.arm=arm;
        this.pos=pos;
        this.n = n;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        arm.set(n, pos);
    }
    @Override
    public boolean isFinished(){
        return true;
    }

}
