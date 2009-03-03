package es.eucm.eadventure.editor.control.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;
import es.eucm.eadventure.editor.gui.treepanel.TreeNodeControl;

public class NormalScenePreviewEditionController implements ScenePreviewEditionController {

	protected ImageElement underMouse;
	
	private ScenePreviewEditionPanel spep;
	
	private int startDragX;
	
	private int startDragY;
	
	private int originalX;
	
	private int originalY;

	private int originalWidth;
	
	private int originalHeight;
	
	private float originalScale;
	
	public NormalScenePreviewEditionController(ScenePreviewEditionPanel spep) {
		this.spep = spep;
	}
	
	public void mouseClicked(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
		if (underMouse != null && !spep.getFixedSelectedElement()) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					spep.setSelectedElement(underMouse);
					spep.repaint();
				} else {
					TreeNodeControl.getInstance().changeTreeNode(underMouse.getDataControl());
				}
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				if (underMouse.getDataControl() != null && underMouse.getReferencedDataControl() != null) {
					JPopupMenu menu = new JPopupMenu();
					JMenuItem item = new JMenuItem(TextConstants.getText("SPEP.GoToElementReference"));
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							TreeNodeControl.getInstance().changeTreeNode(underMouse.getDataControl());
						}
					});
					menu.add(item);
					JMenuItem item2 = new JMenuItem(TextConstants.getText("SPEP.GoToReferencedElement"));
					item2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							TreeNodeControl.getInstance().changeTreeNode(underMouse.getReferencedDataControl());
						}
					});
					menu.add(item2);
					menu.show(spep, e.getX(), e.getY());
				}
			}
		} else if (underMouse == null && !spep.getFixedSelectedElement()) {
			spep.setSelectedElement((ImageElement) null);
			spep.repaint();
		} else if (spep.getFixedSelectedElement()) {
			int x = spep.getRealX(e.getX());
			int y = spep.getRealY(e.getY());
			spep.getSelectedElement().changePosition(x, y);
			spep.updateTextEditionPanel();
			spep.repaint();
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
		if (underMouse != null && (underMouse == spep.getSelectedElement() || underMouse == spep.getInfluenceArea())) {
			startDragX = e.getX();
			startDragY = e.getY();
			originalX = underMouse.getX();
			originalY = underMouse.getY();
			originalWidth = underMouse.getWidth();
			originalHeight = underMouse.getHeight();
			originalScale = underMouse.getScale();
		} else if (underMouse != null && !spep.getFixedSelectedElement()) {
			spep.setSelectedElement(underMouse);
			spep.repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		if (underMouse != null && !spep.isRescale() && !spep.isResize()) {
			int changeX = spep.getRealWidth(e.getX() - startDragX);
			int changeY = spep.getRealHeight(e.getY() - startDragY);
			int x = originalX + changeX;
			int y = originalY + changeY;
			underMouse.changePosition(x, y);
			spep.updateTextEditionPanel();
			spep.repaint();
		} else if (underMouse != null && spep.isRescale() && !spep.isResize()) {
			double changeX = (e.getX() - startDragX);
			double changeY = - (e.getY() - startDragY);
			double width = underMouse.getImage().getWidth(null);
			double height = underMouse.getImage().getHeight(null);
			
			double tempX = changeX / width;
			double tempY = changeY / height;
			
			float scale = originalScale;
			if (tempX*tempX > tempY*tempY)
				scale = (float) (((width * originalScale) + spep.getRealWidth((int) changeX)) / width);
			else
				scale = (float) (((height * originalScale) + spep.getRealHeight((int) changeY)) / height);
			
			if (scale <= 0)
				scale = 0.01f;
			
			underMouse.setScale(scale);
			spep.updateTextEditionPanel();
			spep.repaint();
		} else if (underMouse != null && !spep.isRescale() && spep.isResize()) {
			int changeX = spep.getRealWidth(e.getX() - startDragX);
			int changeY = spep.getRealHeight(e.getY() - startDragY);
			underMouse.changeSize(originalWidth + changeX, originalHeight + changeY);
			underMouse.recreateImage();
			spep.updateTextEditionPanel();
			spep.repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
	}
	
	protected void setMouseUnder(int mouseX, int mouseY) {
		int x = spep.getRealX(mouseX);
		int y = spep.getRealY(mouseY);
		ImageElement imageElement = spep.getMovableElement(x, y);
		ImageElement rescaleElement = spep.getRescaleElement(x, y);
		ImageElement resizeElement = spep.getResizeElement(x, y);

		if (rescaleElement != null) {
			underMouse = rescaleElement;
			spep.setRescale(true);
			spep.setResize(false);
			spep.repaint();
		} else if (resizeElement != null) {
			underMouse = resizeElement;
			spep.setResize(true);
			spep.setRescale(false);
			spep.repaint();
		} else if (imageElement != underMouse || (imageElement != null && (spep.isRescale() || spep.isResize()))) {
			underMouse = imageElement;
			spep.setRescale(false);
			spep.setResize(false);
			spep.repaint();
		} else if (imageElement == null){
			underMouse = null;
			spep.setRescale(false);
			spep.setResize(false);
		}
	}
	
	public ImageElement getUnderMouse() {
		return underMouse;
	}
}