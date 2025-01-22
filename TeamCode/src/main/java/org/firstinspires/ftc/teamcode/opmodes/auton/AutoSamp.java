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

@Autonomous
public class AutoSamp extends OpMode {
    public static ServoSubsystem outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawDist, blocker;
    public static IntakeAutoSubsystem intake;
    public static LimitSwitchSubsystem vLimit, hLimit;
    public static PIDFSlideSubsystem slide;
    public static PIDFSingleSlideSubsystem hSlide;
    public static WaitSubsystem pause;

    //Paths
    private Follower follower;
    private Timer pathTimer;
    private PathChain path1, path2, path3, path4, path5;

    //Poses
    private final Pose pose0 = new Pose(0, 0, Math.toRadians(0));
    private final Pose pose1 = new Pose(5, 28, Math.toRadians(130));
    private final Pose pose2 = new Pose(5, 28, Math.toRadians(60));

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);

        intake = new IntakeAutoSubsystem(hardwareMap, Const.intake, new ElapsedTime());
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Const.hSlide, 0.05, 0, 0.0007, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.005, 0,  0.0, 0.1, 0.005, 0, 0.0, 0.1);
        pause = new WaitSubsystem();
        outtakeClaw = new ServoSubsystem(hardwareMap, Const.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Const.intakeDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Const.intakeRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Const.outtakeDistRight);
        vLimit = new LimitSwitchSubsystem(hardwareMap, Const.vLimit);
        hLimit = new LimitSwitchSubsystem(hardwareMap, Const.hLimit);
        blocker = new ServoSubsystem(hardwareMap, Const.gearShifter);
        outtakeClawRot = new ServoSubsystem(hardwareMap, Const.outtakeRot);

        follower.setStartingPose(pose0);
        path();

        CommandScheduler.getInstance().schedule(
                //Score Preload
                new SequentialCommandGroup(
                        new FollowPathCommand(follower, path1.getPath(0),3000),
                        new ServoCommand(outtakeClawRot, Const.rotBasketPos),
                        new ServoCommand(outtakeClawDist, Const.outtakeClawDistFinalTransfer),
                        new SetPIDFSlideArmCommand(slide, 2750),
                        new ServoCommand(outtakeClaw, Const.release),
                        new FollowPathCommand(follower, path2.getPath(0), 3000)
                ),

                //Score first sample
                new SequentialCommandGroup(
                        new ServoCommand(blocker, Const.unblock),
                        new WaitCommand(pause, 300),
                        new SetPIDFSlideArmCommand(hSlide, 400),
                        new ServoCommand(intakeClawRot, Const.intakeDownPos),
                        new IntakeAutoCommand(intake, -1, 3),
                        new ServoCommand(intakeClawRot, Const.intakeInitTransferPos),
                        new ServoCommand(outtakeClawRot, Const.outtakeClawRotTransfer),
                        new ServoCommand(outtakeClawDist, Const.outtakeClawDistTempTransfer),
                        new SlideResetCommand(slide, vLimit),
                        new SlideResetCommand(hSlide, hLimit),
                        new ServoCommand(blocker, Const.block),
                        new ServoCommand(outtakeClaw, Const.release),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClawDist, Const.outtakeClawDistRightInitTransfer),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, Const.intakeFinalTransferPos),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClaw, Const.grab),
                        new WaitCommand(pause, 300),
                        new ServoCommand(intakeClawRot, Const.intakeSecondFinalTransferPos),
                        new WaitCommand(pause, 300),
                        new ServoCommand(outtakeClawDist, Const.outtakeClawDistFinalTransfer),
                        new ServoCommand(outtakeClawRot, Const.rotBasketPos),
                        new ServoCommand(outtakeClawRot, Const.rotBasketPos),
                        new ServoCommand(outtakeClawDist, Const.outtakeClawDistFinalTransfer),
                        new SetPIDFSlideArmCommand(slide, 2750),
                        new WaitCommand(pause, 2000),
                        new ServoCommand(outtakeClaw, Const.release)
                )
        );
    }

    public void path() {
        path1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pose0), new Point(pose1)))
                .setLinearHeadingInterpolation(pose0.getHeading(), pose1.getHeading())
                .build();

        path2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pose1), new Point(pose2)))
                .setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading())
                .build();
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
