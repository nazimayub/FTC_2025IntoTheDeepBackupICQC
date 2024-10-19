package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
@Autonomous
public class Close extends Robot {
    boolean isRedAlliance = true;
    Drive drive = new Drive(hardwareMap);
    @Override
    public void initialize() {
        schedule(new DriveCommand(drive,10,0,0,0.5));
    }

    @Override
    public void preInit() {
        telemetry.addData("RedAlliance?",isRedAlliance);
        telemetry.addData("A for red alliance, X for blue alliance","");
        if(gamepad1.a|| gamepad2.a){
            isRedAlliance = true;
        }else if(gamepad1.x||gamepad2.x){
            isRedAlliance = false;
        }
        telemetry.update();
    }
}
