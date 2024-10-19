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

    public void addTelemetryData(){
        telemetry.addData("X: "+Close.drive.getX()+", Y: "+Close.drive.getY()+", Heading: "+Close.drive.getHeading(), "");
        telemetry.update();
    }

}
