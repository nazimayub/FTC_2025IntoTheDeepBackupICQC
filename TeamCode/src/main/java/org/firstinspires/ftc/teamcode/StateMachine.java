package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class StateMachine {

    public MODE targetMode = MODE.STOW;
    public MODE currMode;
    public ClimbSubsystem climb;

    public void setMode(MODE m) { targetMode = m; }

    public MODE getargetMode() { return targetMode; }

    public void init(HardwareMap hardwareMap) {
        climb = new ClimbSubsystem(hardwareMap);
    }

    public StateMachine() {
    }

    public void execute() {
        if (targetMode == MODE.STOW) doStow();
        if (targetMode == MODE.INTAKE) doIntake();
        if (targetMode == MODE.LOWBUCKET) doLowBucket();
        if (targetMode == MODE.HIGHBUCKET) doHighBucket();
        if (targetMode == MODE.FIRSTLVL) doOneLvl();
        if (targetMode == MODE.SECONDLVL) doTwoLvl();
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
    public void doHighBucket() {
        climb.setHeight(150);
    }
    public void doOneLvl() {
        climb.setHeight(125);
    }
    public void doTwoLvl() {
        climb.setHeight(175);
    }
}
