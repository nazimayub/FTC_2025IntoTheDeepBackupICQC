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
    //public static PIDFSlideSubsystem slide;
    public static SlideSubsystem slide;
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
        slide = new SlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE);
        //slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0.01, 0, 0.0002, 0.2, 0.01, 0, 0.0002, 0.2);
//      telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());


        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));
        intake.setDefaultCommand(new ServoIntakeCommand(intake, 0));
        hand.setDefaultCommand(new HandCommand(hand, Constants.in));


        //Binding Commands
        //new GamepadButton(base, GamepadKeys.Button.A).toggleWhenPressed(new PIDFSlideArmCommand(slide, -5), new PIDFSlideArmCommand(slide, 5));
        new GamepadButton(base, GamepadKeys.Button.A).toggleWhenPressed(new SlideArmCommand(slide, new GamepadEx(gamepad1)), new SlideArmCommand(slide, new GamepadEx(gamepad1))).whenReleased(new SlideArmCommand(slide, new GamepadEx(gamepad1))); //slide (w/o pid)
        new GamepadButton(base, GamepadKeys.Button.X).toggleWhenPressed(new PIDFSlideArmCommand(arm, 8), new PIDFSlideArmCommand(arm, -8)).whenReleased(new PIDFSlideArmCommand(arm, 0)); //arm
        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER).toggleWhenPressed(new HandCommand(hand, 1), new HandCommand(hand, -1)).whenReleased(new HandCommand(hand, 0)); //hand
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER).toggleWhenPressed(new ServoIntakeCommand(intake, 7)).whenReleased(new ServoIntakeCommand(intake, 7)); //intake
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
