package org.firstinspires.ftc.teamcode.auton;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.ServoCommand;
import org.firstinspires.ftc.teamcode.commands.SetPIDFSlideArmCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.*;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;
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
public class Auton extends CommandOpMode {

    private DcMotor fl;
    private Follower follower;
    private Timer pathTimer;
    private int pathState;
    private PathChain generatedPath;
    public static PIDFSlideSubsystem slide;
    public static ServoSubsystem intakeClaw, outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawDist, blocker;
    public static IntakeSubsystem intake;
    public static LimitSwitchSubsystem vertical, horizontal;
    public static PIDFSingleSlideSubsystem hSlide;
    public static TelemetrySubsystem telemetrySubsystem;
    public static WaitSubsystem pause;
    public double block = 0.03, unblock = 0.12;

    @Override
    public void initialize() {
        pathTimer = new Timer();
        pathTimer.resetTimer();
        follower = new Follower(hardwareMap);
        intake = new IntakeSubsystem(hardwareMap, Constants.intake);
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.05, 0, 0.0007, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.005, 0,  0.0, 0.1, 0.005, 0, 0.0, 0.1);
        pause = new WaitSubsystem();
        outtakeClaw = new ServoSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Constants.intakeDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Constants.intakeRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Constants.outtakeDist);
        vertical = new LimitSwitchSubsystem(hardwareMap, "vSlide");
        horizontal = new LimitSwitchSubsystem(hardwareMap, "hSlide");
        blocker = new ServoSubsystem(hardwareMap, "servo6");
        outtakeClawRot = new ServoSubsystem(hardwareMap, Constants.outtakeRot);

        follower.setStartingPose(new Pose(0.504, 57.802, Math.toRadians(0)));
        buildPaths();
    }

    @Override
    public void run() {
        follower.update();
        autonomousPathUpdate();
        telemetry.addData("Path State", pathState);
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", follower.getPose().getHeading());
        telemetry.update();
    }

    public void buildPaths() {
        PathBuilder scorePreload = follower.pathBuilder();

        scorePreload
                .addPath(new BezierLine(
                        new Point(0.504, 57.802, Point.CARTESIAN),
                        new Point(22.319, 52.412, Point.CARTESIAN)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        generatedPath = scorePreload.build();
//                .addPath(new BezierLine(
//                        new Point(39.319, 71.412, Point.CARTESIAN),
//                        new Point(35.118, 34.278, Point.CARTESIAN)))
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
//                .addPath(new BezierLine(
//                        new Point(35.118, 34.278, Point.CARTESIAN),
//                        new Point(62.506, 34.446, Point.CARTESIAN)))
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
//                .addPath(new BezierLine(
//                        new Point(62.506, 34.446, Point.CARTESIAN),
//                        new Point(62.842, 27.053, Point.CARTESIAN)))
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
//                .addPath(new BezierLine(
//                        new Point(62.842, 27.053, Point.CARTESIAN),
//                        new Point(17.811, 26.716, Point.CARTESIAN)))
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
//                .addPath(new BezierLine(
//                        new Point(17.811, 26.716, Point.CARTESIAN),
//                        new Point(62.842, 27.221, Point.CARTESIAN)))
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
//                .addPath(new BezierLine(
//                        new Point(62.842, 27.221, Point.CARTESIAN),
//                        new Point(62.842, 16.803, Point.CARTESIAN)))
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
//                .addPath(new BezierLine(
//                        new Point(62.842, 16.803, Point.CARTESIAN),
//                        new Point(17.811, 17.139, Point.CARTESIAN)))
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
//                .addPath(new BezierLine(
//                        new Point(17.811, 17.139, Point.CARTESIAN),
//                        new Point(64.187, 8.065, Point.CARTESIAN)))
//                .setTangentHeadingInterpolation()
//                .addPath(new BezierLine(
//                        new Point(64.187, 8.065, Point.CARTESIAN),
//                        new Point(6.217, 8.401, Point.CARTESIAN)))
//                .setTangentHeadingInterpolation()
//                .addPath(new BezierLine(
//                        new Point(6.217, 8.401, Point.CARTESIAN),
//                        new Point(39.823, 75.949, Point.CARTESIAN)))
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
//                .addPath(new BezierLine(
//                        new Point(39.823, 75.949, Point.CARTESIAN),
//                        new Point(8.569, 24.028, Point.CARTESIAN)))
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(Math.PI))
//                .addPath(new BezierLine(
//                        new Point(8.569, 24.028, Point.CARTESIAN),
//                        new Point(38.982, 66.707, Point.CARTESIAN)))
//                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(generatedPath);
                new SequentialCommandGroup(
                        new ServoCommand(outtakeClaw, Constants.grab),
                        new ServoCommand(outtakeClawDist, Constants.distBasketPos),
                        new ServoCommand(outtakeClawRot, Constants.outtakeClawRotTransfer),
                        new SetPIDFSlideArmCommand(slide, 525)
                );
                pathState++;
                break;
            case 1:
        }
    }
}
