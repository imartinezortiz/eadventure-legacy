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
package es.eucm.eadventure.editor.control.controllers.atrezzo;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.assets.AddResourcesBlockTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AtrezzoDataControl extends DataControlWithResources {

    /**
     * Contained atrezzo item.
     */
    private Atrezzo atrezzo;
    
    /**
     * Constructor.
     * 
     * @param atrezzo
     *            Contained atrezzo item
     */
    public AtrezzoDataControl( Atrezzo atrezzo ) {

        this.atrezzo = atrezzo;
        this.resourcesList = atrezzo.getResources( );

        selectedResources = 0;

        // Add a new resource if the list is empty
        if( resourcesList.size( ) == 0 )
            resourcesList.add( new Resources( ) );

        // Create the subcontrollers
        resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
        for( Resources resources : resourcesList )
            resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.ATREZZO ) );
        
    }

    /**
     * Returns the path to the selected preview image.
     * 
     * @return Path to the image, null if not present
     */
    public String getPreviewImage( ) {

        return resourcesDataControlList.get( selectedResources ).getAssetPath( "image" );
    }

    /**
     * Returns the id of the atrezzo item.
     * 
     * @return Atrezzo's id
     */
    public String getId( ) {

        return atrezzo.getId( );
    }

    /**
     * Returns the documentation of the atrezzo item.
     * 
     * @return Atrezzo's documentation
     */
    public String getDocumentation( ) {

        return atrezzo.getDocumentation( );
    }

    @Override
    public Object getContent( ) {

        return atrezzo;
    }

    @Override
    public int[] getAddableElements( ) {

        //return new int[] { Controller.RESOURCES };
        return new int[] {};
    }

    @Override
    public boolean canAddElement( int type ) {

        // It can always add new resources
        return false;
    }

    @Override
    public boolean canBeDeleted( ) {

        return true;
    }

    @Override
    public boolean canBeMoved( ) {

        return true;
    }

    @Override
    public boolean canBeRenamed( ) {

        return true;
    }

    @Override
    public boolean addElement( int type, String id ) {

        boolean elementAdded = false;

        if( type == Controller.RESOURCES ) {
            elementAdded = Controller.getInstance( ).addTool( new AddResourcesBlockTool( resourcesList, resourcesDataControlList, Controller.ATREZZO, this ) );
        }

        return elementAdded;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

        if( elementIndex > 0 ) {
            resourcesList.add( elementIndex - 1, resourcesList.remove( elementIndex ) );
            resourcesDataControlList.add( elementIndex - 1, resourcesDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

        if( elementIndex < resourcesList.size( ) - 1 ) {
            resourcesList.add( elementIndex + 1, resourcesList.remove( elementIndex ) );
            resourcesDataControlList.add( elementIndex + 1, resourcesDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public String renameElement( String name ) {

        boolean elementRenamed = false;
        String oldAtrezzoId = atrezzo.getId( );
        String references = String.valueOf( controller.countIdentifierReferences( oldAtrezzoId ) );

        // Ask for confirmation 
        if( name != null || controller.showStrictConfirmDialog( TC.get( "Operation.RenameAtrezzoTitle" ), TC.get( "Operation.RenameElementWarning", new String[] { oldAtrezzoId, references } ) ) ) {

            // Show a dialog asking for the new atrezzo item id
            String newAtrezzoId = name;
            if( name == null )
                newAtrezzoId = controller.showInputDialog( TC.get( "Operation.RenameAtrezzoTitle" ), TC.get( "Operation.RenameAtrezzoMessage" ), oldAtrezzoId );

            // If some value was typed and the identifiers are different
            if( newAtrezzoId != null && !newAtrezzoId.equals( oldAtrezzoId ) && controller.isElementIdValid( newAtrezzoId ) ) {
                atrezzo.setId( newAtrezzoId );
                controller.replaceIdentifierReferences( oldAtrezzoId, newAtrezzoId );
                controller.getIdentifierSummary( ).deleteAtrezzoId( oldAtrezzoId );
                controller.getIdentifierSummary( ).addAtrezzoId( newAtrezzoId );
                //controller.dataModified( );
                elementRenamed = true;
            }
        }

        if( elementRenamed )
            return oldAtrezzoId;
        else
            return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.updateVarFlagSummary( varFlagSummary );

    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        // Iterate through the resources
        for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
            String resourcesPath = currentPath + " >> " + TC.getElement( Controller.RESOURCES ) + " #" + ( i + 1 );
            valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
        }

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            count += resourcesDataControl.countAssetReferences( assetPath );

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.deleteAssetReferences( assetPath );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;
        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            count += resourcesDataControl.countIdentifierReferences( id );
        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.deleteIdentifierReferences( id );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

        // atrezzo only has name
       // check( this.getBriefDescription( ), TC.get( "Search.BriefDescription" ) );
       // check( this.getDetailedDescription( ), TC.get( "Search.DetailedDescription" ) );
        check( this.getDocumentation( ), TC.get( "Search.Documentation" ) );
        check( this.getId( ), "ID" );
        check( this.getPreviewImage( ), TC.get( "Search.PreviewImage" ) );
        for (ResourcesDataControl r:resourcesDataControlList){
            r.recursiveSearch( );
        }
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, resourcesDataControlList );
    }

}
