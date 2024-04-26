import java.awt.Color;
import java.awt.Graphics2D;

public class Mino {
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];

    public void create(Color c) {
        for (int i = 0; i < 4; i++) {
            b[i] = new Block(c);
            tempB[i] = new Block(c);
        }
    }
    public void setXY(int x, int y) {}
    public void updateXY(int direction) {}
    public void update() {
        
    }
    public void draw(Graphics2D g2) {

    }
}
