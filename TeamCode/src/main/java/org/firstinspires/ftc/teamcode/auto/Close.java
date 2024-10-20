package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.commands.ScoreCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.ScoringSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;

@Autonomous
public class Close extends Robot {
    boolean isRedAlliance = true;
    public static Drive drive;
    public static TelemetrySubsystem telemetryS;
    public static ScoringSubsystem score;
    @Override
    public void initialize() {
        drive = new Drive(hardwareMap);// this is where we schedule commands for auto
        telemetryS = new TelemetrySubsystem(telemetry);
        schedule(
                new ParallelCommandGroup(
                        new DriveCommand(drive,10,0,0,1,1),
                        new ScoreCommand(score,ScoringSubsystem.SprocketStates.STOW)
        ));

//        schedule(
//                new MoveCommand(drive)
//        );
    }

    @Override
    public void preInit() {
        telemetry.addData("X: "+Close.drive.getX()+", Y: "+Close.drive.getY()+", Heading: "+Close.drive.getHeading(), "");
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
