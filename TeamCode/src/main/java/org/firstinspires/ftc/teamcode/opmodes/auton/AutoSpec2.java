package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Autonomous
public class AutoSpec2 extends OpMode {
    public enum AutoPaths {
        PRELOAD(
                new Pose(0, 0, Math.toRadians(180)),
                new Pose(32, 6, Math.toRadians(180))
        ),

        GO_TO_SAMPLES(
                new Pose(20, 0, Math.toRadians(180)),
                new Pose(20, -40, Math.toRadians(0)),
                new Pose(45, -40, Math.toRadians(0))
        ),

        PUSH_SAMPLE_1(
                new Pose(45, -47, Math.toRadians(0)),
                new Pose(10, -47, Math.toRadians(0))
        ),

        PUSH_SAMPLE_2(
                new Pose(45, -47, Math.toRadians(0)),
                new Pose(45, -57, Math.toRadians(0)),
                new Pose(10, -57, Math.toRadians(0))
        ),

        PUSH_SAMPLE_3(
                new Pose(45, -57, Math.toRadians(0)),
                new Pose(45, -64, Math.toRadians(0)),
                new Pose(10, -64, Math.toRadians(0))
        ),

        WHAT_IS_TS_FOR(
                new Pose(4, -33, Math.toRadians(0)),
                new Pose(-2, -33, Math.toRadians(0))
        ),

        GO_TO_GRAB(
                new Pose(33, 4, Math.toRadians(0))
        ),

        SCORE_SAMPLE_1(
                new Pose(4, -33, Math.toRadians(0)),
                new Pose(-2, -33, Math.toRadians(0)),
                new Pose(32, 2, Math.toRadians(0))
        ),

        SCORE_SAMPLE_2(
                new Pose(4, -33, Math.toRadians(0)),
                new Pose(-2, -33, Math.toRadians(0)),
                new Pose(32, 0, Math.toRadians(0))
        ),

        SCORE_SAMPLE_3(
                new Pose(4, -33, Math.toRadians(0)),
                new Pose(-2, -33, Math.toRadians(0)),
                new Pose(32, -2, Math.toRadians(0))
        );

        private final Pose[] poses;

        AutoPaths(Pose... poses) {
            this.poses = poses;
        }

        public PathChain lineTo(Follower follower) {
            PathChain path = follower.pathBuilder()
                    .addPath(new BezierLine(new Point(poses[0]), new Point(poses[1])))
                    .setLinearHeadingInterpolation(poses[0].getHeading(), poses[1].getHeading())
                    .build();
            return path;
        }

        public PathChain curveTo(Follower follower) {
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

        follower.setPose(AutoPaths.PRELOAD.getPoses()[0]);
        outtakeClaw.set(Const.grab);
        intakeClawRot.set(.58);
        slide.reset();

        Command scorePreload =
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new SlideResetCommand(hSlide, hLimit),
                                new ServoCommand(outtakeClawDistRight, .8),
                                new ServoCommand(outtakeClawDistLeft, .2),
                                new ServoCommand(outtakeClawRot, 1),
                                new ServoCommand(outtakeClawTwist, Const.twist),
                                new SetPIDFSlideArmCommand(slide, 4000),
                                new FollowPathCommand(follower, AutoPaths.PRELOAD.lineTo(follower), true, 1)
                        ),
                        new ServoCommand(outtakeClaw, Const.release)
                );


        Command sampsToHp =
                new SequentialCommandGroup(
                        new FollowPathCommand(follower, lineTo(), true, 1),
                        new SlideResetCommand(slide, vLimit),
                        new FollowPathCommand(follower, AutoPaths.GO_TO_SAMPLES.curveTo(follower), true, 1),
                        new ParallelCommandGroup(
                                new ServoCommand(intakeClawRot, .3),
                                new SlideResetCommand(hSlide, hLimit),
                                new ServoCommand(outtakeClawTwist, Const.untwist),
                                new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrab),
                                new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrab),
                                new ServoCommand(outtakeClawRot, Const.rotSpecimenGrab),
                                new ServoCommand(outtakeClaw, Const.release)
                        ),
                        new FollowPathCommand(follower, AutoPaths.PUSH_SAMPLE_1.lineTo(follower), true, 1),
                        new FollowPathCommand(follower, AutoPaths.PUSH_SAMPLE_2.curveTo(follower), true, 1),
                        new FollowPathCommand(follower, AutoPaths.PUSH_SAMPLE_3.curveTo(follower), true, 1),
                        new FollowPathCommand(follower, AutoPaths.WHAT_IS_TS_FOR.lineTo(follower), true, 1),
                        new FollowPathCommand(follower, AutoPaths.GO_TO_GRAB.lineTo(follower), true, 1),
                        new FollowPathCommand(follower, AutoPaths.SCORE_SAMPLE_1.curveTo(follower), true, 1),
                        new FollowPathCommand(follower, AutoPaths.SCORE_SAMPLE_2.curveTo(follower), true, 1),
                        new FollowPathCommand(follower, AutoPaths.SCORE_SAMPLE_3.curveTo(follower), true, 1),
                        new FollowPathCommand(follower, lineTo(poses[10], poses[11]), true, 1)
                );

        Command grabAndScore1 = grabAndScore(12);
        Command grabAndScore2 = grabAndScore(15);
        Command grabAndScore3 = grabAndScore(18);
        Command grabAndScore4 = grabAndScore(21);

        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                scorePreload,
                sampsToHp,
                grabAndScore1,
                grabAndScore2,
                grabAndScore3,
                grabAndScore4
        ));
    }

    public Command grabAndScore(int i) {
        return new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new ServoCommand(outtakeClawTwist, Const.untwist),
                        new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrab),
                        new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrab),
                        new ServoCommand(outtakeClawRot, Const.rotSpecimenGrab),
                        new ServoCommand(outtakeClaw, Const.release)
                ),
                new FollowPathCommand(follower, lineTo(poses[i], poses[i + 1]), true, 1),
                new WaitCommand(pause, 500),
                new FollowPathCommand(follower, lineTo(poses[i + 1], poses[i + 2]), true, 1),
                new WaitCommand(pause, 500),
                new ServoCommand(outtakeClaw, Const.grab),
                new WaitCommand(pause, 500),
                new ParallelCommandGroup(
                        new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrabFinal),
                        new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrabFinal),
                        new ServoCommand(outtakeClawRot, Const.rotSpecimenScore),
                        new ServoCommand(outtakeClawTwist, Const.twist),
                        new SetPIDFSlideArmCommand(slide, 7000)
                ),
                new FollowPathCommand(follower, lineTo(poses[i], poses[i + 3]), true, 1),
                new ParallelCommandGroup(
                        new ServoCommand(outtakeClaw, Const.release),
                        new SlideResetCommand(slide, vLimit)
                )
        );
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
