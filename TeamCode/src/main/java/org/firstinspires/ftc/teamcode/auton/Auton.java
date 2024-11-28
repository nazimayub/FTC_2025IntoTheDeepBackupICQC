package org.firstinspires.ftc.teamcode.auton;

import org.firstinspires.ftc.teamcode.commands.SetPIDFSlideArmCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.*;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ServoSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WaitSubsystem;

public class Auton {
    public static Drive drive;
    public static ServoSubsystem intakeClaw, outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawDist, blocker;
    public static IntakeSubsystem intake;
    public static LimitSwitchSubsystem vertical, horizontal;
    public static PIDFSlideSubsystem slide;
    public static PIDFSingleSlideSubsystem hSlide;
    public static TelemetrySubsystem telemetrySubsystem;
    public static WaitSubsystem pause;

    public Auton() {
        PathBuilder builder = new PathBuilder();

        builder
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(1.700, 70.800, Point.CARTESIAN),
                                new Point(29.417, 70.695, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(29.417, 70.695, Point.CARTESIAN),
                                new Point(27.044, 122.175, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(27.044, 122.175, Point.CARTESIAN),
                                new Point(12.300, 132.200, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(12.300, 132.200, Point.CARTESIAN),
                                new Point(26.333, 131.901, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 5
                        new BezierLine(
                                new Point(26.333, 131.901, Point.CARTESIAN),
                                new Point(12.336, 132.138, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(12.336, 132.138, Point.CARTESIAN),
                                new Point(24.909, 140.916, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 7
                        new BezierLine(
                                new Point(24.909, 140.916, Point.CARTESIAN),
                                new Point(12.336, 132.376, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 8
                        new BezierLine(
                                new Point(12.336, 132.376, Point.CARTESIAN),
                                new Point(9.015, 22.537, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation();
    }
}
