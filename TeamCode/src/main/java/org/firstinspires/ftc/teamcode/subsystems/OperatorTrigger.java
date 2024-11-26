package org.firstinspires.ftc.teamcode.subsystems;
import org.firstinspires.ftc.teamcode.teleop.Duo;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class OperatorTrigger extends Trigger {
    @Override
    public boolean get() {
        // This returns whether the trigger is active
        return 0!=Duo.op.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-Duo.op.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);

    }
}
