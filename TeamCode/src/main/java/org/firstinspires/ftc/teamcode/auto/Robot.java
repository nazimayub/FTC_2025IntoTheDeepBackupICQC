package org.firstinspires.ftc.teamcode.auto;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Main Robot Paradigm
 */
public abstract class Robot extends LinearOpMode {
    public void reset() {
        CommandScheduler.getInstance().reset();
    }

    /**
     * Runs the {@link CommandScheduler} instance
     */
    public void run(){CommandScheduler.getInstance().run();}

    /**
     * Schedules {@link com.arcrobotics.ftclib.command.Command} objects to the scheduler
     */
    public void schedule(Command... commands) {
        CommandScheduler.getInstance().schedule(commands);
    }

    /**
     * Registers {@link com.arcrobotics.ftclib.command.Subsystem} objects to the scheduler
     */
    public void register(Subsystem... subsystems) {
        CommandScheduler.getInstance().registerSubsystem(subsystems);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        while(opModeInInit()&!isStopRequested()){
            preInit();
        }
        telemetry.clearAll(); // clear all telemetry so we dont have pre init sutff here...
        // run the scheduler
        while(opModeIsActive()&&!isStopRequested()){
            run();
        }
        reset();
    }
    /**
     * Schedule your commands here do NOT put while loops here
     */
    public abstract void initialize();
    /**
     * code before init in the opmode DO NOT put init while loop here as it is already in one.
     */
    public abstract void preInit();

    public static void disable() {
        com.arcrobotics.ftclib.command.Robot.disable();
    }

    public static void enable() {
        com.arcrobotics.ftclib.command.Robot.enable();
    }
    public boolean isOpModeActive(){
        return opModeIsActive();
    }

}
