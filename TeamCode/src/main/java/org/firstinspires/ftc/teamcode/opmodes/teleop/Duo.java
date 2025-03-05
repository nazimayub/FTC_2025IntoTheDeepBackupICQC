package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.base.bot.Actions;
import org.firstinspires.ftc.teamcode.base.bot.Robot;

@TeleOp(name="Duo",group=".TeleOp")
public class Duo extends CommandOpMode {
    public Robot bot;
    public Duo(Robot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {
        bot = new Robot(Robot.Mode.DUO, hardwareMap);

        // DRIVER CONTROLS
        new GamepadButton(bot.base, GamepadKeys.Button.A)
                .whenPressed(Actions.SpecimenGrabAction(bot));

        new GamepadButton(bot.base, GamepadKeys.Button.B)
                .whenPressed(Actions.SpecimenScoreAction(bot));

        new GamepadButton(bot.base, GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(Actions.ResetAction(bot));

        // OPERATOR CONTROLS
        new GamepadButton(bot.op, GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(Actions.IntakeAction(bot, true))
                .whenReleased(Actions.IntakeRestAction(bot));

        new GamepadButton(bot.op, GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(Actions.IntakeAction(bot, false))
                .whenReleased(Actions.IntakeRestAction(bot));

        new GamepadButton(bot.op, GamepadKeys.Button.A)
                .whenPressed(Actions.SubmersibleIntakeAction(bot));

        new GamepadButton(bot.op, GamepadKeys.Button.B)
                .whenPressed(Actions.TransferAction(bot));

        new GamepadButton(bot.op, GamepadKeys.Button.Y)
                .whenPressed(Actions.HighBasketAction(bot));

        new GamepadButton(bot.op, GamepadKeys.Button.X)
                .whenPressed(Actions.ReleaseAction(bot));
    }
}
