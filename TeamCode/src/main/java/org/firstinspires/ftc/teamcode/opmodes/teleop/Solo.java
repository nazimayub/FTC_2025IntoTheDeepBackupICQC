package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.base.bot.Const;
import org.firstinspires.ftc.teamcode.base.commands.*;
import org.firstinspires.ftc.teamcode.base.subsystems.*;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;
import org.firstinspires.ftc.teamcode.utils.SimpleLogger;

@TeleOp(name="Solo", group=".TeleOp")
public class Solo extends CommandOpMode {
    public  GamepadEx base;
    public  GamepadEx op;
    SimpleLogger log;

    public Drive drive;
    public ServoSubsystem outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawTwist, outtakeClawDistRight, outtakeClawDistLeft, shifter;
    public IntakeSubsystem intake;
    public IntakeAutoSubsystem intakeAuto;
    public LimitSwitchSubsystem vLimit, hLimit;
    public PIDFSlideSubsystem slide;
    public PIDFSlideSubsystem tSlide;
    public PIDFSingleSlideSubsystem hSlide;
    public FollowerSubsystem follower;
    public WaitSubsystem pause;

    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);

        log = new SimpleLogger();
        intake = new IntakeSubsystem(hardwareMap, Const.intake);
        intakeAuto = new IntakeAutoSubsystem(hardwareMap, Const.intake, new ElapsedTime());
        drive = new Drive(hardwareMap, Const.imu, new MotorConfig(Const.fr, Const.fl, Const.br, Const.bl),
                new MotorDirectionConfig(false,true,false,true));
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Const.hSlide, -0.02, 0, 0, 0.0);
        tSlide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD,
                0.001, 0,  0, 0.01,
                0.001, 0, 0, 0.01);
        slide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD,
                0.1, 0, 0.000004, 0.21,
                0.1, 0, 0.000004, 0.21);
        pause = new WaitSubsystem();
        outtakeClaw = new ServoSubsystem(hardwareMap, Const.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Const.intakeDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Const.intakeRot);
        outtakeClawDistLeft = new ServoSubsystem(hardwareMap, Const.outtakeDistLeft);
        outtakeClawDistRight = new ServoSubsystem(hardwareMap, Const.outtakeDistRight);
        vLimit = new LimitSwitchSubsystem(hardwareMap, Const.vLimit);
        hLimit = new LimitSwitchSubsystem(hardwareMap, Const.hLimit);
        //shifter = new ServoSubsystem(hardwareMap, Const.gearShifter);
        outtakeClawRot = new ServoSubsystem(hardwareMap, Const.outtakeRot);
        outtakeClawTwist = new ServoSubsystem(hardwareMap, Const.outtakeTwist);

        new GamepadButton(base, GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(
                        new ParallelCommandGroup(
                                new ServoCommand(outtakeClaw, Const.release),
                                new SlideResetCommand(slide, vLimit),
                                new ServoCommand(intakeClawRot, .3),
                                new SlideResetCommand(hSlide, hLimit),
                                new ServoCommand(outtakeClawTwist, Const.untwist),
                                new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrab),
                                new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrab),
                                new ServoCommand(outtakeClawRot, Const.rotSpecimenGrab)
                        )
                );

        new GamepadButton(base, GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(
                        new SequentialCommandGroup(
                                new ServoCommand(outtakeClaw, .2),
                                new WaitCommand(pause, 300),
                                new ParallelCommandGroup(
                                        new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrabFinal),
                                        new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrabFinal),
                                        new ServoCommand(outtakeClawRot, Const.rotSpecimenScore),
                                        new ServoCommand(outtakeClawTwist, Const.twist),
                                        new SetPIDFSlideArmCommand(slide, 270)
                                )
                        ));

        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(
                        new SequentialCommandGroup(
                                new IntakeCommand(intake, -.8)
                        ))
                .whenReleased(
                        new SequentialCommandGroup(
                                new IntakeCommand(intake, 0)
                        )
                );

        new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(
                        new SequentialCommandGroup(
                                new IntakeCommand(intake, -.8)
                        )
                )
                .whenReleased(
                        new SequentialCommandGroup(
                                new IntakeCommand(intake, 0)
                        ));

        new GamepadButton(base, GamepadKeys.Button.A)
                .whenPressed(
                        new SequentialCommandGroup(
                                new ServoCommand(intakeClawRot, 0.4),
                                new SetPIDFSlideArmCommand(hSlide, -670),
                                new ServoCommand(intakeClawRot, 0.12)
                        )
                );

        new GamepadButton(base, GamepadKeys.Button.B)
                .whenPressed(
                        new SequentialCommandGroup(
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
                                new ServoCommand(outtakeClaw, Const.grab+.05),
                                new WaitCommand(pause, 300),
                                new ServoCommand(intakeClawRot, .2),
                                new SetPIDFSlideArmCommand(slide, 200)

                        ));

        new GamepadButton(base, GamepadKeys.Button.Y)
                .whenPressed(
                        new ParallelCommandGroup(
                                new ServoCommand(outtakeClawRot, 0.5),
                                new ServoCommand(outtakeClawDistRight, 1-0.378),
                                new ServoCommand(outtakeClawDistLeft, 0.378),
                                new SetPIDFSlideArmCommand(slide, 1300)
                        )
                );

        new GamepadButton(base, GamepadKeys.Button.X)
                .whenPressed(
                        new SequentialCommandGroup(
                                new ServoCommand(outtakeClaw, Const.release)
                        )
                );

        new GamepadButton(base, GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(
                        new ParallelCommandGroup(
                                new SlideResetCommand(slide, vLimit),
                                new ServoCommand(outtakeClawDistLeft, 1),
                                new ServoCommand(outtakeClawDistRight, 0),
                                new ServoCommand(outtakeClawRot, 0.7),
                                new ServoCommand(outtakeClawTwist, 0.924)
                        )
                );
    }
}
