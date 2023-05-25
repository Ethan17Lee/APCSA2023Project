import pkg.*;

class Gomoku implements InputKeyControl 
{
    static Rectangle pieceInd;
    static int border = 50;
    static boolean whiteTurn = true;
    static int[][] board;
    static Text message;
    static Text alert;
    static boolean gameOver = false;
    static int size = 16;
    static boolean confirm = false;

    public static void main(String args[])
    {
        KeyController kc = new KeyController(Canvas.getInstance(), new starter());

        // board
        Rectangle underBoard = new Rectangle(border,border,30*size,30*size);
        underBoard.setColor(new Color(230,180,130));
        underBoard.fill();

        for(int i = 0; i < size; i++) 
        {
            for (int j = 0; j < size; j++) 
            {
                Rectangle rectBoard = new Rectangle(30*i + border,30 * j + border,30,30);
                rectBoard.draw();
            }

        }
        
        // make indicator
        pieceInd = new Rectangle(225 + border,225 + border,30,30);
        pieceInd.setColor(new Color(255,0,0));
        pieceInd.draw();

        // make board array
        board = new int[15][15];

        // default message
        message = new Text(border, 490 + border, "White's Turn");
        message.draw();

        // default alert
        alert = new Text(border, 510 + border, "");
        alert.draw();
        alert.setColor(new Color(255,0,0));

    }

    public void keyPress(String s) 
    {
        // moves rect
        if (s.equals("d")  &&  pieceInd.getX() < 420 + border) 
        {
            pieceInd.translate(30,0);
        }

        if (s.equals("s")  &&  pieceInd.getY() < 420 + border) 
        {
            pieceInd.translate(0,30);
        }

        if (s.equals("a")  &&  pieceInd.getX() > 30 + border) 
        {
            pieceInd.translate(-30,0);
        }

        if (s.equals("w")  &&  pieceInd.getY() > 30 + border)
        {
            pieceInd.translate(0,-30);
        }

        if (s.equals("n")) 
        {
            if (confirm) 
            {
                Rectangle underBoard = new Rectangle(border,border,30*size,30*size);
                underBoard.setColor(new Color(230,180,130));
                underBoard.fill();
                
                for(int i = 0; i < size; i++) 
                {
                    for (int j = 0; j < size; j++) 
                    {
                        Rectangle rectBoard = new Rectangle(30*i + border,30 * j + border,30,30);
                        rectBoard.draw();
                    }

                }

                for(int ii = 0; ii < size-1; ii++) 
                {
                    for (int jj = 0; jj< size-1; jj++) 
                    {
                        board[ii][jj] = 0;

                    }

                }

                whiteTurn = true;
                
                pieceInd = new Rectangle(225 + border,225 + border,30,30);
                pieceInd.setColor(new Color(255,0,0));
                pieceInd.draw();

                // default message
                message.setText("White's Turn");

                // default alert   
                alert.setText("");

                if (gameOver) 
                {
                    gameOver = false;         
                }
                confirm = false;

            }

            else 
            {
                message.setText("Press \"n\" again to reset the game.");
                confirm = true;  
            }

        }

        if (!s.equals("n")) 
        {
            confirm = false;
        }

        // place piece
        if (s.equals("p")  &&  !gameOver) 
        {
            if (board[(pieceInd.getY()-border-15)/30][(pieceInd.getX()-border-15)/30] == 0) 
            {                    
                if (whiteTurn) 
                {
                    Ellipse pieceWhite = new Ellipse(pieceInd.getX() + 2,pieceInd.getY() + 2,26,26);
                    pieceWhite.fill();
                    pieceWhite.setColor(new Color(255,255,255));
                    
                    Ellipse pieceWhiteOut = new Ellipse(pieceInd.getX() + 2,pieceInd.getY() + 2,26,26);
                    pieceWhiteOut.draw();
                    board[(pieceInd.getY()-border-15)/30][(pieceInd.getX()-border-15)/30] = 1;

                    // check win
                    if (checkWin(board) == 1) 
                    {
                        message.setText("WHITE WINS");
                        alert.setText("");
                        gameOver = true;

                    }
                    else 
                    {
                        whiteTurn = false;
                        message.setText("Black's Turn");
                        alert.setText("");
                    }

                }
                else 
                {
                    Ellipse pieceBlack = new Ellipse(pieceInd.getX() + 2,pieceInd.getY() + 2,26,26);
                    pieceBlack.fill();
                    board[(pieceInd.getY()-border-15)/30][(pieceInd.getX()-border-15)/30] = 2;

                    // check win
                    if (checkWin(board) == 2) 
                    {
                        message.setText("BLACK WINS");
                        alert.setText("");
                        gameOver = true;
                    }
                    else 
                    {
                        whiteTurn = true;
                        message.setText("White's Turn");
                        alert.setText("");
                    }

                }

                printBoard(board);
            }

            else
            {
                alert.setText("You can't place a piece there.");
            }
        }
    }

