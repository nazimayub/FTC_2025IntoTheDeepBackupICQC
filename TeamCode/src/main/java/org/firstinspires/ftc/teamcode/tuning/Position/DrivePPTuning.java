package org.firstinspires.ftc.teamcode.tuning.Position;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.base.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.tuning.Pedro.constants.FConstants;
import org.firstinspires.ftc.teamcode.tuning.Pedro.constants.LConstants;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathChain;

import java.util.ArrayList;

/**
 * Change paths/poses through FTC Dash, hit A to run the path
 */
@Config
public class DrivePPTuning extends OpMode {

    private Follower follower;
    private FollowPathCommand followPathCommand;

    public static int pathCount = 1;
    public static int[] poseCounts = {4};
    public static double maxPower = 1.0;
    public static boolean holdEnd = true;

    public static ArrayList<ArrayList<Pose>> Paths = new ArrayList<>();

    @Override
    public void init() {
        follower = new Follower(hardwareMap);
        Constants.setConstants(FConstants.class, LConstants.class);

        while (Paths.size() < pathCount) {
            Paths.add(new ArrayList<>());
        }

        for (int i = 0; i < Paths.size(); i++) {
            while (Paths.get(i).size() < poseCounts[i]) {
                Paths.get(i).add(new Pose(0, 0, 0));
            }
        }
    }

    @Override
    public void loop() {
        telemetry.addData("Path count", pathCount);
        telemetry.addData("Max Power", maxPower);
        telemetry.addData("Hold End", holdEnd);

        for (int i = 0; i < Paths.size(); i++) {
            telemetry.addLine("Path " + i + ":");
            for (int j = 0; j < Paths.get(i).size(); j++) {
                Pose pose = Paths.get(i).get(j);
                telemetry.addData("  Pose " + j, "x: %.2f, y: %.2f, heading: %.2f",
                        pose.getX(), pose.getY(), pose.getHeading());
            }
        }
        telemetry.update();

        if (gamepad1.a) {
            int selectedPath = 0;
            if (selectedPath < Paths.size()) {
                PathChain pathChain = generatePath(selectedPath);
                if (pathChain != null) {
                    followPathCommand = new FollowPathCommand(follower, pathChain, holdEnd, maxPower);
                    CommandScheduler.getInstance().schedule(followPathCommand);
                }
            }
        }
    }

    public PathChain generatePath(int pathIndex) {
        if (Paths.isEmpty() || pathIndex >= Paths.size() || Paths.get(pathIndex).isEmpty()) {
            telemetry.addLine("Invalid path selected.");
            telemetry.update();
            return null;
        }

        ArrayList<Point> controlPoints = new ArrayList<>();
        ArrayList<Pose> selectedPath = Paths.get(pathIndex);

        for (Pose pose : selectedPath) {
            controlPoints.add(new Point(pose.getX(), pose.getY(), 1));
        }

        BezierCurve bezierCurve = new BezierCurve(controlPoints);

        return follower.pathBuilder()
                .addPath(bezierCurve)
                .setLinearHeadingInterpolation(selectedPath.get(0).getHeading(), selectedPath.get(selectedPath.size() - 1).getHeading())
                .build();
    }
}
