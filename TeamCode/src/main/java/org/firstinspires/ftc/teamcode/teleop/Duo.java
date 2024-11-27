package org.firstinspires.ftc.teamcode.teleop;

import android.transition.Slide;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.network.SendOnceRunnable;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utils.*;
@TeleOp
public class Duo extends CommandOpMode {
    GamepadEx base;
    public static GamepadEx op;
    SimpleLogger log;

    public static Drive drive;
    public static ServoSubsystem intakeClaw, outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawDist, blocker;
    public static IntakeSubsystem intake;
    public static LimitSwitchSubsystem vertical, horizontal;
    public static PIDFSlideSubsystem slide;
    public static PIDFSingleSlideSubsystem hSlide;
    public static TelemetrySubsystem telemetrySubsystem;
    public static WaitSubsystem pause;
    public double block = 0.03, unblock = 0.12;


    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);
        log = new SimpleLogger();
        intake = new IntakeSubsystem(hardwareMap, Constants.intake);
        drive = new Drive(hardwareMap, Constants.imu,new MotorConfig(Constants.fr,Constants.fl,Constants.br,Constants.bl),new MotorDirectionConfig(false,true,false,true));
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.05, 0, 0.0007, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0.06, 0,  0.0004, 0.2, 0.06, 0, 0.0004, 0.2);
//      telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());
        pause = new WaitSubsystem();
        outtakeClaw = new ServoSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Constants.intakeClawDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Constants.intakeClawRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Constants.outtakeClawDist);
        vertical = new LimitSwitchSubsystem(hardwareMap, "vSlide");
        horizontal = new LimitSwitchSubsystem(hardwareMap, "hSlide");
        blocker = new ServoSubsystem(hardwareMap, "servo6");
        outtakeClawRot = new ServoSubsystem(hardwareMap, Constants.outtakeClawRot);
        /*
        * DRIVER 1 (base) - Drive & Samples
        * DRIVER 2 (op) - Slides & Specimen
        * */

        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));

        //TODO: Fix how we use horizontal slide
        //hSlide.setDefaultCommand(new SlideArmCommand(hSlide, op));

        //Trigger defaultHSlideCommand = new Trigger(new OperatorTrigger()::get);
        //defaultHSlideCommand.whenActive(new SlideArmCommand(hSlide, op));

        //Intake
        new GamepadButton(op, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new IntakeCommand(intake, -1)).whenReleased(new IntakeCommand(intake, 0));
        new GamepadButton(op, GamepadKeys.Button.LEFT_BUMPER).whenPressed(new IntakeCommand(intake, 1)).whenReleased(new IntakeCommand(intake, 0));

        //Bring intake down
        new GamepadButton(op, GamepadKeys.Button.A).whenPressed(new SequentialCommandGroup(
             new ServoCommand(blocker, unblock),
                new WaitCommand(pause, 300),
                new SetPIDFSlideArmCommand(hSlide, 400),
                new ServoCommand(intakeClawRot, Constants.intakeDownPos)
        ));

        //Transfer
        new GamepadButton(op, GamepadKeys.Button.B).whenPressed(new SequentialCommandGroup(
                new ServoCommand(intakeClawRot, Constants.intakeInitTransferPos),
                new ServoCommand(outtakeClawRot, Constants.outtakeClawRotTransfer),
                new ServoCommand(outtakeClawDist, Constants.outtakeClawDistTempTransfer),
                new SlideResetCommand(slide, vertical),
                new SlideResetCommand(hSlide, horizontal),
                new ServoCommand(blocker, block),
                new ServoCommand(outtakeClaw, Constants.release),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDist, Constants.outtakeClawDistInitTransfer),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, Constants.intakeFinalTransferPos),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClaw, Constants.grab),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, Constants.intakeSecondFinalTransferPos),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDist, Constants.outtakeClawDistFinalTransfer)
        ));


        //Basket Score
        new GamepadButton(op, GamepadKeys.Button.Y).whenPressed(new SetPIDFSlideArmCommand(slide, 1500));

        //Release in Basket
        new GamepadButton(op, GamepadKeys.Button.X).whenPressed(new ServoCommand(outtakeClaw, Constants.release));

        //Specimen Grab Pos
        new GamepadButton(base, GamepadKeys.Button.A).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClawDist, Constants.distSpecimenGrab),
                new ServoCommand(outtakeClawRot, Constants.rotSpecimenGrab),
                new ServoCommand(outtakeClaw, Constants.release)
        ));

        //Grab
        new GamepadButton(base, GamepadKeys.Button.X).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, Constants.grab),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDist, Constants.distSpecimenGrabFinal)
        ));

        //Release
        new GamepadButton(op, GamepadKeys.Button.DPAD_UP).whenPressed(new ServoCommand(outtakeClaw, Constants.release));

        //VSlide Down
        new GamepadButton(op, GamepadKeys.Button.DPAD_DOWN).whenPressed(new SlideResetCommand(slide, vertical));

        //Specimen Score
        new GamepadButton(base, GamepadKeys.Button.Y).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClawDist, Constants.distInitSpecimenScorePos),
                new ServoCommand(outtakeClawRot, Constants.rotSpecimenScorePos),
                new SetPIDFSlideArmCommand(slide, 150)
        ));

        new GamepadButton(base, GamepadKeys.Button.B).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClawDist, Constants.distFinalSpecimenScorePos),
                new WaitCommand(pause, 500),
                new ServoCommand(outtakeClaw, Constants.release)
        ));



    }
}
