package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.utils.MotorConfig;
import org.firstinspires.ftc.teamcode.utils.MotorDirectionConfig;

public class Drive extends SubsystemBase {
    DcMotorEx fr,fl,br,bl;
    IMU imu;

    public Drive(final HardwareMap hMap, String imu, MotorConfig config, MotorDirectionConfig directionConfig) {
        this.fr=hMap.get(DcMotorEx.class,config.getFr());
        this.fl=hMap.get(DcMotorEx.class,config.getFl());
        this.br=hMap.get(DcMotorEx.class,config.getBr());
        this.bl=hMap.get(DcMotorEx.class,config.getBl());
        if(directionConfig.getFr()){
            this.fr.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if(directionConfig.getFl()){
            this.fl.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if(directionConfig.getBr()){
            this.br.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if(directionConfig.getBl()){
            this.bl.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        // Retrieve the IMU from the hardware map
        this.imu = hMap.get(IMU.class, imu);
        IMU.Parameters parameters = Constants.IMU_ORIENTATION;
        this.imu.initialize(parameters);
        this.fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }


    /*
     * y = -leftsticky
     * x = leftstickx
     * r = rightstickx
     */
    public void robotCentricDrive(double x, double y, double r){
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(x), 1);
        fl.setPower((y+x+r)/denominator);
        bl.setPower((y-x+r)/denominator);
        fr.setPower((y-x-r)/denominator);
        br.setPower((y+x-r)/denominator);
    }
    public double getFL(){
        return this.fl.getVelocity();
    }
    public double getFR(){
        return this.fr.getVelocity();
    }
    public double getBL(){
        return this.bl.getVelocity();
    }
    public double getBR(){
        return this.br.getVelocity();
    }
    public double getHeading(){
        return this.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
    /*
     * y = -leftsticky
     * x = leftstickx
     * r = rightstickx
     */
    public void fieldCentricDrive(double y,double x,double r){
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(r), 1);
        fl.setPower((rotY + rotX + r)/denominator);
        bl.setPower((rotY - rotX + r)/denominator);
        fr.setPower((rotY - rotX - r)/denominator);
        br.setPower((rotY + rotX - r)/denominator);
    }

    public void resetYaw(){
        imu.resetYaw();
    }

}
