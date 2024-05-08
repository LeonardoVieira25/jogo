package components;

import org.joml.Vector4f;

import jade.Component;
import renderer.Texture;

public class SpriteRenderer extends Component {
    
    private Vector4f color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    private Texture texture = null;
    private String texturePath = null;

    public String getTexturePath() {
        return texturePath;
    }
    public SpriteRenderer(String texturePath, Vector4f color) {
        // System.out.println("SpriteRenderer created!");
        this.texturePath = texturePath;
        this.color = color;
    }
    public SpriteRenderer(String texturePath) {
        // System.out.println("SpriteRenderer created!");
        this.texturePath = texturePath;
    }
    public SpriteRenderer(Vector4f color) {
        this.color = color;
    }

    public Vector4f getColor() {
        return this.color;
    }
    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public void start() {
        // this.texture = new Texture(texturePath);
        
    }

    public void update(float dt) {
        // TODO Auto-generated method stub
        
        
    }
}
