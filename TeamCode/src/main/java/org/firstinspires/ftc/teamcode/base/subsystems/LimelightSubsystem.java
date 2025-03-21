package org.firstinspires.ftc.teamcode.base.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LimelightSubsystem extends SubsystemBase{

    private final Limelight3A ll;

    public LimelightSubsystem(HardwareMap h, String name, int pipeline){
        ll = h.get(Limelight3A.class, name);
        ll.pipelineSwitch(pipeline);
        ll.start();
    }

    public void stepTelemetry(Telemetry telemetry){
        LLResult result = ll.getLatestResult();
        telemetry.addData("tx", result.getTx());
        telemetry.addData("ty", result.getTy());
        telemetry.addData("ta", result.getTa());
    }

    public LLResult getAllLimelightData(){
        return ll.getLatestResult();
    }

    public double getLimelightX(){
        return ll.getLatestResult().getTx();
    }
    public double getLimelightY(){
        return ll.getLatestResult().getTy();
    }
    public double getLimelightA(){
        return ll.getLatestResult().getTa();
    }




}
