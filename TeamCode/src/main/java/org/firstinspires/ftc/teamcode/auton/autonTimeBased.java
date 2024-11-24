package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

@Autonomous
public class autonTimeBased extends LinearOpMode {


    public DcMotorEx fr;
    public DcMotorEx fl;
    public DcMotorEx br;
    public DcMotorEx bl;
    public Servo outtakeClaw,outtakeClawDist;
    public DcMotorEx right, left;


    @Override
    public void runOpMode() {


        fr = hardwareMap.get(DcMotorEx.class, "fr");
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        br = hardwareMap.get(DcMotorEx.class, "br");
        bl = hardwareMap.get(DcMotorEx.class, "bl");

        right = hardwareMap.get(DcMotorEx.class, Constants.rSlide);
        left = hardwareMap.get(DcMotorEx.class, Constants.lSlide);

        outtakeClaw = hardwareMap.get(Servo.class, Constants.outtakeClaw);
        outtakeClawDist = hardwareMap.get(Servo.class, Constants.outtakeClawDist);

        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        right.setDirection(DcMotorSimple.Direction.FORWARD);
        left.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()) {
            outtakeClaw.setPosition(.533);
            sleep(1000);
            outtakeClawDist.setPosition(.128);
            sleep(300);
            right.setPower(.45);
            left.setPower(.45);
            sleep(450);
            set(.5, 0, 0, 550);
            right.setPower(0);
            left.setPower(0);
            fl.setPower(.05);
            bl.setPower(.05);
            fr.setPower(.05);
            br.setPower(.05);
            sleep(500);
            right.setPower(-1);
            left.setPower(-1);
            set(.25, 0, 0, 500);
            outtakeClawDist.setPosition(.1);
            outtakeClaw.setPosition(.343);
            sleep(300);
            right.setPower(0);
            left.setPower(0);
            set(-.5    , -1, 0, 2000);



            break;

        }

    }
    public void set(double y, double x, double r, long time){
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(r), 1);
            fl.setPower((y-x-r)/denominator);
            bl.setPower((y+x-r)/denominator);
            fr.setPower((y+x+r)/denominator);
            br.setPower((y-x+r)/denominator);

        sleep(time);

        fr.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }
}
