package jade;

import static org.lwjgl.opengl.GL11.GL_TEXTURE;

import org.joml.Vector2d;

import renderer.Shader;
import renderer.Texture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0; // Add this import statement
import static org.lwjgl.opengl.GL13.glActiveTexture;

public abstract class Scene {

    protected static float[] sceneFillColor = new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
    protected static boolean switchingScene = false;
    private static int targetScene = -1;
    public static float animationTime = 0.5f;
    private static float time = 0.0f;
    /*
     * -1: entrando
     * 0: Running
     * 1: saindo
     */
    private static int state = -1;


    private Shader shader = null;
    protected Camera camera = null;

    private Texture texture = null;


    public void init() {
        shader = new Shader("assets/shaders/default.glsl");
        this.camera = new Camera(new Vector2d(0,0));
        shader.setCamera(this.camera);

        texture = new Texture("assets/sprites/gato_de_terno.jpg");
        
        
    }

    public Scene() {
        Window.fillColor[0] = sceneFillColor[0];
        Window.fillColor[1] = sceneFillColor[1];
        Window.fillColor[2] = sceneFillColor[2];
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
        if (switchingScene) {
            return;
        }
        state = -1;
        switchingScene = true;
        targetScene = index;
    }

    public void update(float deltaTime) {

        shader.use();

        shader.uploadTexture("tex", 0);
        glActiveTexture(GL_TEXTURE0);
        texture.bind();
        
        shader.render();
        
        shader.detach();

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
