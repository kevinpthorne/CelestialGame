/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cubes;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

/**
 *
 * @author Carl
 */
public class BlockChunk_Material extends Material{

    public BlockChunk_Material(AssetManager assetManager, String blockTextureFilePath){
        //super(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    	super(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture(blockTextureFilePath);
        texture.setMagFilter(Texture.MagFilter.Nearest);
        texture.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        setTexture("ColorMap", texture);
        /*setTexture("DiffuseMap", texture);
        setBoolean("UseMaterialColors", true);
        setColor("Diffuse",  ColorRGBA.White);
        setColor("Specular", ColorRGBA.White);
        setColor("Ambient",  ColorRGBA.White);*/
        getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
    }
}
