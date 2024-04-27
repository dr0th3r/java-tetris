import java.awt.Color;
import java.awt.Graphics2D;

public class Mino {
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1;
    public boolean active = true;
    boolean deactivating;
    int deactivatingCounter = 0;
    
    boolean leftCollision, rightCollision, bottomCollision;

    public void create(Color c) {
        for (int i = 0; i < 4; i++) {
            b[i] = new Block(c);
            tempB[i] = new Block(c);
        }
    }
    public void setXY(int x, int y) {}
    public void updateXY(int direction) {
        if (leftCollision || rightCollision || bottomCollision) return;

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
    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        for (Block block : b) {
            if (block.x == PlayManager.left_x) {
                leftCollision = true;
            }

            if (block.x + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;
            }

            if (block.y + Block.SIZE == PlayManager.bottom_y) {
                bottomCollision = true;
            } 
        }
    }
    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        for (Block block : tempB) {
            if (block.x < PlayManager.left_x) {
                leftCollision = true;
            }

            if (block.x + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
            }

            if (block.y + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true;
            } 
        }
    }
    public void checkStaticBlockCollision() {
        for (Block staticBlock : PlayManager.staticBlocks) {
            for (Block block : b) {
                if (block.y + Block.SIZE == staticBlock.y && block.x == staticBlock.x) {
                    bottomCollision = true;
                }

                if (block.x - Block.SIZE == staticBlock.x && block.y == staticBlock.y) {
                    leftCollision = true;
                }

                if (block.x + Block.SIZE == staticBlock.x && block.y == staticBlock.y) {
                    rightCollision = true;
                }
            }
        }
    }

    public void update() {
        if (deactivating) {
            deactivating();
        }


        if (KeyHandler.upPressed) {
            switch (direction) {
                case 1: getDirection2(); break;
                case 2: getDirection3(); break;
                case 3: getDirection4(); break;
                case 4: getDirection1(); break;
            }

            KeyHandler.upPressed = false;
        }

        checkMovementCollision();

        if (KeyHandler.downPressed) {
            if (!bottomCollision) {
                for (Block block : b) {
                    block.y += Block.SIZE;
                }

                autoDropCounter = 0;
            }

            KeyHandler.downPressed = false;
        }
        if (KeyHandler.leftPressed) {
            if (!leftCollision) {
                for (Block block : b) {
                    block.x -= Block.SIZE;
                }
            }

            KeyHandler.leftPressed = false;
        }
        if (KeyHandler.rightPressed) {
            if (!rightCollision) {
                for (Block block : b) {
                    block.x += Block.SIZE;
                }
            }

            KeyHandler.rightPressed = false;
        }

        if (bottomCollision) {
            deactivating = true;
            return;
        }
        
        autoDropCounter++;

        if (autoDropCounter == PlayManager.dropInterval) {
            for (Block block : b) {
                block.y += Block.SIZE;
            }
            autoDropCounter = 0;
        }
    }
    
    public void deactivating() {
        deactivatingCounter++;
        
        if (deactivatingCounter == 45) {
            deactivatingCounter = 0;
            checkMovementCollision();

            if (bottomCollision) {
                active = false;
                deactivating = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(b[0].c);
        int margin = 2;
        for (Block block : b) {
            g2.fillRect(block.x + margin, block.y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        }
    }
}
