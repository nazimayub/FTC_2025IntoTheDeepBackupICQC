package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MODE;
import org.firstinspires.ftc.teamcode.StateMachine;
import org.firstinspires.ftc.teamcode.PIDFController;
import static org.firstinspires.ftc.teamcode.RobotDrivePm.*;
import static org.firstinspires.ftc.teamcode.RobotValues.*;


public class ClimbSubsystem extends SubsystemBase{
    DcMotorEx lLift, rLift;
    PIDFController leftPIDF = new PIDFController(0.001, 0, 0, 0);
    PIDFController rightPIDF = new PIDFController(0.001, 0, 0, 0);
    double setPoint = 0;
    
    public ClimbSubsystem(HardwareMap hMap) {
        lLift = initDcMotor(hMap, "lslide", RIGHTDIR, POSITION);
        rLift = initDcMotor(hMap, "lslide", RIGHTDIR, POSITION);

        leftPIDF.setTolerance(5);
        rightPIDF.setTolerance(5);
        
        lLift.setTargetPosition(getLPos());
        rLift.setTargetPosition(getRPos());
        
        lLift.setPower(1);
        rLift.setPower(1);
    }
    
    public int getLPos() { return lLift.getCurrentPosition(); }
    public int getRPos() { return rLift.getCurrentPosition(); }

    public void setHeight(int height) {
        lLift.setTargetPosition(height);
        rLift.setTargetPosition(getLPos());
        setPoint = height;
    }

    public boolean heightReached() {
        return (getLPos() == setPoint && getRPos() == setPoint);
    }

}