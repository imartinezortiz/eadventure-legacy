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
package es.eucm.eadventure.editor.data.meta.ims;

import java.util.ArrayList;


public class IMSMetaMetaData {

	//3.4
	private ArrayList<String> metadatascheme;
	
	
	public IMSMetaMetaData (){
		metadatascheme = new ArrayList<String>();
}
	
	
	/***********************************ADD METHODS **************************/
	public void addMetadatascheme(String metadatascheme){
		this.metadatascheme.add(metadatascheme);
	}
	
	/*********************************** SETTERS **************************/
	public void setMetadatascheme(String metadatascheme){
		this.metadatascheme = new ArrayList<String>();
		this.metadatascheme.add(metadatascheme);
	}
	
	/*********************************** GETTERS **************************/
	public String getMetadatascheme(int i){
		return metadatascheme.get(i);
	}
	public String getMetadatascheme(){
		return metadatascheme.get(0);
	}
	
	
}
