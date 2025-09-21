package org.firstinspires.ftc.teamcode.BackupBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOpMain", group = "TeleOp")
public class TeleOpMain extends OpMode {

    private DcMotor fL, fR, bL, bR, m1;
    private CRServo intake;

    // ===== Arm tuning knobs (adjust these for your robot) =====
    private static final double ARM_DEADZONE = 0.07;         // ignore tiny stick noise
    private static final double ARM_JOG_TICKS_PER_SEC = 1800; // how fast target moves while holding stick
    private static final double ARM_POWER = 0.6;              // power while RUN_TO_POSITION
    private static final int ARM_MIN_TICKS = -10000;          // soft lower limit
    private static final int ARM_MAX_TICKS =  10000;          // soft upper limit
    // ==========================================================

    private int armTargetTicks = 0;
    private double lastTime;

    @Override
    public void init() {
        fL = hardwareMap.get(DcMotor.class, "fL");
        fR = hardwareMap.get(DcMotor.class, "fR");
        bL = hardwareMap.get(DcMotor.class, "bL");
        bR = hardwareMap.get(DcMotor.class, "bR");
        m1 = hardwareMap.get(DcMotor.class, "m1");
        intake = hardwareMap.get(CRServo.class, "intake");

        fR.setDirection(DcMotor.Direction.REVERSE);
        bR.setDirection(DcMotor.Direction.REVERSE);

        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Arm should brake, not float, to help holding when not powered
        m1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize encoder and enter RUN_TO_POSITION with current position as target
        m1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m1.setTargetPosition(0);
        m1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m1.setPower(ARM_POWER);

        armTargetTicks = m1.getCurrentPosition();
        lastTime = getRuntime();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // === Drive (unchanged) ===
        double drive = -gamepad1.left_stick_x; // Forward/Backward
        double strafe =  gamepad1.left_stick_y; // Left/Right
        double turn =    gamepad1.right_stick_x; // Turning

        double fLPower = drive + strafe + turn;
        double fRPower = drive - strafe + turn;
        double bLPower = -drive + strafe + turn;
        double bRPower = -drive - strafe + turn;

        fL.setPower(fLPower);
        fR.setPower(fRPower);
        bL.setPower(bLPower);
        bR.setPower(bRPower);

        // === Arm: RUN_TO_POSITION hold with joystick jogging ===
        double now = getRuntime();
        double dt = now - lastTime;
        lastTime = now;

        double armInput = -gamepad1.left_stick_y; // up is positive
        if (Math.abs(armInput) > ARM_DEADZONE) {
            // Move the target based on stick and elapsed time
            armTargetTicks += (int) (armInput * ARM_JOG_TICKS_PER_SEC * dt);
            // Soft limits
            armTargetTicks = Math.max(ARM_MIN_TICKS, Math.min(ARM_MAX_TICKS, armTargetTicks));
        }
        // Keep RUN_TO_POSITION on every loop and update the target
        if (m1.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            m1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        m1.setTargetPosition(armTargetTicks);
        m1.setPower(ARM_POWER); // power is "how hard to try" to reach/hold target

        //IntakeGrab
        while (gamepad1.b) {
            intake.setPosition(0.5);
        }
            intake.setPosition(0.0);

        // === Telemetry ===
        telemetry.addData("Arm pos", m1.getCurrentPosition());
        telemetry.addData("Arm target", armTargetTicks);
        telemetry.addData("Arm busy", m1.isBusy());
        telemetry.update();


    }
}
