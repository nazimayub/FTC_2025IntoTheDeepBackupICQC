package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
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
public class Duo extends CommandOpMode {
    GamepadEx base;
    public static GamepadEx op;
    SimpleLogger log;

    public static Drive drive;
    public static ServoSubsystem outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawDist, blocker;
    public static IntakeSubsystem intake;
    public static LimitSwitchSubsystem vLimit, hLimit;
    public static PIDFSlideSubsystem slide;
    public static PIDFSingleSlideSubsystem hSlide;
    public static TelemetrySubsystem telemetrySubsystem;
    public static WaitSubsystem pause;

    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);
        log = new SimpleLogger();
        intake = new IntakeSubsystem(hardwareMap, Constants.intake);
        drive = new Drive(hardwareMap, Constants.imu,new MotorConfig(Constants.fr,Constants.fl,Constants.br,Constants.bl),new MotorDirectionConfig(false,true,false,true));
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.05, 0, 0.0007, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.005, 0,  0.0, 0.1, 0.005, 0, 0.0, 0.1);
        pause = new WaitSubsystem();
        outtakeClaw = new ServoSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Constants.intakeDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Constants.intakeRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Constants.outtakeDist);
        vLimit = new LimitSwitchSubsystem(hardwareMap, Constants.vLimit);
        hLimit = new LimitSwitchSubsystem(hardwareMap, Constants.hLimit);
        blocker = new ServoSubsystem(hardwareMap, "servo6");
        outtakeClawRot = new ServoSubsystem(hardwareMap, Constants.outtakeRot);

        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));

        //Intake
        new GamepadButton(op, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new IntakeCommand(intake, -1)).whenReleased(new IntakeCommand(intake, 0));
        new GamepadButton(op, GamepadKeys.Button.LEFT_BUMPER).whenPressed(new IntakeCommand(intake, 1)).whenReleased(new IntakeCommand(intake, 0));

        //Bring intake down
        new GamepadButton(op, GamepadKeys.Button.A).whenPressed(new SequentialCommandGroup(
             new ServoCommand(blocker, Constants.unblock),
                new WaitCommand(pause, 300),
                new SetPIDFSlideArmCommand(hSlide, 400),
                new ServoCommand(intakeClawRot, Constants.intakeDownPos)
        ));

        //Transfer
        new GamepadButton(op, GamepadKeys.Button.B).whenPressed(new SequentialCommandGroup(
                new ServoCommand(intakeClawRot, Constants.intakeInitTransferPos),
                new ServoCommand(outtakeClawRot, Constants.outtakeClawRotTransfer),
                new ServoCommand(outtakeClawDist, Constants.outtakeClawDistTempTransfer),
                new SlideResetCommand(slide, vLimit),
                new SlideResetCommand(hSlide, hLimit),
                new ServoCommand(blocker, Constants.block),
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
        new GamepadButton(op, GamepadKeys.Button.Y).whenPressed(
                new SequentialCommandGroup(
                        new ServoCommand(outtakeClawRot, Constants.outtakeClawRotTransfer),
                        new ServoCommand(outtakeClawDist, Constants.outtakeClawDistFinalTransfer),
                        new SetPIDFSlideArmCommand(slide, 2750)
                ));

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
        new GamepadButton(op, GamepadKeys.Button.DPAD_DOWN).whenPressed(new SlideResetCommand(slide, vLimit));

        //Specimen Score
        new GamepadButton(base, GamepadKeys.Button.Y).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, Constants.grab),
                new ServoCommand(outtakeClawDist, Constants.distBasketPos),
                new ServoCommand(outtakeClawRot, Constants.outtakeClawRotTransfer),
                new SetPIDFSlideArmCommand(slide, 525)
        ));

        new GamepadButton(base, GamepadKeys.Button.B).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClawDist, Constants.distBasketPos-0.1),
                new SlideResetCommand(slide, vLimit),
                new ServoCommand(outtakeClaw, Constants.release)
        ));

        new GamepadButton(base, GamepadKeys.Button.DPAD_LEFT).whenPressed(new SetPIDFSlideArmCommand(slide, 2400));


    }
}
