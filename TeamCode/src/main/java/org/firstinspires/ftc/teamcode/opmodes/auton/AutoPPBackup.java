//package org.firstinspires.ftc.teamcode.auton;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.Servo;
//
//import org.firstinspires.ftc.teamcode.Const;
//import org.firstinspires.ftc.teamcode.pedroPathing.follower.*;
//import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
//import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
//import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
//import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
//import org.firstinspires.ftc.teamcode.pedroPathing.util.Timer;
//
//@Autonomous
//public class Auto extends LinearOpMode {
//    public static DcMotorEx lSlide;
//    public static DcMotorEx rSlide;
//    public static Servo outtakeClawDist;
//    public static Servo outtakeClawRot;
//    public static Servo outtakeClaw;
//    public static Servo blocker;
//    public static Servo intake;
//
//    //Paths
//    private Follower follower;
//    private Timer pathTimer;
//    private int pathState;
//    private PathChain scorePreloadPath, moveFromFirstSpecimenScorePath, strafeToSampsPath,
//            moveToSampsPath, moveToFirstSampPath, pushFirstSampPath, moveToSecondSampPath,
//            strafeToSecondSampPath, pushSecondSampPath, moveToThirdSampPath, strafeToThirdSampPath,
//            pushThirdSampPath;
//
//    //Poses
//    private final Pose startPose = new Pose(0.504, 57.802, Math.toRadians(0));
//    private final Pose scorePreloadPose = new Pose(30.319, 57.802, Math.toRadians(0));
//    private final Pose moveFromScorePreloadPose = new Pose(23.319, 57.802, Math.toRadians(0));
//    private final Pose strafeToSampsPose = new Pose(23.319, 25.802, Math.toRadians(0));
//    private final Pose moveToSampsPose = new Pose(50.319, 25.802, Math.toRadians(0));
//
//    private final Pose moveToFirstSampPose = new Pose(50.319, 17.802, Math.toRadians(0));
//    private final Pose pushFirstSampPose = new Pose(11.00, 17.802, Math.toRadians(0));
//
//    private final Pose moveToSecondSampPose = new Pose(50.319, 17.802, Math.toRadians(0));
//    private final Pose strafeToSecondSampPose = new Pose(50.319, 9.802, Math.toRadians(0));
//    private final Pose pushSecondSampPose = new Pose(11.00, 9.802, Math.toRadians(0));
//
//    private final Pose moveToThirdSampPose = new Pose(65.319, 9.802, Math.toRadians(180));
//    private final Pose strafeToThirdSampPose = new Pose(65.319, -8, Math.toRadians(180));
//    private final Pose pushThirdSampPose = new Pose(8.00, -8, Math.toRadians(180));
//
//    @Override
//    public void runOpMode() {
//        pathTimer = new Timer();
//        pathTimer.resetTimer();
//        follower = new Follower(hardwareMap);
//
//        lSlide = hardwareMap.get(DcMotorEx.class, Const.lSlide);
//        rSlide = hardwareMap.get(DcMotorEx.class, Const.rSlide);
//        outtakeClawDist = hardwareMap.get(Servo.class, Const.outtakeDist);
//        outtakeClawRot = hardwareMap.get(Servo.class, Const.outtakeRot);
//        outtakeClaw = hardwareMap.get(Servo.class, Const.outtakeClaw);
//        blocker = hardwareMap.get(Servo.class, Const.blocker);
//        intake = hardwareMap.get(Servo.class, Const.intakeRot);
//
//        lSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        follower.setStartingPose(startPose);
//        path();
//        pathState = 0;
//
//        waitForStart();
//        while(opModeIsActive()) {
//            follower.update();
//            updatePath();
//            telemetry.addData("Path State", pathState);
//            telemetry.addData("X", follower.getPose().getX());
//            telemetry.addData("Y", follower.getPose().getY());
//            telemetry.addData("Heading", follower.getPose().getHeading());
//            telemetry.update();
//        }
//    }
//
//    public void path() {
//        scorePreloadPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(startPose), new Point(scorePreloadPose)))
//                .setLinearHeadingInterpolation(startPose.getHeading(), scorePreloadPose.getHeading())
//                .build();
//
//        moveFromFirstSpecimenScorePath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(scorePreloadPose), new Point(moveFromScorePreloadPose)))
//                .setLinearHeadingInterpolation(scorePreloadPose.getHeading(), moveFromScorePreloadPose.getHeading())
//                .build();
//
//        strafeToSampsPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(moveFromScorePreloadPose), new Point(strafeToSampsPose)))
//                .setLinearHeadingInterpolation(moveFromScorePreloadPose.getHeading(), strafeToSampsPose.getHeading())
//                .build();
//
//        moveToSampsPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(strafeToSampsPose), new Point(moveToSampsPose)))
//                .setLinearHeadingInterpolation(strafeToSampsPose.getHeading(), moveToSampsPose.getHeading())
//                .build();
//
//        moveToFirstSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(moveToSampsPose), new Point(moveToFirstSampPose)))
//                .setLinearHeadingInterpolation(moveToSampsPose.getHeading(), moveToFirstSampPose.getHeading())
//                .build();
//
//        pushFirstSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(moveToFirstSampPose), new Point(pushFirstSampPose)))
//                .setLinearHeadingInterpolation(moveToFirstSampPose.getHeading(), pushFirstSampPose.getHeading())
//                .build();
//
//        moveToSecondSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(pushFirstSampPose), new Point(moveToSecondSampPose)))
//                .setLinearHeadingInterpolation(pushFirstSampPose.getHeading(), moveToSecondSampPose.getHeading())
//                .build();
//
//        strafeToSecondSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(moveToSecondSampPose), new Point(strafeToSecondSampPose)))
//                .setLinearHeadingInterpolation(moveToSecondSampPose.getHeading(), strafeToSecondSampPose.getHeading())
//                .build();
//
//        pushSecondSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(strafeToSecondSampPose), new Point(pushSecondSampPose)))
//                .setLinearHeadingInterpolation(strafeToSecondSampPose.getHeading(), pushSecondSampPose.getHeading())
//                .build();
//
//        moveToThirdSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(pushSecondSampPose), new Point(moveToThirdSampPose)))
//                .setLinearHeadingInterpolation(pushSecondSampPose.getHeading(), moveToThirdSampPose.getHeading())
//                .build();
//
//        strafeToThirdSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(moveToThirdSampPose), new Point(strafeToThirdSampPose)))
//                .setConstantHeadingInterpolation(strafeToThirdSampPose.getHeading())
//                .build();
//
//       pushThirdSampPath = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(strafeToThirdSampPose), new Point(pushThirdSampPose)))
//                .setConstantHeadingInterpolation(pushThirdSampPose.getHeading())
//                .build();
//    }
//
//    public void setPathState(int num) {
//        pathState = num;
//    }
//
//    public void updatePath() {
//        switch (pathState) {
//            case 0:
//                grabPreload();
//                follower.followPath(scorePreloadPath, true);
//                setPathState(1);
//                break;
//
//            case 1:
//                score();
//                if (follower.getPose().getX() > (scorePreloadPose.getX() - 1)) {
//                    follower.followPath(moveFromFirstSpecimenScorePath, true);
//                    //reset();
//                    setPathState(2);
//                }
//                break;
//
//            case 2:
//                if (follower.getPose().getX() < (moveFromScorePreloadPose.getX() + 1)) {
//                    follower.followPath(strafeToSampsPath, true);
//                    setPathState(3);
//                }
//                break;
//
//            case 3:
//                if (follower.getPose().getY() < (strafeToSampsPose.getY() + 1)) {
//                    follower.followPath(moveToSampsPath, true);
//                    setPathState(4);
//                }
//                break;
//
//            case 4:
//                if (follower.getPose().getX() > (moveToSampsPose.getX() - 1)) {
//                    follower.followPath(moveToFirstSampPath, true);
//                    setPathState(5);
//                }
//                break;
//
//            case 5:
//                if (follower.getPose().getY() < (moveToFirstSampPose.getY() + 1)) {
//                    follower.followPath(pushFirstSampPath, true);
//                    setPathState(6);
//                }
//                break;
//
//            case 6:
//                if (follower.getPose().getX() < (pushFirstSampPose.getX() + 1)) {
//                    follower.followPath(moveToSecondSampPath, true);
//                    setPathState(7);
//                }
//                break;
//
//            case 7:
//                if (follower.getPose().getX() > (moveToSecondSampPose.getX() - 1)) {
//                    follower.followPath(strafeToSecondSampPath, true);
//                    setPathState(8);
//                }
//                break;
//
//            case 8:
//                if (follower.getPose().getY() < (strafeToSecondSampPose.getY() + 1)) {
//                    follower.followPath(pushSecondSampPath, true);
//                    setPathState(9);
//                }
//                break;
//
//            case 9:
//                if (follower.getPose().getX() < (pushSecondSampPose.getX() + 1)) {
//                    follower.followPath(moveToThirdSampPath, true);
//                    setPathState(10);
//                }
//                break;
//
//            case 10:
//                if (follower.getPose().getX() > (moveToThirdSampPose.getX() - 1)) {
//                    follower.followPath(strafeToThirdSampPath, true);
//                    //grabSpecimen();
//                    setPathState(11);
//                }
//                break;
//
//            case 11:
//                if (follower.getPose().getY() < (strafeToThirdSampPose.getY() + 1)) {
//                    follower.followPath(pushThirdSampPath, true);
//                }
//                break;
//        }
//    }
//
//    public void grabPreload() {
//        outtakeClaw.setPosition(Const.grab);
//        outtakeClawDist.setPosition(Const.distBasketPos);
//        outtakeClawRot.setPosition(Const.rotBasketPos);
//        setPos(-575);
//    }
//
//    public void score() {
//        outtakeClawDist.setPosition(Const.distBasketPos - .1);
//        setPos(0);
//    }
//
//    public void setPos(int targetPosition) {
//        lSlide.setTargetPosition(targetPosition);
//        rSlide.setTargetPosition(targetPosition);
//        lSlide.setPower(-1.0);
//        rSlide.setPower(-1.0);
//        lSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        rSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//    }
//
//
//}
