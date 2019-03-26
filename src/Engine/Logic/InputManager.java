package Engine.Logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InputManager implements KeyListener {

    private long lastPass = 0;

    private volatile ArrayList<Integer> keysPressed = new ArrayList<>();

    public InputManager() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public ArrayList<Integer> forwardInput() {
        ArrayList<Integer> returnList = new ArrayList<>();
        returnList.addAll(keysPressed);
        keysPressed.clear();
        return returnList;
    }
}
