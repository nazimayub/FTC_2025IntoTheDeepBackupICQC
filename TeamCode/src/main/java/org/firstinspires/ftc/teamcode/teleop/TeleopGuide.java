package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utils.*;
@TeleOp
public class TeleopGuide extends CommandOpMode {
    //Declare Gamepads
    GamepadEx base;
    //Declare all subsystem
    SimpleLogger log;
    public static IntakeSubsystem intake;
    public static Drive drive;
    public static DroneSubsystem drone;
    public static HandSubsystem hand;
    public static SlideSubsystem slide;
    public static TransferSubsystem transfer;
    public static TelemetrySubsystem telemetrySubsystem;
    @Override
    public void initialize() {
        //Initialize Gamepads
        base = new GamepadEx(gamepad1);
        //Initialize Subsystems
        log = new SimpleLogger();
        intake = new IntakeSubsystem(hardwareMap, Constants.intake);
        drive = new Drive(hardwareMap, Constants.imu,new MotorConfig(Constants.fr,Constants.fl,Constants.br,Constants.bl),new MotorDirectionConfig(false,true,false,true));
        drone = new DroneSubsystem(hardwareMap, Constants.drone);
        hand = new HandSubsystem(hardwareMap, Constants.hand);
        slide = new SlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD);
        transfer = new TransferSubsystem(hardwareMap, Constants.transfer);
        telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());


        //Set Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));
        intake.setDefaultCommand(new IntakeCommand(intake, 0));
        drone.setDefaultCommand(new DroneCommand(drone, Constants.load));
        hand.setDefaultCommand(new HandCommand(hand, Constants.in));
        slide.setDefaultCommand(new SlideArmCommand(slide, base));

        //Bind Commands
        new GamepadButton(base, GamepadKeys.Button.A).toggleWhenPressed(new HandCommand(hand, Constants.out), new HandCommand(hand, Constants.in));
        new GamepadButton(base, GamepadKeys.Button.B).toggleWhenPressed(new DroneCommand(drone, Constants.launch), new DroneCommand(drone, Constants.load));
        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new IntakeCommand(intake, 1)).whenPressed(new TransferCommand(transfer, -1)).whenReleased(new IntakeCommand(intake, 0)).whenReleased(new TransferCommand(transfer, 0));
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER).whenPressed(new IntakeCommand(intake, -1)).whenReleased(new IntakeCommand(intake, 0));

        //Log
        telemetrySubsystem.addLogHeadings();

        //Add Logs
        schedule(new RunCommand(telemetrySubsystem::addTelemetryData));
        schedule(new RunCommand(telemetrySubsystem::addDashBoardData));

        //Update logging
        schedule(new RunCommand(telemetrySubsystem::updateDashboardTelemetry));
        schedule(new RunCommand(telemetrySubsystem::updateLogs));
        schedule(new RunCommand(telemetry::update));
    }

}
