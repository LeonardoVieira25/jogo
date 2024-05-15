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

    public TextDisplay(
            String text,
            Spritesheet font,
            Transform transform,
            float charWidth,
            float charHeight,
            float charSpacing,
            float padding
            ) {
        super("textDisplay", transform);

        this.charWidth = charWidth;
        this.charHeight = charHeight;
        this.charSpacing = charSpacing;
        this.padding = padding;

        spriteRenderers = new ArrayList<>();
        backgroundRenderer = new SpriteRenderer(new Vector4f(1.0f, 1.0f, 1.0f, 0.2f), -10);
        backgroundRenderer.gameObject = this;
        spriteRenderers.add(backgroundRenderer);
        this.font = font;
        setText(text);
    }

    public TextDisplay(String text, Spritesheet font, Transform transform) {
        super("textDisplay", transform);
        this.transform = transform;

        spriteRenderers = new ArrayList<>();
        backgroundRenderer = new SpriteRenderer(new Vector4f(0.0f, 0.0f, 0.0f, 1.0f), -10);
        backgroundRenderer.gameObject = this;
        spriteRenderers.add(backgroundRenderer);
        this.font = font;
        setText(text);
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

            if (
                padding + 
                line.length() * (charWidth + charSpacing) + 
                words[i].length() * (charWidth + charSpacing) + 
                padding    
                    > this.transform.scale.x) {

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

    // TODO : Atualizar o texto
    public void setText(String text) {
        this.text = text;
        List<String> lines = getLines();

        if (spriteRenderers == null) {
            spriteRenderers = new ArrayList<>();
            spriteRenderers.add(backgroundRenderer);
        }
        

        float yOffset = this.transform.scale.y - charHeight - padding;

        for (String line : lines) {
            float xOffset = padding;
            for (int i = 0; i < line.length(); i++) {
                SpriteRenderer sr = new SpriteRenderer(
                        font.getSprite(getCharIndex(line.charAt(i))),
                        -10);
                sr.gameObject = this;

                sr.transformOffset = new Vector2f(xOffset, yOffset);
                sr.transformScale = new Vector2f(charWidth, charHeight);
                sr.setDirty(true);

                spriteRenderers.add(sr);
                xOffset += charWidth + charSpacing;
            }
            yOffset -= charHeight - charSpacing;
        }
    }

    @Override
    public List<SpriteRenderer> getSpriteRenderers() {
        return spriteRenderers;
    }

    private int getCharIndex(char c) {
        if (c == ' ' || c == '\n' || c == '\t')
            return 0;
        if (c == '.')
            return 14;
        
        if (c == ',')
            return 12;
        
        return c - ' ';
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
