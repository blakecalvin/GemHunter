package blake_calvin.game.Level.Tiles;

import blake_calvin.game.Level.Level;
import blake_calvin.game.gfx.Screen;

public class BasicTile extends Tile{

    protected int tileId;

    public BasicTile(int id, int x, int y, int LevelColour){
        super(id, false, false, LevelColour);
        this.tileId = x+y*32;

    }

    public void tick(){

    }

    public void render(Screen screen, Level level, int x, int y) {
        screen.render(x, y, tileId);
    }
}
