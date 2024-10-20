package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ScoringSubsystem extends SubsystemBase {
    public static boolean isFinished = false;
    public DcMotorEx RE;
    public DcMotorEx sprocket;
    public DcMotorEx LE;
    public DcMotor intake;

    public Servo lhook;
    public Servo rhook;
    public Servo wrist;

    double variable;


    public double lhome = 0.4;
    public double rhome = 0.6;

    public double lclimb = 1.0;
    public double rclimb = 0.0;

    PIDController controller = new PIDController(0.01, 0, 0);

    public double drop = 1.0;
    public double intakePos = 0.65;
    public double specimen = 0.4;
    public int extendTarget;
    /**
     * This has the following Extend States
     * <ul>
     *   <li>FIRST</li>
     *   <li>SECOND</li>
     *   <li>THIRD</li>
     *   <li>FOURTH</li>
     *   <li>FIFTH</li>
     *   <li>HOME</li>
     * </ul>
     */
    public enum ExtendStates{
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
        FIFTH,
        HOME
    }
    /**
     * This has the following Wrist States
     * <ul>
     *   <li>DROP</li>
     *   <li>INTAKE</li>
     *   <li>SPECIMEN</li>
     * </ul>
     */
    public enum WristStates{
        DROP,
        INTAKE,
        SPECIMEN
    }
    /**
     * This has the following Extend States
     * <ul>
     *   <li>STOW</li>
     *   <li>SCORE</li>
     *   <li>INTAKE</li>
     *   <li>REACH</li>
     * </ul>
     */
    public enum SprocketStates{
        STOW,
        SCORE,
        INTAKE,
        REACH
    }
    /**
     * This has the following Extend States
     * <ul>
     *   <li>REACH</li>
     *   <li>LOCK</li>
     *   <li>OPEN</li>
     * </ul>
     */
    public enum ClimbStates{
        REACH,
        LOCK,
        OPEN
    }
    /**
     * This has the following Extend States
     * <ul>
     *   <li>SPIN</li>
     *   <li>STOP</li>
     * </ul>
     */
    public enum IntakeStates{
        SPIN,
        STOP
    }
    public WristStates wristState = null;
    public SprocketStates sprocketState = null;
    public ClimbStates climbState = null;
    public ExtendStates extendState = null;
    public IntakeStates intakeState = null;


    public ScoringSubsystem(HardwareMap hardwareMap){
        isFinished = false;
        LE = hardwareMap.get(DcMotorEx.class, "LE");
        RE = hardwareMap.get(DcMotorEx.class, "RE");
        sprocket = hardwareMap.get(DcMotorEx.class, "sprocket");
        intake = hardwareMap.get(DcMotor.class, "intake");

        lhook = hardwareMap.get(Servo.class, "lhook");
        rhook = hardwareMap.get(Servo.class, "rhook");
        wrist = hardwareMap.get(Servo.class, "wrist");
    }
    public void sprocPos(int encoders){
        isFinished = false;
        sprocket.setTargetPosition(encoders);
        sprocket.setPower(1);
        sprocket.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        isFinished = true;

    }
    public void first(){
        isFinished = false;
        //First Level
        extendTarget = -500;
        extendState = ExtendStates.FIRST;
        isFinished = true;
    }
    public void second(){
        isFinished = false;
        //First Level
        extendTarget = -850;
        extendState = ExtendStates.SECOND;
        isFinished = true;
    }
    public void third(){
        isFinished = false;
        //First Level
        extendTarget = -1250;
        extendState = ExtendStates.THIRD;
        isFinished = true;
    }
    public void fourth(){
        isFinished = false;
        //First Level
        extendTarget = -1300;
        extendState = ExtendStates.FOURTH;
        isFinished = true;
    }
    public void fifth(){
        isFinished = false;
        //First Level
        extendTarget = -2200;
        extendState = ExtendStates.FIFTH;
        isFinished = true;
    }public void home(){
        isFinished = false;
        //First Level
        extendTarget = 0;
        extendState = ExtendStates.HOME;
        isFinished = true;
    }
    public void target(double target, double position){
        isFinished = false;
        LE.setPower(-controller.calculate(position,target));
        RE.setPower(controller.calculate(position,target));
        variable = controller.calculate(position,target);
        isFinished = true;
    }
    public void intakeSpin(double power){
        isFinished = false;
        intake.setPower(-power);
        if(power==0){
            intakeState = IntakeStates.STOP;
        }else{
            intakeState = IntakeStates.SPIN;
        }
        isFinished = true;
    }

    public void drop(){
        isFinished = false;
        wrist.setPosition(drop);
        wristState = WristStates.DROP;
        isFinished = true;
    }
    public void intake(){
        isFinished = false;
        wrist.setPosition(intakePos);
        wristState = WristStates.INTAKE;
        isFinished = true;
    }
    public void stow(){
        isFinished = false;
        sprocPos(-1000);
        sprocketState = SprocketStates.STOW;
        isFinished = true;
    }
    public void scorePos() {
        isFinished = false;
        sprocPos(-5500);
        sprocketState = SprocketStates.SCORE;
        isFinished = true;
    }
    public void specimen(){
        isFinished = false;
        wrist.setPosition(specimen);
        wristState = WristStates.SPECIMEN;
        isFinished = true;
    }

    public void reach(){
        isFinished = false;
        lhook.setPosition(lclimb);
        rhook.setPosition(rclimb);
        sprocPos(-1200);
        sprocketState = SprocketStates.REACH;
        climbState = ClimbStates.REACH;
        isFinished = true;

    }
    public void intakePos(){
        isFinished = false;
        sprocPos(-600);
        sprocketState = SprocketStates.INTAKE;
        isFinished = true;
    }

    public void lock(){
        isFinished = false;
        lhook.setPosition(lhome);
        rhook.setPosition(rhome);
        climbState = ClimbStates.LOCK;
        isFinished = true;
    }
    public void open(){
        isFinished = false;
        lhook.setPosition(lclimb);
        rhook.setPosition(rclimb);
        climbState = ClimbStates.OPEN;
        isFinished = true;
    }
    public void setRE(double power){
        isFinished = false;
        RE.setPower(power);
        isFinished = true;
    }
    public void setLE(double power){
        isFinished = false;
        LE.setPower(power);
        isFinished = true;
    }
    public boolean isCompleted(){
        return isFinished;
    }
    public void setCompletionStatus(boolean b){
        isFinished = b;
    }
}