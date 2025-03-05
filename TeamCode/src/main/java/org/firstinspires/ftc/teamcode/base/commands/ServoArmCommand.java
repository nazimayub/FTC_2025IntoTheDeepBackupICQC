package org.firstinspires.ftc.teamcode.base.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.base.subsystems.ServoArmSubsystem;


import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class ServoArmCommand extends CommandBase {

    private final ServoArmSubsystem arm;
    private final double pos;

    public ServoArmCommand(ServoArmSubsystem arm, double pos) {
        this.arm=arm;
        this.pos=pos;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        arm.set(pos);
    }

}
