package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utils.*;

public class Actions {
    public static GamepadEx base = new GamepadEx(gamepad1);
    public static GamepadEx op = new GamepadEx(gamepad2);
    public static SimpleLogger log = new SimpleLogger();
    public static IntakeSubsystem intake = new IntakeSubsystem(hardwareMap, Const.intake);
    public static Drive drive = new Drive(hardwareMap, Const.imu, new MotorConfig(Const.fr, Const.fl, Const.br, Const.bl),
            new MotorDirectionConfig(false, true, false, true));
    public static PIDFSingleSlideSubsystem hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Const.hSlide, 0.05, 0, 0.0007, 0);
    public static PIDFSlideSubsystem slide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide,
            DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.005, 0, 0.0, 0.1, 0.005, 0, 0.0, 0.1);
    public static WaitSubsystem pause = new WaitSubsystem();
    public static ServoSubsystem outtakeClaw = new ServoSubsystem(hardwareMap, Const.outtakeClaw);
    public static ServoSubsystem intakeClawDist = new ServoSubsystem(hardwareMap, Const.intakeDist);
    public static ServoSubsystem intakeClawRot = new ServoSubsystem(hardwareMap, Const.intakeRot);
    public static ServoSubsystem outtakeClawDist = new ServoSubsystem(hardwareMap, Const.outtakeDist);
    public static LimitSwitchSubsystem vLimit = new LimitSwitchSubsystem(hardwareMap, Const.vLimit);
    public static LimitSwitchSubsystem hLimit = new LimitSwitchSubsystem(hardwareMap, Const.hLimit);
    public static ServoSubsystem blocker = new ServoSubsystem(hardwareMap, "servo6");
    public static ServoSubsystem outtakeClawRot = new ServoSubsystem(hardwareMap, Const.outtakeRot);

    public static DriveCommand drive() {
        return new DriveCommand(drive, base);
    }

    public static IntakeCommand intake() {
        return new IntakeCommand(intake, -1);
    }

    public static IntakeCommand outtake() {
        return new IntakeCommand(intake, 1);
    }

    public static IntakeCommand stopIntake() {
        return new IntakeCommand(intake, 0);
    }

    public static SequentialCommandGroup bringIntakeDown() {
        return new SequentialCommandGroup(
                new ServoCommand(blocker, Const.unblock),
                new WaitCommand(pause, Const.pause),
                new SetPIDFSlideArmCommand(hSlide, Const.hSlideExtend),
                new ServoCommand(intakeClawRot, Const.intakeDownPos)
        );
    }

    public static SequentialCommandGroup transfer() {
        return new SequentialCommandGroup(
                new ServoCommand(intakeClawRot, Const.intakeInitTransferPos),
                new ServoCommand(outtakeClawRot, Const.outtakeClawRotTransfer),
                new ServoCommand(outtakeClawDist, Const.outtakeClawDistTempTransfer),
                new SlideResetCommand(slide, vLimit),
                new SlideResetCommand(hSlide, hLimit),
                new ServoCommand(blocker, Const.block),
                new ServoCommand(outtakeClaw, Const.release),
                new WaitCommand(pause, Const.pause),
                new ServoCommand(outtakeClawDist, Const.outtakeClawDistInitTransfer),
                new WaitCommand(pause, Const.pause),
                new ServoCommand(intakeClawRot, Const.intakeFinalTransferPos),
                new WaitCommand(pause, Const.pause),
                new ServoCommand(outtakeClaw, Const.grab),
                new WaitCommand(pause, Const.pause),
                new ServoCommand(intakeClawRot, Const.intakeSecondFinalTransferPos),
                new WaitCommand(pause, Const.pause),
                new ServoCommand(outtakeClawDist, Const.outtakeClawDistFinalTransfer),
                new ServoCommand(outtakeClawRot, Const.rotBasketPos)
        );
    }

    public static SequentialCommandGroup basketScore() {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClawRot, Const.outtakeClawRotTransfer),
                new ServoCommand(outtakeClawDist, Const.outtakeClawDistFinalTransfer),
                new SetPIDFSlideArmCommand(slide, Const.vSlideBasket)
        );
    }

    public static ServoCommand grab() {
        return new ServoCommand(outtakeClaw, Const.grab);
    }

    public static ServoCommand release() {
        return new ServoCommand(outtakeClaw, Const.release);
    }

    public static SequentialCommandGroup specimenGrabPos() {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClawDist, Const.distSpecimenGrab),
                new ServoCommand(outtakeClawRot, Const.rotSpecimenGrab),
                new ServoCommand(outtakeClaw, Const.release)
        );
    }

    public static SequentialCommandGroup specimenGrab() {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, Const.grab),
                new WaitCommand(pause, Const.pause),
                new ServoCommand(outtakeClawDist, Const.distSpecimenGrabFinal)
        );
    }

    public static SequentialCommandGroup specimenScore() {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, Const.grab),
                new ServoCommand(outtakeClawDist, Const.distBasketPos),
                new ServoCommand(outtakeClawRot, Const.outtakeClawRotTransfer),
                new SetPIDFSlideArmCommand(slide, Const.vSlideBar)
        );
    }

    public static SequentialCommandGroup moveFromSpecimenScore() {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClawDist, Const.distBasketPos - 0.1),
                new SlideResetCommand(slide, vLimit),
                new ServoCommand(outtakeClaw, Const.release)
        );
    }

    public static SlideResetCommand resetVertSlides() {
        return new SlideResetCommand(slide, vLimit);
    }

    public static SetPIDFSlideArmCommand setVertSlidesTo2400TicksForSomeReason() {
        return new SetPIDFSlideArmCommand(slide, 2400);
    }
}