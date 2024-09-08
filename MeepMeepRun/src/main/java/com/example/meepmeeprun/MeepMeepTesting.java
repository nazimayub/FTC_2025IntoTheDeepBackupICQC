package com.example.meepmeeprun;

import com.acmerobotics.roadrunner.MappedPosePath;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public class MeepMeepTesting {
    public static void main(String[] args) {

        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(800);
        // custom image stuff
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(150, 150, Math.toRadians(180), Math.toRadians(180), 11) // 11x11 tw
                .build();
        RoadRunnerBotEntity myBot2 = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(150, 150, Math.toRadians(180), Math.toRadians(180), 11) // 11x11 tw
                .build();
        String path = "1+4zp2";
        boolean run = true;
        if(path.equals("1+5zp1") || run){
            myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(20, 60, Math.toRadians(270)))
                    .strafeToConstantHeading(new Vector2d(10,35))
                        .waitSeconds(1)
                    .strafeToConstantHeading(new Vector2d(15, 45))
                    .strafeToLinearHeading(new Vector2d(40, 45), Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(47, 35), Math.toRadians(90))
                        .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(55, 55), Math.toRadians(45))
                        .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(48, 36), Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(-36, 36), Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(-49, -13), Math.toRadians(90))
                        .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(-36, 60), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(50, 60), Math.toRadians(0))
                        .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(56, 24), Math.toRadians(0))
                        .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(60, 50), Math.toRadians(90))
                        .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(50, 36), Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(-60, 36), Math.toRadians(0))
                        .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(-36, 60), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(50, 60), Math.toRadians(0))
                        .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(48, 36), Math.toRadians(270))
                    .strafeToLinearHeading(new Vector2d(-10, 36), Math.toRadians(270))
                    .build());
        }
        if (path.equals("1+4zp2") || run){
            myBot2.runAction(myBot.getDrive().actionBuilder(new Pose2d(-20, 60, Math.toRadians(270)))
                    .strafeToConstantHeading(new Vector2d(-10,35))
                    .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(-36, 36), Math.toRadians(270))
                    .strafeToLinearHeading(new Vector2d(-49, 36), Math.toRadians(90))
                            .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(0, 60), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(50, 60), Math.toRadians(0))
                            .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(60, 36), Math.toRadians(90))
                            .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(55, 55), Math.toRadians(45))
                            .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(36, 36), Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(-36, 36), Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(60, -13), Math.toRadians(90))
                            .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(-36, 60), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(50, 60), Math.toRadians(0))
                            .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(36, 36), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(-36, 36), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(-60, -24), Math.toRadians(0))
                            .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(-36, 60), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(50, 60), Math.toRadians(0))
                            .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(10, 36), Math.toRadians(270))

                    .build());
        }
        /*
        if(path.equals("1+5zp1") || run){
            myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(20, 60, Math.toRadians(270)))
                    .strafeToConstantHeading(new Vector2d(10,35))
                    .waitSeconds(1)
                    .strafeToConstantHeading(new Vector2d(15, 45))
                    .strafeToLinearHeading(new Vector2d(40, 45), Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(47, 35), Math.toRadians(90))
                    .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(55, 55), Math.toRadians(45))
                    .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(57, 35), Math.toRadians(90))
                    .waitSeconds(2)
                    .strafeToLinearHeading(new Vector2d(55, 55), Math.toRadians(45))
                    .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(55, 24), Math.toRadians(180))
                    .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(55, 55), Math.toRadians(45))
                    .waitSeconds(1)
                    .strafeToConstantHeading(new Vector2d(55, 40))
                    .strafeToLinearHeading(new Vector2d(-47, 35), Math.toRadians(90))
                    .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(55, 55), Math.toRadians(45))
                    .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(-57, 35), Math.toRadians(90))
                    .waitSeconds(2.3)
                    .strafeToLinearHeading(new Vector2d(20, 60), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(50, 60), Math.toRadians(0))
                    .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(10, 35), Math.toRadians(90))
                    .build());
        }
        if (path.equals("1+4zp2") || run){
            myBot2.runAction(myBot.getDrive().actionBuilder(new Pose2d(-20, 60, Math.toRadians(270)))
                    .strafeToConstantHeading(new Vector2d(-10,35))
                    .waitSeconds(1)
                    .strafeToConstantHeading(new Vector2d(-36, 36))
                    .strafeToLinearHeading(new Vector2d(-48, -13), Math.toRadians(90))
                    .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(-40,60),Math.toRadians(0))
                    .strafeToConstantHeading(new Vector2d(50,60))
                    .waitSeconds(1)
                    .strafeToConstantHeading(new Vector2d(-36, 36))
                    .strafeToLinearHeading(new Vector2d(-55, -13), Math.toRadians(90))
                    .waitSeconds(2)
                    .strafeToLinearHeading(new Vector2d(-40,60),Math.toRadians(0))
                    .strafeToConstantHeading(new Vector2d(50,60))
                    .waitSeconds(1)
                    .strafeToConstantHeading(new Vector2d(-50, 60))
                    .waitSeconds(1)
                    .strafeToLinearHeading(new Vector2d(-55, -24), Math.toRadians(0))
                    .waitSeconds(1)
                    .strafeToConstantHeading(new Vector2d(-22, 60))
                    .strafeToConstantHeading(new Vector2d(50, 60))
                    .waitSeconds(1)
                    .strafeToConstantHeading(new Vector2d(50, 40))
                    .strafeToLinearHeading(new Vector2d(-10, 35), Math.toRadians(90))
                    .build());
        }
*/
        Image img = null;
        try {
            img = ImageIO.read(new File("field.png"));
        }
        catch (IOException e) {System.out.println(e);}

        meepMeep.setBackground(img)
                .addEntity(myBot)
                .addEntity(myBot2)
                .start();
    }
}