package es.eucm.eadventure.common.data.chapter.scenes;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

/**
 * This class holds the data of a scene of any type in eAdventure.
 */
public abstract class GeneralScene implements Cloneable, Documented, Named {

	/**
	 * A regular eAdventure scene.
	 */
	public static final int SCENE = 0;

	/**
	 * A scene of type "slidescene".
	 */
	public static final int SLIDESCENE = 1;

	/**
	 * A scene of type "videoscene".
	 */
	public static final int VIDEOSCENE = 2;

	/**
	 * Type of the scene.
	 */
	private int type;

	/**
	 * Id of the scene.
	 */
	private String id;

	/**
	 * Name of the scene.
	 */
	private String name;

	/**
	 * Documentation of the scene.
	 */
	private String documentation;

	/**
	 * List of resources for the scene.
	 */
	private List<Resources> resources;
	
    /**
     * Stores if the scene should be loaded at the begining
     */
    private boolean initialScene;

	/**
	 * Creates a new GeneralScene.
	 * 
	 * @param type
	 *            the type of the scene
	 * @param id
	 *            the id of the scene
	 */
	protected GeneralScene( int type, String id ) {
		this.type = type;
		this.id = id;
		name = "";
		documentation = null;
		resources = new ArrayList<Resources>( );
	}

	/**
	 * Returns the type of the scene.
	 * 
	 * @return the type of the scene
	 */
	public int getType( ) {
		return type;
	}

	/**
	 * Returns the id of the scene.
	 * 
	 * @return the id of the scene
	 */
	public String getId( ) {
		return id;
	}

	/**
	 * Returns the name of the scene.
	 * 
	 * @return the name of the scene
	 */
	public String getName( ) {
		return name;
	}

	/**
	 * Returns the documentation of the scene.
	 * 
	 * @return the documentation of the scene
	 */
	public String getDocumentation( ) {
		return documentation;
	}

	/**
	 * Returns the list of resources of this scene.
	 * 
	 * @return the list of resources of this scene
	 */
	public List<Resources> getResources( ) {
		return resources;
	}

	/**
	 * Sets the a new identifier for the general scene.
	 * 
	 * @param id
	 *            New identifier
	 */
	public void setId( String id ) {
		this.id = id;
	}

	/**
	 * Changes the name of this scene.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/**
	 * Changes the documentation of this scene.
	 * 
	 * @param documentation
	 *            The new documentation
	 */
	public void setDocumentation( String documentation ) {
		this.documentation = documentation;
	}

	/**
	 * Adds some resources to the list of resources.
	 * 
	 * @param resources
	 *            the resources to add
	 */
	public void addResources( Resources resources ) {
		this.resources.add( resources );
	}
	
    /**
     * Returns whether this is the initial scenene
     * @return true if this is the initial scnene, false otherwise
     */
    public boolean isInitialScene( ) {
        return initialScene;
    }

    /**
     * Changes whether this is the initial scene
     * @param initialScene true if this is the initial scene, false otherwise
     */
    public void setInitialScene( boolean initialScene ) {
        this.initialScene = initialScene;
    }

	public Object clone() throws CloneNotSupportedException {
		GeneralScene gs = (GeneralScene) super.clone();
		gs.documentation = (documentation != null ? new String(documentation) : null);
		gs.id = (id != null ? new String(id) : null);
		gs.initialScene = initialScene;
		gs.name = (name != null ? new String(name) : null);
		if (resources != null) {
			gs.resources = new ArrayList<Resources>();
			for (Resources r : resources)
				gs.resources.add((Resources) r.clone());
		}
		gs.type = type;
		return gs;
	}
}
