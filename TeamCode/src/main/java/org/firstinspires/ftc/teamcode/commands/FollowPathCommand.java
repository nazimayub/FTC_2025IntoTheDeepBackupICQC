package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.util.Timer;

public class FollowPathCommand extends CommandBase {
    private final Path path;
    private final Timer pathTimer = new Timer();
    Follower follower;
    private final int time;


    public FollowPathCommand(Follower follower, Path path, int time) {
        this.follower = follower;
        this.path = path;
        this.time = time;
        addRequirements();
    }

    @Override
    public void initialize() {
        pathTimer.resetTimer();
    }

    @Override
    public void execute() {
        if (!follower.isBusy() || pathTimer.getElapsedTime() > time) {
            follower.followPath(path);
        }
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy() || pathTimer.getElapsedTime() > time;
    }
}