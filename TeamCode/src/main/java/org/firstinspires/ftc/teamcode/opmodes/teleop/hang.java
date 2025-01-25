package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.subsystems.*;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utils.*;

@TeleOp
public class hang extends CommandOpMode {
    GamepadEx base;
    public static GamepadEx op;
    SimpleLogger log;

    public static Drive drive;
    public static SlideSubsystem vSlides;
    public static ServoSubsystem outtakeClawRot, outtakeClaw, intakeClawDist, intakeClawRot, outtakeClawTwist, outtakeClawDistRight, outtakeClawDistLeft, rHang, lHang, gearShifter;
    public static WaitSubsystem pause;

    @Override
    public void initialize() {
        base = new GamepadEx(gamepad1);
        op = new GamepadEx(gamepad2);
        log = new SimpleLogger();
        drive = new Drive(hardwareMap, Const.imu,new MotorConfig(Const.fr, Const.fl, Const.br, Const.bl),new MotorDirectionConfig(false,true,false,true));
        pause = new WaitSubsystem();
//        outtakeClaw = new ServoSubsystem(hardwareMap, Const.outtakeClaw);
//        intakeClawDist = new ServoSubsystem(hardwareMap, Const.intakeDist);
//        intakeClawRot = new ServoSubsystem(hardwareMap, Const.intakeRot);
//        outtakeClawDistLeft = new ServoSubsystem(hardwareMap, Const.outtakeDistLeft);
//        outtakeClawDistRight = new ServoSubsystem(hardwareMap, Const.outtakeDistRight);
//        rHang = new ServoSubsystem(hardwareMap, Const.rightHang);
//        lHang = new ServoSubsystem(hardwareMap, Const.leftHang);
//        gearShifter = new ServoSubsystem(hardwareMap, Const.gearShifter);
//        outtakeClawRot = new ServoSubsystem(hardwareMap, Const.outtakeRot);
//        outtakeClawTwist = new ServoSubsystem(hardwareMap, Const.outtakeTwist);
        vSlides = new SlideSubsystem(hardwareMap, Const.rSlide, Const.lSlide, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE);

        //Default Commands
        drive.setDefaultCommand(new DriveCommand(drive,base));
        vSlides.setDefaultCommand(new SlideArmCommand(vSlides, base));




    }
}
