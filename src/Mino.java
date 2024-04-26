import java.awt.Color;
import java.awt.Graphics2D;

public class Mino {
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1;

    public void create(Color c) {
        for (int i = 0; i < 4; i++) {
            b[i] = new Block(c);
            tempB[i] = new Block(c);
        }
    }
    public void setXY(int x, int y) {}
    public void updateXY(int direction) {
        this.direction = direction;

        for (int i = 0; i < 4; i++) {
            b[i].x = tempB[i].x;
            b[i].y = tempB[i].y;
        }
    }
    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}
    public void update() {
        if (KeyHandler.upPressed) {
            switch (direction) {
                case 1: getDirection2(); break;
                case 2: getDirection3(); break;
                case 3: getDirection4(); break;
                case 4: getDirection1(); break;
            }

            KeyHandler.upPressed = false;
        }
        if (KeyHandler.downPressed) {
            for (Block block : b) {
                block.y += Block.SIZE;
            }

            KeyHandler.downPressed = false;
        }
        if (KeyHandler.leftPressed) {
            for (Block block : b) {
                block.x -= Block.SIZE;
            }

            KeyHandler.leftPressed = false;
        }
        if (KeyHandler.rightPressed) {
            for (Block block : b) {
                block.x += Block.SIZE;
            }

            KeyHandler.rightPressed = false;
        }

        autoDropCounter++;

        if (autoDropCounter == PlayManager.dropInterval) {
            for (Block block : b) {
                block.y += Block.SIZE;
            }
            autoDropCounter = 0;
        }
    }
    public void draw(Graphics2D g2) {
        g2.setColor(b[0].c);
        int margin = 2;
        for (Block block : b) {
            g2.fillRect(block.x + margin, block.y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        }
    }
}
