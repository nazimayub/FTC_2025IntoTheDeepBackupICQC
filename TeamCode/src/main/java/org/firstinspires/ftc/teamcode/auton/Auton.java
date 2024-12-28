package org.firstinspires.ftc.teamcode.auton;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.*;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;
import org.firstinspires.ftc.teamcode.subsystems.*;

@Autonomous
public class Auton extends CommandOpMode {
    public static PIDFSlideSubsystem slide;
    public static ServoSubsystem intakeClaw, outtakeClawRot, outtakeClaw, intakeClawDist,
            intakeClawRot, outtakeClawDist, blocker;
    public static IntakeSubsystem intake;
    public static LimitSwitchSubsystem vertical, horizontal;
    public static PIDFSingleSlideSubsystem hSlide;
    public static WaitSubsystem pause;

    //Paths
    private Follower follower;
    private Timer pathTimer;
    private int pathState;
    private PathChain scorePreloadPath, moveToSampsPath, pushSampsToHPPath;

    //Poses
    private final Pose startPose = new Pose(0.504, 57.802, Point.CARTESIAN);
    private final Pose scorePreloadPose = new Pose(30.319, 57.802, Point.CARTESIAN);
    private final Pose moveFromScorePreloadPose = new Pose(23.319, 57.802, Point.CARTESIAN);
    private final Pose strafeToSampsPose = new Pose(23.319, 17.802, Point.CARTESIAN);
    private final Pose moveToFirstSampPose = new Pose(60.319, 5.802, Point.CARTESIAN);
    private final Pose pushFirstSampPose = new Pose(5.00, 5.802, Point.CARTESIAN);
    private final Pose moveToSecondSampPose = new Pose(60.319, 3.802, Point.CARTESIAN);
    private final Pose pushSecondSampPose = new Pose(5.00, 3.802, Point.CARTESIAN);
    private final Pose moveToThirdSampPose = new Pose(60.319, 0.802, Point.CARTESIAN);
    private final Pose pushThirdSampPose = new Pose(5.00, 0.802, Point.CARTESIAN);

    @Override
    public void initialize() {
        pathTimer = new Timer();
        pathTimer.resetTimer();
        follower = new Follower(hardwareMap);
        intake = new IntakeSubsystem(hardwareMap, Constants.intake);
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.05, 0, 0.0007, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide,
                DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD,
                0.005, 0, 0.0, 0.1, 0.005, 0, 0.0, 0.1);
        pause = new WaitSubsystem();
        outtakeClaw = new ServoSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Constants.intakeDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Constants.intakeRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Constants.outtakeDist);
        vertical = new LimitSwitchSubsystem(hardwareMap, "vSlide");
        horizontal = new LimitSwitchSubsystem(hardwareMap, "hSlide");
        blocker = new ServoSubsystem(hardwareMap, "servo6");
        outtakeClawRot = new ServoSubsystem(hardwareMap, Constants.outtakeRot);

        follower.setStartingPose(startPose);

        pathing();

        pathState = 0;
    }

    @Override
    public void run() {
        follower.update();
        updatePath();
        telemetry.addData("Path State", pathState);
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", follower.getPose().getHeading());
        telemetry.update();
    }

    public void pathing() {
        PathBuilder scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(scorePreloadPose)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        scorePreloadPath = scorePreload.build();

        PathBuilder moveToSamps = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePreloadPose), new Point(moveFromScorePreloadPose)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(new BezierLine(new Point(moveFromScorePreloadPose), new Point(strafeToSampsPose)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(new BezierLine(new Point(strafeToSampsPose), new Point(moveToFirstSampPose)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        moveToSampsPath = moveToSamps.build();

        PathBuilder pushSampsToHP = follower.pathBuilder()
                .addPath(new BezierLine(new Point(moveToFirstSampPose), new Point(pushFirstSampPose)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(new BezierLine(new Point(pushFirstSampPose), new Point(moveToSecondSampPose)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(new BezierLine(new Point(moveToSecondSampPose), new Point(pushSecondSampPose)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(new BezierLine(new Point(pushSecondSampPose), new Point(moveToThirdSampPose)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(new BezierLine(new Point(moveToThirdSampPose), new Point(pushThirdSampPose)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        pushSampsToHPPath = pushSampsToHP.build();

    }

    public void setPathState(int num){
        pathState = num;
    }

    public void updatePath() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreloadPath);
//                schedule(new SequentialCommandGroup(
//                        new ServoCommand(outtakeClaw, Constants.grab),
//                        new ServoCommand(outtakeClawDist, Constants.distBasketPos),
//                        new ServoCommand(outtakeClawRot, Constants.outtakeClawRotTransfer),
//                        new SetPIDFSlideArmCommand(slide, 525)
//                ));
                setPathState(1);
                break;
            case 1:
                if (follower.getPose().getX() > (scorePreloadPose.getX() - 1) && follower.getPose().getY() > (scorePreloadPose.getY() - 1)) {
                    follower.followPath(moveToSampsPath);
                    setPathState(2);
                }
                break;
            case 2:
                if (follower.getPose().getX() > (moveToFirstSampPose.getX() - 1) && follower.getPose().getY() > (moveToFirstSampPose.getY() - 1)) {
                    follower.followPath(pushSampsToHPPath);
                    setPathState(3);
                }
                break;
            case 3:
                break;
        }
    }
}