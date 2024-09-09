package com.example.meepmeeprun;

import static com.example.meepmeeprun.MeepMeepFunctions.*;

import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        String path = MeepMeepConstants.B1;

        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(800);
        // custom image stuff

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(MeepMeepConstants.MAXVEL, MeepMeepConstants.MAXACCEL, MeepMeepConstants.MAXANGVEL, MeepMeepConstants.MAXANGACCEL, MeepMeepConstants.TRACKWIDTH) // 11x11 tw
                .build();
        if(path.equalsIgnoreCase(MeepMeepConstants.B1)){
            myBot.runAction(myBot.getDrive().actionBuilder(MeepMeepConstants.BLUE_1)
                    .strafeToLinearHeading(new Vector2d(0,34),Math.toRadians(270))
                    .strafeToLinearHeading(new Vector2d(0,40),Math.toRadians(270))
                    .strafeToLinearHeading(new Vector2d(48,34),Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(50,52),Math.toRadians(45)) // score hb
                    .strafeToLinearHeading(new Vector2d(54,34),Math.toRadians(110))
                    .strafeToLinearHeading(new Vector2d(50,52),Math.toRadians(45)) // score hb
                    .strafeToLinearHeading(new Vector2d(58,25),Math.toRadians(180))
                    .strafeToLinearHeading(new Vector2d(50,52),Math.toRadians(45))
                    .strafeToLinearHeading(new Vector2d(0,34),Math.toRadians(270))
                    .build());
        }
        if (path.equalsIgnoreCase(MeepMeepConstants.B2)){
            myBot.runAction(myBot.getDrive().actionBuilder(MeepMeepConstants.BLUE_2)
                    .strafeToLinearHeading(new Vector2d(-9,34),Math.toRadians(270))
                    .strafeToLinearHeading(new Vector2d(-11,40),Math.toRadians(270))
                    .strafeToLinearHeading(new Vector2d(-44,40),Math.toRadians(180+45+18)) //-30,60
                    .strafeToLinearHeading(new Vector2d(-30,60),Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(50,52),Math.toRadians(45))
                    .strafeToLinearHeading(new Vector2d(-30,60),Math.toRadians(180+45+18)) //-55,33
                    .strafeToLinearHeading(new Vector2d(-55,36),Math.toRadians(180+45+18))
                    .strafeToLinearHeading(new Vector2d(-30,60),Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(50,52),Math.toRadians(45))
                    .strafeToLinearHeading(new Vector2d(-30,60),Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(-57,26),Math.toRadians(180))
                    .strafeToLinearHeading(new Vector2d(-30,60),Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(50,52),Math.toRadians(45))
                    .strafeToLinearHeading(new Vector2d(-9,34),Math.toRadians(270))
                    .build());
        }
        if (path.equalsIgnoreCase(MeepMeepConstants.R1)){
            myBot.runAction(myBot.getDrive().actionBuilder(MeepMeepConstants.RED_1) //270 ->90 180->0
                    .strafeToLinearHeading(new Vector2d(0,-34),Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(0,-40),Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(-48,-34),Math.toRadians(270))
                    .strafeToLinearHeading(new Vector2d(-50,-52),Math.toRadians(270-45)) // score hb
                    .strafeToLinearHeading(new Vector2d(-54,-34),Math.toRadians(270+45))
                    .strafeToLinearHeading(new Vector2d(-50,-52),Math.toRadians(270-45)) // score hb
                    .strafeToLinearHeading(new Vector2d(-58,-25),Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(-50,-52),Math.toRadians(270-45))
                    .strafeToLinearHeading(new Vector2d(0,-34),Math.toRadians(90))
                    .build());
        }
        if (path.equalsIgnoreCase(MeepMeepConstants.R2)){ //270 ->90 180->0
            myBot.runAction(myBot.getDrive().actionBuilder(MeepMeepConstants.RED_2)
                    .strafeToLinearHeading(new Vector2d(9,-34),Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(11,-40),Math.toRadians(90))
                    .strafeToLinearHeading(new Vector2d(44,-40),Math.toRadians(180+45+18)) //-30,60
                    .strafeToLinearHeading(new Vector2d(30,-60),Math.toRadians(180))
                    .strafeToLinearHeading(new Vector2d(-50,-52),Math.toRadians(225))
                    .strafeToLinearHeading(new Vector2d(30,-60),Math.toRadians(180+45+18)) //-55,33
                    .strafeToLinearHeading(new Vector2d(55,-36),Math.toRadians(180+45+18))
                    .strafeToLinearHeading(new Vector2d(30,-60),Math.toRadians(180))
                    .strafeToLinearHeading(new Vector2d(-50,-52),Math.toRadians(225))
                    .strafeToLinearHeading(new Vector2d(30,-60),Math.toRadians(180))
                    .strafeToLinearHeading(new Vector2d(57,-26),Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(30,-60),Math.toRadians(180))
                    .strafeToLinearHeading(new Vector2d(-50,-52),Math.toRadians(225))
                    .strafeToLinearHeading(new Vector2d(9,-34),Math.toRadians(90))
                    .build());
        }


        meepMeep.setBackground(getImg())
                .addEntity(myBot)
                .start();
    }
}