#type vertex
#version 330 core

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec4 aColor;
layout(location = 2) in vec2 aUV;
layout(location = 3) in float aTextureIndex;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fUV;


void main() {
    // gl_Position = uProjection * uView * (vec4(aPos, 1.0) + vec4(sin(uTime), cos(uTime), 0.0, 0.0) * 100);
    gl_Position = uProjection * uView * vec4(aPos, 1.0);
    fColor = aColor;
    fUV = aUV;
}

#type fragment
#version 330 core

// uniform sampler2D texture_sampler[8];
uniform sampler2D texture_sampler;

in vec4 fColor;
in vec2 fUV;

// vec2 texSize = vec2(4*16.0, 4*16.0);
// vec2 pixelUV = floor(fUV * texSize + 1) / texSize;
vec2 pixelUV = fUV;


out vec4 color;

void main() {

    float isTexture = step(0.0, fUV.x) * step(0.0, fUV.y);
    color = mix(fColor, texture(texture_sampler, pixelUV) * fColor, isTexture);

}
