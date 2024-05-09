package renderer;

import java.util.List;

import components.SpriteRenderer;
import jade.GameObject;

import java.util.ArrayList;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RendererBatch> batches;

    public Renderer() {
        this.batches = new ArrayList<RendererBatch>();
    }

    public void add(GameObject gameObject) {
        SpriteRenderer spriteRenderer = gameObject.getComponent(SpriteRenderer.class);
        if (spriteRenderer != null) {
            add(spriteRenderer);
        }
    }

    public void add(SpriteRenderer spriteRenderer) {
        boolean added = false;
        for (RendererBatch batch : batches) {
            if (batch.hasRoom()) {
                Texture texture = spriteRenderer.getTexture();
                if (texture == null || (texture != null && (batch.hasTexture(texture) || batch.hasTextureRoom()))) {
                    added = true;
                    batch.addSprite(spriteRenderer);
                    break;
                }
            }
        }
        if (!added) {
            RendererBatch newRenderBatch = new RendererBatch(MAX_BATCH_SIZE);
            newRenderBatch.start();
            newRenderBatch.addSprite(spriteRenderer);
            this.batches.add(newRenderBatch);
        }
    }

    public void render() {
        for (RendererBatch batch : batches) {
            batch.render();
        }
    }
}
