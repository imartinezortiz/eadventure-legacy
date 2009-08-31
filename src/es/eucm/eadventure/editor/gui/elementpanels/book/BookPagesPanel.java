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
package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.displaydialogs.StyledBookDialog;
import es.eucm.eadventure.editor.gui.elementpanels.general.TableScrollPane;
import es.eucm.eadventure.editor.gui.otherpanels.BookPagePreviewPanel;
import es.eucm.eadventure.engine.core.gui.GUI;

public class BookPagesPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private BookDataControl dataControl;

    private JPanel pagesPanel;

    private PagesTable pagesTable;

    private JPanel previewPanelContainer;

    private JScrollPane previewPanelScroll;

    private BookPagePreviewPanel previewPanel;

    private JPanel pageNotLoadedPanel;

    private JButton deleteButton;

    private JButton moveUpButton;

    private JButton moveDownButton;

    private JLabel previewLabel;

    private JSplitPane infoAndPreview;

    private JPanel createPageNotLoadedPanel( ) {

        JPanel panel = new JPanel( );

        panel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;

        panel.add( new JLabel( "Page not available" ), c );
        return panel;
    }

    public BookPagesPanel( BookDataControl dControl ) {

        this.dataControl = dControl;

        createPagesPanel( );

        previewPanelContainer = new JPanel( );
        previewPanelContainer.setLayout( new BorderLayout( ) );

        this.pageNotLoadedPanel = this.createPageNotLoadedPanel( );
        previewPanelScroll = new JScrollPane( pageNotLoadedPanel );
        //		previewPanelContainer.add( previewPanelScroll, BorderLayout.CENTER );

        JButton previewButton = new JButton( TextConstants.getText( "Book.Preview" ) );
        previewButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                StyledBookDialog dialog = new StyledBookDialog( dataControl );
                dialog.setVisible( true );
                dialog.updatePreview( );
            }
        } );

        previewPanelContainer.add( previewButton, BorderLayout.SOUTH );
        //updateSelectedPage();

        previewPanelContainer.setMinimumSize( new Dimension( 100, 150 ) );
        pagesPanel.setMinimumSize( new Dimension( 0, 150 ) );

        infoAndPreview = new JSplitPane( JSplitPane.VERTICAL_SPLIT, pagesPanel, previewPanelContainer );
        infoAndPreview.setOneTouchExpandable( true );
        infoAndPreview.setDividerLocation( 150 );
        infoAndPreview.setResizeWeight( 0 );
        infoAndPreview.setDividerSize( 10 );
        infoAndPreview.setContinuousLayout( true );

        setLayout( new BorderLayout( ) );
        add( infoAndPreview, BorderLayout.CENTER );

        if( dataControl.getBookPagesList( ).getSelectedPage( ) != null ) {
            int index = dataControl.getBookPagesList( ).getBookPages( ).indexOf( dataControl.getBookPagesList( ).getSelectedPage( ) );
            pagesTable.changeSelection( index, 0, false, false );
        }
    }

    public void updatePreview( ) {

        BookPage currentPage = dataControl.getBookPagesList( ).getSelectedPage( );
        if( pageNotLoadedPanel != null )
            previewPanelContainer.remove( previewPanelScroll );
        else if( previewLabel != null )
            previewPanelContainer.remove( previewLabel );

        pageNotLoadedPanel = null;
        if( currentPage != null ) {
            //Get the background image
            String backgroundPath = dataControl.getPreviewImage( );
            Image background;
            if( backgroundPath != null && backgroundPath.length( ) > 0 )
                background = AssetsController.getImage( backgroundPath );
            else
                background = null;

            previewPanel = new BookPagePreviewPanel( null, currentPage, background );
            previewPanel.setPreferredSize( new Dimension( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT ) );
            previewPanel.repaint( );
            Image image = previewPanel.paintToImage( );

            previewLabel = new JLabel( );
            previewLabel.setIcon( new ImageIcon( getResizedImage( image ) ) );

            previewPanelContainer.add( previewLabel, BorderLayout.CENTER );

            previewPanelContainer.updateUI( );
        }

    }

    private void updateSelectedPage( ) {

        int selectedPage = pagesTable.getSelectedRow( );
        dataControl.getBookPagesList( ).changeCurrentPage( selectedPage );

        // No valid row is selected
        if( selectedPage < 0 || selectedPage >= dataControl.getBookPagesList( ).getBookPages( ).size( ) ) {
            //Disable delete button
            deleteButton.setEnabled( false );
            //Disable moveUp and moveDown buttons
            moveUpButton.setEnabled( false );
            moveDownButton.setEnabled( false );
            if( pageNotLoadedPanel != null )
                previewPanelContainer.remove( previewPanelScroll );
            else if( previewLabel != null )
                previewPanelContainer.remove( previewLabel );

            pageNotLoadedPanel = this.createPageNotLoadedPanel( );
            previewLabel = null;
            previewPanelScroll = new JScrollPane( pageNotLoadedPanel );
            previewPanelContainer.add( previewPanelScroll, BorderLayout.CENTER );
        }

        //When a page has been selected
        else {
            // Enable delete button
            deleteButton.setEnabled( true );
            //Enable moveUp and moveDown buttons when there is more than one element
            moveUpButton.setEnabled( dataControl.getBookPagesList( ).getBookPages( ).size( ) > 1 && selectedPage > 0 );
            moveDownButton.setEnabled( dataControl.getBookPagesList( ).getBookPages( ).size( ) > 1 && selectedPage < pagesTable.getRowCount( ) - 1 );
            BookPage currentPage = dataControl.getBookPagesList( ).getSelectedPage( );
            if( pageNotLoadedPanel != null )
                previewPanelContainer.remove( previewPanelScroll );
            else if( previewPanel != null )
                previewPanelContainer.remove( previewLabel );

            pageNotLoadedPanel = null;
            //Get the background image
            String backgroundPath = dataControl.getPreviewImage( );
            Image background;
            if( backgroundPath != null && backgroundPath.length( ) > 0 )
                background = AssetsController.getImage( backgroundPath );
            else
                background = null;

            previewPanel = new BookPagePreviewPanel( null, currentPage, background );
            previewPanel.setPreferredSize( new Dimension( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT ) );
            previewPanel.repaint( );
            Image image = previewPanel.paintToImage( );
            previewLabel = new JLabel( );
            previewLabel.setIcon( new ImageIcon( getResizedImage( image ) ) );
            previewPanelContainer.add( previewLabel, BorderLayout.CENTER );
        }

        previewPanelContainer.updateUI( );
        previewPanelScroll.updateUI( );
        previewPanelContainer.repaint( );
        previewPanel.repaint( );
    }

    private Image getResizedImage( Image image ) {

        // set up the transform
        AffineTransform transform = new AffineTransform( );
        int width = previewPanelContainer.getWidth( ) - 20;
        if( width < 350 )
            width = 350;
        int height = previewPanelContainer.getHeight( ) - 20;
        if( height < 250 )
            height = 250;

        transform.scale( width / (double) image.getWidth( null ), height / (double) image.getHeight( null ) );

        // create a transparent (not translucent) image
        Image newImage = new BufferedImage( width, height, BufferedImage.TYPE_3BYTE_BGR );

        // draw the transformed image
        Graphics2D g = (Graphics2D) newImage.getGraphics( );
        g.drawImage( image, transform, null );
        g.dispose( );

        return newImage;
    }

    private void createPagesPanel( ) {

        // Create the main panel
        pagesPanel = new JPanel( );
        pagesPanel.setLayout( new BorderLayout( ) );

        // Create the table (CENTER)
        pagesTable = new PagesTable( dataControl.getBookPagesList( ), this );
        pagesTable.addMouseListener( new MouseAdapter( ) {

            @Override
            public void mousePressed( MouseEvent e ) {

                // By default the JTable only selects the nodes with the left click of the mouse
                // With this code, we spread a new call everytime the right mouse button is pressed
                if( e.getButton( ) == MouseEvent.BUTTON3 ) {
                    // Create new event (with the left mouse button)
                    MouseEvent newEvent = new MouseEvent( e.getComponent( ), e.getID( ), e.getWhen( ), InputEvent.BUTTON1_MASK, e.getX( ), e.getY( ), e.getClickCount( ), e.isPopupTrigger( ) );

                    // Take the listeners and make the calls
                    for( MouseListener mouseListener : e.getComponent( ).getMouseListeners( ) )
                        mouseListener.mousePressed( newEvent );

                }
            }

            @Override
            public void mouseClicked( MouseEvent evt ) {

                if( evt.getButton( ) == MouseEvent.BUTTON3 ) {
                    JPopupMenu menu = getCompletePopupMenu( );
                    menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
                }
            }
        } );
        pagesTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent e ) {

                if( !e.getValueIsAdjusting( ) ) {
                    setCursor( new Cursor( Cursor.WAIT_CURSOR ) );
                    updateSelectedPage( );
                    SwingUtilities.invokeLater( new Runnable( ) {

                        public void run( ) {

                            setCursor( new Cursor( Cursor.DEFAULT_CURSOR ) );
                        }
                    } );
                }
            }
        } );

        pagesPanel.add( new TableScrollPane( pagesTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );

        //Create the buttons panel (SOUTH)
        JPanel buttonsPanel = new JPanel( );
        JButton newButton = new JButton( new ImageIcon( "img/icons/addNode.png" ) );
        newButton.setContentAreaFilled( false );
        newButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        newButton.setBorder( BorderFactory.createEmptyBorder( ) );
        newButton.setToolTipText( TextConstants.getText( "BookPages.AddPage" ) );
        newButton.addMouseListener( new MouseAdapter( ) {

            @Override
            public void mouseClicked( MouseEvent evt ) {

                addPage( );
            }
        } );
        deleteButton = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
        deleteButton.setContentAreaFilled( false );
        deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteButton.setBorder( BorderFactory.createEmptyBorder( ) );
        deleteButton.setToolTipText( TextConstants.getText( "BookPages.DeletePage" ) );
        deleteButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                deletePage( );
            }
        } );
        moveUpButton = new JButton( new ImageIcon( "img/icons/moveNodeUp.png" ) );
        moveUpButton.setContentAreaFilled( false );
        moveUpButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveUpButton.setBorder( BorderFactory.createEmptyBorder( ) );
        moveUpButton.setToolTipText( TextConstants.getText( "BookPages.MovePageUp" ) );
        moveUpButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                movePageUp( );
            }
        } );
        moveDownButton = new JButton( new ImageIcon( "img/icons/moveNodeDown.png" ) );
        moveDownButton.setContentAreaFilled( false );
        moveDownButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveDownButton.setBorder( BorderFactory.createEmptyBorder( ) );
        moveDownButton.setToolTipText( TextConstants.getText( "BookPages.MovePageDown" ) );
        moveDownButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                movePageDown( );
            }
        } );

        buttonsPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        buttonsPanel.add( newButton, c );
        c.gridy = 1;
        buttonsPanel.add( moveUpButton, c );
        c.gridy = 2;
        buttonsPanel.add( moveDownButton, c );
        c.gridy = 4;
        buttonsPanel.add( deleteButton, c );
        c.gridy = 3;
        c.weighty = 2.0;
        c.fill = GridBagConstraints.VERTICAL;
        buttonsPanel.add( new JFiller( ), c );

        pagesPanel.add( buttonsPanel, BorderLayout.EAST );
    }

    /**
     * Returns a popup menu with all the operations.
     * 
     * @return Popup menu with all operations
     */
    public JPopupMenu getCompletePopupMenu( ) {

        //Create the menu
        JPopupMenu completePopupMenu = new JPopupMenu( );

        //Add page item
        JMenuItem addPageIt = new JMenuItem( TextConstants.getText( "TreeNode.AddElement" + Controller.BOOK_PAGE ) );
        addPageIt.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                addPage( );
            }
        } );
        completePopupMenu.add( addPageIt );

        // Separator
        completePopupMenu.addSeparator( );

        // Create and add the delete item
        JMenuItem deleteMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.DeleteElement" ) );
        deleteMenuItem.setEnabled( deleteButton.isEnabled( ) );
        deleteMenuItem.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                deletePage( );
            }
        } );
        completePopupMenu.add( deleteMenuItem );

        // Separator
        completePopupMenu.addSeparator( );

        // Create and add the move up and down item
        JMenuItem moveUpMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.MoveElementUp" ) );
        JMenuItem moveDownMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.MoveElementDown" ) );
        moveUpMenuItem.setEnabled( moveUpButton.isEnabled( ) );
        moveDownMenuItem.setEnabled( moveDownButton.isEnabled( ) );
        moveUpMenuItem.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                movePageUp( );
            }
        } );
        moveDownMenuItem.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                movePageDown( );
            }
        } );
        completePopupMenu.add( moveUpMenuItem );
        completePopupMenu.add( moveDownMenuItem );

        return completePopupMenu;
    }

    private void addPage( ) {

        if( dataControl.getBookPagesList( ).addPage( ) != null ) {
            int selectedRow = pagesTable.getSelectedRow( );
            if( selectedRow != -1 ) {
                selectedRow++;
            }
            else {
                selectedRow = dataControl.getBookPagesList( ).getBookPages( ).size( ) - 1;
            }
            pagesTable.clearSelection( );
            ( (AbstractTableModel) pagesTable.getModel( ) ).fireTableDataChanged( );
            pagesTable.changeSelection( selectedRow, 0, false, false );
        }

    }

    private void deletePage( ) {

        BookPagesListDataControl pagesDataControl = dataControl.getBookPagesList( );
        BookPage bookPage = pagesDataControl.getSelectedPage( );
        if( bookPage != null && pagesDataControl.deletePage( bookPage ) ) {
            pagesTable.clearSelection( );
            ( (AbstractTableModel) pagesTable.getModel( ) ).fireTableDataChanged( );
        }
    }

    private void movePageUp( ) {

        int selectedRow = pagesTable.getSelectedRow( );
        BookPagesListDataControl pagesDataControl = dataControl.getBookPagesList( );
        BookPage bookPage = pagesDataControl.getSelectedPage( );
        if( bookPage != null && pagesDataControl.movePageUp( bookPage ) ) {
            ( (AbstractTableModel) pagesTable.getModel( ) ).fireTableDataChanged( );
            pagesTable.changeSelection( selectedRow - 1, 0, false, false );
        }
    }

    private void movePageDown( ) {

        int selectedRow = pagesTable.getSelectedRow( );
        BookPagesListDataControl pagesDataControl = dataControl.getBookPagesList( );
        BookPage bookPage = pagesDataControl.getSelectedPage( );
        if( bookPage != null && pagesDataControl.movePageDown( bookPage ) ) {
            ( (AbstractTableModel) pagesTable.getModel( ) ).fireTableDataChanged( );
            pagesTable.changeSelection( selectedRow + 1, 0, false, false );
        }
    }

    public BookDataControl getDataControl( ) {

        return dataControl;
    }
}
