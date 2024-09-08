package com.example.meepmeeprun;

import com.acmerobotics.roadrunner.Pose2d;

public class MeepMeepConstants {
    public static final Pose2d BLUE_1 = new Pose2d(20,60,Math.toRadians(270));
    public static final Pose2d BLUE_2 = new Pose2d(-20,60,Math.toRadians(270));

    public static final Pose2d RED_1 = new Pose2d(-20,-60,Math.toRadians(90));
    public static final Pose2d RED_2 = new Pose2d(20,-60,Math.toRadians(90));


    public static final String B1 = "B1";
    public static final String B2 = "B2";
    public static final String R1 = "R1";
    public static final String R2 = "R2";
    public static final double MAXVEL = 120;
    public static final double MAXACCEL = 120;
    public static final double MAXANGVEL = Math.toRadians(180);
    public static final double MAXANGACCEL = Math.toRadians(180);
    public static final double TRACKWIDTH = 11;
}
