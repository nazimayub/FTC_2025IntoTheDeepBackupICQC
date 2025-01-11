package org.firstinspires.ftc.teamcode.auton;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Actions;
import org.firstinspires.ftc.teamcode.Const;
import org.firstinspires.ftc.teamcode.commands.FollowPath;
import org.firstinspires.ftc.teamcode.pedroPathing.*;

@Autonomous
public class Auto2 extends OpMode {
    public static DcMotorEx lSlide;
    public static DcMotorEx rSlide;
    public static Servo outtakeClawDist;
    public static Servo outtakeClawRot;
    public static Servo outtakeClaw;
    public static Servo blocker;
    public static Servo intake;

    //Paths
    private Follower follower;
    private Timer pathTimer;
    private int pathState;
    private PathChain scorePreloadPath, moveFromFirstSpecimenScorePath, strafeToSampsPath,
            moveToSampsPath, moveToFirstSampPath, pushFirstSampPath, moveToSecondSampPath,
            strafeToSecondSampPath, pushSecondSampPath, moveToThirdSampPath, strafeToThirdSampPath,
            pushThirdSampPath;

    //Poses
    private final Pose startPose = new Pose(0.504, 57.802, Math.toRadians(0));
    private final Pose scorePreloadPose = new Pose(30.319, 57.802, Math.toRadians(0));
    private final Pose moveFromScorePreloadPose = new Pose(23.319, 57.802, Math.toRadians(0));
    private final Pose strafeToSampsPose = new Pose(23.319, 25.802, Math.toRadians(0));
    private final Pose moveToSampsPose = new Pose(50.319, 25.802, Math.toRadians(0));

    private final Pose moveToFirstSampPose = new Pose(50.319, 17.802, Math.toRadians(0));
    private final Pose pushFirstSampPose = new Pose(11.00, 17.802, Math.toRadians(0));

    private final Pose moveToSecondSampPose = new Pose(50.319, 17.802, Math.toRadians(0));
    private final Pose strafeToSecondSampPose = new Pose(50.319, 9.802, Math.toRadians(0));
    private final Pose pushSecondSampPose = new Pose(11.00, 9.802, Math.toRadians(0));

    private final Pose moveToThirdSampPose = new Pose(65.319, 9.802, Math.toRadians(180));
    private final Pose strafeToThirdSampPose = new Pose(65.319, -8, Math.toRadians(180));
    private final Pose pushThirdSampPose = new Pose(8.00, -8, Math.toRadians(180));

    @Override
    public void init() {
        pathTimer = new Timer();
        pathTimer.resetTimer();
        follower = new Follower(hardwareMap);

        lSlide = hardwareMap.get(DcMotorEx.class, Const.lSlide);
        rSlide = hardwareMap.get(DcMotorEx.class, Const.rSlide);
        outtakeClawDist = hardwareMap.get(Servo.class, Const.outtakeDist);
        outtakeClawRot = hardwareMap.get(Servo.class, Const.outtakeRot);
        outtakeClaw = hardwareMap.get(Servo.class, Const.outtakeClaw);
        blocker = hardwareMap.get(Servo.class, Const.blocker);
        intake = hardwareMap.get(Servo.class, Const.intakeRot);

        lSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        follower.setStartingPose(startPose);
        path();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                    new FollowPath(scorePreloadPath.getPath(0)),
                    Actions.specimenScore(),
                    new FollowPath(moveFromFirstSpecimenScorePath.getPath(0)),
                    Actions.transfer(),
                    new FollowPath(strafeToSampsPath.getPath(0)),
                    new FollowPath(moveToSampsPath.getPath(0)),
                    new FollowPath(moveToFirstSampPath.getPath(0)),
                    new FollowPath(pushFirstSampPath.getPath(0)),
                    new FollowPath(moveToSecondSampPath.getPath(0)),
                    new FollowPath(strafeToSecondSampPath.getPath(0)),
                    new FollowPath(pushSecondSampPath.getPath(0)),
                    new FollowPath(moveToThirdSampPath.getPath(0)),
                    Actions.specimenGrabPos(),
                    new FollowPath(strafeToThirdSampPath.getPath(0)),
                    new FollowPath(pushThirdSampPath.getPath(0)),
                    Actions.specimenGrab()
                )
        );
    }

    public void path() {
        scorePreloadPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(scorePreloadPose)))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePreloadPose.getHeading())
                .build();

        moveFromFirstSpecimenScorePath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePreloadPose), new Point(moveFromScorePreloadPose)))
                .setLinearHeadingInterpolation(scorePreloadPose.getHeading(), moveFromScorePreloadPose.getHeading())
                .build();

        strafeToSampsPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(moveFromScorePreloadPose), new Point(strafeToSampsPose)))
                .setLinearHeadingInterpolation(moveFromScorePreloadPose.getHeading(), strafeToSampsPose.getHeading())
                .build();

        moveToSampsPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(strafeToSampsPose), new Point(moveToSampsPose)))
                .setLinearHeadingInterpolation(strafeToSampsPose.getHeading(), moveToSampsPose.getHeading())
                .build();

        moveToFirstSampPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(moveToSampsPose), new Point(moveToFirstSampPose)))
                .setLinearHeadingInterpolation(moveToSampsPose.getHeading(), moveToFirstSampPose.getHeading())
                .build();

        pushFirstSampPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(moveToFirstSampPose), new Point(pushFirstSampPose)))
                .setLinearHeadingInterpolation(moveToFirstSampPose.getHeading(), pushFirstSampPose.getHeading())
                .build();

        moveToSecondSampPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushFirstSampPose), new Point(moveToSecondSampPose)))
                .setLinearHeadingInterpolation(pushFirstSampPose.getHeading(), moveToSecondSampPose.getHeading())
                .build();

        strafeToSecondSampPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(moveToSecondSampPose), new Point(strafeToSecondSampPose)))
                .setLinearHeadingInterpolation(moveToSecondSampPose.getHeading(), strafeToSecondSampPose.getHeading())
                .build();

        pushSecondSampPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(strafeToSecondSampPose), new Point(pushSecondSampPose)))
                .setLinearHeadingInterpolation(strafeToSecondSampPose.getHeading(), pushSecondSampPose.getHeading())
                .build();

        moveToThirdSampPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushSecondSampPose), new Point(moveToThirdSampPose)))
                .setLinearHeadingInterpolation(pushSecondSampPose.getHeading(), moveToThirdSampPose.getHeading())
                .build();

        strafeToThirdSampPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(moveToThirdSampPose), new Point(strafeToThirdSampPose)))
                .setConstantHeadingInterpolation(strafeToThirdSampPose.getHeading())
                .build();

        pushThirdSampPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(strafeToThirdSampPose), new Point(pushThirdSampPose)))
                .setConstantHeadingInterpolation(pushThirdSampPose.getHeading())
                .build();
    }

    @Override
    public void loop() {
        follower.update();

        CommandScheduler.getInstance().run();

        telemetry.addData("Path State", pathState);
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", follower.getPose().getHeading());
        telemetry.update();
    }
}
