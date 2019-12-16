/**
 * @author Germaniac
 * @date 25/03/2012
 */

package Class;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import javax.swing.JFrame;

public final class SpaceInvaders extends Canvas implements KeyListener {

    public static final int FRAME_WIDTH = 700;
    public static final int FRAME_HEIGHT  = 650;

    public static Game game;
    public static Menu menu;

    private JFrame window;

    SpaceInvaders() {

        window = new JFrame();
        window.setTitle("Space Invaders");
        window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.add(this);

        menu = new Menu();
        menu.setVisible(true);
        menu.setGraphics(this.getGraphics());

        game = new Game();
        game.setVisible(false);
        game.setGraphics(this.getGraphics());

        this.requestFocus();

        while(true) {

            if(menu.isVisible()) {
                this.addKeyListener(menu);
                menu.start();
                this.removeKeyListener(menu);
            }

            if(game.isVisible()) {
                this.addKeyListener(game);
                game.start();
                this.removeKeyListener(game);
            }
        }
    }
 
    public static void main(String [] args) {
        new SpaceInvaders();
    }

    public void keyTyped(KeyEvent ke) {}
    public void keyPressed(KeyEvent ke) {}
    public void keyReleased(KeyEvent ke) {}
}