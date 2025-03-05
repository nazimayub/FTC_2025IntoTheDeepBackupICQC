package org.firstinspires.ftc.teamcode.base.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.base.subsystems.VisionSubSystem;

public class VisionCommand extends CommandBase {
    private final VisionSubSystem v;
    public VisionCommand(VisionSubSystem v) {
        this.v = v;
    }
}
