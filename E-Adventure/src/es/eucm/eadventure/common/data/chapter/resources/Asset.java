package es.eucm.eadventure.common.data.chapter.resources;

/**
 * Defines an asset of any type (background, slides, image, icon, [...],
 * or the animatinos of the characters.
 */
public class Asset implements Cloneable {
    
    /**
     * String with the type of the asset
     */
    private String type;
    
    /**
     * Path of the asset
     */
    private String path;
    
    /**
     * Creates a new asset
     * @param type the type of the asset
     * @param path the path of the asset
     */
    public Asset( String type, String path ) {
        this.type = type;
        this.path = path;
    }

    /**
     * Returns the type of the asset.
     * @return the type of the asset
     */
    public String getType( ) {
        return type;
    }

    /**
     * Returns the path of the asset.
     * @return the path of the asset
     */
    public String getPath( ) {
        return path;
    }
    
	public Object clone() throws CloneNotSupportedException {
		Asset a = (Asset) super.clone();
		a.path = (path != null ? new String(path) : null);
		a.type = (type != null ? new String(type) : null);
		return a;
	}
}
