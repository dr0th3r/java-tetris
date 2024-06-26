import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    public static int dropInterval = 60; // = 60 frames
    boolean gameOver;

    public PlayManager() {
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT; 

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;
        NEXTMINO_X = right_x + 195;
        NEXTMINO_Y = top_y + 520;

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
    }

    private Mino pickMino() {
        Mino mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0: mino = new Mino_L1(); break;
            case 1: mino = new Mino_L2(); break;
            case 2: mino = new Mino_Square(); break;
            case 3: mino = new Mino_Bar(); break;
            case 4: mino = new Mino_T(); break;
            case 5: mino = new Mino_Z1(); break;
            case 6: mino = new Mino_Z2(); break;
        }

        return mino;
    }

    public void update() {
        if (!currentMino.active) {
            for (Block block : currentMino.b) {
                staticBlocks.add(block);
            }

            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                //if mino could't move it means it's game over
                gameOver = true;
            }

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            checkDelete();
        } 

        currentMino.update();
    }

    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;

        while (x < right_x && y < bottom_y) {            
            for (Block block : staticBlocks) {
                if (block.x == x && block.y == y) {
                    blockCount++;
                }
            } 
    
            x += Block.SIZE;
    
            if (x == right_x) {
                if (blockCount == 12) { 
                    //we go from top to bottom, so we need to delete top to bottom as well (newest blocks are on the top)
                    for (int i = staticBlocks.size() - 1; i >= 0; i--) {
                        Block block = staticBlocks.get(i);
                        if (block.y == y) {
                            staticBlocks.remove(i);
                        } else if (block.y < y) {
                            block.y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.setFont(new Font("Arail", Font.PLAIN, 30));
        //draw play area frame
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        if (currentMino != null) {
            currentMino.draw(g2);
        }

        nextMino.draw(g2);;

        for (Block block : staticBlocks) {
            block.draw(g2);
        }
    
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            x = left_x + 35;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);

        }
        if (KeyHandler.pausePressed) {
            x = left_x + 90;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }
    } 
}
