package components;

import java.util.List;
import java.util.ArrayList;

import jade.Component;


public class TextDisplay extends Component{
    private String text;
    private List<Integer> charIndices;
    private Spritesheet font;

    public TextDisplay(String text, Spritesheet font) {
        setText(text);
        this.font = font;
    }

    private void setText(String text) {
        this.text = text;
        charIndices = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            // charIndices.add(getCharIndex(text.charAt(i)));
            charIndices.add(getCharIndex(text.charAt(i)));
        }
    }

    private int getCharIndex(char c) {
        if (c == ' ' || c == '\n' || c == '\t')
            return 0;
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
