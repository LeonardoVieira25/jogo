package components;

import org.joml.Vector4f;

import jade.Component;
import jade.Transform;
import renderer.Texture;
import util.AssetPool;

public class SpriteRenderer extends Component {

    private Vector4f color = new Vector4f(1.0f, 0.0f, 1.0f, 1.0f);

    private String texturePath = null;

    private Sprite sprite = null;

    private boolean isDirty = true;

    private Transform lastTransform = null;

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public SpriteRenderer(String texturePath, Vector4f color) {
        this.isDirty = true;
        this.sprite = AssetPool.getSprite(texturePath);
        this.texturePath = texturePath;
        this.color = color;
    }

    public SpriteRenderer(String texturePath) {
        this.isDirty = true;
        this.sprite = AssetPool.getSprite(texturePath);
        this.texturePath = texturePath;
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public SpriteRenderer(Sprite sprite) {
        this.isDirty = true;
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
        // lastTransform.copy(gameObject.transform);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        isDirty = true;
    }

    public void setColor(Vector4f color) {
        this.color = color;
        isDirty = true;
    }

    public void update(float dt) {
        
        if (isDirty) {
            return;
        }
        if (lastTransform == null) {
            lastTransform = new Transform();
            return;
        }

        if (lastTransform.position.x != gameObject.transform.position.x ||
                lastTransform.position.y != gameObject.transform.position.y ||
                lastTransform.scale.x != gameObject.transform.scale.x ||
                lastTransform.scale.y != gameObject.transform.scale.y) {
            isDirty = true;
            lastTransform.copy(gameObject.transform);
        }

    }
}
