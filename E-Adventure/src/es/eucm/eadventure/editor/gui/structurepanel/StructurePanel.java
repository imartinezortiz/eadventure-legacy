package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.tools.structurepanel.AddElementTool;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.AdaptationControllerStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.AdvancedFeaturesListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.AssessmentControllerStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.AtrezzoListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.BooksListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ChapterStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ConversationsListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.CutscenesListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ItemsListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.NPCsListStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.PlayerStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.ScenesListStructureElement;

public class StructurePanel extends JPanel implements DataControlsPanel {

	private static final long serialVersionUID = -1768584184321389780L;
	
	private static final int INCREMENT = 5;
	
	private static final int UNSELECTED_BUTTON_HEIGHT = 35;

	/**
	 * The container in which the edition panel will be placed.
	 */
	private Container editorContainer;
	
	protected int selectedElement;
	
	protected int selectedListItem = -1;
	
	protected List<StructureListElement> structureElements;
	
	protected JTable list;
	
	protected JButton button;
		
	private Image image;
	
	private Image basicImage;
	
	private Image tempImage;
	
	private int increment;

	private int top;
	
	private int bottom;
	
	private int cont;
	
	public StructurePanel(Container editorContainer) {
		this.editorContainer = editorContainer;
		this.selectedElement = 0;
		this.setLayout(new StructurePanelLayout());
		structureElements = new ArrayList<StructureListElement>();
		this.addComponentListener(new ComponentListener() {
			public void componentHidden(ComponentEvent arg0) {
			}

			public void componentMoved(ComponentEvent arg0) {
			}

			public void componentResized(ComponentEvent arg0) {
				if (getWidth() > 0 && getHeight() > 0)
					basicImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			}

			public void componentShown(ComponentEvent arg0) {
			}
		});
		update();
	}
	
	public void recreateElements() {
		ChapterDataControl chapterDataControl = Controller.getInstance().getSelectedChapterDataControl( );
		structureElements.clear();
		if (chapterDataControl != null) {
			structureElements.add(new ChapterStructureElement(chapterDataControl));
			structureElements.add(new ScenesListStructureElement(chapterDataControl.getScenesList()));
			structureElements.add(new CutscenesListStructureElement(chapterDataControl.getCutscenesList()));
			structureElements.add(new BooksListStructureElement(chapterDataControl.getBooksList()));
			structureElements.add(new ItemsListStructureElement(chapterDataControl.getItemsList()));
			structureElements.add(new AtrezzoListStructureElement(chapterDataControl.getAtrezzoList()));
			structureElements.add(new PlayerStructureElement(chapterDataControl.getPlayer()));
			structureElements.add(new NPCsListStructureElement(chapterDataControl.getNPCsList()));
			structureElements.add(new ConversationsListStructureElement(chapterDataControl.getConversationsList()));
			structureElements.add(new AdvancedFeaturesListStructureElement(Controller.getInstance().getAdvancedFeaturesController()));
			structureElements.add(new AdaptationControllerStructureElement(Controller.getInstance().getAdaptationController()));
			structureElements.add(new AssessmentControllerStructureElement(Controller.getInstance().getAssessmentController()));
		}
		update();
	}
	
