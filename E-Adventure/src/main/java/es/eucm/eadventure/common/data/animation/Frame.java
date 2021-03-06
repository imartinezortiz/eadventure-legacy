/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.data.animation;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * This class holds the information for an animation frame
 * 
 * @author Eugenio Marchiori
 * 
 */
public class Frame implements Cloneable, Timed, Documented {

    /**
     * The xml tag for the sound of the frame
     */
    public static final String RESOURCE_TYPE_SOUND = "sound";

    /**
     * The frame is a image
     */
    public static final int TYPE_IMAGE = 0;

    /**
     * The frame is a video
     */
    public static final int TYPE_VIDEO = 1;

    /**
     * The default time of a frame
     */
    public static final int DEFAULT_TIME = 40;

    /**
     * The url/resource path
     */
    private String uri;

    /**
     * Time to display the frame
     */
    private long time;

    /**
     * Type of the frame: {@link #TYPE_IMAGE} or {@link #TYPE_VIDEO}
     */
    private int type;

    /**
     * The image of the frame, buffered when possible
     */
    private Image image;

    /**
     * Set of resources for the frame
     */
    private List<Resources> resources;

    private boolean waitforclick;

    private String soundUri;

    private int maxSoundTime;

    private String animationPath;
    
    private ImageLoaderFactory factory;

    /**
     * Creates a new empty frame
     */
    public Frame( ImageLoaderFactory factory ) {
        this(factory, "", DEFAULT_TIME, false);
    }

    /**
     * Creates a new frame with a image uri
     * 
     * @param uri
     *            the uri for the image
     */
    public Frame( ImageLoaderFactory factory, String uri ) {
        this(factory, uri, DEFAULT_TIME, false);
    }
    
    /**
     * Creates a new frame with the given duration
     * 
     * @param time
     *            integer with the duration of the frame
     */
    public Frame( ImageLoaderFactory factory, int time ) {
        this(factory, "", time, false);
    }


    /**
     * Creates a new frame with a image uri, a duration time and the selection for user click waiting
     * 
     * @param uri
     *            The uri for the image
     * @param time
     *            The time (duration) of the frame
     */
    public Frame( ImageLoaderFactory factory, String uri, long time, boolean waitForClick ) {
        this.uri = uri;
        this.factory = factory;
        type = TYPE_IMAGE;
        this.time = time;
        image = null;
        this.waitforclick = waitForClick;
        resources = new ArrayList<Resources>( );
        soundUri = "";
        maxSoundTime = 1000;
    }

    /**
     * Returns the uri/path of the frame resource
     * 
     * @return the uri/path of the frame resource
     */
    public String getUri( ) {

        return uri;
    }

    /**
     * Set the uri/path of the frame resource
     * 
     * @param uri
     *            the uri/path of the frame resource
     */
    public void setUri( String uri ) {

        this.uri = uri;
        image = null;
    }

    /**
     * Returns the time (duration) of the frame in milliseconds
     * 
     * @return the time (duration) of the frame in milliseconds
     */
    public long getTime( ) {

        return time;
    }

    /**
     * Set the time (duration) of the frame in milliseconds
     * 
     * @param time
     *            the time (duration) of the frame in milliseconds
     */
    public void setTime( long time ) {

        this.time = time;
    }

    /**
     * Returns the type of the frame
     * 
     * @return the type of the frame
     */
    public int getType( ) {

        return type;
    }

    /**
     * Sets the type of the frame
     * 
     * @param type
     *            the type of the frame
     */
    public void setType( int type ) {

        this.type = type;
    }

    public String getSoundUri( ) {

        return soundUri;
    }

    public void setSoundUri( String soundUri ) {

        this.soundUri = soundUri;
    }

    public int getMaxSoundTime( ) {

        return maxSoundTime;
    }

    public void setMaxSoundTime( int maxSoundTime ) {

        this.maxSoundTime = maxSoundTime;
    }

    /**
     * Adds some resources to the list of resources
     * 
     * @param resources
     *            the resources to add
     */
    public void addResources( Resources resources ) {

        this.resources.add( resources );
    }

    /**
     * Returns the list of resources of the frame
     * 
     * @return The list of resources of the frame
     */
    public List<Resources> getResources( ) {

        return resources;
    }

    /**
     * Returns the image for the frame. The image can be vertically inverted or
     * scaled to fullscreen
     * 
     * @param mirror
     *            If true, the image is vertically inverted
     * @param fullscreen
     *            If true, the image is scaled to fullscreen
     * @return The image for the frame, with the necessary modifications made
     */
    public Image getImage( boolean mirror, boolean fullscreen, int where ) {

        if( image != null )
            return image;
        if( uri != null && uri.length( ) > 0 ) {
            if( where == Animation.ENGINE || where == Animation.EDITOR)
                image = factory.getImageFromPath(  uri );
            else if( where == Animation.PREVIEW )
                image = getImageFromAnimationPath( );
        }
        if( image != null && mirror )
            image = getScaledImage( image, -1, 1 );
        if( image != null && fullscreen )
            image = getFullscreenImage( image );
        if( image == null ) {
            ImageIcon icon = new ImageIcon( "img/icons/noImageFrame.png" );
            if( icon != null && icon.getImage( ) != null )
                return icon.getImage( );
            else
                return new BufferedImage( 100, 120, BufferedImage.TYPE_3BYTE_BGR );
        }

        return image;
    }

