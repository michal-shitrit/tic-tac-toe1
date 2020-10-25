import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class gameTest {

    static final int BOARD_SIZE = 3;
    static Game game = new Game();

    @BeforeEach
    public void setBoard(){
        game.initGame(BOARD_SIZE, "p1", "p2", "X");
    }

    @Test
    public void testWinRow(){

        Player player1 = game.getPlayerX();
        Player player2 = game.getPlayerO();

        game.playUserMove(1, player1);
        game.playUserMove(2, player1);
        game.playUserMove(3, player1);

        assert(game.isPlayerWon(player1));
    }

    @Test
    public void testWinCol(){

        Player player1 = game.getPlayerX();
        Player player2 = game.getPlayerO();

        game.playUserMove(2, player1);
        game.playUserMove(5, player1);
        game.playUserMove(8, player1);

        assert(game.isPlayerWon(player1));
    }

    @Test
    public void testWinDiagonal1(){

        Player player1 = game.getPlayerX();
        Player player2 = game.getPlayerO();

        game.playUserMove(1, player1);
        game.playUserMove(5, player1);
        game.playUserMove(9, player1);

        assert(game.isPlayerWon(player1));
    }

    @Test
    public void testWinDiagonal2(){

        Player player1 = game.getPlayerX();
        Player player2 = game.getPlayerO();

        game.playUserMove(3, player1);
        game.playUserMove(5, player1);
        game.playUserMove(7, player1);

        assert(game.isPlayerWon(player1));
    }

    @Test
    public void testWinDiagonalFalse(){

        Player player1 = game.getPlayerX();
        Player player2 = game.getPlayerO();

        game.playUserMove(1, player1);
        game.playUserMove(5, player1);
        game.playUserMove(9, player2);

        Assertions.assertFalse(game.isPlayerWon(player1));
    }

    @Test
    public void testAI(){

        Player player1 = game.getPlayerX();
        Player player2 = game.getPlayerO();

        game.playUserMove(1, player1);
        game.playUserMove(8, player1);
        game.playUserMove(9, player1);
        game.playUserMove(2, player2);
        game.playUserMove(3, player2);
        game.playUserMove(5, player2);


        game.playComputerMove(player1,player2);
        Assertions.assertTrue(game.isPlayerWon(player1));
    }
}
