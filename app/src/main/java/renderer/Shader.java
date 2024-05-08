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
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4d;
import org.lwjgl.BufferUtils;

import jade.Window;
import util.Time;

public class Shader {
    private String filepath;

    private int vertexID;
    private int fragmentID;
    private int programID;

    private boolean beingUsed = false;

    private int vaoID, vboID, eboID;

    // private float[] vertexArray = {
    //         // positions--------- colors----------------- uv coordinates
    //         400.0f, 100.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1, 0, // Bottom right 0
    //         100.0f, 400.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0, 1, // Top left 1
    //         400.0f, 400.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1, 1, // Top right 2
    //         100.0f, 100.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0, 0 // Bottom left 3
    // };
    // private int[] elementArray = {
    //         2, 1, 0, // top right triangle
    //         0, 1, 3 // bottom left triangle
    // };

    public Shader(String filepath) {
        
        this.filepath = filepath;

        String source = readFile(filepath);

        String vertexShaderSrc = source.split("#type vertex")[1].split("#type fragment")[0];
        String fragmentShaderSrc = source.split("#type fragment")[1];

        this.vertexID = compile(GL_VERTEX_SHADER, vertexShaderSrc);
        this.fragmentID = compile(GL_FRAGMENT_SHADER, fragmentShaderSrc);

        this.programID = createProgram();

        // sendBuffers(vertexArray, elementArray);
    }

    // public void setCamera(Camera camera) {
    //     System.out.println("set camera");
    //     this.camera = camera;
    // }

    public int getShaderProgram() {
        return this.programID;
    }

    private String readFile(String filepath) {
        String source = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line = "";
            while ((line = reader.readLine()) != null) {
                source += line + "\n";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return source;
    }

    private int compile(int type, String source) {
        int ID = glCreateShader(type);
        glShaderSource(ID, source);
        glCompileShader(ID);

        int success = glGetShaderi(ID, GL_COMPILE_STATUS);
        if (success == 0) {
            int length = glGetShaderi(ID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: " + this.filepath + " shader compilation type " + type + " failed.");
            System.out.println(glGetShaderInfoLog(ID, length));

            throw new IllegalStateException("Vertex shader failed to compile");
        }

        return ID;
    }

    private int createProgram() {
        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        int success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == 0) {
            int length = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: " + this.filepath + " linking failed.");
            System.out.println(glGetProgramInfoLog(shaderProgram, length));

            throw new IllegalStateException("Shader program failed to link");
        }

        return shaderProgram;
    }

    public void use() {
        if (!beingUsed) {
            glUseProgram(this.programID);
            beingUsed = true;
        }
    }

    public void detach() {
        if (beingUsed) {
            glUseProgram(0);
            beingUsed = false;
        }
    }

    public void sendBuffers(float[] vertexArray, int[] elementArray) {
        System.out.println("Upload de buffers");

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        int positionsSize = 3;
        int colorSize = 4;
        int uvSize = 2;

        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, (positionsSize + colorSize + uvSize) * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, (positionsSize + colorSize + uvSize) * Float.BYTES,
                positionsSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, (positionsSize + colorSize + uvSize) * Float.BYTES,
                (positionsSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    public void render(float[] vertexArray, int nVertices) {
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexArray);
        
        use();
        uploadMatrix4f("uProjection", Window.getCamera().getProjectionMatrix());
        uploadMatrix4f("uView", Window.getCamera().getViewMatrix());
        uploadFloat("uTime", Time.getTime());

        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, nVertices, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
    }

    public void uploadMatrix4f(String name, Matrix4d matrix) {
        use();
        int location = glGetUniformLocation(this.programID, name); // ? pega a posição da variável uniforme na GPU
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); // ? 4x4 matrix
        matrix.get(matrixBuffer); // ? pega a matriz e coloca no buffer

        glUniformMatrix4fv(
                location, // ? localização da variável uniforme
                false, // ? se a matriz deve ser transposta?
                matrixBuffer // ? o buffer que vai ser enviado para a GPU
        ); // ? envia a matriz para a GPU
    }

    public void uploadFloat(String name, float value) {
        int location = glGetUniformLocation(this.programID, name);
        use();
        glUniform1f(location, value);
    }

    public void uploadTexture(String name, int slot) {
        int location = glGetUniformLocation(this.programID, name);
        use();
        glUniform1i(location, slot);
    }
}
