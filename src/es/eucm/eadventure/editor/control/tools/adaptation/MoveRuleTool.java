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
package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveRuleTool extends Tool {

    public static final int MODE_UP = 0;

    public static final int MODE_DOWN = 1;

    private DataControl dataControl;

    private int mode;

    private AdaptationProfileDataControl adaptDataControl;

    public MoveRuleTool( AdaptationProfileDataControl adaptDataControl, DataControl dataControl, int mode ) {

        this.adaptDataControl = adaptDataControl;
        this.dataControl = dataControl;
        this.mode = mode;
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
    public boolean doTool( ) {

        if( mode == MODE_UP )
            return adaptDataControl.moveElementUp( dataControl );
        else if( mode == MODE_DOWN )
            return adaptDataControl.moveElementDown( dataControl );
        else
            return false;
    }

    @Override
    public boolean redoTool( ) {

        boolean done = false;
        if( mode == MODE_UP )
            done = adaptDataControl.moveElementUp( dataControl );
        else if( mode == MODE_DOWN )
            done = adaptDataControl.moveElementDown( dataControl );

        if( done )
            Controller.getInstance( ).updatePanel( );
        return done;
    }

    @Override
    public boolean undoTool( ) {

        boolean done = false;
        if( mode == MODE_UP ) {
            done = adaptDataControl.moveElementDown( dataControl );
        }
        else if( mode == MODE_DOWN ) {
            done = adaptDataControl.moveElementUp( dataControl );

        }

        if( done )
            Controller.getInstance( ).updatePanel( );
        return done;

    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }
}