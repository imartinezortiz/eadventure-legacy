package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;

public class MacrosTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	protected MacroListDataControl dataControl;
	
	public MacrosTable (MacroListDataControl dControl){
		super();
		this.dataControl = dControl;
		
		this.setModel( new MacrosTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
				
		this.getColumnModel().getColumn(0).setCellEditor(new StringCellRendererEditor());
		this.getColumnModel().getColumn(0).setCellRenderer(new StringCellRendererEditor());
		
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		this.setSize(200, 150);
	}
	
	
	private class MacrosTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		public int getColumnCount( ) {
			return 1;
		}

		public int getRowCount( ) {
			return dataControl.getMacros().size();
		}
				
		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex == 0)
				return dataControl.getMacros().get(rowIndex).getId();
			return null;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex == 0)
				return TextConstants.getText( "MacrosList.Name" );
			return "";
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 0)
				dataControl.getMacros().get(rowIndex).renameElement((String) value);
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return getSelectedRow() == row;
		}
	}
}
