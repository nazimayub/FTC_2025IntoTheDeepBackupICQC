package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.*;


import java.util.function.DoubleSupplier;

/**

 A command to drive the robot with joystick input
 (passed in as {@link DoubleSupplier}s). Written
 explicitly for pedagogical purposes.*/
public class WaitCommand extends CommandBase {

    private final WaitSubsystem arm;
    private final long pos;

    public WaitCommand(WaitSubsystem arm, long pos) {
        this.arm=arm;
        this.pos=pos;
        this.arm.start();
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        this.arm.start();
    }
    @Override
    public boolean isFinished(){
        return arm.elapesd() > pos;
    }

}

