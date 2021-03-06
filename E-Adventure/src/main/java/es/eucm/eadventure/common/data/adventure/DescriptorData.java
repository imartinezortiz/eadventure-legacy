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
package es.eucm.eadventure.common.data.adventure;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Titled;

/**
 * Stores the description of the eAdventure file
 */
public class DescriptorData implements Cloneable, Described, Titled {

    public static final String DEFAULT_CURSOR = "default";

    public static final String USE_CURSOR = "use";

    public static final String LOOK_CURSOR = "look";

    public static final String EXAMINE_CURSOR = "examine";

    public static final String TALK_CURSOR = "talk";

    public static final String GRAB_CURSOR = "grab";

    public static final String GIVE_CURSOR = "give";

    public static final String EXIT_CURSOR = "exit";

    public static final String CURSOR_OVER = "over";

    public static final String CURSOR_ACTION = "action";

    public static final String TALK_BUTTON = "talk";

    public static final String USE_GRAB_BUTTON = "use-grab";

    // Specific buttons for grab, use, use-with and give-to were added in eAd1.4
    // Before that, a use-grab generic button was used. It is kept for backwards compatibility
    public static final String GRAB_BUTTON = "grab";
    
    public static final String USE_BUTTON = "use";
    
    public static final String USEWITH_BUTTON = "use-with";
    
    public static final String GIVETO_BUTTON = "give-to";
    
    public static final String DRAGTO_BUTTON = "drag-to";
    // 
    
    public static final String EXAMINE_BUTTON = "examine";

    public static final String HIGHLIGHTED_BUTTON = "highlighted";
    //ONLY USE IT IN editor.ButtonsPanel
    public static final String SOUND_PATH = "sound";
    public static final String SOUND_PATH_ARROW_RIGHT = "sound-right";
    public static final String SOUND_PATH_ARROW_LEFT = "sound-left";

    public static final String NORMAL_BUTTON = "normal";

    public static final String PRESSED_BUTTON = "pressed";

    public static final String NORMAL_ARROW_RIGHT = "normalright";

    public static final String NORMAL_ARROW_LEFT = "normalleft";

    public static final String HIGHLIGHTED_ARROW_RIGHT = "highlightedright";

    public static final String HIGHLIGHTED_ARROW_LEFT = "highlightedleft";

    public static final int INVENTORY_NONE = 0;

    public static final int INVENTORY_TOP_BOTTOM = 1;

    public static final int INVENTORY_TOP = 2;

    public static final int INVENTORY_BOTTOM = 3;
    
    public static final int INVENTORY_FIXED_TOP = 4;

    public static final int INVENTORY_FIXED_BOTTOM = 5;

    
    public static enum DefaultClickAction {
        SHOW_DETAILS, SHOW_ACTIONS
    }
    
    public static enum Perspective {
        REGULAR, ISOMETRIC
    }
    
    public static enum DragBehaviour {
        IGNORE_NON_TARGETS, CONSIDER_NON_TARGETS
    }

    public static String getCursorTypeString( int index ) {

        switch( index ) {
            case 0:
                return DEFAULT_CURSOR;
            case 1:
                return CURSOR_OVER;
            case 2:
                return CURSOR_ACTION;
            case 3:
                return EXIT_CURSOR;
            case 4:
                return USE_CURSOR;
            case 5:
                return LOOK_CURSOR;
            case 6:
                return EXAMINE_CURSOR;
            case 7:
                return TALK_CURSOR;
            case 8:
                return GRAB_CURSOR;
            case 9:
                return GIVE_CURSOR;
            default:
                return null;
        }
    }

    public static int getCursorTypeIndex( String type ) {

        if( type.equals( DEFAULT_CURSOR ) ) {
            return 0;
        }
        else if( type.equals( USE_CURSOR ) ) {
            return 4;
        }
        else if( type.equals( LOOK_CURSOR ) ) {
            return 5;
        }
        else if( type.equals( EXAMINE_CURSOR ) ) {
            return 6;
        }
        else if( type.equals( TALK_CURSOR ) ) {
            return 7;
        }
        else if( type.equals( GRAB_CURSOR ) ) {
            return 8;
        }
        else if( type.equals( GIVE_CURSOR ) ) {
            return 9;
        }
        else if( type.equals( EXIT_CURSOR ) ) {
            return 3;
        }
        else if( type.equals( CURSOR_OVER ) ) {
            return 1;
        }
        else if( type.equals( CURSOR_ACTION ) ) {
            return 2;
        }
        else {
            return -1;
        }
    }

