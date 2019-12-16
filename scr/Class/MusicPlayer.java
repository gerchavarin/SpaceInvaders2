/**
 * @author Germaniac
 * @date 25/03/2012
 */

package Class;

import javazoom.jlgui.basicplayer.BasicPlayer;
import java.io.File;

public class MusicPlayer {

    private BasicPlayer player;

	MusicPlayer() {
        player = new BasicPlayer();
	}

	public void pause() {
		try {
			player.pause();
		}
		catch(Exception e) {}
	}
	 
	public void resume() {
		try {
			player.resume();
		}
		catch(Exception e) {}
	}
	 
	public void stop() {
		try {
			player.stop();
		}
		catch(Exception e) {}
	}

	public void open(String path) {
		try {
			player.open(new File(path));
		}
		catch(Exception e) {}
	}

	public void start() {
		try {
			player.play();
		}
		catch(Exception e) {}
	}
}