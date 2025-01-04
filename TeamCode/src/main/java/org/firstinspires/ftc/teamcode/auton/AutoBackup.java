package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

// 0 + 1 Auto

@Autonomous
public class AutoBackup extends LinearOpMode{

    static DcMotorEx fl;
    static DcMotorEx fr;
    static DcMotorEx bl;
    static DcMotorEx br;
    static DcMotorEx intake, lSlide, rSlide;

    double speed = 0.3;

    static Servo outtakeClaw, outtakeClawDist, outtakeClawRot, intakeClawRot;
    public final double ticksInDeg = 700/180.0;

    @Override
    public void runOpMode() {
        init(hardwareMap);
        waitForStart();
        while(opModeIsActive()) {
            drive(715);
            scorePos();
            sleep(1000);
            score();
            sleep(500);
            setPos(50);
            sleep(500);
            drive(-500);
            break;
        }
    }

    public void init(HardwareMap hardwareMap) {
        fl = initDcMotor(hardwareMap, "fl", DcMotorSimple.Direction.FORWARD);
        fr = initDcMotor(hardwareMap, "fr", DcMotorSimple.Direction.REVERSE);
        bl = initDcMotor(hardwareMap, "bl", DcMotorSimple.Direction.FORWARD);
        br = initDcMotor(hardwareMap, "br", DcMotorSimple.Direction.REVERSE);
        intake = hardwareMap.get(DcMotorEx.class, "motor4");

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        rSlide = hardwareMap.get(DcMotorEx.class, "motor2");
        lSlide = hardwareMap.get(DcMotorEx.class, "motor1");

        lSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        rSlide.setDirection(DcMotorSimple.Direction.REVERSE);
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

    public void drive (int i){
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if(fl.getCurrentPosition()<i){
            while(fl.getCurrentPosition()<i){
                fr.setPower(speed);
                fl.setPower(speed);
                br.setPower(speed);
                bl.setPower(speed);
            }
        }
        else {
            while(fl.getCurrentPosition()>i){
                fr.setPower(-speed);
                fl.setPower(-speed);
                br.setPower(-speed);
                bl.setPower(-speed);
            }
        }

        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
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

    public void rot (int i){
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if(fl.getCurrentPosition()<i){
            while(fl.getCurrentPosition()<i){
                fr.setPower(-speed);
                fl.setPower(speed);
                br.setPower(-speed);
                bl.setPower(speed);
            }
        }
        else {
            while(fl.getCurrentPosition()>i){
                fr.setPower(speed);
                fl.setPower(-speed);
                br.setPower(speed);
                bl.setPower(-speed);
            }
        }

        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }

    public void strafe (int i){
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if(fl.getCurrentPosition()>i){
            while(fl.getCurrentPosition()>i){
                fr.setPower(-speed);
                fl.setPower(speed);
                br.setPower(speed);
                bl.setPower(-speed);
            }
        }
        else {
            while(fl.getCurrentPosition()<i){
                fr.setPower(speed);
                fl.setPower(-speed);
                br.setPower(-speed);
                bl.setPower(speed);
            }
        }

        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
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

    public static void grabPos(){
        outtakeClaw.setPosition(0.85);
        outtakeClawDist.setPosition(0.335);
        outtakeClawRot.setPosition(0.894);
    }

    public static void grab(){
        outtakeClaw.setPosition(.25);
    }

    public static void scorePos(){
        outtakeClaw.setPosition(.25);
        outtakeClawDist.setPosition(.619);
        outtakeClawRot.setPosition(0.025);
        setPos(540);
    }

    public static void score(){
        outtakeClaw.setPosition(.25);
        outtakeClawDist.setPosition(.5);
        outtakeClawRot.setPosition(0.2);
        setPos(0);
        outtakeClaw.setPosition(Constants.release);
    }

    public static void setPos(int t){
        if(t > rSlide.getCurrentPosition()){
            while(rSlide.getCurrentPosition()<t){
                lSlide.setPower(0.5);
                rSlide.setPower(0.5);
            }
        }
        else {
            while(rSlide.getCurrentPosition()>t){
                lSlide.setPower(-1);
                rSlide.setPower(-1);
            }
        }

        lSlide.setPower(0.2);
        rSlide.setPower(0.2);
    }

    public static void reset() {
        outtakeClaw.setPosition(Constants.grab);
        outtakeClawDist.setPosition(.8);
        outtakeClawRot.setPosition(.6);
    }

}
