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
public class TeleopForIrfan extends CommandOpMode {
    GamepadEx base;
    GamepadEx op;
    SimpleLogger log;
    public static ServoIntakeSubsystem intake;
    public static Drive drive;
    public static HandSubsystem hand;
    //public static PIDFSlideSubsystem slide;
    public static PIDFSlideSubsystem slide;
    public static TelemetrySubsystem telemetrySubsystem;
    public static PIDFArmSubsystem arm;
    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);
        log = new SimpleLogger();
        intake = new ServoIntakeSubsystem(hardwareMap, Constants.intake);
        drive = new Drive(hardwareMap, Constants.imu,new MotorConfig(Constants.fr,Constants.fl,Constants.br,Constants.bl),new MotorDirectionConfig(true,true,true,true));
        //TODO: Retune PID
        arm = new PIDFArmSubsystem(hardwareMap, Constants.arm, 0.01, 0, 0.0001, 0.001, 1926/180);
        hand = new HandSubsystem(hardwareMap, Constants.hand);
        //TODO: Retune PID
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0.01, 0, 0.0002, 0.2, 0.01, 0, 0.0002, 0.2);
//      telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());


        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));12wec
        intake.setDefaultCommand(new ServoIntakeCommand(intake, 0));
        hand.setDefaultCommand(new HandCommand(hand, Constants.in));

        //Binding Commands
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER).whileHeld(new ServoIntakeCommand(intake, -1)).whenReleased(new ServoIntakeCommand(intake, 1)); //intake
        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER).whileHeld(new ServoIntakeCommand(intake, -1)).whenReleased(new ServoIntakeCommand(intake, 1)); //intake
        //Stow Position TODO: Find positions
        new GamepadButton(base, GamepadKeys.Button.X).whenPressed(new SetPIDFSlideArmCommand(slide, 0)).whenPressed(new SetPIDFSlideArmCommand(arm, 0)).whenPressed(new HandCommand(hand, 0));
        //Intake Position TODO: Find positions
        new GamepadButton(base, GamepadKeys.Button.A).whenPressed(new SetPIDFSlideArmCommand(slide, 0)).whenPressed(new SetPIDFSlideArmCommand(arm, 0)).whenPressed(new HandCommand(hand, 0));
        //High Basket Position TODO: Find positions
        new GamepadButton(base, GamepadKeys.Button.Y).whenPressed(new SetPIDFSlideArmCommand(slide, 0)).whenPressed(new SetPIDFSlideArmCommand(arm, 0)).whenPressed(new HandCommand(hand, 0));
        //Hang Position TODO: Find positions
        new GamepadButton(base, GamepadKeys.Button.B).whenPressed(new SetPIDFSlideArmCommand(slide, 0)).whenPressed(new SetPIDFSlideArmCommand(arm, 0)).whenPressed(new HandCommand(hand, 0));

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
