//package org.firstinspires.ftc.teamcode.opmodes.auton;
//
//import com.arcrobotics.ftclib.command.CommandScheduler;
//import com.arcrobotics.ftclib.command.button.GamepadButton;
//import com.arcrobotics.ftclib.gamepad.GamepadEx;
//import com.arcrobotics.ftclib.gamepad.GamepadKeys;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.localization.Pose;
//import com.pedropathing.util.Constants;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//
//import org.firstinspires.ftc.teamcode.utils.Robot.Const;
//import org.firstinspires.ftc.teamcode.commands.DriveCommand;
//import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
//import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
//import org.firstinspires.ftc.teamcode.subsystems.Drive;
//import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.ServoSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.WaitSubsystem;
//import org.firstinspires.ftc.teamcode.utils.*;
//
//import java.util.ArrayList;
//
//@Autonomous(group = "Tuner")
///*
// * Run your bot using your Solo code to each Pose you want for auto.
// * Hit the left bumper once you've
// * reached a Pose you want to save.
// */
//public class PoseFinder extends OpMode {
//    private Follower follower;
//    private ArrayList<Pose> poses;
//    private boolean previousLeftBumper = false;
//
//    @Override
//    public void init() {
//        Constants.setConstants(FConstants.class, LConstants.class);
//        follower = new Follower(hardwareMap);
//        poses = new ArrayList<>();
//
//        // DRIVING
//        (Robot.drive).setDefaultCommand(new DriveCommand(Robot.drive,Robot.base));
//        new GamepadButton(Robot.base, GamepadKeys.Button.DPAD_LEFT).whenPressed(Actions.SpecimenGrabAction());
//        new GamepadButton(Robot.base, GamepadKeys.Button.DPAD_RIGHT).whenPressed(Actions.SpecimenScoreAction());
//        new GamepadButton(Robot.base, GamepadKeys.Button.DPAD_UP).whenPressed(Actions.ShiftGearAction());
//
//        // OPERATING
//        new GamepadButton(Robot.base, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(Actions.IntakeAction(true))
//                .whenReleased(Actions.IntakeRestAction());
//        new GamepadButton(Robot.base, GamepadKeys.Button.LEFT_BUMPER).whenPressed(Actions.IntakeAction(false))
//                .whenReleased(Actions.IntakeRestAction());
//        new GamepadButton(Robot.base, GamepadKeys.Button.A).whenPressed(Actions.SetSampleGrabAction());
//        new GamepadButton(Robot.base, GamepadKeys.Button.B).whenPressed(Actions.TransferAction());
//        new GamepadButton(Robot.base, GamepadKeys.Button.Y).whenPressed(Actions.SetSampleScoreAction());
//        new GamepadButton(Robot.base, GamepadKeys.Button.X).whenPressed(Actions.SampleScoreAction());
//        new GamepadButton(Robot.base, GamepadKeys.Button.DPAD_DOWN).whenPressed(Actions.ResetAction());
//    }
//
//    @Override
//    public void loop() {
//        follower.update();
//        CommandScheduler.getInstance().run();
//
//        if (follower != null) {
//            Pose currentPose = follower.getPose();
//            telemetry.addData("X", String.format("%.2f", currentPose.getX()));
//            telemetry.addData("Y", String.format("%.2f", currentPose.getY()));
//            telemetry.addData("Heading", String.format("%.2f", Math.toDegrees(currentPose.getHeading())));
//
//            if (gamepad1.left_bumper && !previousLeftBumper) {
//                poses.add(new Pose(currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
//            }
//            previousLeftBumper = gamepad1.left_bumper;
//
//            for (Pose p : poses) {
//                telemetry.addLine(String.format("Pose: (%.2f, %.2f, %.2f)", p.getX(), p.getY(), Math.toDegrees(p.getHeading())));
//            }
//        }
//    }
//
//    @Override
//    public void stop() {
//        CommandScheduler.getInstance().reset();
//    }
//}