    // the deleted cursors are related with the traditional hud, which has been removed 
    private static final String[] cursorTypes = { DEFAULT_CURSOR, CURSOR_OVER, CURSOR_ACTION, EXIT_CURSOR/*, USE_CURSOR, LOOK_CURSOR, EXAMINE_CURSOR, TALK_CURSOR, GRAB_CURSOR, GIVE_CURSOR */};

    public static String[] getCursorTypes( ) {

        return cursorTypes;
    }

    /*
     * Action types, buttonTypes & arrowTypes are only used in the editor, to create the gui customization dialog 
     * automatically. So: order matters!
     */
    private static final String[] actionTypes = { EXAMINE_BUTTON, TALK_BUTTON, /*USE_GRAB_BUTTON, */USE_BUTTON, GRAB_BUTTON, USEWITH_BUTTON, GIVETO_BUTTON, DRAGTO_BUTTON };

    public static String[] getActionTypes( ) {

        return actionTypes;
    }

    private static final String[] buttonTypes = { NORMAL_BUTTON, HIGHLIGHTED_BUTTON, SOUND_PATH/*, PRESSED_BUTTON */};

    public static String[] getButtonTypes( ) {

        return buttonTypes;
    }

    private static final String[] arrowTypes = { NORMAL_ARROW_RIGHT, NORMAL_ARROW_LEFT, HIGHLIGHTED_ARROW_RIGHT, HIGHLIGHTED_ARROW_LEFT, SOUND_PATH_ARROW_RIGHT, SOUND_PATH_ARROW_LEFT };

    public static String[] getArrowTypes( ) {

        return arrowTypes;
    }

    public static final boolean[][] typeAllowed = {
    //TRADITIONAL GUI
    { true, false, false, true, true, true, true, true, true, true },
    //CONTEXTUAL GUI
    { true, true, true, true, false, false, false, false, false, false } };

    /**
     * Constant for traditional GUI.
     */
    public static final int GUI_TRADITIONAL = 0;

    /**
     * Constant for contextual GUI.
     */
    public static final int GUI_CONTEXTUAL = 1;

    public static final int GRAPHICS_WINDOWED = 0;

    public static final int GRAPHICS_BLACKBKG = 1;

    public static final int GRAPHICS_FULLSCREEN = 2;

    /**
     * Constant for 1st person adventure mode
     */
    public static final int MODE_PLAYER_1STPERSON = 0;

    /**
     * Constant for 3rd person adventure mode
     */
    public static final int MODE_PLAYER_3RDPERSON = 1;

    /**
     * Title of the adventure.
     */
    protected String title;

    /**
     * Description of the adventure.
     */
    protected String description;

    /**
     * Type of the GUI (Traditional or contextual)
     */
    protected int guiType;

    /**
     * Default graphic configuration (fullscreen, windowed, etc)
     */
    private int graphicConfig;

    /**
     * Adventure mode (1st person/3rd person)
     */
    protected int playerMode;

    /**
     * Stores if the GUI's graphics are customized
     */
    protected boolean guiCustomized;

    /**
     * List of contents of the game
     */
    protected List<ChapterSummary> contents;

    /**
     * List of custom cursors
     */
    protected List<CustomCursor> cursors;

    /**
     * List of custom buttons
     */
    protected List<CustomButton> buttons;

    protected List<CustomArrow> arrows;

    /**
     * This flag tells if the adventure should show automatic commentaries.
     */
    protected boolean commentaries = false;
    
    /**
     * This flag tell if the conversations in this adventure will stop the conversation lines until user skip them
     */
    protected boolean keepShowing = false;

    /**
     * The name of the player, only used when reports are send by e-mail.
     */
    protected String playerName = "";

    protected Integer inventoryPosition = INVENTORY_TOP_BOTTOM;

    /**
     * The version number of the current game/proyect
     */
    protected String versionNumber;

    /**
     * This is not store in physical descriptor xml file.
     * 
     * Is used to only allow to load a saved game in the same project or .ead
     * game
     */
    protected String projectName;
    
    protected DefaultClickAction defaultClickAction;

    protected Perspective perspective;
    
    protected DragBehaviour dragBehaviour;
    
    protected boolean keyboardNavigationEnabled;
    
    /**
     * Constant for identify when a game is executed from engine
     */
    public static final String ENGINE_EXECUTION = "engine";

