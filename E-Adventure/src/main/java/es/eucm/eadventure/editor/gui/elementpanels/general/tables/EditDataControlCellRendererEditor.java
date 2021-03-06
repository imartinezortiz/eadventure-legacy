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
package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class EditDataControlCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    protected DataControl value;

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int col ) {

        this.value = (DataControl) value;
        JButton button = new JButton( TC.get( "GeneralText.Edit" ) );
        button.setFocusable( false );
        button.setEnabled( isSelected );
        button.addActionListener( new EditButtonActionListener( this.value ) );
        return button;
    }

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        JButton button = new JButton( TC.get( "GeneralText.Edit" ) );
        button.setFocusable( false );
        button.setEnabled( isSelected );
        return button;
    }

    private class EditButtonActionListener implements ActionListener {

        private DataControl dataControl;

        public EditButtonActionListener( DataControl dataControl ) {

            this.dataControl = dataControl;
        }

        public void actionPerformed( ActionEvent e ) {

            StructureControl.getInstance( ).changeDataControl( dataControl );
        }
    }

}
