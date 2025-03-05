//package org.firstinspires.ftc.teamcode.opmodes.auton;
//
//import com.arcrobotics.ftclib.command.Command;
//import com.arcrobotics.ftclib.command.CommandScheduler;
//import com.arcrobotics.ftclib.command.ParallelCommandGroup;
//import com.arcrobotics.ftclib.command.SequentialCommandGroup;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.localization.Pose;
//import com.pedropathing.pathgen.BezierCurve;
//import com.pedropathing.pathgen.BezierLine;
//import com.pedropathing.pathgen.PathChain;
//import com.pedropathing.pathgen.Point;
//import com.pedropathing.util.Constants;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.utils.Robot.Const;
//import org.firstinspires.ftc.teamcode.commands.FollowPathCommand;
//import org.firstinspires.ftc.teamcode.commands.ServoCommand;
//import org.firstinspires.ftc.teamcode.commands.WaitCommand;
//import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
//import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
//import org.firstinspires.ftc.teamcode.subsystems.IntakeAutoSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.ServoSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.WaitSubsystem;
//import org.firstinspires.ftc.teamcode.utils.Robot.Actions;
//
//import java.util.ArrayList;
//
//@Autonomous(group = "Auton")
//public class AutoSamp extends OpMode {
//
//    public static Pose score = new Pose(-4.09, -9.43, Math.toRadians(133.2));
//    public enum AutoPaths {
//        PRELOAD(
//                new Pose( 0, 0, 0),
//                score
//        ),
//
//        GRAB_SAMPLE_1(
//                score,
//                new Pose(-7.5, -8.25, Math.toRadians(164.2))
//        ),
//
//        SCORE_SAMPLE_1(
//                new Pose(-7.5, -8.25, Math.toRadians(164.2)),
//                score
//        ),
//
//        GRAB_SAMPLE_2(
//                score,
//                new Pose(-8.43, -9.03, Math.toRadians(181.83))
//        ),
//
//        SCORE_SAMPLE_2(
//                new Pose(-8.43, -9.03, Math.toRadians(181.83)),
//                score
//        ),
//
//        GRAB_SAMPLE_3(
//                score,
//                new Pose(-11.02, -9.35, Math.toRadians(200.09))
//        ),
//
//        SCORE_SAMPLE_3(
//                new Pose(-11.02, -9.35, Math.toRadians(200.09)),
//                score
//        );
//
//        private final Pose[] poses;
//
//        AutoPaths(Pose... poses) {
//            this.poses = poses;
//        }
//
//        public PathChain line(Follower follower) {
//            PathChain path = follower.pathBuilder()
//                    .addPath(new BezierLine(new Point(poses[0]), new Point(poses[1])))
//                    .setLinearHeadingInterpolation(poses[0].getHeading(), poses[1].getHeading())
//                    .build();
//            return path;
//        }
//
//        public PathChain curve(Follower follower) {
//            ArrayList<Point> controlPoints = new ArrayList<>();
//            for (Pose pose : poses) {
//                controlPoints.add(new Point(pose.getX(), pose.getY(), 1));
//            }
//
//            BezierCurve bezierCurve = new BezierCurve(controlPoints);
//
//            PathChain path = follower.pathBuilder()
//                    .addPath(bezierCurve)
//                    .setLinearHeadingInterpolation(poses[0].getHeading(), poses[poses.length - 1].getHeading())
//                    .build();
//
//            return path;
//        }
//
//        public Pose[] getPoses() {
//            return poses;
//        }
//    }
//
//    // Paths
//    private Follower follower;
//
//    public static ServoSubsystem outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawTwist, outtakeClawDistRight, outtakeClawDistLeft;
//    public static IntakeAutoSubsystem intake;
//    public static LimitSwitchSubsystem vLimit, hLimit;
//    public static PIDFSlideSubsystem slide;
//    public static PIDFSingleSlideSubsystem hSlide;
//    public static WaitSubsystem pause;
//
//    @Override
//    public void init() {
//        Constants.setConstants(FConstants.class, LConstants.class);
//        follower = new Follower(hardwareMap);
//
//        intake = new IntakeAutoSubsystem(hardwareMap, Const.intake, new ElapsedTime());
//        hSlide = new PIDFSingleSlideSubsystem(hardwareMap, Const.hSlide, -0.02, 0, 0, 0.0);
//        slide = new PIDFSlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, 0.1, 0, 0.000004, 0.21, 0.1, 0, 0.000004, 0.21);
//        pause = new WaitSubsystem();
//        outtakeClaw = new ServoSubsystem(hardwareMap, Const.outtakeClaw);
//        intakeClawDist = new ServoSubsystem(hardwareMap, Const.intakeDist);
//        intakeClawRot = new ServoSubsystem(hardwareMap, Const.intakeRot);
//        outtakeClawDistLeft = new ServoSubsystem(hardwareMap, Const.outtakeDistLeft);
//        outtakeClawDistRight = new ServoSubsystem(hardwareMap, Const.outtakeDistRight);
//        vLimit = new LimitSwitchSubsystem(hardwareMap, Const.vLimit);
//        hLimit = new LimitSwitchSubsystem(hardwareMap, Const.hLimit);
//        outtakeClawRot = new ServoSubsystem(hardwareMap, Const.outtakeRot);
//        outtakeClawTwist = new ServoSubsystem(hardwareMap, Const.outtakeTwist);
//
//        follower.setPose(AutoPaths.PRELOAD.getPoses()[0]);
//        outtakeClaw.set(Const.grab);
//        intakeClawRot.set(.58);
//
//        Command scorePreload =
//                new SequentialCommandGroup(
//                        new ParallelCommandGroup(
//                                new ServoCommand(outtakeClawTwist, .924),
//                                new ServoCommand(outtakeClawRot, .8),
//                                new ServoCommand(outtakeClawDistLeft, .5),
//                                new ServoCommand(outtakeClawDistRight, .5)
//                        ),
//                        new FollowPathCommand(follower, AutoPaths.PRELOAD.curve(follower), true, 0.8),
//                        Actions.RaiseToBasketAutoAction(),
//                        new WaitCommand(pause, 300),
//                        Actions.ClawReleaseAction()
//                );
//
//        Command scoreFirstSamp =
//                new SequentialCommandGroup(
//                        new FollowPathCommand(follower, AutoPaths.GRAB_SAMPLE_1.curve(follower), true, 0.8),
//                        Actions.HSlideAction(),
//                        Actions.VSlideDownAction(),
//                        Actions.IntakeAutoAccept(),
//                        Actions.TransferAction(),
//                        new ParallelCommandGroup(
//                                Actions.RaiseToBasketAction(),
//                                new FollowPathCommand(follower, AutoPaths.SCORE_SAMPLE_1.curve(follower), true, 0.8)
//                        ),
//                        new WaitCommand(pause, 300),
//                        Actions.ClawReleaseAction(),
//                        new WaitCommand(pause, 300)
//                );
//        Command scoreSecondSamp =
//                new SequentialCommandGroup(
//                        new FollowPathCommand(follower, AutoPaths.GRAB_SAMPLE_2.curve(follower), true, 0.8),
//                        new ParallelCommandGroup(
//                                new ServoCommand(intakeClawRot, .3),
//                                Actions.VSlideDownAction()
//                        ),
//                        Actions.HSlideAction(),
//                        Actions.VSlideDownAction(),
//                        Actions.IntakeAutoAccept(),
//                        Actions.TransferAction(),
//                        new ParallelCommandGroup(
//                                Actions.RaiseToBasketAction(),
//                                new FollowPathCommand(follower, AutoSamp.AutoPaths.SCORE_SAMPLE_2.curve(follower), true, 0.8)
//                        ),
//                        new WaitCommand(pause, 300),
//                        Actions.ClawReleaseAction(),
//                        new WaitCommand(pause, 300)
//                );
//        Command scoreThirdSamp =
//                new SequentialCommandGroup(
//                        new FollowPathCommand(follower, AutoPaths.GRAB_SAMPLE_3.curve(follower), true, 0.8),
//                        new ParallelCommandGroup(
//                                new ServoCommand(intakeClawRot, .3),
//                                Actions.VSlideDownAction()
//                        ),
//                        Actions.HSlideAction(),
//                        Actions.VSlideDownAction(),
//                        Actions.IntakeAutoAccept(),
//                        Actions.TransferAction(),
//                        new ParallelCommandGroup(
//                                Actions.RaiseToBasketAction(),
//                                new FollowPathCommand(follower, AutoPaths.SCORE_SAMPLE_3.curve(follower), true, 0.8)
//                        ),
//                        new WaitCommand(pause, 300),
//                        Actions.ClawReleaseAction(),
//                        new WaitCommand(pause, 300)
//                );
//
//
//        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
//                scorePreload,
//                scoreFirstSamp,
//                scoreSecondSamp,
//                scoreThirdSamp
//        ));
//    }
//
//    @Override
//    public void loop() {
//        follower.update();
//        CommandScheduler.getInstance().run();
//
//        telemetry.addData("X", follower.getPose().getX());
//        telemetry.addData("Y", follower.getPose().getY());
//        telemetry.addData("Heading", follower.getPose().getHeading());
//        telemetry.addData("Busy", follower.isBusy());
//        telemetry.update();
//    }
//}
