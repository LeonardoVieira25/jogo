package jade;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;

import java.util.ArrayList;

public class TextDisplay extends GameObject {
    private String text;
    private List<Integer> charIndices;
    private Spritesheet font;
    private SpriteRenderer backgroundRenderer;
    private List<SpriteRenderer> spriteRenderers;

    public float charWidth = 32;
    public float charHeight = 32;
    public float charSpacing = 0;
    public float padding = 10;
    private int n_sprites = 0;
    private int backgroundOffsetIndex = 1;
    private int n_columns = 0;

    public TextDisplay(
            String text,
            Spritesheet font,
            Transform transform,
            float charWidth,
            float charHeight,
            float charSpacing,
            float padding) {
        super("textDisplay", transform);

        this.charWidth = charWidth;
        this.charHeight = charHeight;
        this.charSpacing = charSpacing;
        this.padding = padding;

        

        spriteRenderers = new ArrayList<>();

        backgroundRenderer = new SpriteRenderer(new Vector4f(1.0f, 1.0f, 1.0f, 0.2f), -10);
        backgroundRenderer.gameObject = this;
        backgroundRenderer.setDirty(true);
        spriteRenderers.add(backgroundRenderer);
        
        this.backgroundOffsetIndex = spriteRenderers.size();

        this.n_sprites = (int)(
            ((transform.scale.x - 2 * padding) / (charWidth + charSpacing)) *
            ((transform.scale.y - 2 * padding) / (charHeight + charSpacing))
            );

        this.n_columns = (int) ((transform.scale.x - 2 * padding) / (charWidth + charSpacing));


        this.font = font;
        initDisplay();
        setText(text);
    }

    private void initDisplay() {
        float xOffset = padding;
        float yOffset = transform.scale.y - charHeight - padding;
        for (int i = 0; i < n_sprites; i++) {
            SpriteRenderer sr = new SpriteRenderer(font.getSprite(1), -10);
            sr.gameObject = this;
            sr.transformOffset = new Vector2f(xOffset, yOffset);
            xOffset += charWidth + charSpacing;
            if (xOffset + charWidth + padding > transform.scale.x) {
                xOffset = padding;
                yOffset -= charHeight + charSpacing;
            }
            sr.transformScale = new Vector2f(charWidth, charHeight);
            sr.setDirty(true);
            spriteRenderers.add(sr);
        }
    }

    public void setBackgroundColor(Vector4f color) {
        backgroundRenderer.setColor(color);
    }

    private List<String> getLines() {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String line = "";
        float xOffset = padding;

        for (int i = 0; i < words.length; i++) {

            if (words[i].equals("\n")) {
                lines.add(line);
                line = "";
                xOffset = padding;
                continue;
            }

            if (padding +
                    line.length() * (charWidth + charSpacing) +
                    words[i].length() * (charWidth + charSpacing) +
                    padding > this.transform.scale.x) {

                lines.add(line);
                line = "";

                xOffset = padding;
                i--;
                continue;
            }

            if (xOffset + words[i].length() * (charWidth + charSpacing) + padding > this.transform.scale.x) {
                lines.add(line);
                line = "";

                xOffset = padding;
                System.out.println("Break");
            }

            line += words[i] + " ";
            xOffset += words[i].length() * (charWidth + charSpacing);
        }
        lines.add(line);
        return lines;
    }

    public void setText(String text) {
        this.text = text;
        List<String> lines = getLines();

        // * apaga o texto da tela
        for (int i = this.backgroundOffsetIndex; i < spriteRenderers.size(); i++) {
            spriteRenderers.get(i).setSprite(font.getSprite(0));
            spriteRenderers.get(i).setDirty(true);
        }
        

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            
            for (int j = 0; j < line.length(); j++) {
                if (j >= n_columns) {
                    break;
                }

                int spriteIndex = i * n_columns + j;
                SpriteRenderer sr = spriteRenderers.get(spriteIndex + this.backgroundOffsetIndex);

                sr.setSprite(font.getSprite(getCharIndex(line.charAt(j), false)));
            }
        }
    }

    @Override
    public List<SpriteRenderer> getSpriteRenderers() {
        return spriteRenderers;
    }

    private int getCharIndex(int charCode, boolean isSpecialChar) {
        if (!isSpecialChar) {
            if (charCode == ' ' || charCode == '\n' || charCode == '\t')
            return 0;
            return charCode - ' ';
        }
        System.out.println("charCode: " + (char) charCode + " " + charCode);
        if (charCode == 195) {
            return 99;
        }
        if (charCode == 194) {
            return 100;
        }
        return 1;
    }

    public List<Sprite> getSprites() {
        List<Sprite> sprites = new ArrayList<>();
        for (int i = 0; i < charIndices.size(); i++) {
            Sprite sprite = font.getSprite(charIndices.get(i));
            sprites.add(sprite);
        }
        return sprites;
    }

    @Override
    public void update(float dt) {
        // TODO Auto-generated method stub

    }
}
