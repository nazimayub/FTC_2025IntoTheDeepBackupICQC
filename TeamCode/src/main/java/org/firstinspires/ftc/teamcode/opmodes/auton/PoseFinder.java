package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Const;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ServoSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WaitSubsystem;
import org.firstinspires.ftc.teamcode.utils.Actions;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;
import org.firstinspires.ftc.teamcode.utils.SimpleLogger;

import java.util.ArrayList;

@Autonomous(group = "Tuner")
/*
 * Run your bot using your Solo code to each Pose you want for auto.
 * Hit the left bumper once you've reached a Pose you want to save.
 */
public class PoseFinder extends OpMode {
    private Follower follower;
    private ArrayList<Pose> poses;
    private boolean previousLeftBumper = false;

    public static GamepadEx base;
    SimpleLogger log;

    public static Drive drive;
    public static ServoSubsystem outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawTwist, outtakeClawDistRight, outtakeClawDistLeft, shifter;
    public static IntakeSubsystem intake;
    public static LimitSwitchSubsystem vLimit, hLimit;
    public static PIDFSlideSubsystem slide, tSlide;
    public static PIDFSingleSlideSubsystem hSlide;
    public static WaitSubsystem pause;

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        base = new GamepadEx(gamepad1);

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
        poses = new ArrayList<>();


        drive.setDefaultCommand(new DriveCommand(drive,base));
        new GamepadButton(base, GamepadKeys.Button.DPAD_LEFT).whenPressed(Actions.SpecimenGrabAction(outtakeClaw, slide, vLimit, intakeClawRot, hSlide, hLimit, outtakeClawTwist, outtakeClawDistRight, outtakeClawDistLeft, outtakeClawRot));
        new GamepadButton(base, GamepadKeys.Button.DPAD_RIGHT).whenPressed(Actions.SpecimenScoreAction(outtakeClaw, pause, outtakeClawDistRight, outtakeClawDistLeft, outtakeClawRot, outtakeClawTwist, slide));
        new GamepadButton(base, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(Actions.IntakeAcceptAction(intake)).whenReleased(Actions.IntakeRestAction(intake));
        //new GamepadButton(base, GamepadKeys.Button.LEFT_BUMPER).whenPressed(Actions.IntakeRejectAction(intake)).whenReleased(Actions.IntakeRestAction(intake));
        new GamepadButton(base, GamepadKeys.Button.A).whenPressed(Actions.HSlideAction(hSlide, intakeClawRot));
        new GamepadButton(base, GamepadKeys.Button.B).whenPressed(Actions.TransferAction(outtakeClaw, intakeClawRot, outtakeClawDistLeft, outtakeClawDistRight, outtakeClawRot, outtakeClawTwist, slide, hSlide, vLimit, hLimit, pause));
        new GamepadButton(base, GamepadKeys.Button.Y).whenPressed(Actions.RaiseToBasketAction(outtakeClawRot, outtakeClawDistRight, outtakeClawDistLeft, slide));
        new GamepadButton(base, GamepadKeys.Button.X).whenPressed(Actions.ClawReleaseAction(outtakeClaw));
        new GamepadButton(base, GamepadKeys.Button.DPAD_DOWN).whenPressed(Actions.VSlideDownAction(slide, vLimit, outtakeClawDistLeft, outtakeClawDistRight, outtakeClawRot, outtakeClawTwist));
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();

        boolean currentLeftBumper = gamepad1.left_bumper;
        if (currentLeftBumper && !previousLeftBumper) {
            poses.add(follower.getPose());
            telemetry.addData("Pose Saved", follower.getPose());
        }
        previousLeftBumper = currentLeftBumper;

        telemetry.update();
    }

    @Override
    public void stop() {
        CommandScheduler.getInstance().reset();
    }
}
