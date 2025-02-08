package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
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
public class AutoSpec extends OpMode {

    /** POSES
     * X - Forward Positive, Backwards Negative
     * Y - Left Positive, Right Negative
     */
    private final Pose[] poses = {
            new Pose(0, 0, Math.toRadians(0)),
            new Pose(-30, 0, Math.toRadians(0)),
            new Pose(-20, 30, Math.toRadians(180))
    };

    // Paths
    private Follower follower;
    private PathChain[] paths = new PathChain[poses.length - 1];

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
        outtakeClaw.set(Const.grab);
        intakeClawRot.set(.58);

        Command scorePreload =
                new SequentialCommandGroup(
                        new SlideResetCommand(hSlide, hLimit),
                        new ServoCommand(outtakeClawDistRight, .8),
                        new ServoCommand(outtakeClawDistLeft, .2),
                        new ServoCommand(outtakeClawRot, 1),
                        new ServoCommand(outtakeClawTwist, Const.twist),
                        new SetPIDFSlideArmCommand(slide, 4000),
                        new FollowPathCommand(follower, path(0, true), true),
                        new ServoCommand(outtakeClaw, Const.release)
                );

        Command sampsToHp =
                new SequentialCommandGroup(
                        new FollowPathCommand(follower, path(1, false), true),
                        new ServoCommand(outtakeClaw, Const.release),
                        new SlideResetCommand(slide, vLimit),
                        new ServoCommand(intakeClawRot, .3),
                        new SlideResetCommand(hSlide, hLimit),
                        new ServoCommand(outtakeClawTwist, Const.untwist),
                        new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrab),
                        new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrab),
                        new ServoCommand(outtakeClawRot, Const.rotSpecimenGrab),
                        new ServoCommand(outtakeClaw, Const.release)

                );

        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                scorePreload,
                sampsToHp
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
