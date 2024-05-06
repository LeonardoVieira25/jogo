package jade;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private boolean[] mouseButtonPressed = new boolean[3];
    private boolean isDragging;

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static MouseListener get() {
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }

    public static void mousePosCallback(long window, double xPos, double yPos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xPos;
        get().yPos = yPos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];

        System.out.println("X: " + xPos + " Y: " + yPos +  " Dragging: " + get().isDragging);
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == 1) {
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = true;
            }
        } else if (action == 0) {
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().isDragging = false;
            }
        }
        System.out.println("Button: " + button + " action: " + action);
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;

        System.out.println("Scroll X: " + xOffset + " Scroll Y: " + yOffset);
    }

    public static double getX() {
        return get().xPos;
    }

    public static double getY() {
        return get().yPos;
    }

    public static double getDx() {
        return get().xPos - get().lastX;
    }

    public static double getDy() {
        return get().lastY - get().yPos;
    }

    public static double getScrollX() {
        return get().scrollX;
    }

    public static double getScrollY() {
        return get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }
}
