import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class MouseHandler implements MouseInputListener{


    public int startX;
    public int startY;
    public int x;
    public int y;
    public boolean isPressed = false;
    public boolean hasPiece = false;
    public boolean checkLegal = false;

    public int[] convertToTile(int x, int y){
        int[] out = new int[2];
        /*
         * 800*800 window
         * 720*720 game board
         * top left corner = (80,80)
         * top right corner = (720,80)
         * 
         * bottom left corner = (80,720)
         * bootom right corner = (720,720)
         * 
         * Binary type search using each tile? 
         * when it gets 2, Choose the one its closest to.
         * 
         * divide x and y by 80, reverse the num so 1,1 is bottom left.
         * 
         */
        x = (x/80);
        y = 9-(y/80);
        out[0] = x;
        out[1] = y;
        return out;
       }

    @Override
    public void mousePressed(MouseEvent e) {
        int[] temp = convertToTile(e.getX(), e.getY());
        isPressed = true;
        startX = temp[0];
        startY = temp[1];
        x = temp[0];
        y = temp[1];
        
       //System.out.println("STARTED: " + startX + "," + startY );
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
        hasPiece = false;
        checkLegal = true;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int[] temp = convertToTile(e.getX(), e.getY());
        if (x == 0 || y == 0){
            x = startX;
            y = startY;
        }
       x = temp[0];
       y = temp[1]; 
       //System.out.println(x + "," + y);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int[] temp = convertToTile(e.getX(), e.getY());
        System.out.println(temp[0] + "," + temp[1]);
    }
}
