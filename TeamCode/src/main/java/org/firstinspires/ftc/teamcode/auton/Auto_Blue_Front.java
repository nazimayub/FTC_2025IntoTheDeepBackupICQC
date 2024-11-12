package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;
import org.firstinspires.ftc.teamcode.utils.SimpleLogger;

@Autonomous
public class Auto_Blue_Front extends CommandOpMode {
    public static class Params {
        public double originx = 36;
        public double originy = 12;
        public double initialHeading = 180;
    }

    SimpleLogger log;
    public static ServoIntakeSubsystem intake;
    public static Drive drive;
    public static SparkFunOTOSDrive sparkDrive;
    public static HandSubsystem hand;
    public static LimitSwitchSubsystem vertical, horizontal;
    public static PIDFSlideSubsystem slide;
    public static TelemetrySubsystem telemetrySubsystem;
    public static PIDFSingleSlideSubsystem hSlide;
    public static ServoSubsystem intakeClaw;
    public static ServoSubsystem outtakeClaw;
    public static ServoSubsystem intakeClawDist;
    public static ServoSubsystem intakeClawRot;
    public static ServoSubsystem outtakeClawDist;
    public static WaitSubsystem pause;
    public static ServoSubsystem blocker;
    public double block = 0.03, unblock = 0.12;
    public static Params PARAMS = new Params();

    @Override
    public void initialize() {
        sparkDrive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(36, 62, 180));
        log = new SimpleLogger();

        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.05, 0.1, 0.0007, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0.06, 0, 0.001, 0.01, 0.06, 0, 0.001, 0.01);
        telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());
        intakeClaw = new ServoSubsystem(hardwareMap, Constants.intakeClaw);
        outtakeClaw = new ServoSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Constants.intakeClawDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Constants.intakeClawRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Constants.outtakeClawDist);
        vertical = new LimitSwitchSubsystem(hardwareMap, "vSlide");
        horizontal = new LimitSwitchSubsystem(hardwareMap, "hSlide");
        blocker = new ServoSubsystem(hardwareMap, "servo6");
        pause = new WaitSubsystem();

        waitForStart();


        while (opModeIsActive()) {
            telemetry.addData("Position", sparkDrive.pose);
            telemetry.addData("Heading", drive.getHeading());
            telemetry.update();
        }
    }
}