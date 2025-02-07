package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Const;
import org.firstinspires.ftc.teamcode.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeAutoCommand;
import org.firstinspires.ftc.teamcode.commands.ServoCommand;
import org.firstinspires.ftc.teamcode.commands.SetPIDFSlideArmCommand;
import org.firstinspires.ftc.teamcode.commands.SlideResetCommand;
import org.firstinspires.ftc.teamcode.commands.WaitCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeAutoSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ServoSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WaitSubsystem;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;

@Autonomous
public class AutoSamp extends OpMode {
    // Paths
    private Follower follower;
    private PathChain[] paths = new PathChain[8];

    /** POSES
     * X - Forward Positive, Backwards Negative
     * Y - Left Positive, Right Negative
     */
    private final Pose[] poses = {
            new Pose(6.689, 110.741, Math.toRadians(90)),
            new Pose(6.2, 123.5, Math.toRadians(315)),
            new Pose(16.055, 117.588, Math.toRadians(0)),
            new Pose(6.2, 123.5, Math.toRadians(315)),
            new Pose(16.055, 126.622, Math.toRadians(0)),
            new Pose(6.2, 126.5, Math.toRadians(315)),
            new Pose(16.055, 126.22, Math.toRadians(20)),
            new Pose(6, 126.5, Math.toRadians(315)),
            new Pose(71.164, 94.204, Math.toRadians(0))
    };

