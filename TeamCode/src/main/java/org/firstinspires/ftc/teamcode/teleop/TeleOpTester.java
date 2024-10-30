package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.network.SendOnceRunnable;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utils.*;

@TeleOp
public class TeleOpTester extends CommandOpMode {
    GamepadEx base;
    GamepadEx op;
    SimpleLogger log;
    public static Drive drive;
    public static TelemetrySubsystem telemetrySubsystem;
    public static PIDFSlideSubsystem vSlides;
    public static PIDFSlideSubsystem hSlides;
    public static ServoIntakeSubsystem intakeClaw;
    public static ServoIntakeSubsystem outtakeClaw;
    public static ServoIntakeSubsystem intakeClawDist;
    public static ServoIntakeSubsystem intakeClawRot;
    public static ServoIntakeSubsystem outtakeClawDist;


    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);
        log = new SimpleLogger();
        intakeClaw = new ServoIntakeSubsystem(hardwareMap, Constants.intakeClaw);
        outtakeClaw = new ServoIntakeSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new ServoIntakeSubsystem(hardwareMap, Constants.intakeClawDist);
        intakeClawRot = new ServoIntakeSubsystem(hardwareMap, Constants.intakeClawRot);
        outtakeClawDist = new ServoIntakeSubsystem(hardwareMap, Constants.outtakeClawDist);

        drive.setDefaultCommand(new DriveCommand(drive,base));

        new GamepadButton(base, GamepadKeys.Button.A).toggleWhenPressed(new ServoIntakeCommand(intakeClaw, Constants.grabInt), new ServoIntakeCommand(intakeClaw, Constants.relInt));
        new GamepadButton(base, GamepadKeys.Button.B).toggleWhenPressed(new ServoIntakeCommand(outtakeClaw, Constants.grabOut), new ServoIntakeCommand(outtakeClaw, Constants.relOut));
        new GamepadButton(base, GamepadKeys.Button.X).toggleWhenPressed(new ServoIntakeCommand(intakeClawDist, Constants.intStowIn), new ServoIntakeCommand(intakeClawDist, Constants.intStowOut));
        new GamepadButton(base, GamepadKeys.Button.Y).toggleWhenPressed(new ServoIntakeCommand(intakeClawRot, Constants.clawIn), new ServoIntakeCommand(intakeClawDist, Constants.clawOut));
        new GamepadButton(base, GamepadKeys.Button.DPAD_UP).toggleWhenPressed(new ServoIntakeCommand(outtakeClawDist, Constants.outStowIn), new ServoIntakeCommand(outtakeClawDist, Constants.outStowOut));

    }
}
