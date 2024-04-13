public class Piece {
    Boolean isWhite;
    int x; //grid co-odinate
    int y; //grid co-odinate
    int type; // 1:pawn 2:knight 3:Bishop 4:Rook 5:Queen 6:King
    String id;
    int startX;
    int startY;
    boolean hasMoved;
    

    public Piece(Boolean isWhite, int x, int y, int type, String id, int startX, int startY, boolean hasMoved){
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
        this.type = type;
        this.id = id;
        this.startX = startX;
        this.startY = startY;
        this.hasMoved = hasMoved;

    }

    public void capture(){
        x = 10;
        y = 10;
        startX = 10;
        startY = 10;
    }

    public String toString(){              
        return "Current X,Y: " + x + "," + y + "\nStart X,Y: "+ startX  + "," + startX + "\n" + "\nPiece Type: " + type + "\nID: " + id + "\n";
    }

}
