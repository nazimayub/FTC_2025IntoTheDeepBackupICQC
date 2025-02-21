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
import org.firstinspires.ftc.teamcode.actions.SpecimenGrabAction;
import org.firstinspires.ftc.teamcode.actions.SpecimenScoreAction;
import org.firstinspires.ftc.teamcode.utils.ActionUtils;
import org.firstinspires.ftc.teamcode.utils.Actions;

public class Duo2 extends CommandOpMode {
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
        drive.setDefaultCommand(new DriveCommand(drive,base));
        new GamepadButton(base, GamepadKeys.Button.A).whenPressed(Actions.SpecimenGrabAction(outtakeClaw, slide, vLimit, intakeClawRot, hSlide, hLimit, outtakeClawTwist, outtakeClawDistRight, outtakeClawDistLeft));
        new GamepadButton(base, GamepadKeys.Button.B).whenPressed(Actions.SpecimenScoreAction(outtakeClaw, pause, outtakeClawDistRight, outtakeClawDistLeft, outtakeClawRot, outtakeClawTwist, slide));
        new GamepadButton(base, GamepadKeys.Button.DPAD_DOWN).whenPressed(Actions.ShiftGearAction(Const.torque)).whenReleased(new SequentialCommandGroup(Actions.ShiftGearAction(Const.speed)));

        // OPERATOR
        new GamepadButton(op, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(Actions.IntakeAcceptAction(intake)).whenReleased(Actions.IntakeRestAction(intake));
        new GamepadButton(op, GamepadKeys.Button.LEFT_BUMPER).whenPressed(Actions.IntakeRejectAction(intake)).whenReleased(Actions.IntakeRestAction(intake));
        new GamepadButton(op, GamepadKeys.Button.A).whenPressed(Actions.HSlideAction(hSlide));
        new GamepadButton(op, GamepadKeys.Button.B).whenPressed(Actions.TransferAction(outtakeClaw, intakeClawRot, outtakeClawDistLeft, outtakeClawDistRight, outtakeClawRot, outtakeClawTwist, slide, hSlide, vLimit, hLimit, pause));
        new GamepadButton(op, GamepadKeys.Button.Y).whenPressed(Actions.RaiseToBasketAction(outtakeClawRot, outtakeClawDistRight, outtakeClawDistLeft, slide));
        new GamepadButton(op, GamepadKeys.Button.X).whenPressed(Actions.ClawReleaseAction(outtakeClaw));
        new GamepadButton(op, GamepadKeys.Button.DPAD_DOWN).whenPressed(Actions.VSlideDownAction(slide, vLimit, outtakeClawDistLeft, outtakeClawDistRight, outtakeClawRot, outtakeClawTwist));
    }
}

