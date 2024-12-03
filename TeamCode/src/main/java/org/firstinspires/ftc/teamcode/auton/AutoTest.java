package org.firstinspires.ftc.teamcode.auton;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utils.*;
@Autonomous
public class AutoTest extends CommandOpMode {
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
        intake = new IntakeSubsystem(hardwareMap, Constants.intake);
        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.05, 0, 0.0007, 0);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0.06, 0,  0.0004, 0.2, 0.06, 0, 0.0004, 0.2);
//      telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());
        pause = new WaitSubsystem();
        outtakeClaw = new ServoSubsystem(hardwareMap, Constants.outtakeClaw);
        intakeClawDist = new ServoSubsystem(hardwareMap, Constants.intakeDist);
        intakeClawRot = new ServoSubsystem(hardwareMap, Constants.intakeRot);
        outtakeClawDist = new ServoSubsystem(hardwareMap, Constants.outtakeDist);
        vertical = new LimitSwitchSubsystem(hardwareMap, "vSlide");
        horizontal = new LimitSwitchSubsystem(hardwareMap, "hSlide");
        blocker = new ServoSubsystem(hardwareMap, "servo6");
        outtakeClawRot = new ServoSubsystem(hardwareMap, Constants.outtakeRot);

        while(opModeIsActive()) {
            new ServoCommand(outtakeClaw, Constants.grab);
            new SetPIDFSlideArmCommand(slide, 250);
            new ServoCommand(outtakeClawDist, Constants.distSpecimenScorePos);
            new ServoCommand(outtakeClawRot, Constants.rotSpecimenScorePos);
            new SlideResetCommand(slide, vertical);
            new ServoCommand(outtakeClaw, Constants.release);
        }


    }
}
