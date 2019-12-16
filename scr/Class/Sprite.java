/**
 * @author Germaniac
 * @date 25/03/2012
 */
 
package Class;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.lang.reflect.Array;

public class Sprite {

    private String[] sprite;
    private boolean visible;
    private int frame;
    private int x;
    private int y;

    public Sprite() {
        sprite = new String[1];
        frame = 0;
        x = 0;
        y = 0;
        visible = false;
    }

    public void setSprite(String image) {
        sprite[sprite.length-1]=image;
        sprite=(String[])resizeArray(sprite,sprite.length+1);
    }

    public String getSprite() {
        return sprite[frame];
    }

    public void putSprite(Graphics2D g2d) {
        if(visible)
            g2d.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(sprite[frame])), x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(sprite[frame])).getHeight(null);
    }

    public int getWidth() {
        return Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(sprite[frame])).getWidth(null);
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getFrame() {
        return frame;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    private static Object resizeArray(Object oldArray, int newSize) {
        int oldSize = Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = Array.newInstance(elementType, newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if(preserveLength>0)
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        return newArray;
    }
}