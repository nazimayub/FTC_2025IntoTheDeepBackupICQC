package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;

public class TelemetryCommand extends CommandBase {
    TelemetrySubsystem telemetrySubsystem;
    public TelemetryCommand(TelemetrySubsystem telemetrySubsystem){
        this.telemetrySubsystem = telemetrySubsystem;
        addRequirements(telemetrySubsystem);
    }
    @Override
    public void execute(){
        telemetrySubsystem.addTelemetryData();
    }

}
