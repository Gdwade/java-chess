

import javax.imageio.ImageIO;
import javax.lang.model.element.Name;
import javax.lang.model.util.ElementScanner14;
import javax.net.ssl.TrustManager;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;
import java.util.HashMap;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;

public class Chess extends JPanel implements Runnable{
    final int origionalTileSize = 16; //16x16 tile
    final int scale = 5;
    public final int tileSize = origionalTileSize * scale; //80x80 tile
    final int screenWidth = tileSize * 10; 
    final int screenHeight = tileSize * 10;
    public Piece[][] boardState = new Piece[8][8];
    public Piece curPiece;
    MouseHandler mouse = new MouseHandler();
    BufferedImage boardImage;
    Thread gameThread;
    final int FPS = 60;
    boolean showFPS = false;

    public Chess(){
        setDefaultBoardState();
        this.setPreferredSize(new DimensionUIResource(screenWidth, screenHeight));
        this. setBackground(Color.BLUE);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.setFocusable(true);     
        gameThread = new Thread(this);
        gameThread.start();
   }
   //uses startX and startY if useStartChords is true
   public Piece getPiece(int x, int y, boolean useStartChords){
    for (Piece[] pA : boardState){
            for (Piece p: pA ){
                if (p != null){
                    if (useStartChords){
                        if (p.startX == x && p.startY == y){
                            //System.out.println(p.id);
                            return p;
                       }
                    }else{
                        if (p.x == x && p.x == y){
                            //System.out.println(p.id);
                            return p;
                       }
                    }    
                }
                continue;
            }
    }
    return null;
   }  

   public void setDefaultBoardState(){
    // 1:pawn 2:knight 3:Bishop 4:Rook 5:Queen 6:King
    Boolean white; // if true, is white
    for (int i = 0; i<8; i++){
        if (i<2){
            white = true;
        }else{
            white = false;
        }
        if (i == 0 || i== 7){
            boardState[i][0] = new Piece(white, 1, i+1, 4, "rl" + white, 1, i+1, false);
            boardState[i][1] = new Piece(white, 2, i+1, 2, "nl" + white, 2, i+1, false);
            boardState[i][2] = new Piece(white, 3, i+1, 3, "bl" + white, 3, i+1, false);
            boardState[i][3] = new Piece(white, 4, i+1, 5, "q" + white, 4, i+1, false);
            boardState[i][4] = new Piece(white, 5, i+1, 6, "k" + white, 5, i+1, false);
            boardState[i][5] = new Piece(white, 6, i+1, 3, "br" + white, 6, i+1, false);
            boardState[i][6] = new Piece(white, 7, i+1, 2, "nr" + white, 7, i+1, false);
            boardState[i][7] = new Piece(white, 8, i+1, 4, "rr" + white, 8, i+1, false);
        }else{
           if (i== 1 || i==6){
            for (int j = 0; j < 8; j++){
                boardState[i][j] = new Piece(white, j+1, i+1, 1, "p" + i + "" + j, j+1, i+1, false);
            }
           }
        }
    }
    System.out.println("IS THERE A PEICE AT 7,2" + getPiece(7, 1, false));
   }
    
   public void printBoard(Piece[][] array){
    for (int a = 0; a<8; a++){
        for (int b=0; b<8; b++){
            if (array[a][b] != null){
                System.out.print(array[a][b].isWhite+"-"+array[a][b].type + " (" + array[a][b].x + ","+ array[a][b].y + ")");
            }
        }
        System.out.println("");
    }
   }

    public void drawBoardState(Graphics2D g2){
        // i gets colum
        BufferedImage image;
        String fileName = "";
        for (int i = 0; i<8; i++){
            for (int i2=0; i2<8; i2++){
                if (boardState[i][i2] != null){
                    Piece p = boardState[i][i2];
                    switch (p.type) {
                        case 1:
                            if (p.isWhite){
                                fileName = "/Chess_Assets/whitePawn.png";
                            }else{
                                fileName = "/Chess_Assets/blackPawn.png";
                            }
                            break;
                        case 2:
                            if (p.isWhite){
                                fileName = "/Chess_Assets/whiteKnight.png";
                            }else{
                                fileName = "/Chess_Assets/blackKnight.png";
                            }
                            break;
                        case 3:
                            if (p.isWhite){
                                fileName = "/Chess_Assets/whiteBishop.png";
                            }else{
                                fileName = "/Chess_Assets/blackBishop.png";
                            }
                            break;
                        case 4:
                            if (p.isWhite){
                                fileName = "/Chess_Assets/whiteRook.png";
                            }else{
                                fileName = "/Chess_Assets/blackRook.png";
                            }
                            break;
                        case 5:
                            if (p.isWhite){
                                fileName = "/Chess_Assets/whiteQueen.png";
                            }else{
                                fileName = "/Chess_Assets/blackQueen.png";
                            }
                            break;
                        case 6:
                            if (p.isWhite){
                                fileName = "/Chess_Assets/whiteKing.png";
                            }else{
                                fileName = "/Chess_Assets/blackKing.png";
                            }
                            break;
                            
                        default:
                            break;
                    }
                    try {
                        image = ImageIO.read(getClass().getResourceAsStream(fileName));
                        g2.drawImage(image, (p.x)*tileSize, 800 - (p.y + 1)*tileSize, tileSize, tileSize, null);
                    } catch (Exception e) {
                        System.out.println("COULDNT FIND FILE: " + fileName);
                    }
                }
            }
        }
   }

