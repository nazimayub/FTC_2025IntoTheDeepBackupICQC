package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Const;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;


import java.util.ArrayList;

@Autonomous(group = "Tuner")
/*
 * Run your bot using gamepad1 to each Pose you want for auto.
 * Hit the left bumper once you've reached a Pose you want to save.
 */
public class PoseFinder extends OpMode {
    private Follower follower;
    private static GamepadEx base;
    private static Drive drive;
    private ArrayList<Pose> poses;
    private boolean previousLeftBumper = false;  // For edge detection

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        base = new GamepadEx(gamepad1);
        drive = new Drive(hardwareMap, Const.imu,
                new MotorConfig(Const.fr, Const.bl, Const.br, Const.fl),
                new MotorDirectionConfig(false, true, false, true));
        drive.setDefaultCommand(new DriveCommand(drive, base));
        poses = new ArrayList<>();
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();

        if (follower != null) {
            Pose currentPose = follower.getPose();
            telemetry.addData("X", String.format("%.2f", currentPose.getX()));
            telemetry.addData("Y", String.format("%.2f", currentPose.getY()));
            telemetry.addData("Heading", String.format("%.2f", currentPose.getHeading()));

            // Edge detection for left bumper press
            if (gamepad1.left_bumper && !previousLeftBumper) {
                poses.add(new Pose(currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
            }
            previousLeftBumper = gamepad1.left_bumper;

            for (Pose p : poses) {
                telemetry.addLine(String.format("Pose: (%.2f, %.2f, %.2f)", p.getX(), p.getY(), p.getHeading()));
            }
        }
    }


    @Override
    public void stop() {
        CommandScheduler.getInstance().reset();  // Prevent memory leaks
    }
}
