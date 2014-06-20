package com.cubes.render;

import com.cubes.Block;
import com.cubes.BlockChunkControl;
import com.cubes.BlockSkin;
import com.cubes.BlockType;
import com.jme3.scene.Mesh;

public class NaiveMesher extends VoxelMesher {

	@Override
	public Mesh generateMesh(BlockChunkControl terrain, int chunkSize) {
		ArrayList<Vector3f> verts = new ArrayList<>();
        ArrayList<Vector3f> textCoords = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<Float> normals = new ArrayList<>();
        Vector3f tmpLocation = new Vector3f();
        Vector3i tmpI = new Vector3i();
        for (int val = 0; val < terrain.getBlocks().length; val++) {
        	terrain.vectorFromInt555(val, tmpI);
            tmpLocation.setX(tmpI.getX()).setY(tmpI.getY()).setZ(tmpI.getZ());
            BlockType bt = MaterialManager.getInstance().getType(terrain.getBlocks()[val]);
            if (bt != null) {
                BlockSkin skin = bt.getSkin();
                Vector3f faceLoc_frontBotLeft = tmpLocation.add(Block.FRONT_BOTTOM_LEFT);
                Vector3f faceLoc_frontBotRight = tmpLocation.add(Block.FRONT_BOTTOM_RIGHT);
                Vector3f faceLoc_rearBotLeft = tmpLocation.add(Block.REAR_BOTTOM_LEFT);
                Vector3f faceLoc_rearBotRight = tmpLocation.add(Block.REAR_BOTTOM_RIGHT);
                Vector3f faceLoc_frontTopLeft = tmpLocation.add(Block.FRONT_TOP_LEFT);
                Vector3f faceLoc_frontTopRight = tmpLocation.add(Block.FRONT_TOP_RIGHT);
                Vector3f faceLoc_rearTopLeft = tmpLocation.add(Block.REAR_TOP_LEFT);
                Vector3f faceLoc_rearTopRight = tmpLocation.add(Block.REAR_TOP_RIGHT);
                // Loop over all 6 faces and verify which one should be visible as we currently don't cache this value
                for (Face face : Face.values()) {
                    if (terrain.isFaceVisible(tmpI, face)) {
                        // Write the verts
                        switch (face) {
                            case TOP:
                                writeQuad(verts, indices, normals, 
                                        faceLoc_rearTopLeft, faceLoc_rearTopRight, faceLoc_frontTopLeft, faceLoc_frontTopRight, face);
                                break;
                            case BOTTOM:
                                 writeQuad(verts, indices, normals,
                                        faceLoc_rearBotRight, faceLoc_rearBotLeft, faceLoc_frontBotRight, faceLoc_frontBotLeft, face);
                                break;
                            case LEFT:
                                writeQuad(verts, indices, normals, 
                                        faceLoc_frontBotLeft, faceLoc_rearBotLeft, faceLoc_frontTopLeft, faceLoc_rearTopLeft, face);
                                break;
                            case RIGHT:
                                writeQuad(verts, indices, normals, 
                                        faceLoc_rearBotRight, faceLoc_frontBotRight, faceLoc_rearTopRight, faceLoc_frontTopRight, face);
                                break;
                            case FRONT:
                                writeQuad(verts, indices, normals, 
                                        faceLoc_rearBotLeft, faceLoc_rearBotRight, faceLoc_rearTopLeft, faceLoc_rearTopRight, face);
                                break;
                            case BACK:
                                writeQuad(verts, indices, normals, 
                                        faceLoc_frontBotRight, faceLoc_frontBotLeft, faceLoc_frontTopRight, faceLoc_frontTopLeft, face);
                                break;
                            default:       
                        }
                        // Write the texture coords, width and height is always 1 as we render each cube individually
                        this.writeTextureCoords(textCoords, terrain, tmpI, face, 1, 1, skin);
                    }
                }
            }
        }
        if (indices.isEmpty()) {
            return null;
        }
        // Return the generated mesh from the list of data
        return genMesh(verts, textCoords, indices, normals);
	}

}