   public void update(){

        if (mouse.isPressed){
            if (!mouse.hasPiece){
                curPiece = getPiece(mouse.startX, mouse.startY, true);
                //System.out.println("ID: " + curPiece.id);
                mouse.hasPiece = true;
            }else{
                //System.out.println("didnt get peice");
                if (curPiece != null){
                    if (mouse.x > 0 && mouse.x < 9){
                        curPiece.x = mouse.x;
                    }
                    if (mouse.y > 0 && mouse.y < 9){
                        curPiece.y = mouse.y;
                    }             
                }
            }
        }
        
        if (mouse.checkLegal){
            if (curPiece != null){
                //System.out.println(isLegalMove(curPiece));
                if (isLegalMove(curPiece)){
                    curPiece.hasMoved = true;
                    curPiece.startX = curPiece.x;
                    curPiece.startY = curPiece.y;
                }else{
                    curPiece.x = curPiece.startX;
                    curPiece.y = curPiece.startY;
                }       
            }
            mouse.checkLegal = false;
        }
    }

    //facilitates captures and ensures that the king isnt captured.
    public boolean capture(int x, int y){
        Piece p = getPiece(x, y, true);
        if (p.type != 6){
            //if its not a king...
            p.capture();
            return true;
        }else{
            return false;
        }
    }

