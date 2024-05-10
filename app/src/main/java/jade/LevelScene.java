package jade;

import org.joml.Vector2d;
import org.joml.Vector2f;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import util.AssetPool;

public class LevelScene extends Scene {

    public LevelScene() {
        Scene.sceneFillColor = new float[] { 0.0f, 1.0f, 0.0f, 0.0f };

        System.out.println("LevelScene");
    }

    private Spritesheet spritesheet;
    private Spritesheet spritesheet1;
    private Spritesheet spritesheet2;
    private Spritesheet spritesheet3;
    private SpriteRenderer spriteRenderer;

    @Override
    public void init() {
        AssetPool.clear();
        super.init();

        // spritesheet = AssetPool.getSpritesheet("assets/sprites/spriteseetTeste2.png", 4, 4, 16);
        spritesheet = new Spritesheet("assets/sprites/spriteseetTeste2.png", 4, 4, 16);
        spritesheet1 = new Spritesheet("assets/sprites/spriteseetTeste.png", 4, 4, 16);
        spritesheet2 = new Spritesheet("assets/sprites/capivara.png", 1, 1, 1);
        spritesheet3 = new Spritesheet("assets/sprites/gato_de_terno.jpg", 1, 1, 1);


        spriteRenderer = new SpriteRenderer(spritesheet.getSprite(0), 2);

        System.out.println(
                "========================================== LevelScene init ==========================================");

        Window.getCamera().setPosition(new Vector2d(100, 0));
        GameObject newGameObject = new GameObject("Teste", new Transform(
                new Vector2f(200, 200),
                new Vector2f(50, 50)));
        newGameObject.addComponent(spriteRenderer);
        addGameObject(newGameObject);

        // newGameObject = new GameObject("Teste", new Transform(
        //         new Vector2f(300, 200),
        //         new Vector2f(50, 50)));
        // newGameObject.addComponent(spriteRenderer);
        // addGameObject(newGameObject);
    }

    private Spritesheet getRandomSprite() {
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0:
                return spritesheet;
            case 1:
                return spritesheet1;
            case 2:
                return spritesheet2;
            case 3:
                return spritesheet3;
            default:
                return spritesheet;
        }

        
    }

    float time = 0;
    int counter = 0;
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        time += deltaTime;
        if (time > 0.5) {
            System.out.println("n de game objects: " + gameObjects.size() + " FPS: " + 1/deltaTime);
            counter += 1;
            if(counter > 15) counter = 0;

            // spriteRenderer.setSprite(spritesheet.getSprite(counter));
            // for (GameObject go : gameObjects) {
            //     go.getComponent(SpriteRenderer.class).setSprite(spritesheet.getSprite(counter));
            // }
            time = 0;
        }

        if (MouseListener.isMouseButtonDown(1)) {
            GameObject newGameObject = new GameObject("Teste", new Transform(
                    Camera.getWorldMousePosition(),
                    new Vector2f(50, 50)));
            newGameObject.addComponent(new SpriteRenderer(getRandomSprite().getSprite(0)));
            addGameObject(newGameObject);
        }

        if (MouseListener.isMouseButtonDown(0)) {
            Scene.goToScene(0);
        }
    }
}
