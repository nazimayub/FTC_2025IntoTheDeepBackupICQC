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

    public FollowPathCommand(PathChain pathChain, int time) {
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
        for(int i = 0; i < pathChain.size(); i++)
            if(!follower.isBusy() || pathTimer.getElapsedTime() > time)
                follower.followPath(pathChain.getPath(i));
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy() || pathTimer.getElapsedTime() > time;
    }
}