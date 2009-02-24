package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.ChangeRectangleValueTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeDetailedDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeNameTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class BarrierDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference (used to extract the id of the scene).
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
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Parent scene controller
	 * @param activeArea
	 *            Exit of the data control structure
	 */
	public BarrierDataControl( SceneDataControl sceneDataControl, Barrier barrier  ) {
		this.sceneDataControl = sceneDataControl;
		this.barrier = barrier;

		// Create subcontrollers
		conditionsController = new ConditionsController( barrier.getConditions( ) );

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
	 * Returns the name of the item.
	 * 
	 * @return Item's name
	 */
	public String getName( ) {
		return barrier.getName( );
	}

	/**
	 * Returns the brief description of the item.
	 * 
	 * @return Item's description
	 */
	public String getBriefDescription( ) {
		return barrier.getDescription( );
	}

	/**
	 * Returns the detailed description of the item.
	 * 
	 * @return Item's detailed description
	 */
	public String getDetailedDescription( ) {
		return barrier.getDetailedDescription( );
	}
	
	
	/**
	 * Sets the new documentation of the item.
	 * 
	 * @param documentation
	 *            Documentation of the item
	 */
	public void setDocumentation( String documentation ) {
		controller.addTool(new ChangeDocumentationTool(barrier, documentation));
	}

	/**
	 * Sets the new name of the activeArea.
	 * 
	 * @param name
	 *            Name of the activeArea
	 */
	public void setName( String name ) {
		controller.addTool(new ChangeNameTool(barrier, name));
	}

	/**
	 * Sets the new brief description of the activeArea.
	 * 
	 * @param description
	 *            Description of the activeArea
	 */
	public void setBriefDescription( String description ) {
		controller.addTool(new ChangeDescriptionTool(barrier, description));
	}

	/**
	 * Sets the new detailed description of the activeArea.
	 * 
	 * @param detailedDescription
	 *            Detailed description of the activeArea
	 */
	public void setDetailedDescription( String detailedDescription ) {
		controller.addTool(new ChangeDetailedDescriptionTool(barrier, detailedDescription));
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
		controller.addTool(new ChangeRectangleValueTool(barrier, x, y, width, height));
	}

	@Override
	public Object getContent( ) {
		return barrier;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { };
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
	public boolean addElement( int type ) {
		boolean elementAdded = false;
		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
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
		return 0;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		//actionsListDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		//actionsListDataControl.deleteIdentifierReferences( id );
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
	public void recursiveSearch() {
		check(this.getBriefDescription(), TextConstants.getText("Search.BriefDescription"));
		check(this.getConditions(), TextConstants.getText("Search.Conditions"));
		check(this.getDetailedDescription(), TextConstants.getText("Search.DetailedDescription"));
		check(this.getDocumentation(), TextConstants.getText("Search.Documentation"));
		check(this.getId(), "ID");
		check(this.getName(), TextConstants.getText("Search.Name"));
	}

}