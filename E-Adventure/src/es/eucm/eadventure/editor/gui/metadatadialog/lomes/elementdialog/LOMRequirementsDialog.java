/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fern�ndez-Manj�n, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleContribute;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleDate;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMOrComposite;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMESCreateComposeTypePanel;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMVocabularyPanel;


public class LOMRequirementsDialog extends JDialog{

	
	private LOMVocabularyPanel type;
    
	private LOMVocabularyPanel name;
	
	private JTextField minimumVersion;
    
	private JTextField maximumVersion;
	
	private Vocabulary typeValue;

	private Vocabulary nameValue;
	
	private String maxValue;
	
	private String minValue;
	
	private LOMESContainer container;
	

	
	public LOMRequirementsDialog(LOMESContainer container, int selectedItem){
		super( Controller.getInstance( ).peekWindow( ), container.getTitle(), Dialog.ModalityType.APPLICATION_MODAL );
		
		Controller.getInstance().pushWindow(this);
		
		this.container = container;
		
		if (selectedItem ==0){
			
		    typeValue=new Vocabulary(Vocabulary.TE_TYPE_4_4_1_1);
		    nameValue= new Vocabulary(Vocabulary.TE_NAME_4_4_1_2);
		    maxValue = Controller.getInstance().getEditorMinVersion();
		    minValue = Controller.getInstance().getEditorVersion();
		}else {
		    
		    typeValue=((LOMOrComposite)container.get(selectedItem-1)).getType();
		    nameValue= ((LOMOrComposite)container.get(selectedItem-1)).getName();
		    maxValue = ((LOMOrComposite)container.get(selectedItem-1)).getMaximumVersion();
		    minValue = ((LOMOrComposite)container.get(selectedItem-1)).getMinimumVersion();
		}
		
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets = new Insets(2,2,2,2);c.weightx=1;c.fill = GridBagConstraints.BOTH;
		JPanel typePanel = new JPanel(new GridBagLayout());
		type = new LOMVocabularyPanel(LOMOrComposite.getTypeOptions(),typeValue.getValueIndex());
		typePanel.add(type,c);
		typePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.Technical.Type" ) ) );
		
		JPanel namePanel = new JPanel(new GridBagLayout());
		name = new LOMVocabularyPanel(LOMOrComposite.getNameOptions(),nameValue.getValueIndex());
		namePanel.add(name,c);
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.Technical.Name" ) ) );
		
		
		JPanel maxPanel = new JPanel(new GridBagLayout());
		maximumVersion = new JTextField(maxValue);
		maximumVersion.setEditable(false);
		//maximumVersion.getDocument().addDocumentListener(new TextFieldListener (maximumVersion));
		maxPanel.add(maximumVersion,c);
		maxPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOM.Technical.MaximumVersion" ) ) );
		
		JPanel minPanel = new JPanel(new GridBagLayout());
		minimumVersion = new JTextField(minValue);
		minimumVersion.setEditable(false);
		//minimumVersion.getDocument().addDocumentListener(new TextFieldListener minimumVersion));
		minPanel.add(minimumVersion,c);
		minPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOM.Technical.MinimumVersion" ) ) );
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		c =  new GridBagConstraints(); 
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
			    typeValue.setValueIndex(type.getSelection());
			    nameValue.setValueIndex(name.getSelection());
			    	dispose();
				
			}
			
		});
		buttonPanel.add(ok,c);
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints(); 
		c.insets = new Insets(5,5,5,5);c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;c.weightx=1;
		mainPanel.add(typePanel,c);
		c.gridy=1;
		mainPanel.add(namePanel,c);
		c.gridy=2;
		mainPanel.add(maxPanel,c);
		c.gridy=3;
		mainPanel.add(minPanel,c);
		c.gridy=4;
		mainPanel.add(buttonPanel,c);
		
		
		
		this.getContentPane().setLayout(new GridBagLayout());
		this.getContentPane().add(mainPanel);
		//this.getContentPane().add(buttonPanel);
	
		this.setSize( new Dimension(250,320) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setResizable( false );
		setVisible( true );
		
		
		addWindowListener( new WindowAdapter (){
			@Override
			public void windowClosed(WindowEvent e) {
				Controller.getInstance().popWindow();
				
			}
			
		});
	
	}


	/**
	 * @return the maxValue
	 */
	public String getMaxValue() {
	    return maxValue;
	}

	/**
	 * @return the typeValue
	 */
	public Vocabulary getTypeValue() {
	    return typeValue;
	}



	/**
	 * @return the nameValue
	 */
	public Vocabulary getNameValue() {
	    return nameValue;
	}



	/**
	 * @return the minValue
	 */
	public String getMinValue() {
	    return minValue;
	}
	

	
	
	
}
