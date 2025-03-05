package org.firstinspires.ftc.teamcode.base.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.tuning.Pedro.constants.FConstants;
import org.firstinspires.ftc.teamcode.tuning.Pedro.constants.LConstants;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class FollowerSubsystem extends CommandBase {
    private Follower follower;

    public FollowerSubsystem(HardwareMap h, Follower follower, Pose start)  {
        Constants.setConstants(FConstants.class, LConstants.class);
        this.follower = follower;
        follower.setPose(start);
    }

    public Follower getFollower() {
        return this.follower;
    }

    @Override
    public void initialize() {

    }

    public void execute() {
        follower.update();
        CommandScheduler.getInstance().run();

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", follower.getPose().getHeading());
        telemetry.addData("Busy", follower.isBusy());
        telemetry.update();
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy();
    }
}
