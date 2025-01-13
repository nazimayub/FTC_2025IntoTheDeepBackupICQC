package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.util.Timer;

public class FollowPathCommand extends CommandBase {
    private final PathChain pathChain;
    private final Timer pathTimer = new Timer();
    Follower follower;
    private final int time;
    private int currentPathIndex = 0;


    public FollowPathCommand(Follower follower, PathChain pathChain, int time) {
        this.follower = follower;
        this.pathChain = pathChain;
        this.time = time;
        addRequirements();
    }

    @Override
    public void initialize() {
        pathTimer.resetTimer();
    }

    @Override
    public void execute() {
        if (currentPathIndex < pathChain.size()) {
            if (!follower.isBusy() || pathTimer.getElapsedTime() > time) {
                follower.followPath(pathChain.getPath(currentPathIndex));
                currentPathIndex++;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return currentPathIndex >= pathChain.size() || pathTimer.getElapsedTime() > time;
    }
}