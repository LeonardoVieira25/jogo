package jade;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import util.Time;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private String title = "Jade Engine";

    private static Window window = null;
    private long glfwWindow;
    private int width, height;

    public static float[] fillColor = new float[] { 1.0f, 0.0f, 0.0f, 0.0f };

    private static Scene currentScene;

    private Window() {
        this.width = 800;
        this.height = 600;
    }

    public static Window get() {
        if (Window.window == null) {
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
    }

    @SuppressWarnings("null")
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        // * glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        this.glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (this.glfwWindow == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        /*
         * key: codigo da tecla
         * scancode: codigo da tecla tbm?
         * action: 1 = press, 0 = release, 2 = hold
         * mods: 1 = shift, 2 = ctrl, 4 = alt, 8 = super
         */
        glfwSetKeyCallback(this.glfwWindow, KeyEventListener::keyCallback);

        glfwSetCursorPosCallback(this.glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(this.glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(this.glfwWindow, MouseListener::mouseScrollCallback);

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(this.glfwWindow, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    this.glfwWindow,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(this.glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(this.glfwWindow);

        Scene.start(0);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        // glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        double lastTime = Time.getTime();
        double deltaTime = -1.0f;
        while (!glfwWindowShouldClose(this.glfwWindow)) {
            glfwPollEvents();
            
            glClearColor(Window.fillColor[0], Window.fillColor[1], Window.fillColor[2], Window.fillColor[3]);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            
            glfwSwapBuffers(this.glfwWindow); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.

            if (deltaTime >= 0) {
                Window.currentScene.update((float) (deltaTime));
            }

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