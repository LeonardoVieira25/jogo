package jade;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector4f;

import components.SpriteRenderer;
import components.Spritesheet;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {
        Scene.sceneFillColor = new float[] { 0.0f, 0.0f, 1.0f, 0.0f };
        System.out.println("LevelEditorScene");

        Window.getCamera().setPosition(new Vector2d());
    }

    private Spritesheet spritesheet = null;
    private Spritesheet spritesheet2 = null;
    private SpriteRenderer playerSpriteRenderer = null;
    private SpriteRenderer capivaraSpriteRenderer = null;
    private GameObject player = null;

    @Override
    public void init() {
        System.out.println("Criando game objects: ======================================================");
        GameObject newGameObject;

        // spritesheet2 = AssetPool.getSpritesheet("assets/sprites/capivara.png", 2, 2, 4);
        capivaraSpriteRenderer = new SpriteRenderer(new Vector4f(
            1.0f, 1.0f, 1.0f, 1.0f
        ), -1);
        // capivaraSpriteRenderer = new SpriteRenderer(spritesheet2.getSprite(2), 0);
        // capivaraSpriteRenderer.setColor(new Vector4f(1.0f, 1.0f, 1.0f, 1.f));

        newGameObject = new GameObject(
                "capivara",
                new Transform(
                        new Vector2f(200, 0),
                        new Vector2f(100, 100)));
        newGameObject.addComponent(capivaraSpriteRenderer);
        addGameObject(newGameObject);

        spritesheet = AssetPool.getSpritesheet("assets/sprites/spriteseetTeste2.png", 4, 4, 16);
        playerSpriteRenderer = new SpriteRenderer(spritesheet.getSprite(0), 2);
        playerSpriteRenderer.setColor(new Vector4f(1.0f, 1.0f, 1.0f, 1.f));
        newGameObject = new GameObject(
                "player",
                new Transform(
                        new Vector2f(0, 0),
                        new Vector2f(100, 100)));
        newGameObject.addComponent(playerSpriteRenderer);
        player = newGameObject;
        addGameObject(newGameObject);

        TextDisplay textDisplay = new TextDisplay(
                "Essa e a tela de edicao de niveis.",
                AssetPool.getSpritesheet("assets/sprites/fonte.png", 15, 8, 120),
                new Transform(
                        new Vector2f(10, 500),
                        new Vector2f(1000, 70)),
                20,
                25,
                -3,
                20);
                
        textDisplay.setBackgroundColor(new Vector4f(0f, 0f, 0f, 0.8f));

        addGameObject(textDisplay);

        super.init();

        System.out.println("fim. ======================================================");
        System.out.println("GameObjects: " + gameObjects.size());
    }

    @Override
    public void loadResources() {
        super.loadResources();
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

        if (MouseListener.isDragging()) {
            player.transform.position = Camera.getWorldMousePosition();
        }

        if (KeyEventListener.isKeyDown(GLFW_KEY_D)) {
            player.transform.position.add(new Vector2f(deltaTime * 1000, 0));
        }
        if (KeyEventListener.isKeyDown(GLFW_KEY_A)) {
            player.transform.position.add(new Vector2f(-deltaTime * 1000, 0));
        }
        if (KeyEventListener.isKeyDown(GLFW_KEY_W)) {
            player.transform.position.add(new Vector2f(0, deltaTime * 1000));
        }
        if (KeyEventListener.isKeyDown(GLFW_KEY_S)) {
            player.transform.position.add(new Vector2f(0, -deltaTime * 1000));
        }

        if (KeyEventListener.isKeyDown(GLFW_KEY_0)) {
            playerSpriteRenderer.setSprite(spritesheet.getSprite(0));
            capivaraSpriteRenderer.setSprite(spritesheet2.getSprite(0));
        }
        if (KeyEventListener.isKeyDown(GLFW_KEY_1)) {
            playerSpriteRenderer.setSprite(spritesheet.getSprite(1));
            capivaraSpriteRenderer.setSprite(spritesheet2.getSprite(1));
        }
        if (KeyEventListener.isKeyDown(GLFW_KEY_2)) {
            playerSpriteRenderer.setSprite(spritesheet.getSprite(2));
            capivaraSpriteRenderer.setSprite(spritesheet2.getSprite(2));
        }
        if (KeyEventListener.isKeyDown(GLFW_KEY_3)) {
            playerSpriteRenderer.setSprite(spritesheet.getSprite(3));
            capivaraSpriteRenderer.setSprite(spritesheet2.getSprite(3));
        }

        if (KeyEventListener.isKeyDown(GLFW_KEY_SPACE)) {
            Scene.goToScene(1);
        }
        for (GameObject go : gameObjects) {
            go.update(deltaTime);
        }
    }
}
