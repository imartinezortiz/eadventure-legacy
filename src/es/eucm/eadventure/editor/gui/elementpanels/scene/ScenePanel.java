package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.PlayerPositionDialog;

public class ScenePanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the scene.
	 */
	private SceneDataControl sceneDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Text field for the name.
	 */
	private JTextField nameTextField;

	/**
	 * Initial position button.
	 */
	private JButton initialPositionButton;

	private JPanel docPanel;

	private SceneLooksPanel looksPanel;

	private JTabbedPane tabPanel;
	
	private JCheckBox isAllowPlayerLayer;

	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Scene controller
	 */
	public ScenePanel( SceneDataControl sDataControl ) {

		//Create the doc, look & tab panels and set layouts 
		docPanel = new JPanel( );
		docPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints cDoc = new GridBagConstraints( );
		looksPanel = new SceneLooksPanel( sDataControl );
		tabPanel = new JTabbedPane( );

		// Set the controller
		this.sceneDataControl = sDataControl;

		// Set the layout
		setLayout( new GridBagLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.Title" ) ) );
		//GridBagConstraints c = new GridBagConstraints( );
		cDoc.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		cDoc.fill = GridBagConstraints.BOTH;
		cDoc.weighty = 0.5;
		cDoc.weightx = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( sceneDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) sceneDataControl.getContent()) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.Documentation" ) ) );
		docPanel.add( documentationPanel, cDoc );

		// Create the text field for the name
		cDoc.gridy = 1;
		cDoc.weighty = 0;
		cDoc.fill = GridBagConstraints.HORIZONTAL;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( sceneDataControl.getName( ) );
		nameTextField.getDocument().addDocumentListener( new NameChangeListener(nameTextField, (Named) sceneDataControl.getContent()));
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.Name" ) ) );
		docPanel.add( namePanel, cDoc );

		if (!Controller.getInstance( ).isPlayTransparent( )){
			cDoc.gridy++;
			JPanel useTrajectoryPanel = new JPanel();
			useTrajectoryPanel.setLayout( new GridLayout(0,1));
			JCheckBox useTrajectoryCheckBox = new JCheckBox(TextConstants.getText("Scene.UseTrajectory"), sceneDataControl.getTrajectory().hasTrajectory());
			useTrajectoryCheckBox.addActionListener( new TrajectoryCheckBoxListener() );
			
			useTrajectoryPanel.add(useTrajectoryCheckBox);
			useTrajectoryPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.UseTrajectoryPanel" ) ) );

			docPanel.add( useTrajectoryPanel, cDoc );
		}
		
		cDoc.gridy++;
		JPanel initialPositionPanel = new JPanel( );
		initialPositionPanel.setLayout( new GridLayout( 0, 1 ) );
		JCheckBox initialPositionCheckBox = new JCheckBox( TextConstants.getText( "Scene.UseInitialPosition" ), sceneDataControl.hasDefaultInitialPosition( ) );
		initialPositionCheckBox.addActionListener( new InitialPositionCheckBoxListener( ) );
		
		initialPositionButton = new JButton( TextConstants.getText( "Scene.EditInitialPosition" ) );
		initialPositionButton.setEnabled( sceneDataControl.hasDefaultInitialPosition( ) );
		initialPositionButton.addActionListener( new InitialPositionButtonListener( ) );
		
		if (!Controller.getInstance( ).isPlayTransparent( )){
			initialPositionPanel.add( initialPositionCheckBox );
			initialPositionPanel.add( initialPositionButton );
			initialPositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.DefaultInitialPosition" ) ) );
		}
		
		docPanel.add( initialPositionPanel, cDoc );
		
		cDoc.gridy++;
		JPanel allowPlayerLayer = new JPanel();
		allowPlayerLayer.setLayout(new GridLayout( 0, 1 ) );
		isAllowPlayerLayer = new JCheckBox(TextConstants.getText("Scene.AllowPlayer"),sceneDataControl.isAllowPlayer());
		isAllowPlayerLayer.addActionListener(new PlayerLayerCheckBoxListener());
		allowPlayerLayer.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.AllowPlayerBorder" ) ) );
		allowPlayerLayer.add(isAllowPlayerLayer);
		docPanel.add(allowPlayerLayer, cDoc);
		
		cDoc.gridy++;
		cDoc.weightx = 1;
		cDoc.weighty = 0.5;
		docPanel.add( new JFiller( ), cDoc );

		//Finally, add lookPanel to its scrollPane container, and insert it as a tab along with docPanel

		tabPanel.insertTab( TextConstants.getText( "Scene.LookPanelTitle" ), null, looksPanel, TextConstants.getText( "Scene.LookPanelTip" ), 0 );
		tabPanel.insertTab( TextConstants.getText( "Scene.DocPanelTitle" ), null, docPanel, TextConstants.getText( "Scene.DocPanelTip" ), 1 );
		setLayout( new BorderLayout( ) );
		add( tabPanel, BorderLayout.CENTER );

	}

	/**
	 * Listener for the "Use initial position in this scene" check box.
	 */
	private class InitialPositionCheckBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			sceneDataControl.toggleDefaultInitialPosition( );
			initialPositionButton.setEnabled( sceneDataControl.hasDefaultInitialPosition( ) );
		}

	}
	
	

	/**
	 * Listener for the "Allow player layer" check box.
	 */
	private class PlayerLayerCheckBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			
			sceneDataControl.changeAllowPlayerLayer(isAllowPlayerLayer.isSelected(),looksPanel);
			
			
			
		}

	}
	
	private class TrajectoryCheckBoxListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if (sceneDataControl.getTrajectory().hasTrajectory()) {
				sceneDataControl.getTrajectory().setHasTrajectory(false);
			} else {
				sceneDataControl.getTrajectory().setHasTrajectory(true);
			}
		}
	}
	

	/**
	 * Listener for the "Set default initial position" button
	 */
	private class InitialPositionButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent arg0 ) {
			// Create the dialog with the initial position and show it
			PlayerPositionDialog initialPositionDialog = new PlayerPositionDialog( sceneDataControl.getId( ), sceneDataControl.getDefaultInitialPositionX( ), sceneDataControl.getDefaultInitialPositionY( ) );

			// Set the new data
			sceneDataControl.setDefaultInitialPosition( initialPositionDialog.getPositionX( ), initialPositionDialog.getPositionY( ) );
		}
	}

}