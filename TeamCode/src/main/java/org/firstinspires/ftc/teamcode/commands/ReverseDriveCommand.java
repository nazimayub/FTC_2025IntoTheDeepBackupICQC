package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.*;


import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class ReverseDriveCommand extends CommandBase {


    public ReverseDriveCommand() {
        if(Constants.direction.equals("forward")){
            Constants.direction = "backward";
        }
        else {
            Constants.direction = "forward";
        }
    }

    @Override
    public boolean isFinished(){
        return true;
    }



}
