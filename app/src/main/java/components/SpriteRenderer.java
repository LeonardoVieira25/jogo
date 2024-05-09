package components;

import org.joml.Vector4f;

import jade.Component;
import renderer.Texture;
import util.AssetPool;

public class SpriteRenderer extends Component {
    
    private Vector4f color = new Vector4f(1.0f, 0.0f, 1.0f, 1.0f);

    // private Texture texture = null;

    private String texturePath = null;

    private Sprite sprite = null;

    public Sprite getSprite() {
        return sprite;
    }
    public String getTexturePath() {
        return texturePath;
    }
    public SpriteRenderer(String texturePath, Vector4f color) {
        this.sprite = AssetPool.getSprite(texturePath);
        
        // this.texture = AssetPool.getTexture(texturePath);
        this.texturePath = texturePath;
        this.color = color;
    }
    public SpriteRenderer(String texturePath) {
        this.sprite = AssetPool.getSprite(texturePath);
        this.texturePath = texturePath;
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.texturePath = sprite.getTexture().getPath();
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    public SpriteRenderer(Vector4f color) {
        this.color = color;
    }

    public Vector4f getColor() {
        return this.color;
    }
    public Texture getTexture() {
        return this.sprite.getTexture();
    }

    @Override
    public void start() {
        // this.texture = new Texture(texturePath); 
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void update(float dt) {
        // TODO Auto-generated method stub
        
        
    }
}
