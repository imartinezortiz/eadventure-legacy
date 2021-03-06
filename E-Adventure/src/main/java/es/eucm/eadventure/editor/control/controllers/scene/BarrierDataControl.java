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
package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DescriptionsController;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionContextProperty;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionOwner;
import es.eucm.eadventure.editor.control.tools.general.ChangeRectangleValueTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDetailedDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeNameTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class BarrierDataControl extends DataControl {

    /**
     * Scene controller that contains this element reference (used to extract
     * the id of the scene).
     */
    private SceneDataControl sceneDataControl;

    /**
     * Contained barrier.
     */
    private Barrier barrier;

    /**
     * Conditions controller.
     */
    private ConditionsController conditionsController;
    
    /**
     * Controller for descriptions
     */
    private DescriptionsController descriptionController;

    /**
     * Constructor.
     * 
     * @param sceneDataControl
     *            Parent scene controller
     * @param activeArea
     *            Exit of the data control structure
     */
    public BarrierDataControl( SceneDataControl sceneDataControl, Barrier barrier ) {

        this.sceneDataControl = sceneDataControl;
        this.barrier = barrier;

        // Create subcontrollers
        HashMap<String, ConditionContextProperty> context1 = new HashMap<String, ConditionContextProperty>( );
        ConditionOwner parent = new ConditionOwner( Controller.SCENE, sceneDataControl.getId( ) );
        ConditionOwner owner = new ConditionOwner( Controller.BARRIER, barrier.getId( ), parent );

        context1.put( ConditionsController.CONDITION_OWNER, owner );

        conditionsController = new ConditionsController( barrier.getConditions( ), context1 );
        
        descriptionController = new DescriptionsController(barrier.getDescriptions( ));
        
        //Barriers can only have name, and only one description, so we set selectedDEscription to 0
        descriptionController.setSelectedDescription( 0 );

    }

    /**
     * Returns the id of the scene that contains this element reference.
     * 
     * @return Parent scene id
     */
    public String getParentSceneId( ) {

        return sceneDataControl.getId( );
    }

    /**
     * Returns the id of the item.
     * 
     * @return Item's id
     */
    public String getId( ) {

        return barrier.getId( );
    }

    /**
     * Returns the documentation of the item.
     * 
     * @return Item's documentation
     */
    public String getDocumentation( ) {

        return barrier.getDocumentation( );
    }
    
    /**
     * Sets the new documentation of the item.
     * 
     * @param documentation
     *            Documentation of the item
     */
    public void setDocumentation( String documentation ) {

        controller.addTool( new ChangeDocumentationTool( barrier, documentation ) );
    }

    /**
     * Sets the new name of the activeArea.
     * 
     * @param name
     *            Name of the activeArea
     */
    public void setName( String name ) {

        controller.addTool( new ChangeNameTool( descriptionController.getSelectedDescription( ), name ) );
    }

    /**
     * Sets the new brief description of the activeArea.
     * 
     * @param description
     *            Description of the activeArea
     */
    public void setBriefDescription( String description ) {

        controller.addTool( new ChangeDescriptionTool( descriptionController.getSelectedDescription( ), description ) );
    }

    /**
     * Sets the new detailed description of the activeArea.
     * 
     * @param detailedDescription
     *            Detailed description of the activeArea
     */
    public void setDetailedDescription( String detailedDescription ) {

        controller.addTool( new ChangeDetailedDescriptionTool( descriptionController.getSelectedDescription( ), detailedDescription ) );
    }

    /**
     * Returns the X coordinate of the upper left position of the exit.
     * 
     * @return X coordinate of the upper left point
     */
    public int getX( ) {

        return barrier.getX( );
    }

    /**
     * Returns the Y coordinate of the upper left position of the exit.
     * 
     * @return Y coordinate of the upper left point
     */
    public int getY( ) {

        return barrier.getY( );
    }

    /**
     * Returns the width of the exit.
     * 
     * @return Width of the exit
     */
    public int getWidth( ) {

        return barrier.getWidth( );
    }

    /**
     * Returns the height of the exit.
     * 
     * @return Height of the exit
     */
    public int getHeight( ) {

        return barrier.getHeight( );
    }

    /**
     * Sets the new values for the exit.
     * 
     * @param x
     *            X coordinate of the upper left point
     * @param y
     *            Y coordinate of the upper left point
     * @param width
     *            Width of the exit area
     * @param height
     *            Height of the exit area
     */
    public void setBarrier( int x, int y, int width, int height ) {

        controller.addTool( new ChangeRectangleValueTool( barrier, x, y, width, height ) );
    }

    @Override
    public Object getContent( ) {

        return barrier;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] {};
    }

    @Override
    public boolean canAddElement( int type ) {

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

        return false;
    }

    @Override
    public boolean addElement( int type, String id ) {

        boolean elementAdded = false;
        return elementAdded;
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        boolean elementDeleted = false;
        return elementDeleted;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        return elementMoved;
    }

    @Override
    public String renameElement( String name ) {

        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        ConditionsController.updateVarFlagSummary( varFlagSummary, barrier.getConditions( ) );
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        return true;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // DO nothing
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Delete the references from the actions
        // Do nothing
    }

    @Override
    public int countIdentifierReferences( String id ) {

        return conditionsController.countIdentifierReferences( id );
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        conditionsController.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        conditionsController.deleteIdentifierReferences( id );
    }

    /**
     * Returns the conditions of the element reference.
     * 
     * @return Conditions of the element reference
     */
    public ConditionsController getConditions( ) {

        return conditionsController;
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

       // barriers have no brief descriptions
        // check( this.getBriefDescription( ), TC.get( "Search.BriefDescription" ) );
        check( this.getConditions( ), TC.get( "Search.Conditions" ) );
     // barriers have no detailed descriptions
        //check( this.getDetailedDescription( ), TC.get( "Search.DetailedDescription" ) );
        check( this.getDocumentation( ), TC.get( "Search.Documentation" ) );
       // check( this.getId( ), "ID" );
        //Barriers have no name
        //check( this.getName( ), TC.get( "Search.Name" ) );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return null;
    }

}
