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
package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMTaxonPath extends LOMESContainer{

	
	public LOMTaxonPath(){
		super();
	}
	
	public LOMTaxonPath(LangString source,LOMTaxon taxon){
		this();
		add(new LOMClassificationTaxonPath(source,taxon));
	}
	
	public void addTaxonPath(LangString source,LOMTaxon taxon){
		add(new LOMClassificationTaxonPath(source,taxon));
	}

	public void addTaxonPath(LangString source,LOMTaxon taxon,int index){
		if (index ==0){
			add(new LOMClassificationTaxonPath(source,taxon));
		}else {
		    delete(index-1);
		    add(new LOMClassificationTaxonPath(source,taxon),index-1);

		}
	}
	
	
	public String[] elements(){
		String[] elements= new String[container.size()];
		for (int i=0; i<container.size();i++)
			elements[i] = ((LOMClassificationTaxonPath)container.get(i)).getSource().getValue(0);
		return elements;
	}

	@Override
	public String getTitle() {
		return TextConstants.getText( "LOMES.ClassificationTaxonPath.DialogTitle" );
	}

	@Override
	public String[] attributes() {
		LOMESGeneralId generalId = new LOMESGeneralId();
		return generalId.attributes();
	}
	
	
}

