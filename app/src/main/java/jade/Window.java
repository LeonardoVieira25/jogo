package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
// import org.lwjgl.system.MemoryStack;

import util.Time;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.NULL;;

public class Window {
    private String title = "Jade Engine";

    private static Window window = null;
    public static long glfwWindow;
    private int width, height;

    public static float[] fillColor = new float[] { 1.0f, 0.0f, 0.0f, 0.0f };

    private static Scene currentScene;

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
        GL.createCapabilities();

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyEventListener::keyCallback);

        glfwSwapInterval(1); // Enable v-sync

        glfwShowWindow(glfwWindow);

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

        glfwFreeCallbacks(this.glfwWindow);
        glfwDestroyWindow(this.glfwWindow);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}