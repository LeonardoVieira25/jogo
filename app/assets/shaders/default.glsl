#type vertex
#version 330 core

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec4 aColor;
layout(location = 2) in vec2 aUV;

uniform mat4 uProjection;
uniform mat4 uView;

uniform float uTime;

out vec4 fColor;
out vec2 fUV;

void main() {
    gl_Position = uProjection * uView * (vec4(aPos, 1.0) + vec4(sin(uTime), cos(uTime), 0.0, 0.0) * 100);
    fColor = aColor;
    fUV = aUV;
}

#type fragment
#version 330 core

uniform sampler2D texture_sampler;

in vec4 fColor;
in vec2 fUV;

uniform float uTime;

vec2 texSize = vec2(32.0, 32.0) * sin(uTime) * 0.5 + 32.0;
vec2 pixelUV = floor(fUV * texSize + 1) / texSize;
// vec2 pixelUV = fUV;

out vec4 color;

void main() {
    color = texture( //? Pegar a cor da textura
    texture_sampler, //? Textura
    pixelUV //? posição do pixel na textura
    * vec2(1.0, -1.0) //? Inverter coordenada y
    );
}
