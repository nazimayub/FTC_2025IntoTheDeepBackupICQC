package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
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
public class Solo extends CommandOpMode {
    GamepadEx base;
    SimpleLogger log;
    public static ServoIntakeSubsystem intake;
    public static Drive drive;
    public static HandSubsystem hand;
    public static SlideSubsystem slide;
    public static TelemetrySubsystem telemetrySubsystem;
    public static ServoArmSubsystem arm;
    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        log = new SimpleLogger();
        intake = new ServoIntakeSubsystem(hardwareMap, Constants.intake);
        drive = new Drive(hardwareMap, Constants.imu,new MotorConfig(Constants.fr,Constants.fl,Constants.br,Constants.bl),new MotorDirectionConfig(true,true,true,true));
        arm = new ServoArmSubsystem(hardwareMap, Constants.arm);
        hand = new HandSubsystem(hardwareMap, Constants.hand);
        slide = new SlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD);
//        telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());


        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));
        intake.setDefaultCommand(new ServoIntakeCommand(intake, 0));
        hand.setDefaultCommand(new HandCommand(hand, Constants.in));
        slide.setDefaultCommand(new SlideArmCommand(slide, base));


        //Binding Commands
        new GamepadButton(base, GamepadKeys.Button.A).toggleWhenPressed(new HandCommand(hand, Constants.out), new HandCommand(hand, Constants.in)).toggleWhenPressed(new ServoArmCommand(arm, Constants.up), new ServoArmCommand(arm, Constants.down));
        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new ServoIntakeCommand(intake, 1)).whenReleased(new ServoIntakeCommand(intake, 0));
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER).whenPressed(new ServoIntakeCommand(intake, -1)).whenReleased(new ServoIntakeCommand(intake, 0));
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

}
