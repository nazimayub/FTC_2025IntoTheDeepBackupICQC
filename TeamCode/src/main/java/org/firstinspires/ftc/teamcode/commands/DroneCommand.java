package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.*;


import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class DroneCommand extends CommandBase {

    private final DroneSubsystem drone;
    private final double pos;

    public DroneCommand(DroneSubsystem drone, double pos) {
        this.drone=drone;
        this.pos=pos;
        addRequirements(drone);
    }

    @Override
    public void execute() {
        drone.set(pos);
    }

}
