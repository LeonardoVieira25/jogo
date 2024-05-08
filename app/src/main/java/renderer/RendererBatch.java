package renderer;

import org.joml.Vector4f;

import components.SpriteRenderer;

public class RendererBatch {
    /*
     * Vertex:
     * |position ===========|Color ====================|UV =====|
     * |float, float, float |float, float, float, float|int, int|
     * |====================|==========================|========|
     */
    private final int POSITION_SIZE = 3;
    private final int COLOR_SIZE = 4;
    private final int UV_SIZE = 2;

    private final int VERTEX_SIZE = POSITION_SIZE + COLOR_SIZE + UV_SIZE;

    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;

    private Shader shader;

    private int maxBatchSize;

    public RendererBatch(int maxBatchSize) {
        this.maxBatchSize = maxBatchSize;

        this.shader = new Shader("assets/shaders/default.glsl");

        this.sprites = new SpriteRenderer[this.maxBatchSize];
        this.vertices = new float[this.maxBatchSize * 4 * VERTEX_SIZE];

        this.numSprites = 0;
        this.hasRoom = true;
    }

    public void start() {}

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
        this.sprites[index] = sprite;
        this.numSprites++;

        loadVertexProperties(index);

        if (this.numSprites >= this.maxBatchSize) {
            this.hasRoom = false;
        }
    }

    private void loadVertexProperties(int index) {

        SpriteRenderer sprite = this.sprites[index];

        int offset = index * 4 * VERTEX_SIZE;

        Vector4f color = sprite.getColor();

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

            this.vertices[offset + 0] = sprite.gameObject.transform.position.x
                    + (xAdd * sprite.gameObject.transform.scale.x);
            this.vertices[offset + 1] = sprite.gameObject.transform.position.y
                    + (yAdd * sprite.gameObject.transform.scale.y);

            this.vertices[offset + 3] = color.x;
            this.vertices[offset + 4] = color.y;
            this.vertices[offset + 5] = color.z;
            this.vertices[offset + 6] = color.w;

            this.vertices[offset + 7] = coisas[i][0];
            this.vertices[offset + 8] = coisas[i][1];

            offset += VERTEX_SIZE;
        }
    }

    public boolean hasRoom() {
        return this.hasRoom;
    }

    private boolean firstRender = true;

    public void render() {
        if (firstRender) {
            firstRender = false;
            shader.sendBuffers(vertices, generateIndeces());
            return;
        }
        shader.render(this.vertices, numSprites * 6);

    }
}
