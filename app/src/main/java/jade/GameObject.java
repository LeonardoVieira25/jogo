package jade;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private String name;
    private List<Component> components = new ArrayList<Component>();
    public Transform transform;
    public GameObject(String name) {
        System.out.println("GameObject created!");
        this.name = name;
        transform = new Transform();
    }
    public GameObject(String name, Transform transform) {
        // System.out.println("GameObject created!");
        // System.out.println(transform);
        this.name = name;
        this.transform = transform;
    }
    
    public String getName() {
        return name;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component component : components) {
            if (componentClass.isInstance(component)) {
                try {
                    return componentClass.cast(component);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    throw new ClassCastException("Error: erro ao achar o componente");
                }
            }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            if (componentClass.isInstance(components.get(i).getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component component) {
        components.add(component);
        component.gameObject = this;
    }

    public void update(float dt) {
        for (Component component : components) {
            component.update(dt);
        }
    }

    public void start() {
        for (Component component : components) {
            component.start();
        }
    }
    
}
