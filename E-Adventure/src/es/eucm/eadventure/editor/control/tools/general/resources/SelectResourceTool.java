package es.eucm.eadventure.editor.control.tools.general.resources;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.data.AssetInformation;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;

/**
 * Edition tool for selecting a resource. It supports undo, redo but not combine.
 * @author Javier
 *
 */
public class SelectResourceTool extends ResourcesTool{

	
	public SelectResourceTool(Resources resources,
			AssetInformation[] assetsInformation,
			int resourcesType,
			int index) throws CloneNotSupportedException {
		super(resources, assetsInformation, resourcesType, index);
	}

	@Override
	public boolean doTool() {
		boolean done = false;
		
		String selectedAsset = null;
		AssetChooser chooser = AssetsController.getAssetChooser( assetsInformation[index].category, assetsInformation[index].filter );
		int option = chooser.showAssetChooser( controller.peekWindow( ) );
		//In case the asset was selected from the zip file
		if( option == AssetChooser.ASSET_FROM_ZIP ) {
			selectedAsset = chooser.getSelectedAsset( );
		}

		//In case the asset was not in the zip file: first add it
		else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
			boolean added = AssetsController.addSingleAsset( assetsInformation[index].category, chooser.getSelectedFile( ).getAbsolutePath( ) );
			if( added ) {
				selectedAsset = chooser.getSelectedFile( ).getName( );
			}
		}

		// If a file was selected
		if( selectedAsset != null ) {
			// Take the index of the selected asset
			String[] assetFilenames = AssetsController.getAssetFilenames( assetsInformation[index].category, assetsInformation[index].filter );
			String[] assetPaths = AssetsController.getAssetsList( assetsInformation[index].category, assetsInformation[index].filter );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( selectedAsset ) )
					assetIndex = i;

			// Store the data in the resources block (removing the suffix if necessary)
			if( assetsInformation[index].category == AssetsController.CATEGORY_ANIMATION ){
				done =resources.addAsset( assetsInformation[index].name, AssetsController.removeSuffix( assetPaths[assetIndex] ) );
				
				// For player and character resources block, check if the other animations are set. When any are set, ask the user to set them automatically
				if (resourcesType == Controller.PLAYER || resourcesType == Controller.NPC){
					boolean someAnimationSet = false;
					for (int i=0; i<assetsInformation.length; i++){
						if (i!=index && resources.getAssetPath( assetsInformation[i].name )!= null &&
								!resources.getAssetPath( assetsInformation[i].name ).equals( "" )){
							someAnimationSet = true;
							break;
						}
					}
					
					if (!someAnimationSet &&
							controller.showStrictConfirmDialog( TextConstants.getText( "Operation.SetAllAnimations.Title" ), 
									TextConstants.getText( "Operation.SetAllAnimations.Message" ) )){
						for (int i=0; i<assetsInformation.length; i++){
							if (i!=index){
								done |= resources.addAsset( assetsInformation[i].name, AssetsController.removeSuffix( assetPaths[assetIndex] ) );
							}
						}
					}
				}
			}
			else{
				done = resources.addAsset( assetsInformation[index].name, assetPaths[assetIndex] );
			}
		}
		return done;
	}

	/**
	 * Uses a SelectResourceTool to get the assetPath of a resource belonging to the given category using an asset chooser with
	 * the given filter
	 * @param category
	 * @param filter
	 * @return
	 */
	public static String selectAssetPathUsingChooser ( int category, int filter ){
		String assetPath = null;
		
		Resources resources = new Resources();
		AssetInformation[] assetsInformation= new AssetInformation[1];
		assetsInformation[0] = new AssetInformation("", "marihuanhell", false, category, filter);
		int resourcesType = -10;
		int index = 0;
		try {
			SelectResourceTool tool = new SelectResourceTool(resources, assetsInformation, resourcesType, index);
			tool.doTool();
			if (tool.resources.existAsset("marihuanhell"))
				assetPath = tool.resources.getAssetPath("marihuanhell");
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, true, "Error selecting asset path");
		}
		return assetPath;
	}
	
}