package blake_calvin.game.Entities;

import blake_calvin.game.InputHandler;
import blake_calvin.game.Level.Level;
import blake_calvin.game.gfx.Screen;


public class Player extends Mob{

    private InputHandler input;
    private int scale = 1;
    protected boolean isSwimming = false;
    private int tickCount = 0;


    public Player(Level level, int x, int y, InputHandler input){
        super(level, "Player", x, y, 1);
        this.input = input;

    }

    public void tick() {
        int xa = 0;
        int ya = 0;

        if(input.up.isPressed()){
            ya--;
        }
        if(input.down.isPressed()){
            ya++;
        }
        if(input.left.isPressed()){
            xa--;
        }
        if(input.right.isPressed()){
            xa++;
        }

        if(xa != 0 || ya != 0){
            move(xa, ya);
            isMoving = true;
        }
        else {
            isMoving = false;
        }

        if(level.getTile(this.x >> 3, this.y >> 3).getId() == 5){
            isSwimming = true;
        }
        if(isSwimming && level.getTile(this.x >> 3, this.y >> 3).getId() != 5){
            isSwimming = false;
        }
        tickCount++;
    }

    public void render(Screen screen) {
         int pose;
         int startTile;
         int waveOffset = 0;

         int walkingSpeed = 3;
         int flip = (numSteps >> walkingSpeed) % 6;

         if(isMoving == false){
             flip = 0;
             numSteps = 1;
         }

         int modifier = 8 * scale;
         int xOffset = x - modifier/2;
         int yOffset = y - modifier/2 -4;

         if(movingDir == 1){
             pose = 0 + flip;
             startTile = 64;
         }
         else if(movingDir == 2){
             pose = 6 + flip;
             startTile = 160;
             waveOffset = -2;
         }
         else if(movingDir == 3){
             pose = 0 + flip;
             startTile = 160;
             waveOffset = 2;
         }
         else{
             pose = 6 + flip;
             startTile = 64;
         }

         if(isSwimming){
            int waveTile = 256;
            yOffset += 14;

            if(tickCount % 45 < 15){
                waveTile = 256;
            }
            else if(15 <= tickCount % 45 && tickCount % 45 < 30){
                yOffset -= 1;
                waveTile = 258;
            }
            else{
                waveTile = 260;
            }
             screen.render(xOffset + waveOffset, yOffset + 2, waveTile);
             screen.render(xOffset + modifier + waveOffset, yOffset + 2, waveTile + 1);
         }

        screen.render(xOffset, yOffset, startTile+(2*pose));
        screen.render(xOffset + modifier, yOffset, startTile+1+(2*pose));

        if(!isSwimming){
            screen.render(xOffset, yOffset + modifier, startTile+32+(2*pose));
            screen.render(xOffset + modifier, yOffset + modifier, startTile+33+(2*pose));
            screen.render(xOffset, yOffset + (modifier*2), startTile+64+(2*pose));
            screen.render(xOffset + modifier, yOffset + (modifier*2), startTile+65+(2*pose));
        }

    }

    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 7;
        int yMin = 8;
        int yMax = 14;

        for(int x = xMin; x < xMax; x++){
            if(isSolidTile(xa, ya, x, yMin)){
                return true;
            }
        }
        for(int x = xMin; x < xMax; x++){
            if(isSolidTile(xa, ya, x, yMax)){
                return true;
            }
        }
        for(int y = yMin; y < yMax; y++){
            if(isSolidTile(xa, ya, xMin, y)){
                return true;
            }
        }
        for(int y = yMin; y < yMax; y++){
            if(isSolidTile(xa, ya, xMax, y)){
                return true;
            }
        }
        return false;
    }

}
