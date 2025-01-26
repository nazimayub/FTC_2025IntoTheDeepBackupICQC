package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.util.Timer;

public class FollowPathCommand extends CommandBase {
    private final Path path;
    Follower follower;


    public FollowPathCommand(Follower follower, Path path) {
        this.follower = follower;
        this.path = path;
        addRequirements();
    }

    @Override
    public void execute() {
        if(!follower.isBusy())
            follower.followPath(path);
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy();
    }
}