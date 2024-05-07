package jade;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;


public class LevelEditorScene extends Scene {




    public LevelEditorScene() {
        Scene.sceneFillColor = new float[] { 0.0f, 0.0f, 1.0f, 0.0f };
        System.out.println("LevelEditorScene");
    }
    
    @Override
    public void init() {
        super.init();
        // camera.setPosition(new Vector2d(100.0f, 0.0f));
    }

    

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (KeyEventListener.isKeyDown(GLFW_KEY_SPACE)) {
            Scene.goToScene(1);
        }
    }
}
