package jade;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector4f;

import components.SpriteRenderer;

public class LevelScene extends Scene {

    public LevelScene() {
        Scene.sceneFillColor = new float[] { 0.0f, 1.0f, 0.0f, 0.0f };

        System.out.println("LevelScene");
    }

    @Override
    public void init() {
        System.out.println("Criando game objects: ======================================================");
        for (int x = 0; x < 80; x++) {
            for (int y = 0; y < 80; y++) {

                GameObject newGameObject = new GameObject("Teste", new Transform(
                        new Vector2f(100 + x * 11, 100 + y * 11),
                        new Vector2f(10, 10)));
                newGameObject.addComponent(
                        new SpriteRenderer("assets/sprites/gato_de_terno.jpg",
                                new Vector4f(
                                        1.0f,
                                        1.0f,
                                        1.0f,
                                        1.0f)));
                addGameObject(newGameObject);
            }
        }
        System.out.println("fim. ======================================================");

        super.init();
        // camera.setPosition(new Vector2d(0.0f, 0.0f));
        Window.getCamera().setPosition(new Vector2d(100, 0));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (MouseListener.isMouseButtonDown(0)) {
            Scene.goToScene(0);
        }
    }
}
