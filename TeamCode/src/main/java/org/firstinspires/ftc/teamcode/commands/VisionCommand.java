package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.VisionSubSystem;

public class VisionCommand extends CommandBase {
    private final VisionSubSystem v;
    public VisionCommand(VisionSubSystem v) {
        this.v = v;
    }
}
