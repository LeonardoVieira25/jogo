package jade;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector4f;

import components.SpriteRenderer;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {
        Scene.sceneFillColor = new float[] { 0.0f, 0.0f, 1.0f, 0.0f };
        System.out.println("LevelEditorScene");

        Window.getCamera().setPosition(new Vector2d());
    }

    @Override
    public void init() {

        int width = Window.getWidth();
        int height = Window.getHeight();

        int w = 10;
        int h = 10;
        int gap = 0;

        int cols = width / (w + gap);
        int rows = height / (h + gap);

        System.out.println("Criando game objects: ======================================================");
        GameObject newGameObject;

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                newGameObject = new GameObject(
                        (x + y) % 2 == 0 ? "capivara" : "gato_de_terno",
                        new Transform(
                                new Vector2f(x * (w + gap), y * (h + gap)),
                                new Vector2f(w, h)));
                if ((x + y) % 3 == 0) {
                    newGameObject.addComponent(
                            new SpriteRenderer(new Vector4f(0.0f, 0.0f, 0.0f, 1.0f)));
                } else {

                    if ((x + y) % 2 == 0) {
                        newGameObject.addComponent(
                                new SpriteRenderer(
                                        "assets/sprites/capivara.png",
                                        new Vector4f(1.0f, 0.0f, 1.0f, 1.0f)));
                    } else {
                        newGameObject.addComponent(
                                new SpriteRenderer(
                                        "assets/sprites/gato_de_terno.jpg",
                                        new Vector4f(0.0f, 1.0f, 1.0f, 1.0f)));
                    }
                }
                // newGameObject.addComponent(
                // new SpriteRenderer(
                // (x + y) % 3 == 0 ? "assets/sprites/capivara.png" :
                // "assets/sprites/gato_de_terno.jpg",
                // // "assets/sprites/gato_de_terno.jpg",
                // // "assets/sprites/capivara.png",
                // new Vector4f(1.0f, 1.0f, 1.0f, 1.0f)));
                addGameObject(newGameObject);
            }
        }
        ;
        // newGameObject = new GameObject(
        // "capivara",
        // new Transform(
        // new Vector2f(0, 0),
        // new Vector2f(100, 100)));
        // newGameObject.addComponent(
        // new SpriteRenderer(
        // // "assets/sprites/gato_de_terno.jpg",
        // "assets/sprites/capivara.png",
        // new Vector4f(1.0f, 1.0f, 1.0f, 1.0f)));
        // addGameObject(newGameObject);
        // newGameObject = new GameObject(
        // "gato_de_terno",
        // new Transform(
        // new Vector2f(105, 0),
        // new Vector2f(100, 100)));
        // newGameObject.addComponent(
        // new SpriteRenderer(
        // "assets/sprites/gato_de_terno.jpg",
        // // "assets/sprites/capivara.png",
        // new Vector4f(1.0f, 1.0f, 1.0f, 1.0f)));
        // addGameObject(newGameObject);
        System.out.println("fim. ======================================================");
        System.out.println("GameObjects: " + gameObjects.size());

        super.init();
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

        if (KeyEventListener.isKeyDown(GLFW_KEY_SPACE)) {
            Scene.goToScene(1);
        }
        for (GameObject go : gameObjects) {
            go.update(deltaTime);
        }
    }
}
