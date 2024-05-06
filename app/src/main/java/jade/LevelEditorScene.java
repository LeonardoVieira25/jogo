package jade;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {
        Scene.sceneFillColor = new float[] { 0.0f, 0.0f, 1.0f, 0.0f };
        System.out.println("LevelEditorScene");
    }

    // private float time = 0.0f;
    // private float animationTime = 1.0f;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (KeyEventListener.isKeyDown(GLFW_KEY_SPACE)) {
            Scene.goToScene(1);
        }
    }
}