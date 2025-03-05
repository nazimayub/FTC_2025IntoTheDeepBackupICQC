package org.firstinspires.ftc.teamcode.base.bot;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.base.commands.*;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

public class Actions {
    //Specimen
    public static ParallelCommandGroup SpecimenGrabAction(Robot bot) {
        return new ParallelCommandGroup(
                new ServoCommand(bot.outtakeClaw, Const.release),
                new SlideResetCommand(bot.slide, bot.vLimit),
                new ServoCommand(bot.intakeClawRot, .3),
                new SlideResetCommand(bot.hSlide, bot.hLimit),
                new ServoCommand(bot.outtakeClawTwist, Const.untwist),
                new ServoCommand(bot.outtakeClawDistRight, 1-Const.distSpecimenGrab),
                new ServoCommand(bot.outtakeClawDistLeft, Const.distSpecimenGrab),
                new ServoCommand(bot.outtakeClawRot, Const.rotSpecimenGrab)
        );
    }

    public static SequentialCommandGroup SpecimenScoreAction(Robot bot) {
        return new SequentialCommandGroup(
                new ServoCommand(bot.outtakeClaw, .2),
                new WaitCommand(bot.pause, 300),
                new ParallelCommandGroup(
                        new ServoCommand(bot.outtakeClawDistRight, 1-Const.distSpecimenGrabFinal),
                        new ServoCommand(bot.outtakeClawDistLeft, Const.distSpecimenGrabFinal),
                        new ServoCommand(bot.outtakeClawRot, Const.rotSpecimenScore),
                        new ServoCommand(bot.outtakeClawTwist, Const.twist),
                        new SetPIDFSlideArmCommand(bot.slide, 270)
                )
        );
    }

    public static ParallelCommandGroup SpecimenScoreReverseAction(Robot bot) {
        return new ParallelCommandGroup(
            new ServoCommand(bot.outtakeClaw, .25),
            new ServoCommand(bot.outtakeClawDistRight, 1-Const.distSpecimenGrabFinal),
            new ServoCommand(bot.outtakeClawDistLeft, Const.distSpecimenGrabFinal),
            new ServoCommand(bot.outtakeClawRot, Const.rotSpecimenScore),
            new ServoCommand(bot.outtakeClawTwist, Const.twist),
            new SetPIDFSlideArmCommand(bot.slide, 270)
        );
    }

    //Samples
    public static SequentialCommandGroup IntakeAction(Robot bot, boolean accept) {
        if(accept)
            return new SequentialCommandGroup(
                    new IntakeCommand(bot.intake, -.8)
            );
        return new SequentialCommandGroup(
            new IntakeCommand(bot.intake, .8)
        );
    }

    public static SequentialCommandGroup IntakeRestAction(Robot bot) {
        return new SequentialCommandGroup(
                new IntakeCommand(bot.intake, 0)
        );
    }

    public static SequentialCommandGroup IntakeAutoAction(Robot bot, boolean accept, int time) {
        if(accept)
            return new SequentialCommandGroup(
                    new IntakeAutoCommand(bot.intakeAuto, -.8, time)
            );
        return new SequentialCommandGroup(
            new IntakeAutoCommand(bot.intakeAuto, .8, time)
        );
    }

    public static SequentialCommandGroup SubmersibleIntakeAction(Robot bot) {
        return new SequentialCommandGroup(
                new ServoCommand(bot.intakeClawRot, 0.4),
                new SetPIDFSlideArmCommand(bot.hSlide, -670),
                new ServoCommand(bot.intakeClawRot, 0.12)
        );
    }

    public static SequentialCommandGroup TransferAction(Robot bot) {
        return new SequentialCommandGroup(
                new ServoCommand(bot.outtakeClaw, Const.release),
                new ServoCommand(bot.intakeClawRot, .3),
                new ServoCommand(bot.outtakeClawDistLeft, 1),
                new ServoCommand(bot.outtakeClawDistRight, 0),
                new ServoCommand(bot.outtakeClawRot, 0.7),
                new ServoCommand(bot.outtakeClawTwist, 0.924),
                new SlideResetCommand(bot.slide, bot.vLimit),
                new SlideResetCommand(bot.hSlide, bot.hLimit),
                new WaitCommand(bot.pause, 300),
                new ServoCommand(bot.outtakeClawRot, 0.83),
                new WaitCommand(bot.pause, 300),
                new ServoCommand(bot.intakeClawRot, 0.36),
                new WaitCommand(bot.pause, 300),
                new ServoCommand(bot.outtakeClaw, Const.grab),
                new WaitCommand(bot.pause, 300),
                new ServoCommand(bot.intakeClawRot, .2),
                new SetPIDFSlideArmCommand(bot.slide, 200)
        );
    }

    public static ParallelCommandGroup HighBasketAction(Robot bot) {
        return new ParallelCommandGroup(
                new ServoCommand(bot.outtakeClawRot, 0.5),
                new ServoCommand(bot.outtakeClawDistRight, 1-0.378),
                new ServoCommand(bot.outtakeClawDistLeft, 0.378),
                new SetPIDFSlideArmCommand(bot.slide, 1300)
        );
    }

    public static SequentialCommandGroup ReleaseAction(Robot bot) {
        return new SequentialCommandGroup(
                new ServoCommand(bot.outtakeClaw, Const.release)
        );
    }

    //Default
    public static ParallelCommandGroup ResetAction(Robot bot) {
        return new ParallelCommandGroup(
            new SlideResetCommand(bot.slide, bot.vLimit),
            new ServoCommand(bot.outtakeClawDistLeft, 1),
            new ServoCommand(bot.outtakeClawDistRight, 0),
            new ServoCommand(bot.outtakeClawRot, 0.7),
            new ServoCommand(bot.outtakeClawTwist, 0.924)
        );
    }

    public static FollowPathCommand FollowPathAction(Robot bot, PathChain path) {
        return new FollowPathCommand(
                bot.follower.getFollower(),
                path,
                true,
                1
        );
    }

    public static ParallelCommandGroup InitAutoAction(Robot bot, Robot.Mode m) {
        return new ParallelCommandGroup(
          new ServoCommand(bot.outtakeClaw, Const.grab),
          new ServoCommand(bot.intakeClawRot, .58)
        );
    }

    public static void InitTeleAction(Robot bot, Robot.Mode m) {
        bot.drive.setDefaultCommand(new DriveCommand(bot.drive, bot.base));
    }
}