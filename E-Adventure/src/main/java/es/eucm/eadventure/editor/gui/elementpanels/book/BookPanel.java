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
package es.eucm.eadventure.editor.gui.elementpanels.book;

import javax.swing.JComponent;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class BookPanel extends ElementPanel {

    private static final long serialVersionUID = 1L;

    public BookPanel( BookDataControl dataControl ) {

        addTab( new BookContentPanel( dataControl ) );
        addTab( new BookAppPanelTab( dataControl ) );
        addTab( new BookDocPanelTab( dataControl ) );
    }

    private class BookDocPanelTab extends PanelTab {

        private BookDataControl dataControl;

        public BookDocPanelTab( BookDataControl dataControl ) {

            super( TC.get( "Book.Doc" ), dataControl );
            setToolTipText( TC.get( "Book.Doc.Tip" ) );
            this.dataControl = dataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new BookDocPanel( dataControl );
        }
    }

    private class BookAppPanelTab extends PanelTab {

        private BookDataControl dataControl;

        public BookAppPanelTab( BookDataControl dataControl ) {

            super( TC.get( "Book.App" ), dataControl );
            setToolTipText( TC.get( "Book.App.Tip" ) );
            this.dataControl = dataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new BookAppPanel( dataControl );
        }
    }

    private class BookContentPanel extends PanelTab {

        private BookDataControl dataControl;

        public BookContentPanel( BookDataControl dataControl ) {

            super( TC.get( "Book.Contents" ), dataControl );
            setToolTipText( TC.get( "Book.Contents.Tip" ) );
            this.dataControl = dataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            if( dataControl.getType( ) == Book.TYPE_PARAGRAPHS ) {
                return new BookParagraphsPanel( dataControl );
            }
            else {
                return new BookPagesPanel( dataControl );
            }
        }

        @Override
        public DataControl getDataControl( ) {

            if( dataControl.getType( ) == Book.TYPE_PARAGRAPHS ) {
                return dataControl.getBookParagraphsList( );
            }
            return super.getDataControl( );
        }
    }
}
