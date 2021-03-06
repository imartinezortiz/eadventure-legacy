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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.gui.auxiliar.JPositionedDialog;

public class BookTypesDialog extends JPositionedDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the option selected for the style of the GUI;
     */
    private int optionSelected;

    /**
     * Radio button for the "Simple content" option.
     */
    private JRadioButton simpleRadioButton;

    /**
     * Radio button for the "Formatted" option.
     */
    private JRadioButton formattedRadioButton;

    /**
     * Constructor.
     * 
     * @param optionSelected
     *            GUI style active
     */
    public BookTypesDialog( int optionSelected ) {

        // Set the values
        super( Controller.getInstance( ).peekWindow( ), TC.get( "BookTypes.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
        this.optionSelected = optionSelected;

        // Panel with the report options
        JPanel bookTypesPanel = new JPanel( );
        bookTypesPanel.setLayout( new GridBagLayout( ) );
        bookTypesPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "BookTypes.Title" ) ) );

        // Traditional radio button
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 10, 10, 3, 10 );
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        simpleRadioButton = new JRadioButton( TC.get( "BookTypes.Simple" ) );
        bookTypesPanel.add( simpleRadioButton, c );

        // Traditional description
        c.insets = new Insets( 0, 10, 10, 10 );
        c.gridy = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        JTextPane xmlReportInfo = new JTextPane( );
        xmlReportInfo.setEditable( false );
        xmlReportInfo.setBackground( getContentPane( ).getBackground( ) );
        xmlReportInfo.setText( TC.get( "BookTypes.SimpleDescription" ) );
        bookTypesPanel.add( xmlReportInfo, c );

        // Contextual radio button
        c.insets = new Insets( 10, 10, 3, 10 );
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        formattedRadioButton = new JRadioButton( TC.get( "BookTypes.Formatted" ) );
        bookTypesPanel.add( formattedRadioButton, c );

        // Contextual description
        c.insets = new Insets( 0, 10, 10, 10 );
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        JTextPane htmlReportInfo = new JTextPane( );
        htmlReportInfo.setEditable( false );
        htmlReportInfo.setBackground( getContentPane( ).getBackground( ) );
        htmlReportInfo.setText( TC.get( "BookTypes.FormattedDescription" ) );
        bookTypesPanel.add( htmlReportInfo, c );

        // Panel with the buttons
        JPanel buttonsPanel = new JPanel( );
        buttonsPanel.setLayout( new FlowLayout( ) );
        JButton btnLoad = new JButton( TC.get( "GeneralText.OK" ) );
        btnLoad.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                setVisible( false );
                dispose( );
            }
        } );
        buttonsPanel.add( btnLoad );
        JButton btnCancel = new JButton( TC.get( "GeneralText.Cancel" ) );
        btnCancel.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                invalidateOptionSelected( );
                setVisible( false );
                dispose( );
            }
        } );
        buttonsPanel.add( btnCancel );

        // Add the principal and the buttons panel
        add( bookTypesPanel, BorderLayout.CENTER );
        add( buttonsPanel, BorderLayout.SOUTH );

        // Configure the radio buttons
        simpleRadioButton.addActionListener( new OptionChangedListener( ) );
        formattedRadioButton.addActionListener( new OptionChangedListener( ) );
        ButtonGroup guiButtonGroup = new ButtonGroup( );
        guiButtonGroup.add( simpleRadioButton );
        guiButtonGroup.add( formattedRadioButton );
        if( optionSelected == Book.TYPE_PARAGRAPHS )
            simpleRadioButton.setSelected( true );
        else if( optionSelected == Book.TYPE_PAGES )
            formattedRadioButton.setSelected( true );

        // If the dialog is being closed, disable the selection option
        addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosing( WindowEvent e ) {

                invalidateOptionSelected( );
                setVisible( false );
                dispose( );
            }
        } );

        // Set size and position and show the dialog
        setSize( 400, 320 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        //setResizable( false );
        setVisible( true );
    }

    /**
     * Returns the option that has been selected in the dialog.
     * 
     * @return Option selected: 0 for traditional, 1 for contextual, -1 if the
     *         dialog was closed or the "Cancel" button pressed
     */
    public int getOptionSelected( ) {

        return optionSelected;
    }

    /**
     * Invalidates the option selected (when the "Cancel" button is pressed or
     * the dialog is closed).
     */
    private void invalidateOptionSelected( ) {

        optionSelected = -1;
    }

    /**
     * Listener for the radio buttons.
     */
    private class OptionChangedListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            if( e.getSource( ).equals( simpleRadioButton ) )
                optionSelected = Book.TYPE_PARAGRAPHS;
            else if( e.getSource( ).equals( formattedRadioButton ) )
                optionSelected = Book.TYPE_PAGES;
        }
    }

}
