package util;

import renderer.Shader;
import renderer.Texture;

import java.io.File;
import java.util.Map;

import components.Sprite;
import components.Spritesheet;

import java.util.HashMap;


public class AssetPool{
    private static Map<String, Shader> shaders = new HashMap<String, Shader>();
    private static Map<String, Texture> textures = new HashMap<String, Texture>();
    private static Map<String, Sprite> sprites = new HashMap<String, Sprite>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<String, Spritesheet>();

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

    public static Sprite getSprite(String path){
        File file = new File(path);
        if (sprites.containsKey(file.getAbsolutePath())){
            return sprites.get(file.getAbsolutePath());
        } else {
            Sprite sprite = new Sprite(AssetPool.getTexture(path));
            sprites.put(file.getAbsolutePath(), sprite);
            return sprite;
        }
    }

    public static Spritesheet getSpritesheet(String path, int columns, int rows, int numSprites){
        File file = new File(path);
        if (spritesheets.containsKey(file.getAbsolutePath())){
            return spritesheets.get(file.getAbsolutePath());
        } else {
            Spritesheet spritesheet = new Spritesheet(file.getAbsolutePath(), columns, rows, numSprites);
            spritesheets.put(file.getAbsolutePath(), spritesheet);
            return spritesheet;
        }
    }

    public static void clear(){
        System.out.println("Clearing AssetPool");
        shaders.clear();
        for (Texture texture : textures.values()){
            texture.cleanup();
        }
        textures.clear();
        sprites.clear();
        spritesheets.clear();

    }

    
}