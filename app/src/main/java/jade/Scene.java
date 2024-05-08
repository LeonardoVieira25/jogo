package jade;

import java.util.ArrayList;
import java.util.List;

import renderer.Renderer;

public abstract class Scene {

    private Renderer renderer = new Renderer();

    private boolean isRunning = false;

    protected static float[] sceneFillColor = new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
    protected static boolean switchingScene = false;
    private static int targetScene = -1;
    public static float animationTime = 0.2f;
    private static float time = 0.0f;
    /*
     * -1: entrando
     * 0: Running
     * 1: saindo
     */
    private static int state = -1;

    private static Scene currentScene;

    protected List<GameObject> gameObjects = new ArrayList<GameObject>();

    public void init() {
        start();
    }

    public Scene() {
        currentScene = this;
        Window.fillColor[0] = sceneFillColor[0];
        Window.fillColor[1] = sceneFillColor[1];
        Window.fillColor[2] = sceneFillColor[2];
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public void addGameObject(GameObject go) {
        gameObjects.add(go);
        this.renderer.add(go);
        go.start();
        if (isRunning) {
        }
    }

    public void start() {
        System.out.println("Start Scene");
        for (GameObject go : gameObjects) {
            go.start();
        }
        this.isRunning = true;
    }

    public static void goToScene(int index) {
        if (switchingScene) {
            return;
        }
        state = 1;
        switchingScene = true;
        targetScene = index;
    }

    public static void start(int index) {
        System.out.println("Static start scene");
        if (switchingScene) {
            return;
        }
        state = -1;
        switchingScene = true;
        targetScene = index;
    }

    public void update(float deltaTime) {
        // System.out.println("FPS: "+1/deltaTime);
        if (!isRunning) {
            isRunning = true;
        }

        this.renderer.render();

        if (switchingScene) {
            time += deltaTime;

            float alpha = (animationTime - time) / animationTime;

            if (state == 1) {

                if (time >= animationTime) {
                    time = 0.0f;

                    Window.changeScene(targetScene);
                    state = -1;
                }

            } else if (state == -1) {

                alpha = 1.0f - alpha;

                if (time >= animationTime) {
                    time = 0.0f;
                    state = 0;
                    targetScene = -1;
                    switchingScene = false;
                    alpha = 1.0f;
                }
            }
            Window.fillColor[0] = sceneFillColor[0] * alpha;
            Window.fillColor[1] = sceneFillColor[1] * alpha;
            Window.fillColor[2] = sceneFillColor[2] * alpha;
        }
    }

}
