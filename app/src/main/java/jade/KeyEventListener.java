package jade;

public class KeyEventListener {
    private static KeyEventListener instance;
    private boolean[] keys = new boolean[350];

    private KeyEventListener() {
    }

    public static KeyEventListener get() {
        if (KeyEventListener.instance == null) {
            KeyEventListener.instance = new KeyEventListener();
        }
        return KeyEventListener.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == 1) {
            get().keys[key] = true;
        } else if (action == 0) {
            get().keys[key] = false;
        }
    }

    public static boolean isKeyDown(int keyCode) {
        return get().keys[keyCode];
    }   
}
