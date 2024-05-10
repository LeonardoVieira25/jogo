package jade;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.joml.Vector2d;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import util.Time;;

public class Window {
    private String title = "Jade Engine";

    private static Window window = null;
    public static long glfwWindow;
    private int width, height;

    public static float[] fillColor = new float[] { 1.0f, 0.0f, 0.0f, 0.0f };

    private static Scene currentScene;

    private static Camera camera = new Camera(new Vector2d());

    public static Camera getCamera() {
        return camera;
    }


    public static int getWidth() {
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(glfwWindow, widthBuffer, heightBuffer);
        return widthBuffer.get(0);
    }

    public static int getHeight() {
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(glfwWindow, widthBuffer, heightBuffer);
        return heightBuffer.get(0);
    }

    private Window() {
        this.width = 800;
        this.height = 600;
    }

    public static Window get() {
        if (Window.window == null) {
            // GL.createCapabilities();
            Window.window = new Window();
            Window.currentScene = new LevelEditorScene();
        }
        return Window.window;
    }

    public static void changeScene(int newSceneIndex) {
        switch (newSceneIndex) {
            case 0:
                Window.currentScene = new LevelEditorScene();
                break;
            case 1:
                Window.currentScene = new LevelScene();
                break;

            default:
                throw new IllegalArgumentException("Invalid scene index");
        }
        Window.currentScene.init();
    }



    private void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyEventListener::keyCallback);
        
        glfwSwapInterval(1); // Enable v-sync ( limita o fps ) se tirar a gpu vai pra 100%
        
        glfwShowWindow(glfwWindow);
        
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Window.changeScene(0);
        Scene.start(0);
        Window.currentScene.init();

    }

    @SuppressWarnings("static-access")
    private void loop() {
        double lastTime = Time.getTime();
        double deltaTime = -1.0f;
        while (!glfwWindowShouldClose(this.glfwWindow)) {
            glfwPollEvents();

            glClearColor(Window.fillColor[0], Window.fillColor[1], Window.fillColor[2], Window.fillColor[3]);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            if (deltaTime >= 0) {
                Window.currentScene.update((float) (deltaTime));
            }

            glfwSwapBuffers(this.glfwWindow); // swap the color buffers

            deltaTime = Time.getTime() - lastTime;
            lastTime = Time.getTime();

        }
    }

    @SuppressWarnings("null")
    public void run() {
        System.out.println("Running window with width LWJGL: " + Version.getVersion() + "!");
        init();
        loop();

        glfwFreeCallbacks(Window.glfwWindow);
        glfwDestroyWindow(Window.glfwWindow);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}