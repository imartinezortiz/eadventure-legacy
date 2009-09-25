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
package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.SmallActionsTable;

public class SmallActionsListPanel extends ActionsListPanel implements Updateable {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param actionsListDataControl
     *            Actions list controller
     */
    public SmallActionsListPanel( ActionsListDataControl actionsListDataControl ) {

        super( actionsListDataControl );
        this.dataControl = actionsListDataControl;

        setLayout( new BorderLayout( ) );

        table = new SmallActionsTable( dataControl );

        table.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent e ) {

                updateSelectedAction( );
            }
        } );

        add( new JScrollPane( table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );

        //Create the buttons panel (SOUTH)
        JPanel buttonsPanel = new JPanel( );
        JButton newButton = new JButton( new ImageIcon( "img/icons/addNode.png" ) );
        newButton.setContentAreaFilled( false );
        newButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        newButton.setBorder( BorderFactory.createEmptyBorder( ) );
        newButton.setToolTipText( TC.get( "ItemReferenceTable.AddParagraph" ) );
        newButton.addMouseListener( new MouseAdapter( ) {

            @Override
            public void mouseClicked( MouseEvent evt ) {

                JPopupMenu menu = getAddChildPopupMenu( );
                menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
            }
        } );

        deleteButton = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
        deleteButton.setContentAreaFilled( false );
        deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteButton.setBorder( BorderFactory.createEmptyBorder( ) );
        deleteButton.setToolTipText( TC.get( "ItemReferenceTable.Delete" ) );
        deleteButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                delete( );
            }
        } );

        deleteButton.setEnabled( false );
        moveUpButton = new JButton( new ImageIcon( "img/icons/moveNodeUp.png" ) );
        moveUpButton.setContentAreaFilled( false );
        moveUpButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveUpButton.setBorder( BorderFactory.createEmptyBorder( ) );
        moveUpButton.setToolTipText( TC.get( "ItemReferenceTable.MoveUp" ) );
        moveUpButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                moveUp( );
            }
        } );

        moveUpButton.setEnabled( false );
        moveDownButton = new JButton( new ImageIcon( "img/icons/moveNodeDown.png" ) );
        moveDownButton.setContentAreaFilled( false );
        moveDownButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveDownButton.setBorder( BorderFactory.createEmptyBorder( ) );
        moveDownButton.setToolTipText( TC.get( "ItemReferenceTable.MoveDown" ) );
        moveDownButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                moveDown( );
            }
        } );
        moveDownButton.setEnabled( false );

        buttonsPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        buttonsPanel.add( newButton, c );
        c.gridx++;
        buttonsPanel.add( deleteButton, c );
        c.gridx++;
        buttonsPanel.add( moveUpButton, c );
        c.gridx++;
        buttonsPanel.add( moveDownButton, c );

        add( buttonsPanel, BorderLayout.SOUTH );
    }

    private void updateSelectedAction( ) {

        int selectedAction = table.getSelectedRow( );
        if( selectedAction != -1 ) {
            deleteButton.setEnabled( true );
            moveUpButton.setEnabled( dataControl.getActions( ).size( ) > 1 && selectedAction > 0 );
            moveDownButton.setEnabled( dataControl.getActions( ).size( ) > 1 && selectedAction < table.getRowCount( ) - 1 );
        }
        else {
            deleteButton.setEnabled( false );
            moveUpButton.setEnabled( false );
            moveDownButton.setEnabled( false );
        }
    }

    @Override
    public boolean updateFields( ) {

        int selected = table.getSelectedRow( );
        int items = table.getRowCount( );
        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );

        if( items == table.getRowCount( ) ) {
            if( selected != -1 ) {
                table.changeSelection( selected, 0, false, false );
                if( table.getEditorComponent( ) != null )
                    table.editCellAt( selected, table.getEditingColumn( ) );
            }
        }

        return true;
    }

}
