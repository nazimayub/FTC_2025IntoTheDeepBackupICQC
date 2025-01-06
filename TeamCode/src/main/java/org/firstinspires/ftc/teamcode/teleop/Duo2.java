package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utils.*;

@TeleOp
public class Duo2 extends CommandOpMode {
    GamepadEx base;
    public static GamepadEx op;
    public static Drive drive;

    @Override
    public void initialize() {
        drive.setDefaultCommand(Actions.drive());

        new GamepadButton(base, GamepadKeys.Button.A)
                .whenPressed(Actions.specimenGrabPos());

        new GamepadButton(base, GamepadKeys.Button.X)
                .whenPressed(Actions.specimenGrab());

        new GamepadButton(base, GamepadKeys.Button.Y)
                .whenPressed(Actions.specimenScore());

        new GamepadButton(base, GamepadKeys.Button.B)
                .whenPressed(Actions.moveFromSpecimenScore());

        new GamepadButton(base, GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(Actions.setVertSlidesTo2400TicksForSomeReason());

        new GamepadButton(op, GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(Actions.intake())
                .whenReleased(Actions.stopIntake());

        new GamepadButton(op, GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(Actions.outtake())
                .whenReleased(Actions.stopIntake());

        new GamepadButton(op, GamepadKeys.Button.A)
                .whenPressed(Actions.bringIntakeDown());

        new GamepadButton(op, GamepadKeys.Button.B)
                .whenPressed(Actions.transfer());

        new GamepadButton(op, GamepadKeys.Button.Y)
                .whenPressed(Actions.basketScore());

        new GamepadButton(op, GamepadKeys.Button.X)
                .whenPressed(Actions.release());

        new GamepadButton(op, GamepadKeys.Button.DPAD_UP)
                .whenPressed(Actions.release());

        new GamepadButton(op, GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(Actions.resetVertSlides());
    }
}
