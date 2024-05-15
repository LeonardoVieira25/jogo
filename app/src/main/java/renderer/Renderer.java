package renderer;

import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.ArrayList;
import java.util.List;

import components.SpriteRenderer;
import jade.GameObject;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RendererBatch> batches;
    public static List<float[]> debugColors = new ArrayList<float[]>();

    public Renderer() {
        this.batches = new ArrayList<RendererBatch>();
        for (int i = 0; i < 20; i++) {
            debugColors.add(new float[] {
                    (float) (Math.random()),
                    (float) (Math.random()),
                    (float) (Math.random()) });
        }
    }

    public void add(List<GameObject> gameObjects) {
        for (GameObject gameObject : gameObjects) {
            add(gameObject);
        }
    }

    // public void add(GameObject gameObject) {
    // SpriteRenderer spriteRenderer =
    // gameObject.getComponent(SpriteRenderer.class);
    // if (spriteRenderer != null) {
    // add(spriteRenderer);
    // }
    // }
    public void add(GameObject gameObject) {
        List<SpriteRenderer> spriteRenderers = gameObject.getSpriteRenderers();
        if (spriteRenderers != null) {
            for (SpriteRenderer spriteRenderer : spriteRenderers) {
                add(spriteRenderer);
            }
        }
    }

    public void add(SpriteRenderer spriteRenderer) {
        boolean added = false;
        for (RendererBatch batch : batches) {
            // if (batch.hasRoom() && (spriteRenderer.getTexture() == null ||
            // batch.hasTexture(spriteRenderer.getTexture()))){
            if (batch.hasRoom()
                    && (spriteRenderer.getTexture() == null || batch.getTexture() == spriteRenderer.getTexture())) {
                // Texture texture = spriteRenderer.getTexture();
                // if (texture == null || (texture != null && (batch.hasTexture(texture) ||
                // batch.hasTextureRoom()))) {
                // }
                added = true;
                batch.addSprite(spriteRenderer);
                break;
            }
        }
        if (!added) {
            RendererBatch newRenderBatch = new RendererBatch(MAX_BATCH_SIZE, spriteRenderer.getZIndex()
            // ,debugColors.get((int) (Math.random() * debugColors.size())) //! descometar
            // essa linha para visualizar os batchs
            );

            // RendererBatch newRenderBatch = new RendererBatch(MAX_BATCH_SIZE);
            newRenderBatch.setTexture(spriteRenderer.getTexture());
            newRenderBatch.start();
            newRenderBatch.addSprite(spriteRenderer);
            this.batches.add(newRenderBatch);

            this.batches.sort((RendererBatch a, RendererBatch b) -> {
                return b.getZIndex() - a.getZIndex();
            });
        }
    }

    public void remove(GameObject gameObject) {
        SpriteRenderer spriteRenderer = gameObject.getComponent(SpriteRenderer.class);
        if (spriteRenderer != null) {
            for (RendererBatch batch : batches) {
                if (batch.getTexture() == spriteRenderer.getTexture()) {
                    batch.removeSprite(spriteRenderer);
                    if (batch.getNumSprites() == 0) {
                        batches.remove(batch);
                    }
                    break;
                }
            }
        }
    }

    public void render() {
        for (RendererBatch batch : batches) {
            batch.getTexture().bind();
            glActiveTexture(batch.getTexture().getTextureID());
            batch.render();
        }
    }
}
