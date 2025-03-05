package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.base.bot.Actions;
import org.firstinspires.ftc.teamcode.base.bot.Robot;

@TeleOp(name="Solo", group="TeleOp")
public class Solo extends CommandOpMode {
    @Override
    public void initialize() {
        Robot bot = new Robot(Robot.Mode.SOLO, hardwareMap);

        new GamepadButton(bot.base, GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(Actions.SpecimenGrabAction(bot));

        new GamepadButton(bot.base, GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(Actions.SpecimenScoreAction(bot));

        new GamepadButton(bot.base, GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(Actions.IntakeAction(bot, true))
                .whenReleased(Actions.IntakeRestAction(bot));

        new GamepadButton(bot.base, GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(Actions.IntakeAction(bot, true))
                .whenReleased(Actions.IntakeRestAction(bot));

        new GamepadButton(bot.base, GamepadKeys.Button.A)
                .whenPressed(Actions.SubmersibleIntakeAction(bot));

        new GamepadButton(bot.base, GamepadKeys.Button.B)
                .whenPressed(Actions.TransferAction(bot));

        new GamepadButton(bot.base, GamepadKeys.Button.Y)
                .whenPressed(Actions.HighBasketAction(bot));

        new GamepadButton(bot.base, GamepadKeys.Button.X)
                .whenPressed(Actions.ReleaseAction(bot));

        new GamepadButton(bot.base, GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(Actions.ResetAction(bot));
    }
}
