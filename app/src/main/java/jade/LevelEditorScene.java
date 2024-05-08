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

        Window.getCamera().setPosition(new Vector2d(-100, -100));
    }

    @Override
    public void init() {

        System.out.println("Criando game objects: ======================================================");
        for (int x = 0; x < 1; x++) {
            for (int y = 0; y < 1; y++) {

                GameObject newGameObject = new GameObject("Teste", new Transform(
                        new Vector2f(x * 1, y * 1),
                        new Vector2f(1, 1)));
                newGameObject.addComponent(
                        new SpriteRenderer("assets/sprites/gato_de_terno.jpg",
                                new Vector4f(
                                        x % 2 == 0 ? 1 : 0,
                                        y % 2 == 0 ? 1 : 0,
                                        1.0f,
                                        1.0f)));
                addGameObject(newGameObject);
            }
        }
        System.out.println("fim. ======================================================");

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
