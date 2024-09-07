package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;
import org.firstinspires.ftc.teamcode.utils.SimpleLogger;
@TeleOp(name = "TeleOp", group = Constants.GROUP_ANDROID)
public class RobotContainer extends CommandOpMode {
    public static Drive drive;
    public static SimpleLogger log;
    public static TelemetrySubsystem telemetrySubsystem;

    @Override
    public void initialize() {
        GamepadEx base = new GamepadEx(gamepad1);
        telemetrySubsystem = new TelemetrySubsystem(log,telemetry, FtcDashboard.getInstance());
        drive = new Drive(hardwareMap,"imu",new MotorConfig("fr","fl","br","bl"),new MotorDirectionConfig(false,false,false,false));
        drive.setDefaultCommand(new DriveCommand(drive,base));
        telemetrySubsystem.addLogHeadings();

        schedule(new RunCommand(telemetrySubsystem::addTelemetryData));
        schedule(new RunCommand(telemetrySubsystem::addDashBoardData));

        // update logging stuffs

        schedule(new RunCommand(telemetrySubsystem::updateDashboardTelemetry));
        schedule(new RunCommand(telemetrySubsystem::updateLogs));
        schedule(new RunCommand(telemetry::update));
    }
}
