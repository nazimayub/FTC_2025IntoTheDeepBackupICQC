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
import org.firstinspires.ftc.teamcode.subsystems.IntakeAutoSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ServoSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WaitSubsystem;

import java.util.ArrayList;

@Autonomous(group = "Auton")
public class AutoSamp extends OpMode {
    public static Pose score = new Pose(14.95, -0.82, 3.86);
    public enum AutoPaths {
        PRELOAD(
                new Pose( 0, 0, 0),
                score
        ),

        GRAB_SAMPLE_1(
                score,
                new Pose(14.47, -2.48, 4.41)
        ),

        SCORE_SAMPLE_1(
                new Pose(14.47, -2.48, 4.41),
                score
        ),

        GRAB_SAMPLE_2(
                score,
                new Pose(14.8, -4.56, 4.72)
        ),

        SCORE_SAMPLE_2(
                new Pose(14.8, -4.56, 4.72),
                score
        ),

        GRAB_SAMPLE_3(
                score,
                new Pose(14.55, -7.05, 5.16)
        ),

        SCORE_SAMPLE_3(
                new Pose(14.55, -7.05, 5.16),
                score
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

    // Paths
    private Follower follower;

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
        slide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.1, 0, 0.000004, 0.21, 0.1, 0, 0.000004, 0.21);
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

        follower.setPose(AutoPaths.PRELOAD.getPoses()[0]);
        outtakeClaw.set(Const.grab);
        intakeClawRot.set(.58);

        Command scorePreload =
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new ServoCommand(outtakeClaw, Const.grab),
                                new ServoCommand(outtakeClawRot, 0.9),
                                new ServoCommand(outtakeClawDistRight, 1-0.378),
                                new ServoCommand(outtakeClawDistLeft, 0.378),
                                new FollowPathCommand(follower, AutoPaths.PRELOAD.curve(follower), true),
                                new SetPIDFSlideArmCommand(slide, 1150)
                        ),
                        new ServoCommand(outtakeClawRot, 0.6),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClaw, Const.release),
                        new WaitCommand(pause, 300),
                        new ParallelCommandGroup(
                                new ServoCommand(intakeClawRot, .3),
                                new ServoCommand(outtakeClawDistLeft, 1),
                                new ServoCommand(outtakeClawDistRight, 0),
                                new ServoCommand(outtakeClawRot, 0.7),
                                new ServoCommand(outtakeClawTwist, 0.924)
                        )
                );

        Command scoreFirstSamp =
                new SequentialCommandGroup(
                        new FollowPathCommand(follower, AutoPaths.GRAB_SAMPLE_1.curve(follower), true),
                        new ServoCommand(intakeClawRot, 0.5),
                        new SetPIDFSlideArmCommand(hSlide, -350),
                        new ServoCommand(intakeClawRot, .12),
                        new SlideResetCommand(slide, vLimit),
                        new ParallelCommandGroup(
                                new SetPIDFSlideArmCommand(hSlide, -800),
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
                        new ServoCommand(outtakeClaw, Const.grab - .3),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, .3),
                        new SetPIDFSlideArmCommand(slide, 200),
                        new ServoCommand(outtakeClawRot, 0.9),
                        new ServoCommand(outtakeClawDistRight, 1-0.378),
                        new ServoCommand(outtakeClawDistLeft, 0.378),
                        new ParallelCommandGroup(
                                new FollowPathCommand(follower, AutoPaths.SCORE_SAMPLE_1.line(follower), true),
                                new SetPIDFSlideArmCommand(slide, 1200)
                        ),
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
                        new FollowPathCommand(follower, AutoPaths.GRAB_SAMPLE_2.curve(follower), true),
                        new ServoCommand(intakeClawRot, 0.5),
                        new SetPIDFSlideArmCommand(hSlide, -350),
                        new ServoCommand(intakeClawRot, .12),
                        new SlideResetCommand(slide, vLimit),
                        new ParallelCommandGroup(
                                new SetPIDFSlideArmCommand(hSlide, -800),
                                new IntakeAutoCommand(intake, -0.8, 2)
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
                        new ServoCommand(outtakeClaw, Const.grab - .3),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, .3),
                        new SetPIDFSlideArmCommand(slide, 200),
                        new ServoCommand(outtakeClawRot, 0.9),
                        new ServoCommand(outtakeClawDistRight, 1-0.378),
                        new ServoCommand(outtakeClawDistLeft, 0.378),
                        new ParallelCommandGroup(
                                new FollowPathCommand(follower, AutoPaths.SCORE_SAMPLE_2.line(follower), true),
                                new SetPIDFSlideArmCommand(slide, 1200)
                        ),
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
                        new FollowPathCommand(follower, AutoPaths.GRAB_SAMPLE_3.curve(follower), true),
                        new ServoCommand(intakeClawRot, 0.5),
                        new SetPIDFSlideArmCommand(hSlide, -350),
                        new ServoCommand(intakeClawRot, .12),
                        new SlideResetCommand(slide, vLimit),
                        new ParallelCommandGroup(
                                new SetPIDFSlideArmCommand(hSlide, -800),
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
                        new ServoCommand(outtakeClaw, Const.grab - .3),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, .3),
                        new SetPIDFSlideArmCommand(slide, 200),
                        new ServoCommand(outtakeClawRot, 0.9),
                        new ServoCommand(outtakeClawDistRight, 1-0.378),
                        new ServoCommand(outtakeClawDistLeft, 0.378),
                        new ParallelCommandGroup(
                                new FollowPathCommand(follower, AutoPaths.SCORE_SAMPLE_3.line(follower), true),
                                new SetPIDFSlideArmCommand(slide, 1200)
                        ),
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

        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                scorePreload,
                scoreFirstSamp,
                scoreSecondSamp,
                scoreThirdSamp
        ));
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
