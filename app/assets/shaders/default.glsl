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
out float fTextureIndex;

void main() {
    // gl_Position = uProjection * uView * (vec4(aPos, 1.0) + vec4(sin(uTime), cos(uTime), 0.0, 0.0) * 100);
    gl_Position = uProjection * uView * vec4(aPos, 1.0);
    fColor = aColor;
    fUV = aUV;
    fTextureIndex = aTextureIndex;
}

#type fragment
#version 330 core

uniform sampler2D texture_sampler[8];

in vec4 fColor;
in vec2 fUV;
in float fTextureIndex;

vec2 texSize = vec2(32.0, 32.0);
// vec2 pixelUV = floor(fUV * texSize + 1) / texSize;
vec2 pixelUV = fUV;


out vec4 color;

void main() {
    // color = vec4(fUV, 0.0, 1.0);

    if(fTextureIndex >= 0.0) {
        color = texture(texture_sampler[int(fTextureIndex)], pixelUV);
    } else {
        color = fColor;
    }

    // color = fColor;
}
