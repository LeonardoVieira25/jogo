package jade;

public class LevelScene extends Scene {
    
    public LevelScene() {
        Scene.sceneFillColor = new float[] { 0.0f, 1.0f, 0.0f, 0.0f };

        System.out.println("LevelScene");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(MouseListener.isMouseButtonDown(0)) {
            Scene.goToScene(0);
        }
    }
}