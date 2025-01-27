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
import org.firstinspires.ftc.teamcode.Const2;
import org.firstinspires.ftc.teamcode.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.commands.ServoCommand;
import org.firstinspires.ftc.teamcode.commands.SetPIDFSlideArmCommand;
import org.firstinspires.ftc.teamcode.commands.SlideResetCommand;
import org.firstinspires.ftc.teamcode.commands.WaitCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ServoSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WaitSubsystem;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;

@Autonomous
public class AutoSamp extends OpMode {
    public static ServoSubsystem outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawTwist, outtakeClawDistRight, outtakeClawDistLeft, shifter;
    public static IntakeSubsystem intake;
    public static LimitSwitchSubsystem vLimit, hLimit;
    public static PIDFSlideSubsystem slide;
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
    private final Pose startPose = new Pose(6.689, 110.741, Math.toRadians(0));
    private final Pose scorePreloadPose = new Pose(12.307, 135.994, Math.toRadians(315));
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
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Const.hSlide, -0.02, 0, 0, 0.0);
        slide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.001, 0,  0, 0.2, 0.001, 0, 0, 0.2);        pause = new WaitSubsystem();
        outtakeClaw = new ServoSubsystem(hardwareMap, Const.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Const.intakeDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Const.intakeRot);
        outtakeClawDistLeft = new ServoSubsystem(hardwareMap, Const.outtakeDistLeft);
        outtakeClawDistRight = new ServoSubsystem(hardwareMap, Const.outtakeDistRight);
        vLimit = new LimitSwitchSubsystem(hardwareMap, Const.vLimit);
        hLimit = new LimitSwitchSubsystem(hardwareMap, Const.hLimit);
        shifter = new ServoSubsystem(hardwareMap, Const.gearShifter);
        outtakeClawRot = new ServoSubsystem(hardwareMap, Const.outtakeRot);
        outtakeClawTwist = new ServoSubsystem(hardwareMap, Const.outtakeTwist);
        pause = new WaitSubsystem();

        follower.setStartingPose(startPose);
        path();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new ServoCommand(intakeClawRot, .58),
                        new ServoCommand(outtakeClaw, Const.grab),
                        new FollowPathCommand(follower, scorePreloadPath, true),
                        new ServoCommand(outtakeClawRot, 0.5),
                        new ServoCommand(outtakeClawDistRight, 1-0.378),
                        new ServoCommand(outtakeClawDistLeft, 0.378),
                        new SetPIDFSlideArmCommand(slide, 40000),
                        new ServoCommand(outtakeClaw, Const.release)
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
        telemetry.addData("Busy", follower.isBusy());
        telemetry.update();
    }
}
