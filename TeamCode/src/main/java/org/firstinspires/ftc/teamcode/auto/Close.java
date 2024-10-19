package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
@Autonomous
public class Close extends Robot {
    boolean isRedAlliance = true;

    @Override
    public void initialize() {
        Drive drive = new Drive(hardwareMap);// this is where we schedule commands for auto
        schedule(new DriveCommand(drive,10,drive.getY(),drive.getHeading(),1,0.5));
        //schedule(new DriveCommand(drive,2)); // wait with timeout
        //schedule(new DriveCommand(drive,drive.getX(),10, drive.getHeading(), 1,0.5));
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
