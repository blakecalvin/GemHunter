package blake_calvin.game;

import blake_calvin.game.Entities.Player;
import blake_calvin.game.Level.Level;
import blake_calvin.game.gfx.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH/12*9;
    public static final int SCALE = 3; // originally 3
    public static final String NAME = "Gem Hunter";

    private JFrame frame;

    public boolean running = false;
    public int tickCount = 0;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();


    private Screen screen;
    public InputHandler input;
    public Level level;
    public Player player;

    //colors
    public static final int BLACK = -16777216;
    public static final int WHITE = -1;


    public Game(){
        setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

        frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init(){
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/Sprite_Sheet.png"));
        input = new InputHandler(this);
        level = new Level("/Levels/Level_(200x200).png");
        player = new Player(level, 0,0, input);
        level.addEntity(player);
    }

    private synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    private synchronized void stop() {
        running = false;
    }

    public void run(){
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D/60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        init();

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime)/nsPerTick;
            lastTime = now;
            boolean shouldRender = true;

            while(delta >= 1){
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(shouldRender){
                frames++;
                render();
            }


            if(System.currentTimeMillis() - lastTimer >= 1000){
                lastTimer += 1000;
                System.out.println(frames+","+ticks);
                frames = 0;
                ticks = 0;
            }
        }
    }



    public void tick(){
        tickCount++;
        level.tick();
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }

        int xOffset = player.x - (screen.width/2) + 5;
        int yOffset = player.y - (screen.height/2);

        level.renderTiles(screen, xOffset, yOffset);

        level.renderEntities(screen);


        for(int y = 0; y < screen.height; y++){
            for(int x= 0; x < screen.width; x++){
                int colourCode = screen.pixels[x+y*screen.width];
                pixels[x+y*WIDTH]=colourCode;
            }
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args){
        new Game().start();
    }


}
