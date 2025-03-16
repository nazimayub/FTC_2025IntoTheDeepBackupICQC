package org.firstinspires.ftc.teamcode.BackupBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

@Autonomous(name = "Limelight Auton", group = "Auton")
public class LimelightAuton extends LinearOpMode {
    private DcMotor fL, fR, bL, bR;
    private PhotonCamera limelight;

    public void runOpMode() {
        fL = hardwareMap.get(DcMotor.class, "fL");
        fR = hardwareMap.get(DcMotor.class, "fR");
        bL = hardwareMap.get(DcMotor.class, "bL");
        bR = hardwareMap.get(DcMotor.class, "bR");

        fR.setDirection(DcMotor.Direction.REVERSE);
        bR.setDirection(DcMotor.Direction.REVERSE);

        limelight = new PhotonCamera("limelight");

        waitForStart();

        while (opModeIsActive()) {
            PhotonPipelineResult result = limelight.getLatestResult();
            if (result.hasTargets()) {
                double xOffset = result.getBestTarget().getYaw();
                double forwardSpeed = 0.2;
                double turnSpeed = -xOffset * 0.02;

                fL.setPower(forwardSpeed + turnSpeed);
                fR.setPower(forwardSpeed - turnSpeed);
                bL.setPower(forwardSpeed + turnSpeed);
                bR.setPower(forwardSpeed - turnSpeed);
            } else {
                fL.setPower(0);
                fR.setPower(0);
                bL.setPower(0);
                bR.setPower(0);
            }
            telemetry.addData("Target Found", result.hasTargets());
            telemetry.addData("X Offset", result.hasTargets() ? result.getBestTarget().getYaw() : "N/A");
            telemetry.update();
        }
    }
}
