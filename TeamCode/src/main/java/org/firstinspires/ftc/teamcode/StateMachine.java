package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class StateMachine {

    public MODE mode = MODE.STOW;
    public ClimbSubsystem climb;

    public void setMode(MODE m) { mode = m; }

    public MODE getMode() { return mode; }

    public void init(HardwareMap hardwareMap) {
        climb = new ClimbSubsystem(hardwareMap);
    }

    public StateMachine() {

        if (mode == MODE.STOW) doStow();
        if (mode == MODE.INTAKE) doIntake();
        if (mode == MODE.LOWBUCKET) doLowBucket();
        if (mode == MODE.HIGHBUCKET) doHiBucket();
        if (mode == MODE.FIRSTLVL) doOneLvl();
        if (mode == MODE.SECONDLVL) doTwoLvl();

    }

    public void doStow() {
        climb.setHeight(0);
    }
    public void doIntake() {
        climb.setHeight(0);
    }
    public void doLowBucket() {
        climb.setHeight(100);
    }
    public void doHiBucket() {
        climb.setHeight(150);
    }
    public void doOneLvl() {
        climb.setHeight(125);
    }
    public void doTwoLvl() {
        climb.setHeight(175);
    }
}
