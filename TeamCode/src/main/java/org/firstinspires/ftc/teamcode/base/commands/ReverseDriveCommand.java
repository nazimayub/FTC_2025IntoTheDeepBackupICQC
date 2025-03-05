package org.firstinspires.ftc.teamcode.base.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.base.bot.Const;


import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class ReverseDriveCommand extends CommandBase {


    public ReverseDriveCommand() {
        if(Const.direction.equals("forward")){
            Const.direction = "backward";
        }
        else {
            Const.direction = "forward";
        }
    }

    @Override
    public boolean isFinished(){
        return true;
    }



}
