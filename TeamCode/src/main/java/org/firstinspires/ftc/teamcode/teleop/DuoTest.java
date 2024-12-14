package org.firstinspires.ftc.teamcode.teleop;


import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;

@TeleOp
public class DuoTest extends CommandOpMode {
    SlideSubsystem slides;

    GamepadEx base;
    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        slides = new SlideSubsystem(hardwareMap, Constants.rSlide, Constants.lSlide, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD);
        slides.setDefaultCommand(new SlideArmCommand(slides, base));
    }
}
