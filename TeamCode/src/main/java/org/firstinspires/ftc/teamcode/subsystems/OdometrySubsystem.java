package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import org.firstinspires.ftc.teamcode.utils.*;

import java.util.HashMap;
import java.util.HashSet;

public class OdometrySubsystem extends SubsystemBase {
    private SparkFunOTOS otos;
    public static HashMap<String, Pose2d> startPoses;

    public OdometrySubsystem(HardwareMap hardwareMap, String name) {
        I2cDeviceSynch deviceClient = hardwareMap.get(I2cDeviceSynch.class, name);
        startPoses = new HashMap<>();
        startPoses.put("redFront", new Pose2d(12, 12, 0));
        startPoses.put("redBack", new Pose2d(12, 62, 0));
        startPoses.put("blueFront", new Pose2d(36, 12, 180));
        startPoses.put("blueBack", new Pose2d(36, 62, 180));

        otos = new SparkFunOTOS(deviceClient);
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
