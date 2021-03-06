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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.PlayerPositionDialog;

public class NextSceneCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 4870491701696223525L;

    private Object[] items;

    private Object selectedItem;

    private JComboBox combo;

    private ExitDataControl exit;

    private JButton editPosition;

    private JCheckBox hasPosition;

    public NextSceneCellRendererEditor( Object[] items ) {

        this.items = items;
    }

    public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int col ) {

        this.exit = (ExitDataControl) value;
        selectedItem = exit.getNextSceneId( );
        return comboPanel( table );
    }

    public Object getCellEditorValue( ) {

        return exit;
    }

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.exit = (ExitDataControl) value;
        selectedItem = exit.getNextSceneId( );
        if( !isSelected ) {
            return new JLabel( (String) selectedItem );
        }
        else {
            return comboPanel( table );
        }
    }

    private JPanel comboPanel( JTable table ) {

        JPanel tempPanel = new JPanel( );
        tempPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        combo = new JComboBox( items );
        combo.setSelectedItem( selectedItem );
        combo.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                selectedItem = combo.getSelectedItem( );
                exit.setNextSceneId( (String) selectedItem );
                if( hasPosition != null && editPosition != null ) {
                    hasPosition.setEnabled( Controller.getInstance( ).getIdentifierSummary( ).isScene( exit.getNextSceneId( ) ) );
                    editPosition.setEnabled( hasPosition.isEnabled( ) && exit.hasDestinyPosition( ) );
                    hasPosition.setSelected( exit.hasDestinyPosition( ) );
                }
            }
        } );
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 2.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        tempPanel.add( combo, c );

        if( !Controller.getInstance( ).isPlayTransparent( ) ) {

            hasPosition = new JCheckBox( TC.get( "NextSceneCell.UsePosition" ) );
            hasPosition.addActionListener( new DestinyPositionCheckBoxListener( ) );
            hasPosition.setEnabled( Controller.getInstance( ).getIdentifierSummary( ).isScene( exit.getNextSceneId( ) ) );
            hasPosition.setSelected( exit.hasDestinyPosition( ) );

            editPosition = new JButton( TC.get( "NextSceneCell.EditDestinyPosition" ) );
            editPosition.addActionListener( new DestinyPositionButtonListener( ) );
            editPosition.setEnabled( hasPosition.isEnabled( ) && exit.hasDestinyPosition( ) );
            c.gridy++;
            tempPanel.add( hasPosition, c );
            c.gridy++;
            tempPanel.add( editPosition, c );
        }

        tempPanel.setBorder( BorderFactory.createMatteBorder( 2, 2, 2, 0, table.getSelectionBackground( ) ) );
        return tempPanel;
    }

    private class DestinyPositionButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent arg0 ) {

            PlayerPositionDialog destinyPositionDialog = new PlayerPositionDialog( selectedItem.toString( ), exit.getDestinyPositionX( ), exit.getDestinyPositionY( ) );
            exit.setDestinyPosition( destinyPositionDialog.getPositionX( ), destinyPositionDialog.getPositionY( ) );
        }
    }

    /**
     * Listener for the "Use destiny position in this next scene" check box.
     */
    private class DestinyPositionCheckBoxListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            exit.toggleDestinyPosition( );
            editPosition.setEnabled( exit.hasDestinyPosition( ) );
        }
    }

}
