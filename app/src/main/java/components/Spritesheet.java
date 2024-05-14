package components;

import java.util.ArrayList;
import java.util.List;

import renderer.Texture;
import util.AssetPool;

public class Spritesheet {
    private Texture texture;
    private List<Sprite> sprites;

    public Spritesheet(
            String texturePath,
            int columns,
            int rows,
            int numSprites) {

        System.out.println("Loading Spritesheet: " + texturePath);
        this.sprites = new ArrayList<>();

        this.texture = AssetPool.getTexture(texturePath); // aqui que ta o pulo do gato

        float textureWidth = this.texture.getWidth();
        float textureHeight = this.texture.getHeight();

        // int numSpritesX = columns;
        // int numSpritesY = rows;

        int spriteWidth = (int) textureWidth / columns;
        int spriteHeight = (int) textureHeight / rows;

        int currentX = 0;
        // int currentY = (int) textureHeight - spriteHeight;
        int currentY = 0;

        for (int i = 0; i < numSprites; i++) {
            float[][] texCoords = {
                    { currentX / textureWidth, currentY / textureHeight },
                    { (currentX + spriteWidth) / (float) textureWidth, (currentY + spriteHeight) / textureHeight },
                    { (currentX + spriteWidth) / (float) textureWidth, currentY / textureHeight },
                    { currentX / (float) textureWidth, (currentY + spriteHeight) / textureHeight },
            };

            this.sprites.add(new Sprite(this.texture, texCoords));

            currentX += spriteWidth;
            if (currentX >= textureWidth) {
                currentX = 0;
                currentY += spriteHeight;
            }
        }

    }

    public Sprite getSprite(int index) {
        return this.sprites.get(index);
    }
}
