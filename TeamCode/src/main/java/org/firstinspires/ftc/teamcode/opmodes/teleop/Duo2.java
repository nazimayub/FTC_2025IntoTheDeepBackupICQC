package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.base.bot.Robot;

@TeleOp(name="Duo2", group=".TeleOp")
public class Duo2 extends CommandOpMode {
    public static Robot bot;
    @Override
    public void initialize() {
        bot = new Robot(Robot.Mode.DUO, gamepad1, gamepad2, hardwareMap);

        bot.Action(bot.base,
                GamepadKeys.Button.A,
                bot.SpecimenGrab(),
                null);

        bot.Action(bot.base,
                GamepadKeys.Button.B,
                bot.SpecimenScore(),
                null);

        bot.Action(bot.base,
                GamepadKeys.Button.DPAD_DOWN,
                bot.Reset(),
                null);

        bot.Action(bot.op,
                GamepadKeys.Button.RIGHT_BUMPER,
                bot.Intake(true),
                bot.IntakeRest());

        bot.Action(bot.op,
                GamepadKeys.Button.LEFT_BUMPER,
                bot.Intake(false),
                bot.IntakeRest());

        bot.Action(bot.op,
                GamepadKeys.Button.A,
                bot.SubmersibleIntake(),
                null);

        bot.Action(bot.op,
                GamepadKeys.Button.B,
                bot.Transfer(),
                null);

        bot.Action(bot.op,
                GamepadKeys.Button.Y,
                bot.HighBasketPos(),
                null);

        bot.Action(bot.op,
                GamepadKeys.Button.X,
                bot.HighBasketScore(),
                null);
    }
}
