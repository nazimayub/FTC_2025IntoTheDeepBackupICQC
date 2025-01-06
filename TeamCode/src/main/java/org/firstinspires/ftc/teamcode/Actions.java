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
    public static IntakeSubsystem intake = new IntakeSubsystem(hardwareMap, Constants.intake);
    public static Drive drive = new Drive(hardwareMap, Constants.imu,new MotorConfig(Constants.fr,Constants.fl,Constants.br,Constants.bl),
            new MotorDirectionConfig(false,true,false,true));
    public static PIDFSingleSlideSubsystem hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.05, 0, 0.0007, 0);
    public static PIDFSlideSubsystem slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide,
            DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.005, 0,  0.0, 0.1, 0.005, 0, 0.0, 0.1);
    public static WaitSubsystem pause = new WaitSubsystem();
    public static ServoSubsystem outtakeClaw = new ServoSubsystem(hardwareMap, Constants.outtakeClaw);
    public static ServoSubsystem intakeClawDist = new ServoSubsystem(hardwareMap, Constants.intakeDist);
    public static ServoSubsystem intakeClawRot = new ServoSubsystem(hardwareMap, Constants.intakeRot);
    public static ServoSubsystem outtakeClawDist = new ServoSubsystem(hardwareMap, Constants.outtakeDist);
    public static LimitSwitchSubsystem vLimit = new LimitSwitchSubsystem(hardwareMap, Constants.vLimit);
    public static LimitSwitchSubsystem hLimit = new LimitSwitchSubsystem(hardwareMap, Constants.hLimit);
    public static ServoSubsystem blocker = new ServoSubsystem(hardwareMap, "servo6");
    public static ServoSubsystem outtakeClawRot = new ServoSubsystem(hardwareMap, Constants.outtakeRot);

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
                new ServoCommand(blocker, Constants.unblock),
                new WaitCommand(pause, Constants.pause),
                new SetPIDFSlideArmCommand(hSlide, Constants.hSlideExtend),
                new ServoCommand(intakeClawRot, Constants.intakeDownPos)
        );
    }

    public static SequentialCommandGroup transfer() {
        return new SequentialCommandGroup(
                new ServoCommand(intakeClawRot, Constants.intakeInitTransferPos),
                new ServoCommand(outtakeClawRot, Constants.outtakeClawRotTransfer),
                new ServoCommand(outtakeClawDist, Constants.outtakeClawDistTempTransfer),
                new SlideResetCommand(slide, vLimit),
                new SlideResetCommand(hSlide, hLimit),
                new ServoCommand(blocker, Constants.block),
                new ServoCommand(outtakeClaw, Constants.release),
                new WaitCommand(pause, Constants.pause),
                new ServoCommand(outtakeClawDist, Constants.outtakeClawDistInitTransfer),
                new WaitCommand(pause, Constants.pause),
                new ServoCommand(intakeClawRot, Constants.intakeFinalTransferPos),
                new WaitCommand(pause, Constants.pause),
                new ServoCommand(outtakeClaw, Constants.grab),
                new WaitCommand(pause, Constants.pause),
                new ServoCommand(intakeClawRot, Constants.intakeSecondFinalTransferPos),
                new WaitCommand(pause, Constants.pause),
                new ServoCommand(outtakeClawDist, Constants.outtakeClawDistFinalTransfer),
                new ServoCommand(outtakeClawRot, Constants.rotBasketPos)
        );
    }

    public static SequentialCommandGroup basketScore() {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClawRot, Constants.outtakeClawRotTransfer),
                new ServoCommand(outtakeClawDist, Constants.outtakeClawDistFinalTransfer),
                new SetPIDFSlideArmCommand(slide, Constants.vSlideBasket)
        );
    }

    public static ServoCommand grab() {
        return new ServoCommand(outtakeClaw, Constants.grab);
    }

    public static ServoCommand release() {
        return new ServoCommand(outtakeClaw, Constants.release);
    }

    public static SequentialCommandGroup specimenGrabPos() {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClawDist, Constants.distSpecimenGrab),
                new ServoCommand(outtakeClawRot, Constants.rotSpecimenGrab),
                new ServoCommand(outtakeClaw, Constants.release)
        );
    }

    public static SequentialCommandGroup specimenGrab() {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, Constants.grab),
                new WaitCommand(pause, Constants.pause),
                new ServoCommand(outtakeClawDist, Constants.distSpecimenGrabFinal)
        );
    }

    public static SequentialCommandGroup specimenScore() {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, Constants.grab),
                new ServoCommand(outtakeClawDist, Constants.distBasketPos),
                new ServoCommand(outtakeClawRot, Constants.outtakeClawRotTransfer),
                new SetPIDFSlideArmCommand(slide, Constants.vSlideBar)
        );
    }

    public static SequentialCommandGroup moveFromSpecimenScore() {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClawDist, Constants.distBasketPos-0.1),
                new SlideResetCommand(slide, vLimit),
                new ServoCommand(outtakeClaw, Constants.release)
        );
    }

    public static SlideResetCommand resetVertSlides() {
        return new SlideResetCommand(slide, vLimit);
    }

    public static SetPIDFSlideArmCommand setVertSlidesTo2400TicksForSomeReason() {
        return new SetPIDFSlideArmCommand(slide, 2400);
    }

}
