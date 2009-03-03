package es.eucm.eadventure.editor.control.controllers;

import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.ims.IMSDataControl;
import es.eucm.eadventure.editor.control.controllers.lom.LOMDataControl;
import es.eucm.eadventure.editor.control.tools.AddCursorTool;
import es.eucm.eadventure.editor.control.tools.DeleteCursorTool;
import es.eucm.eadventure.editor.control.tools.SelectButtonTool;
import es.eucm.eadventure.editor.control.tools.SelectCursorPathTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeTitleTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeIntegerValueTool;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomButton;
import es.eucm.eadventure.common.data.adventure.CustomCursor;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;

/**
 * This class holds all the information of the adventure, including the chapters and the configuration of the HUD.
 * 
 * @author Bruno Torijano Bueno
 */
public class AdventureDataControl {

	/**
	 * The whole data of the adventure
	 */
	private AdventureData adventureData;
	
	/**
	 * Controller for LOM data (only required when
	 * exporting games to LOM)
	 */
	private LOMDataControl lomController;
	
	/**
	 * Controller for IMS data (only required when exporting
	 * games to SCORM)
	 */
	private IMSDataControl imsController;
	
	/**
	 * Assessment file data controller
	 */
	private AssessmentProfilesDataControl assessmentProfilesDataControl;
	
	/**
	 * Adaptation file data controller
	 */
	private AdaptationProfilesDataControl adaptationProfilesDataControl;

	/**
	 * Constructs the data control with the adventureData
	 */
	public AdventureDataControl( AdventureData data ){
		this( );
		adventureData = data;

		// add profiles subcontrollers
		for ( AssessmentProfile profile: data.getAssessmentProfiles() ){
			assessmentProfilesDataControl.getProfiles().add(new AssessmentProfileDataControl(profile))	;
		}
		
		for ( AdaptationProfile profile: data.getAdaptationProfiles() ){
			adaptationProfilesDataControl.getProfiles().add(new AdaptationProfileDataControl(profile))	;
		}
		

	}
	
	/**
	 * Empty constructor. Sets all values to null.
	 */
	public AdventureDataControl( ) {
		adventureData = new AdventureData();
		lomController = new LOMDataControl();
		imsController = new IMSDataControl();
		assessmentProfilesDataControl = new AssessmentProfilesDataControl();
		adaptationProfilesDataControl = new AdaptationProfilesDataControl();
	}

	/**
	 * Constructor which creates an adventure data with default title and description, traditional GUI and one empty
	 * chapter (with a scene).
	 * 
	 * @param adventureTitle
	 *            Default title for the adventure
	 * @param chapterTitle
	 *            Default title for the chapter
	 * @param sceneId
	 *            Default identifier for the scene
	 */
	public AdventureDataControl( String adventureTitle, String chapterTitle, String sceneId, int playerMode ) {
		adventureData = new AdventureData( );
		adventureData.setTitle(adventureTitle);
		adventureData.setDescription("");
		adventureData.setGUIType( DescriptorData.GUI_CONTEXTUAL );
		adventureData.setPlayerMode( playerMode );
		adventureData.addChapter( new Chapter( chapterTitle, sceneId ) );
		lomController = new LOMDataControl();
		imsController = new IMSDataControl();
		assessmentProfilesDataControl = new AssessmentProfilesDataControl();
		adaptationProfilesDataControl = new AdaptationProfilesDataControl();
	}

	public AdventureDataControl( String adventureTitle, String chapterTitle, String sceneId ) {
		this( adventureTitle, chapterTitle, sceneId, DescriptorData.MODE_PLAYER_3RDPERSON );
	}

