/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.books;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeBookPageTypeTool extends Tool {

    private BookPage bookPage;

    private int newType;

    private int oldType;

    private String oldUri;

    public ChangeBookPageTypeTool( BookPage bookPage, int newType ) {

        this.bookPage = bookPage;
        this.newType = newType;
        this.oldType = bookPage.getType( );
        this.oldUri = bookPage.getUri( );
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

        if( newType != oldType ) {
            bookPage.setType( newType );
            if( newType == BookPage.TYPE_RESOURCE )
                bookPage.setUri( "" );
            else if( newType == BookPage.TYPE_IMAGE )
                bookPage.setUri( "" );
            else
                bookPage.setUri( "http://www." );
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        bookPage.setType( newType );
        if( newType == BookPage.TYPE_RESOURCE )
            bookPage.setUri( "" );
        else if( newType == BookPage.TYPE_IMAGE )
            bookPage.setUri( "" );
        else
            bookPage.setUri( "http://www." );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        bookPage.setType( oldType );
        bookPage.setUri( oldUri );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
