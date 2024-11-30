package org.firstinspires.ftc.teamcode.auton;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robot.Robot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class TestAuto extends LinearOpMode{

    static DcMotorEx fl;
    static DcMotorEx fr;
    static DcMotorEx bl;
    static DcMotorEx br;
    static DcMotorEx intake, lSlide, rSlide;

    static Servo outtakeClaw, outtakeClawDist, outtakeClawRot, intakeClawRot;

    @Override
    public void runOpMode() {
        waitForStart();
        while(opModeIsActive()) {
            drive(300, 300, 300, 300);
            scorePos();
            sleep(500);
            score();
            drive(0, -600, -600, 0);
            grabPos();
            drive(300, -300, 300, -300);
            grab();
            drive(-300, 900, 300, 300);
            scorePos();
            sleep(500);
            score();

        }
    }


    public void drive(int q, int w, int e, int r){
        resetEncoders();
        while(!at(q, w, e, r, 0)){
            if(!at(q, w, e, r, 1)){
               fl.setPower(Math.abs(fl.getCurrentPosition()-q)/(fl.getCurrentPosition-q));
            }
            else {
                fl.setPower(0);
            }
            if(!at(q, w, e, r, 2)){
                fr.setPower(Math.abs(fr.getCurrentPosition()-q)/(fr.getCurrentPosition-q));
            }
            else {
                fr.setPower(0);
            }
            if(!at(q, w, e, r, 3)){
                bl.setPower(Math.abs(bl.getCurrentPosition()-q)/(bl.getCurrentPosition-q));
            }
            else {
                bl.setPower(0);
            }
            if(!at(q, w, e, r, 4)){
                br.setPower(Math.abs(br.getCurrentPosition()-q)/(br.getCurrentPosition-q));
            }
            else {
                br.setPower(0);
            }
        }
    }
    public boolean at(int q, int w, int e, int r, int n){
        if(n==0){
            return Math.abs(fl.getCurrentPosition()-q)<3&&Math.abs(fr.getCurrentPosition()-w)<3&&Math.abs(bl.getCurrentPosition()-e)<3&&Math.abs(br.getCurrentPosition()-r)<3;
        }
        else if(n==1){
            return Math.abs(fl.getCurrentPosition()-q)<3;
        }
        else if (n==2){
            return Math.abs(fr.getCurrentPosition()-w)<3;
        }
        else if (n==3){
            return Math.abs(bl.getCurrentPosition()-e)<3;
        }
        else{
            return Math.abs(br.getCurrentPosition()-r)<3;
        }
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
    public void init(HardwareMap hardwareMap) {
        fl = initDcMotor(hardwareMap, "fl", DcMotorSimple.Direction.REVERSE);
        fr = initDcMotor(hardwareMap, "fr", DcMotorSimple.Direction.FORWARD);
        bl = initDcMotor(hardwareMap, "bl", DcMotorSimple.Direction.REVERSE);
        br = initDcMotor(hardwareMap, "br", DcMotorSimple.Direction.FORWARD);
        intake = hardwareMap.get(DcMotorEx.class, "motor4");

        rSlide = hardwareMap.get(DcMotorEx.class, "motor2");
        lSlide = hardwareMap.get(DcMotorEx.class, "motor1");

        lSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rSlide.setZeroPowebrehavior(DcMotor.ZeroPowebrehavior.BRAKE);
        lSlide.setZeroPowebrehavior(DcMotor.ZeroPowebrehavior.BRAKE);
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
        m.setZeroPowebrehavior(DcMotor.ZeroPowebrehavior.BRAKE);
        return m;
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
