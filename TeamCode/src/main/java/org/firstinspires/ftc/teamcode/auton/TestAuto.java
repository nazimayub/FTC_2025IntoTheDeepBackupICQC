package org.firstinspires.ftc.teamcode.auton;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
        init(hardwareMap);
        waitForStart();
        while(opModeIsActive()) {
            drive(150, 150, 150, 150);
            sleep(2000);
            scorePos();
            sleep(2000);
            score();
            //drive(0, -300, -300, 0);
            //grabPos();
            //drive(150, -150, 150, -150);
            //grab();
            //drive(-150, 450, 150, 150);
            //scorePos();
            //sleep(500);
            //score();

        }
    }


    public void drive(int q, int w, int e, int r){
        resetEncoders();
        while(!at(q, w, e, r, 0)){
            if(!at(q, w, e, r, 1)){
               fl.setPower((double) (fl.getCurrentPosition() - q) /Math.max(1, Math.abs(fl.getCurrentPosition()-q)));
            }
            else {
                fl.setPower(0);
            }
            if(!at(q, w, e, r, 2)){
                fr.setPower((double) (fr.getCurrentPosition() - q) /Math.max(1, Math.abs(fr.getCurrentPosition()-q)));
            }
            else {
                fr.setPower(0);
            }
            if(!at(q, w, e, r, 3)){
                bl.setPower((double) (bl.getCurrentPosition() - q) /Math.max(1, Math.abs(bl.getCurrentPosition()-q)));
            }
            else {
                bl.setPower(0);
            }
            if(!at(q, w, e, r, 4)){
                br.setPower((double) (br.getCurrentPosition() - q) /Math.max(1, Math.abs(br.getCurrentPosition()-q)));
            }
            else {
                br.setPower(0);
            }
        }
    }
    public boolean at(int q, int w, int e, int r, int n){
        if(n==0){
            return Math.abs(fl.getCurrentPosition()-q)<5&&Math.abs(fr.getCurrentPosition()-w)<5&&Math.abs(bl.getCurrentPosition()-e)<5&&Math.abs(br.getCurrentPosition()-r)<5;
        }
        else if(n==1){
            return Math.abs(fl.getCurrentPosition()-q)<5;
        }
        else if (n==2){
            return Math.abs(fr.getCurrentPosition()-w)<5;
        }
        else if (n==3){
            return Math.abs(bl.getCurrentPosition()-e)<5;
        }
        else{
            return Math.abs(br.getCurrentPosition()-r)<5;
        }
    }
    public void resetEncoders(){
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        rSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeClaw = hardwareMap.get(Servo.class, "servo3");
        outtakeClawDist = hardwareMap.get(Servo.class, "servo4");
        outtakeClawRot = hardwareMap.get(Servo.class, "servo5");
        intakeClawRot = hardwareMap.get(Servo.class, "servo1");


        lSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }



    public DcMotorEx initDcMotor(HardwareMap hardwareMap,
                                 String name,
                                 DcMotor.Direction dir) {
        DcMotorEx m = hardwareMap.get(DcMotorEx.class, name);
        m.setDirection(dir);
        m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
