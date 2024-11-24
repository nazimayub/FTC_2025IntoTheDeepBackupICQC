//package org.firstinspires.ftc.teamcode.auton;
//
//import com.acmerobotics.roadrunner.Pose2d;
//import com.arcrobotics.ftclib.command.SequentialCommandGroup;
//import com.arcrobotics.ftclib.command.button.GamepadButton;
//import com.arcrobotics.ftclib.gamepad.GamepadKeys;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//
//import org.firstinspires.ftc.teamcode.Constants;
//import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;
//import org.firstinspires.ftc.teamcode.commands.AutoDriveCommand;
//import org.firstinspires.ftc.teamcode.commands.PIDFSlideArmCommand;
//import org.firstinspires.ftc.teamcode.commands.ServoCommand;
//import org.firstinspires.ftc.teamcode.commands.SetPIDFSlideArmCommand;
//import org.firstinspires.ftc.teamcode.commands.SlideResetCommand;
//import org.firstinspires.ftc.teamcode.commands.WaitCommand;
//import org.firstinspires.ftc.teamcode.subsystems.Drive;
//import org.firstinspires.ftc.teamcode.subsystems.HandSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.ServoIntakeSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.ServoSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.WaitSubsystem;
//import org.firstinspires.ftc.teamcode.utils.MotorConfig;
//import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;
//
//@Autonomous
//public class TestAuto extends LinearOpMode {
//    public static ServoIntakeSubsystem intake;
//    public static HandSubsystem hand;
//    public static LimitSwitchSubsystem vertical, horizontal;
//    public static PIDFSlideSubsystem slide;
//    public static TelemetrySubsystem telemetrySubsystem;
//    public static PIDFSingleSlideSubsystem hSlide;
//    public static ServoSubsystem intakeClaw;
//    public static ServoSubsystem outtakeClaw;
//    public static ServoSubsystem intakeClawDist;
//    public static ServoSubsystem intakeClawRot;
//    public static ServoSubsystem outtakeClawDist;
//    public static WaitSubsystem pause;
//    public static ServoSubsystem blocker;
//    public static SparkFunOTOSDrive drive;
//    public double block = 0.03, unblock = 0.12;
//    @Override
//    public void runOpMode(){
//        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Constants.hSlide, 0.05, 0.1, 0.0007, 0);
//        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, 0.06, 0, 0.001, 0.01, 0.06, 0, 0.001, 0.01);
////      telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());
//        pause = new WaitSubsystem();
//        intakeClaw = new ServoSubsystem(hardwareMap, Constants.intakeClaw);
//        outtakeClaw = new ServoSubsystem(hardwareMap, Constants.outtakeClaw);
//        intakeClawDist = new ServoSubsystem(hardwareMap, Constants.intakeClawDist);
//        intakeClawRot = new ServoSubsystem(hardwareMap, Constants.intakeClawRot);
//        outtakeClawDist = new ServoSubsystem(hardwareMap, Constants.outtakeClawDist);
//        vertical = new LimitSwitchSubsystem(hardwareMap, "vSlide");
//        horizontal = new LimitSwitchSubsystem(hardwareMap, "hSlide");
//        blocker = new ServoSubsystem(hardwareMap, "servo6");
//        drive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(0, 0, 0));
//        waitForStart();
//        while (opModeIsActive()) {
//            new SequentialCommandGroup(
//            new SetPIDFSlideArmCommand(slide, 800),
//            //Drive to Submersible
//                    new AutoDriveCommand(drive, 0, 36, 0),
//                    new SetPIDFSlideArmCommand(slide, 300),
//                    new ServoCommand(outtakeClawDist, 0.1),
//                    new ServoCommand(outtakeClaw, 0.343),
//            //Drive to sample 1
//                    new AutoDriveCommand(drive, -36, 5, 0),
//                    new ServoCommand(blocker, unblock),
//                    new WaitCommand(pause, 300),
//                    new SetPIDFSlideArmCommand(hSlide, 450),
//                    new WaitCommand(pause, 500),
//                    new ServoCommand(intakeClawDist, 0.233),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawRot, 0.865),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClaw, 0.44),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawDist, 0.168),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClaw, 0.668),
//                    new WaitCommand(pause, 300),
//                    new SlideResetCommand(slide, vertical),
//
//                    new ServoCommand(outtakeClaw, 0.343),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawRot, 0.5),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawDist, 0.623),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawRot, 0.05),
//                    new WaitCommand(pause, 300),
//                    new SetPIDFSlideArmCommand(hSlide, 0),
//                    new ServoCommand(blocker, block),
//                    new ServoCommand(outtakeClawDist, 0.682),
//                    new WaitCommand(pause, 500),
//                    new ServoCommand(outtakeClaw, 0.533),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClaw, 0.44),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(outtakeClawDist, 0.344),
//            //go to basket
//                    new AutoDriveCommand(drive, -48, 5, 315),
//            new PIDFSlideArmCommand(slide, 1450),
//            //score
//            new ServoCommand(outtakeClaw, 0.344),
//            new WaitCommand(pause, 300),
//            //Drive to sample 2
//                    new AutoDriveCommand(drive, -40, 5, 0),
//                    new ServoCommand(blocker, unblock),
//                    new WaitCommand(pause, 300),
//                    new SetPIDFSlideArmCommand(hSlide, 450),
//                    new WaitCommand(pause, 500),
//                    new ServoCommand(intakeClawDist, 0.233),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawRot, 0.865),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClaw, 0.44),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawDist, 0.168),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClaw, 0.668),
//                    new WaitCommand(pause, 300),
//                    new SlideResetCommand(slide, vertical),
//
//                    new ServoCommand(outtakeClaw, 0.343),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawRot, 0.5),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawDist, 0.623),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawRot, 0.05),
//                    new WaitCommand(pause, 300),
//                    new SetPIDFSlideArmCommand(hSlide, 0),
//                    new ServoCommand(blocker, block),
//                    new ServoCommand(outtakeClawDist, 0.682),
//                    new WaitCommand(pause, 500),
//                    new ServoCommand(outtakeClaw, 0.533),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClaw, 0.44),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(outtakeClawDist, 0.344),
//
//            //go to basket
//                    new AutoDriveCommand(drive, -48, 5, 315),
//            new PIDFSlideArmCommand(slide, 1450),
//            //score
//            new ServoCommand(outtakeClaw, 0.344),
//            new WaitCommand(pause, 300),
//            //Drive to sample 3
//                    new AutoDriveCommand(drive, -44, 5, 0),
//                    new ServoCommand(blocker, unblock),
//                    new WaitCommand(pause, 300),
//                    new SetPIDFSlideArmCommand(hSlide, 450),
//                    new WaitCommand(pause, 500),
//                    new ServoCommand(intakeClawDist, 0.233),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawRot, 0.865),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClaw, 0.44),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawDist, 0.168),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClaw, 0.668),
//                    new WaitCommand(pause, 300),
//                    new SlideResetCommand(slide, vertical),
//
//                    new ServoCommand(outtakeClaw, 0.343),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawRot, 0.5),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawDist, 0.623),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClawRot, 0.05),
//                    new WaitCommand(pause, 300),
//                    new SetPIDFSlideArmCommand(hSlide, 0),
//                    new ServoCommand(blocker, block),
//                    new ServoCommand(outtakeClawDist, 0.682),
//                    new WaitCommand(pause, 500),
//                    new ServoCommand(outtakeClaw, 0.533),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(intakeClaw, 0.44),
//                    new WaitCommand(pause, 300),
//                    new ServoCommand(outtakeClawDist, 0.344),
//            //go to basket
//                    new AutoDriveCommand(drive, -48, 5, 315),
//            new PIDFSlideArmCommand(slide, 1450),
//            //score
//            new ServoCommand(outtakeClaw, 0.344),
//            new WaitCommand(pause, 300)
//            );
//
//        }
//    }
//}
