package es.eucm.eadventure.editor.control.tools.general.conditions;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for deleting a condition
 * @author Javier
 *
 */
public class DeleteConditionTool extends Tool{

	private Conditions conditions;
	private int index1;
	private int index2;
	
	private List<Condition> blockRemoved;
	private Condition singleConditionRemoved;
	
	public DeleteConditionTool (Conditions conditions, int index1, int index2){
		this.conditions = conditions;
		this.index1 = index1;
		this.index2 = index2;
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
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		if (conditions.get(index1).size() == 1){
			this.blockRemoved = conditions.delete(index1);
		} 
		else 
			this.singleConditionRemoved = conditions.get(index1).remove(index2);
		
		Controller.getInstance().updateVarFlagSummary();
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean redoTool() {
		return doTool();
	}

	@Override
	public boolean undoTool() {
		if (blockRemoved!=null){
			conditions.add(index1, blockRemoved);
		} else if (singleConditionRemoved!=null){
			conditions.get(index1).add(index2, singleConditionRemoved);
		}
		Controller.getInstance().updateVarFlagSummary();
		Controller.getInstance().updatePanel();
		return true;
	}
}
