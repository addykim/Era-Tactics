package askim.eratactics.gamelogic;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Board {

    private Piece[][] pieces;

    public Board(Team team) {
        pieces = new Piece[6][3];
        for (int i = 0; i < 9; i++) {
            Adventurer temp = team.getAdventurer(i);
            if (temp != null) {
                Piece p;
                if (team.getLeader() == temp) {
                    p = new Piece(temp, true);
                }
                else {
                    p = new Piece(temp, false);
                }
            }
        }
    }

    private void generateEnemies() {

    }



}
