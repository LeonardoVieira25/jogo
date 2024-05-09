package jade;

import org.joml.Vector2f;

public class Transform {
    public Vector2f position = new Vector2f(0, 0);
    public Vector2f scale = new Vector2f(1, 1);

    public String toString(){
        return "Transform -> x: " + position.x + " y: " + position.y; 
    }
    public Transform() {
    }

    public Transform(Vector2f position) {
        this.position = position;
    }

    public Transform(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    // public Transform(Vector2f position, Vector2f scale, float rotation) {
    //     this.position = position;
    //     this.scale = scale;
    // }

    public Transform(float x, float y) {
        this.position = new Vector2f(x, y);
    }

    public Transform(float x, float y, float scaleX, float scaleY) {
        this.position = new Vector2f(x, y);
        this.scale = new Vector2f(scaleX, scaleY);
    }

    // public Transform(float x, float y, float scaleX, float scaleY, float rotation) {
    //     this.position = new Vector2f(x, y);
    //     this.scale = new Vector2f(scaleX, scaleY);
    // }

    public Transform(Transform transform) {
        this.position = new Vector2f(transform.position);
        this.scale = new Vector2f(transform.scale);
    }

    public void copy(Transform transform) {
        this.position = new Vector2f(transform.position);
        this.scale = new Vector2f(transform.scale);
    }
}
