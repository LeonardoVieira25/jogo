package components;

import jade.Component;
import renderer.Texture;

public class Sprite extends Component {
    private Texture texture;
    private float[][] texCoords;

    public Sprite(Texture texture) {
        this.texture = texture;
        // int[][] texCoords = {
        //         { 1, 0 },
        //         { 0, 1 },
        //         { 1, 1 },
        //         { 0, 0 }
        // };
        float[][] texCoords = {
            { 0, 0 },
            { 1, 1 },
            { 1, 0 },
            { 0, 1 },
        };
        this.texCoords = texCoords;
    }

    public Sprite(Texture texture, float[][] texCoords) {
        this.texture = texture;
        
        if(texCoords.length != 4 || texCoords[0].length != 2) {
            throw new IllegalArgumentException("texCoords must be a 4x2 array");
        }
        // this.texCoords = texCoordsTeste;

        this.texCoords = texCoords;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public float[][] getTexCoords() {
        return this.texCoords;
    }

    public void start() {
    }

    public void update(float dt) {
    }
}
