/**
 * @author Germaniac
 * @date 25/03/2012
 */

package Class;

public class Bonus extends Sprite {

    public static MusicPlayer soundBonus1;
    public static MusicPlayer soundBonus2;

    private int step;
    
    Bonus() {

        this.setSprite("Images/bonus.png");

        soundBonus1 = new MusicPlayer();
        soundBonus1.open("Sounds/bonus1.wav");

        soundBonus2 = new MusicPlayer();
        soundBonus2.open("Sounds/bonus2.wav");

        resetComponents();
	}

	public void resetComponents() {

        this.setVisible(false);
        this.setX(110);
        this.setY(145);

        step = 1;
	}
    
    public void upgrade() {

        if(this.getX() == 110) {
        	this.soundBonus1.stop();
        }

        this.soundBonus1.start();
        this.setX(this.getX()+step);

        if(this.getX() > 520) {
            this.soundBonus1.stop();
            resetComponents();
        }
    }
}