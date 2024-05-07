package renderer;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer; // Import the ByteBuffer class

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load; // Import the stbi_load method

public class Texture {
    private String path;
    private int textureID;

    public Texture(String path) {
        this.path = path;

        // ? gera uma textura na gpu
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(
                GL_TEXTURE_2D,
                GL_TEXTURE_WRAP_S, // ? o que fazer quando a textura for menor que o objeto
                GL_REPEAT // ? repete a textura
        );
        glTexParameteri(
                GL_TEXTURE_2D,
                GL_TEXTURE_WRAP_T, // ? o que fazer quando a textura for menor que o objeto
                GL_REPEAT // ? repete a textura
        );

        glTexParameteri(
                GL_TEXTURE_2D,
                GL_TEXTURE_MIN_FILTER, // ? o que fazer quando esticar a textura
                GL_NEAREST // ? pega o pixel mais proximo
        );

        glTexParameteri(
                GL_TEXTURE_2D,
                GL_TEXTURE_MAG_FILTER, // ? o que fazer quando encolher a textura
                GL_NEAREST // ? pega o pixel mais proximo
        );

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1); // ? rgb ou rgba

        ByteBuffer data = stbi_load(path, width, height, channels, 0);

        if (data != null) {
            if (channels.get(0) == 3) { //* RGB
                glTexImage2D( // ? upload da textura para a gpu
                        GL_TEXTURE_2D,
                        0, // ? mipmap level?
                        GL_RGB, // ? formato da textura (estamos assumindo que é rgba)
                        width.get(), // ? largura da textura
                        height.get(), // ? altura da textura
                        0,
                        GL_RGB, // ? formato da textura
                        GL_UNSIGNED_BYTE, // ? tipo do buffer
                        data);
            }else if (channels.get(0) == 4) { //* RGBA
                glTexImage2D( // ? upload da textura para a gpu
                        GL_TEXTURE_2D,
                        0, // ? mipmap level?
                        GL_RGBA, // ? formato da textura (estamos assumindo que é rgba)
                        width.get(), // ? largura da textura
                        height.get(), // ? altura da textura
                        0,
                        GL_RGBA, // ? formato da textura
                        GL_UNSIGNED_BYTE, // ? tipo do buffer
                        data);
            } else {
                throw new RuntimeException("Error: (Texture) Sei la que textura é essa?: " + channels.get(0));
            }
        } else {
            throw new RuntimeException("Error loading texture " + path + "\n"
                    + stbi_load(path, width, height, channels, 0) + "\n" + "channels: " + channels.get(0) + "\n"
                    + "width: " + width.get(0) + "\n" + "height: " + height.get(0) + "\n");
        }
        stbi_image_free(data);

    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
