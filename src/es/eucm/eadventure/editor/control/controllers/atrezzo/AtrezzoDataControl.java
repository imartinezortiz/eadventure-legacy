/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author L�pez Ma�as, E., P�rez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fern�ndez-Manj�n, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.control.controllers.atrezzo;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.assets.AddResourcesBlockTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDetailedDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeNameTool;
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

    /**
     * Returns the name of the atrezzo item.
     * 
     * @return Atrezzo's name
     */
    public String getName( ) {

        return atrezzo.getName( );
    }

    /**
     * Returns the brief description of the atrezzo item.
     * 
     * @return Atrezzo's description
     */
    public String getBriefDescription( ) {

        return atrezzo.getDescription( );
    }

    /**
     * Returns the detailed description of the atrezzo item.
     * 
     * @return Atrezzo's detailed description
     */
    public String getDetailedDescription( ) {

        return atrezzo.getDetailedDescription( );
    }

    /**
     * Sets the new documentation of the atrezzo item.
     * 
     * @param documentation
     *            Documentation of the atrezzo item
     */
    public void setDocumentation( String documentation ) {

        controller.addTool( new ChangeDocumentationTool( atrezzo, documentation ) );
    }

    /**
     * Sets the new name of the atrezzo item.
     * 
     * @param name
     *            Name of the atrezzo item
     */
    public void setName( String name ) {

        controller.addTool( new ChangeNameTool( atrezzo, name ) );
    }

    /**
     * Sets the new brief description of the atrezzo item.
     * 
     * @param description
     *            Description of the atrezzo item
     */
    public void setBriefDescription( String description ) {

        controller.addTool( new ChangeDescriptionTool( atrezzo, description ) );
    }

    /**
     * Sets the new detailed description of the atrezzo item.
     * 
     * @param detailedDescription
     *            Detailed description of the atrezzo item
     */
    public void setDetailedDescription( String detailedDescription ) {

        controller.addTool( new ChangeDetailedDescriptionTool( atrezzo, detailedDescription ) );
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
        if( name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameAtrezzoTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldAtrezzoId, references } ) ) ) {

            // Show a dialog asking for the new atrezzo item id
            String newAtrezzoId = name;
            if( name == null )
                newAtrezzoId = controller.showInputDialog( TextConstants.getText( "Operation.RenameAtrezzoTitle" ), TextConstants.getText( "Operation.RenameAtrezzoMessage" ), oldAtrezzoId );

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
            String resourcesPath = currentPath + " >> " + TextConstants.getElementName( Controller.RESOURCES ) + " #" + ( i + 1 );
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

        check( this.getBriefDescription( ), TextConstants.getText( "Search.BriefDescription" ) );
        check( this.getDetailedDescription( ), TextConstants.getText( "Search.DetailedDescription" ) );
        check( this.getDocumentation( ), TextConstants.getText( "Search.Documentation" ) );
        check( this.getId( ), "ID" );
        check( this.getName( ), TextConstants.getText( "Search.Name" ) );
        check( this.getPreviewImage( ), TextConstants.getText( "Search.PreviewImage" ) );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, resourcesDataControlList );
    }

}
