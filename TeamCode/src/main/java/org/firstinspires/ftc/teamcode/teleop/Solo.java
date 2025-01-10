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
public class Solo extends CommandOpMode {
    GamepadEx base;
    GamepadEx op;
    SimpleLogger log;

    public static Drive drive;
    public static ServoSubsystem intakeClaw, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawDist, blocker;
    public static ServoIntakeSubsystem intake;
    public static LimitSwitchSubsystem vertical, horizontal;
    public static PIDFSlideSubsystem slide;
    public static PIDFSingleSlideSubsystem hSlide;
    public static WaitSubsystem pause;
    public double block = 0.03, unblock = 0.12;

    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);
        log = new SimpleLogger();

        drive = new Drive(hardwareMap, Const.imu,new MotorConfig(Const.fr, Const.fl, Const.br, Const.bl),new MotorDirectionConfig(false,true,false,true));
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Const.hSlide, 0.05, 0.1, 0.0005, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, .05, 0.25, 0.0, 0.2, 0.05, 0.25, 0.0, 0.25);
        pause = new WaitSubsystem();
        intakeClaw = new ServoSubsystem(hardwareMap, Const.intake);
        outtakeClaw = new ServoSubsystem(hardwareMap, Const.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Const.intakeDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Const.intakeRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Const.outtakeDist);
        vertical = new LimitSwitchSubsystem(hardwareMap, "vSlide");
        horizontal = new LimitSwitchSubsystem(hardwareMap, "hSlide");
        blocker = new ServoSubsystem(hardwareMap, "servo6");

        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));
        //Bring intake down
        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER).whenPressed(new SequentialCommandGroup(
                new ServoCommand(blocker, unblock),
                new WaitCommand(pause, 300),
                new SetPIDFSlideArmCommand(hSlide, 370),
                new WaitCommand(pause, 500),
                new ServoCommand(intakeClawDist, 0.233),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, 0.865),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClaw, 0.44)
        ));


        //Grabs sample Stows intake and transfers
        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new SequentialCommandGroup(
                new SlideResetCommand(slide, vertical),

                new ServoCommand(outtakeClaw, 0.343),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, 0.5),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawDist, 0.623),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClawRot, 0.05),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDist, 0.682),
                new WaitCommand(pause, 500),
                new SlideResetCommand(hSlide, horizontal),
                new ServoCommand(blocker, block),
                new ServoCommand(outtakeClaw, 0.533),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClaw, 0.44),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDist, 0.344)
        ));

        //Place Specimen
        new GamepadButton(base, GamepadKeys.Button.DPAD_RIGHT).whenPressed(new SequentialCommandGroup(
                new SetPIDFSlideArmCommand(slide, 800)
        ));

        //Place Specimen
        new GamepadButton(base, GamepadKeys.Button.DPAD_LEFT).whenPressed(new SequentialCommandGroup(
                new SetPIDFSlideArmCommand(slide, 365),
                new ServoCommand(outtakeClawDist, 0.1),
                new ServoCommand(outtakeClaw, 0.343)
        ));



        //Slides down
        new GamepadButton(base, GamepadKeys.Button.DPAD_DOWN).whenPressed(new SequentialCommandGroup(
                new SlideResetCommand(slide, vertical)
        ));

        //Slides up
        new GamepadButton(base, GamepadKeys.Button.DPAD_UP).whenPressed(new SequentialCommandGroup(
                new SetPIDFSlideArmCommand(slide, 1900),
                new ServoCommand(outtakeClawDist, 0.1)
        ));

        //Intakes
        new GamepadButton(base, GamepadKeys.Button.A).whenPressed(new SequentialCommandGroup(
                new ServoCommand(intakeClawDist, 0.168),
                new WaitCommand(pause, 300),
                new ServoCommand(intakeClaw, 0.668)

        ));

        //Drops
        new GamepadButton(base, GamepadKeys.Button.X).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, 0.344)

        ));
        //Moves outtake claw to specimen position
        new GamepadButton(base, GamepadKeys.Button.B).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClawDist, .08),
                new ServoCommand(outtakeClaw, 0.343)
        ));

        //Intakes Specimen
        new GamepadButton(base, GamepadKeys.Button.Y).whenPressed(new SequentialCommandGroup(
                new ServoCommand(outtakeClaw, .533),
                new WaitCommand(pause, 300),
                new ServoCommand(outtakeClawDist, .128)
        ));

    }
}
