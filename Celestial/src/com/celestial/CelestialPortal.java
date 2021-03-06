/**
@author	Mitch Talmadge
Date Created:
	Jun 8, 2013
 */

package com.celestial;

import com.celestial.Gui.Gui;
import com.celestial.SinglePlayer.Components.Galaxy;
import com.celestial.SinglePlayer.Components.Player;
import com.celestial.SinglePlayer.Input.InputControl;
import com.celestial.SinglePlayer.Inventory.InventoryManager;
import com.cubes.CubesSettings;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

public abstract class CelestialPortal
{
    
    private Celestial parent;
    protected InventoryManager invmanager;
    protected Node rootNode;
    protected Node guiNode;
    public Camera cam;
    protected FlyByCamera flyCam;
    private ViewPort viewPort;
    protected AssetManager assetManager;
    public Vector3f walkDirection;
    public boolean left = false, right = false, up = false, down = false;
    protected AppSettings settings;
    protected InputControl inputControl;
    protected InputManager inputManager;
    public Player player;
    public SimpleApplication app;
    public BitmapText posText;
    public BitmapText InvText;
    public BitmapText PlanetText;
    public BitmapText Vector3IntPosText;
    public BitmapText blocksLiveText;
    public CubesSettings csettings;
    public Galaxy galaxy;
    protected Gui gui;
    
    public abstract void startGame();
    
    public abstract void stopGame();
    
    public abstract void simpleUpdate(float tpf);
    
    public abstract void simpleRender(RenderManager rm);
    
    public abstract Player getPlayer();
    
    public InventoryManager getInventoryManager()
    {
	return this.invmanager;
    }
    
    public InputControl getInputControl()
    {
	return this.inputControl;
    }
    
    public Node getGuiNode()
    {
	return this.guiNode;
    }
    
    public Node getRootNode()
    {
	return this.rootNode;
    }
    
    public void setCamSpeed(float speed)
    {
	this.flyCam.setMoveSpeed(speed);
    }
    
    public void setDisplayFps(boolean b)
    {
	getParent().setDisplayFps(b);
    }
    
    public void setDisplayStatView(boolean b)
    {
	getParent().setDisplayStatView(b);
    }
    
    public AppSettings getSettings()
    {
	return settings;
    }
    
    public AssetManager getAssetManager()
    {
	return assetManager;
    }
    
    public FlyByCamera getFlyCam()
    {
	return flyCam;
    }
    
    public Camera getCam()
    {
	return cam;
    }
    
    /**
     * @return the viewPort
     */
    public ViewPort getViewPort()
    {
	return viewPort;
    }
    
    /**
     * @param viewPort
     *            the viewPort to set
     */
    public void setViewPort(ViewPort viewPort)
    {
	this.viewPort = viewPort;
    }
    
    public abstract InputManager getInputManager();
    
    public abstract Object[] getNiftyUtils();
    
    public Gui getGui()
    {
	return this.gui;
    }
    
    public abstract void hideHighlight();
    
    /**
     * Returns the value of the field called 'parent'.
     * 
     * @return Returns the parent.
     */
    public Celestial getParent()
    {
	return parent;
    }
    
    /**
     * Sets the field called 'parent' to the given value.
     * 
     * @param parent
     *            The parent to set.
     */
    public void setParent(Celestial parent)
    {
	this.parent = parent;
    }
    
}
