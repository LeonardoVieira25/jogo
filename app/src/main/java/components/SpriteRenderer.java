package components;

import org.joml.Vector2f;
import org.joml.Vector4f;

import jade.Component;
import jade.Transform;
import renderer.Texture;

public class SpriteRenderer extends Component {

    private Vector4f color = new Vector4f(1.0f, 0.0f, 1.0f, 1.0f);

    private String texturePath = null;

    private Sprite sprite = null;

    private boolean isDirty = true;

    private Transform lastTransform = null;

    public Vector2f transformOffset = new Vector2f(0, 0);
    public Vector2f transformScale = null;

    private int zIndex = 0;
    public int getZIndex() {
        return zIndex;
    }
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

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

    public SpriteRenderer(Sprite sprite, int zIndex) {
        this.zIndex = zIndex;
        this.isDirty = true;
        this.sprite = sprite;
        this.texturePath = sprite.getTexture().getPath();
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public SpriteRenderer(Vector4f color, int zIndex) {
        this.zIndex = zIndex;
        this.isDirty = true;
        this.color = color;
    }
    

    public SpriteRenderer(Sprite sprite) {
        this.isDirty = true;
        this.sprite = sprite;
        this.texturePath = sprite.getTexture().getPath();
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        if (sprite == null) {
            return null;
        }
        return this.sprite.getTexture();
    }

    @Override
    public void start() {
        
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