    public static ServoSubsystem outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawTwist, outtakeClawDistRight, outtakeClawDistLeft, shifter;
    public static IntakeAutoSubsystem intake;
    public static LimitSwitchSubsystem vLimit, hLimit;
    public static PIDFSlideSubsystem slide;
    public static PIDFSingleSlideSubsystem hSlide;
    public static WaitSubsystem pause;

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);

        intake = new IntakeAutoSubsystem(hardwareMap, Const.intake, new ElapsedTime());
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Const.hSlide, -0.02, 0, 0, 0.0);
        slide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.001, 0, 0, 0.05, 0.001, 0, 0, 0.05);
        pause = new WaitSubsystem();
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

        follower.setStartingPose(poses[0]);
        hSlide.reset();
        intakeClawRot.set(.58);

        Command scorePreload =
                new SequentialCommandGroup(
                        new SlideResetCommand(hSlide, hLimit),
                        new ServoCommand(outtakeClaw, Const.grab),
                        new ServoCommand(outtakeClawRot, 0.9),
                        new ServoCommand(outtakeClawDistRight, 1-0.378),
                        new ServoCommand(outtakeClawDistLeft, 0.378),
                        new FollowPathCommand(follower, path(0, true), true),
                        new SetPIDFSlideArmCommand(slide, 38000),
                        new ServoCommand(outtakeClawRot, 0.6),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClaw, Const.release),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, .3),
                        new ServoCommand(outtakeClawDistLeft, 1),
                        new ServoCommand(outtakeClawDistRight, 0),
                        new ServoCommand(outtakeClawRot, 0.7),
                        new ServoCommand(outtakeClawTwist, 0.924)
                );

        Command scoreFirstSamp =
                new SequentialCommandGroup(
                        new FollowPathCommand(follower, path(1, false), true),
                        new SlideResetCommand(slide, vLimit),
                        new ServoCommand(intakeClawRot, 0.5),
                        new SetPIDFSlideArmCommand(hSlide, -350),
                        new ServoCommand(intakeClawRot, .22),
                        new ParallelCommandGroup(
                                new SetPIDFSlideArmCommand(hSlide, -735),
                                new IntakeAutoCommand(intake, -0.8, 1)
                        ),
                        new ServoCommand(outtakeClaw, Const.release),
                        new ServoCommand(intakeClawRot, .3),
                        new ServoCommand(outtakeClawDistLeft, 1),
                        new ServoCommand(outtakeClawDistRight, 0),
                        new ServoCommand(outtakeClawRot, 0.7),
                        new ServoCommand(outtakeClawTwist, 0.924),
                        new SlideResetCommand(slide, vLimit),
                        new SlideResetCommand(hSlide, hLimit),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClawRot, 0.83),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, 0.46),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClaw, Const.grab),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, .3),
                        new SetPIDFSlideArmCommand(slide, 5000),
                        new ServoCommand(outtakeClawRot, 0.9),
                        new ServoCommand(outtakeClawDistRight, 1-0.378),
                        new ServoCommand(outtakeClawDistLeft, 0.378),
                        new FollowPathCommand(follower, path(2, false), true),
                        new SetPIDFSlideArmCommand(slide, 38000),
                        new ServoCommand(outtakeClawRot, 0.4),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClaw, Const.release),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, .3),
                        new ServoCommand(outtakeClawDistLeft, 1),
                        new ServoCommand(outtakeClawDistRight, 0),
                        new ServoCommand(outtakeClawRot, 0.7),
                        new ServoCommand(outtakeClawTwist, 0.924)
                );

        Command scoreSecondSamp =
                new SequentialCommandGroup(
                        new FollowPathCommand(follower, path(3, false), true),
                        new SlideResetCommand(slide, vLimit),
                        new ServoCommand(intakeClawRot, 0.5),
                        new SetPIDFSlideArmCommand(hSlide, -350),
                        new ServoCommand(intakeClawRot, .22),
                        new ParallelCommandGroup(
                                new SetPIDFSlideArmCommand(hSlide, -735),
                                new IntakeAutoCommand(intake, -0.8, 1)
                        ),
                        new ServoCommand(outtakeClaw, Const.release),
                        new ServoCommand(intakeClawRot, .3),
                        new ServoCommand(outtakeClawDistLeft, 1),
                        new ServoCommand(outtakeClawDistRight, 0),
                        new ServoCommand(outtakeClawRot, 0.7),
                        new ServoCommand(outtakeClawTwist, 0.924),
                        new SlideResetCommand(slide, vLimit),
                        new SlideResetCommand(hSlide, hLimit),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClawRot, 0.83),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, 0.46),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClaw, Const.grab),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, .3),
                        new SetPIDFSlideArmCommand(slide, 5000),
                        new ServoCommand(outtakeClawRot, 0.9),
                        new ServoCommand(outtakeClawDistRight, 1-0.378),
                        new ServoCommand(outtakeClawDistLeft, 0.378),
                        new FollowPathCommand(follower, path(4, false), true),
                        new SetPIDFSlideArmCommand(slide, 38000),
                        new ServoCommand(outtakeClawRot, 0.4),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClaw, Const.release),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, .3),
                        new ServoCommand(outtakeClawDistLeft, 1),
                        new ServoCommand(outtakeClawDistRight, 0),
                        new ServoCommand(outtakeClawRot, 0.7),
                        new ServoCommand(outtakeClawTwist, 0.924)
                );

        Command scoreThirdSamp =
                new SequentialCommandGroup(
                        new FollowPathCommand(follower, path(5, false), true),
                        new SlideResetCommand(slide, vLimit),
                        new ServoCommand(intakeClawRot, 0.5),
                        new SetPIDFSlideArmCommand(hSlide, -350),
                        new ServoCommand(intakeClawRot, .22),
                        new ParallelCommandGroup(
                                new SetPIDFSlideArmCommand(hSlide, -660),
                                new IntakeAutoCommand(intake, -0.8, 1)
                        ),
                        new ServoCommand(outtakeClaw, Const.release),
                        new ServoCommand(intakeClawRot, .3),
                        new ServoCommand(outtakeClawDistLeft, 1),
                        new ServoCommand(outtakeClawDistRight, 0),
                        new ServoCommand(outtakeClawRot, 0.7),
                        new ServoCommand(outtakeClawTwist, 0.924),
                        new SlideResetCommand(slide, vLimit),
                        new SlideResetCommand(hSlide, hLimit),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClawRot, 0.83),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, 0.46),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClaw, Const.grab),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, .3),
                        new SetPIDFSlideArmCommand(slide, 5000),
                        new ServoCommand(outtakeClawRot, 0.9),
                        new ServoCommand(outtakeClawDistRight, 1-0.378),
                        new ServoCommand(outtakeClawDistLeft, 0.378),
                        new FollowPathCommand(follower, path(6, false), true),
                        new SetPIDFSlideArmCommand(slide, 38000),
                        new ServoCommand(outtakeClawRot, 0.4),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClaw, Const.release),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, .3),
                        new ServoCommand(outtakeClawDistLeft, 1),
                        new ServoCommand(outtakeClawDistRight, 0),
                        new ServoCommand(outtakeClawRot, 0.7),
                        new ServoCommand(outtakeClawTwist, 0.924)
                );

        Command park =
                new SequentialCommandGroup(
                        new FollowPathCommand(follower, path( 7, false), true)
                );

        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                scorePreload,
                scoreFirstSamp,
                scoreSecondSamp,
                scoreThirdSamp
                //park
        ));
    }

    public PathChain path(int i, boolean isLine) {
        if(isLine)
            paths[i] = follower.pathBuilder()
                    .addPath(new BezierLine(new Point(poses[i]), new Point(poses[i + 1])))
                    .setLinearHeadingInterpolation(poses[i].getHeading(), poses[i + 1].getHeading())
                    .build();
        else
            paths[i] = follower.pathBuilder()
                    .addPath(new BezierCurve(new Point(poses[i]), new Point(poses[i + 1])))
                    .setLinearHeadingInterpolation(poses[i].getHeading(), poses[i + 1].getHeading())
                    .build();

        return paths[i];
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", follower.getPose().getHeading());
        telemetry.addData("Busy", follower.isBusy());
        telemetry.update();
    }
}
