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
package es.eucm.eadventure.editor.gui.editdialogs.effectdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.gui.displaydialogs.AudioDialog;

public class PlaySoundEffectDialog extends EffectDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Controller of the effects.
     */
    private EffectsController effectsController;

    /**
     * Check box for the background playing of the state.
     */
    private JCheckBox backgroundCheckBox;

    /**
     * Text field containing the path.
     */
    private JTextField pathTextField;

    /**
     * Button to display the actual asset.
     */
    private JButton viewButton;

    /**
     * Constructor.
     * 
     * @param effectsController
     *            Controller of the effects
     * @param currentProperties
     *            Set of initial values
     */
    public PlaySoundEffectDialog( EffectsController effectsController, HashMap<Integer, Object> currentProperties ) {

        // Call the super method
        super( TextConstants.getText( "PlaySoundEffect.Title" ), false );
        this.effectsController = effectsController;

        // Load the image for the delete content button
        Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

        // Create the main panel and set the border
        JPanel mainPanel = new JPanel( );
        mainPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 4, 4, 4, 4 );

        // Set the border of the panel with the description
        mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "PlaySoundEffect.Description" ) ) ) );

        // Create and add the background check box
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 4;
        c.weightx = 1;
        backgroundCheckBox = new JCheckBox( TextConstants.getText( "PlaySoundEffect.BackgroundCheckBox" ) );
        mainPanel.add( backgroundCheckBox, c );

        // Create the delete content button
        JButton deleteContentButton = new JButton( deleteContentIcon );
        deleteContentButton.addActionListener( new DeleteContentButtonActionListener( ) );
        deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
        deleteContentButton.setToolTipText( TextConstants.getText( "Resources.DeleteAsset" ) );
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.weighty = 0;
        mainPanel.add( deleteContentButton, c );

        // Create the text field and insert it
        pathTextField = new JTextField( );
        pathTextField.setEditable( false );
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        mainPanel.add( pathTextField, c );

        // Create the "Selext" button and insert it
        JButton selectButton = new JButton( TextConstants.getText( "Resources.Select" ) );
        selectButton.addActionListener( new ExamineButtonActionListener( ) );
        c.gridx = 2;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        mainPanel.add( selectButton, c );

        // Create the "View" button and insert it
        viewButton = new JButton( TextConstants.getText( "Resources.PlayAsset" ) );
        viewButton.setEnabled( false );
        viewButton.addActionListener( new ViewButtonActionListener( ) );
        c.gridx = 3;
        mainPanel.add( viewButton, c );

        // Add the panel in the center
        add( mainPanel, BorderLayout.CENTER );

        // Set the defualt values (if present)
        if( currentProperties != null ) {
            if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_PATH ) ) {
                pathTextField.setText( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_PATH ) );
                viewButton.setEnabled( pathTextField.getText( ) != null );
            }

            if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_BACKGROUND ) ) {
                backgroundCheckBox.setSelected( Boolean.parseBoolean( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_BACKGROUND ) ) );
            }
        }

        // Set the dialog
        setResizable( false );
        setSize( 400, 180 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
    }

    @Override
    protected void pressedOKButton( ) {

        // Create a set of properties, and put the selected value
        properties = new HashMap<Integer, Object>( );
        properties.put( EffectsController.EFFECT_PROPERTY_PATH, pathTextField.getText( ) );
        properties.put( EffectsController.EFFECT_PROPERTY_BACKGROUND, String.valueOf( backgroundCheckBox.isSelected( ) ) );
    }

    /**
     * Listener for the delete content button.
     */
    private class DeleteContentButtonActionListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            // Delete the current path and disable the view button
            pathTextField.setText( null );
            viewButton.setEnabled( false );
        }
    }

    /**
     * Listener for the examine button.
     */
    private class ExamineButtonActionListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            // Ask the user for an animation
            String newPath = effectsController.selectAsset( EffectsController.ASSET_SOUND );

            // If a new value was selected, set it and enable the view button
            if( newPath != null ) {
                pathTextField.setText( newPath );
                viewButton.setEnabled( true );
            }
        }
    }

    /**
     * Listener for the view button.
     */
    private class ViewButtonActionListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            new AudioDialog( pathTextField.getText( ) );
        }
    }
}
