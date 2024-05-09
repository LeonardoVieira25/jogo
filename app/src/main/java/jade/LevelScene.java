package jade;

import org.joml.Vector2d;
import org.joml.Vector2f;

import components.SpriteRenderer;
import components.Spritesheet;

public class LevelScene extends Scene {

    public LevelScene() {
        Scene.sceneFillColor = new float[] { 0.0f, 1.0f, 0.0f, 0.0f };

        System.out.println("LevelScene");
    }

    @Override
    public void init() {
        System.out.println("Criando game objects: ======================================================");
        // for (int x = 0; x < 80; x++) {
        // for (int y = 0; y < 40; y++) {

        // GameObject newGameObject = new GameObject("Teste", new Transform(
        // new Vector2f(100 + x * 11, 100 + y * 11),
        // new Vector2f(10, 10)));
        // newGameObject.addComponent(spriteRenderer);
        // addGameObject(newGameObject);
        // }
        // }
        System.out.println("fim. ======================================================");

        super.init();
        // camera.setPosition(new Vector2d(0.0f, 0.0f));
        Window.getCamera().setPosition(new Vector2d(100, 0));
    }
    private Spritesheet spritesheet = new Spritesheet("assets/sprites/gato_de_terno.jpg", 1, 1, 1);
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (MouseListener.isMouseButtonDown(1)) {
            GameObject newGameObject = new GameObject("Teste", new Transform(
                    Camera.getWorldMousePosition(),
                    new Vector2f(50, 50)));
            newGameObject.addComponent(new SpriteRenderer(spritesheet.getSprite(0)));
            addGameObject(newGameObject);
        }

        if (MouseListener.isMouseButtonDown(0)) {
            Scene.goToScene(0);
        }
    }
}
