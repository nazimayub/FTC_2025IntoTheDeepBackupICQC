package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;

public class TelemetryCommand extends CommandBase {
    TelemetrySubsystem telemetrySubsystem;
    String key;
    Object value;
    public TelemetryCommand(TelemetrySubsystem telemetrySubsystem, String key,Object value){
        this.telemetrySubsystem = telemetrySubsystem;
        this.key = key;
        this.value = value;

        addRequirements(telemetrySubsystem);
    }
    @Override
    public void execute(){
        telemetrySubsystem.addData(key,value);
        telemetrySubsystem.update();
    }
    @Override
    public boolean isFinished(){
        return false;
    }

}
