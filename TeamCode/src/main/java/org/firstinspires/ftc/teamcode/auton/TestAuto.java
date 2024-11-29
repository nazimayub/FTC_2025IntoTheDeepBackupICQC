////1Plus3Auton
///*
//Copyright 2024 FIRST Tech Challenge Team 21330
//
//Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
//associated documentation files (the "Software"), to deal in the Software without restriction,
//including without limitation the rights to use, copy, modify, merge, publish, distribute,
//sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all copies or substantial
//portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
//NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
//NONINfrINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
//DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING frOM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//*/
//package org.firstinspires.ftc.teamcode.auton;
//
//import android.provider.SyncStateContract;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.hardware.IMU;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
//import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
//import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
//import com.qualcomm.robotcore.hardware.ServoImplEx;
//
//
//
///**
// * This file contains a minimal example of a Linear "OpMode". An OpMode is a 'program' that runs
// * in either the autonomous or the TeleOp period of an FTC match. The names of OpModes appear on
// * the menu of the FTC Driver Station. When an selection is made from the menu, the corresponding
// * OpMode class is instantiated on the Robot Controller and executed.
// *
// * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
// * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
// * added to the Driver Station.
// */
//@Autonomous
//
//public class TestAuto extends LinearOpMode {
//    ElapsedTime timer;
//    DcMotorEx fl, fr, bl, br, lslide, rslide, intake;
//    public Servo outtakeClaw, outtakeClawDist, outtakeClawRot, intakeRot, blocker;
//    IMU imu;
//    private ElapsedTime runtime = new ElapsedTime();
//    static final double COUNTS_PER_MOTOR_REV = 900;
//    static final double DRIVE_GEAR_REDUCTION = 1.0;
//    static final double WHEEL_DIAMETER_INCHES = 4.0;
//    static final double COUNTS_PER_INCH = ((COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
//            (WHEEL_DIAMETER_INCHES * 3.1415)) / 3.5625;
//
//    @Override
//    public void runOpMode() {
//
//        timer = new ElapsedTime();
//
//        initHardware(hardwareMap);
//
//        double power = 1;
//        double lefty1, leftx1, rightx1, armpos, sliderpos;
//
//        imu.resetYaw();
//
//        specimenCycle();
//        strafe(1.0, 55.0, 0.0, 2.1);
//
//        //Setup for S1
//
//        drive(1.0, 20.0, 0.0, 1.10);
//        // drive(0.5, 0.0, 90.0, 0.75);
//        //S1 Push
//        drive(1.0, 13.0, 0.0, 0.8);
//        strafe(0.4, 75.0, 0.0, 2.0);
//        strafe(1.0, -70.0, 0.0, 1.0);
//        //S2 Push
//        drive(1.0, 10.0, 0.0, 1.0);
//        strafe(0.4, 75.0, 0.0, 2.0);
//        strafe(1.0, -30.0, 0.0, 1.0);
//
//        // Grab Pre-load
//        hOffset-=getAngle();
//        // drive(0.5, 0.0, 90.0, 0.75);
//        strafe(1.0, -30.0, 0.0, 1.0);
//        grabSpecimen(0.30);
//
//    }
//    public void specimen() {
//        lservo.setPwmEnable();
//        lservo.setPosition(0.99);
//        rservo.setPosition(0.01);
//        sleep(750);
//        claw.setPosition(0.37);
//        lservo.setPwmDisable();
//        // lservo.setPosition(0.40);
//        rservo.setPosition(0.70);
//        claw.setPosition(0.01);
//    }
//    public void specimenCycle() {
//        hOffset+=getAngle();
//        extendTarget=260;
//        claw.setPosition(0.01);
//        drive(1.0, 34.0, 0.0, 0.8);
//
//        specimen();
//        extendTarget=0;
//        claw.setPosition(0.01);
//        drive(1.0, -5.0, 0.0, 0.5);
//        claw.setPosition(0.37);
//    }
//    public void grabSpecimen(double speed) {
//        // lservo.setPosition(0.01)
//        rservo.setPosition(0.27); //Tweaking needed
//        claw.setPosition(0.315);
//        drive(speed, 40.0, 0.0, 1.1);
//        claw.setPosition(0.01);
//        sleep(300);
//        rservo.setPosition(0.70);
//        drive(1.0, -10.0, 0.0, 0.25);
//    }
//
//
//    public void drive(double speed, double inches, double angle, double timeoutS) {
//        // Ensure that the OpMode is still active
//        if (opModeIsActive()) {
//            wrist.setPwmEnable();
//            ls.setPwmEnable();
//            rs.setPwmEnable();
//            fl.setTargetPosition(fl.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH) + (int)(angle * 4.25));
//            fr.setTargetPosition(fr.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH) - (int)(angle * 4.25));
//            bl.setTargetPosition(bl.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH) + (int)(angle * 4.25));
//            br.setTargetPosition(br.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH) - (int)(angle * 4.25));
//
//
//            // reset the timeout time and start motion.
//            runtime.reset();
//
//            while (opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (fl.isBusy() && fr.isBusy() && bl.isBusy() && br.isBusy())) {
//
//                double realSpeed = Math.min(runtime.seconds() * speed * 2, speed);
//
//                fl.setPower(realSpeed);
//                bl.setPower(realSpeed);
//                fr.setPower(realSpeed);
//                br.setPower(realSpeed);
//
//                // Display it for the driver.
//                telemetry.addData("Angle: ", getAngle());
//                telemetry.addData("Running to", " %7d :%7d", fl.getTargetPosition(), fr.getTargetPosition());
//                telemetry.addData("Currently at", " at %7d :%7d", fl.getCurrentPosition(), fr.getCurrentPosition());
//                telemetry.update();
//            }
//
//            // Stop all motion;
//            fl.setPower(0);
//            bl.setPower(0);
//            fr.setPower(0);
//            br.setPower(0);
//
//            sleep(100); // optional pause after each move.
//        }
//    }
//
//    public void strafe(double speed, double inches, double angle, double timeoutS) {
//
//        // Ensure that the OpMode is still active
//        if (opModeIsActive()) {
//            wrist.setPwmEnable();
//            ls.setPwmEnable();
//            rs.setPwmEnable();
//            // Determine new target position, and pass to motor controller
//            fl.setTargetPosition((fl.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH)) + (int)(angle * 6.5));
//            bl.setTargetPosition((bl.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH)) + (int)(angle * 6.5));
//            fr.setTargetPosition((fr.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH)) - (int)(angle * 6.5));
//            br.setTargetPosition((br.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH)) - (int)(angle * 6.5));
//
//            // reset the timeout time and start motion.
//            runtime.reset();
//            fl.setPower(speed);
//            bl.setPower(speed);
//            fr.setPower(speed);
//            br.setPower(speed);
//
//
//            while (opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (fl.isBusy() && fr.isBusy())) {
//
//                // Display it for the driver.
//                telemetry.addData("Angle: ", getAngle());
//                telemetry.addData("Running to", " %7d :%7d", fl.getTargetPosition(), fr.getTargetPosition());
//                telemetry.addData("Currently at", " at %7d :%7d", fl.getCurrentPosition(), fr.getCurrentPosition());
//                telemetry.update();
//            }
//
//            // Stop all motion;
//            fl.setPower(0);
//            bl.setPower(0);
//            fr.setPower(0);
//            br.setPower(0);
//
//            sleep(100); // optional pause after each move.
//
//            telemetry.addData("Imu", getAngle());
//            telemetry.update();
//        }
//    }
//
//    public double getAngle() {
//        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
//    }
//
//    public void initIMU(HardwareMap hardwareMap) {
//        imu = hardwareMap.get(IMU.class, "imu");
//        IMU.Parameters params = new IMU.Parameters(
//                new RevHubOrientationOnRobot(
//                        LogoFacingDirection.RIGHT,
//                        UsbFacingDirection.UP));
//        imu.initialize(params);
//    }
//    public void initHardware(HardwareMap hMap) {
//        // Motor and servo declarations
//        fl = hMap.get(DcMotorEx.class, "fl");
//        bl = hMap.get(DcMotorEx.class, "bl");
//        fr = hMap.get(DcMotorEx.class, "fr");
//        br = hMap.get(DcMotorEx.class, "br");
//
//
//        outtakeClaw = hMap.get(Servo.class, );
//        intake = hMap.get(DcMotorEx.class, "intake");
//
//        lslide = hMap.get(DcMotorEx.class, "lslide");
//        rslide = hMap.get(DcMotorEx.class, "rslide");
//
//        initIMU(hMap);
//
//
//        // Motor direction configurations
//        fl.setDirection(DcMotor.Direction.REVERSE);
//        fr.setDirection(DcMotor.Direction.FORWARD);
//        br.setDirection(DcMotor.Direction.FORWARD);
//        bl.setDirection(DcMotor.Direction.REVERSE);
//        lslide.setDirection(DcMotorEx.Direction.REVERSE);
//        rslide.setDirection(DcMotorEx.Direction.FORWARD);
//
//        // Set zero power behavior for all motors
//        fl.setZeroPowebrehavior(DcMotor.ZeroPowebrehavior.BRAKE);
//        fr.setZeroPowebrehavior(DcMotor.ZeroPowebrehavior.BRAKE);
//        bl.setZeroPowebrehavior(DcMotor.ZeroPowebrehavior.BRAKE);
//        br.setZeroPowebrehavior(DcMotor.ZeroPowebrehavior.BRAKE);
//        // lslide.setZeroPowebrehavior(DcMotor.ZeroPowebrehavior.flOAT);
//        // rslide.setZeroPowebrehavior(DcMotor.ZeroPowebrehavior.flOAT);
//
//        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        lslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        sleep(500);
//
//        fl.setTargetPosition(0);
//        fr.setTargetPosition(0);
//        bl.setTargetPosition(0);
//        br.setTargetPosition(0);
//
//        fl.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//        fr.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//        bl.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//        br.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//        lslide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
//        rslide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
//    }
//}
