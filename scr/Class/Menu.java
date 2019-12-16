/**
 * @author Germaniac
 * @date 25/03/2012
 */

package Class;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JOptionPane;

public class Menu implements KeyListener {

    private boolean animationPressEnter;
    private boolean exit;
    private boolean keyDown;
    private boolean keyEnter;
    private boolean keyEscape;
    private boolean keyUp;
    private boolean showDisplay;
    private boolean showMenu;
    private boolean showOptions;
    private boolean visible;
    private BufferedImage screen;
    private Graphics graphics;
    private Graphics2D g2;
    private int numberOptions;
    private int numberTracksMusic;
    private int selectedOption;
    private int selectedTrackMusic;
    private int speedPressEnter;
    private long timePressEnter;
    private Sprite background;
    private Sprite logo;
    private String tracks[];

    Menu() {

		graphics = null;
        visible = false;

        screen = new BufferedImage(SpaceInvaders.FRAME_WIDTH, SpaceInvaders.FRAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2 = screen.createGraphics();

    	background = new Sprite();
        background.setSprite("Images/background.png");

        logo = new Sprite();
        logo.setSprite("Images/logo.png");

        numberOptions = 3;
        numberTracksMusic = 3;

        tracks = new String[numberTracksMusic];

        tracks[0] = "Music/Chop Suey.mp3";
        tracks[1] = "Music/One.mp3";
        tracks[2] = "Music/Black Metal Ist Krieg.mp3";

        speedPressEnter = 700;
    }

	public void resetComponents() {

        background.setVisible(true);
        background.setX(0);
        background.setY(0);

        logo.setVisible(true);
        logo.setX(228);
        logo.setY(150);

        exit = false;

        selectedOption = 1;
        selectedTrackMusic = 1;

        animationPressEnter = true;

        keyDown = false;
        keyEscape = false;
        keyEnter = false;
        keyUp = false;
        
        showDisplay = true;
        showMenu = false;
        showOptions = false;

        timePressEnter = System.currentTimeMillis();
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

    public String getTrackMusic() {
        return tracks[selectedTrackMusic-1];
    }

    public void start() {

    	resetComponents();

        while(!exit) {

            if(System.currentTimeMillis()-timePressEnter > speedPressEnter) {
                animationPressEnter = !animationPressEnter;
                timePressEnter = System.currentTimeMillis();
            }

            if(showDisplay) {
                if(keyEnter) {
                    showMenu = true;
                    showDisplay = false;
                    keyEnter = false;
                }
            }

            if(showMenu) {

                if(keyEnter) {
                    switch(selectedOption) {
                        case 1:
                            SpaceInvaders.game.setVisible(true);
                            setVisible(false);
                            exit = true;
                            break;
                        case 2:
                            showMenu = false;
                            showOptions = true;
                            keyEnter = false;
                            break;
                        case 3:
                            resetComponents();
                            break;
                    }
                    
                }

                if(keyUp) {
                    selectedOption = selectedOption - 1 % numberOptions;
                    keyUp = false;
                }

                if(keyDown) {
                    selectedOption = (selectedOption % numberOptions) + 1;
                    keyDown = false;
                }

                if(selectedOption == 0) {
                    selectedOption = numberOptions;
                }
            }

            if(showOptions) {

                if(keyEnter) {
                    showMenu = true;
                    showOptions = false;
                    keyEnter = false;
                }

                if(keyUp) {
                    selectedTrackMusic = selectedTrackMusic - 1 % numberOptions;
                    keyUp = false;
                }

                if(keyDown) {
                    selectedTrackMusic = (selectedTrackMusic % numberOptions) + 1;
                    keyDown = false;
                }

                if(selectedTrackMusic == 0) {
                    selectedTrackMusic = numberTracksMusic;
                }
            }

            if(keyEscape) {
                if(JOptionPane.showConfirmDialog(null, "Do you really want exit?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
                    System.exit(0);
                }
                keyEscape = false;
            }

            draw();
        }
    }

    private void draw() {

        background.putSprite(g2);

        if(showDisplay) {
            logo.putSprite(g2);
            if(animationPressEnter) {
                drawText("Press Enter", 28, new Color(255, 255, 255, 210), 260, 420);
            }
        }

        if(showMenu) {

            int posX = 180;
            int posY = 160;

            drawText("Start Game", 28, new Color(255, 255, 255, 210), posX, posY);
            drawText("Options", 28, new Color(255, 255, 255, 210), posX, posY+40);
            drawText("Exit", 28, new Color(255, 255, 255, 210), posX, posY+80);
            drawText(">", 28, new Color(255, 255, 255, 210), posX-30, posY+((selectedOption-1)*40));
        }

        if(showOptions) {

            int posX = 180;
            int posY = 160;

            drawText("Track Music", 28, new Color(255, 255, 255, 210), posX, posY);
            drawText("Chop Suey - System of a Down", 20, new Color(255, 255, 255, 210), posX, posY+60);
            drawText("One - Metallica", 20, new Color(255, 255, 255, 210), posX, posY+90);
            drawText("Black Metal Ist Krieg - Nargaroth", 20, new Color(255, 255, 255, 210), posX, posY+120);
            drawText(">", 20, new Color(255, 255, 255, 210), posX-30, posY+((selectedTrackMusic-1)*30)+60);
        }

        graphics.drawImage(screen, 0, 0, null);
    }

    private void drawText(String text, int size, Color color, int x, int y) {
    	g2.setColor(color);
    	g2.setFont(new Font("Consolas", Font.BOLD, size));
    	g2.drawString(text, x, y);
    }

    public void keyPressed(KeyEvent ke) {

        switch(ke.getKeyCode()) {

            case KeyEvent.VK_DOWN:
                keyDown = true;
                break;

            case KeyEvent.VK_ENTER:
                keyEnter = true;
                break;

            case KeyEvent.VK_ESCAPE:
                keyEscape = true;
                break;

            case KeyEvent.VK_UP:
                keyUp = true;
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
        }
    }

    public void keyTyped(KeyEvent ke) {}
}