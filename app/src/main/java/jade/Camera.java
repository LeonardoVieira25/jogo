package jade;

import org.joml.Matrix4d;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector4d;

public class Camera {
    private Matrix4d projectionMatrix, viewMatrix;
    private Vector2d position;

    public Camera(Vector2d position) {
        this.position = position;
        this.projectionMatrix = new Matrix4d();
        this.viewMatrix = new Matrix4d();

        adustProjection();
    }

    public void adustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(
                0.0f, // ? distancia do canto esquerdo
                32.0f * 40.0f, // ? distancia do canto direito
                0.0f, // ? distancia do canto inferior
                32.0f * 21.0f, // ? distancia do canto superior
                -1.0f, // ? zNear
                100.0f // ? zFar
        );
    }

    public Matrix4d getViewMatrix() {
        Vector3d cameraFront = new Vector3d(0.0f, 0.0f, -1.0f);
        Vector3d cameraUp = new Vector3d(0.0f, 1.0f, 0.0f);
        this.viewMatrix.identity();
        this.viewMatrix.lookAt(
                new Vector3d(this.position.x, this.position.y, 20.0f), // ? onde a camera esta
                cameraFront.add(this.position.x, this.position.y, 0.0f), // ? para onde a camera esta olhando
                cameraUp // ? vetor que indica a direcao de cima
        );
        return this.viewMatrix;
    }

    public Matrix4d getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public static Vector2f getWorldMousePosition() {
        Vector2d mousePos = new Vector2d(MouseListener.getX(), MouseListener.getY());
        Vector4d clipSpacePos = new Vector4d(
                2.0 * mousePos.x / Window.getWidth() - 1.0,
                1.0 - 2.0 * mousePos.y / Window.getHeight(),
                -1.0,
                1.0);
        Matrix4d invertedProjection = new Matrix4d(Window.getCamera().getProjectionMatrix()).invert();
        Vector4d eyeSpacePos = invertedProjection.transform(clipSpacePos);
        Vector4d worldSpacePos = new Vector4d(eyeSpacePos.x, eyeSpacePos.y, -1.0, 0.0);
        return new Vector2f((float) worldSpacePos.x, (float) worldSpacePos.y);
    }
}
