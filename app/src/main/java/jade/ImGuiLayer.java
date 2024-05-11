package jade;

import imgui.ImGui;

public class ImGuiLayer {
    
    public ImGuiLayer() {
    }
    public void imgui() {
        ImGui.begin("Hello, world!");


        ImGui.text("This is some useful text.");

        ImGui.end();
    }

}