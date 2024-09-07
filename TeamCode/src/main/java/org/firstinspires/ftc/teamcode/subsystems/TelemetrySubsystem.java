package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.teleop.Solo;
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
                .add("Right Slide Velocity")
                .add("Left Slide Velocity")
                .add("Transfer Power")
                .add("Hand Position")
                .add("Drone Position")
                .add("Front Right Velocity")
                .add("Front Left Velocity")
                .add("Back Right Velocity")
                .add("Back Left Velocity")
                .add("Heading (Degrees)")
                .headerLine();
    }
    public void addTelemetryData(){
        telemetry.addData("Intake Velocity: ", Solo.intake.get());
        telemetry.addData("Right Slide Velocity: ", Solo.slide.getRight());
        telemetry.addData("Left Slide Velocity: ", Solo.slide.getLeft());
        telemetry.addData("Transfer Power: ", Solo.transfer.get());
        telemetry.addData("Hand Position: ", Solo.hand.get());
        telemetry.addData("Drone position: ", Solo.drone.get());
        telemetry.addData("Front Right Velocity: ", Solo.drive.getFR());
        telemetry.addData("Front Left Velocity: ", Solo.drive.getFL());
        telemetry.addData("Back Right Velocity: ", Solo.drive.getBR());
        telemetry.addData("Back Left Velocity: ", Solo.drive.getBL());
        telemetry.addData("Heading (Degrees): ", Solo.drive.getHeading());
    }


    public void updateLogs(){ //use Solo.Subsystem.Method()
        log.add(Solo.intake.get())
                .add(Solo.slide.getRight())
                .add(Solo.slide.getLeft())
                .add(Solo.transfer.get())
                .add(Solo.hand.get())
                .add(Solo.drone.get())
                .add(Solo.drive.getFR())
                .add(Solo.drive.getFL())
                .add(Solo.drive.getBR())
                .add(Solo.drive.getBL())
                .add(Solo.drive.getHeading());

    }
    public void addDashBoardData(){
        packet.put("Intake Velocity: ", Solo.intake.get());
        packet.put("Right Slide Velocity: ", Solo.slide.getRight());
        packet.put("Left Slide Velocity: ", Solo.slide.getLeft());
        packet.put("Transfer Power: ", Solo.transfer.get());
        packet.put("Hand Position: ", Solo.hand.get());
        packet.put("Drone position: ", Solo.drone.get());
        packet.put("Front Right Velocity: ", Solo.drive.getFR());
        packet.put("Front Left Velocity: ", Solo.drive.getFL());
        packet.put("Back Right Velocity: ", Solo.drive.getBR());
        packet.put("Back Left Velocity: ", Solo.drive.getBL());
        packet.put("Heading (Degrees): ", Solo.drive.getHeading());
    }
    public void updateDashboardTelemetry(){
        dashboard.sendTelemetryPacket(packet);
    }
}
