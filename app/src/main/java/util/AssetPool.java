package util;

import renderer.Shader;
import renderer.Texture;

import java.io.File;
import java.util.Map;
import java.util.HashMap;


public class AssetPool{
    private static Map<String, Shader> shaders = new HashMap<String, Shader>();
    private static Map<String, Texture> textures = new HashMap<String, Texture>();

    public static Shader getShader(String path){
        File file = new File(path);
        if (shaders.containsKey(file.getAbsolutePath())){
            return shaders.get(file.getAbsolutePath());
        } else {
            Shader shader = new Shader(path);
            shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static Texture getTexture(String path){
        File file = new File(path);
        if (textures.containsKey(file.getAbsolutePath())){
            return textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture(path);
            textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }
}