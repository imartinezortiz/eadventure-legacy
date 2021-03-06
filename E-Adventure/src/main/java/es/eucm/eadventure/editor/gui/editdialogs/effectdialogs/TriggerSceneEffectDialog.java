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
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels.ElementImagePanel;
import es.eucm.eadventure.editor.gui.otherpanels.positionpanel.PositionPanel;

/**
 * This class represents a dialog used to add and edit trigger scene effects. It
 * allows to select a scene and an insertion point to it.
 * 
 * @author Bruno Torijano Bueno
 */
public class TriggerSceneEffectDialog extends EffectDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Combo box with the scenes.
     */
    private JComboBox scenesComboBox;

    /**
     * Panel to select and show the position.
     */
    private PositionPanel playerPositionPanel;

    /**
     * Constructor.
     * 
     * @param currentProperties
     *            Set of initial values
     */
    public TriggerSceneEffectDialog( HashMap<Integer, Object> currentProperties ) {

        // Call the super method
        super( TC.get( "TriggerSceneEffect.Title" ), true );

        // Take the list of characters
        String[] scenesArray = controller.getIdentifierSummary( ).getSceneIds( );

        boolean isFirstPersonGame = Controller.getInstance( ).isPlayTransparent( );
        
        // If there is one scene
        if( scenesArray.length > 0 ) {

            // Load the path to the image preview of the player
            String playerPath = controller.getPlayerImagePath( );

            // Create the main panel
            JPanel mainPanel = new JPanel( );
            mainPanel.setLayout( new GridBagLayout( ) );
            GridBagConstraints c = new GridBagConstraints( );

            // Set the border of the panel with the description
            if( !isFirstPersonGame  )
                mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "TriggerSceneEffect.Description" ) ) ) );
            else 
                mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "TriggerSceneEffect.Description.FirstPerson" ) ) ) );
            // Create and add the list of scenes
            c.insets = new Insets( 2, 4, 4, 4 );
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1;
            scenesComboBox = new JComboBox( scenesArray );
            mainPanel.add( scenesComboBox, c );

            // Create and add the panel
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 1;
            c.weightx = 1;
            c.weighty = 1;
            // Set the defualt values (if present)
            if( currentProperties != null ) {
                int x = 0;
                int y = 0;

                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
                    scenesComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );

                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_X ) )
                    x = Integer.parseInt( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_X ) );

                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_Y ) )
                    y = Integer.parseInt( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_Y ) );

                if( x > 5000 )
                    x = 5000;
                if( x < -2000 )
                    x = -2000;
                if( y > 5000 )
                    y = 5000;
                if( y < -2000 )
                    y = -2000;
                playerPositionPanel = new PositionPanel( new ElementImagePanel( null, playerPath ), x, y );
            }
            else {
                // Select the first element
                scenesComboBox.setSelectedIndex( 0 );
                playerPositionPanel = new PositionPanel( new ElementImagePanel( null, playerPath ), 400, 500 );
            }
            playerPositionPanel.loadImage( controller.getSceneImagePath( scenesComboBox.getSelectedItem( ).toString( ) ) );

            if( !isFirstPersonGame )
                mainPanel.add( playerPositionPanel, c );

            // Add the panel to the center
            add( mainPanel, BorderLayout.CENTER );

            scenesComboBox.addActionListener( new ScenesComboBoxActionListener( ) );

            // Set the dialog
            //setResizable( false );
            if( !isFirstPersonGame  )
                setSize( 640, 480 );
            else 
                setSize(400,300);
            Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
            setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
            setVisible( true );
        }

        // If the list had no elements, show an error message
        else
            controller.showErrorDialog( getTitle( ), TC.get( "TriggerSceneEffect.ErrorNoScenes" ) );
    }

    @Override
    protected void pressedOKButton( ) {

        // Create a set of properties, and put the selected value
        properties = new HashMap<Integer, Object>( );
        properties.put( EffectsController.EFFECT_PROPERTY_TARGET, scenesComboBox.getSelectedItem( ).toString( ) );
        properties.put( EffectsController.EFFECT_PROPERTY_X, String.valueOf( playerPositionPanel.getPositionX( ) ) );
        properties.put( EffectsController.EFFECT_PROPERTY_Y, String.valueOf( playerPositionPanel.getPositionY( ) ) );
    }

    /**
     * Listener for the scenes combo box.
     */
    private class ScenesComboBoxActionListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent arg0 ) {

            // Load the image of the selected scene
            playerPositionPanel.loadImage( controller.getSceneImagePath( scenesComboBox.getSelectedItem( ).toString( ) ) );
        }
    }
}
