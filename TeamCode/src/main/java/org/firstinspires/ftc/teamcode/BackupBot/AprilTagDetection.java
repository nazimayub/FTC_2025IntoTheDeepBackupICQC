package org.firstinspires.ftc.teamcode.BackupBot;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "April Tag Demo", group = ".Auton")

public class AprilTagDetection extends LinearOpMode {

    private Limelight3A limelight;

    private final double TARGET_X = 0;
    private final double TARGET_A = 8;
    private double movePower = 0;
    private double turnPower = 0;
    private final double KP_TURN = 0.05;
    private final double KP_MOVE = 1;
    private final double TX_DEADZONE = 0.5;
    private final double TA_DEADZONE = 1;



    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);
        limelight.start();
        waitForStart();

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();
            // int tagID = result.getDetectorResults()
            telemetry.addData("tx", result.getTx());
            telemetry.addData("ty", result.getTy());
            telemetry.addData("ta", result.getTa());
            telemetry.update();

            if (result.getTx() > TARGET_X) {
                telemetry.addData("specifics", "need to turn right");
            } else if (result.getTx() < TARGET_X){
                telemetry.addData("specifics", "need to turn left");
            }
            if (result.getTa() < TARGET_A) {
                telemetry.addData("specifics", "need to move forward");
            } else if (result.getTa() >= TARGET_A || result.getTa() == 0 && result.getTx() == 0 && result.getTy() == 0) {
                telemetry.addData("specifics", "need to stop");
            }
        }

    }
}