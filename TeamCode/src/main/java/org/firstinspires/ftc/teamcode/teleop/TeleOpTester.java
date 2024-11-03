package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
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
    public static PIDFSingleSlideSubsystem hSlide;
    public static HandSubsystem intakeClaw;
    public static HandSubsystem outtakeClaw;
    public static HandSubsystem intakeClawDist;
    public static HandSubsystem intakeClawRot;
    public static HandSubsystem outtakeClawDist;
    public static WaitSubsystem pause;

    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);
        log = new SimpleLogger();
        drive = new Drive(hardwareMap, Constants.imu,new MotorConfig(Constants.fr,Constants.fl,Constants.br,Constants.bl),new MotorDirectionConfig(false,true,false,true));
        vSlides = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, .03, 0, .001, .01, .03, 0, .001, .01);
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, .002, 0, 0, 0);
        intakeClaw = new HandSubsystem(hardwareMap, Constants.intakeClaw);
        outtakeClaw = new HandSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new HandSubsystem(hardwareMap, Constants.intakeClawDist);
        intakeClawRot = new HandSubsystem(hardwareMap, Constants.intakeClawRot);
        outtakeClawDist = new HandSubsystem(hardwareMap, Constants.outtakeClawDist);
        pause = new WaitSubsystem();

        drive.setDefaultCommand(new DriveCommand(drive,base));

        //intake
        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new HandCommand(intakeClaw, Constants.intClawGrab))
                .whenPressed(new HandCommand(intakeClawRot, Constants.intClawIn))
                .whenPressed(new HandCommand(intakeClawDist, Constants.intStow)
        );

        //outtake basket
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new HandCommand(outtakeClaw, Constants.transfer))
                .whenPressed(new HandCommand(outtakeClawDist, Constants.basket)
        );

        //outtake basket
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new HandCommand(outtakeClaw, Constants.transfer))
                .whenPressed(new HandCommand(intakeClawRot, Constants.specimen)
        );

        //stow
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new HandCommand(intakeClawDist, Constants.intStow))
                .whenPressed(new HandCommand(outtakeClawDist, Constants.outStow)
        );
    }
}
