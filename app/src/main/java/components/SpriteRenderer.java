package components;

import org.joml.Vector4f;

import jade.Component;
import renderer.Texture;
import util.AssetPool;

public class SpriteRenderer extends Component {
    
    private Vector4f color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    private Texture texture = null;

    private String texturePath = null;

    public String getTexturePath() {
        return texturePath;
    }
    public SpriteRenderer(String texturePath, Vector4f color) {
        this.texture = AssetPool.getTexture(texturePath);
        this.texturePath = texturePath;
        this.color = color;
    }
    public SpriteRenderer(String texturePath) {
        // this.texture = new Texture(texturePath);
        this.texture = AssetPool.getTexture(texturePath);
        this.texturePath = texturePath;
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
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
