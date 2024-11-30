package org.firstinspires.ftc.teamcode.auton;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robot.Robot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotDrive {

    static DcMotorEx lf;
    static DcMotorEx rf;
    static DcMotorEx lb;
    static DcMotorEx rb;
    static DcMotorEx intake, lSlide, rSlide;

    static Servo outtakeClaw, outtakeClawDist, outtakeClawRot, intakeClawRot;

    IMU imu;
    static double headingOffset = 0;

    Pose field = new Pose(0,0,0);

    int lfTicksPrev = 0;
    int rfTicksPrev = 0;
    int lbTicksPrev = 0;
    int rbTicksPrev = 0;
    double headingPrev = 0;

    public void init(HardwareMap hardwareMap) {
        lf = initDcMotor(hardwareMap, "fl", DcMotorSimple.Direction.REVERSE);
        rf = initDcMotor(hardwareMap, "fr", DcMotorSimple.Direction.FORWARD);
        lb = initDcMotor(hardwareMap, "bl", DcMotorSimple.Direction.REVERSE);
        rb = initDcMotor(hardwareMap, "br", DcMotorSimple.Direction.FORWARD);
        intake = hardwareMap.get(DcMotorEx.class, "motor4");

        rSlide = hardwareMap.get(DcMotorEx.class, "motor2");
        lSlide = hardwareMap.get(DcMotorEx.class, "motor1");

        lSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeClaw = hardwareMap.get(Servo.class, "servo3");
        outtakeClawDist = hardwareMap.get(Servo.class, "servo4");
        outtakeClawRot = hardwareMap.get(Servo.class, "servo5");
        intakeClawRot = hardwareMap.get(Servo.class, "servo1");

        initIMU(hardwareMap);
        setFieldXY(0,0);

        lSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void resetEncoders(){
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void initIMU(HardwareMap hardwareMap) { //init imu
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters params = new IMU.Parameters(
                new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(params);
    }

    public DcMotorEx initDcMotor(HardwareMap hardwareMap,
                                 String name,
                                 DcMotor.Direction dir) {
        DcMotorEx m = hardwareMap.get(DcMotorEx.class, name);
        m.setDirection(dir);
        m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        return m;
    }

    // HEADING SETUP FUNCTIONS ============================================

    public double getIMUHeading() { //grab IMU data
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public double getHeading() { //heading offset determines which direction robot wants to point
        return AngleUnit.normalizeDegrees(headingOffset + getIMUHeading());
    }

    public void setHeading(double h) { //create an offset from current position
        headingOffset = h - getIMUHeading();
    }

    // HEADING DRIVE FUNCTIONS ============================================

    public void driveXYW(double rx, double ry, double rw) { //drive without offset

        double denom = Math.max(1, (Math.abs(rx)+Math.abs(ry)+Math.abs(rw))); //constrain output between -1 & 1
        double lfPower = (rx - ry - rw) / denom;
        double rfPower = (rx + ry + rw) / denom;
        double lbPower = (rx + ry - rw) / denom;
        double rbPower = (rx - ry + rw) / denom;
        double fxMult = 1.0;

        lf.setPower(lfPower*fxMult);
        rf.setPower(rfPower*fxMult);
        lb.setPower(lbPower*fxMult);
        rb.setPower(rbPower*fxMult);
    }

    public void driveFieldXYW(double fx, double fy, double fw) { //drive with joystick IMU offset
        // rotate field orientation to robot orientation
        double theta = Math.toRadians(getHeading());
        double rx = fx * Math.cos(-theta) - fy * Math.sin(-theta);
        double ry = fx * Math.sin(-theta) + fy * Math.cos(-theta);

        driveXYW(rx, ry, fw);
    }

    public double computeHeadingW(double h, double vel) { //constrain heading while driving
        double err = h - getHeading(); //where are you at vs where do you want to be
        err = AngleUnit.normalizeDegrees(err); //take shortest distance
        return (err * 0.0075); //velocity * p-gain for heading
    }

    // FIELD AUTONOMY =======================================================

    public void setFieldXY(double fx, double fy) { //updates the field pos based on current pos
        updateTracking();
        field.x = fx;
        field.y = fy;
        field.h = getHeading();
    }

    public void updateTracking() { //compute pos from encoders
        double heading = getHeading();

        // get current motor ticks
        int lfTicks = lf.getCurrentPosition();
        int rfTicks = rf.getCurrentPosition();
        int lbTicks = lb.getCurrentPosition();
        int rbTicks = rb.getCurrentPosition();

        // determine angular delta (rotations) for each motor
        double lfD = (lfTicks - lfTicksPrev) / 355.6;
        double rfD = (rfTicks - rfTicksPrev) / 355.6;
        double lbD = (lbTicks - lbTicksPrev) / 355.6;
        double rbD = (rbTicks - rbTicksPrev) / 355.6;

        // remember new tick values
        lfTicksPrev=lfTicks; rfTicksPrev=rfTicks; lbTicksPrev=lbTicks; rbTicksPrev=rbTicks;

        // calculate delta distances in field units (rdx, rdy, rdw)
        double rdx = ((lfD + rfD + lbD + rbD) * (96.0/25.4*Math.PI)) / 4.0;
        double rdy = ((-lfD + rfD + lbD - rbD) * (96.0/25.4*Math.PI)) / 4.0;
        double rdw = Math.toRadians(heading - headingPrev);
        headingPrev = heading;

        // calculate pose exponential (pdx, pdy, pdw)
        // from https://file.tavsys.net/control/controls-engineering-in-frc.pdf
        //     Figure 10.2.1

        double s;
        double c;
        if (rdw < -0.1 || rdw > 0.1) {
            s = Math.sin(rdw) / rdw;
            c = (1-Math.cos(rdw)) / rdw;
        } else {
            // for angles near zero, we approximate with taylor series
            s = 1 - ((rdw*rdw) / 6);            // sin(w)/w     ~~> (1-(w*w)/6)
            c = rdw / 2;                        // (1-cos(w))/w ~~> (w/2)
        }
        double pdx = s * rdx - c * rdy;
        double pdy = c * rdx + s * rdy;
        double pdw = rdw;

        // compute delta change in field coordinates (fdx, fdy, fdw)
        double fw0 = Math.toRadians(field.h);
        double fdx = Math.cos(fw0) * pdx - Math.sin(fw0) * pdy;
        double fdy = Math.sin(fw0) * pdx + Math.cos(fw0) * pdy;
        double fdw = rdw;

        // integrate into field coordinates
        field.x += fdx;
        field.y += fdy;
        field.h = heading;
    }

    public double driveToXY(double tx, double ty, double vel) { //auto drive with 0 heading
        return driveToPose(tx, ty, 0, vel);
    }

    public double driveToPose(Pose p, double vel) { //auto drive with heading included
        return driveToPose(p.x, p.y, computeHeadingW(p.h, vel), vel);
    }

    public double driveToPose(double tx, double ty, double rw, double vel) {//overloaded function. same name different param
        double fdx = tx - field.x; //x to target
        double fdy = ty - field.y; //y to target
        double dist = Math.hypot(fdx, fdy); //hypotenous vector length
        double absHeadingRad = Math.atan2(fdy, fdx); //triangle angle
        double relHeadingRad = absHeadingRad - Math.toRadians(getHeading()); //triangle heading - robot heading
        double rdx = Math.cos(relHeadingRad) * dist; //how far in x in robot coord to drive
        double rdy = Math.sin(relHeadingRad) * dist; //how far in y in robot coord to drive
        double dScale = Math.abs(rdx) + Math.abs(rdy); //normalize between 0-1
        double rx = rdx / dScale; //norm x
        double ry = rdy / dScale; //norm y

        if(tx == 0 & ty == 0){
            driveXYW(0, 0, rw); //if x & y are zero, turn on the spot
        }
        else{
            driveXYW(rx * vel, ry * vel, rw); //else turn and move at the same time
        }
        return dist;
    }

    public static class Pose {
        double y;
        double x;
        double h;
        double vel;
        double lr;

        static double lrDefault = 18;
        static double velDefault = 0.5;

        public Pose() { setValues(0,0,0,velDefault,lrDefault); }
        public Pose(double x, double y) { setValues(x,y,0,velDefault,lrDefault); }
        public Pose(double x, double y, double h) { setValues(x,y,h,velDefault,lrDefault); }
        public Pose(Pose p) { setValues(p.x,p.y,p.h,p.vel,p.lr); }

        public Pose setValues(double x, double y, double h, double vel, double lr) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.vel = vel;
            this.lr = lr;
            return this;
        }

        public String toString() {
            return String.format("x=%.2f, y=%.2f, h=%.1f", x, y, h);
        }
        public static void grabPos(){
            outtakeClaw.setPosition(0.85);
            outtakeClawDist.setPosition(.34);
            outtakeClawRot.setPosition(0.565);
        }

        public static void grab(){
            outtakeClaw.setPosition(.25);
        }

        public static void scorePos(){
            outtakeClaw.setPosition(.25);
            outtakeClawDist.setPosition(.619);
            outtakeClawRot.setPosition(0.025);
            setPos(250);
        }

        public static void score(){
            outtakeClaw.setPosition(.25);
            outtakeClawDist.setPosition(.45);
            outtakeClawRot.setPosition(0.4);
            setPos(0);
        }

        public static void setPos(int t){
            while(Math.abs(t-lSlide.getCurrentPosition())>3 || Math.abs(t-lSlide.getCurrentPosition())>3){
                lSlide.setPower(new PIDController(0.06, 0, 0.0004).calculate(t, lSlide.getCurrentPosition()) + 0.2);
                rSlide.setPower(new PIDController(0.06, 0, 0.0004).calculate(t, rSlide.getCurrentPosition()) + 0.2);
            }
            lSlide.setPower(0.2);
            rSlide.setPower(0.2);
        }

    }
}