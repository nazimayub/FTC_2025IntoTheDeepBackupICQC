package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.arcrobotics.ftclib.command.*;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.bot.Robot;
import org.firstinspires.ftc.teamcode.base.bot.Actions;

import java.util.ArrayList;

@Autonomous(name="5+1", group = ".Auton")
public class AutoSpec2 extends OpMode {
    public enum AutoPaths {
        PRELOAD(
                new Pose(0, 0, Math.toRadians(180)),
                new Pose(34, 6, Math.toRadians(180))
        ),

        GO_TO_SAMPLES(
                new Pose(38,  0, Math.toRadians(180)),
                new Pose(15, 0, Math.toRadians(180)),
                new Pose(15, -30, Math.toRadians(0)),
                new Pose(45, -30, Math.toRadians(0)),
                new Pose(45, -47, Math.toRadians(0))
        ),

        PUSH_SAMPLE_1(
                new Pose(45, -47, Math.toRadians(0)),
                new Pose(10, -47, Math.toRadians(0))
        ),

        PUSH_SAMPLE_2(
                new Pose(10, -47, Math.toRadians(0)),
                new Pose(45, -47, Math.toRadians(0)),
                new Pose(45, -58, Math.toRadians(0)),
                new Pose(10, -58, Math.toRadians(0))
        ),

        PUSH_SAMPLE_3(
                new Pose(10, -58, Math.toRadians(0)),
                new Pose(45, -58, Math.toRadians(0)),
                new Pose(45, -64, Math.toRadians(0)),
                new Pose(10, -64, Math.toRadians(0))
        ),

        GRAB_SPECIMEN_1(
                new Pose(10, -64, Math.toRadians(0)),
                new Pose(20, -33, Math.toRadians(0)),
                new Pose(4.75, -33, Math.toRadians(0))
        ),

        SCORE_SPECIMEN_1(
                new Pose(4.75, -33, Math.toRadians(0)),
                new Pose(34, 4, Math.toRadians(0))
        ),

        GRAB_SPECIMEN_2(
                new Pose(36, 4, Math.toRadians(0)),
                new Pose(20, -33, Math.toRadians(0)),
                new Pose(4.75, -33, Math.toRadians(0))
        ),

        SCORE_SPECIMEN_2(
                new Pose(4.75, -33, Math.toRadians(0)),
                new Pose(34,4, Math.toRadians(0))
        ),

        GRAB_SPECIMEN_3(
                new Pose(34, 3, Math.toRadians(0)),
                new Pose(20, -33, Math.toRadians(0)),
                new Pose(4.75, -33, Math.toRadians(0))
        ),

        SCORE_SPECIMEN_3(
                new Pose(4.75, -33, Math.toRadians(0)),
                new Pose(34, 2, Math.toRadians(0))
        ),

        GRAB_SPECIMEN_4(
                new Pose(34, 2, Math.toRadians(0)),
                new Pose(20, -33, Math.toRadians(0)),
                new Pose(4.75, -33, Math.toRadians(0))
        ),

        SCORE_SPECIMEN_4(
                new Pose(4.75, -33, Math.toRadians(0)),
                new Pose(34, 0, Math.toRadians(0))
        );

        private final Pose[] poses;

        AutoPaths(Pose... poses) {
            this.poses = poses;
        }

        public PathChain line(Follower follower) {
            PathChain path = follower.pathBuilder()
                    .addPath(new BezierLine(new Point(poses[0]), new Point(poses[1])))
                    .setLinearHeadingInterpolation(poses[0].getHeading(), poses[1].getHeading())
                    .build();
            return path;
        }

        public PathChain curve(Follower follower) {
            ArrayList<Point> controlPoints = new ArrayList<>();
            for (Pose pose : poses) {
                controlPoints.add(new Point(pose.getX(), pose.getY(), 1));
            }

            BezierCurve bezierCurve = new BezierCurve(controlPoints);

            PathChain path = follower.pathBuilder()
                    .addPath(bezierCurve)
                    .setLinearHeadingInterpolation(poses[0].getHeading(), poses[poses.length - 1].getHeading())
                    .build();

            return path;
        }

        public Pose[] getPoses() {
            return poses;
        }
    }

    @Override
    public void init() {
        Robot bot = new Robot(Robot.Mode.AUTO, hardwareMap);
        Follower f = bot.follower.getFollower();
        
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        Actions.SpecimenScoreReverseAction(bot),
                        Actions.FollowPathAction(bot, AutoPaths.PRELOAD.curve(f)),
                        Actions.FollowPathAction(bot, AutoPaths.GO_TO_SAMPLES.curve(f)),
                        Actions.ResetAction(bot),
                        Actions.FollowPathAction(bot, AutoPaths.PUSH_SAMPLE_1.curve(f)),
                        Actions.FollowPathAction(bot, AutoPaths.PUSH_SAMPLE_2.curve(f)),
                        Actions.FollowPathAction(bot, AutoPaths.PUSH_SAMPLE_3.curve(f)),
                        Actions.SpecimenGrabAction(bot),
                        Actions.FollowPathAction(bot, AutoPaths.GRAB_SPECIMEN_1.curve(f)),
                        Actions.SpecimenScoreAction(bot),
                        Actions.FollowPathAction(bot, AutoPaths.SCORE_SPECIMEN_1.curve(f)),
                        Actions.SpecimenGrabAction(bot),
                        Actions.FollowPathAction(bot, AutoPaths.GRAB_SPECIMEN_2.curve(f)),
                        Actions.SpecimenScoreAction(bot),
                        Actions.FollowPathAction(bot, AutoPaths.SCORE_SPECIMEN_2.curve(f)),
                        Actions.SpecimenGrabAction(bot),
                        Actions.FollowPathAction(bot, AutoPaths.GRAB_SPECIMEN_3.curve(f)),
                        Actions.SpecimenScoreAction(bot),
                        Actions.FollowPathAction(bot, AutoPaths.SCORE_SPECIMEN_3.curve(f)),
                        Actions.SpecimenGrabAction(bot),
                        Actions.FollowPathAction(bot, AutoPaths.GRAB_SPECIMEN_4.curve(f)),
                        Actions.SpecimenScoreAction(bot),
                        Actions.FollowPathAction(bot, AutoPaths.SCORE_SPECIMEN_4.curve(f))
                )
        );
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
    }
}
