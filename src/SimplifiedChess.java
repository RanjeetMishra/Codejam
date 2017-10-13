import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class SimplifiedChess {

    //We are adding all details in Move so that in backtracking algorithm we can simply call applyMove and undoMove and let
    //these functions worry about the task

    //These two pieces reference will never change
    static Piece whiteQueen;
    static Piece blackQueen;

    static Piece[][] whitePlayerBoard;
    static Piece[][] blackPlayerBoard;


    static int[] knightRowMoves = {1,2,2,1,-1,-2,-2,-1};
    static int[] knightColMoves = {-2,-1,1,2, 2, 1,-1,-2};

    static class Move {

        boolean isWhiteMove;
        char oldType;
        char newType;
        int initialRow;
        int initialColumn;
        int finalRow;
        int finalColumn;

        public Move() {

        }

        public Move getCopy() {
            Move copy = new Move();
            copy.initialRow = initialRow;
            copy.initialColumn = initialColumn;
            copy.finalRow = finalRow;
            copy.finalColumn = finalColumn;
            copy.isWhiteMove = isWhiteMove;
            copy.oldType = oldType;
            copy.newType = newType;
            return copy;
        }

    }

    static class Piece {

        char type;
        boolean isRemoved;
        public Piece(char type) {
            this.type = type;
            this.isRemoved = false;
        }
    }

    private static boolean isWhitePiecePresent(int row, int column) {
        return whitePlayerBoard[row][column]!=null && (whitePlayerBoard[row][column].isRemoved==false);
    }

    private static boolean isBlackPiecePresent(int row, int column) {
        return blackPlayerBoard[row][column]!=null && (blackPlayerBoard[row][column].isRemoved==false);
    }

    private static boolean isValidMove(int row, int column, boolean isWhiteMove) {
        if(!(row>=0 && row <=3 && column>=0 && column<=3))
            return false;
        if(isWhiteMove && isWhitePiecePresent(row,column))
            return false;
        if(!isWhiteMove && isBlackPiecePresent(row,column))
            return false;
        return true;
    }

    private static List<Move> getPawnMovesForNextMove(int initialRow, int initialColumn, int finalRow, int finalColumn, boolean isWhitePlayer) {

        List<Move> allMoves= new ArrayList<Move>();

        Move newMove = new Move();
        newMove.oldType = 'P';
        newMove.initialRow = initialRow;
        newMove.initialColumn = initialColumn;
        newMove.isWhiteMove = isWhitePlayer;
        newMove.finalRow = finalRow;
        newMove.finalColumn = finalColumn;

        if((isWhitePlayer && finalRow==3) || (!isWhitePlayer && finalRow==0)) {
            Move BMove = newMove.getCopy();
            BMove.newType = 'B';
            allMoves.add(BMove);

            Move NMove = newMove.getCopy();
            NMove.newType = 'N';
            allMoves.add(NMove);

            Move RMove = newMove.getCopy();
            RMove.newType = 'R';
            allMoves.add(RMove);

        }else {
            newMove.newType = 'P';
            allMoves.add(newMove);
        }

        return allMoves;
    }

    private static List<Move> getPawnMoves(int row, int column, boolean isWhitePlayer) {

        List<Move> allMoves= new ArrayList<Move>();

        if(isWhitePlayer) {
            //White Player pawn will only move up till it converts at the end
            if(isValidMove(row+1,column,isWhitePlayer) && !isBlackPiecePresent(row+1,column)) {
              allMoves.addAll(getPawnMovesForNextMove(row,column,row+1,column,isWhitePlayer));
            }

            //White Player pawn can do diagonal movement
            if(isValidMove(row+1,column-1, isWhitePlayer) && isBlackPiecePresent(row+1,column-1)) {
                allMoves.addAll(getPawnMovesForNextMove(row, column, row+1, column-1, isWhitePlayer));
            }

            if(isValidMove(row+1,column+1, isWhitePlayer)  && isBlackPiecePresent(row+1,column+1)) {
                allMoves.addAll(getPawnMovesForNextMove(row, column, row+1, column+1, isWhitePlayer));
            }

        }else {
            //Black Player pawn will only move down till it converts at the end
            if(isValidMove(row-1,column,isWhitePlayer) && !isWhitePiecePresent(row-1,column)) {
                allMoves.addAll(getPawnMovesForNextMove(row,column,row+1,column,isWhitePlayer));
            }

            //Black Player pawn can do diagonal movement
            if(isValidMove(row-1,column-1,isWhitePlayer) && isWhitePiecePresent(row-1,column-1)) {
                allMoves.addAll(getPawnMovesForNextMove(row, column, row-1, column-1, isWhitePlayer));
            }

            if(isValidMove(row-1,column+1,isWhitePlayer) && isWhitePiecePresent(row-1, column+1)) {
                allMoves.addAll(getPawnMovesForNextMove(row, column, row-1, column+1, isWhitePlayer));
            }
        }

        return allMoves;
    }

    private static List<Move> getNightMoves(int row, int column, boolean isWhitePlayer) {

        List<Move> allMoves = new ArrayList<Move>();
        Move newMove = new Move();
        newMove.oldType = 'N';
        newMove.newType = 'N';
        newMove.initialRow = row;
        newMove.initialColumn = column;
        newMove.isWhiteMove = isWhitePlayer;

        for(int i=0;i<8;i++) {
            if(isValidMove(row+knightRowMoves[i],column+knightColMoves[i],isWhitePlayer)) {

                Move copy = newMove.getCopy();
                newMove.finalRow = row+knightRowMoves[i];
                newMove.finalColumn = column+knightColMoves[i];
                allMoves.add(copy);
            }
        }
        return allMoves;
    }

    private static List<Move> getBishopMoves(int row, int column, boolean isWhitePlayer) {

        //Diagonal moves till a blocker
        List<Move> allMoves = new ArrayList<Move>();
        Move newMove = new Move();
        newMove.oldType = 'B';
        newMove.newType = 'B';
        newMove.initialRow = row;
        newMove.initialColumn = column;
        newMove.isWhiteMove = isWhitePlayer;

        //Diagonal left bottom
        for(int i=1;;i++) {
            if(!isValidMove(row-i,column-i,isWhitePlayer))
                break;
            Move copy = newMove.getCopy();
            copy.finalRow = row-i;
            copy.finalColumn = column-i;
            allMoves.add(copy);
            if((isWhitePlayer && isBlackPiecePresent(copy.finalRow,copy.finalColumn))
                    || (!isWhitePlayer && isWhitePiecePresent(copy.finalRow,copy.finalColumn)))
                break;
        }

        //Diagonal left top
        for(int i=1;;i++) {
            if(!isValidMove(row+i,column-i,isWhitePlayer))
                break;
            Move copy = newMove.getCopy();
            copy.finalRow = row+i;
            copy.finalColumn = column-i;
            allMoves.add(copy);
            if((isWhitePlayer && isBlackPiecePresent(copy.finalRow,copy.finalColumn))
                    || (!isWhitePlayer && isWhitePiecePresent(copy.finalRow,copy.finalColumn)))
                break;
        }

        //Diagonal right bottom
        for(int i=1;;i++) {
            if(!isValidMove(row-i,column+i,isWhitePlayer))
                break;
            Move copy = newMove.getCopy();
            copy.finalRow = row-i;
            copy.finalColumn = column+i;
            allMoves.add(copy);
            if((isWhitePlayer && isBlackPiecePresent(copy.finalRow,copy.finalColumn))
                    || (!isWhitePlayer && isWhitePiecePresent(copy.finalRow,copy.finalColumn)))
                break;
        }

        //Diagonal right top
        for(int i=1;;i++) {
            if(!isValidMove(row+i,column+i,isWhitePlayer))
                break;
            Move copy = newMove.getCopy();
            copy.finalRow = row+i;
            copy.finalColumn = column+i;
            allMoves.add(copy);
            if((isWhitePlayer && isBlackPiecePresent(copy.finalRow,copy.finalColumn))
                    || (!isWhitePlayer && isWhitePiecePresent(copy.finalRow,copy.finalColumn)))
                break;
        }

        return allMoves;
    }

    //Done
    private static List<Move> getRookMoves(int row, int column, boolean isWhitePlayer) {

        List<Move> allMoves = new ArrayList<Move>();

        Move newMove = new Move();
        newMove.oldType = 'R';
        newMove.newType = 'R';
        newMove.initialRow = row;
        newMove.initialColumn = column;
        newMove.isWhiteMove = isWhitePlayer;

        //UpMovement
        for(int i=row+1;i<4;i++) {

            if(!isValidMove(i,column,isWhitePlayer))
                break;
            Move copy = newMove.getCopy();
            copy.finalRow = i;
            copy.finalColumn = column;
            allMoves.add(copy);
            if((isWhitePlayer && isBlackPiecePresent(copy.finalRow,copy.finalColumn))
                    || (!isWhitePlayer && isWhitePiecePresent(copy.finalRow,copy.finalColumn)))
                break;
        }

        //Down Movement
        for(int i=row-1;i>=0;i--) {
            if(!isValidMove(i,column,isWhitePlayer))
                break;
            Move copy = newMove.getCopy();
            copy.finalRow = i;
            copy.finalColumn = column;
            allMoves.add(copy);
            if((isWhitePlayer && isBlackPiecePresent(copy.finalRow,copy.finalColumn))
                    || (!isWhitePlayer && isWhitePiecePresent(copy.finalRow,copy.finalColumn)))
                break;
        }

        //Left Movement
        for(int j=column-1;j>=0;j++) {
            if(!isValidMove(row,j,isWhitePlayer))
                break;
            Move copy = newMove.getCopy();
            copy.finalRow = row;
            copy.finalColumn = j;
            allMoves.add(copy);
            if((isWhitePlayer && isBlackPiecePresent(copy.finalRow,copy.finalColumn))
                    || (!isWhitePlayer && isWhitePiecePresent(copy.finalRow,copy.finalColumn)))
                break;
        }

        //Right movement
        for(int j=column+1;j<4;j++) {
            if(!isValidMove(row,j,isWhitePlayer))
                break;
            Move copy = newMove.getCopy();
            copy.finalRow = row;
            copy.finalColumn = j;
            allMoves.add(copy);
            if((isWhitePlayer && isBlackPiecePresent(copy.finalRow,copy.finalColumn))
                    || (!isWhitePlayer && isWhitePiecePresent(copy.finalRow,copy.finalColumn)))
                break;
        }

        return allMoves;
    }

    private static List<Move> getQueenMoves(int row, int column, boolean isWhitePlayer) {


        //Queen movements are combination of RookMoves and BishopMoves. We will only have to change the type values once we get these values

        List<Move> allMoves = new ArrayList<Move>();

        List<Move> bishopMoves = getBishopMoves(row, column, isWhitePlayer);
        if(bishopMoves!=null)
            allMoves.addAll(bishopMoves);

        List<Move> rookMoves = getRookMoves(row, column, isWhitePlayer);
        if(rookMoves!=null)
            allMoves.addAll(rookMoves);

        for(Move move: allMoves) {
            move.oldType = 'Q';
            move.newType = 'Q';
        }

        return allMoves;

    }

    public static List<Move> getAllMoves(int row, int column, boolean isWhitePlayer) {

        Piece p = (isWhitePlayer)?whitePlayerBoard[row][column]:blackPlayerBoard[row][column];

        switch (p.type) {
            case 'Q':
                return getQueenMoves(row, column, isWhitePlayer);
            case 'B':
                return getBishopMoves(row, column, isWhitePlayer);
            case 'R':
                return getRookMoves(row, column, isWhitePlayer);
            case 'N':
                return getNightMoves(row, column, isWhitePlayer);
            case 'P':
                return getPawnMoves(row, column, isWhitePlayer);
            default:
                System.out.println("Piece type error: "+p.type);
        }

        return null;
    }

    private static void applyMove(Move move, int movesLeft) {
        count++;
        if(move.isWhiteMove) {

            System.out.println(" White "+move.finalRow+ " "+move.finalColumn+" set");
            Piece oldPiece = whitePlayerBoard[move.initialRow][move.initialColumn];
            whitePlayerBoard[move.initialRow][move.initialColumn] = null;
            oldPiece.type = move.newType;
            whitePlayerBoard[move.finalRow][move.finalColumn] = oldPiece;

            if(blackPlayerBoard[move.finalRow][move.finalColumn]!=null) {
                blackPlayerBoard[move.finalRow][move.finalColumn].isRemoved = true;
            }
        }else {
            System.out.println(" Black "+move.finalRow+ " "+move.finalColumn+" set");

            Piece oldPiece = blackPlayerBoard[move.initialRow][move.initialColumn];
            blackPlayerBoard[move.initialRow][move.initialColumn] = null;
            oldPiece.type = move.newType;
            blackPlayerBoard[move.finalRow][move.finalColumn] = oldPiece;

            if(whitePlayerBoard[move.finalRow][move.finalColumn]!=null) {
                whitePlayerBoard[move.finalRow][move.finalColumn].isRemoved = true;
            }
        }

    }

    private static void undoMove(Move move, int movesLeft) {
        count++;
        if(move.isWhiteMove) {
            System.out.println(" White "+move.finalRow+ " "+move.finalColumn+" unset");

            Piece newPiece = whitePlayerBoard[move.finalRow][move.finalColumn];
            whitePlayerBoard[move.finalRow][move.finalColumn] = null;
            if(newPiece==null) {
                System.out.println("Came here :(");
            }
            newPiece.type = move.oldType;
            whitePlayerBoard[move.initialRow][move.initialColumn] = newPiece;

            if(blackPlayerBoard[move.finalRow][move.finalColumn]!=null) {
                blackPlayerBoard[move.finalRow][move.finalColumn].isRemoved = false;
            }
        }else {
            System.out.println(" Black "+move.finalRow+ " "+move.finalColumn+" unset");

            Piece newPiece = blackPlayerBoard[move.finalRow][move.finalColumn];
            blackPlayerBoard[move.finalRow][move.finalColumn] = null;
            newPiece.type = move.oldType;
            blackPlayerBoard[move.initialRow][move.initialColumn] = newPiece;

            if(whitePlayerBoard[move.finalRow][move.finalColumn]!=null) {
                whitePlayerBoard[move.finalRow][move.finalColumn].isRemoved = false;
            }
        }

    }

    private static boolean isWhiteWinner() {
        if(blackQueen.isRemoved) {
            return true;
        }
        return false;
    }

    static int count = 0;

    private static boolean canWhiteWin(int movesLeft, boolean isWhitePlayer) {

        if(movesLeft<=0)
            return false;

        if(count >= 128)
            System.out.println(count);
        if(isWhitePlayer) {

            boolean canWin = false;
            for(int i=0;i<4;i++) {
                for(int j=0;j<4;j++) {
                    if(whitePlayerBoard[i][j]!=null && !whitePlayerBoard[i][j].isRemoved) {
                        List<Move> allMoves = getAllMoves(i,j,true);
                        for(Move move: allMoves) {
                            applyMove(move,movesLeft);
                            canWin = canWin || isWhiteWinner() || canWhiteWin(movesLeft-1, !isWhitePlayer);
                            undoMove(move,movesLeft);
                            if(canWin)
                                return canWin;
                        }

                    }
                }
            }

            return canWin;

        }else {

            boolean canWin = true;
            for(int i=0;i<4;i++) {
                for(int j=0;j<4;j++) {
                    if(blackPlayerBoard[i][j]!=null && !blackPlayerBoard[i][j].isRemoved) {
                        List<Move> allMoves = getAllMoves(i,j, false);
                        for(Move move: allMoves) {
                            applyMove(move,movesLeft);
                            canWin = canWhiteWin(movesLeft, true) && canWin;
                            undoMove(move,movesLeft);
                        }
                    }
                }
            }
            return canWin;
        }
    }

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int g = in.nextInt();
        for(int a0 = 0; a0 < g; a0++){
            int w = in.nextInt();
            int b = in.nextInt();
            int m = in.nextInt();

            whitePlayerBoard = new Piece[4][4];
            blackPlayerBoard = new Piece[4][4];

            for(int white_i=0; white_i < w; white_i++){
                //type column row
                char type = in.next().charAt(0);
                int column = (in.next().charAt(0)-'A');
                int row = in.nextInt()-1;

                Piece newPiece = new Piece(type);
                whitePlayerBoard[row][column] = newPiece;

                if(type=='Q')
                    whiteQueen = newPiece;
            }

            for(int black_i=0; black_i < b; black_i++){
                //type column row
                char type = in.next().charAt(0);
                int column = (in.next().charAt(0)-'A');
                int row = in.nextInt()-1;

                Piece newPiece = new Piece(type);
                blackPlayerBoard[row][column] = newPiece;

                if(type=='Q')
                    blackQueen = newPiece;
            }

            boolean canWhiteWin = canWhiteWin(m,true);
            String result = canWhiteWin?"YES":"NO";
            System.out.println(result);

        }
    }

}
