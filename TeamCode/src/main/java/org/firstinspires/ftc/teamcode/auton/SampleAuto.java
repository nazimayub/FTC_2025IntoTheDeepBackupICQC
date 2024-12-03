package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.auton.RobotDrive;
import org.firstinspires.ftc.teamcode.auton.Minibot;

//Class that scores 3 + 1 Samples in Auto



@Autonomous

public class SampleAuto extends LinearOpMode {

    public Minibot bot;

    @Override
    public void runOpMode() {

        bot = new Minibot(); //init robot class (which includes robotdrive)
        bot.init(hardwareMap);
        bot.resetEncoders();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // bot.linkage("in");
        // bot.intakewrist("up");
        // bot.scorewrist("middle");
        // bot.grabber("grab");

        waitForStart();

        // bot starts zeroed out. moves are relative to starting pos
        bot.setHeading(270); //if zero NaN error
        bot.setFieldXY(0.001, 0.001); //if zero NaN error

        Minibot.Pose[] score = new Minibot.Pose[]{ //sets your targets
                new Minibot.Pose(0, 50,  0),// (x inches (abs), y inches (abs), rotation degrees (abs))
        };
        Minibot.Pose[] grab = new Minibot.Pose[]{ //sets your targets
                new Minibot.Pose(10, 6,  180),// (x inches (abs), y inches (abs), rotation degrees (abs))
        };

        Minibot.Pose[] scorePreload = new Minibot.Pose[]{ //sets your targets
                new Minibot.Pose(0, -52,  0),// (x inches (abs), y inches (abs), rotation degrees (abs))
        };
        //Unused Poses
        /*
        Minibot.Pose[] score1 = new Minibot.Pose[]{ //sets your targets
                new Minibot.Pose(2, 32,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
        };
        Minibot.Pose[] score2 = new Minibot.Pose[]{ //sets your targets
                new Minibot.Pose(4, 32,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
        };
        Minibot.Pose[] score3 = new Minibot.Pose[]{ //sets your targets
                new Minibot.Pose(6, 32,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
        };
        Minibot.Pose[] score4 = new Minibot.Pose[]{ //sets your targets
                new Minibot.Pose(8, 32,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
        };

        Minibot.Pose[] push = new Minibot.Pose[]{ //sets your targets
                new Minibot.Pose(-32, 28,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
                new Minibot.Pose(-36, 50,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
                new Minibot.Pose(-42, 54,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
                new Minibot.Pose(-42, 12,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
                new Minibot.Pose(-42, 50,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
                new Minibot.Pose(-48, 54,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
                new Minibot.Pose(-48, 12,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
                new Minibot.Pose(-48, 50,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
                new Minibot.Pose(-56, 54,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
                new Minibot.Pose(-56, 12,  0.001),// (x inches (abs), y inches (abs), rotation degrees (abs))
        };

         */



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            followPath(scorePreload, .5);
            sleep(1000);
            bot.scoreBasket();
            sleep(1000);
            bot.score();
            //followPath(push, 0.6);
            //sleep(1000);
            //bot.grabPos();
            //followPath(grab, 0.5);
            //bot.grab();
            //followPath(score1, 0.5);
            //bot.score();
            //sleep(1000);
            //bot.grabPos();
            //followPath(grab, 0.5);
            //bot.grab();
            //followPath(score2, 0.5);
            //bot.score();
            //sleep(1000);
            //bot.grabPos();
            //followPath(grab, 0.5);
            //bot.grab();
            //followPath(score3, 0.5);
            //bot.score();
            //sleep(1000);
            //bot.grabPos();
            //followPath(grab, 0.5);
            //bot.grab();
            //followPath(score4, 0.5);
            //bot.score();

            //int temp = 0;
//            bot.intakewrist("mid");
//            bot.grabber("grab");
//            followPath(highBasket1,0.6);
//            sleep(1200);
//            bot.highBasket();
//            sleep(1000);
//            bot.grabber("ungrab");
//            sleep(1000);
//            bot.drop();
//            sleep(750);
//            bot.home();
//
//            //preload
//
//            followPath(firstSampleTurn,0.6);
//            sleep(500);
//            bot.intake();
//            sleep(2000);
//            bot.home();
//            sleep(1000);
//            transfer();
//            sleep(2000);
//            followPath(highBasket2,0.6);
//            //followPath(highBasket1,0.6,1.0,2.5);// lowered XY tolerance because the robot wasnt aligning to the basket enough lol
//            sleep(1200);
//            bot.highBasket();
//            sleep(1000);
//            bot.grabber("ungrab");
//            sleep(1000);
//            bot.drop();
//            sleep(750);
//            bot.home();

            // first sample

            telemetry.addData("imheer", "kill yourself"); // dude what is this emo code :skull: aasiya prolly coded this lmfaooo
            telemetry.update();
            sleep(5000);

            break;
        }
        sleep(50000);
    }

    public void followPath(Minibot.Pose[] path, double power) {

        int i = 0;
        while (i < path.length) {

            if (!opModeIsActive()) return;

            bot.updateTracking();

            double dist = bot.driveToPose(path[i], power);
            double H_error = Math.abs(path[i].h-bot.field.h);
            double X_error = Math.abs(path[i].x-bot.field.x);
            double Y_error = Math.abs(path[i].y-bot.field.y);

            double error = Math.abs(X_error+Y_error+H_error);

            telemetry.addData("path target", i);
            telemetry.addData("target pose", path[i]);
            telemetry.addData("field pose", bot.field);
            telemetry.addData("dist", dist);
            telemetry.addData("power", bot.lb.getPower());
            telemetry.addData("IMU Raw", bot.getIMUHeading());
            telemetry.addData("IMU + Init Offset", bot.getHeading());
            telemetry.addData("fr encoder", bot.rf.getCurrentPosition());
            telemetry.addData("fl encoder", bot.lf.getCurrentPosition());
            telemetry.addData("br encoder", bot.rb.getCurrentPosition());
            telemetry.addData("bl encoder", bot.lb.getCurrentPosition());
            telemetry.addData("Error",error);

            telemetry.update();

            if (Math.sqrt(Math.pow(X_error,2)+Math.pow(Y_error,2))<1.5 && H_error < 2.5) i++; // if error radius of (x,y) is less than sqrt(1.5) and heading error is less than 2.5 degrees then go on

        }
        bot.driveXYW(0,0,0); //stop that move in robot relative pos
    }
    public void followPath(Minibot.Pose[] path, double power, double tolerance_XY, double tolerance_heading) {

        int i = 0;
        while (i < path.length) {

            if (!opModeIsActive()) return;

            bot.updateTracking();

            double dist = bot.driveToPose(path[i], power);
            double H_error = Math.abs(path[i].h-bot.field.h);
            double X_error = Math.abs(path[i].x-bot.field.x);
            double Y_error = Math.abs(path[i].y-bot.field.y);

            double error = Math.abs(X_error+Y_error+H_error);

            telemetry.addData("path target", i);
            telemetry.addData("target pose", path[i]);
            telemetry.addData("field pose", bot.field);
            telemetry.addData("dist", dist);
            telemetry.addData("power", bot.lb.getPower());
            telemetry.addData("IMU Raw", bot.getIMUHeading());
            telemetry.addData("IMU + Init Offset", bot.getHeading());
            telemetry.addData("fr encoder", bot.rf.getCurrentPosition());
            telemetry.addData("fl encoder", bot.lf.getCurrentPosition());
            telemetry.addData("br encoder", bot.rb.getCurrentPosition());
            telemetry.addData("bl encoder", bot.lb.getCurrentPosition());
            telemetry.addData("Error",error);

            telemetry.update();

            if (Math.sqrt(Math.pow(X_error,2)+Math.pow(Y_error,2))<tolerance_XY && H_error < tolerance_heading) i++; // if error radius of (x,y) is less than sqrt(1.5) and heading error is less than 2.5 degrees then go on

        }
        bot.driveXYW(0,0,0); //stop that move in robot relative pos
    }

}
