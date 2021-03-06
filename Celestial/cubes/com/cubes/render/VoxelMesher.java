package com.cubes.render;

/**
 * 
 * Copyright (c) 2014, Nicholas Minkler
 * 
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 *      are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, 
 *      this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *      this list of conditions and the following disclaimer in the documentation 
 *      and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors 
 *      may be used to endorse or promote products derived from this software 
 *      without specific prior written permission.
 *
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */

import com.cubes.Block.Face;
import com.cubes.BlockChunkControl;
import com.cubes.BlockSkin;
import com.cubes.BlockSkin_TextureLocation;
import com.cubes.BlockTerrainControl;
import com.cubes.Vector3i;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import java.util.ArrayList;

public abstract class VoxelMesher {
	protected BlockChunkControl terrain;
	private boolean isTransparent;
	
    public abstract Mesh generateMesh(BlockChunkControl terrain, int chunkSize, boolean isTransparent);
    
    protected Mesh genMesh(ArrayList<Vector3f> vertexList, ArrayList<Vector2f> textCoordsList, ArrayList<Integer> indicesList, ArrayList<Float> normalsList) {
        // Dump all of the Data into buffers on a Mesh
        Mesh mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertexList.toArray(new Vector3f[vertexList.size()])));
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(textCoordsList.toArray(new Vector2f[textCoordsList.size()])));
        int[] indices = new int[indicesList.size()];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = indicesList.get(i);
        }
        mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(indices));
        if (normalsList != null) {
            float[] normals = new float[normalsList.size()];
            for (int i = 0; i < normals.length; i++) {
                normals[i] = normalsList.get(i);
            }
            mesh.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(normals));
        }
        mesh.updateBound();
        mesh.setStatic();
        return mesh;
    }

    /**
     * Dumps information for rendering a quad of the given width/height into the given lists
     * @param verts
     * @param textureCoords
     * @param indices
     * @param normals
     * @param bottomLeft
     * @param topLeft
     * @param topRight
     * @param bottomRight
     * @param width
     * @param height
     * @param face
     * @param skin 
     */
    protected void writeQuad(ArrayList<Vector3f> verts, ArrayList<Integer> indices, ArrayList<Float> normals,
            Vector3f bottomLeft, Vector3f topLeft, Vector3f topRight, Vector3f bottomRight, Face face) {

        // Get current vertex count, and add indices to the index list
        int vertCount = verts.size();
        indices.add(vertCount + 2);
        indices.add(vertCount + 0);
        indices.add(vertCount + 1);
        indices.add(vertCount + 1);
        indices.add(vertCount + 3);
        indices.add(vertCount + 2);

        // add the face normals
        normals.addAll(face.getNormals());
        
        // Add the verts in the necessary order
        verts.add(bottomLeft.mult(terrain.getTerrain().getSettings().getBlockSize()));
        verts.add(topLeft.mult(terrain.getTerrain().getSettings().getBlockSize()));
        verts.add(topRight.mult(terrain.getTerrain().getSettings().getBlockSize()));
        verts.add(bottomRight.mult(terrain.getTerrain().getSettings().getBlockSize()));    
    }
    
    /**
     * Write the texture coordinates t othe coordinate list takes into account the texture offset, then calculates what the 4 corners of the u,v should be.
     * @param textureCoords
     * @param terrain
     * @param blockLoc
     * @param face
     * @param width
     * @param height
     * @param skin 
     */
    protected void writeTextureCoords(ArrayList<Vector2f> textureCoords, BlockChunkControl terrain, Vector3i blockLoc, Face face, int width, int height, BlockSkin skin) {
        BlockSkin_TextureLocation textureLocation = skin.getTextureLocation(terrain, blockLoc, face);
        textureCoords.add(getTextureCoordinates(textureLocation, 0, 0));
        textureCoords.add(getTextureCoordinates(textureLocation, 1, 0));
        textureCoords.add(getTextureCoordinates(textureLocation, 0, 1));
        textureCoords.add(getTextureCoordinates(textureLocation, 1, 1));
    }
    
    private static Vector2f getTextureCoordinates(BlockSkin_TextureLocation textureLocation, int xUnitsToAdd, int yUnitsToAdd){
        float textureCount = 16;
        float textureUnit = 1f / textureCount;
        float x = (((textureLocation.getColumn() + xUnitsToAdd) * textureUnit));
        float y = ((((-1 * textureLocation.getRow()) + (yUnitsToAdd - 1)) * textureUnit) + 1);
        return new Vector2f(x, y);
    }
}
