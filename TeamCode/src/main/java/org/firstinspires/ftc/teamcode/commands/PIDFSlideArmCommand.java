package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSingleSlideSubsystemAdv;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PIDFSlideSubsystemAdv;
import org.firstinspires.ftc.teamcode.subsystems.SlideSubsystem;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input
 * (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class PIDFSlideArmCommand extends CommandBase {

    private PIDFSlideSubsystem PIDFSlide;
    private PIDFSlideSubsystemAdv PIDFSlideAdv;
    private PIDFArmSubsystem arm;
    private PIDFSingleSlideSubsystemAdv PIDFSingleSlideAdv;
    private PIDFSingleSlideSubsystem PIDFSingleSlide;
    private final double change;

    public PIDFSlideArmCommand(PIDFSingleSlideSubsystem PIDFSingleSlide, double change) {
        this.PIDFSingleSlide=PIDFSingleSlide;
        this.change = change;
        addRequirements(PIDFSingleSlide);
    }
    public PIDFSlideArmCommand(PIDFSingleSlideSubsystemAdv PIDFSingleSlideAdv, double change) {
        this.PIDFSingleSlideAdv=PIDFSingleSlideAdv;
        this.change = change;
        addRequirements(PIDFSingleSlideAdv);
    }
    public PIDFSlideArmCommand(PIDFSlideSubsystem PIDFSlide, double change) {
        this.PIDFSlide=PIDFSlide;
        this.change = change;
        addRequirements(PIDFSlide);
    }
    public PIDFSlideArmCommand(PIDFSlideSubsystemAdv PIDFSlideAdv, double change) {
        this.PIDFSlideAdv=PIDFSlideAdv;
        this.change = change;
        addRequirements(PIDFSlideAdv);
    }
    public PIDFSlideArmCommand(PIDFArmSubsystem arm, double change) {
        this.arm=arm;
        this.change = change;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        if(PIDFSlide!=null){
            PIDFSlide.change(change);
        }
        else if(PIDFSlideAdv != null){
            PIDFSlideAdv.change(change);
        }
        else if(PIDFSingleSlideAdv != null){
            PIDFSingleSlideAdv.change(change);
        }
        else if(PIDFSingleSlide != null){
            PIDFSingleSlide.change(change);
        }
        else{
            arm.change(change);
        }
    }

}
