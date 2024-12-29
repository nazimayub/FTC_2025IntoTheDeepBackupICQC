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
    private PathChain scorePreloadPath, moveFromFirstSpecimenScorePath, strafeToSampsPath,
            moveToSampsPath, moveToFirstSampPath, pushFirstSampPath, moveToSecondSampPath,
            strafeToSecondSampPath, pushSecondSampPath, moveToThirdSampPath, strafeToThirdSampPath,
            pushThirdSampPath;

    //Poses
    private final Pose startPose = new Pose(0.504, 57.802, Math.toRadians(0));
    private final Pose scorePreloadPose = new Pose(30.319, 57.802, Math.toRadians(0));
    private final Pose moveFromScorePreloadPose = new Pose(23.319, 57.802, Math.toRadians(0));
    private final Pose strafeToSampsPose = new Pose(23.319, 17.802, Math.toRadians(0));
    private final Pose moveToSampsPose = new Pose(60.319, 17.802, Math.toRadians(0));

    private final Pose moveToFirstSampPose = new Pose(60.319, 5.802, Math.toRadians(0));
    private final Pose pushFirstSampPose = new Pose(5.00, 5.802, Math.toRadians(0));

    private final Pose moveToSecondSampPose = new Pose(60.319, 5.802, Math.toRadians(0));
    private final Pose strafeToSecondSampPose = new Pose(60.319, 2.802, Math.toRadians(0));
    private final Pose pushSecondSampPose = new Pose(5.00, 2.802, Math.toRadians(0));

    private final Pose moveToThirdSampPose = new Pose(60.319, 2.802, Math.toRadians(0));
    private final Pose strafeToThirdSampPose = new Pose(60.319, 0.802, Math.toRadians(0));
    private final Pose pushThirdSampPose = new Pose(5.00, 0.802, Math.toRadians(0));

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
//
//        moveToThirdSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(pushSecondSampPose), new Point(moveToThirdSampPose)))
//                .setLinearHeadingInterpolation(pushSecondSampPose.getHeading(), moveToThirdSampPose.getHeading())
//                .build();
//
//        strafeToThirdSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(moveToThirdSampPose), new Point(strafeToThirdSampPose)))
//                .setLinearHeadingInterpolation(moveToThirdSampPose.getHeading(), strafeToThirdSampPose.getHeading())
//                .build();
//
//       pushThirdSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(strafeToThirdSampPose), new Point(pushThirdSampPose)))
//                .setLinearHeadingInterpolation(strafeToThirdSampPose.getHeading(), pushThirdSampPose.getHeading())
//                .build();
    }

    public void setPathState(int num){
        pathState = num;
    }

    public void updatePath() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreloadPath, true);
//                schedule(new SequentialCommandGroup(
//                        new ServoCommand(outtakeClaw, Constants.grab),
//                        new ServoCommand(outtakeClawDist, Constants.distBasketPos),
//                        new ServoCommand(outtakeClawRot, Constants.outtakeClawRotTransfer),
//                        new SetPIDFSlideArmCommand(slide, 525)
//                ));
                setPathState(1);
                break;

            case 1:
                if (follower.getPose().getX() > (scorePreloadPose.getX() - 1)) {
                    follower.followPath(moveFromFirstSpecimenScorePath, true);
                    setPathState(2);
                }
                break;

            case 2:
                if (follower.getPose().getX() < (moveFromScorePreloadPose.getX() - 1)) {
                    follower.followPath(strafeToSampsPath, true);
                    setPathState(3);
                }
                break;

            case 3:
                if (follower.getPose().getX() > (strafeToSampsPose.getX() - 1)) {
                    follower.followPath(moveToSampsPath, true);
                    setPathState(4);
                }
                break;

            case 4:
                if (follower.getPose().getX() > (moveToSampsPose.getX() - 1)) {
                    follower.followPath(moveToFirstSampPath, true);
                    setPathState(5);
                }
                break;

            case 5:
                if (follower.getPose().getY() < (moveToFirstSampPose.getY() - 1)) {
                    follower.followPath(pushFirstSampPath, true);
                    setPathState(6);
                }
                break;

            case 6:
                if (follower.getPose().getX() < (pushFirstSampPose.getX() - 1)) {
                    follower.followPath(moveToSecondSampPath, true);
                    setPathState(7);
                }
                break;

            case 7:
                if (follower.getPose().getX() > (moveToSecondSampPose.getX() - 1)) {
                    follower.followPath(pushSecondSampPath, true);
                    setPathState(8);
                }
                break;

            case 8:
                if (follower.getPose().getX() < (pushSecondSampPose.getX() - 1)) {
                    //follower.followPath(moveToThirdSampPath, true);
                    setPathState(9);
                }
                break;

//            case 9:
//                if (follower.getPose().getX() > (moveToThirdSampPose.getX() - 1) && follower.getPose().getY() > (moveToThirdSampPose.getY() - 1)) {
//                    follower.followPath(strafeToThirdSampPath, true);
//                    setPathState(10);
//                }
//                break;
//
//            case 10:
//                if (follower.getPose().getX() > (strafeToThirdSampPose.getX() - 1) && follower.getPose().getY() > (strafeToThirdSampPose.getY() - 1)) {
//                    follower.followPath(pushThirdSampPath, true);
//                    setPathState(11);
//                }
//                break;
//            case 11:
//                break;

        }
    }
}