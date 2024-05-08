package renderer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector4d;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import components.SpriteRenderer;
import jade.Scene;
import jade.Window;
import util.Time;

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

    private final int POSITION_OFFSET = 0;
    // private final int COLOR_OFFSET = POSITION_OFFSET + POSITION_SIZE *
    // Float.BYTES;

    // TODO enviar o uv

    private final int VERTEX_SIZE = POSITION_SIZE + COLOR_SIZE + UV_SIZE;
    // private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;

    // private int vaoID, vboID, eboID;

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

    public void start() {

    }

    // private float[] vertexArrayTeste = {
    // // positions--------- colors----------------- uv coordinates
    // 200.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1, 0, // Bottom right 0
    // 0.0f, 100.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0, 1, // Top left 1
    // 100.0f, 100.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1, 1, // Top right 2
    // 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0, 0 // Bottom left 3
    // };
    // private int[] elementArrayTeste = {
    // 2, 1, 0, // top right triangle
    // 0, 1, 3 // bottom left triangle
    // };
    private int[] generateIndeces() {
        int[] elements = new int[6 * this.maxBatchSize];
        for (int i = 0; i < this.maxBatchSize; i++) {
            loadElementIndices(elements, i);
        }

        // int[] cola = { 2, 1, 0, 0, 1, 3 };
        // for (int i = 0; i < 6; i++) {
        //     elements[i] = cola[i];
        //     System.out.print(elements[i] + " ");
        // }
        // System.out.println(" ");

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

            // float xAdd = 1.0f;
            // float yAdd = 1.0f;
            // for (int i = 0; i < 4; i++) {
            // if (i == 1) {
            // yAdd = 0.0f;
            // } else if (i == 2) {
            // xAdd = 0.0f;
            // } else if (i == 3) {
            // yAdd = 1.0f;
            // }

            // System.out.println(xAdd + " " + yAdd);

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

        // int i = 0;
        // for (float f : this.vertices) {
        //     i++;
        //     System.out.print(f + " ");
        //     if (i % 8 == 0) {
        //         System.out.println(" ");
        //     }
        //     if (i > 45) {
        //         break;
        //     }
        // }
        // System.out.println(" ");

    }

    public boolean hasRoom() {
        return this.hasRoom;
    }

    private boolean firstRender = true;

    public void render() {
        if (firstRender) {
            firstRender = false;
            shader.sendBuffers(vertices, generateIndeces());
            // System.out.println("Numero de sprites: " + numSprites);
            return;
        }
        shader.render(this.vertices, numSprites * 6);

    }
}
