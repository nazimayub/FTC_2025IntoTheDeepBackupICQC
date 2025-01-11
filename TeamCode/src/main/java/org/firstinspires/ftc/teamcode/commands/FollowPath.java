package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.Path;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FollowPath extends CommandBase {
    private final Path path;
    Follower follower;

    public FollowPath(Path path) {
        this.path = path;
        addRequirements();
    }

    @Override
    public void initialize() {
        follower = new Follower(hardwareMap);

    }

    @Override
    public void execute() {
        if(isFinished())
            follower.followPath(this.path);
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy();
    }
}
