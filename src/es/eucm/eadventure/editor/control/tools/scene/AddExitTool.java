/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author L�pez Ma�as, E., P�rez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fern�ndez-Manj�n, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitsListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;

public class AddExitTool extends Tool {

    private ExitsListDataControl dataControl;

    private IrregularAreaEditionPanel iaep;

    private ExitDataControl newExit;

    public AddExitTool( ExitsListDataControl dataControl2, IrregularAreaEditionPanel iaep ) {

        this.dataControl = dataControl2;
        this.iaep = iaep;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        if( dataControl.addElement( dataControl.getAddableElements( )[0], null ) ) {
            newExit = dataControl.getLastExit( );
            iaep.getScenePreviewEditionPanel( ).addExit( dataControl.getLastExit( ) );
            iaep.repaint( );
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        dataControl.getExits( ).add( newExit );
        dataControl.getExitsList( ).add( (Exit) newExit.getContent( ) );
        iaep.getScenePreviewEditionPanel( ).addExit( newExit );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        dataControl.deleteElement( newExit, false );
        iaep.getScenePreviewEditionPanel( ).removeElement( newExit );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}
