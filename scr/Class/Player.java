/**
 * @author Germaniac
 * @date 25/03/2012
 */

package Class;

public class Player extends Sprite {

    public static Bullet bullet;

    private boolean shooting;
    private int step;
    private MusicPlayer enemyDeadSound;
    private MusicPlayer playerShotSound;

	Player() {

        this.setSprite("Images/player.png");

        bullet = new Bullet();

        playerShotSound = new MusicPlayer();
        playerShotSound.open("Sounds/shot.wav");

        enemyDeadSound = new MusicPlayer();
        enemyDeadSound.open("Sounds/dead.wav");

        resetComponents();
	}

	public void resetComponents() {

        this.setVisible(true);
        this.setX(340);
        this.setY(470);

        step = 10;

        shooting = false;
	}

    public int getStep() {
        return step;
    }
    
    public void setStep(int step) {
        this.step = step;
    }

    public boolean isShooting() {
        return shooting;
    }
    
    public void setShooting(boolean state) {
        this.shooting = state;
    }
    
	public void upgrade(boolean left, boolean right, boolean up, boolean down, boolean shot) {

        if(left) {
            if(this.getX()>115+this.getWidth())
                this.setX(this.getX()-step);
        }
        
        if(right) {
            if(this.getX()<115+460-(this.getWidth()*2))
                this.setX(this.getX()+step);
        }

        if(left || right) {
        	SpaceInvaders.game.enemies.bulletCheck();
        }

        if(shot) {
            if(!isShooting()) {
                playerShotSound.stop();
                playerShotSound.start();
                shooting = true;
                bullet.setVisible(true);
                bullet.setX(this.getX()+(this.getWidth()/2)-3);
                bullet.setY(this.getY()-(this.getHeight()));
            }
        }
    }

    public void upgradeBullet() {

        if(isShooting()) {

			if(bullet.getY() >= SpaceInvaders.game.enemies.POSITION_Y-25) {

				bullet.setY(bullet.getY()-bullet.getStep());

                for(int i = 0; i < SpaceInvaders.game.enemies.getNumberEnemies(); i++)  {
                    if(bullet.checkShoot(SpaceInvaders.game.enemies.enemy[i])) {
                        enemyDeadSound.stop();
                        enemyDeadSound.start();
                        setShooting(false);
                        bullet.setVisible(false);
                        SpaceInvaders.game.enemies.enemy[i].setVisible(false);
                        SpaceInvaders.game.score += 10;
                    }
                }

                if(bullet.checkShoot(SpaceInvaders.game.bonus)) {
                    SpaceInvaders.game.bonus.soundBonus1.stop();
                    SpaceInvaders.game.bonus.soundBonus2.start();
                	setShooting(false);
                    bullet.setVisible(false);
                    SpaceInvaders.game.bonus.setVisible(false);
                    SpaceInvaders.game.score += 6;
                }
   
			} else {
				setShooting(false);
				bullet.setVisible(false);
            }
        }
    }
}