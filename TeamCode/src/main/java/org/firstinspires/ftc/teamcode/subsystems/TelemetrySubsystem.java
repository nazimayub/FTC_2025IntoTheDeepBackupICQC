package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.auto.Close;
import org.firstinspires.ftc.robotcore.external.Telemetry;



public class TelemetrySubsystem extends SubsystemBase {

    Telemetry telemetry;

    public TelemetrySubsystem(Telemetry telemetry){
        this.telemetry = telemetry;
    }

    public void addData(String key,Object data){
        telemetry.addData(key, data);
    }
    public void update(){
        telemetry.update();
    }

}
