import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static final int BOARD_SIZE = 3;
    static final String COMPUTER_NAME = "Michal (AI)";
    static Game game = new Game();

    public static void main(String args[]){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //System.out.println("Enter player name: ");
        String playerName = "";
       // try {
            playerName = "dd";
            //playerName = reader.readLine();
     /*   } catch (IOException e) {
            e.printStackTrace();
        }*/

        System.out.println("Who first (X/O)?");
        String firstSign = "";

        try {
            firstSign = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        game.initGame(BOARD_SIZE,playerName, COMPUTER_NAME, firstSign);
        Player user = game.getPlayerX();
        Player computer = game.getPlayerO();
        boolean win = false;

        while (!game.isGameOver() && !win){
            if (game.currentTurn.equals(SIGN.X)){
                System.out.println("Current board:");
                game.printCurrentBoard();

                System.out.println("board index, please choose your next step:");
                game.printIndexBoard();
                try {
                    String nextStep = reader.readLine();
                    game.playUserMove(Integer.valueOf(nextStep).intValue(), user);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (game.isPlayerWon(user)) {
                    win = true;
                    System.out.println("User won!!! ahhh noooo");
                    game.printCurrentBoard();
                }

                game.currentTurn = SIGN.O;
            }else {
                game.playComputerMove(computer, user);

                if (game.isPlayerWon(computer)) {
                    win = true;
                    System.out.println("Computer Won!!! Michal is the best!!");
                    game.printCurrentBoard();
                }

                game.currentTurn = SIGN.X;
            }
        }

        if (!win){
            System.out.println("Game ended by tie");
            game.printCurrentBoard();
        }
    }
}
