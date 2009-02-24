package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import es.eucm.eadventure.editor.control.controllers.SceneLinksController;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ScenesListDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.scenelistelements.ExitElement;
import es.eucm.eadventure.editor.gui.otherpanels.scenelistelements.SceneElement;

/**
 * A panel with all the scenes in a chapter with the relations of their exits between
 * one another.
 * 
 * @author Eugenio Marchiori
 *
 */
public class SceneLinksPanel extends JPanel {

	/**
	 * Default generated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The panel where everything is drawn
	 */
	private DrawPanel drawPanel;
	
	/**
	 * The data control of the list of scenes
	 */
	private ScenesListDataControl sldc;
	
	/**
	 * List of sceneElements
	 */
	private List<SceneElement> sceneElements;
	
	/**
	 * The scale with which to draw the scenes in the panel
	 */
	private float drawingScale = 0.8f;
	
	/**
	 * The list of checkboxes 
	 */
	private JTable checkBoxes;
	
	/**
	 * The table model for the list of checkboxes
	 */
	private DefaultTableModel dtm;
	
	public SceneLinksPanel(ScenesListDataControl sceneListDataControl) {
		this.sldc = sceneListDataControl;
		setLayout(new GridBagLayout());
		drawPanel = new DrawPanel(true);
		BufferedImage background = new BufferedImage(800, 600, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = background.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		drawPanel.setBackground(background);
		add(drawPanel, c);
				
		JScrollPane sp = createCheckBoxTable();
		
		sceneElements = new ArrayList<SceneElement>();
		for (SceneDataControl scene : sldc.getScenes()) {
			SceneElement element = new SceneElement(scene);
			sceneElements.add(element);
			dtm.addRow(new Object[]{new Boolean(element.isVisible()),element.getId()}); 
		}
		
		SceneLinksController controller = new SceneLinksController(this);
		drawPanel.addMouseListener(controller);
		drawPanel.addMouseMotionListener(controller);
		
		c.gridx = 1;
		c.weightx = 0.1;
		c.fill = GridBagConstraints.VERTICAL;

		sp.setMinimumSize(new Dimension(110, 0));
		sp.setPreferredSize(new Dimension(110, 1000));
		add(sp, c);
	}
	
	/**
	 * Creates the checkbox table and all the necessary elements.
	 * Returns a scrollpane with the table.
	 * 
	 * @return A scroll pane with the checkbox table
	 */
	private JScrollPane createCheckBoxTable() {
		String colNames[] = {"Show?", "Scene ID"}; 
		Object[][] data = null; 
		
		dtm = new DefaultTableModel(data, colNames);
		checkBoxes = new JTable(dtm);
		TableColumn tc = checkBoxes.getColumnModel().getColumn(0); 
		tc.setCellEditor(checkBoxes.getDefaultEditor(Boolean.class)); 
		tc.setCellRenderer(checkBoxes.getDefaultRenderer(Boolean.class)); 
		tc.setMaxWidth(30);
		checkBoxes.getColumnModel().getColumn(1).setMaxWidth(80);
		dtm.addTableModelListener(new TableModelListener(){ 
			public void tableChanged(TableModelEvent tme) { 
				if (tme.getType() == TableModelEvent.UPDATE) { 
					int row = tme.getFirstRow(); 
					int col = tme.getColumn(); 
					if (col == 0){ 
						sceneElements.get(row).setVisible(((Boolean) dtm.getValueAt(row, col)).booleanValue());
						SceneLinksPanel.this.repaint();
					} 
				} 
			} 
		}); 
		return new JScrollPane(checkBoxes, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}

	public void repaint() {
		super.repaint();
	}
	
	public void paint(Graphics g) {
		paintGraph();
		super.paint(g);
	}
	
	private void paintGraph() {
		drawPanel.paintBackground();
		
		drawingScale = (float) (0.5f * (1 - Math.log(sceneElements.size()) / Math.log(100)));
		List<Line> lines = new ArrayList<Line>();
		
		for (SceneElement scene : sceneElements) {
			if (scene.isVisible()) {
				drawPanel.paintRelativeImageTop(scene.getImage(), scene.getPosX(), scene.getPosY(), drawingScale);
				if (scene.isShowName())
					drawPanel.drawRelativeString(scene.getId(), scene.getPosX() , scene.getPosY());
				for (ExitElement exit : scene.getExitElements()) {
					int x1 = (int) (scene.getPosX() + exit.getPosX() * drawingScale);
					int y1 = (int) (scene.getPosY() + exit.getPosY() * drawingScale);
					for (String id : exit.getSceneIds()) {
						SceneElement temp = this.getSceneElementForId(id);
						if (temp != null && temp.isVisible()) {
							addLine(x1, y1, temp, scene.getColor(), lines);
						}
					}
				}
			}
		}
		
		for (Line line : lines) {
			drawPanel.drawRelativeLine(line.x1, line.y1, line.x2, line.y2, line.color);
			drawPanel.drawRelativeArrowTip(line.x1, line.y1, line.x2, line.y2, line.color);
		}
	}
	
	/**
	 * Adds a line form a given point to the given sceneElement
	 * 
	 * @param x1 The x value of the initial point
	 * @param y1 The y value of the initial point
	 * @param temp The destination sceneElement
	 * @param color The color of the line
	 * @param lines The list of lines where to add the new one
	 */
	private void addLine(int x1, int y1, SceneElement temp, Color color, List<Line> lines) {
		double w = temp.getWidth() * drawingScale /2;
		double h = temp.getHeight() * drawingScale / 2;
		int x2 = (int) (temp.getPosX() + w);
		int y2 = (int) (temp.getPosY() + h);
		
		int x3 = x2;
		int y3 = y2;
		
		if (h == 0 || ((y2 - y1) != 0 && Math.abs(w/h) <= Math.abs((x2 - x1)/(y2 - y1)))) {
			if (x1 <= x2) {
				x3 = (int) (x2 - w);
				y3 = (int) (y2 - (w/(x2 - x1))*(y2 - y1));
			} else if (x1 > x2) {
				x3 = (int) (x2 + w);
				y3 = (int) (y2 + (w/(x2 - x1))*(y2 - y1));
			}
		} else {
			if (y1 <= y2) {
				y3 = (int) (y2 - h);
				x3 = (int) (x2 - (h/(y2 - y1))*(x2 - x1));
			} else if (y1 > y2) {
				y3 = (int) (y2 + h);
				x3 = (int) (x2 + (h/(y2 - y1))*(x2 - x1));
			}
		}
		lines.add(new Line(x1, y1, x3, y3, color));
	}

	/**
	 * Class with all the elements of a line
	 */
	private class Line {
		public int x1;
		public int x2;
		public int y1;
		public int y2;
		public Color color;
		public Line(int x1, int y1, int x2, int y2, Color color) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
			this.color = color;
		}
	}

	public SceneElement getSceneElementForId(String id) {
		for (SceneElement scene : sceneElements) {
			if (scene.getId().equals(id))
				return scene;
		}
		return null;
	}

	/**
	 * Returns the sceneElements at a given mouse position. Null if there is none.
	 * 
	 * @param mouseX The x value of the mouse position relative to the panel
	 * @param mouseY The y value of the mouse position relative to the panel
	 * @return The sceneElement in the given position
	 */
	public SceneElement getSceneElement(int mouseX, int mouseY) {
		int x = drawPanel.getRealX(mouseX);
		int y = drawPanel.getRealY(mouseY);

		for (int i = sceneElements.size() - 1; i >= 0; i--) {
			SceneElement scene = sceneElements.get(i);
			if (scene.isVisible() && scene.getPosX() < x && scene.getPosX() + scene.getWidth() * drawingScale > x
					&& scene.getPosY() < y && scene.getPosY() + scene.getHeight() * drawingScale > y) {
				checkBoxes.getSelectionModel().setSelectionInterval(i, i);
				return scene;
			}
		}
		
		return null;
	}

	public int getRealWidth(int i) {
		return drawPanel.getRealWidth(i);
	}
	
	public int getRealHeight(int i ) {
		return drawPanel.getRealHeight(i);
	}
}