    private Image getImageFromAnimationPath( ) {

        Image image = null;

        try {
            InputStream inputStream = null;

            String regexp = java.io.File.separator;
            if( regexp.equals( "\\" ) )
                regexp = "\\\\";
            String temp[] = this.animationPath.split( regexp );
            String filename = "";
            for( int i = 0; i < temp.length - 1; i++ ) {
                filename += temp[i] + java.io.File.separator;
            }

            temp = this.uri.split( "/" );
            filename += temp[temp.length - 1];

            if( new File( filename ).exists( ) )
                inputStream = new FileInputStream( filename );

            if( inputStream != null ) {
                image = ImageIO.read( inputStream );
                if( image == null || image.getHeight( null ) == -1 || image.getWidth( null ) == -1 ) {
                    factory.showErrorDialog( TC.get( "Error.Title") , TC.get( "Error.ImageTypeNotSupported" ) );
                }
                inputStream.close( );
            }
        }
        catch( IOException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return image;
    }

    /**
     * Returns a new imaged scaled accordingly.
     * 
     * @param image
     *            The original image
     * @param x
     *            The scale along the x-axis
     * @param y
     *            The scale alogn the y-axis
     * @return A new, scaled image
     */
    private Image getScaledImage( Image image, float x, float y ) {

        Image newImage = null;

        if( image != null ) {

            // set up the transform
            AffineTransform transform = new AffineTransform( );
            transform.scale( x, y );
            transform.translate( ( x - 1 ) * image.getWidth( null ) / 2, ( y - 1 ) * image.getHeight( null ) / 2 );

            // create a transparent (not translucent) image
            newImage = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( image.getWidth( null ), image.getHeight( null ), Transparency.TRANSLUCENT );

            // draw the transformed image
            Graphics2D g = (Graphics2D) newImage.getGraphics( );

            g.drawImage( image, transform, null );
            g.dispose( );
        }

        return newImage;
    }

    /**
     * Returns a scaled image that fits in the game screen.
     * 
     * @param image
     *            the image to be scaled.
     * @return a scaled image that fits in the game screen.
     */
    private Image getFullscreenImage( Image image ) {
        Image newImage = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, Transparency.TRANSLUCENT );

        Graphics2D g = (Graphics2D) newImage.getGraphics( );
        g.drawImage( image, 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, null );
        g.dispose( );

        return newImage;
    }

    /**
     * Returns the value of waitforclick
     * 
     * @return the value of waitforclick
     */
    public boolean isWaitforclick( ) {

        return waitforclick;
    }

    /**
     * Set the value of waitforclick
     * 
     * @param waitforclick
     */
    public void setWaitforclick( boolean waitforclick ) {

        this.waitforclick = waitforclick;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Frame f = (Frame) super.clone( );
        f.image = image;
        if( resources != null ) {
            f.resources = new ArrayList<Resources>( );
            for( Resources r : resources )
                f.resources.add( (Resources) r.clone( ) );
        }
        f.time = time;
        f.type = type;
        f.uri = ( uri != null ? new String( uri ) : null );
        f.waitforclick = waitforclick;
        return f;
    }

    public void setAbsolutePath( String animationPath ) {

        this.animationPath = animationPath;
    }

    public String getImageAbsolutePath( ) {

        String regexp = java.io.File.separator;
        if( regexp.equals( "\\" ) )
            regexp = "\\\\";
        String temp[] = this.animationPath.split( regexp );
        String filename = "";
        for( int i = 0; i < temp.length - 1; i++ ) {
            filename += temp[i] + java.io.File.separator;
        }

        temp = this.uri.split( "/" );
        filename += temp[temp.length - 1];

        return filename;
    }

    public String getSoundAbsolutePath( ) {

        if( soundUri == null || soundUri.equals( "" ) )
            return null;

        String regexp = java.io.File.separator;
        if( regexp.equals( "\\" ) )
            regexp = "\\\\";
        String temp[] = this.animationPath.split( regexp );
        String filename = "";
        for( int i = 0; i < temp.length - 1; i++ ) {
            filename += temp[i] + java.io.File.separator;
        }

        temp = this.soundUri.split( "/" );
        filename += temp[temp.length - 1];

        return filename;
    }
    
    /***************************************************************/
    // Added for accessibility purposes
    /****************************************************************/
    
    private String documentation = "";
    public String getDocumentation( ) {
        return documentation;
    }

    public void setDocumentation( String documentation ) {
        this.documentation = documentation;
    }

}
