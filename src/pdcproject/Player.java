/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcproject;

/**
 *
 * @author Gerard Gomez 
 *         Victor Feng
 */
public class Player {

    //  Store name and winnings
    public String playerName = "";
    private int winnings = 0;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getWinnings() {
        return winnings;
    }

    public void Player(String playerName, int winnings) {
        this.playerName = playerName;
        this.winnings = winnings;
    }
}
