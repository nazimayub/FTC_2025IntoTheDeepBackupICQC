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
    GamepadEx op;
    SimpleLogger log;
    public static ServoIntakeSubsystem intake;
    public static Drive drive;
    public static HandSubsystem hand;
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
        arm = new PIDFArmSubsystem(hardwareMap, Constants.arm, 0.01, 0, 0.0001, 0.001, 1926/180);
        hand = new HandSubsystem(hardwareMap, Constants.hand);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0.01, 0, 0.0002, 0.2, 0.01, 0, 0.0002, 0.2);
//      telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());

        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));
        intake.setDefaultCommand(new ServoIntakeCommand(intake, 0));
        hand.setDefaultCommand(new HandCommand(hand, Constants.in));
        slide.setDefaultCommand(new PIDFSlideArmCommand(slide, 0));
        arm.setDefaultCommand(new PIDFSlideArmCommand(arm, 0));

        //Binding Commands

        //Slides
        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER)
                .toggleWhenPressed(new PIDFSlideArmCommand(slide, -8), new PIDFSlideArmCommand(slide, 5))
                .whenReleased(new PIDFSlideArmCommand(slide, 0));

        //Arm
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER)
                .toggleWhenPressed(new PIDFSlideArmCommand(arm, 8), new PIDFSlideArmCommand(arm, -5))
                .whenReleased(new PIDFSlideArmCommand(arm, 0));

        //Hand
        new GamepadButton(base, GamepadKeys.Button.B)
                .toggleWhenPressed(new HandCommand(hand, 1), new HandCommand(hand, -1));

        //Spinner
        new GamepadButton(base, GamepadKeys.Button.X)
                .toggleWhenPressed(new ServoIntakeCommand(intake, 5), new ServoIntakeCommand(intake,-5));

        //Automated Commands (will change ticks later)
        //Intake
        new GamepadButton(base, GamepadKeys.Button.DPAD_UP)
                .whenPressed(new HandCommand(hand, 0))
                .toggleWhenPressed(new ServoIntakeCommand(intake, 2), new ServoIntakeCommand(intake, -2));

        //Stow
        new GamepadButton(base, GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new PIDFSlideArmCommand(slide, slide.getTick() > 0 ? -slide.getTick() : slide.getTick()))
                .whenPressed(new PIDFSlideArmCommand(arm, -1 - arm.getTick()))
                .whenPressed(new HandCommand(hand, 1));

        //High Basket
        new GamepadButton(base, GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new PIDFSlideArmCommand(arm, 1 - arm.getTick()))
                .whenPressed(new PIDFSlideArmCommand(slide, 1 - slide.getTick()))
                .whenPressed(new HandCommand(hand, 0))
                .whenPressed(new ServoIntakeCommand(intake, -2));

        //Hang
        new GamepadButton(base, GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new PIDFSlideArmCommand(arm, 1))
                .whenPressed(new PIDFSlideArmCommand(slide, 1))
                .whenPressed(new HandCommand(hand, 1))
                .whenPressed(new PIDFSlideArmCommand(slide, -1));

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
