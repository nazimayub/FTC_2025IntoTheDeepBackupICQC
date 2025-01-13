package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Const;
import org.firstinspires.ftc.teamcode.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.commands.ServoCommand;
import org.firstinspires.ftc.teamcode.commands.SetPIDFSlideArmCommand;
import org.firstinspires.ftc.teamcode.commands.SlideResetCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ServoSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WaitSubsystem;

@Autonomous
public class Auto extends OpMode {
    public static ServoSubsystem outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawDist, blocker;
    public static IntakeSubsystem intake;
    public static LimitSwitchSubsystem vLimit, hLimit;
    public static PIDFSlideSubsystem slide;
    public static PIDFSingleSlideSubsystem hSlide;
    public static TelemetrySubsystem telemetrySubsystem;
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
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);

        intake = new IntakeSubsystem(hardwareMap, Const.intake);
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Const.hSlide, 0.05, 0, 0.0007, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.005, 0,  0.0, 0.1, 0.005, 0, 0.0, 0.1);
        pause = new WaitSubsystem();
        outtakeClaw = new ServoSubsystem(hardwareMap, Const.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Const.intakeDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Const.intakeRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Const.outtakeDist);
        vLimit = new LimitSwitchSubsystem(hardwareMap, Const.vLimit);
        hLimit = new LimitSwitchSubsystem(hardwareMap, Const.hLimit);
        blocker = new ServoSubsystem(hardwareMap, Const.blocker);
        outtakeClawRot = new ServoSubsystem(hardwareMap, Const.outtakeRot);

        follower.setStartingPose(startPose);
        path();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new ServoCommand(outtakeClaw, Const.grab),
                        new ServoCommand(outtakeClawDist, Const.distBasketPos),
                        new ServoCommand(outtakeClawRot, Const.rotBasketPos),
                        new SetPIDFSlideArmCommand(slide, Const.vSlideBar),
                        new FollowPathCommand(scorePreloadPath, 3000),
                        new ServoCommand(outtakeClawDist, Const.distBasketPos-0.1),
                        new SlideResetCommand(slide, vLimit),
                        new FollowPathCommand(moveFromFirstSpecimenScorePath,3000),
                        new FollowPathCommand(strafeToSampsPath,3000),
                        new FollowPathCommand(moveToSampsPath,3000),
                        new FollowPathCommand(moveToFirstSampPath,3000),
                        new FollowPathCommand(pushFirstSampPath,3000),
                        new FollowPathCommand(moveToSecondSampPath,3000),
                        new FollowPathCommand(strafeToSecondSampPath,3000),
                        new FollowPathCommand(pushSecondSampPath,3000),
                        new FollowPathCommand(moveToThirdSampPath,3000),
                        new FollowPathCommand(strafeToThirdSampPath,3000),
                        new FollowPathCommand(pushThirdSampPath,3000)
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
