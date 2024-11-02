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
    public static HandSubsystem intakeClaw;
    public static HandSubsystem outtakeClaw;
    public static HandSubsystem intakeClawDist;
    public static HandSubsystem intakeClawRot;
    public static HandSubsystem outtakeClawDist;


    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);
        log = new SimpleLogger();
        intakeClaw = new HandSubsystem(hardwareMap, Constants.intakeClaw);
        outtakeClaw = new HandSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new HandSubsystem(hardwareMap, Constants.intakeClawDist);
        intakeClawRot = new HandSubsystem(hardwareMap, Constants.intakeClawRot);
        outtakeClawDist = new HandSubsystem(hardwareMap, Constants.outtakeClawDist);

        //drive.setDefaultCommand(new DriveCommand(drive,base));

        new GamepadButton(base, GamepadKeys.Button.A).toggleWhenPressed(new HandCommand(intakeClaw, Constants.grabInt), new HandCommand(intakeClaw, Constants.relInt));
        new GamepadButton(base, GamepadKeys.Button.B).toggleWhenPressed(new HandCommand(outtakeClaw, Constants.grabOut), new HandCommand(outtakeClaw, Constants.relOut));
        new GamepadButton(base, GamepadKeys.Button.X).toggleWhenPressed(new HandCommand(intakeClawDist, Constants.intStowIn), new HandCommand(intakeClawDist, Constants.intStowOut));
        new GamepadButton(base, GamepadKeys.Button.Y).toggleWhenPressed(new HandCommand(intakeClawRot, Constants.clawIn), new HandCommand(intakeClawDist, Constants.clawOut));
        new GamepadButton(base, GamepadKeys.Button.DPAD_UP).toggleWhenPressed(new HandCommand(outtakeClawDist, Constants.outStowIn), new HandCommand(outtakeClawDist, Constants.outStowOut));

    }
}