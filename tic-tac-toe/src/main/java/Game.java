import java.util.ArrayList;
import java.util.List;

public class Game {

    Player[][] board;
    static int[][] boardIndex;

    Player playerX = new Player();
    Player playerO = new Player();

    public SIGN currentTurn;
    static int boardSize = 3;

    public Player getPlayerX(){
        return playerX;
    }

    public Player getPlayerO(){
        return playerO;
    }

    private static boolean isGameOver(Player[][] board){

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null){
                    return false;
                }
            }
        }

        return true;
    };

    public boolean isGameOver(){
        return isGameOver(board);
    }

    public boolean isPlayerWon(Player player){

        return checkWinOnBoard(board, player);
    }

    private static boolean checkWinOnBoard(Player[][] tempBoard, Player player) {
        if (checkWinRow(tempBoard,player)){
            return true;
        }
        if (checkWinCol(tempBoard, player)){
            return true;
        }
        if (checkWinDiagonal(tempBoard, player)){
            return true;
        }if (checkWinRevDiagonal(tempBoard, player)){
            return true;
        }

        return false;
    }

    private static boolean isPlayerWon(Player[][] tempBoard, Player player){
        return checkWinOnBoard(tempBoard, player);
    }

    private static boolean checkWinRow(Player[][] tempBoard, Player player) {
        boolean won = false;
        for (int i = 0; i < tempBoard.length; i++) {

            boolean wonDirCheck = true;

            for (int j = 0; j < tempBoard[i].length; j++) {

                if (tempBoard[i][j] != player) {
                    wonDirCheck = false;
                }
            }
            if (wonDirCheck) {
                won = true;
            }
        }

        return won;
    }

    private static boolean checkWinCol(Player[][] tempBoard, Player player) {
        boolean won = false;
        for (int i = 0; i < tempBoard.length; i++) {

            boolean wonDirCheck = true;

            for (int j = 0; j < tempBoard[i].length; j++) {

                if (tempBoard[j][i] != player) {
                    wonDirCheck = false;
                }
            }
            if (wonDirCheck) {
                won = true;
            }
        }

        return won;
    }

    private static boolean checkWinDiagonal(Player[][] tempBoard, Player player) {
        boolean won = true;
        for (int i = 0; i < tempBoard.length; i++) {
            if (tempBoard[i][i] != player) {
                won = false;
            }
        }
        return won;
    }

    private static boolean checkWinRevDiagonal(Player[][] tempBoard, Player player) {
        boolean won = true;
        for (int i = 0; i < tempBoard.length; i++) {
            for (int j = 0; j < tempBoard[i].length; j++) {
                if ( (i + j) == (tempBoard.length-1)){
                    if (tempBoard[i][j] != player) {
                        won = false;
                    }
                }
            }
        }
        return won;
    }

    public void playComputerMove(Player player, Player other){
        int move = findNextBestMove(board, player, other);
        playMove(board,move,player);
    }

    private static List<Integer> getPossiableMoves(Player[][] board){
        List<Integer> emptySpots = new ArrayList<Integer>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null){
                    emptySpots.add(Game.boardIndex[i][j]);
                }
            }
        }

        return emptySpots;
    }

    public void playUserMove(int location, Player player){

        playMove(board, location, player);
    }

    private static void playMove(Player[][] tempBoard,  int location, Player player) {
        int row;
        int col;

        if (location <= boardSize){
            row = 0;
            col = location - 1;
        }else{
            row = (location + 1) / (boardSize + 1);

            if (location % boardSize == 0){
                col = boardSize - 1;
            }
            else {
                col = (location % boardSize) - 1;
            }
        }

        if ((player != null) && (tempBoard[row][col] != null)){
            throw new RuntimeException("This place is taken, please choose differnt location");
        }

        tempBoard[row][col] = player;
    }

    public void printCurrentBoard(){

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(getStringValue(board[i][j]));

                if (j < (board.length - 1)){
                    System.out.print('|');
                }
            }
            System.out.println();

            if (i < (board.length - 1)) {
                System.out.println("-----");
            }
        }
    }

    private String getStringValue(Player player) {
        if (player == null){
            return " ";
        }
        return player.getSign().toString();
    }

    public void initGame(int boardSize, String playerXName, String playerOName, String firstSign) {
        this.boardSize = boardSize;
        board = new Player[boardSize][boardSize];
        boardIndex = new int[boardSize][boardSize];

        playerX.setName(playerXName);
        playerX.setSign(SIGN.X);

        playerO.setName(playerOName);
        playerO.setSign(SIGN.O);

        markFirst(firstSign);
        setBoardIndex();
    }

    private void markFirst(String firstSign) {

        if (firstSign.toUpperCase().equals(SIGN.X.toString())){
            currentTurn = SIGN.X;
        }else{
            currentTurn = SIGN.O;
        }
    }

    private void setBoardIndex(){
        for (int i = 0; i < boardIndex.length; i++) {
            for (int j = 0; j < boardIndex[i].length; j++) {
                int col = j + 1;

                boardIndex[i][j] = (col + (boardIndex.length * i));
            }
        }
    }

    public void printIndexBoard() {
        for (int i = 0; i < boardIndex.length; i++){
            for (int j = 0; j < boardIndex[i].length; j++) {
                System.out.print(boardIndex[i][j]);

                if (j < (boardIndex.length - 1)){
                    System.out.print('|');
                }
            }
            System.out.println();

            if (i < (boardIndex.length - 1)) {
                System.out.println("-----");
            }
        }
    }


    private static int findNextBestMove(Player[][] board, Player computer, Player user){

        Integer bestScore = Integer.MIN_VALUE;
        int bestMove = 0;

        for (Integer move : getPossiableMoves(board)) {
            playMove(board, move, computer);
            int scoreMove = minimax(board,  computer, user, false);
            playMove(board,move, null);
            if (bestScore < scoreMove) {
                bestScore = scoreMove;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private static int minimax(Player[][] board, Player maxPlayer, Player minPlayer, boolean isMaxTurn){

        if (isPlayerWon(board,maxPlayer)){
            return 1;
        }
        if (isPlayerWon(board, minPlayer)){
            return -1;
        }
        if (isGameOver(board)){
            return 0;
        }


        int bestMove = 0;
        int score;

        if (isMaxTurn) {
            Integer bestScore = Integer.MIN_VALUE;
            for (Integer move : getPossiableMoves(board)) {
                playMove(board, move, maxPlayer);
                int scoreMove = minimax(board,  maxPlayer, minPlayer, false);
                playMove(board,move, null);
                if (bestScore < scoreMove) {
                    bestScore = scoreMove;
                    bestMove = move;
                }
            }
            score = bestScore;
        }else{
            Integer bestScore = Integer.MAX_VALUE;
            for (Integer move : getPossiableMoves(board)) {
                playMove(board, move, minPlayer);
                int scoreMove = minimax(board, maxPlayer, minPlayer, true);
                playMove(board,move, null);
                if (bestScore > scoreMove) {
                    bestScore = scoreMove;
                    bestMove = move;
                }
            }
            score = bestScore;
        }
        return score;
    }
}
