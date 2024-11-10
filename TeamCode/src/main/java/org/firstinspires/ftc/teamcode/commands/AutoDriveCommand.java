package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.Subsystem;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.subsystems.*;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class AutoDriveCommand extends CommandBase {

    private final SparkFunOTOSDrive drive;
    private final double x, y, deg;

    public AutoDriveCommand(SparkFunOTOSDrive drive, double x, double y, double deg) {
        this.drive =drive;
        this.x = x;
        this.y = y;
        this.deg = deg;
    }

    @Override
    public void execute() {
        drive.actionBuilder(new Pose2d(x, y, deg));
    }
    @Override
    public boolean isFinished(){
        if (Math.abs(drive.pose.position.x-x) < 0.5 && Math.abs(drive.pose.position.y-y) < 0.5 && Math.abs(drive.pose.heading.toDouble()-Math.toRadians(deg)) < 1){
            return true;
        }
        return false;
    }

}
