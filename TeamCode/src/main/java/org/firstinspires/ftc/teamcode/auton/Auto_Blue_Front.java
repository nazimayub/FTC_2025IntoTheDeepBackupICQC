//package org.firstinspires.ftc.teamcode.auton;
//
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.*;
//import com.qualcomm.robotcore.hardware.*;
//import org.firstinspires.ftc.teamcode.Constants;
//import org.firstinspires.ftc.teamcode.MecanumDrive;
//import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;
//import org.firstinspires.ftc.teamcode.subsystems.*;
//import org.firstinspires.ftc.teamcode.utils.MotorConfig;
//import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;
//import org.firstinspires.ftc.teamcode.utils.SimpleLogger;
//
//@Autonomous
//public class Auto_Blue_Front extends LinearOpMode {
//    public static SimpleLogger log;
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
//    public double block = 0.03, unblock = 0.12;
//
//    public static class Params {
//        public double originx = 36;
//        public double originy = 12;
//        public double initialHeading = 180;
//    }
//
//    public static HardwareMap hMap;
//    public static Params PARAMS = new Params();
//
//    @Override
//    public void runOpMode() {
//        SparkFunOTOSDrive sparkDrive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(PARAMS.originx, PARAMS.originy, Math.toRadians(PARAMS.initialHeading)));
//
//}
