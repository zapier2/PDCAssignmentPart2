/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcproject;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Gerard Gomez 
 *         Victor Feng
 */
public class Scoreboard {
    private String nameAndScore;
    
    //  Takes in Player which have player name and winnings
    public void writeScores(Player p){
        BufferedWriter bw = null;
        
        try{
            nameAndScore = "Player: " + p.getPlayerName() + " Winnings: $" + p.getWinnings() + "\n";
          
            bw = new BufferedWriter(new FileWriter("scores.txt",true));
            bw.append(nameAndScore);
            bw.close();
            
        } catch(IOException ex){
            System.out.println("Error while saving scores.");
        }finally{
            System.out.println("Saving player: "+p.getPlayerName()+"'s Winnings: $" +p.getWinnings());
        }
    }
}
