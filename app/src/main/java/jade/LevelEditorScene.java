package jade;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

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
    private SpriteRenderer playerSpriteRenderer = null;

    @Override
    public void init() {
        spritesheet = AssetPool.getSpritesheet("assets/sprites/spriteseetTeste.png", 4, 4, 4);
        playerSpriteRenderer = new SpriteRenderer(spritesheet.getSprite(0));

        int width = Window.getWidth();
        int height = Window.getHeight();

        int w = 200;
        int h = 200;
        int gap = 5;

        // int cols = width / (w + gap);
        // int rows = height / (h + gap);


        System.out.println("Criando game objects: ======================================================");
        GameObject newGameObject;
        newGameObject = new GameObject(
                "capivara",
                new Transform(
                        new Vector2f(0, 0),
                        new Vector2f(100, 100)));
        newGameObject.addComponent(playerSpriteRenderer);
        addGameObject(newGameObject);

        // for (int x = 0; x < cols; x++) {
        // for (int y = 0; y < rows; y++) {
        // newGameObject = new GameObject(
        // (x + y) % 2 == 0 ? "capivara" : "gato_de_terno",
        // new Transform(
        // new Vector2f(x * (w + gap), y * (h + gap)),
        // new Vector2f(w, h)));
        // if ((x + y) % 3 == 0) {
        // newGameObject.addComponent(new SpriteRenderer(spritesheet.getSprite(0)));
        // } else {

        // if ((x + y) % 2 == 0) {
        // newGameObject.addComponent(new SpriteRenderer(spritesheet.getSprite(1)));
        // } else {
        // newGameObject.addComponent(new SpriteRenderer(spritesheet.getSprite(2)));
        // }
        // }
        // // newGameObject.addComponent(
        // // new SpriteRenderer(
        // // (x + y) % 3 == 0 ? "assets/sprites/capivara.png" :
        // // "assets/sprites/gato_de_terno.jpg",
        // // // "assets/sprites/gato_de_terno.jpg",
        // // // "assets/sprites/capivara.png",
        // // new Vector4f(1.0f, 1.0f, 1.0f, 1.0f)));
        // addGameObject(newGameObject);
        // }
        // };

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

        if (KeyEventListener.isKeyDown(GLFW_KEY_0)) {
            playerSpriteRenderer.setSprite(spritesheet.getSprite(0));
        }
        if (KeyEventListener.isKeyDown(GLFW_KEY_1)) {
            playerSpriteRenderer.setSprite(spritesheet.getSprite(1));
        }
        if (KeyEventListener.isKeyDown(GLFW_KEY_2)) {
            playerSpriteRenderer.setSprite(spritesheet.getSprite(2));
        }
        if (KeyEventListener.isKeyDown(GLFW_KEY_ENTER)) {
            playerSpriteRenderer.setSprite(spritesheet.getSprite(3));
        }

        if (KeyEventListener.isKeyDown(GLFW_KEY_SPACE)) {
            Scene.goToScene(1);
        }
        for (GameObject go : gameObjects) {
            go.update(deltaTime);
        }
    }
}
