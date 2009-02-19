package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.editor.control.Controller;


/**
 * Edition tool for changing the destiny position of a NextScene
 * @author Javier
 *
 */
public class ChangeNSDestinyPositionTool extends ChangePositionTool{

	public ChangeNSDestinyPositionTool(NextScene nextScene, int newX, int newY){
		super (nextScene, newX, newY);
		this.addListener(new ChangePositionToolListener(){

			@Override
			public void positionUpdated(int newX, int newY) {
				Controller.getInstance().reloadPanel();
			}
			
		});
	}
	

}
