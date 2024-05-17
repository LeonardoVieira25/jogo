package jade;

import org.joml.Vector2f;

import components.SpriteRenderer;
import components.Spritesheet;

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
        // spritesheet = AssetPool.getSpritesheet("assets/sprites/spriteseetTeste2.png",
        // 4, 4, 16);
        spritesheet = new Spritesheet("assets/sprites/spriteseetTeste2.png", 4, 4, 16);
        spritesheet1 = new Spritesheet("assets/sprites/spriteseetTeste.png", 4, 4, 16);
        spritesheet2 = new Spritesheet("assets/sprites/capivara.png", 1, 1, 1);
        spritesheet3 = new Spritesheet("assets/sprites/gato_de_terno.jpg", 1, 1, 1);

        spriteRenderer = new SpriteRenderer(spritesheet.getSprite(0), 2);

        System.out.println(
                "========================================== LevelScene init ==========================================");

        // Window.getCamera().setPosition(new Vector2d(100, 0));
        GameObject newGameObject = new GameObject("Teste", new Transform(
                new Vector2f(200, 200),
                new Vector2f(50, 50)));
        newGameObject.addComponent(spriteRenderer);
        addGameObject(newGameObject);

        // newGameObject = new GameObject("Teste", new Transform(
        // new Vector2f(300, 200),
        // new Vector2f(50, 50)));
        // newGameObject.addComponent(spriteRenderer);
        // addGameObject(newGameObject);
        super.init();
    }


    private class SpriteAndIndex {
        public Spritesheet spritesheet;
        public int index;
    }
    private SpriteAndIndex getRandomSpriteAndIndex() {
        int random = (int) (Math.random() * 4);
        Spritesheet newSpritesheet = null;

        switch (random) {
            case 0:
                newSpritesheet = spritesheet;
                break;
            case 1:
                newSpritesheet = spritesheet1;
                break;
            case 2:
                newSpritesheet = spritesheet2;
                break;
            case 3:
                newSpritesheet = spritesheet3;
                break;
            default:
                newSpritesheet = spritesheet;
                break;
        }
        SpriteAndIndex sai = new SpriteAndIndex();
        sai.spritesheet = newSpritesheet;
        sai.index = random;
        return sai;
    }

    float time = 0;
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        time += deltaTime;
        if (time > 0.5) {
            System.out.println("n de game objects: " + gameObjects.size() + " FPS: " + 1 / deltaTime);

            time = 0;
        }

        if (MouseListener.isMouseButtonDown(1)) {
            GameObject newGameObject = new GameObject("Teste", new Transform(
                    Camera.getWorldMousePosition(),
                    new Vector2f(
                        (int) (Math.random() * 100) + 20, 
                        (int) (Math.random() * 100) + 20)));
            SpriteAndIndex sai = getRandomSpriteAndIndex();
            newGameObject.addComponent(new SpriteRenderer(sai.spritesheet.getSprite(0), sai.index));
            addGameObject(newGameObject);
        }

        if (MouseListener.isMouseButtonDown(0)) {
            Scene.goToScene(0);
        }
    }
}
