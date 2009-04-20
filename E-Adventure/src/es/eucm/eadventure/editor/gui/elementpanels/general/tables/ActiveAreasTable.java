package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import javax.swing.DefaultCellEditor;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ActiveAreasListPanel;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ActiveAreasTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ActiveAreasListDataControl dataControl;
	
	protected IrregularAreaEditionPanel iaep;
	
	protected ScenePreviewEditionPanel spep;
	
	public ActiveAreasTable (ActiveAreasListDataControl dControl, IrregularAreaEditionPanel iaep2, JSplitPane previewAuxSplit){
		super();
		this.spep = iaep2.getScenePreviewEditionPanel();
		this.iaep = iaep2;
		this.dataControl = dControl;
		
		this.setModel( new ElementsTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
		
		this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (getSelectedRow() >= 0) {
					iaep.setRectangular(dataControl.getActiveAreas().get(getSelectedRow()));
					iaep.repaint();
				}
			}
		});
		
		this.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
		this.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));

		this.getColumnModel().getColumn(2).setCellRenderer(new ConditionsCellRendererEditor());
		this.getColumnModel().getColumn(2).setCellEditor(new ConditionsCellRendererEditor());
		
		String text = TextConstants.getText("ActiveAreasList.EditActions");
		this.getColumnModel().getColumn(3).setCellRenderer(new AuxEditCellRendererEditor(previewAuxSplit, ActiveAreasListPanel.VERTICAL_SPLIT_POSITION, text));
		this.getColumnModel().getColumn(3).setCellEditor(new AuxEditCellRendererEditor(previewAuxSplit, ActiveAreasListPanel.VERTICAL_SPLIT_POSITION, text));

		
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.setSize(200, 150);
	}
	
	
	private class ElementsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		public int getColumnCount( ) {
			return 4;
		}

		public int getRowCount( ) {
			return dataControl.getActiveAreas().size();
		}
				
		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex == 0)
				return dataControl.getActiveAreas().get(rowIndex).getId();
			if (columnIndex == 1)
				return dataControl.getActiveAreas().get(rowIndex).getName();
			if (columnIndex == 2)
				return dataControl.getActiveAreas().get(rowIndex).getConditions();
			return null;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex == 0)
				return TextConstants.getText( "ActiveAreasList.Id" );
			if (columnIndex == 1)
				return TextConstants.getText( "ActiveAreasList.Name" );
			if (columnIndex == 2)
				return TextConstants.getText( "ActiveAreasList.Conditions" );
			if (columnIndex == 3)
				return TextConstants.getText( "ActiveAreasList.Actions");
			return "";
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 1) {
				dataControl.getActiveAreas().get(rowIndex).setName((String) value);
			} else if (columnIndex == 0) {
				dataControl.getActiveAreas().get(rowIndex).renameElement((String) value);
			}
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return getSelectedRow() == row;
		}
	}
}
