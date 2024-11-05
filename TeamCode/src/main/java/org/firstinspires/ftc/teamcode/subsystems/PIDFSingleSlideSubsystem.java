package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PIDFSingleSlideSubsystem extends SubsystemBase {
    /**
     * Creates a new ExampleSubsystem.
     */
    private final DcMotorEx slide;
    private final double p, i, d, f;
    private int pos = 0;
    private double target = 0;
    private boolean use = false;
    private PIDController controller;
    public PIDFSingleSlideSubsystem(HardwareMap h, String slide, double p, double i, double d, double f) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
        this.slide = h.get(DcMotorEx.class, slide);
        this.slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.slide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.slide.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        controller = new PIDController(p, i, d);
    }
    public void set(double target) {
        this.target = target;
    }
    public void set(double pow, double x){
        this.slide.setPower(pow);
    }
    public void change(double amount){this.target+=amount;}
    public int getTick(){return -1*this.slide.getCurrentPosition();}
    public void reset(){
        this.slide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.slide.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void usePID(boolean yes){
        use = yes;
    }
    public PIDController getController(){
        return controller;
    }
    public double getF(){
        return f;
    }
    public boolean PIDUse(){
        return use;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //if(Math.abs(target-pos)<3){
        //    slide.setPower(0);
        //}
        if (use){
            controller.setPID(p, i, d);
            pos = slide.getCurrentPosition();
            double pid = controller.calculate(pos, target);
            double power = pid + f;
            slide.setPower(power);
        }

        //telemetry.addData("pos", pos);
        //telemetry.addData("target", target);
        //telemetry.update();
    }
}