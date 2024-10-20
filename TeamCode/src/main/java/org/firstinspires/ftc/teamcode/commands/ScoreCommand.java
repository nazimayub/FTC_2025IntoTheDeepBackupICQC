package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ScoringSubsystem;
/**
 * Score Command, responsible for all scoring functionalities
 */
public class ScoreCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ScoringSubsystem score;
    int encoders=Integer.MAX_VALUE; // max default value SHOULD NEVER BE USED
    public ScoringSubsystem.WristStates wristState = null;
    public ScoringSubsystem.SprocketStates sprocketState = null;
    public ScoringSubsystem.ExtendStates extendState = null;
    public ScoringSubsystem.ClimbStates climbState = null;
    double intakeSpeed = Double.MAX_VALUE;
    double position = Double.MAX_VALUE;
    double target = Double.MAX_VALUE;
    /**
     * This Constructor sets the Sprockets Position; to an encoder specified value given in the parameter.
     */
    public ScoreCommand(ScoringSubsystem score, int encoders){
        this.score = score;
        this.encoders = encoders;
        addRequirements(score);
    }
    /**
     * This Constructor sets the Wrists Position to a WristState given in the parameter.
     */
    public ScoreCommand(ScoringSubsystem score, ScoringSubsystem.WristStates wristState){
        this.score = score;
        this.wristState = wristState;
        addRequirements(score);
    }
    /**
     * This Constructor sets the Intake Spin Speed to a Intake Speed given in the parameter.
     */
    public ScoreCommand(ScoringSubsystem score, double intakeSpeed){
        this.score = score;
        this.intakeSpeed = intakeSpeed;
        addRequirements(score);
    }
    /**
     * This Constructor sets the Sprockets position (sprocket or hooks) to a Sprocket State given in the parameter.
     */
    public ScoreCommand(ScoringSubsystem score, ScoringSubsystem.SprocketStates sprocketState){
        this.score = score;
        this.sprocketState = sprocketState;
        addRequirements(score);
    }
    /**
     * This Constructor sets the Climb (hooks or sprocket) position to a Climb State given in the parameter.
     */
    public ScoreCommand(ScoringSubsystem score, ScoringSubsystem.ClimbStates climbState){
        this.score = score;
        this.climbState = climbState;
        addRequirements(score);
    }
    /**
     * This Constructor sets the Extend position to a Extend State given in the parameter.
     */
    public ScoreCommand(ScoringSubsystem score, ScoringSubsystem.ExtendStates extendState){
        this.score = score;
        this.extendState = extendState;
        addRequirements(score);
    }
    /**
     * This Constructor sets the Extend position to <b>ANY</b> position given in the parameter.
     */
    public ScoreCommand(ScoringSubsystem score, double target,double position){
        this.score = score;
        this.target = target;
        this.position = position;
        addRequirements(score);
    }


    @Override
    public void execute(){
        if(encoders==Integer.MAX_VALUE){
            // we arent using encoders
            if(wristState!=null){
                // we need to set the wrist state
                if(wristState == ScoringSubsystem.WristStates.DROP){
                    score.drop();
                } else if (wristState == ScoringSubsystem.WristStates.INTAKE) {
                    score.intake();
                } else if (wristState == ScoringSubsystem.WristStates.SPECIMEN){
                    score.specimen();
                }
            } else if (intakeSpeed>=-1&&intakeSpeed<=1) { // if -1<=intakeSpeed<=1 (in the speed range...)
                // set intake speed
                score.intakeSpin(intakeSpeed);
            } else if (sprocketState!=null){
                if(sprocketState == ScoringSubsystem.SprocketStates.STOW){
                    score.stow();
                }else if(sprocketState == ScoringSubsystem.SprocketStates.SCORE){
                    score.scorePos();
                }else if(sprocketState == ScoringSubsystem.SprocketStates.INTAKE){
                    score.intakePos();
                }else if (sprocketState == ScoringSubsystem.SprocketStates.REACH){ // u could also do this with climbstates.reach
                    score.reach();
                }
            } else if(climbState != null){
                if(climbState == ScoringSubsystem.ClimbStates.REACH){
                    score.reach();
                } else if (climbState == ScoringSubsystem.ClimbStates.LOCK) {
                    score.lock();
                } else if(climbState == ScoringSubsystem.ClimbStates.OPEN){
                    score.open();
                }
            } else if (extendState !=null) {
                if (extendState == ScoringSubsystem.ExtendStates.FIRST){
                    score.first();
                }else if(extendState == ScoringSubsystem.ExtendStates.SECOND){
                    score.second();
                }else if(extendState == ScoringSubsystem.ExtendStates.THIRD){
                    score.third();
                } else if (extendState == ScoringSubsystem.ExtendStates.FOURTH) {
                    score.fourth();
                }else if (extendState == ScoringSubsystem.ExtendStates.FIFTH){
                    score.fifth();
                }else if (extendState == ScoringSubsystem.ExtendStates.HOME){
                    score.home();
                }
            }else if (target != Double.MAX_VALUE && position !=Double.MAX_VALUE){
                score.target(target,position);
            }

        }else{
            // sproc command
            score.sprocPos(encoders);
        }
    }
    @Override
    public boolean isFinished(){
        return score.isCompleted();
    }

}
