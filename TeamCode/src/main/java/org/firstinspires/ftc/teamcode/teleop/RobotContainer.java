package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.commands.TestPorts;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySubsystem;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;
import org.firstinspires.ftc.teamcode.utils.SimpleLogger;

@TeleOp(name = "IOT_CB", group = Constants.GROUP_ANDROID)
public class RobotContainer extends CommandOpMode {
    public static Drive drive;

    public static SimpleLogger log;
    public static TelemetrySubsystem telemetrySubsystem;

    public static Boolean isAllianceBlue = true;


    @Override
    public void initialize() {
        // variables
        GamepadEx base = new GamepadEx(gamepad1);
        GamepadEx operator = new GamepadEx(gamepad2);

        telemetrySubsystem = new TelemetrySubsystem(log, telemetry, FtcDashboard.getInstance());
        drive = new Drive(hardwareMap, "imu", new MotorConfig("fr", "fl", "br", "bl"), new MotorDirectionConfig(false, true, false, true));

        // default commands

        drive.setDefaultCommand(new DriveCommand(drive, base));

        // telemetry stuffs

        //telemetrySubsystem.addLogHeadings();

        schedule(new RunCommand(telemetrySubsystem::addTelemetryData));
        //schedule(new RunCommand(telemetrySubsystem::addDashBoardData));

        // update logging stuffs

        //schedule(new RunCommand(telemetrySubsystem::updateDashboardTelemetry));
        //schedule(new RunCommand(telemetrySubsystem::updateLogs));
        schedule(new RunCommand(telemetry::update));
        // do everything before this loop.....

        configureButtonBindings(base, operator);
        // pre init
        while (opModeInInit() && !opModeIsActive()) {
            // do pre init stuffs here!!!!!
            telemetry.addLine("'X' to set alliance to BLUE");
            telemetry.addLine("'Y' to set alliance to RED");


            if (base.getButton(GamepadKeys.Button.X) | operator.getButton(GamepadKeys.Button.X)) {
                // set it to blue
                telemetry.addLine("Blue Selected!");
                isAllianceBlue = true;
            } else if (base.getButton(GamepadKeys.Button.Y) | operator.getButton(GamepadKeys.Button.Y)) {
                telemetry.addLine("Red Selected!");
                isAllianceBlue = false;
            }
            telemetry.clearAll();
        }
    }

    public void configureButtonBindings(GamepadEx b1, GamepadEx o2) {


        Button povUP = new GamepadButton(b1, GamepadKeys.Button.DPAD_UP)
            .whileHeld(new TestPorts(drive, "chm0"));

        Button povRIGHT = new GamepadButton(b1, GamepadKeys.Button.DPAD_RIGHT)
                .whileHeld(new TestPorts(drive, "chm1"));

        Button povDOWN = new GamepadButton(b1, GamepadKeys.Button.DPAD_DOWN)
                .whileHeld(new TestPorts(drive, "chm2"));

        Button povLEFT = new GamepadButton(b1, GamepadKeys.Button.DPAD_LEFT)
                .whileHeld(new TestPorts(drive, "chm3"));

        Button A = new GamepadButton(b1, GamepadKeys.Button.A)
                .whileHeld(new TestPorts(drive, "none"));
    }

}
