/**
 * @author Germaniac
 * @date 25/03/2012
 */

package Class;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Game implements KeyListener {

    public static Bonus bonus;
    public static boolean enemiesDead;
    public static boolean playerDead;
    public static Enemies enemies;
    public static int score;
    public static Player player;

    private boolean exit;
    private boolean keyDown;
    private boolean keyEnter;
    private boolean keyEscape;
    private boolean keyLeft;
    private boolean keyRight;
    private boolean keySpace;
    private boolean keyUp;
    private boolean pauseGame;
    private boolean visible;
    private BufferedImage screen;
    private Graphics graphics;
    private Graphics2D g2;
    private int speedBonus;
    private int speedBulletEnemies;
    private int speedBulletPlayer;
    private int speedEnemies;
    private int speedPlayer;
    private int stage;
    private long timeBonus;
    private long timeBulletEnemies;
    private long timeBulletPlayer;
    private long timeEnemies;
    private long timePlayer;
    private MusicPlayer musicPlayer;
    private Sprite background;
    private Sprite logo;

    Game() {

        graphics = null;
        visible = false;

        screen = new BufferedImage(SpaceInvaders.FRAME_WIDTH, SpaceInvaders.FRAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2 = screen.createGraphics();

        background = new Sprite();
        background.setSprite("Images/background.png");

        logo = new Sprite();
        logo.setSprite("Images/venom.png");

        musicPlayer = new MusicPlayer();

        player = new Player();
        enemies = new Enemies();
        bonus = new Bonus();
    }

    public void resetComponents() {

        background.setVisible(true);
        background.setX(0);
        background.setY(0);

        logo.setVisible(false);
        logo.setX(250);
        logo.setY(100);

        exit = false;
        pauseGame = false;

        speedBonus = 10000;
        speedBulletEnemies = 100;
        speedBulletPlayer = 20;
        speedEnemies = 240;
        speedPlayer = 10;

        score = 0;
        stage = 0;

        playerDead = false;

        musicPlayer.open(SpaceInvaders.menu.getTrackMusic());
        musicPlayer.start();

        player.resetComponents();
        enemies.resetComponents();

        timeBonus= System.currentTimeMillis();
        timeBulletEnemies= System.currentTimeMillis();
        timeBulletPlayer= System.currentTimeMillis();
        timeEnemies = System.currentTimeMillis();
        timePlayer = System.currentTimeMillis();
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setGraphics(Graphics g) {
        graphics = g;
    }

    public void start() {

        resetComponents();

        while(!exit) {

            if(keyEnter) {
                pauseGame = !pauseGame;
                if(pauseGame) {
                    musicPlayer.pause();
                } else {
                    musicPlayer.resume();
                }
                keyEnter = false;
            }

            if(keyEscape) {
                if(!pauseGame) {
                    musicPlayer.stop();
                    SpaceInvaders.menu.setVisible(true);
                    setVisible(false);
                    exit = true;
                }
                keyEscape = false;
            }

            if(!pauseGame) {

                if(System.currentTimeMillis()-timePlayer > speedPlayer) {
                    player.upgrade(keyLeft, keyRight, keyUp, keyDown, keySpace);
                    timePlayer = System.currentTimeMillis();
                }

                if(System.currentTimeMillis()-timeEnemies > speedEnemies) {
                    enemies.upgrade();
                    timeEnemies = System.currentTimeMillis();
                }

                if(System.currentTimeMillis()-timeBulletPlayer > speedBulletPlayer) {
                    player.upgradeBullet();
                    timeBulletPlayer = System.currentTimeMillis();
                }

                if(System.currentTimeMillis()-timeBulletEnemies > speedBulletEnemies) {
                    enemies.upgradeBullet();
                    timeBulletEnemies = System.currentTimeMillis();
                }

                if(System.currentTimeMillis()-timeBonus > speedBonus || bonus.isVisible()) {
                    bonus.setVisible(true);
                    bonus.upgrade();
                    timeBonus = System.currentTimeMillis();
                }

            }

            if(playerDead || enemiesDead) {
                while(!keyEnter) {
                    draw();
                }
                musicPlayer.stop();
                SpaceInvaders.menu.setVisible(true);
                setVisible(false);
                exit = true;
                keyEnter = false;
                playerDead = false;
                enemiesDead = false;
            }

            draw();
        }
    }

    private void draw() {

        background.putSprite(g2);
        player.putSprite(g2);
        player.bullet.putSprite(g2);
        bonus.putSprite(g2);

        for(int i = 0; i < enemies.getNumberEnemies(); i++) {
            enemies.enemy[i].putSprite(g2);
        }

        for(int i = 0; i < enemies.bullet.length; i++) {
            enemies.bullet[i].putSprite(g2);
        }

        g2.setColor(new Color(255, 255, 70));
        g2.setFont(new Font("Consolas", Font.PLAIN, 18));
        String scoreText = score + "";
        for(int i = scoreText.length(); i < 4; i++) {
            scoreText = "0" + scoreText;
        }
        g2.drawString("SCORE " + scoreText, 135, 130);
        g2.drawString("STAGE " + stage, 490, 130);

        if(pauseGame || enemiesDead || playerDead) {
            g2.setColor(new Color(0, 0, 0, 220));
            g2.fill(new Rectangle(0, 0, SpaceInvaders.FRAME_WIDTH, SpaceInvaders.FRAME_HEIGHT));
            g2.setColor(new Color(255, 255, 255, 210));
        }

        if(pauseGame) {
            g2.setFont(new Font("Consolas", Font.BOLD, 28));
            g2.drawString("PAUSE", 310, 345);
            logo.setVisible(true);
            logo.putSprite(g2);
        }

        if(playerDead || enemiesDead) {
            g2.setFont(new Font("Consolas", Font.BOLD, 32));
            if(enemiesDead) {
                g2.drawString("You Winner", 270, 250);
            }
            if(playerDead) {
                g2.drawString("You Lost", 280, 250);
            }
            g2.drawString("Score: " + scoreText, 250, 300);
        }

        graphics.drawImage(screen, 0, 0, null);
    }

    public void keyPressed(KeyEvent ke) {

        switch(ke.getKeyCode()) {

            case KeyEvent.VK_ESCAPE:
                keyEscape = true;
                break;

            case KeyEvent.VK_DOWN:
                keyDown = true;
                break;

            case KeyEvent.VK_ENTER:
                keyEnter = true;
                break;

            case KeyEvent.VK_UP:
                keyUp = true;
                break;

            case KeyEvent.VK_SPACE:
                keySpace = true;
                break;

            case KeyEvent.VK_LEFT:
                keyLeft = true;
                break;

            case KeyEvent.VK_RIGHT:
                keyRight = true;
                break;
        }
    }

    public void keyReleased(KeyEvent ke) {

        switch(ke.getKeyCode()) {

            case KeyEvent.VK_DOWN:
                keyDown = false;
                break;

            case KeyEvent.VK_UP:
                keyUp = false;
                break;

            case KeyEvent.VK_LEFT:
                keyLeft = false;
                break;

            case KeyEvent.VK_RIGHT:
                keyRight = false;
                break;

            case KeyEvent.VK_SPACE:
                keySpace = false;
                break;
        }
    }

    public void keyTyped(KeyEvent ke) {}
}