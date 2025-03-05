package org.firstinspires.ftc.teamcode.base.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.base.subsystems.Drive;
import org.firstinspires.ftc.teamcode.base.bot.Const;

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

        d.getFl().setTargetPosition((int)((-1)*(y* Const.tickInInch + x * Const.lateralTickInInch - theta * Const.tickInDeg)));
        d.getFr().setTargetPosition((int)(y* Const.tickInInch - x * Const.lateralTickInInch + theta * Const.tickInDeg));
        d.getBl().setTargetPosition((int)(y* Const.tickInInch - x * Const.lateralTickInInch - theta * Const.tickInDeg));
        d.getBr().setTargetPosition((int)((-1)*(y* Const.tickInInch + x * Const.lateralTickInInch + theta * Const.tickInDeg)));
        d.getFl().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        d.getFr().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        d.getBl().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        d.getBr().setMode(DcMotor.RunMode.RUN_TO_POSITION);

        d.getFl().setPower(1);
        d.getFr().setPower(1);
        d.getBl().setPower(1);
        d.getBr().setPower(1);
    }

    @Override
    public void execute() {
//        d.getFl().setTargetPosition((int)((-1)*(y*Constants.tickInInch + x * Constants.lateralTickInInch - theta * Constants.tickInDeg)));
//        d.getFr().setTargetPosition((int)(y* Constants.tickInInch - x * Constants.lateralTickInInch + theta * Constants.tickInDeg));
//        d.getBl().setTargetPosition((int)(y*Constants.tickInInch - x * Constants.lateralTickInInch - theta * Constants.tickInDeg));
//        d.getBr().setTargetPosition((int)((-1)*(y*Constants.tickInInch + x * Constants.lateralTickInInch + theta * Constants.tickInDeg)));
//        d.getFl().setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        d.getFr().setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        d.getBl().setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        d.getBr().setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        d.getFl().setPower(1);
//        d.getFr().setPower(1);
//        d.getBl().setPower(1);
//        d.getBr().setPower(1);
    }

    @Override
    public boolean isFinished() {
        return !d.getFl().isBusy() && !d.getFr().isBusy() && !d.getBl().isBusy() && !d.getBr().isBusy();
    }

    @Override
    public void end(boolean interrupted) {
        d.getFl().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.getFr().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.getBl().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.getBr().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        d.getFl().setPower(0);
        d.getFr().setPower(0);
        d.getBl().setPower(0);
        d.getBr().setPower(0);
    }

}
