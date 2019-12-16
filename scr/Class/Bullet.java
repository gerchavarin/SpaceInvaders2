/**
 * @author Germaniac
 * @date 25/03/2012
 */

package Class;

public class Bullet extends Sprite {

	private int step;

	Bullet() {

        this.setSprite("Images/bullet.png");

        resetComponents();
	}

	public void resetComponents() {

		this.setVisible(false);
        this.setX(0);
        this.setY(0);
        
        step = 12;
	}

    public int getStep() {
        return step;
    }
    
    public void setStep(int step) {
        this.step = step;
    }

    public boolean checkShoot(Sprite sprite) {
        if(sprite.isVisible())
        	if((this.getX() >= sprite.getX() && (this.getX()+this.getWidth())  <= (sprite.getX()+sprite.getWidth())) && 
               (this.getY() >= sprite.getY() && (this.getY()-this.getHeight()) <= (sprite.getY()+sprite.getHeight()))) {
            	return true;
            }
        return false;
    }
}