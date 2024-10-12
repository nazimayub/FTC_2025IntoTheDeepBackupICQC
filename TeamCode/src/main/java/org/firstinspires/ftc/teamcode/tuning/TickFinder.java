package org.firstinspires.ftc.teamcode.tuning;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Command;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.PIDFSlideArmCommand;
import org.firstinspires.ftc.teamcode.subsystems.HandSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;

@TeleOp
public class TickFinder extends CommandOpMode {
    double s;
    Servo servo;
    PIDFSlideSubsystem slide;
    GamepadEx base;
    PIDFArmSubsystem arm;



    @Override
    public void initialize() {
        arm = new PIDFArmSubsystem(hardwareMap, Constants.arm, 0.01, 0, 0.0001, 0.001, 1926/180);
        slide = new PIDFSlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.FORWARD, 0.03, 0, 0.0003, 0.2, 0.03, 0, 0.0003, 0.2);
        base = new GamepadEx(gamepad1);
        new GamepadButton(base, GamepadKeys.Button.A).whileHeld(new PIDFSlideArmCommand(arm, -1));
        new GamepadButton(base, GamepadKeys.Button.Y).whileHeld(new PIDFSlideArmCommand(arm, 1));
        new GamepadButton(base, GamepadKeys.Button.DPAD_DOWN).whenPressed(new PIDFSlideArmCommand(slide, -1));
        new GamepadButton(base, GamepadKeys.Button.DPAD_UP).whenPressed(new PIDFSlideArmCommand(slide, 1));
        telemetry.addLine("Arm tick " + arm.getTick());
        telemetry.addLine("Slide tick " + slide.getTick());
        telemetry.update();
    }
}
