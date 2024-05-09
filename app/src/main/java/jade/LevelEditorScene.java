package jade;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
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
        // int cols = width / (w + gap);
        // int rows = height / (h + gap);

        System.out.println("Criando game objects: ======================================================");
        GameObject newGameObject;

        spritesheet = AssetPool.getSpritesheet("assets/sprites/spriteseetTeste.png", 4, 4, 16);
        playerSpriteRenderer = new SpriteRenderer(spritesheet.getSprite(0));

        newGameObject = new GameObject(
                "player",
                new Transform(
                        new Vector2f(0, 0),
                        new Vector2f(100, 100)));
        newGameObject.addComponent(playerSpriteRenderer);
        player = newGameObject;
        addGameObject(newGameObject);

        spritesheet2 = AssetPool.getSpritesheet("assets/sprites/capivara.png", 2, 2, 4);
        capivaraSpriteRenderer = new SpriteRenderer(spritesheet2.getSprite(2));
        capivaraSpriteRenderer.setColor(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));

        newGameObject = new GameObject(
                "capivara",
                new Transform(
                        new Vector2f(200, 0),
                        new Vector2f(100, 100)));
        newGameObject.addComponent(capivaraSpriteRenderer);
        addGameObject(newGameObject);

        System.out.println("fim. ======================================================");
        System.out.println("GameObjects: " + gameObjects.size());

        super.init();
    }

    @Override
    public void loadResources() {
        super.loadResources();
        // AssetPool.getShader("assets/shaders/default.glsl");
        // AssetPool.getTexture("assets/sprites/capivara.png");
        // AssetPool.getTexture("assets/sprites/gato_de_terno.jpg");
        // AssetPool.getSpritesheet("assets/sprites/capivara.png", 1, 1, 1);
        // AssetPool.getSpritesheet("assets/sprites/gato_de_terno.jpg", 1, 1, 1);
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

        if(MouseListener.isDragging()) {
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
        if (KeyEventListener.isKeyDown(GLFW_KEY_ENTER)) {
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
