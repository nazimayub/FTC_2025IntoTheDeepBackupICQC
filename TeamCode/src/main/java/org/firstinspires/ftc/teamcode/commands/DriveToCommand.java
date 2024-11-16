package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.*;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class DriveToCommand extends CommandBase {

    private final Drive d;
    private final double x, y, theta;

    public DriveToCommand(Drive d, double x, double y, double theta) {
        this.d=d;
        this.x = x;
        this.y = y;
        this.theta = theta;
        addRequirements(d);
        d.getFl().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.getFr().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.getBl().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.getBr().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void execute() {
        d.getFl().setTargetPosition((int)(y*Constants.tickInInch + x * Constants.lateralTickInInch - theta * Constants.tickInDeg));
        d.getFr().setTargetPosition((int)(y* Constants.tickInInch - x * Constants.lateralTickInInch + theta * Constants.tickInDeg));
        d.getBl().setTargetPosition((int)(y*Constants.tickInInch - x * Constants.lateralTickInInch - theta * Constants.tickInDeg));
        d.getBr().setTargetPosition((int)(y*Constants.tickInInch + x * Constants.lateralTickInInch + theta * Constants.tickInDeg));
        d.getFl().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        d.getFr().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        d.getBl().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        d.getBr().setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public boolean isFinished(){
        return Math.abs(d.getFl().getCurrentPosition() - d.getFl().getTargetPosition()) < 2 && Math.abs(d.getBr().getCurrentPosition() - d.getBr().getTargetPosition()) < 2 && Math.abs(d.getBl().getCurrentPosition() - d.getBl().getTargetPosition()) < 2 && Math.abs(d.getFr().getCurrentPosition() - d.getFr().getTargetPosition()) < 2;
    }

    @Override
    public void end(boolean inturrupted){
        d.getFl().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.getFr().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.getBl().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.getBr().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
