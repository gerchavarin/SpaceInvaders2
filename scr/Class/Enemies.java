/**
 * @author Germaniac
 * @date 25/03/2012
 */

package Class;

public class Enemies {

	public static final int POSITION_Y = 180;

        public static Bullet bullet[];

        public boolean rowCourse[];
        public Enemy enemy[];

        private boolean shooting[];
        private int delayShooting;
        private int enemyShooting[];

	Enemies() {

        enemy = new Enemy[66];
        
        for(int i = 0; i < enemy.length; i++) {
            enemy[i] = new Enemy();
    	}

        rowCourse = new boolean[6];

        for(int i = 0; i < 11; i++) {
            enemy[i].setSprite("Images/enemy1a.png");
            enemy[i].setSprite("Images/enemy1b.png");
        }

        for(int i = 11; i < 22; i++) {
            enemy[i].setSprite("Images/enemy1a.png");
            enemy[i].setSprite("Images/enemy1b.png");
        }

        for(int i = 22; i < 33; i++) {
            enemy[i].setSprite("Images/enemy2a.png");
            enemy[i].setSprite("Images/enemy2b.png");
        }

        for(int i = 33; i < 44; i++) {
            enemy[i].setSprite("Images/enemy2a.png");
            enemy[i].setSprite("Images/enemy2b.png");
        }

        for(int i = 44; i < 55; i++) {
            enemy[i].setSprite("Images/enemy3a.png");
            enemy[i].setSprite("Images/enemy3b.png");
        }

        for(int i = 55; i < 66; i++) {
            enemy[i].setSprite("Images/enemy3a.png");
            enemy[i].setSprite("Images/enemy3b.png");
        }

        bullet = new Bullet[4];
        shooting = new boolean[bullet.length];
        enemyShooting = new int[bullet.length];

        for(int i = 0; i < bullet.length; i++) {
            bullet[i] = new Bullet();
    	}

        resetComponents();
	}

	public void resetComponents() {
        
        for(int i = 0; i < 11; i++) {
            enemy[i].setVisible(true);
            enemy[i].setX(150+((i+1)*30));
            enemy[i].setY(POSITION_Y);
            enemy[i].setCourse(true);
            enemy[i].setStep(5);
        }
        
        for(int i = 11; i < 22; i++) {
            enemy[i].setVisible(true);
            enemy[i].setX(150+((i-10)*30));
            enemy[i].setY(POSITION_Y+30);
            enemy[i].setCourse(true);
            enemy[i].setStep(5);
        }

        for(int i = 22; i < 33; i++) {
            enemy[i].setVisible(true);
            enemy[i].setX(150+((i-21)*30));
            enemy[i].setY(POSITION_Y+60);
            enemy[i].setCourse(true);
            enemy[i].setStep(5);
        }

        for(int i = 33; i < 44; i++) {
            enemy[i].setVisible(true);
            enemy[i].setX(150+((i-32)*30));
            enemy[i].setY(POSITION_Y+90);
            enemy[i].setCourse(true);
            enemy[i].setStep(5);
        }

        for(int i = 44; i < 55; i++) {
            enemy[i].setVisible(true);
            enemy[i].setX(150+((i-43)*29));
            enemy[i].setY(POSITION_Y+120);
            enemy[i].setCourse(true);
            enemy[i].setStep(5);
        }

        for(int i = 55; i < 66; i++) {
            enemy[i].setVisible(true);
            enemy[i].setX(150+((i-54)*29));
            enemy[i].setY(POSITION_Y+150);
            enemy[i].setCourse(true);
            enemy[i].setStep(5);
        }

        for(int i = 0; i < rowCourse.length; i++) {
            rowCourse[i] = false;
        }

        for(int i = 0; i < bullet.length; i++) {
            shooting[i] = false;
            enemyShooting[i] = 0;
            bullet[i].resetComponents();
        }

        delayShooting = 50;
	}

    public int getNumberEnemies() {
    	return enemy.length;
    }

	public void upgrade() {
        SpaceInvaders.game.enemiesDead = true;

        for(int i = 0; i < enemy.length; i++) {
            enemy[i].upgrade();
            if(enemy[i].isVisible()) {
                SpaceInvaders.game.enemiesDead = false;
            }
        }
    }

	public void upgradeBullet() {

        int selectColumn;
        int selectBullet;
		int selectEnemy;
		boolean selectRandom;

        selectColumn = 0;

        for(int i = 0; i < shooting.length; i++) {

        	if(shooting[i]) {

				if(bullet[i].getY() <= 470+16) {
					bullet[i].setY(bullet[i].getY()+bullet[i].getStep());
				} else {
					shooting[i] = false;
					bullet[i].setVisible(false);
	            }

        	} else {

                if(delayShooting == 0) {

                    selectBullet = (int) (Math.random()*enemyShooting.length);

                    selectRandom = false;

                    while(!selectRandom) {
                		for(int j = 0; j < enemyShooting.length; j++) {
                            if(j != selectBullet) {
                                selectColumn = (int) (Math.random()*11);
                                if(selectColumn != enemyShooting[i]) {
                                    selectRandom = true;
                                }
                            }
                        }
                    }

    		        for(int j = 5; j >= 0; j--) {
    		        	selectEnemy = selectColumn + (j*11);
    		        	if(enemy[selectEnemy].isVisible()) {
    		        		shooting[i] = true;
    		        		enemyShooting[i] = selectEnemy;
    		                bullet[i].setVisible(true);
    		                bullet[i].setX(enemy[selectEnemy].getX()+(enemy[selectEnemy].getWidth()/2)-3);
    		                bullet[i].setY(enemy[selectEnemy].getY()+(enemy[selectEnemy].getHeight()));
    		        		System.out.println(selectEnemy);
    		        		break;
    		        	}
    		        }

                } else {
                    delayShooting--;
                }
	    	}
        }

        bulletCheck();
	}

	public void bulletCheck()  {
        for(int i = 0; i < shooting.length; i++) {
        	if(shooting[i]) {
				if(bullet[i].getY() <= 470+16) {
                    if(bullet[i].checkShoot(SpaceInvaders.game.player)) {
                        shooting[i] = false;
                        SpaceInvaders.game.playerDead = true;
                    }
				} else {
					shooting[i] = false;
					bullet[i].setVisible(false);
	            }
        	}
        }
	}
}