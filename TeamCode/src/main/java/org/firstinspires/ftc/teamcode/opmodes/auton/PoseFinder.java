package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.base.bot.Robot;
import org.firstinspires.ftc.teamcode.base.subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.opmodes.teleop.Solo;

import java.util.ArrayList;

@Autonomous(group = "Tuner")
public class PoseFinder extends OpMode {
    private ArrayList<Pose> poses;
    private boolean previousLeftBumper = false;
    private Solo solo;

    @Override
    public void init() {
        solo = new Solo(new Robot(Robot.Mode.SOLO, hardwareMap));
        solo.initialize();
        poses = new ArrayList<>();
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        FollowerSubsystem follower = solo.bot.follower;

        if (follower != null) {
            Pose currentPose = follower.getFollower().getPose();
            telemetry.addData("X", String.format("%.2f", currentPose.getX()));
            telemetry.addData("Y", String.format("%.2f", currentPose.getY()));
            telemetry.addData("Heading", String.format("%.2f", Math.toDegrees(currentPose.getHeading())));

            if (gamepad1.left_bumper && !previousLeftBumper) {
                poses.add(new Pose(currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
            }
            previousLeftBumper = gamepad1.left_bumper;

            for (Pose p : poses) {
                telemetry.addLine(String.format("Pose: (%.2f, %.2f, %.2f)", p.getX(), p.getY(), Math.toDegrees(p.getHeading())));
            }
        }
    }

    @Override
    public void stop() {
        CommandScheduler.getInstance().reset();
    }
}