	/**
	 * Constructor gith given parameters.
	 * 
	 * @param title
	 *            Title of the adventure
	 * @param description
	 *            Description of the adventure
	 * @param guiType
	 *            Type of the GUI
	 * @param chapters
	 *            Chapters of the adventure
	 */
	public AdventureDataControl( String title, String description, List<Chapter> chapters ) {
		adventureData = new AdventureData();
		adventureData.setTitle(title);
		adventureData.setDescription(description);
		adventureData.setGUIType( DescriptorData.GUI_TRADITIONAL );
		adventureData.setChapters( chapters );
		adventureData.setGraphicConfig( DescriptorData.GRAPHICS_WINDOWED);
		adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_3RDPERSON );
		 
		lomController = new LOMDataControl();
		imsController = new IMSDataControl();
	}

    public boolean isCursorTypeAllowed( String type ){
    	return isCursorTypeAllowed(DescriptorData.getCursorTypeIndex( type ));
    }

    public boolean isCursorTypeAllowed( int type ){
    	return DescriptorData.typeAllowed[adventureData.getGUIType()][type];
    }

	
	/**
	 * Returns the title of the adventure
	 * 
	 * @return Adventure's title
	 */
	public String getTitle( ) {
		return adventureData.getTitle();
	}

	/**
	 * Returns the description of the adventure.
	 * 
	 * @return Adventure's description
	 */
	public String getDescription( ) {
		return adventureData.getDescription();
	}

	/**
	 * Returns the GUI type of the adventure.
	 * 
	 * @return Adventure's GUI type
	 */
	public int getGUIType( ) {
		return adventureData.getGUIType();
	}

	/**
	 * Returns the list of chapters of the adventure.
	 * 
	 * @return Adventure's chapters list
	 */
	public List<Chapter> getChapters( ) {
		return adventureData.getChapters();
	}

	/**
	 * Sets the title of the adventure.
	 * 
	 * @param title
	 *            New title for the adventure
	 */
	public void setTitle( String title ) {
		Controller.getInstance().addTool(new ChangeTitleTool(adventureData, title));
	}

	/**
	 * Sets the description of the adventure.
	 * 
	 * @param description
	 *            New description for the adventure
	 */
	public void setDescription( String description ) {
		Controller.getInstance().addTool(new ChangeDescriptionTool(adventureData, description));
	}

	/**
	 * Sets the GUI type of the adventure.
	 * 
	 * @param guiType
	 *            New GUI type for the adventure
	 */
	public void setGUIType( int guiType ) {
		Controller.getInstance().addTool( new ChangeIntegerValueTool(adventureData, guiType, "getGUIType", "setGUIType") );
	}

	/**
	 * @return the playerMode
	 */
	public int getPlayerMode( ) {
		return adventureData.getPlayerMode();
	}

	/**
	 * @param playerMode the playerMode to set
	 */
	public void setPlayerMode( int playerMode ) {
		Controller.getInstance().addTool( new ChangeIntegerValueTool(adventureData, playerMode, "getPlayerMode", "setPlayerMode") );
	}
	
    public List<CustomCursor> getCursors(){
        return adventureData.getCursors();
    }
    
    public List<CustomButton> getButtons(){
        return adventureData.getButtons();
    }

    
    public String getCursorPath(String type){
        for (CustomCursor cursor: adventureData.getCursors()){
            if (cursor.getType( ).equals( type )){
                return cursor.getPath( );
            }
        }
        return null;
    }
    
    public String getCursorPath(int type){
        return getCursorPath(DescriptorData.getCursorTypeString(type));
    }
    
    public void deleteCursor(int type){
    	String typeS = DescriptorData.getCursorTypeString(type);
    	int position=-1;
    	for (int i=0; i<adventureData.getCursors().size( ); i++){
    		if (adventureData.getCursors().get(i).getType( ).equals( typeS )){
    			position= i;break;
    		}
    	}
    	if (position>=0){
    		Controller.getInstance().addTool( new DeleteCursorTool(adventureData, position));
    	}
    }

    public void editCursorPath(int t){
    	try {
			Controller.getInstance().addTool( new SelectCursorPathTool( adventureData, t  ) );
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, false, "Could not clone cursor-adventureData");
		}
    }

	/**
	 * @return the lomController
	 */
	public LOMDataControl getLomController( ) {
		return lomController;
	}

	/**
	 * @param lomController the lomController to set
	 */
	public void setLomController( LOMDataControl lomController ) {
		this.lomController = lomController;
	}
	
	/**
	 * @return the imsController
	 */
	public IMSDataControl getImsController() {
		return imsController;
	}

	/**
	 * @param imsController the imsController to set
	 */
	public void setImsController(IMSDataControl imsController) {
		this.imsController = imsController;
	}

	/**
	 * @return the assessmentRulesListDataControl
	 */
	public AssessmentProfilesDataControl getAssessmentRulesListDataControl( ) {
		return assessmentProfilesDataControl;
	}

	/**
	 * @return the adaptationRulesListDataControl
	 */
	public AdaptationProfilesDataControl getAdaptationRulesListDataControl( ) {
		return adaptationProfilesDataControl;
	}

	public boolean isCommentaries() {
		return adventureData.isCommentaries();
	}

	public void setCommentaries(boolean commentaries) {
		Controller.getInstance().addTool(new ChangeBooleanValueTool(adventureData, commentaries, "isCommentaries","setCommentaries"));
	}
	
	public int getGraphicConfig() {
		return adventureData.getGraphicConfig();
	}
	 
	public void setGraphicConfig(int graphicConfig) {
		Controller.getInstance().addTool(new ChangeIntegerValueTool(adventureData, graphicConfig, "getGraphicConfig","setGraphicConfig"));
	}

	/**
	 * @return the adventureData
	 */
	public AdventureData getAdventureData() {
		return adventureData;
	}
	
	public void setButton(String action, String type, String path) {
		CustomButton button = new CustomButton(action, type, path);
		CustomButton temp = null;
		for (CustomButton cb : adventureData.getButtons()) {
			if (cb.equals(button))
				temp = cb;
		}
		if (temp != null)
			adventureData.getButtons().remove(temp);
		adventureData.addButton(button);
	}

	public String getButtonPath(String action, String type) {
		CustomButton button = new CustomButton(action, type, null);
		for (CustomButton cb : adventureData.getButtons()) {
			if (cb.equals(button))
				return cb.getPath();
		}
		return null;
	}

	public void deleteButton(String action, String type) {
		CustomButton button = new CustomButton(action, type, null);
		CustomButton temp = null;
		for (CustomButton cb : adventureData.getButtons()) {
			if (cb.equals(button))
				temp = cb;
		}
		if (temp != null)
			adventureData.getButtons().remove(temp);
	}

	public void editButtonPath(String action, String type) {
		/*String selectedButton = null;
		AssetChooser chooser = AssetsController.getAssetChooser( AssetsController.CATEGORY_BUTTON, AssetsController.FILTER_NONE );
		int option = chooser.showAssetChooser( Controller.getInstance().peekWindow( ) );
		//In case the asset was selected from the zip file
		if( option == AssetChooser.ASSET_FROM_ZIP ) {
			selectedButton = chooser.getSelectedAsset( );
		}

		//In case the asset was not in the zip file: first add it
		else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
			boolean added = AssetsController.addSingleAsset( AssetsController.CATEGORY_BUTTON, chooser.getSelectedFile( ).getAbsolutePath( ) );
			if( added ) {
				selectedButton = chooser.getSelectedFile( ).getName( );
			}
		}

		// If a file was selected
		if( selectedButton != null ) {
			// Take the index of the selected asset
			String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_BUTTON );
			String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_BUTTON );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( selectedButton ) )
					assetIndex = i;

			this.setButton(action, type, assetPaths[assetIndex]);			
			
			Controller.getInstance( ).dataModified( );
		}*/
		try {
			Controller.getInstance().addTool( new SelectButtonTool( adventureData, action, type ) );
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, false, "Could not clone resources: buttons");
		}
	}
}