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
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.002, 0, 0, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0.03, 0, 0.001, 0.01, 0.03, 0, 0.001, 0.01);
//      telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());
        pause = new WaitSubsystem();
        intakeClaw = new ServoSubsystem(hardwareMap, Constants.intakeClaw);
        outtakeClaw = new ServoSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Constants.intakeClawDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Constants.intakeClawRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Constants.outtakeClawDist);


        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));

        //Bring intake down
        new GamepadButton(base, GamepadKeys.Button.A).whenPressed(new SequentialCommandGroup(
                new ServoCommand(intakeClawDist, 0.233),
                new WaitCommand(pause, 5000),
                new ServoCommand(intakeClawRot, 0.865)
                //new WaitCommand(pause, 1000),
                //new ServoCommand(intakeClawRot, 1)

        ));


        //Grabs sample Stows intake and transfers
        new GamepadButton(base, GamepadKeys.Button.Y).whenPressed(new SequentialCommandGroup(
                new ServoCommand(intakeClaw, 0.64),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDist, 0.682),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClaw, 0.343),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, 0.5),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawDist, 0.623),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, 0.14),
                new WaitCommand(pause, 300),
                //Retract slides
                new ServoCommand(outtakeClaw, 0.533),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDist, 0.344)
        ));


        //Scores
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER).whenPressed(new ServoCommand(outtakeClaw, 0.3));
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
