package es.eucm.eadventure.adventureeditor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.book.BookParagraphsListDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.otherpanels.imagepanels.BookImagePanel;

public class BookParagraphsPanel extends JPanel{
	
	private BookDataControl dataControl;
	
	private JPanel paragraphsPanel;
	
	private ParagraphsTable paragraphsTable;
	
	private JPanel previewPanelContainer;
	
	private BookImagePanel previewPanel;
	
	private JPanel paragraphEditionPanel;
	
	private JPanel paragraphEditionPanelContainer;
	
	private JButton deleteButton;
	
	private JButton moveUpButton;
	
	private JButton moveDownButton;
	
	public BookParagraphsPanel (BookDataControl dControl){
		this.dataControl = dControl;
		
		createParagraphsPanel();
		
		previewPanelContainer = new JPanel();
		previewPanelContainer.setLayout( new BorderLayout() );
		previewPanelContainer.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), TextConstants.getText("BookParagraphs.Preview")) );
		previewPanel = new BookImagePanel(dControl.getPreviewImage( ), dControl.getBookParagraphsList( ));
		paragraphsTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener(){
			public void valueChanged( ListSelectionEvent e ) {
				updateSelectedParagraph();
			}
		});
		
		// Create the info panel
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "BookParagraphs.PreviewDescription" ) );
		previewPanelContainer.add( informationTextPane, BorderLayout.NORTH );
		previewPanelContainer.add( previewPanel, BorderLayout.CENTER );
		
		paragraphEditionPanelContainer = new JPanel();
		paragraphEditionPanelContainer.setLayout( new BorderLayout() );
		
		updateSelectedParagraph();
		
		this.setLayout( new BoxLayout(this, BoxLayout.LINE_AXIS) );
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		paragraphEditionPanelContainer.setPreferredSize( new Dimension(350,250) );
		previewPanelContainer.setPreferredSize( new Dimension(350,250) );
		paragraphsPanel.setPreferredSize( new Dimension (150,0) );
		//paragraphEditionPanelContainer.setMaximumSize( new Dimension(400,250) );
		leftPanel.add( paragraphEditionPanelContainer );
		leftPanel.add( Box.createVerticalStrut(3) );
		leftPanel.add( previewPanelContainer );
		
		this.add( leftPanel );
		this.add( Box.createHorizontalStrut(3) );
		this.add( paragraphsPanel );

	}
	
	private void updateSelectedParagraph(){
		// No valid row is selected
		if (paragraphsTable.getSelectedRow( )<0 || paragraphsTable.getSelectedRow( )>=dataControl.getBookParagraphsList( ).getBookParagraphs( ).size( )){
			paragraphEditionPanel = new JPanel();
			paragraphEditionPanel.setLayout( new GridBagLayout() );
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets( 5, 2, 5, 2 );
			c.anchor = GridBagConstraints.FIRST_LINE_START;

			// Create the info panel
			JTextPane informationTextPane = new JTextPane( );
			informationTextPane.setEditable( false );
			informationTextPane.setBackground( getBackground( ) );
			informationTextPane.setText( TextConstants.getText( "BookParagraph.DescriptionPanel" ) );
			c.fill = GridBagConstraints.VERTICAL;c.weighty=1;
			paragraphEditionPanel.add( informationTextPane, c );

			c.gridy=1; c.anchor = GridBagConstraints.BASELINE;
			paragraphEditionPanel.add( new JLabel(TextConstants.getText("BookParagraph.NotAvailable") ), c );
			paragraphEditionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookParagraph.TitlePanel", new String[]{ "", TextConstants.getText( "BookParagraph.None" )} ) ) );
			
			//Disable delete button
			deleteButton.setEnabled( false );
			//Disable moveUp and moveDown buttons
			moveUpButton.setEnabled( false );
			moveDownButton.setEnabled( false );
		}
		
		//When a paragraph has been selected
		else {
			int selectedParagraph = paragraphsTable.getSelectedRow( );
			BookParagraphDataControl paragraphDataControl = dataControl.getBookParagraphsList( ).getBookParagraphs( ).get( selectedParagraph );
			if (paragraphDataControl.getType( ) == Controller.BOOK_TITLE_PARAGRAPH ||
				paragraphDataControl.getType( ) == Controller.BOOK_TEXT_PARAGRAPH || 
				paragraphDataControl.getType( ) == Controller.BOOK_BULLET_PARAGRAPH){
				paragraphEditionPanel = new TextBookParagraphPanel(paragraphDataControl, selectedParagraph);
			} else if (paragraphDataControl.getType( ) == Controller.BOOK_IMAGE_PARAGRAPH){
				paragraphEditionPanel = new ImageBookParagraphPanel(paragraphDataControl, selectedParagraph);
			}

			// Enable delete button
			deleteButton.setEnabled( true );
			//Enable moveUp and moveDown buttons when there is more than one element
			moveUpButton.setEnabled( dataControl.getBookParagraphsList( ).getBookParagraphs( ).size( )>1 && selectedParagraph>0);
			moveDownButton.setEnabled( dataControl.getBookParagraphsList( ).getBookParagraphs( ).size( )>1 && selectedParagraph<paragraphsTable.getRowCount( )-1 );

		}
		
		paragraphEditionPanelContainer.removeAll( );
		paragraphEditionPanelContainer.add( paragraphEditionPanel, BorderLayout.CENTER );
		paragraphEditionPanelContainer.updateUI( );
		
		previewPanel.updatePreview( );
	}

	private void createParagraphsPanel(){
		// Create the main panel
		paragraphsPanel = new JPanel();
		paragraphsPanel.setLayout( new BorderLayout() );
		
		// Create the info panel (NORTH)
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "BookParagraphs.ListDescription" , Integer.toString( dataControl.getBookParagraphsList( ).getBookParagraphs( ).size( ) ) ));
		paragraphsPanel.add( informationTextPane, BorderLayout.NORTH );
		
		// Create the table (CENTER)
		paragraphsTable = new ParagraphsTable(dataControl.getBookParagraphsList( ));
		paragraphsTable.addMouseListener( new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				// By default the JTable only selects the nodes with the left click of the mouse
				// With this code, we spread a new call everytime the right mouse button is pressed
				if( e.getButton( ) == MouseEvent.BUTTON3 ) {
					// Create new event (with the left mouse button)
					MouseEvent newEvent = new MouseEvent( e.getComponent( ), e.getID( ), e.getWhen( ), MouseEvent.BUTTON1_MASK, e.getX( ), e.getY( ), e.getClickCount( ), e.isPopupTrigger( ) );

					// Take the listeners and make the calls
					for( MouseListener mouseListener : e.getComponent( ).getMouseListeners( ) )
						mouseListener.mousePressed( newEvent );
					
				}
			}
			
			public void mouseClicked(MouseEvent evt){
				if (evt.getButton( ) == MouseEvent.BUTTON3){
					JPopupMenu menu = getCompletePopupMenu();
					menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
				}
			}
		});
		paragraphsPanel.add( new JScrollPane(paragraphsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER );
		
		//Create the buttons panel (SOUTH)
		JPanel buttonsPanel = new JPanel();
		JButton newButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		newButton.setContentAreaFilled( false );
		newButton.setMargin( new Insets(0,0,0,0) );
		newButton.setToolTipText( TextConstants.getText( "BookParagraphs.AddParagraph" ) );
		newButton.addMouseListener( new MouseAdapter(){
			public void mouseClicked (MouseEvent evt){
				JPopupMenu menu= getAddChildPopupMenu();
				menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
			}
		});
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setToolTipText( TextConstants.getText( "BookParagraphs.DeleteParagraph" ) );
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				delete();
			}
		});
		moveUpButton = new JButton(new ImageIcon("img/icons/moveNodeUp.png"));
		moveUpButton.setContentAreaFilled( false );
		moveUpButton.setMargin( new Insets(0,0,0,0) );
		moveUpButton.setToolTipText( TextConstants.getText( "BookParagraphs.MoveParagraphUp" ) );
		moveUpButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				moveUp();
			}
		});
		moveDownButton = new JButton(new ImageIcon("img/icons/moveNodeDown.png"));
		moveDownButton.setContentAreaFilled( false );
		moveDownButton.setMargin( new Insets(0,0,0,0) );
		moveDownButton.setToolTipText( TextConstants.getText( "BookParagraphs.MoveParagraphDown" ) );
		moveDownButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				moveDown();
			}
		});

		buttonsPanel.add( newButton );
		buttonsPanel.add( deleteButton );
		buttonsPanel.add( moveUpButton );
		buttonsPanel.add( moveDownButton );
		
		paragraphsPanel.add( buttonsPanel, BorderLayout.SOUTH );
		paragraphsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BookParagraphs.List" ) ) );
	}
	
	/**
	 * Returns a popup menu with the add operations.
	 * 
	 * @return Popup menu with child adding operations
	 */
	public JPopupMenu getAddChildPopupMenu( ) {
		JPopupMenu addChildPopupMenu = new JPopupMenu( );

		// If the element accepts children
		if( dataControl.getBookParagraphsList( ).getAddableElements( ).length > 0 ) {
			// Add an entry in the popup menu for each type of possible child
			for( int type : dataControl.getBookParagraphsList( ).getAddableElements( ) ) {
				JMenuItem addChildMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.AddElement" + type ) );
				addChildMenuItem.setEnabled( true );
				addChildMenuItem.addActionListener( new AddElementActionListener( type ) );
				addChildPopupMenu.add( addChildMenuItem );
			}
		}

		// If no element can be added, insert a disabled general option
		else {
			JMenuItem addChildMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.AddElement" ) );
			addChildMenuItem.setEnabled( false );
			addChildPopupMenu.add( addChildMenuItem );
		}

		return addChildPopupMenu;
	}
	
	/**
	 * Returns a popup menu with all the operations.
	 * 
	 * @return Popup menu with all operations
	 */
	public JPopupMenu getCompletePopupMenu( ) {
		JPopupMenu completePopupMenu = getAddChildPopupMenu( );

		// Separator
		completePopupMenu.addSeparator( );

		// Create and add the delete item
		JMenuItem deleteMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.DeleteElement" ) );
		deleteMenuItem.setEnabled( deleteButton.isEnabled( ));
		deleteMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				delete( );
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
				moveUp( );
			}
		} );
		moveDownMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				moveDown( );
			}
		} );
		completePopupMenu.add( moveUpMenuItem );
		completePopupMenu.add( moveDownMenuItem );

		return completePopupMenu;
	}

	
	private void delete( ) {
		BookParagraphDataControl paragraph = dataControl.getBookParagraphsList( ).getBookParagraphs( ).get( paragraphsTable.getSelectedRow( ) );
		if (dataControl.getBookParagraphsList( ).deleteElement( paragraph )){
			paragraphsTable.clearSelection( );
			paragraphsTable.updateUI( );
		}
	}

	private void moveUp(){
		int selectedRow = paragraphsTable.getSelectedRow( );
		BookParagraphDataControl paragraph = dataControl.getBookParagraphsList( ).getBookParagraphs( ).get( selectedRow );
		if (dataControl.getBookParagraphsList( ).moveElementUp( paragraph )){
			paragraphsTable.getSelectionModel( ).setSelectionInterval( selectedRow-1, selectedRow-1 );
			paragraphsTable.updateUI( );
		}
	}
	
	private void moveDown(){
		int selectedRow = paragraphsTable.getSelectedRow( );
		BookParagraphDataControl paragraph = dataControl.getBookParagraphsList( ).getBookParagraphs( ).get( selectedRow );
		if (dataControl.getBookParagraphsList( ).moveElementDown( paragraph )){
			paragraphsTable.getSelectionModel( ).setSelectionInterval( selectedRow+1, selectedRow+1 );
			paragraphsTable.updateUI( );
		}
	}
	/**
	 * This class is the action listener for the add buttons of the popup menus.
	 */
	private class AddElementActionListener implements ActionListener {

		/**
		 * Type of element to be created.
		 */
		int type;

		/**
		 * Constructor
		 * 
		 * @param type
		 *            Type of element the listener must call
		 */
		public AddElementActionListener( int type ) {
			this.type = type;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			if (dataControl.getBookParagraphsList( ).addElement( type )){
				
				int selectedRow = paragraphsTable.getSelectedRow( ); 
				//If a row is selected, move the new paragraph up until it is under the selected one
				if (selectedRow>=0 && selectedRow<paragraphsTable.getRowCount( )){
					
					//We need size-2-selectedRow movements
					BookParagraphDataControl newParagraph = dataControl.getBookParagraphsList( ).getLastBookParagraph( );
					for (int i=0; i<paragraphsTable.getRowCount( )-2-paragraphsTable.getSelectedRow( ); i++){
						dataControl.getBookParagraphsList( ).moveElementUp( newParagraph );
					}
					
					//Set the new paragraph selected
					paragraphsTable.clearSelection( );
					paragraphsTable.getSelectionModel( ).setSelectionInterval( selectedRow+1, selectedRow+1 );
				} 
				
				paragraphsTable.updateUI( );
			}
		}
	}


}
