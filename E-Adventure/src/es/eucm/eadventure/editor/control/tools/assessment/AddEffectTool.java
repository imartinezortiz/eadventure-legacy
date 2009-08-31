/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fern�ndez-Manj�n, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control.tools.assessment;

import es.eucm.eadventure.common.data.assessment.TimedAssessmentEffect;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition Tool for adding an effect in a timedAssessmentRule
 * @author Javier
 *
 */
public class AddEffectTool extends Tool{

	protected TimedAssessmentRule containsAS;
	
	protected TimedAssessmentEffect oldEffectBlock;
	
	protected int index;
	
	protected int mode;
	
	public AddEffectTool (TimedAssessmentRule rule, int index){
		this.containsAS = rule;
		this.index = index;
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return oldEffectBlock!=null;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		containsAS.addEffect( );
		oldEffectBlock = containsAS.getLastEffect();
		return true;
	}

	@Override
	public boolean redoTool() {
		containsAS.getEffects().add( oldEffectBlock );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		containsAS.getEffects().remove( oldEffectBlock );
		Controller.getInstance().updatePanel();
		return true;
	}

}
