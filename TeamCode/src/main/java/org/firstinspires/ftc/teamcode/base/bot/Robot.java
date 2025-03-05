package org.firstinspires.ftc.teamcode.base.bot;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.base.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.base.subsystems.*;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;
import org.firstinspires.ftc.teamcode.utils.SimpleLogger;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

public class Robot {

    public enum Mode {
        SOLO,
        DUO,
        AUTO
    }

    private final HardwareMap hardwareMap;
    private final Mode mode;
    public  GamepadEx base;
    public  GamepadEx op;
    private Follower follow;
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

    public Robot(Mode mode, HardwareMap hardwareMap) {
        this.mode = mode;
        this.hardwareMap = hardwareMap;

        Follower follow = new Follower(hardwareMap);
        follower = new FollowerSubsystem(hardwareMap, follow, new Pose(0,0,Math.toRadians(0)));

        base = new GamepadEx(null);
        op = new GamepadEx(null);

        log = new SimpleLogger();
        intake = new IntakeSubsystem(hardwareMap, Const.intake);
        intakeAuto = new IntakeAutoSubsystem(hardwareMap, Const.intake, new ElapsedTime());
        drive = new Drive(hardwareMap, Const.imu, new MotorConfig(Const.fr, Const.bl, Const.br, Const.fl),
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

        setBindings();
    }

    public void setGamePads(GamepadEx gamepad1, GamepadEx gamepad2) {
        this.base = gamepad1;
        this.op = gamepad2;
    }

    private void setBindings() {
        if(mode == Mode.SOLO)
            Solo();
        else if(mode == Mode.DUO)
            Duo();
        else if(mode == Mode.AUTO)
            Auto();
    }

    private void Solo() {
        this.setGamePads(new GamepadEx(gamepad1), null);
        drive.setDefaultCommand(new DriveCommand(drive,base));
    }

    private void Duo() {
        this.setGamePads(new GamepadEx(gamepad1), new GamepadEx(gamepad2));
        drive.setDefaultCommand(new DriveCommand(drive,base));
    }

    private void Auto() {
        Actions.InitAutoAction(this);
    }
}
