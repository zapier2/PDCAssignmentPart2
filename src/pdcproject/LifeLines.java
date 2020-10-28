/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcproject;

import java.util.ArrayList;

/**
 *
 * @author Gerard Gomez 
 *         Victor Feng
 */
public class LifeLines {

    //  Boolean to check if hint is avaliable
    private boolean hintOn = false;
    private ArrayList<Question> questions;

    public LifeLines(ArrayList<Question> questions){
        this.questions = questions;
    }
    //  Return the hint
    public String getHintOn(int questionNumber) {
        return questions.get(questionNumber).getHint();
    }
    
    public void setStatus(boolean hintOn){
        this.hintOn = hintOn;
    }
    public boolean hintStatus() {
        return hintOn;
    }

    

}
