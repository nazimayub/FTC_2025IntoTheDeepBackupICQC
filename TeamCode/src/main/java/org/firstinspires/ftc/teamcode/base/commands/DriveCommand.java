package org.firstinspires.ftc.teamcode.base.commands;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.base.subsystems.Drive;

public class DriveCommand extends RunCommand {

    public DriveCommand(Drive drive, GamepadEx gamepad){
        super(() -> drive.robotCentricDrive(
                gamepad.getLeftX(),
                -gamepad.getLeftY(),
                gamepad.getRightX()
        ), drive);
//        super(()->drive.fieldCentricDrive(
//                gamepad.getLeftX(),
//                -gamepad.getLeftY(),
//                gamepad.getRightX()
//        ),drive);
    }


}
