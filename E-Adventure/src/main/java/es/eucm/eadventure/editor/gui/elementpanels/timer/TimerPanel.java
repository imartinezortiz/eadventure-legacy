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
package es.eucm.eadventure.editor.gui.elementpanels.timer;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.timer.TimerDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;

public class TimerPanel extends JPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Controller of the timer.
     */
    private TimerDataControl timerDataControl;

    /**
     * Documentation text area
     */
    private JTextArea documentationTextArea;

    /**
     * End conditions button
     */
    private JButton conditions2Button;

    /**
     * Text field for the display name
     */
    private JTextField displayName;

    /**
     * Check box that set the timer to count-down
     */
    private JCheckBox countDown;

    /**
     * Check box that sets the timer to show when stopped
     */
    private JCheckBox showWhenStopped;

    /**
     * Constructor.
     * 
     * @param timerDataControl
     *            timer controller
     */
    public TimerPanel( TimerDataControl timerDataControl ) {

        this.timerDataControl = timerDataControl;

        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        c.insets = new Insets( 5, 5, 5, 5 );
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.3;
        JPanel documentationPanel = new JPanel( );
        documentationPanel.setLayout( new GridLayout( ) );
        documentationTextArea = new JTextArea( timerDataControl.getDocumentation( ), 4, 0 );
        documentationTextArea.setLineWrap( true );
        documentationTextArea.setWrapStyleWord( true );
        documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
        documentationPanel.add( new JScrollPane( documentationTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) );
        documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Timer.Documentation" ) ) );
        add( documentationPanel, c );

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0;
        c.gridy = 1;
        add( createTimePanel( ), c );

        c.gridy++;
        add( createLoopControlPanel( ), c );

        c.gridy++;
        JPanel conditionsPanel = new JPanel( );
        conditionsPanel.setLayout( new GridLayout( ) );
        JButton conditionsButton = new JButton( TC.get( "GeneralText.EditInitConditions" ) );
        conditionsButton.addActionListener( new InitConditionsButtonListener( ) );
        conditionsPanel.add( conditionsButton );
        conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Timer.InitConditions" ) ) );
        add( conditionsPanel, c );

        c.gridy++;
        JPanel conditions2Panel = new JPanel( );
        conditions2Panel.setLayout( new GridLayout( 2, 1 ) );
        JCheckBox usesEndCondition = new JCheckBox( TC.get( "Timer.UsesEndCondition" ) );
        usesEndCondition.setSelected( timerDataControl.isUsesEndCondition( ) );
        usesEndCondition.addChangeListener( new CheckBoxChangeListener( CheckBoxChangeListener.USESENDCONDITION ) );
        conditions2Panel.add( usesEndCondition );
        conditions2Button = new JButton( TC.get( "GeneralText.EditEndConditions" ) );
        conditions2Button.addActionListener( new EndConditionsButtonListener( ) );
        conditions2Button.setEnabled( timerDataControl.isUsesEndCondition( ) );
        conditions2Panel.add( conditions2Button );
        conditions2Panel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Timer.EndConditions" ) ) );
        add( conditions2Panel, c );

        c.gridy++;
        JPanel allEffectsPanel = new JPanel( );
        allEffectsPanel.setLayout( new GridLayout( 1, 2 ) );

        JPanel effectsPanel = new JPanel( );
        effectsPanel.setLayout( new GridLayout( ) );
        JButton effectsButton = new JButton( TC.get( "GeneralText.EditEffects" ) );
        effectsButton.addActionListener( new EffectsButtonListener( ) );
        effectsPanel.add( effectsButton );
        effectsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Timer.Effects" ) ) );

        allEffectsPanel.add( effectsPanel );

        JPanel postEffectsPanel = new JPanel( );
        postEffectsPanel.setLayout( new GridLayout( ) );
        JButton postEffectsButton = new JButton( TC.get( "GeneralText.EditPostEffects" ) );
        postEffectsButton.addActionListener( new PostEffectsButtonListener( ) );
        postEffectsPanel.add( postEffectsButton );
        postEffectsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Timer.PostEffects" ) ) );

        allEffectsPanel.add( postEffectsPanel );

        add( allEffectsPanel, c );
    }

    private JPanel createTimePanel( ) {

        JPanel timePanel = new JPanel( );
        timePanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        JLabel dnLabel = new JLabel( TC.get( "Timer.DisplayName" ) );
        timePanel.add( dnLabel, c );

        displayName = new JTextField( 8 );
        displayName.setText( timerDataControl.getDisplayName( ) );
        displayName.setEnabled( timerDataControl.isShowTime( ) );
        displayName.getDocument( ).addDocumentListener( new DisplayNameChangesListener( ) );
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx++;
        timePanel.add( displayName, c );

        countDown = new JCheckBox( TC.get( "Timer.CountDown" ) );
        countDown.setSelected( timerDataControl.isCountDown( ) );
        countDown.setEnabled( timerDataControl.isShowTime( ) );
        countDown.addChangeListener( new CheckBoxChangeListener( CheckBoxChangeListener.COUNTDOWN ) );
        c.fill = GridBagConstraints.NONE;
        c.gridx++;
        timePanel.add( countDown, c );

        showWhenStopped = new JCheckBox( TC.get( "Timer.ShowWhenStopped" ) );
        showWhenStopped.setSelected( timerDataControl.isShowWhenStopped( ) );
        showWhenStopped.setEnabled( timerDataControl.isShowTime( ) );
        showWhenStopped.addChangeListener( new CheckBoxChangeListener( CheckBoxChangeListener.SHOWWHENSTOPPED ) );
        c.gridx++;
        timePanel.add( showWhenStopped, c );

        timePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Timer.Time" ) ) );
        return timePanel;
    }

    private JPanel createLoopControlPanel( ) {

        JPanel loopControlPanel = new JPanel( );
        loopControlPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c2 = new GridBagConstraints( );
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.anchor = GridBagConstraints.WEST;
        c2.gridx = 0;
        c2.gridy = 0;
        c2.weightx = 0.1;

        JCheckBox multipleStart = new JCheckBox( TC.get( "Timer.MultipleStarts" ) );
        multipleStart.setSelected( timerDataControl.isMultipleStarts( ) );
        multipleStart.addChangeListener( new CheckBoxChangeListener( CheckBoxChangeListener.MULTIPLESTARTS ) );
        loopControlPanel.add( multipleStart, c2 );
        c2.gridy++;
        c2.weightx = 1.0;

        JTextPane informationTextPane = new JTextPane( );
        informationTextPane.setEditable( false );
        informationTextPane.setBackground( getBackground( ) );
        Font font = informationTextPane.getFont( );
        font = font.deriveFont( 10.0f );
        informationTextPane.setFont( font );
        informationTextPane.setText( TC.get( "Timer.MultipleStartsDesc" ) );
        loopControlPanel.add( informationTextPane, c2 );
        c2.gridy++;
        c2.weightx = 0.1;

        JCheckBox runsInLoop = new JCheckBox( TC.get( "Timer.RunsInLoop" ) );
        runsInLoop.setSelected( timerDataControl.isRunsInLoop( ) );
        runsInLoop.addChangeListener( new CheckBoxChangeListener( CheckBoxChangeListener.RUNSINLOOP ) );
        loopControlPanel.add( runsInLoop, c2 );
        c2.gridy++;
        c2.weightx = 1.0;

        informationTextPane = new JTextPane( );
        informationTextPane.setEditable( false );
        informationTextPane.setBackground( getBackground( ) );
        informationTextPane.setFont( font );
        informationTextPane.setText( TC.get( "Timer.RunsInLoopDesc" ) );
        loopControlPanel.add( informationTextPane, c2 );

        loopControlPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Timer.LoopControl" ) ) );
        return loopControlPanel;
    }

    /**
     * Listener for the edit conditions button.
     */
    private class InitConditionsButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            new ConditionsDialog( timerDataControl.getInitConditions( ) );
        }
    }

    /**
     * Listener for the edit conditions button.
     */
    private class EndConditionsButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            new ConditionsDialog( timerDataControl.getEndConditions( ) );
        }
    }

    /**
     * Listener for the edit effects button.
     */
    private class EffectsButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            new EffectsDialog( timerDataControl.getEffects( ) );
        }
    }

    /**
     * Listener for the edit post-effects button.
     */
    private class PostEffectsButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            new EffectsDialog( timerDataControl.getPostEffects( ) );
        }
    }

    /**
     * Listener for the text area. It checks the value of the area and updates
     * the documentation.
     */
    private class DocumentationTextAreaChangesListener implements DocumentListener {

        public void changedUpdate( DocumentEvent arg0 ) {

            // Do nothing
        }

        public void insertUpdate( DocumentEvent arg0 ) {

            timerDataControl.setDocumentation( documentationTextArea.getText( ) );
        }

        public void removeUpdate( DocumentEvent arg0 ) {

            timerDataControl.setDocumentation( documentationTextArea.getText( ) );
        }
    }

    private class DisplayNameChangesListener implements DocumentListener {

        public void changedUpdate( DocumentEvent arg0 ) {

            // Do nothing
        }

        public void insertUpdate( DocumentEvent arg0 ) {

            timerDataControl.setDisplayName( displayName.getText( ) );
        }

        public void removeUpdate( DocumentEvent arg0 ) {

            timerDataControl.setDisplayName( displayName.getText( ) );
        }
    }

    /**
     * A ChangeListener for all the JCheckBox components in the panel
     */
    private class CheckBoxChangeListener implements ChangeListener {

        public static final int USESENDCONDITION = 0;

        public static final int MULTIPLESTARTS = 1;

        public static final int RUNSINLOOP = 2;

        public static final int SHOW_TIME = 3;

        public static final int COUNTDOWN = 4;

        public static final int SHOWWHENSTOPPED = 5;

        private int type;

        public CheckBoxChangeListener( int type ) {

            this.type = type;
        }

        public void stateChanged( ChangeEvent arg0 ) {

            boolean selected = ( (JCheckBox) arg0.getSource( ) ).isSelected( );
            if( type == USESENDCONDITION ) {
                timerDataControl.setUsesEndCondition( selected );
                conditions2Button.setEnabled( timerDataControl.isUsesEndCondition( ) );
            }
            else if( type == MULTIPLESTARTS ) {
                timerDataControl.setMultipleStarts( selected );
            }
            else if( type == RUNSINLOOP ) {
                timerDataControl.setRunsInLoop( selected );
            }
            else if( type == SHOW_TIME ) {
                timerDataControl.setShowTime( selected );
                if( timerDataControl.isShowTime( ) ) {
                    displayName.setEnabled( true );
                    countDown.setEnabled( true );
                    showWhenStopped.setEnabled( true );
                }
                else {
                    displayName.setEnabled( false );
                    countDown.setEnabled( false );
                    showWhenStopped.setEnabled( false );
                }
            }
            else if( type == COUNTDOWN ) {
                timerDataControl.setCountDown( selected );
            }
            else if( type == SHOWWHENSTOPPED ) {
                timerDataControl.setShowWhenStopped( selected );
            }
        }

    }

}
