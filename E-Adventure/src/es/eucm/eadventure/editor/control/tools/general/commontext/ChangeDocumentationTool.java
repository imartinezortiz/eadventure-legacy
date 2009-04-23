package es.eucm.eadventure.editor.control.tools.general.commontext;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeDocumentationTool extends Tool {

	private Documented documented;
	
	private String documentation;
	
	private String oldDocumentation;
	
	private Controller controller;
	
	public ChangeDocumentationTool(Documented documented, String documentation) {
		this.documented = documented;
		this.documentation = documentation;
		this.controller = Controller.getInstance();
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean doTool() {
		if( !documentation.equals( documented.getDocumentation( ) ) ) {
			oldDocumentation = documented.getDocumentation();
			documented.setDocumentation( documentation );
			return true;
		}
		return false;
	}

	@Override
	public String getToolName() {
		return "change docuemntation";
	}

	@Override
	public boolean redoTool() {
		documented.setDocumentation( documentation );
		controller.updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		documented.setDocumentation( oldDocumentation );
		controller.updatePanel();
		return true;
	}
	
	@Override
	public boolean combine(Tool other) {
		if (other instanceof ChangeDocumentationTool) {
			ChangeDocumentationTool cnt = (ChangeDocumentationTool) other;
			if (cnt.documented == documented && cnt.oldDocumentation == documentation) {
				documentation = cnt.documentation;
				timeStamp = cnt.timeStamp;
				return true;
			}
		}
		return false;
	}


}