	public void update(int oldIndex, final int newIndex) {
		if (oldIndex != newIndex && oldIndex != -1) {
			this.setEnabled(false);
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			increment = 10;
			top = 0;
			bottom = 0;
			cont = 0;
			
			if (newIndex > oldIndex) {
				if (this.structureElements.get(oldIndex).getDataControl().getAddableElements().length > 0) {
					increment = -INCREMENT;
					if (this.structureElements.get(newIndex).getDataControl().getAddableElements().length > 0) {
						bottom = this.getHeight() - UNSELECTED_BUTTON_HEIGHT * (structureElements.size() - newIndex - 1);
						top = bottom - UNSELECTED_BUTTON_HEIGHT * (newIndex - oldIndex);
						int reach = UNSELECTED_BUTTON_HEIGHT * (oldIndex + 1);
						cont = - (top - reach) / increment;
					} else {
						bottom = this.getHeight();
						top = bottom - UNSELECTED_BUTTON_HEIGHT * (this.structureElements.size() - oldIndex - 1);
						int reach = UNSELECTED_BUTTON_HEIGHT * (oldIndex + 1);
						cont = - (top - reach) / increment;
					}
				} else {
					if (this.structureElements.get(newIndex).getDataControl().getAddableElements().length > 0) {
						increment = INCREMENT;
						top = UNSELECTED_BUTTON_HEIGHT * newIndex  + 40;
						bottom = top + UNSELECTED_BUTTON_HEIGHT * (this.structureElements.size() - newIndex - 1);
						int reach = this.getHeight() - UNSELECTED_BUTTON_HEIGHT * (this.structureElements.size() - newIndex + 1);
						cont = - (top - reach) / increment;
					} else {
						cont = 0;
					}
				}
			} else {
				if (this.structureElements.get(oldIndex).getDataControl().getAddableElements().length > 0) {
					if (this.structureElements.get(newIndex).getDataControl().getAddableElements().length > 0) {
						increment = INCREMENT;
						top = UNSELECTED_BUTTON_HEIGHT * newIndex + UNSELECTED_BUTTON_HEIGHT;
						bottom = top + UNSELECTED_BUTTON_HEIGHT * (oldIndex - newIndex - 1) + 40;
						int reach = this.getHeight() - UNSELECTED_BUTTON_HEIGHT * (this.structureElements.size() - newIndex);
						cont = - (top - reach) / increment;
					} else {
						increment = -INCREMENT;
						top = this.getHeight() - UNSELECTED_BUTTON_HEIGHT * (structureElements.size() - oldIndex - 1);
						bottom = this.getHeight();
						int reach = UNSELECTED_BUTTON_HEIGHT * (oldIndex + 1);
						cont = - (top - reach) / increment;
					}
				} else {
					if (this.structureElements.get(newIndex).getDataControl().getAddableElements().length > 0) {
						increment = INCREMENT;
						top = UNSELECTED_BUTTON_HEIGHT * newIndex + UNSELECTED_BUTTON_HEIGHT;
						bottom = UNSELECTED_BUTTON_HEIGHT * this.structureElements.size() - UNSELECTED_BUTTON_HEIGHT + 40;
						int reach = this.getHeight() - UNSELECTED_BUTTON_HEIGHT * (this.structureElements.size() - newIndex);
						cont = - (top - reach) / increment;
					} else {
						cont = 0;
					}
				}
				
			}

			if (this.getHeight() > 0 && this.getWidth() > 0 && increment != 0 && cont != 0) {
				image = basicImage;
				this.paint(image.getGraphics());
				if (bottom - top > 0) {
					tempImage = new BufferedImage(this.getWidth(), bottom - top, BufferedImage.TYPE_4BYTE_ABGR);
					tempImage.getGraphics().drawImage(image, 0, 0, this.getWidth(), bottom - top, 0, top, this.getWidth(), bottom, null);
				} else {
					image = null;
					cont = 0;
				}
			}
			selectedElement = newIndex;
			new Thread( new Runnable() {
				public void run() {
					removeAll();
					setIgnoreRepaint(true);
					for (int i = 0; i < cont; i++) {
						Graphics2D g2 = (Graphics2D) getGraphics();
						Graphics g = image.getGraphics();
						g.setColor(StructurePanel.this.getBackground());
						if (i > 0)
							g.fillRect(0, top + increment * (i - 1), getWidth(), bottom - top);
						g.drawImage(tempImage, 0, top + increment * i, null);
						g2.drawImage(image, 0, 0, null);
						g2.finalize();
						try {
							Thread.sleep(700 / cont );
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					image = null;
					update();
					list.requestFocusInWindow();
					setEnabled(true);
					setIgnoreRepaint(false);
					SwingUtilities.invokeLater(new Runnable()
					{
					    public void run()
					    {
							setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					    }
					});
				}
			}).start( );
			SwingUtilities.invokeLater(new Runnable()
			{
			    public void run()
			    {
					if (editorContainer != null) {
					    editorContainer.removeAll();
					    editorContainer.add(structureElements.get(newIndex).getEditPanel());
					    StructureControl.getInstance().visitDataControl(structureElements.get(newIndex).getDataControl());
					    editorContainer.validate( );
					    editorContainer.repaint( );
					}
			    }
			});

		} else {
			update();
			selectedElement = newIndex;
			if (editorContainer != null) {
			    editorContainer.removeAll();
			    editorContainer.add(structureElements.get(newIndex).getEditPanel());
			    StructureControl.getInstance().visitDataControl(structureElements.get(newIndex).getDataControl());
			    editorContainer.validate( );
			    editorContainer.repaint( );
			}
		}
		
	}
	
	public void update() {
		int i = 0;
		removeAll();
		
		for (StructureListElement element : structureElements) {
			if (i == selectedElement)
				add(createSelectedElementPanel(element, i), new Integer(element.getChildCount() != 0 || element.getDataControl().getAddableElements().length > 0 ? -1 : 40));
			else {
				button = new JButton(element.getName(), element.getIcon());
				button.setHorizontalAlignment(SwingConstants.LEFT);
				Border b1 = BorderFactory.createRaisedBevelBorder();
		        Border b2 = BorderFactory.createEmptyBorder(3, 10, 3, 10);
		        button.setBorder(BorderFactory.createCompoundBorder(b1,b2));
		        button.setContentAreaFilled(false);
				button.addActionListener(new ElementButtonActionListener(i));
				button.setFocusable(false);
				if (i < selectedElement)
					add(button, new Integer(UNSELECTED_BUTTON_HEIGHT));
				else if (i > selectedElement)
					add(button, new Integer(UNSELECTED_BUTTON_HEIGHT));
			} 
			i++;
		}
		this.updateUI();
	}
	
	protected JPanel createSelectedElementPanel(final StructureListElement element, final int index) {
		final JPanel temp = new JPanel();
		temp.setLayout(new StructureListElementLayout());
		button = new JButton(element.getName(), element.getIcon());
		button.setHorizontalAlignment(SwingConstants.LEFT);
		Border b1 = BorderFactory.createLineBorder(Color.GRAY, 3);
		Border b2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        button.setBorder(BorderFactory.createCompoundBorder(b1,b2));
        button.setContentAreaFilled(false);
		button.addActionListener(new ElementButtonActionListener(index));
		button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		button.setFocusable(false);
		
		temp.add(button, "title");
		
		TableModel childData = new AbstractTableModel() {
			private static final long serialVersionUID = 3895333816471270996L;
			
			@Override
			public int getColumnCount() {
				return 1;
			}
			@Override
			public int getRowCount() {
				return element.getChildCount();
			}
			@Override
			public Object getValueAt(int arg0, int arg1) {
				return element.getChild(arg0);
			}
			@Override
		    public boolean isCellEditable(int row, int col) {
				return list.getSelectedRow() == row;
	        }
		};
		 
		list = new JTable(childData);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		StructureElementRenderer renderer = new StructureElementRenderer(element);
		list.getColumnModel().getColumn(0).setCellRenderer(renderer);
		list.getColumnModel().getColumn(0).setCellEditor(renderer);
		list.setCellSelectionEnabled(true);
		list.setShowHorizontalLines(true);
		list.setRowHeight(20);
		list.setTableHeader(null);
		list.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				if (list.getSelectedRow() >= 0) {
					list.setRowHeight(20);
					list.setRowHeight(list.getSelectedRow(), 70);
					list.editCellAt(list.getSelectedRow(), 0);
					if (editorContainer!=null){
					    editorContainer.removeAll();
					    editorContainer.add(((StructureElement) list.getValueAt(list.getSelectedRow(), 0)).getEditPanel());
					    StructureControl.getInstance().visitDataControl(((StructureElement) list.getValueAt(list.getSelectedRow(), 0)).getDataControl());
					    editorContainer.validate( );
					    editorContainer.repaint( );
					}
				} else if (editorContainer!=null){
					editorContainer.removeAll();
					editorContainer.add(structureElements.get(index).getEditPanel());
					StructureControl.getInstance().visitDataControl(structureElements.get(index).getDataControl());
					editorContainer.validate( );
					editorContainer.repaint( );
				}	
				SwingUtilities.invokeLater(new Runnable()
				{
				    public void run()
				    {
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				    }
				});

			}
		});
		
