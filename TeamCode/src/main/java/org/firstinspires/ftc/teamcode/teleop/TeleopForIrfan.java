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
    public static MultiServoSubsystem servos;
    public static WaitSubsystem pause;
    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);
        log = new SimpleLogger();
        drive = new Drive(hardwareMap, Constants.imu,new MotorConfig(Constants.fr,Constants.fl,Constants.br,Constants.bl),new MotorDirectionConfig(true,true,true,true));
        //hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.002, 0, 0, 0);
        //slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0.03, 0, 0.001, 0.01, 0.03, 0, 0.001, 0.01);
//      telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());
        pause = new WaitSubsystem();
        servos = new MultiServoSubsystem(hardwareMap, 5, Constants.intakeClaw, Constants.outtakeClaw, Constants.intakeClawDist, Constants.intakeClawRot, Constants.outtakeClawDist);


        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));

        //Bring intake down
        new GamepadButton(base, GamepadKeys.Button.A).whenPressed(new SequentialCommandGroup(
                new MultiServoCommand(servos,3,0.65),
                new WaitCommand(pause, 500),
                new MultiServoCommand(servos, 2, 0.16),
                new WaitCommand(pause, 1000),
                new MultiServoCommand(servos, 3, 1)

        ));

        //Grabs sample Stows intake and transfers
        new GamepadButton(base, GamepadKeys.Button.Y).whenPressed(new SequentialCommandGroup(
                new MultiServoCommand(servos, 0, 0.64),
                new WaitCommand(pause, 300),
                new MultiServoCommand(servos, 4, 0.682),
                new WaitCommand(pause, 300),
                new MultiServoCommand(servos, 1, 0.343),
                new WaitCommand(pause, 300),
                new MultiServoCommand(servos, 3, 0.231),
                new WaitCommand(pause, 300),
                new MultiServoCommand(servos, 2, 0.623),
                new WaitCommand(pause, 300),
                new MultiServoCommand(servos, 3, 0.006),
                new WaitCommand(pause, 300),
                //Retract slides
                new MultiServoCommand(servos, 1, 0.533),
                new WaitCommand(pause, 300),
                new MultiServoCommand(servos, 4, 0.344)
        ));

        //Brings intake up
        new GamepadButton(base, GamepadKeys.Button.Y).whenPressed(new SequentialCommandGroup(
                new MultiServoCommand(servos, 0, 0.5),
                new WaitCommand(pause, 300),
                new MultiServoCommand(servos, 3, 0.233),
                new WaitCommand(pause, 300),
                new MultiServoCommand(servos, 0, 0.865),
                new WaitCommand(pause, 300),
                new MultiServoCommand(servos, 1, 0.0013)
        ));

        //Scores
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER).whenPressed(new MultiServoCommand(servos,1, 0.3));

        //Intakes
        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new SequentialCommandGroup(
                new MultiServoCommand(servos, 3, 0.168),
                new WaitCommand(pause, 300),
                new MultiServoCommand(servos, 1, 0.668)
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
