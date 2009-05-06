package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class AuxEditCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private JSplitPane previewAuxSplit;
	
	private int splitPosition;
	
	private String text;
	
	public AuxEditCellRendererEditor(JSplitPane previewAuxSplit, int verticalSplitPosition, String text) {
		this.previewAuxSplit = previewAuxSplit;
		this.splitPosition = verticalSplitPosition;
		this.text = text;
	}

	public Object getCellEditorValue() {
		return null;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		return getComponent(isSelected, table);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		return getComponent(isSelected, table);
	}
	
	private Component getComponent(boolean isSelected, JTable table) {
		JPanel temp = new JPanel();
		if (isSelected)
			temp.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, table.getSelectionBackground()));
		JButton button = new JButton(text);
		button.setFocusable(false);
		button.setEnabled(isSelected);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (previewAuxSplit.getDividerLocation() >= previewAuxSplit.getWidth() - splitPosition)
					previewAuxSplit.setDividerLocation(-splitPosition);
				else
					previewAuxSplit.setDividerLocation(Integer.MAX_VALUE);
			}
		});
		temp.setLayout(new BorderLayout());
		temp.add(button, BorderLayout.CENTER);
		return temp;
	}

}