		if (element.getDataControl()!=null&&element.getDataControl().getAddableElements().length > 0) {
			JButton addButton = new JButton(new ImageIcon("img/icons/addNode.png"));
			addButton.setContentAreaFilled( false );
			addButton.setMargin( new Insets(0,0,0,0) );
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Controller.getInstance().addTool(new AddElementTool(element, list));
				}
			});
			addButton.setToolTipText(TextConstants.getText("GeneralText.AddNew"));
			temp.add(addButton, "addButton");
			temp.setComponentZOrder(addButton, 0);
			addButton.setFocusable(false);
		}

		
		JScrollPane scrollPane = new JScrollPane(list);
		temp.add(scrollPane, "list");
		return temp;		
	}

	private class ElementButtonActionListener implements ActionListener {
		private int index;
		
		public ElementButtonActionListener(int index) {
			this.index = index;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			update(selectedElement, index);
		}
	}
	
	public void updateElementPanel() {
		boolean temp = false;
		if (editorContainer!=null){
		    if (editorContainer.getComponentCount() == 1) {
			if (editorContainer.getComponent(0) instanceof Updateable) {
				temp = ((Updateable) editorContainer.getComponent(0)).updateFields();
			}
		    }
		}
		if (!temp) {
			reloadElementPanel();
		}
	}

	public void reloadElementPanel() {
	    if (editorContainer!=null){
		editorContainer.removeAll();
		if (list == null || list.getSelectedRow() == -1) {
			editorContainer.add(structureElements.get(selectedElement).getEditPanel());
			StructureControl.getInstance().visitDataControl(structureElements.get(selectedElement).getDataControl());
		} else {
			editorContainer.add(structureElements.get(selectedElement).getChild(list.getSelectedRow()).getEditPanel());
		}
		editorContainer.validate( );
		editorContainer.repaint( );
	    }
	}

	
	public void setSelectedItem(List<DataControl> path) {
		boolean element = true;
		while (path.size() > 0 && element) {
			element = false;
			for (int i = 0; i < structureElements.size() && !element; i++) {
				if (structureElements.get(i).getDataControl() == path.get(path.size() - 1)) {
					selectedElement = i;
					selectedListItem = -1;
					element = true;
				}
			}
			if (element)
				path.remove(path.size() - 1);
		}
		
		update();
		if (structureElements.get(selectedElement).getChildCount() > 0 && path.size() > 0) {
			element = false;
			for (int i = 0; i < structureElements.get(selectedElement).getChildCount() && !element; i++) {
				if (structureElements.get(selectedElement).getChild(i).getDataControl() == path.get(path.size() - 1)) {
					selectedListItem = i;
					path.remove(path.size() - 1);
					element = true;
				}
			}
		}
		if (editorContainer!=null){
		editorContainer.removeAll();
		
		if (selectedListItem == -1) {
			editorContainer.add(structureElements.get(selectedElement).getEditPanel());
			StructureControl.getInstance().visitDataControl(structureElements.get(selectedElement).getDataControl());
		} else {
			list.changeSelection(selectedListItem, 0, false, false);
		}
		
		if (editorContainer.getComponent(0) instanceof DataControlsPanel) {
			((DataControlsPanel) editorContainer.getComponent(0)).setSelectedItem(path);
		}
		
		editorContainer.validate( );
		editorContainer.repaint( );
		list.requestFocusInWindow();
		}
	}
	
}