    /**
     * Constructor
     */
    public DescriptorData( ) {

        contents = new ArrayList<ChapterSummary>( );
        cursors = new ArrayList<CustomCursor>( );
        buttons = new ArrayList<CustomButton>( );
        arrows = new ArrayList<CustomArrow>( );
        title = null;
        description = null;
        guiType = -1;
        defaultClickAction = DefaultClickAction.SHOW_DETAILS;
        perspective = Perspective.REGULAR;
        dragBehaviour = DragBehaviour.CONSIDER_NON_TARGETS;
        playerMode = MODE_PLAYER_1STPERSON;
        graphicConfig = GRAPHICS_WINDOWED;
        projectName = ENGINE_EXECUTION;
        versionNumber = "0";
        keyboardNavigationEnabled=false;
    }

    /**
     * Returns the title of the adventure
     * 
     * @return Adventure's title
     */
    public String getTitle( ) {

        return title;
    }

    /**
     * Returns the description of the adventure.
     * 
     * @return Adventure's description
     */
    public String getDescription( ) {

        return description;
    }

    /**
     * Returns the GUI type of the adventure.
     * 
     * @return Adventure's GUI type
     */
    public Integer getGUIType( ) {

        return guiType;
    }

    /**
     * Sets the title of the adventure.
     * 
     * @param title
     *            New title for the adventure
     */
    public void setTitle( String title ) {

        this.title = title;
    }

    /**
     * Sets the description of the adventure.
     * 
     * @param description
     *            New description for the adventure
     */
    public void setDescription( String description ) {

        this.description = description;
    }

    /**
     * Sets the GUI type of the adventure.
     * 
     * @param guiType
     *            New GUI type for the adventure
     */
    public void setGUIType( Integer guiType ) {

        this.guiType = guiType;
    }

    /**
     * @return the playerMode
     */
    public Integer getPlayerMode( ) {

        return playerMode;
    }

    /**
     * @param playerMode
     *            the playerMode to set
     */
    public void setPlayerMode( Integer playerMode ) {

        this.playerMode = playerMode;
    }

    /**
     * Returns whether the GUI is customized
     * 
     * @return True if the GUI is customized, false otherwise
     */
    public boolean isGUICustomized( ) {

        return guiCustomized;
    }

    /**
     * Sets the parameters of the GUI
     * 
     * @param guiType
     *            Type of the GUI
     * @param guiCustomized
     *            False if the GUI should be customized, false otherwise
     */
    public void setGUI( int guiType, boolean guiCustomized ) {

        this.guiType = guiType;
        this.guiCustomized = guiCustomized;
    }

    /**
     * Returns the list of chapters of the game
     * 
     * @return List of chapters of the game
     */
    public List<ChapterSummary> getChapterSummaries( ) {

        return contents;
    }

    /**
     * Adds a new chapter to the list
     * 
     * @param chapter
     *            Chapter to be added
     */
    public void addChapterSummary( ChapterSummary chapter ) {

        contents.add( chapter );
    }

    public void addCursor( CustomCursor cursor ) {

        cursors.add( cursor );
        this.guiCustomized = true;
    }

    public List<CustomCursor> getCursors( ) {

        return cursors;
    }

    public void addCursor( String type, String path ) {

        addCursor( new CustomCursor( type, path ) );
    }

    public String getCursorPath( String type ) {

        for( CustomCursor cursor : cursors ) {
            if( cursor.getType( ).equals( type ) ) {
                return cursor.getPath( );
            }
        }
        return null;
    }

    public void addButton( CustomButton button ) {

        buttons.add( button );
        this.guiCustomized = true;
    }

    public List<CustomButton> getButtons( ) {

        return buttons;
    }

    public void addButton( String action, String type, String path ) {
        CustomButton cb =new CustomButton( action, type, path );
        addButton( cb );
    }

    public String getButtonPathFromEditor( String action, String type) {
        return getButtonPath( action, type, false );
    }
    
    public String getButtonPathFromEngine( String action, String type ) {
        return getButtonPath( action, type, true );
    }
    
    private String getButtonPath( String action, String type, boolean checkLegacyButtons ) {

        for( CustomButton button : buttons ) {
            if( button.getType( ).equals( type ) && button.getAction( ).equals( action ) ) {
                return button.getPath( );
            }
        }
        
        // ADDED IN eAd1.4: different types of buttons for use, use-with, grab and give-to. If the specific button is not found, a generic "use-grab" button is
        // searched
        if (checkLegacyButtons && action!=null && (action.equals( USEWITH_BUTTON ) || action.equals( USE_BUTTON ) || action.equals( GRAB_BUTTON ) || action.equals( GIVETO_BUTTON ))){
            return getButtonPath ( USE_GRAB_BUTTON, type, false);
        }
        return null;
    }

