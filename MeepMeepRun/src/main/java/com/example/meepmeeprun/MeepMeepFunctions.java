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
}
