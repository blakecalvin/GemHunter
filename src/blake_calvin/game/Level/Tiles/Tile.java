package blake_calvin.game.Level.Tiles;

import blake_calvin.game.Level.Level;
import blake_calvin.game.gfx.Screen;

public abstract class Tile {

    public static final int BLACK = -16777216;
    public static final int WHITE = -1;

    public static final Tile[] tiles =  new Tile[256];
    public static final Tile VOID = new BasicSolidTile(0,0,0, 0xFF000000);
    public static final Tile STONE = new BasicSolidTile(1, 4, 0, 0xFF646464);
    public static final Tile GRASS = new BasicTile(2, 1, 0, 0XFF00FF00);
    public static final Tile GRASS2 = new BasicTile(3, 2, 0, 0xFF01E801);
    public static final Tile GRASS3 = new BasicTile(4, 3, 0, 0xFF01CD01);
    public static final Tile WATER = new AnimatedTile(5, new int[][] {{0,1}, {1,1}, {2,1}, {1,1}}, 0xFF5A9CAD, 500);
    public static final Tile STONE_MID = new BasicSolidTile(6, 5, 0, 0xFFFF1616);
    public static final Tile STONE_LEFT = new BasicSolidTile(7, 8, 0, 0xFFFF8080);
    public static final Tile STONE_RIGHT = new BasicSolidTile(8, 7, 0, 0xFFA90707);
    public static final Tile S_TOP_LEFT = new BasicSolidTile(9, 9, 0, 0xFFA447FB);
    public static final Tile S_TOP_RIGHT = new BasicSolidTile(10, 10, 0, 0xFF8A12FB);
    public static final Tile S_TOP_MID = new BasicSolidTile(11, 11, 0, 0xFF7E23D4);
    public static final Tile STONE_SINGLE = new BasicSolidTile(12, 6, 0, 0xFF903E3E);



    protected byte id;
    protected boolean solid;
    protected boolean emitter;
    private int levelColour;

    public Tile(int id, boolean isSolid, boolean isEmitter, int levelColour){
        this.id = (byte) id;
        if(tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
        this.solid = isSolid;
        this.emitter = isEmitter;
        this.levelColour = levelColour;
        tiles[id] = this;
    }

    public byte getId() {

        return id;
    }

    public boolean isSolid(){

        return solid;
    }

    public boolean isEmitter(){

        return emitter;
    }

    public int getLevelColour() {

        return levelColour;
    }

    public abstract void tick();

    public abstract void render(Screen screen, Level level, int x, int y);
}