    public void addArrow( CustomArrow arrow ) {

        arrows.add( arrow );
    }

    public void addArrow( String type, String path ) {
        CustomArrow ca = new CustomArrow( type, path);
        arrows.add( ca );
    }

    public List<CustomArrow> getArrows( ) {

        return arrows;
    }

    public String getArrowPath( String type ) {

        for( CustomArrow arrow : arrows ) {
            if( arrow.getType( ).equals( type ) ) {
                return arrow.getPath( );
            }
        }
        return null;
    }

    public Boolean isCommentaries( ) {

        return commentaries;
    }

    public void setCommentaries( Boolean commentaries ) {

        this.commentaries = commentaries;
    }

    public Integer getGraphicConfig( ) {

        return this.graphicConfig;
    }

    public void setGraphicConfig( Integer graphicConfig ) {

        this.graphicConfig = graphicConfig;
    }

    /**
     * @return the playerName
     */
    public String getPlayerName( ) {

        return playerName;
    }

    /**
     * @param playerName
     *            the playerName to set
     */
    public void setPlayerName( String playerName ) {

        this.playerName = playerName;
    }

    public Integer getInventoryPosition( ) {

        return inventoryPosition;
    }

    public void setInventoryPosition( Integer inventoryPosition ) {

        this.inventoryPosition = inventoryPosition;
    }

