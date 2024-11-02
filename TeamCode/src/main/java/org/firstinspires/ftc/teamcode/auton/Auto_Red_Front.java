/*
package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;

@Autonomous
public class Auto_Red_Front extends LinearOpMode {
    public static class Params {
        public double originx = 12;
        public double originy = 12;
        public double initialHeading = 0;
    }

    public static Drive drive;
    public static PIDFArmSubsystem arm;

    private static PIDFSlideSubsystem slide;
    public static HandSubsystem hand;
    public static ServoIntakeSubsystem intake;
    public static HardwareMap hMap;
    public static Params PARAMS = new Params();

    @Override
    public void runOpMode() {
        MecanumDrive mecDrive = new MecanumDrive(hardwareMap, new Pose2d(PARAMS.originx, PARAMS.originy, Math.toRadians(PARAMS.initialHeading)));
        intake = new ServoIntakeSubsystem(hardwareMap, Constants.intake);
        drive = new Drive(hardwareMap, Constants.imu,new MotorConfig(Constants.fr,Constants.fl,Constants.br,Constants.bl),new MotorDirectionConfig(true,true,true,true));
        arm = new PIDFArmSubsystem(hardwareMap, Constants.arm, 0.01, 0, 0.0001, 0.001, 1926/180);
        hand = new HandSubsystem(hardwareMap, Constants.hand);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.FORWARD, 0.03, 0, 0.0003, 0.2, 0.03, 0, 0.0003, 0.2);

        waitForStart();

        Actions.runBlocking(mecDrive
                .actionBuilder(mecDrive.pose)
                .build());

        slide.set(1);
        arm.set(1);

        while (opModeIsActive() && slide.getTick() != 1 && arm.getTick() != 1) {
            telemetry.addData("Slide Position", slide.getTick());
            telemetry.addData("Arm Position", arm.getTick());
            telemetry.update();
        }

        releaseSpecimen();

        Actions.runBlocking(mecDrive
                .actionBuilder(mecDrive.pose)
                .build());
    }

    private void releaseSpecimen() {
    }
}

*/