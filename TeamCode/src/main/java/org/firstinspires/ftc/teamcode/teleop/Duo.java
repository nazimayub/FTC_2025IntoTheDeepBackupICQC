package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class Duo extends CommandOpMode {
    @Override
    public void initialize() {
        GamepadEx drive = new GamepadEx(gamepad1);
        GamepadEx tool = new GamepadEx(gamepad2);
        Button a1 = new GamepadButton(
                drive, GamepadKeys.Button.A
        );
        Button b1 = new GamepadButton(
                drive, GamepadKeys.Button.B
        );
        Button x1 = new GamepadButton(
                drive, GamepadKeys.Button.X
        );
        Button y1 = new GamepadButton(
                drive, GamepadKeys.Button.Y
        );
        Button up1 = new GamepadButton(
                drive, GamepadKeys.Button.DPAD_UP
        );
        Button left1 = new GamepadButton(
                drive, GamepadKeys.Button.DPAD_LEFT
        );
        Button right1 = new GamepadButton(
                drive, GamepadKeys.Button.DPAD_RIGHT
        );
        Button down1 = new GamepadButton(
                drive, GamepadKeys.Button.DPAD_DOWN
        );
        Button rightB1 = new GamepadButton(
                drive, GamepadKeys.Button.RIGHT_BUMPER
        );
        Button leftB1 = new GamepadButton(
                drive, GamepadKeys.Button.LEFT_BUMPER
        );
        Button leftS1 = new GamepadButton(
                drive, GamepadKeys.Button.LEFT_STICK_BUTTON
        );
        Button rightS1 = new GamepadButton(
                drive, GamepadKeys.Button.RIGHT_BUMPER
        );


        Button a2 = new GamepadButton(
                drive, GamepadKeys.Button.A
        );
        Button b2 = new GamepadButton(
                tool, GamepadKeys.Button.B
        );
        Button x2 = new GamepadButton(
                tool, GamepadKeys.Button.X
        );
        Button y2 = new GamepadButton(
                tool, GamepadKeys.Button.Y
        );
        Button up2 = new GamepadButton(
                tool, GamepadKeys.Button.DPAD_UP
        );
        Button left2 = new GamepadButton(
                tool, GamepadKeys.Button.DPAD_LEFT
        );
        Button right2 = new GamepadButton(
                tool, GamepadKeys.Button.DPAD_RIGHT
        );
        Button down2 = new GamepadButton(
                tool, GamepadKeys.Button.DPAD_DOWN
        );
        Button rightB2 = new GamepadButton(
                tool, GamepadKeys.Button.RIGHT_BUMPER
        );
        Button leftB2 = new GamepadButton(
                tool, GamepadKeys.Button.LEFT_BUMPER
        );
        Button leftS2 = new GamepadButton(
                tool, GamepadKeys.Button.LEFT_STICK_BUTTON
        );
        Button rightS2 = new GamepadButton(
                tool, GamepadKeys.Button.RIGHT_BUMPER
        );
    }
}
