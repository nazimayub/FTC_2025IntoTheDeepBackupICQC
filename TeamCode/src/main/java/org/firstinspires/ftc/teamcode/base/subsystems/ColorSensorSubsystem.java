package org.firstinspires.ftc.teamcode.base.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ColorSensorSubsystem extends SubsystemBase {
    ColorSensor color;

    public ColorSensorSubsystem(HardwareMap h, String name) {
        this.color = h.get(ColorSensor.class, name);
    }

    public int getColor() {
        return Math.max(color.red(), Math.max(color.blue(), color.green()));
    }
}