    // returns 0 if no win
    // returns 1 if white wins
    // returns 2 if black wins
    public static int checkWin(int[][] b)
    {
        for (int i = 0; i < b.length; i++)
        {
            for (int j = 0; j < b.length; j++)
            {         
                // check horizontal
                if (j < 11  &&  b[i][j] == 1  &&  b[i][j+1] == 1  &&  b[i][j+2] == 1  &&  b[i][j+3] == 1  &&  b[i][j+4] == 1)
                {
                    // white wins
                    return 1;
                }
                if (j < 11  &&  b[i][j] == 2  &&  b[i][j+1] == 2  &&  b[i][j+2] == 2  &&  b[i][j+3] == 2  &&  b[i][j+4] == 2)
                {
                    // black wins
                    return 2;
                }

                // check vertical
                if (i < 11  &&  b[i][j] == 1  &&  b[i+1][j] == 1  &&  b[i+2][j] == 1  &&  b[i+3][j] == 1  &&  b[i+4][j] == 1)
                {
                    //white wins
                    return 1;
                }
                if (i < 11  &&  b[i][j] == 2  &&  b[i+1][j] == 2  &&  b[i+2][j] == 2  &&  b[i+3][j] == 2  &&  b[i+4][j] == 2)
                {
                    // black wins
                    return 2;
                }

                //check diagonals
                if (i < 11  &&  j < 11  &&  b[i][j] == 1  &&  b[i+1][j+1] == 1  &&  b[i+2][j+2] == 1  &&  b[i+3][j+3] == 1  &&  b[i+4][j+4] == 1)
                {
                    // white wins
                    return 1;
                }
                if (i < 11  &&  j < 11  &&  b[i][j] == 2  &&  b[i+1][j+1] == 2  &&  b[i+2][j+2] == 2  &&  b[i+3][j+3] == 2  &&  b[i+4][j+4] == 2)
                {
                    // black wins
                    return 2;
                }
                if (b[i][j] == 1  &&  i > 0  &&  j < 14  &&  b[i-1][j+1] == 1  &&  i > 1  &&  j < 13  &&  b[i-2][j+2] == 1  &&  i > 2  &&  j < 12  &&  b[i-3][j+3] == 1  &&  i > 3  &&  j < 11  &&  b[i-4][j+4] == 1)
                {
                    // white wins
                    return 1;
                }
                if (b[i][j] == 2  &&  i > 0  &&  j < 14  &&  b[i-1][j+1] == 2  &&  i > 1  &&  j < 13  &&  b[i-2][j+2] == 2  &&  i > 2  &&  j < 12  &&  b[i-3][j+3] == 2  &&  i > 3  &&  j < 11  &&  b[i-4][j+4] == 2)
                {
                    // black wins
                    return 2;
                }
            }
        }
        
        return 0;
    }
    
    public static void printBoard(int[][] b)
    {
        for (int i = 0; i < b.length; i++)
        {
            for (int j = 0; j < b.length; j++)
            {
                System.out.print(b[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}