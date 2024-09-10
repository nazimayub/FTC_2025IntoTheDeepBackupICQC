package com.example.meepmeeprun;

import com.noahbres.meepmeep.MeepMeep;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepFunctions {
    public static Image getImg(){
        Image img = null;
        try {
            img = ImageIO.read(new File("field.png"));
        }
        catch (IOException e) {System.out.println(e);}
        return img;
    }
    public static double normalizeAngle(double angle) {
        // Add 180 degrees to flip the angle
        double flippedAngle = angle + Math.PI;

        // Normalize angle to be within [0, 2*PI) radians
        double normalized = Math.toDegrees(flippedAngle) % 360;
        return normalized < 0 ? Math.toRadians(normalized + 360) : Math.toRadians(normalized);
    }
    public static int tilesToInches(int tiles){
        return tiles*24;
    }
    public static double InchesToTiles(int inches){
        return inches/24;
    }
}
