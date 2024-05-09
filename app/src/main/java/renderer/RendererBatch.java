package renderer;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import components.SpriteRenderer;
import util.AssetPool;
import static org.lwjgl.opengl.GL13.*;

public class RendererBatch {

    private List<Texture> textures;

    /*
     * Vertex:
     * |position ===========|Color ====================|UV =========|TextureIndex|
     * |float, float, float |float, float, float, float|float, float|float |
     * |====================|==========================|============|============|
     */
    private final int POSITION_SIZE = 3;
    private final int COLOR_SIZE = 4;
    private final int UV_SIZE = 2;
    private final int TEXTURE_INDEX_SIZE = 1;

    private final int VERTEX_SIZE = POSITION_SIZE + COLOR_SIZE + UV_SIZE + TEXTURE_INDEX_SIZE;

    private SpriteRenderer[] spriteRenderers;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;

    private Shader shader;

    private int maxBatchSize;

    public RendererBatch(int maxBatchSize) {
        this.maxBatchSize = maxBatchSize;

        this.shader = AssetPool.getShader("assets/shaders/default.glsl");

        this.spriteRenderers = new SpriteRenderer[this.maxBatchSize];
        this.vertices = new float[this.maxBatchSize * 4 * VERTEX_SIZE];

        this.numSprites = 0;
        this.hasRoom = true;

        textures = new ArrayList<>();
    }

    public void start() {
    }

    private int[] generateIndeces() {
        int[] elements = new int[6 * this.maxBatchSize];
        for (int i = 0; i < this.maxBatchSize; i++) {
            loadElementIndices(elements, i);
        }
        return elements;
    }

    private void loadElementIndices(int[] elements, int i) {
        int start = i * 6;
        int offset = i * 4;

        elements[start + 0] = offset + 0;
        elements[start + 1] = offset + 2;
        elements[start + 2] = offset + 3;

        elements[start + 3] = offset + 1;
        elements[start + 4] = offset + 2;
        elements[start + 5] = offset + 3;
    }

    public void addSprite(SpriteRenderer sprite) {
        int index = this.numSprites;
        this.spriteRenderers[index] = sprite;
        this.numSprites++;

        if (sprite.getTexture() != null) {
            if (!textures.contains(sprite.getTexture())) {
                textures.add(sprite.getTexture());
            }
        }
        

        loadVertexProperties(index);

        if (this.numSprites >= this.maxBatchSize) {
            this.hasRoom = false;
        }
    }

    public boolean hasTextureRoom() {
        return textures.size() < 8;
    }
    public boolean hasTexture(Texture texture) {
        return textures.contains(texture);
    }

    private void loadVertexProperties(int index) {

        SpriteRenderer spriteRenderer = this.spriteRenderers[index];

        int offset = index * 4 * VERTEX_SIZE;

        Vector4f color = spriteRenderer.getColor();

        int textureIndex = -1;
        if (spriteRenderer.getTexture() != null) {
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i) == spriteRenderer.getTexture()) {
                    textureIndex = i;
                    break;
                }
            }
        }

        float[][] texCoords = spriteRenderer.getSprite().getTexCoords();

        int[][] coisas = {
                { 1, 0 },
                { 0, 1 },
                { 1, 1 },
                { 0, 0 }
        };
        float xAdd = 1.0f;
        float yAdd = 1.0f;
        for (int i = 0; i < 4; i++) {
            yAdd = coisas[i][0];
            xAdd = coisas[i][1];

            this.vertices[offset + 0] = spriteRenderer.gameObject.transform.position.x
                    + (xAdd * spriteRenderer.gameObject.transform.scale.x);
            this.vertices[offset + 1] = spriteRenderer.gameObject.transform.position.y
                    + (yAdd * spriteRenderer.gameObject.transform.scale.y);

            this.vertices[offset + 2] = 1.0f;

            this.vertices[offset + 3] = color.x;
            this.vertices[offset + 4] = color.y;
            this.vertices[offset + 5] = color.z;
            this.vertices[offset + 6] = color.w;

            this.vertices[offset + 7] = texCoords[i][0];
            this.vertices[offset + 8] = texCoords[i][1];

            this.vertices[offset + 9] = textureIndex;

            offset += VERTEX_SIZE;
        }
    }

    public boolean hasRoom() {
        return this.hasRoom;
    }

    private boolean firstRender = true;

    public void render() {
        loadVertexProperties(0);
        if (firstRender) {
            firstRender = false;

            // for (int i = 0; i < vertices.length && i < 90; i++) {
            //     System.out.print(vertices[i] + " ");
            //     if ((i + 1) % 10 == 0)
            //         System.out.println("");
            // }
            // System.out.println("");

            shader.sendBuffers(vertices, generateIndeces());
            return;
        }

        for (int i = 0; i < textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            textures.get(i).bind();
        }
        
        shader.uploadIntArray("texture_sampler", new int[] { 0, 1, 2, 3, 4, 5, 6, 7 });

        for (int i = 0; i < textures.size(); i++) {
            textures.get(i).unbind();
        }

        shader.render(this.vertices, numSprites * 6);
    }
}
