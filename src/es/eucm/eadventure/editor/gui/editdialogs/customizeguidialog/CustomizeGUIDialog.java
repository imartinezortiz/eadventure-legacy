package es.eucm.eadventure.editor.gui.editdialogs.customizeguidialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;

public class CustomizeGUIDialog extends JDialog{

	private AdventureDataControl dataControl;
	
	public CustomizeGUIDialog (AdventureDataControl dControl){
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "CustomizeGUI.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
		this.dataControl=dControl;
		//Create the tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//Create the cursors panel
		CursorsPanel cursorsPanel= new CursorsPanel (dataControl);
		ButtonsPanel buttonsPanel = new ButtonsPanel (dataControl);
		
		tabbedPane.insertTab( TextConstants.getText( "Cursors.Title" ), null, cursorsPanel, TextConstants.getText( "Cursors.Tip" ), 0 );
		tabbedPane.insertTab( TextConstants.getText( "Buttons.Title" ), null, buttonsPanel, TextConstants.getText( "Buttons.Tip" ), 1 );

		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				setVisible( false );
				dispose( );
			}
		} );
		
		this.getContentPane( ).setLayout( new BorderLayout() );
		this.getContentPane( ).add( tabbedPane, BorderLayout.CENTER );
		Dimension screen = Toolkit.getDefaultToolkit( ).getScreenSize( );
		this.setLocation( (int)screen.getWidth( )/2 -350, (int)screen.getHeight( )/2 -250 );
		this.setSize( 700,500 );
		this.setVisible( true );
	}
	
}