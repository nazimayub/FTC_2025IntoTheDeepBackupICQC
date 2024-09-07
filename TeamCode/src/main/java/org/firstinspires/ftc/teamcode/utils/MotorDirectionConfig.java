package org.firstinspires.ftc.teamcode.utils;

public class MotorDirectionConfig {
    boolean fr,fl,br,bl = false;
    public MotorDirectionConfig(boolean fr, boolean fl, boolean br, boolean bl){
        this.fl = fl;
        this.fr = fr;
        this.br = br;
        this.bl = bl;
    }
    public boolean getFr(){
        return fr;
    }
    public boolean getFl(){
        return fl;
    }
    public boolean getBr(){
        return br;
    }
    public boolean getBl(){
        return bl;
    }
    /*
     * Remember that changing these before putting them in the constructor will not change anything as it wont be implemented.
     */
    public void setBr(boolean br) {
        this.br = br;
    }

    public void setBl(boolean bl) {
        this.bl = bl;
    }

    public void setFr(boolean fr) {
        this.fr = fr;
    }

    public void setFl(boolean fl) {
        this.fl = fl;
    }
}
