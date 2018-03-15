package blake_calvin.game.Entities;

import blake_calvin.game.InputHandler;
import blake_calvin.game.Level.Level;
import blake_calvin.game.gfx.Screen;

public class Item extends Entity {

    private InputHandler input;
    public boolean isNear = false;
    private int tickCount = 0;
    public boolean action = false;

    public Item(Level level, int x, int y, InputHandler input) {
        super(level);
        this.x = x;
        this.y = y;
        this.input = input;
    }

    @Override
    public void tick() {
        for(Entity e: level.entities){
            if(e.getClass() == Player.class && e.x <= x+3*8 && e.x >= x-3*8 && e.y <= y+3*8 && e.y >= y-3*8){
                isNear = true;
                if(isNear && input.open.isPressed()){
                    action = true;
                }
            }
        }

        tickCount++;
    }

    @Override
    public void render(Screen screen) {

    }
}
