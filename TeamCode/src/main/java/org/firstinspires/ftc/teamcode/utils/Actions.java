package org.firstinspires.ftc.teamcode.utils;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Const;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.subsystems.*;

public class Actions {

    public static ParallelCommandGroup SpecimenGrabAction(ServoSubsystem outtakeClaw, PIDFSlideSubsystem slide, LimitSwitchSubsystem vLimit, ServoSubsystem intakeClawRot, PIDFSingleSlideSubsystem hSlide, LimitSwitchSubsystem hLimit, ServoSubsystem outtakeClawTwist, ServoSubsystem outtakeClawDistRight, ServoSubsystem outtakeClawDistLeft, ServoSubsystem outtakeClawRot) {
        return new ParallelCommandGroup(
            new ServoCommand(outtakeClaw, Const.release),
            new SlideResetCommand(slide, vLimit),
            new ServoCommand(intakeClawRot, .3),
            new SlideResetCommand(hSlide, hLimit),
            new ServoCommand(outtakeClawTwist, Const.untwist),
            new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrab),
            new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrab),
            new ServoCommand(outtakeClawRot, Const.rotSpecimenGrab)
        );
    }

    public static SequentialCommandGroup SpecimenScoreAction(ServoSubsystem outtakeClaw, WaitSubsystem pause, ServoSubsystem outtakeClawDistRight, ServoSubsystem outtakeClawDistLeft, ServoSubsystem outtakeClawRot, ServoSubsystem outtakeClawTwist, PIDFSlideSubsystem slide) {
        return new SequentialCommandGroup(
            new ServoCommand(outtakeClaw, .2),
            new WaitCommand(pause, 300),
            new ParallelCommandGroup(
                new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrabFinal),
                new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrabFinal),
                new ServoCommand(outtakeClawRot, Const.rotSpecimenScore),
                new ServoCommand(outtakeClawTwist, Const.twist),
                new SetPIDFSlideArmCommand(slide, 270)
            )
        );
    }

    public static ParallelCommandGroup SpecimenScoreReverseAction(ServoSubsystem outtakeClaw, WaitSubsystem pause, ServoSubsystem outtakeClawDistRight, ServoSubsystem outtakeClawDistLeft, ServoSubsystem outtakeClawRot, ServoSubsystem outtakeClawTwist, PIDFSlideSubsystem slide) {
        return new ParallelCommandGroup(
            new ServoCommand(outtakeClaw, .25),
            new WaitCommand(pause, 300),
            new ServoCommand(outtakeClawDistRight, 1-Const.distSpecimenGrabFinal),
            new ServoCommand(outtakeClawDistLeft, Const.distSpecimenGrabFinal),
            new ServoCommand(outtakeClawRot, Const.rotSpecimenScore),
            new ServoCommand(outtakeClawTwist, Const.twist),
            new SetPIDFSlideArmCommand(slide, 270)
        );
    }

    public static SequentialCommandGroup ShiftGearAction(PIDFSlideSubsystem slide, PIDFSlideSubsystem tSlide, LimitSwitchSubsystem vLimit, double gear, ServoSubsystem shifter, WaitSubsystem pause, double hangHeight) {
        return new SequentialCommandGroup(
            new SlideResetCommand(slide, vLimit),
            new ServoCommand(shifter, gear),
            new SetSlideArmCommand(slide, 1),
            new WaitCommand(pause, 1000),
            new SetSlideArmCommand(tSlide, hangHeight),
            new SlideResetCommand(tSlide, vLimit)
        );
    }

    public static SequentialCommandGroup TransferAction(ServoSubsystem outtakeClaw, ServoSubsystem intakeClawRot, ServoSubsystem outtakeClawDistLeft, ServoSubsystem outtakeClawDistRight, ServoSubsystem outtakeClawRot, ServoSubsystem outtakeClawTwist, PIDFSlideSubsystem slide, PIDFSingleSlideSubsystem hSlide, LimitSwitchSubsystem vLimit, LimitSwitchSubsystem hLimit, WaitSubsystem pause) {
        return new SequentialCommandGroup(
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
            new ServoCommand(outtakeClaw, Const.grab),
            new WaitCommand(pause, 300),
            new ServoCommand(intakeClawRot, .2),
            new SetPIDFSlideArmCommand(slide, 200)
        );
    }

    public static ParallelCommandGroup RaiseToBasketAction(ServoSubsystem outtakeClawRot, ServoSubsystem outtakeClawDistRight, ServoSubsystem outtakeClawDistLeft, PIDFSlideSubsystem slide) {
        return new ParallelCommandGroup(
            new ServoCommand(outtakeClawRot, 0.5),
            new ServoCommand(outtakeClawDistRight, 1-0.378),
            new ServoCommand(outtakeClawDistLeft, 0.378),
            new SetPIDFSlideArmCommand(slide, 1300)
        );
    }

    public static SequentialCommandGroup RaiseToBasketAutoAction(ServoSubsystem outtakeClawRot, ServoSubsystem outtakeClawDistRight, ServoSubsystem outtakeClawDistLeft, PIDFSlideSubsystem slide) {
        return new SequentialCommandGroup(
                new SetPIDFSlideArmCommand(slide, 1300),
                new ServoCommand(outtakeClawRot, 0.5),
                new ServoCommand(outtakeClawDistRight, 1-0.378),
                new ServoCommand(outtakeClawDistLeft, 0.378)
        );
    }
    public static SequentialCommandGroup BasketSlides(PIDFSlideSubsystem slide) {
        return new SequentialCommandGroup(
                new SetPIDFSlideArmCommand(slide, 1300)
        );
    }
    public static SequentialCommandGroup BasketServos(ServoSubsystem outtakeClawRot, ServoSubsystem outtakeClawDistRight, ServoSubsystem outtakeClawDistLeft) {
        return new SequentialCommandGroup(
                new ServoCommand(outtakeClawRot, 0.5),
                new ServoCommand(outtakeClawDistRight, 1-0.378),
                new ServoCommand(outtakeClawDistLeft, 0.378)
        );
    }

    public static ParallelCommandGroup VSlideDownAction(PIDFSlideSubsystem slide, LimitSwitchSubsystem vLimit, ServoSubsystem outtakeClawDistLeft, ServoSubsystem outtakeClawDistRight, ServoSubsystem outtakeClawRot, ServoSubsystem outtakeClawTwist) {
        return new ParallelCommandGroup(
            new SlideResetCommand(slide, vLimit),
            new ServoCommand(outtakeClawDistLeft, 1),
            new ServoCommand(outtakeClawDistRight, 0),
            new ServoCommand(outtakeClawRot, 0.7),
            new ServoCommand(outtakeClawTwist, 0.924)
        );
    }

        public static SequentialCommandGroup IntakeAcceptAction(IntakeSubsystem intake) {
                return new SequentialCommandGroup(
                        new IntakeCommand(intake, -1)
                );
        }

        public static SequentialCommandGroup IntakeRejectAction(IntakeSubsystem intake) {
                return new SequentialCommandGroup(
                        new IntakeCommand(intake, 1)
                );
        }

        public static SequentialCommandGroup IntakeRestAction(IntakeSubsystem intake) {
                return new SequentialCommandGroup(
                        new IntakeCommand(intake, 0)
                );
        }

        public static ParallelCommandGroup IntakeAutoAccept(IntakeAutoSubsystem intake, PIDFSingleSlideSubsystem hSlide) {
            return new ParallelCommandGroup(
                new SetPIDFSlideArmCommand(hSlide, -800),
                new IntakeAutoCommand(intake, -0.8, 1)
            );
        }

        public static SequentialCommandGroup IntakeAutoReject(IntakeAutoSubsystem intake) {
            return new SequentialCommandGroup(
                    new IntakeAutoCommand(intake, 0.8, 1)
            );
        }

        public static SequentialCommandGroup HSlideAction(PIDFSingleSlideSubsystem hSlide, ServoSubsystem intakeClawRot) {
                return new SequentialCommandGroup(
                        new ServoCommand(intakeClawRot, 0.4),
                        new SetPIDFSlideArmCommand(hSlide, -670),
                        new ServoCommand(intakeClawRot, 0.12)
                );
        }

        public static SequentialCommandGroup ClawReleaseAction(ServoSubsystem servo) {
                return new SequentialCommandGroup(
                        new ServoCommand(servo, Const.release)
                );
        }
}