package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public abstract class ResizeableCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	protected DataControl value;
	
	private int size = 1;
	
	protected String name;
	
	protected Image image;
	
	protected JPanel createPanel() {
		if (image == null)
			image = AssetsController.getImage("img/assets/EmptyImage.png");
		
		if (size == 2 && image.getHeight(null) > 170) {
			int newWidth = (int) ((float) image.getWidth(null) / (float) image.getHeight(null) * 170.0);
			image = image.getScaledInstance(newWidth, 170, Image.SCALE_FAST);
		}
		if (size == 1 && image.getHeight(null) > 70) {
			int newWidth = (int) ((float) image.getWidth(null) / (float) image.getHeight(null) * 70.0);
			image = image.getScaledInstance(newWidth, 70, Image.SCALE_FAST);
		}
		ImageIcon icon = new ImageIcon(image);
		
		
		JButton goToButton = new JButton("Editar");
		goToButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StructureControl.getInstance().changeDataControl(value);
			}
		});

		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		
		if (size == 1 || size == 2) {
			panel.add(new JLabel(name), BorderLayout.NORTH);
			panel.add(new JLabel(icon), BorderLayout.CENTER);
			panel.add(goToButton, BorderLayout.SOUTH);
		} else if (size == 0) {
			panel.add(new JLabel(name), BorderLayout.CENTER);
			panel.add(goToButton, BorderLayout.EAST);
		}
		
		panel.setBackground(Color.WHITE);
		return panel;
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
}
