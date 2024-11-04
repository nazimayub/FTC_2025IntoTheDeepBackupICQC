package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.internal.network.SendOnceRunnable;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utils.*;
@TeleOp
public class TeleopForIrfan extends CommandOpMode {
    GamepadEx base;
    GamepadEx op;
    SimpleLogger log;
    public static ServoIntakeSubsystem intake;
    public static Drive drive;
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
    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);
        log = new SimpleLogger();
        drive = new Drive(hardwareMap, Constants.imu,new MotorConfig(Constants.fr,Constants.fl,Constants.br,Constants.bl),new MotorDirectionConfig(true,true,true,true));
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.04, 0, 0, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0.04, 0, 0.001, 0.01, 0.04, 0, 0.001, 0.01);
//      telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());
        pause = new WaitSubsystem();
        intakeClaw = new ServoSubsystem(hardwareMap, Constants.intakeClaw);
        outtakeClaw = new ServoSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Constants.intakeClawDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Constants.intakeClawRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Constants.outtakeClawDist);
        vertical = new LimitSwitchSubsystem(hardwareMap, "vSlide");
        horizontal = new LimitSwitchSubsystem(hardwareMap, "hSlide");

        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));
        hSlide.setDefaultCommand(new SlideArmCommand(hSlide, base));

        //Bring intake down
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER).whenPressed(new SequentialCommandGroup(
                new SetPIDFSlideArmCommand(hSlide, 400),
                new ServoCommand(intakeClawDist, 0.233),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, 0.865),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClaw, 0.44)
        ));


        //Grabs sample Stows intake and transfers
        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new SequentialCommandGroup(
                new SlideResetCommand(slide, vertical),
                new ServoCommand(intakeClaw, 0.668),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDist, 0.682),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClaw, 0.343),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, 0.5),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawDist, 0.623),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, 0.05),
                new WaitCommand(pause, 300),
                new SlideResetCommand(hSlide, horizontal),
                new ServoCommand(outtakeClaw, 0.533),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClaw, 0.44),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDist, 0.344)
        ));

        //Go to high basket
        //Scores
        new GamepadButton(base, GamepadKeys.Button.X).whenPressed(new SetPIDFSlideArmCommand(slide, 3000));

        //Scores
        new GamepadButton(base, GamepadKeys.Button.Y).whenPressed(new SequentialCommandGroup(
                new SetPIDFSlideArmCommand(slide, 200),
                new ServoCommand(outtakeClaw, 0.3)
        ));

        //Intakes
        new GamepadButton(base, GamepadKeys.Button.A).whenPressed(new SequentialCommandGroup(
                new ServoCommand(intakeClawDist, 0.168),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClaw, 0.668)

        ));
    }
    // logs stuffs

//        telemetrySubsystem.addLogHeadings();

    // add logs

//        schedule(new RunCommand(telemetrySubsystem::addTelemetryData));
//        schedule(new RunCommand(telemetrySubsystem::addDashBoardData));
//
//        // update logging stuffs
//
//        schedule(new RunCommand(telemetrySubsystem::updateDashboardTelemetry));
//        schedule(new RunCommand(telemetrySubsystem::updateLogs));
//        schedule(new RunCommand(telemetry::update));
}