    public int countAssetReferences( String path ) {

        int count = 0;
        for( CustomButton cb : buttons ) {
            if( cb.getPath( ).equals( path ) )
                count++;
            if (cb.getSoundPath( ).equals( path ) )
                count++;
        }
        for( CustomArrow a : arrows ) {
            if( a.getPath( ).equals( path ) )
                count++;
            if (a.getSoundPath( ).equals( path ) )
                count++;
        }
        for( CustomCursor cc : cursors ) {
            if( cc.getPath( ).equals( path ) )
                count++;
        }

        return count;
    }

    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        if( assetPaths != null && assetTypes != null ) {
            // Firstly iterate arrows
            for( CustomArrow arrow : arrows ) {
                int assetType = AssetsConstants.CATEGORY_BUTTON;
                String assetPath = arrow.getPath( );
                getAssetReferencesForOneAsset( assetPaths, assetTypes, assetPath, assetType );
                if (arrow.getSoundPath( )!= null && !arrow.getSoundPath( ).equals( "" )){
                    assetPath = arrow.getSoundPath( );
                    assetType = AssetsConstants.CATEGORY_AUDIO;
                    getAssetReferencesForOneAsset( assetPaths, assetTypes, assetPath, assetType );
                }
            }
            // Secondly iterate buttons
            for( CustomButton button : buttons ) {
                int assetType = AssetsConstants.CATEGORY_BUTTON;
                String assetPath = button.getPath( );
                getAssetReferencesForOneAsset( assetPaths, assetTypes, assetPath, assetType );
                if (button.getSoundPath( )!= null && !button.getSoundPath( ).equals( "" )){
                    assetPath = button.getSoundPath( );
                    assetType = AssetsConstants.CATEGORY_AUDIO;
                    getAssetReferencesForOneAsset( assetPaths, assetTypes, assetPath, assetType );
                }
            }
            // Finally iterate cursors
            for( CustomCursor cursor : cursors ) {
                int assetType = AssetsConstants.CATEGORY_CURSOR;
                String assetPath = cursor.getPath( );
                getAssetReferencesForOneAsset( assetPaths, assetTypes, assetPath, assetType );
            }

        }
    }

    private void getAssetReferencesForOneAsset( List<String> assetPaths, List<Integer> assetTypes, String assetPath, int assetType ) {

        if( assetPath == null )
            return;

        boolean found = false;
        for( String path : assetPaths ) {
            if( path.toLowerCase( ).equals( assetPath.toLowerCase( ) ) ) {
                found = true;
                break;
            }
        }
        if( !found ) {
            int last = assetPaths.size( );
            assetPaths.add( last, assetPath );
            assetTypes.add( last, assetType );
        }
    }

    /**
     * Deletes a given asset from the script, removing all occurrences.
     * 
     * @param assetPath
     *            Path of the asset (relative to the ZIP), without suffix in
     *            case of an animation or set of slides
     */
    public void deleteAssetReferences( String assetPath ) {

        if( assetPath != null ) {
            // Firstly iterate arrows
            for( int i = 0; i < arrows.size( ); i++ ) {
                CustomArrow arrow = arrows.get( i );
                if( arrow.getPath( ) != null && arrow.getPath( ).equals( assetPath ) ) {
                    arrow.setPath( null );
                }
                
                if( arrow.getSoundPath( ) != null && arrow.getSoundPath( ).equals( assetPath ) ) {
                    arrow.setSoundPath( null );
                }
                
                if (arrow.getPath( )==null && arrow.getSoundPath( )==null){
                    arrows.remove( i );
                    i--;
                }
            }
            // Secondly iterate buttons
            for( int i = 0; i < buttons.size( ); i++ ) {
                CustomButton button = buttons.get( i );
                if( button.getPath( ) != null && button.getPath( ).equals( assetPath ) ) {
                    button.setPath( null );
                }
                
                if( button.getSoundPath( ) != null && button.getSoundPath( ).equals( assetPath ) ) {
                    button.setSoundPath( null );
                }
                
                if (button.getPath( )==null && button.getSoundPath( )==null){
                    arrows.remove( i );
                    i--;
                }
            }
            // Finally iterate cursors
            for( int i = 0; i < cursors.size( ); i++ ) {
                CustomCursor cursor = cursors.get( i );
                if( cursor.getPath( ) != null && cursor.getPath( ).equals( assetPath ) ) {
                    cursors.remove( i );
                    i--;
                }
            }
        }
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {
        //TODO the keepShowing is now no included due to decide the final situation
        DescriptorData dd = (DescriptorData) super.clone( );
        if( buttons != null ) {
            dd.buttons = new ArrayList<CustomButton>( );
            for( CustomButton cb : buttons )
                dd.buttons.add( (CustomButton) cb.clone( ) );
        }
        dd.commentaries = commentaries;
        if( contents != null ) {
            dd.contents = new ArrayList<ChapterSummary>( );
            for( ChapterSummary cs : contents )
                dd.contents.add( (ChapterSummary) cs.clone( ) );
        }
        if( cursors != null ) {
            dd.cursors = new ArrayList<CustomCursor>( );
            for( CustomCursor cc : cursors )
                dd.cursors.add( (CustomCursor) cc.clone( ) );
        }
        if( arrows != null ) {
            dd.arrows = new ArrayList<CustomArrow>( );
            for( CustomArrow ca : arrows )
                dd.arrows.add( (CustomArrow) ca.clone( ) );
        }
        dd.description = ( description != null ? new String( description ) : null );
        dd.graphicConfig = graphicConfig;
        dd.guiCustomized = guiCustomized;
        dd.guiType = guiType;
        dd.playerMode = playerMode;
        dd.playerName = ( playerName != null ? new String( playerName ) : null );
        dd.title = ( title != null ? new String( title ) : null );
        dd.inventoryPosition = new Integer( inventoryPosition );
        return dd;
    }

    /**
     * @return the versionNumber
     */
    public String getVersionNumber( ) {

        return versionNumber;
    }

    /**
     * @param versionNumber
     *            the versionNumber to set
     */
    public void setVersionNumber( String versionNumber ) {

        this.versionNumber = versionNumber;
    }

    /**
     * @return the projectName
     */
    public String getProjectName( ) {

        return projectName;
    }

    /**
     * @param projectName
     *            the projectName to set
     */
    public void setProjectName( String projectName ) {

        this.projectName = projectName;
    }

    
    public Boolean isKeepShowing( ) {
    
        return keepShowing;
    }

    
    public void setKeepShowing( Boolean keepShowing ) {
    
        this.keepShowing = keepShowing;
    }

    public void setDeafultClickAction( DefaultClickAction clickAction ) {
        this.defaultClickAction = clickAction;
    }

    public DefaultClickAction getDefaultClickAction() {
        return defaultClickAction;
    }
    
    public void setPerspective( Perspective perspective ) {
        this.perspective = perspective;
    }
    
    public Perspective getPerspective() {
        return perspective;
    }

    
    public DragBehaviour getDragBehaviour( ) {
    
        return dragBehaviour;
    }

    
    public void setDragBehaviour( DragBehaviour dragBehaviour ) {
    
        this.dragBehaviour = dragBehaviour;
    }

    
    public Boolean isKeyboardNavigationEnabled( ) {
    
        return keyboardNavigationEnabled;
    }

    
    public void setKeyboardNavigation( Boolean keyboardNavigation ) {
    
        this.keyboardNavigationEnabled = keyboardNavigation;
    }

    
}
