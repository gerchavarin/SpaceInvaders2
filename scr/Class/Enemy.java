/**
 * @author Germaniac
 * @date 25/03/2012
 */

package Class;

public class Enemy extends Sprite {

    private boolean course;
	private boolean sprite;
	private int step;

    public void setSprite(boolean sprite) {
        this.sprite = sprite;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setCourse(boolean course) {
        this.course = course;
    }
    
    public boolean getCourse() {
        return course;
    }

    public int getStep() {
        return step;
    }

	public boolean checkEnemiesCourse(int ini, int end, boolean status) {
        for(int i = ini; i < end; i++) {
            if(SpaceInvaders.game.enemies.enemy[i].isVisible()) {
                if(SpaceInvaders.game.enemies.enemy[i].getCourse() != status) {
                    for(int j = ini;  j < end; j++) {
                        SpaceInvaders.game.enemies.enemy[j].setCourse(SpaceInvaders.game.enemies.enemy[i].getCourse());
                    }
                    return SpaceInvaders.game.enemies.enemy[i].getCourse();
                }
            }
        }
        return status;
    }
    
    public void upgrade() {
        
        for(int i = 0; i < SpaceInvaders.game.enemies.rowCourse.length; i++) {
            SpaceInvaders.game.enemies.rowCourse[i] = checkEnemiesCourse(i*11,(i*11)+11,SpaceInvaders.game.enemies.rowCourse[i]);
        }
        
        if(sprite) {
            this.setFrame(0);
            sprite = false;
        } else {
            this.setFrame(1);
            sprite = true;
        }
        
        if(course) {
            if(this.getX()<115+460-(this.getWidth()*2)) {
                this.setX(this.getX()+step);
            } else {
                this.setX(this.getX()+step);
                course = false;
            }
        } else {
            if(this.getX()>115+this.getWidth()) {
                this.setX(this.getX()-step);
            } else {
                this.setX(this.getX()+step);
                course = true;
            }
        }
    }
}