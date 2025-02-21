package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utils.*;

@TeleOp(group="TeleOp")
public class Duo extends CommandOpMode {
    public static GamepadEx base;
    public static GamepadEx op;
    SimpleLogger log;

    public static Drive drive;
    public static ServoSubsystem outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawTwist, outtakeClawDistRight, outtakeClawDistLeft, shifter;
    public static IntakeSubsystem intake;
    public static LimitSwitchSubsystem vLimit, hLimit;
    public static PIDFSlideSubsystem slide, tSlide;
    public static PIDFSingleSlideSubsystem hSlide;
    public static WaitSubsystem pause;

    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);

        log = new SimpleLogger();
        intake = new IntakeSubsystem(hardwareMap, Const.intake);
        drive = new Drive(hardwareMap, Const.imu, new MotorConfig(Const.fr, Const.bl, Const.br, Const.fl),new MotorDirectionConfig(false,true,false,true));
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Const.hSlide, -0.02, 0, 0, 0.0);
        tSlide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.001, 0,  0, 0.05, 0.001, 0, 0, 0.05);
        slide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.1, 0, 0.000004, 0.21, 0.1, 0, 0.000004, 0.21);
        pause = new WaitSubsystem();
        outtakeClaw = new ServoSubsystem(hardwareMap, Const.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Const.intakeDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Const.intakeRot);
        outtakeClawDistLeft = new ServoSubsystem(hardwareMap, Const.outtakeDistLeft);
        outtakeClawDistRight = new ServoSubsystem(hardwareMap, Const.outtakeDistRight);
        vLimit = new LimitSwitchSubsystem(hardwareMap, Const.vLimit);
        hLimit = new LimitSwitchSubsystem(hardwareMap, Const.hLimit);
        shifter = new ServoSubsystem(hardwareMap, Const.gearShifter);
        outtakeClawRot = new ServoSubsystem(hardwareMap, Const.outtakeRot);
        outtakeClawTwist = new ServoSubsystem(hardwareMap, Const.outtakeTwist);

        // DRIVER

        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));

        //Specimen Grab
        new GamepadButton(base, GamepadKeys.Button.A).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, Const.release),
                new SlideResetCommand(slide, vLimit),
                new ServoCommand(intakeClawRot, .3),
                new SlideResetCommand(hSlide, hLimit),
                new ServoCommand(outtakeClawTwist, Const.untwist),
                new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrab),
                new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrab),
                new ServoCommand(outtakeClawRot, Const.rotSpecimenGrab),
                new ServoCommand(outtakeClaw, Const.release)
        ));

        //Specimen Score
        new GamepadButton(base, GamepadKeys.Button.B).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, Const.grab),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrabFinal),
                new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrabFinal),
                new ServoCommand(outtakeClawRot, Const.rotSpecimenScore),
                new ServoCommand(outtakeClawTwist, Const.twist),
                new SetPIDFSlideArmCommand(slide, 7000)
        ));

        //Shift to high torque
        new GamepadButton(base, GamepadKeys.Button.DPAD_DOWN).whenPressed(new SequentialCommandGroup(
                new WaitCommand(pause, 1000),
                new ServoCommand(shifter, .5)
        )).whenReleased(new SequentialCommandGroup(
                new ServoCommand(shifter, .2)
        ));

        //Hang
        new GamepadButton(base, GamepadKeys.Button.DPAD_UP).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClawRot, 0.5),
                new ServoCommand(outtakeClawDistRight, 1-0.378),
                new ServoCommand(outtakeClawDistLeft, 0.378),
                new SetPIDFSlideArmCommand(slide, 40000)
        ));

        // OPERATOR

        //Intake
        new GamepadButton(op, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new IntakeCommand(intake, -1)).whenReleased(new IntakeCommand(intake, 0));
        new GamepadButton(op, GamepadKeys.Button.LEFT_BUMPER).whenPressed(new IntakeCommand(intake, 1)).whenReleased(new IntakeCommand(intake, 0));

        //HorizontalSlide Extend
        new GamepadButton(op, GamepadKeys.Button.A).whenPressed(new SequentialCommandGroup(
                new ServoCommand(intakeClawRot, 0.4),
                new SetPIDFSlideArmCommand(hSlide, -700),
                new ServoCommand(intakeClawRot, 0.12)
        ));

        //Transfer
        new GamepadButton(op, GamepadKeys.Button.B).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, Const.release),
                new ServoCommand(intakeClawRot, .3),
                new ServoCommand(outtakeClawDistLeft, 1),
                new ServoCommand(outtakeClawDistRight, 0),
                new ServoCommand(outtakeClawRot, 0.7),
                new ServoCommand(outtakeClawTwist, 0.924),
                new SlideResetCommand(slide, vLimit),
                new SlideResetCommand(hSlide, hLimit),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawRot, 0.83),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, 0.36),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClaw, Const.grab),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, .2),
                new SetPIDFSlideArmCommand(slide, 5000)
        ));

        //Raise to Basket
        new GamepadButton(op, GamepadKeys.Button.Y).whenPressed(
                new SequentialCommandGroup(
                        new ServoCommand(outtakeClawRot, 0.5),
                        new ServoCommand(outtakeClawDistRight, 1-0.378),
                        new ServoCommand(outtakeClawDistLeft, 0.378),
                        new SetPIDFSlideArmCommand(slide, 40000)
                ));

        //Release in Basket
        new GamepadButton(op, GamepadKeys.Button.X).whenPressed(new ServoCommand(outtakeClaw, Const.release));

        //VSlide Down
        new GamepadButton(op, GamepadKeys.Button.DPAD_DOWN).whenPressed(new SequentialCommandGroup(
                new SlideResetCommand(slide, vLimit),
                new ServoCommand(outtakeClawDistLeft, 1),
                new ServoCommand(outtakeClawDistRight, 0),
                new ServoCommand(outtakeClawRot, 0.7),
                new ServoCommand(outtakeClawTwist, 0.924)
        ));


    }
}

