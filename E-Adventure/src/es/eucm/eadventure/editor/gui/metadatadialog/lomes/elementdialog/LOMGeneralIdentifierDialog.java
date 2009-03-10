package es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESGeneralId;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMESTextPanel;

public class LOMGeneralIdentifierDialog extends JDialog{

	
	private JTextField catalog;
	
	private JTextField entry;
	
	private String catalogValue;
	
	private String entryValue;
	
	private LOMESContainer container;
	
	public LOMGeneralIdentifierDialog(LOMESContainer container, int selectedItem){
		super( Controller.getInstance( ).peekWindow( ), container.getTitle(), Dialog.ModalityType.APPLICATION_MODAL );
		this.container = container;
		if (selectedItem ==0){
			catalogValue="";
			entryValue="";
		}else {
			catalogValue = ((LOMESGeneralId)this.container.get(selectedItem-1)).getCatalog();
			entryValue = ((LOMESGeneralId)this.container.get(selectedItem-1)).getCatalog();
		}
		
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets = new Insets(2,2,2,2);c.weightx=1;c.fill = GridBagConstraints.BOTH;
		JPanel catalogPanel = new JPanel(new GridBagLayout());
		catalog = new JTextField(catalogValue);
		catalog.getDocument().addDocumentListener(new TextFieldListener (catalog));
		catalogPanel.add(catalog,c);
		catalogPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.GeneralId.CatalogName" ) ) );
		
		JPanel entryPanel = new JPanel(new GridBagLayout());
		entry = new JTextField(entryValue);
		entry.getDocument().addDocumentListener(new TextFieldListener (entry));
		entryPanel.add(entry,c);
		entryPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.GeneralId.EntryName" ) ) );
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints(); 
		c.insets = new Insets(5,5,5,5);c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;c.weightx=1;
		mainPanel.add(catalogPanel,c);
		c.gridy=1;
		mainPanel.add(entryPanel,c);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		c =  new GridBagConstraints(); 
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
			
		});
		buttonPanel.add(ok,c);
		
		this.getContentPane().setLayout(new GridLayout(0,2));
		this.getContentPane().add(mainPanel);
		this.getContentPane().add(buttonPanel);
	
		this.setSize( new Dimension(250,200) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setResizable( false );
		setVisible( true );
	
	}
	
	
	public String getCatalog(){
		return this.catalogValue;
	}
	
	public String getEntry(){
		return this.entryValue;
	}
	
	
	private class TextFieldListener implements DocumentListener {

		private JTextField textField;
		
		public TextFieldListener(JTextField textField){
			this.textField = textField;
		}
		
		public void changedUpdate( DocumentEvent e ) {
			//Do nothing
		}

		public void insertUpdate( DocumentEvent e ) {
			if (textField == catalog){
				catalogValue = textField.getText( );
			}
			else if (textField == entry){
				entryValue = textField.getText( );
			}
		}
		public void removeUpdate( DocumentEvent e ) {
			insertUpdate(e);
		}
		
	}

	
}
