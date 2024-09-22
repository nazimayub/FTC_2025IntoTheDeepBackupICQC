package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.teleop.RobotContainer;
import org.firstinspires.ftc.teamcode.utils.SimpleLogger;


public class TelemetrySubsystem {
    SimpleLogger log;
    Telemetry telemetry;
    FtcDashboard dashboard;
    TelemetryPacket packet;
    public TelemetrySubsystem(SimpleLogger log, Telemetry telemetry, FtcDashboard dashboard){
        this.log = log;
        this.telemetry = telemetry;
        this.dashboard = dashboard;

    }
    public void addLogHeadings(){
        log.add("Intake Velocity")
                .add("Front Right Velocity")
                .add("Front Left Velocity")
                .add("Back Right Velocity")
                .add("Back Left Velocity")
                .add("Heading (Degrees)")
                .headerLine();
    }
    public void addTelemetryData(){
        telemetry.addData("Front Right Velocity: ", RobotContainer.drive.getFR());
        telemetry.addData("Front Left Velocity: ", RobotContainer.drive.getFL());
        telemetry.addData("Back Right Velocity: ", RobotContainer.drive.getBR());
        telemetry.addData("Back Left Velocity: ", RobotContainer.drive.getBL());
        telemetry.addData("Heading (Degrees): ", RobotContainer.drive.getHeading());
        telemetry.addData("Arm encoder:", RobotContainer.arm.getEncoderValue());
        telemetry.addData("slidel enc val l", RobotContainer.slide.getEncoderValueL());
        telemetry.addData("slider enc val r", RobotContainer.slide.getEncoderValueR());
        telemetry.addData("R current", RobotContainer.slide.getCurrentR());
        telemetry.addData("l current", RobotContainer.slide.getCurrentL());

//        telemetry.addData("slider power r", RobotContainer.slide.getPowerR());
//        telemetry.addData("slider power l", RobotContainer.slide.getPowerL());

    }


    public void updateLogs(){ //use Solo.Subsystem.Method()
        log.add(
                RobotContainer.drive.getHeading())
                .add(RobotContainer.drive.getFR())
                .add(RobotContainer.drive.getFL())
                .add(RobotContainer.drive.getBR())
                .add(RobotContainer.drive.getBL())
                .add(RobotContainer.arm.getEncoderValue());

    }
    public void addDashBoardData(){
        packet.put("Front Right Velocity: ", RobotContainer.drive.getFR());
        packet.put("Front Left Velocity: ", RobotContainer.drive.getFL());
        packet.put("Back Right Velocity: ", RobotContainer.drive.getBR());
        packet.put("Back Left Velocity: ", RobotContainer.drive.getBL());
        packet.put("Heading (Degrees): ", RobotContainer.drive.getHeading());
        packet.put("Arm encoder:", RobotContainer.arm.getEncoderValue());
    }
    public void updateDashboardTelemetry(){
        dashboard.sendTelemetryPacket(packet);
    }
}