    //bottom left corner of the board is 1,1 top left is 1,8
    public boolean checkPathDiagonal(Piece p){
        //Returns true if the path is clear
        //Peice wont place if the cursor is brought out of bounds
        int difX = p.startX - p.x;
        int difY = p.startY - p.y;
        if (p.isWhite){
            for (int i2 = 1; i2 < Math.abs(difX); i2++){
                if (difX > 0){
                    //the differential is greater than 0 so the peice moved left
                    if (difY < 0){
                        //The peice went up (py > p.startY)
                        if (getPiece(p.startX-i2, p.startY + i2, true) != null){
                            return false;
                        }
                    }else{
                        // difY > 0 --> (py < p.startY) so the peice went down
                        if (getPiece(p.startX-i2, p.startY - i2, true) != null){
                            return false;
                        }
                    }
                }else{
                    //piece moved right
                    if (difY < 0){
                        //The peice went up (py > p.startY)
                        if (getPiece(p.startX+i2, p.startY + i2, true) != null){
                            return false;
                        }
                    }else{
                        // difY > 0 --> (py < p.startY) so the peice went down
                        if (getPiece(p.startX+i2, p.startY - i2, true) != null){
                            return false;
                        }
                    }
                }
            }
            return true;
        }else{
            //Piece is black!
            //down and right keeps going through pieces.
            for (int i3 = 1; i3 < Math.abs(difX); i3++){
                if (difX > 0){
                    //the differential is greater than 0 so the peice moved left
                    if (difY < 0){
                        //The peice went up (py > p.startY)
                        if (getPiece(p.startX-i3, p.startY + i3, true) != null){
                            return false;
                        }
                    }else{
                        // difY > 0 --> (py < p.startY) so the peice went down
                        if (getPiece(p.startX-i3, p.startY - i3, true) != null){
                            return false;
                        }
                    }
                }else{
                    //piece moved right
                    if (difY < 0){
                        //The peice went up (py > p.startY)
                        if (getPiece(p.startX+i3, p.startY + i3, true) != null){
                            return false;
                        }
                    }else{
                        // difY > 0 --> (py < p.startY) so the peice went down
                        if (getPiece(p.startX+i3, p.startY - i3, true) != null){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

    public boolean checkPathStraight(Piece p){
        //up and down
        if (p.startX == p.x){
            if (p.startY > p.y){
                for (int i = p.startY - 1; i>p.y; i--){
                    if (getPiece(p.x, i, true) != null){
                        System.out.println("vertical conflict");
                        return false;
                    }
                }
            }else{
                for (int i = p.startY + 1; i<p.y; i++){
                    if (getPiece(p.x, i, true) != null){
                        System.out.println("vertical conflict");
                        return false;
                    }
                }
            }
            return true;
        }else if (p.startY == p.y){
            if (p.startX > p.x){
                for (int i = p.startX - 1; i>p.x; i--){
                    if (getPiece(i, p.y, true) != null){
                        System.out.println("Horizontal conflict");
                        return false;
                    }
                }
            }else{
                for (int i = p.startX + 1; i<p.x; i++){
                    if (getPiece(i, p.y, true) != null){
                        System.out.println("Horizontal conflict");
                        return false;
                    }
                }
            }
            return true;
        }else{
            return false;
        }
        
        
    }

    //TODO: Turn based gameplay.
    //TODO: impliment check
    //TODO: Impliment checkmate detection!
    public boolean isLegalMove(Piece p){
        int whiteOffset;
        boolean isCapturing = false;

        if (p.id.contains("true") || (p.id.charAt(0) == 'p' && (Character.getNumericValue(p.id.charAt(1))) == 1)){
            whiteOffset = 1;
        }else{
            whiteOffset = -1;
        }

        //checks for collisions
        if (getPiece(p.x, p.y, true) != null){
            if (getPiece(p.x, p.y, true).isWhite == p.isWhite){
                return false;
            }else{
                isCapturing = true;
            }
        }
        System.out.println("capturing: " + isCapturing);

        //TODO: acount for captures
        switch (p.type) {
            case 1:
                //pawn
                //TODO: Acount For an passant
                char char1 = p.id.charAt(1);
                char char2 = p.id.charAt(2);
                int row = Character.getNumericValue(char1) + 1;
                int colum = Character.getNumericValue(char2) + 1;
                if (isCapturing == true && (p.startY + whiteOffset == mouse.y && (p.startX + 1 == mouse.x || p.startX - 1 == mouse.x))){
                    return capture(p.x, p.y);
                }
                if (!p.hasMoved){
                    //HASNT MOVED
                    
                    if (!checkPathStraight(p)){
                        //inversed because it isnt wrapping the rest of th func.
                        return false;
                    }
                    if (whiteOffset == 1){
                        //Pawn is white
                        if (mouse.y - row <= 2 && mouse.y - row > 0 && mouse.x == colum){
                            return !isCapturing;
                        }
                    }else{
                        if (row - mouse.y <= 2 && row - mouse.y > 0 && mouse.x == colum){
                            return !isCapturing;
                        }
                    }
                    return false;
                }else{
                    if (p.startY + whiteOffset == mouse.y && p.startX == mouse.x){
                        return !isCapturing;
                    }else{
                        return false;
                    }
                }
            
            case 2:
                //knight
                if ((Math.abs(p.startX - mouse.x) == 2 && Math.abs(p.startY - mouse.y) == 1) || (Math.abs(p.startX - mouse.x) == 1 && Math.abs(p.startY - mouse.y) == 2)){
                    //System.out.println(p.x + "," + p.y);
                    if (isCapturing){
                        return capture(p.x, p.y);
                    }
                    return true;
                }
                
                break;

            case 3:
                //Bishop
                if (checkPathDiagonal(p)){
                    if (Math.abs(p.startY - mouse.y) ==  Math.abs(p.startX - mouse.x)){
                        if (isCapturing){
                            return capture(p.x, p.y);
                         }
                        return true;
                    }
                }
                
                break;
            case 4:
                //rook
                if (checkPathStraight(p)){
                    if (p.startX == mouse.x || p.startY == mouse.y){
                        if (isCapturing){
                            return capture(p.x, p.y);
                         }
                        return true;
                    }
                }
                break;
            case 5:
                //queen
                if (p.startX == p.x || p.startY == p.y){
                    if (checkPathStraight(p)){
                        if (isCapturing){
                            return capture(p.x, p.y);
                         }
                        return true;
                    }
                }else{
                    if (checkPathDiagonal(p)){
                        if (Math.abs(p.startY - mouse.y) ==  Math.abs(p.startX - mouse.x)){
                            if (isCapturing){
                                return capture(p.x, p.y);
                             }
                            return true;
                        }
                    }
                }
                break;
            case 6:
            //TODO: add castling
                //king
                if (Math.abs(p.startX - mouse.x) <= 1 && Math.abs(p.startY - mouse.y) <= 1){
                    if (isCapturing){
                        return capture(p.x, p.y);
                     }
                    return true;
                }
                break;
            default:
                break;
        }
        
        return false;
    }

    public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    try {
        boardImage = ImageIO.read(getClass().getResourceAsStream("/Chess_Assets/chessBoard.png"));
        g2.drawImage(boardImage, 0,0, 800,800, null);
        //System.out.println("boardDrawn");
        //printBoard(boardState);
        drawBoardState(g2);
    } catch (Exception e) {
        System.out.println("Couldnt get board!");
    }
   }

@Override
public void run() {
    double drawInterval = 1000000000/FPS; //0.0166666 sec
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;

    while (gameThread != null){
       
        currentTime = System.nanoTime();
        
        delta += (currentTime - lastTime)/drawInterval;
        timer += (currentTime - lastTime);
        lastTime = currentTime;

        if (delta >= 1){
        // 1: update information
        update();
        // 2: draw
        repaint();

        delta--;
        drawCount++;
        }
        if (showFPS){
            if (timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
}

}
