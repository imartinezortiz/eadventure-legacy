/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.controllers;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.editor.control.controllers.scene.PointDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.RectangleArea;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementInfluenceArea;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementPoint;

/**
 * Controller for the mouse clicks in the ScenePreviewEditionPanel when editing
 * an irregular area
 * 
 * @author Eugenio Marchiori
 */
public class IrregularAreaEditionController extends NormalScenePreviewEditionController {

    /**
     * Id for the point edit tool
     */
    public static final int POINT_EDIT = 0;

    /**
     * Id for the delete tool
     */
    public static final int DELETE_TOOL = 2;

    /**
     * The data control of the rectangle being edited
     */
    private RectangleArea aadc;

    private int selectedTool = POINT_EDIT;

    private Color color;

    private boolean hasInfluenceArea;

    public IrregularAreaEditionController( ScenePreviewEditionPanel spep, RectangleArea rectangleArea, Color color, boolean hasInfluenceArea ) {

        super( spep );
        this.spep = spep;
        this.aadc = rectangleArea;
        this.color = color;
        this.hasInfluenceArea = hasInfluenceArea;
        spep.setIrregularRectangle( aadc.getRectangle( ), color );
    }

    @Override
    public void mouseClicked( MouseEvent e ) {

        int x = spep.getRealX( e.getX( ) );
        int y = spep.getRealY( e.getY( ) );
        setMouseUnder( e.getX( ), e.getY( ) );

        if( selectedTool == POINT_EDIT ) {
            if( this.underMouse == null || this.underMouse instanceof ImageElementInfluenceArea ) {
                aadc.addPoint( x, y );
                spep.addPoint( new PointDataControl( aadc.getLastPoint( ) ) );
                if( hasInfluenceArea ) {
                    if( aadc.getPoints( ).size( ) >= 3 )
                        ( (ImageElementInfluenceArea) spep.getInfluenceArea( ) ).setVisible( true );
                    spep.getInfluenceArea( ).recreateImage( );
                }
                spep.setIrregularRectangle( aadc.getRectangle( ), color );
                spep.repaint( );
            }
        }
        else if( selectedTool == DELETE_TOOL ) {
            if( underMouse != null && underMouse instanceof ImageElementPoint ) {
                PointDataControl pointDataControl = (PointDataControl) ( (ImageElementPoint) underMouse ).getDataControl( );
                aadc.deletePoint( (Point) pointDataControl.getContent( ) );
                spep.removeElement( ScenePreviewEditionPanel.CATEGORY_POINT, underMouse );
                underMouse = null;
                spep.setIrregularRectangle( aadc.getRectangle( ), color );
                spep.repaint( );
            }
        }
    }

    @Override
    public void mouseEntered( MouseEvent e ) {

    }

    @Override
    public void mouseExited( MouseEvent e ) {

    }

    @Override
    public void mousePressed( MouseEvent e ) {

        if( selectedTool == POINT_EDIT ) {
            setMouseUnder( e.getX( ), e.getY( ) );
            if( underMouse != null ) {
                startDragX = e.getX( );
                startDragY = e.getY( );
                originalX = underMouse.getX( );
                originalY = underMouse.getY( );
                originalWidth = underMouse.getImage( ).getWidth( null );
                originalHeight = underMouse.getImage( ).getHeight( null );
                originalScale = underMouse.getScale( );
                spep.setSelectedElement( underMouse );
                spep.repaint( );
            }
        }
    }

    @Override
    public void mouseReleased( MouseEvent e ) {

    }

    @Override
    public void mouseDragged( MouseEvent e ) {

        if( selectedTool == POINT_EDIT ) {
            if( underMouse != null && !spep.isRescale( ) && !( spep.isResize( ) || spep.isResizeInflueceArea( ) ) ) {
                int changeX = spep.getRealWidth( e.getX( ) - startDragX );
                int changeY = spep.getRealHeight( e.getY( ) - startDragY );
                int x = originalX + changeX;
                int y = originalY + changeY;
                underMouse.changePosition( x, y );
                spep.repaint( );
            }
            else if( underMouse != null && spep.isRescale( ) && !( spep.isResize( ) || spep.isResizeInflueceArea( ) ) ) {
                double changeX = ( e.getX( ) - startDragX );
                double changeY = -( e.getY( ) - startDragY );
                double width = originalWidth / originalScale;
                double height = ( originalHeight - 10 ) / originalScale;

                double tempX = changeX / width;
                double tempY = changeY / height;

                float scale = originalScale;
                if( tempX * tempX > tempY * tempY )
                    scale = (float) ( ( ( width * originalScale ) + spep.getRealWidth( (int) changeX ) ) / width );
                else
                    scale = (float) ( ( ( height * originalScale ) + spep.getRealHeight( (int) changeY ) ) / height );

                if( scale <= 0 )
                    scale = 0.01f;

                underMouse.setScale( scale );
                underMouse.recreateImage( );
                spep.repaint( );
            }
            else if( underMouse != null && !spep.isRescale( ) && ( spep.isResize( ) || spep.isResizeInflueceArea( ) ) ) {
                int changeX = spep.getRealWidth( e.getX( ) - startDragX );
                int changeY = spep.getRealHeight( e.getY( ) - startDragY );
                underMouse.changeSize( originalWidth + changeX, originalHeight + changeY );
                underMouse.recreateImage( );
                spep.updateTextEditionPanel( );
                spep.repaint( );
            }
        }
    }

    @Override
    public void mouseMoved( MouseEvent e ) {

        setMouseUnder( e.getX( ), e.getY( ) );
        if( spep.getFirstElement( ) != null ) {
            spep.repaint( );
        }
    }

    @Override
    public ImageElement getUnderMouse( ) {

        return underMouse;
    }

    public void setSelectedTool( int tool ) {

        selectedTool = tool;
        if( selectedTool == POINT_EDIT ) {
            spep.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_POINT, true );
            spep.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_INFLUENCEAREA, true );
        }
        else if( selectedTool == DELETE_TOOL ) {
            spep.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_POINT, true );
            spep.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_INFLUENCEAREA, false );
        }
        spep.repaint( );
    }

    public RectangleArea getEditionRectangle( ) {

        return this.aadc;
    }
}
