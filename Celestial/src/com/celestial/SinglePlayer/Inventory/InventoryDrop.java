package com.celestial.SinglePlayer.Inventory;

import com.celestial.Celestial;
import com.celestial.SinglePlayer.Components.Planet.Planet;
import com.celestial.SinglePlayer.Components.Planet.PlanetFace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;

/**
 * Inventory Drop Items
 *
 * @author kevint.
 *         Created Jun 7, 2013.
 */
public class InventoryDrop{
	
	private Geometry itemdrop;
	private BillboardControl itemQuad;
	private Quad itemBox;
	
	private InventoryItem item;
	private RigidBodyControl rigidBodyControl;
	
	private Node itemdropnode;
	
	private Planet planet;
	
	public InventoryDrop(InventoryItem item, Vector3f location, Planet planet) {
		Material mat = new Material(Celestial.portal.getAssetManager(),  // Create new material and...
			    "Common/MatDefs/Misc/Unshaded.j3md");
		//mat.setColor("Diffuse", new ColorRGBA(0.5f,0.5f,0.5f,0.3f));
		//mat.setColor("Ambient", new ColorRGBA(0.5f,0.5f,0.5f,0.3f));
		mat.setTexture("ColorMap", Celestial.portal.getAssetManager().loadTexture(item.getIcon()));
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		
		this.itemBox = new Quad(1,1);
		this.itemdrop = new Geometry("DropItem", itemBox);
		itemQuad = new BillboardControl();
		this.itemdrop.addControl(itemQuad);
		this.itemdrop.setMaterial(mat);
		this.itemdrop.getMesh().scaleTextureCoordinates(new Vector2f(1f,1f));
		
		this.itemdrop.setLocalTranslation(location);
		
		this.itemdrop.setQueueBucket(Bucket.Transparent);
		this.itemdrop.setShadowMode(ShadowMode.Cast);
		
		this.rigidBodyControl = new RigidBodyControl();
		this.rigidBodyControl.setMass(5);
		//this.rigidBodyControl.removeCollideWithGroup(Celestial.portal.player.getCollisionGroup());
        this.itemdrop.addControl(rigidBodyControl);
        //Celestial.portal.player.removeCollideWithGroup(this.rigidBodyControl.getCollisionGroup());
        planet.getBulletAppState().getPhysicsSpace().add(this.itemdrop);
        
        this.itemdropnode = new Node();
        this.itemdropnode.attachChild(itemdrop);
        
        planet.getPlanetNode().attachChild(itemdropnode);
        this.planet = planet;
        
        this.item = item;
	}
	
	public BillboardControl getShape() {
		return this.itemQuad;
	}
	
	public Geometry getGeometry() {
		return this.itemdrop;
	}
	
	public InventoryItem getItem() {
		return this.item;
	}
	public Node getNode() {
		return this.itemdropnode;
	}
	public RigidBodyControl getCollisionBox() {
		return this.rigidBodyControl;
	}
	public InventorySlot getSlot(InventoryManager invmanager) {
		return new InventorySlot(getItem(), 1, invmanager);
	}
	public Planet getPlanet() {
		return this.planet;
	}
	
	public PlanetFace getCurrentFaceOfPlanet(Planet planet)
	{		
		Vector3f P1 = planet.getOriginalPlanetTranslation();

		Vector3f itemP = null;
		if(this.planet.getBulletAppState().isEnabled())
			itemP = this.itemdrop.getWorldTranslation();
		else
			return PlanetFace.UNKNOWN;

		Vector3f rot1P = planet.getStarNode().getLocalRotation().inverse().mult(itemP);
		Vector3f transP = rot1P.subtract(P1);
		Vector3f rot2P = planet.getPlanetNode().getLocalRotation().inverse().mult(transP);

		float x = rot2P.x;
		float y = rot2P.y;
		float z = rot2P.z;

		if( Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(z) ) {
			if( y < 0 ) {
				return PlanetFace.BOTTOM;
			} else {
				return PlanetFace.TOP;
			}
		} else if( Math.abs(x) > Math.abs(z) ) {
			if( x < 0 ) {
				return PlanetFace.WEST;
			} else {
				return PlanetFace.EAST;
			}
		} else if( Math.abs(z) > Math.abs(x) ) {
			if( z < 0 ) {
				return PlanetFace.NORTH;
			} else {
				return PlanetFace.SOUTH;
			}
		} else {
			return PlanetFace.UNKNOWN;
		}
	}
	
}
