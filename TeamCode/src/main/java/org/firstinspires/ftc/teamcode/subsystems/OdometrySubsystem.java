package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.SparkFunOTOSCorrected;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import org.firstinspires.ftc.teamcode.utils.*;

import java.util.HashMap;
import java.util.HashSet;

public class OdometrySubsystem extends SubsystemBase {
    private SparkFunOTOSCorrected otos;

    public OdometrySubsystem(HardwareMap hardwareMap, String name) {
        I2cDeviceSynch deviceClient = hardwareMap.get(I2cDeviceSynch.class, name);

        otos = new SparkFunOTOSCorrected(deviceClient);
        otos.initialize();
    }

    public double getXPosition() {
        return otos.getPosition().x;
    }

    public double getYPosition() {
        return otos.getPosition().y;
    }

    public double getHeading() {
        return otos.getPosition().h;
    }
}
