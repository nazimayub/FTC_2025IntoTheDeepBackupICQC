package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.pedropathing.pathgen.PathChain;

public class FollowPathCommand extends CommandBase {
    private final PathChain pathChain;

    public FollowPathCommand(PathChain pathChain) {
        this.pathChain = pathChain;
        addRequirements();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
    }

    @Override
    public void end(boolean interrupted) {
    }
}
