package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.*;


import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class ReverseDriveCommand extends CommandBase {


    public ReverseDriveCommand(Drive d) {
        if(d.getFl().getDirection() == DcMotorSimple.Direction.FORWARD){
            d.getFl().setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else {
            d.getFl().setDirection(DcMotorSimple.Direction.FORWARD);
        }
        if(d.getFr().getDirection() == DcMotorSimple.Direction.FORWARD){
            d.getFr().setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else {
            d.getFr().setDirection(DcMotorSimple.Direction.FORWARD);
        }
        if(d.getBl().getDirection() == DcMotorSimple.Direction.FORWARD){
            d.getBl().setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else {
            d.getBl().setDirection(DcMotorSimple.Direction.FORWARD);
        }
        if(d.getBr().getDirection() == DcMotorSimple.Direction.FORWARD){
            d.getBr().setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else {
            d.getBr().setDirection(DcMotorSimple.Direction.FORWARD);
        }
        addRequirements(d);
    }

    @Override
    public boolean isFinished(){
        return true;
    }



}
