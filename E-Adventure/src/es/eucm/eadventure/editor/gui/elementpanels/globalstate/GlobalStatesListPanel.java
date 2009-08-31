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
package es.eucm.eadventure.editor.gui.elementpanels.globalstate;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.tools.globalstate.AddGlobalStateTool;
import es.eucm.eadventure.editor.control.tools.globalstate.DeleteGlobalStateTool;
import es.eucm.eadventure.editor.control.tools.globalstate.DuplicateGlobalStateTool;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.elementpanels.general.TableScrollPane;

public class GlobalStatesListPanel extends JPanel implements DataControlsPanel, Updateable {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    private static final int HORIZONTAL_SPLIT_POSITION = 100;

    private GlobalStateListDataControl dataControl;

    private JPanel globalStateInfoPanel;

    private JTable table;

    private JButton deleteButton;

    private JButton duplicateButton;

    /**
     * Constructor.
     * 
     * @param gloabalStatesListDataControl
     *            Scenes list controller
     */
    public GlobalStatesListPanel( GlobalStateListDataControl gloabalStatesListDataControl ) {

        this.dataControl = gloabalStatesListDataControl;
        setLayout( new BorderLayout( ) );

        globalStateInfoPanel = new JPanel( );
        globalStateInfoPanel.setLayout( new BorderLayout( ) );

        JPanel tablePanel = createTablePanel( );

        JSplitPane tableWithSplit = new JSplitPane( JSplitPane.VERTICAL_SPLIT, tablePanel, globalStateInfoPanel );
        tableWithSplit.setOneTouchExpandable( true );
        tableWithSplit.setDividerLocation( HORIZONTAL_SPLIT_POSITION );
        tableWithSplit.setContinuousLayout( true );
        tableWithSplit.setResizeWeight( 0 );
        tableWithSplit.setDividerSize( 10 );

        add( tableWithSplit, BorderLayout.CENTER );
    }

    private JPanel createTablePanel( ) {

        JPanel tablePanel = new JPanel( );

        table = new GlobalStatesTable( dataControl );
        JScrollPane scroll = new TableScrollPane( table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        scroll.setMinimumSize( new Dimension( 0, HORIZONTAL_SPLIT_POSITION ) );

        table.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                if( table.getSelectedRow( ) >= 0 ) {
                    deleteButton.setEnabled( true );
                    duplicateButton.setEnabled( true );
                }
                else {
                    deleteButton.setEnabled( false );
                    duplicateButton.setEnabled( false );
                }
                updateInfoPanel( table.getSelectedRow( ) );
                deleteButton.repaint( );
            }
        } );

        JPanel buttonsPanel = new JPanel( );
        JButton newButton = new JButton( new ImageIcon( "img/icons/addNode.png" ) );
        newButton.setContentAreaFilled( false );
        newButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        newButton.setBorder( BorderFactory.createEmptyBorder( ) );
        newButton.setToolTipText( TextConstants.getText( "GlobalStatesList.AddGlobalState" ) );
        newButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                addGlobalState( );
            }
        } );
        deleteButton = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
        deleteButton.setContentAreaFilled( false );
        deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteButton.setBorder( BorderFactory.createEmptyBorder( ) );
        deleteButton.setToolTipText( TextConstants.getText( "GlobalStatesList.DeleteGlobalState" ) );
        deleteButton.setEnabled( false );
        deleteButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                deleteGlobalState( );
            }
        } );
        duplicateButton = new JButton( new ImageIcon( "img/icons/duplicateNode.png" ) );
        duplicateButton.setContentAreaFilled( false );
        duplicateButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        duplicateButton.setBorder( BorderFactory.createEmptyBorder( ) );
        duplicateButton.setToolTipText( TextConstants.getText( "GlobalStatesList.DuplicateGlobalState" ) );
        duplicateButton.setEnabled( false );
        duplicateButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                duplicateGlobalState( );
            }
        } );

        buttonsPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        buttonsPanel.add( newButton, c );
        c.gridy = 1;
        buttonsPanel.add( duplicateButton, c );
        c.gridy = 3;
        buttonsPanel.add( deleteButton, c );
        c.gridy = 2;
        c.weighty = 2.0;
        c.fill = GridBagConstraints.VERTICAL;
        buttonsPanel.add( new JFiller( ), c );

        tablePanel.setLayout( new BorderLayout( ) );
        tablePanel.add( scroll, BorderLayout.CENTER );
        tablePanel.add( buttonsPanel, BorderLayout.EAST );

        return tablePanel;
    }

    public void updateInfoPanel( int row ) {

        globalStateInfoPanel.removeAll( );
        if( row >= 0 ) {
            GlobalStateDataControl globalState = dataControl.getGlobalStates( ).get( row );
            JPanel timerPanel = new GlobalStatePanel( globalState );
            globalStateInfoPanel.add( timerPanel );
        }
        globalStateInfoPanel.updateUI( );
    }

    protected void addGlobalState( ) {

        Controller.getInstance( ).addTool( new AddGlobalStateTool( dataControl ) );
        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
        table.changeSelection( dataControl.getGlobalStates( ).size( ) - 1, 0, false, false );
    }

    protected void duplicateGlobalState( ) {

        Controller.getInstance( ).addTool( new DuplicateGlobalStateTool( dataControl, table.getSelectedRow( ) ) );
        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
        table.changeSelection( dataControl.getGlobalStates( ).size( ) - 1, 0, false, false );
    }

    protected void deleteGlobalState( ) {

        Controller.getInstance( ).addTool( new DeleteGlobalStateTool( dataControl, table.getSelectedRow( ) ) );
        table.clearSelection( );
        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
    }

    public void setSelectedItem( List<Searchable> path ) {

        if( path.size( ) > 0 ) {
            for( int i = 0; i < dataControl.getGlobalStates( ).size( ); i++ ) {
                if( dataControl.getGlobalStates( ).get( i ) == path.get( path.size( ) - 1 ) )
                    table.changeSelection( i, i, false, false );
            }
        }
    }

    public boolean updateFields( ) {

        int selected = table.getSelectedRow( );
        int items = table.getRowCount( );
        if( table.getCellEditor( ) != null )
            table.getCellEditor( ).cancelCellEditing( );

        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );

        if( items > 0 && items == dataControl.getGlobalStates( ).size( ) ) {
            if( selected != -1 && selected < table.getRowCount( ) ) {
                table.changeSelection( selected, 0, false, false );
                updateInfoPanel( table.getSelectedRow( ) );
            }
        }
        return true;
    }

}
