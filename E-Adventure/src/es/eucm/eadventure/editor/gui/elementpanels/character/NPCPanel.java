package es.eucm.eadventure.editor.gui.elementpanels.character;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DetailedDescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.auxiliar.components.TextPreviewPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class NPCPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the NPC.
	 */
	private NPCDataControl npcDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Text color preview panel.
	 */
	private TextPreviewPanel textPreviewPanel;

	/**
	 * Text field for the name.
	 */
	private JTextField nameTextField;

	/**
	 * Text field for the description.
	 */
	private JTextField descriptionTextField;

	/**
	 * Text field for the detailed description.
	 */
	private JTextField detailedDescriptionTextField;
	

	/**
	 * Combo box to select between the existent voices
	 */
	private JComboBox voicesComboBox;
	
	/**
	 * Text field to get the text to try the current voice in the synthesizer
	 */
	private JTextField trySynthesizer;
	
	/**
	 * Check box to set that the conversation lines of the player must be read by synthesizer
	 */
	private JCheckBox alwaysSynthesizer;

	private JTabbedPane tabPanel;

	private JPanel docPanel;

	private LooksPanel looksPanel;

	/**
	 * Constructor.
	 * 
	 * @param playerDataControl
	 *            Player controller
	 */
	public NPCPanel( NPCDataControl npcDataControl ) {
		// Create the doc panel and layout
		docPanel = new JPanel( );
		docPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints cDoc = new GridBagConstraints( );

		// Set the controller
		this.npcDataControl = npcDataControl;

		// Set the layout
		setLayout( new BorderLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Title" ) ) );
		//GridBagConstraints c = new GridBagConstraints( );
		cDoc.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		cDoc.fill = GridBagConstraints.HORIZONTAL;
		cDoc.weightx = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( npcDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) npcDataControl.getContent()) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Documentation" ) ) );
		docPanel.add( documentationPanel, cDoc );

		// Create the field for the text color
		cDoc.gridy = 1;
		JPanel textColorPanel = new JPanel( );
		textColorPanel.setLayout( new GridLayout( 3, 1, 4, 4 ) );
		textPreviewPanel = new TextPreviewPanel( npcDataControl.getTextFrontColor( ), npcDataControl.getTextBorderColor( ) );
		textColorPanel.add( textPreviewPanel );
		JButton frontColorButton = new JButton( TextConstants.getText( "NPC.FrontColor" ) );
		frontColorButton.addActionListener( new ChangeTextColorListener( ChangeTextColorListener.FRONT_COLOR ) );
		textColorPanel.add( frontColorButton );
		JButton borderColorButton = new JButton( TextConstants.getText( "NPC.BorderColor" ) );
		borderColorButton.addActionListener( new ChangeTextColorListener( ChangeTextColorListener.BORDER_COLOR ) );
		textColorPanel.add( borderColorButton );
		textColorPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.TextColor" ) ) );
		docPanel.add( textColorPanel, cDoc );

		// Create the field for the name
		cDoc.gridy = 2;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( npcDataControl.getName( ) );
		nameTextField.getDocument().addDocumentListener( new NameChangeListener(nameTextField, (Named) npcDataControl.getContent()));
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Name" ) ) );
		docPanel.add( namePanel, cDoc );

		// Create the field for the brief description
		cDoc.gridy = 3;
		JPanel descriptionPanel = new JPanel( );
		descriptionPanel.setLayout( new GridLayout( ) );
		descriptionTextField = new JTextField( npcDataControl.getBriefDescription( ) );
		descriptionTextField.getDocument().addDocumentListener(new DescriptionChangeListener(descriptionTextField, (Described) npcDataControl.getContent()));
		descriptionPanel.add( descriptionTextField );
		descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Description" ) ) );
		docPanel.add( descriptionPanel, cDoc );

		// Create the field for the detailed description
		cDoc.gridy = 4;
		JPanel detailedDescriptionPanel = new JPanel( );
		detailedDescriptionPanel.setLayout( new GridLayout( ) );
		detailedDescriptionTextField = new JTextField( npcDataControl.getDetailedDescription( ) );
		detailedDescriptionTextField.getDocument().addDocumentListener(new DetailedDescriptionChangeListener(detailedDescriptionTextField, (Detailed) npcDataControl.getContent()));
		detailedDescriptionPanel.add( detailedDescriptionTextField );
		detailedDescriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.DetailedDescription" ) ) );
		docPanel.add( detailedDescriptionPanel, cDoc );

		// Create the field for voice selection
		cDoc.gridy = 5;
		JPanel voiceSelection = new JPanel();
		voiceSelection.setLayout(new GridLayout(2,2));
				// Create ComboBox for select the voices
		String[] voices = availableVoices();
		// alan voice doesn't work
		String[] usefullVoices = new String[voices.length-1]; 
		int j=0;
		boolean hasAlan=false;
		for (int k =0; k<voices.length;k++){
			if (k<=usefullVoices.length){
				if (!voices[k].equals("alan")){
					usefullVoices[j] = voices[k];
					j++;
				} else {
					hasAlan = true;
				}
			}
		}
		String[] checkVoices; 
		if (!hasAlan){
			voicesComboBox = new JComboBox(voices);
			checkVoices = voices;
		}
		else{ 
			voicesComboBox = new JComboBox(usefullVoices);
			checkVoices = usefullVoices;
		}
		voicesComboBox.addItemListener(new VoiceComboBoxListener());
		if (npcDataControl.getNPCVoice() != null){
			for (int i =1; i<checkVoices.length;i++)
				if (checkVoices[i].equals(npcDataControl.getNPCVoice()))
					voicesComboBox.setSelectedIndex(i);
		}			
		voiceSelection.add(voicesComboBox);
				// Create CheckBox for select if always synthesizer voices
		alwaysSynthesizer = new JCheckBox(TextConstants.getText("Synthesizer.CheckAlways"));
		alwaysSynthesizer.addItemListener(new VoiceCheckVoxListener());
		alwaysSynthesizer.setSelected(npcDataControl.isAlwaysSynthesizer());
		voiceSelection.add(alwaysSynthesizer);
				// Create a TextField to introduce text to try it in the synthesizer
		trySynthesizer = new JTextField();
		voiceSelection.add(trySynthesizer);
				// Create a Button to take the text and try it in the synthesizer
		JButton playText = new JButton(TextConstants.getText("Synthesizer.ButtonPlay"));
		playText.addActionListener(new VoiceButtonListener());
		voiceSelection.add(playText);
		
		TitledBorder border = BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "Synthesizer.BorderVoices" ), TitledBorder.LEFT, TitledBorder.TOP );
		voiceSelection.setBorder(border);
		docPanel.add(voiceSelection,cDoc);
		
		// Add the tabs
		//Finally, add lookPanel to its scrollPane container, and insert it as a tab along with docPanel
		// Create the panels and layouts
		setLayout( new BorderLayout( ) );
		if( npcDataControl.buildResourcesTab( ) ) {
			tabPanel = new JTabbedPane( );
			looksPanel = new NPCLooksPanel( npcDataControl );
			tabPanel.insertTab( TextConstants.getText( "NPC.LookPanelTitle" ), null, looksPanel, TextConstants.getText( "NPC.LookPanelTip" ), 0 );
			tabPanel.insertTab( TextConstants.getText( "NPC.DocPanelTitle" ), null, docPanel, TextConstants.getText( "NPC.DocPanelTip" ), 1 );
			add( tabPanel, BorderLayout.CENTER );
		} else {
			docPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Title" ) ) );
			add( docPanel, BorderLayout.CENTER );
		}

	}
	
	/**
	 * Return a string array with names of the available voices for synthesizer text
	 * @return
	 * 			string array with names of the available voices for synthesizer text
	 */
	private String[] availableVoices(){
		VoiceManager voiceManager = VoiceManager.getInstance();
		Voice[] availableVoices = voiceManager.getVoices();
		String[] voiceName = new String[availableVoices.length+1];
		voiceName[0] = TextConstants.getText( "Synthesizer.Empty" );
		for (int i=0; i<availableVoices.length;i++)
			voiceName[i+1] = availableVoices[i].getName();
		return voiceName;
	}
	
	/**
	 * Called when the demo synthesizer button has been pressed
	 */
	private class VoiceButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			if (voicesComboBox.getSelectedIndex()!=0 && trySynthesizer.getText()!=null){
				VoiceManager voiceManager = VoiceManager.getInstance();
				Voice voice = voiceManager.getVoice((String)voicesComboBox.getSelectedItem());
				voice.allocate();
				voice.speak(trySynthesizer.getText());
				}
		}
		
	}
	
	
	/**
	 * Called when change the state of checkbox
	 */

	private class VoiceCheckVoxListener implements ItemListener{

		public void itemStateChanged(ItemEvent arg0) {
			npcDataControl.setAlwaysSynthesizer(alwaysSynthesizer.isSelected());
			
		}
		
	}
	
	/**
	 * 
	 * Called when select a field in combo box voiceComboBox
	 *
	 */
	private class VoiceComboBoxListener implements ItemListener{

		public void itemStateChanged(ItemEvent arg0) {
			int selection = voicesComboBox.getSelectedIndex();
			if (selection != 0){
				npcDataControl.setNPCVoice((String)voicesComboBox.getSelectedItem());
			}else 
				npcDataControl.setNPCVoice(new String(""));
		}
		
	}


	/**
	 * Listener for the change color buttons.
	 */
	private class ChangeTextColorListener implements ActionListener {

		/**
		 * Constant for front color.
		 */
		public static final boolean FRONT_COLOR = true;

		/**
		 * Constant for border color.
		 */
		public static final boolean BORDER_COLOR = false;

		/**
		 * True if the front color must change, false if the border color must change.
		 */
		private boolean frontColor;

		/**
		 * Color chooser.
		 */
		private JColorChooser colorChooser;

		/**
		 * Text preview panel.
		 */
		private TextPreviewPanel colorPreviewPanel;

		/**
		 * Constructor.
		 * 
		 * @param frontColor
		 *            Whether the front or border color must be changed
		 */
		public ChangeTextColorListener( boolean frontColor ) {
			this.frontColor = frontColor;

			// Create the color chooser
			colorChooser = new JColorChooser( );

			// Create and add the preview panel, attaching it to the color chooser
			colorPreviewPanel = new TextPreviewPanel( npcDataControl.getTextFrontColor( ), npcDataControl.getTextBorderColor( ) );
			colorPreviewPanel.setPreferredSize( new Dimension( 400, 40 ) );
			colorPreviewPanel.setBorder( BorderFactory.createEmptyBorder( 1, 1, 1, 1 ) );
			colorChooser.setPreviewPanel( colorPreviewPanel );
			colorChooser.getSelectionModel( ).addChangeListener( new UpdatePreviewPanelListener( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Update the color on the color chooser and the preview panel
			colorChooser.setColor( frontColor ? npcDataControl.getTextFrontColor( ) : npcDataControl.getTextBorderColor( ) );
			colorPreviewPanel.setTextFrontColot( npcDataControl.getTextFrontColor( ) );
			colorPreviewPanel.setTextBorderColor( npcDataControl.getTextBorderColor( ) );

			// Create and show the dialog
			JDialog colorDialog = JColorChooser.createDialog( null, TextConstants.getText( frontColor ? "NPC.FrontColor" : "NPC.BorderColor" ), true, colorChooser, new UpdateColorListener( ), null );
			colorDialog.setResizable( false );
			colorDialog.setVisible( true );
		}

		/**
		 * Listener for the "Acept" button of the color chooser dialog.
		 */
		private class UpdateColorListener implements ActionListener {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed( ActionEvent e ) {
				// Update the text color
				if( frontColor ) {
					npcDataControl.setTextFrontColor( colorChooser.getColor( ) );
					textPreviewPanel.setTextFrontColot( colorChooser.getColor( ) );
				} else {
					npcDataControl.setTextBorderColor( colorChooser.getColor( ) );
					textPreviewPanel.setTextBorderColor( colorChooser.getColor( ) );
				}
			}
		}

		/**
		 * Listener for the color preview panel.
		 */
		private class UpdatePreviewPanelListener implements ChangeListener {

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			public void stateChanged( ChangeEvent e ) {
				if( frontColor )
					colorPreviewPanel.setTextFrontColot( colorChooser.getColor( ) );
				else
					colorPreviewPanel.setTextBorderColor( colorChooser.getColor( ) );
			}
		}
	}

	private class NPCLooksPanel extends LooksPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Preview image panel.
		 */
		private ImagePanel imagePanel;

		public NPCLooksPanel( DataControlWithResources control ) {
			super( control );
		}

		@Override
		protected void createPreview( ) {
			npcDataControl = (NPCDataControl) this.dataControl;
			// Take the path to the current image of the player
			String playerImagePath = npcDataControl.getPreviewImage( );

			imagePanel = new ImagePanel( playerImagePath );
			imagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Preview" ) ) );
			lookPanel.add( imagePanel, cLook );
			// TODO Parche, arreglar
			lookPanel.setPreferredSize( new Dimension( 0, 1100 ) );

		}

		@Override
		public void updatePreview( ) {
			imagePanel.loadImage( npcDataControl.getPreviewImage( ) );
			imagePanel.repaint( );
			getParent( ).getParent( ).repaint( );

		}

		public void updateResources( ) {
			super.updateResources( );
			getParent( ).getParent( ).repaint( );
		}

	}
}