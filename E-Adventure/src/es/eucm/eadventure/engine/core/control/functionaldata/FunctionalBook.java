/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author L�pez Ma�as, E., P�rez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fern�ndez-Manj�n, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.resources.Asset;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.gui.auxiliar.ImageTransformer;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This class manages the eGame "bookscenes".
 */

public abstract class FunctionalBook {

    /**
     * Position of the upper left corner of the next page button
     */
    protected Point nextPage;

    /**
     * Position of the upper left corner of the previous page button
     */
    protected Point previousPage;

    /**
     * Dimensions for next page arrow
     */
    protected Dimension nextPageDimension;

    /**
     * Dimensions of the previous page arrow
     */
    protected Dimension previousPageDimension;

    /**
     * Book with the information
     */
    protected Book book;

    /**
     * Current page.
     */
    protected int currentPage;
    
    /**
     * Image for background
     */
    protected Image background;
    
    /**
     * Current images for the arrows
     */
    protected Image currentArrowLeft, currentArrowRight;
    
    /**
     * All images for the arrows
     */
    protected Image arrowLeftNormal, arrowLeftOver, arrowRightNormal, arrowRightOver;

    /**
     * Number of pages.
     */
    protected int numPages;

    /**
     * Returns whether the mouse pointer is in the "next page" button
     * 
     * @param x
     *            the horizontal position of the mouse pointer
     * @param y
     *            the vertical position of the mouse pointer
     * @return true if the mouse is in the "next page" button, false otherwise
     */
    public boolean isInNextPage( int x, int y ) {

        return ( nextPage.getX( ) < x ) && ( x < nextPage.getX( ) + nextPageDimension.getWidth( ) ) && ( nextPage.getY( ) < y ) && ( y < nextPage.getY( ) + nextPageDimension.getHeight( ) );
    }

    protected FunctionalBook( Book b ){
        
        this.book = b;
        // Create necessaries resources to display the book
        Resources r = createResourcesBlock( book );
        // Load images and positions
        loadImages( r );
        
        // Load arrows position
        this.previousPage = book.getPreviousPagePoint( );
        this.nextPage = book.getNextPagePoint( );
        
        if ( previousPage == null || nextPage == null ){
            this.setDefaultArrowsPosition( );
        }
        
    }
    
    /**
     * Returns wheter the mouse pointer is in the "previous page" button
     * 
     * @param x
     *            the horizontal position of the mouse pointer
     * @param y
     *            the vertical position of the mouse pointer
     * @return true if the mouse is in the "previous page" button, false
     *         otherwise
     */
    public boolean isInPreviousPage( int x, int y ) {

        return ( previousPage.x < x ) && ( x < previousPage.x + previousPageDimension.getWidth( ) ) && ( previousPage.y < y ) && ( y < previousPage.y + previousPageDimension.height );
    }

    /**
     * Returns the book's data (text and images)
     * 
     * @return the book's data
     */
    public Book getBook( ) {
        return book;
    }

    /**
     * Returns whether the book is in its last page
     * 
     * @return true if the book is in its last page, false otherwise
     */
    public abstract boolean isInLastPage( );
    
    /**
     * Returns whether the book is in its first page
     * 
     * @return true if the book is in its first page, false otherwise
     */
    public abstract boolean isInFirstPage( );

    /**
     * Changes the current page to the next one
     */
    public abstract void nextPage( );

    /**
     * Changes the current page to the previous one
     */
    public abstract void previousPage( );
    
    /**
     * Load the necessaries images for displaying the book. This method is pretty much the same
     * as "loadImages" from BookPagePreviewPanel.
     */
    protected void loadImages( Resources r ){
        background = MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );
        
        try {
            arrowLeftNormal = MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_ARROW_LEFT_NORMAL ), MultimediaManager.IMAGE_SCENE );
        }
        catch ( Exception e ){
            arrowLeftNormal = null;
        }
        
        try {
            arrowRightNormal = MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_ARROW_RIGHT_NORMAL ), MultimediaManager.IMAGE_SCENE );
        } catch ( Exception e ){
            arrowRightNormal = null;
        }
        
        try {
            arrowLeftOver = MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_ARROW_LEFT_OVER ), MultimediaManager.IMAGE_SCENE );
        } catch ( Exception e ){
            arrowLeftOver = null;
        }
        
        try {
            arrowRightOver = MultimediaManager.getInstance( ).loadImageFromZip( r.getAssetPath( Book.RESOURCE_TYPE_ARROW_RIGHT_OVER ), MultimediaManager.IMAGE_SCENE );
        } catch ( Exception e ){
            arrowRightOver = null;
        }
        
        // We check the arrowLeftNormal
        if ( arrowLeftNormal == null ){
            // We look for first in the over arrow
            if ( arrowLeftOver != null ){
                
                arrowLeftNormal = arrowLeftOver;
            }
            else if ( arrowRightNormal != null ){
                
                arrowLeftNormal = ImageTransformer.getInstance( ).getScaledImage( arrowRightNormal, -1.0f, 1.0f );
            }
            else if ( arrowRightOver != null ){
                
                arrowLeftNormal = ImageTransformer.getInstance( ).getScaledImage( arrowRightOver, -1.0f, 1.0f );
            }
            //  Else, we load defaults left arrows
            else{
                
                arrowLeftNormal = MultimediaManager.getInstance( ).loadImageFromZip( SpecialAssetPaths.ASSET_DEFAULT_ARROW_NORMAL, MultimediaManager.IMAGE_SCENE );
                arrowLeftOver = MultimediaManager.getInstance( ).loadImageFromZip( SpecialAssetPaths.ASSET_DEFAULT_ARROW_OVER, MultimediaManager.IMAGE_SCENE );
            }
        }
        
        // We check the arrowRightNormal
        if ( arrowRightNormal == null ){
            //We look for first in the over arrow
            if ( arrowRightOver != null ){
                
                arrowRightNormal = arrowRightOver;
            }
            // Else, we use the mirrored left arrow
            else {
                
                arrowRightNormal = ImageTransformer.getInstance( ).getScaledImage( arrowLeftNormal, -1.0f, 1.0f );
            }
        }
        
        // We check the arrowLeftNormal
        if ( arrowLeftOver == null ){
            
            arrowLeftOver = arrowLeftNormal;
        }
        
        // We check the arrowRightOver
        if ( arrowRightOver == null ){
            
            arrowRightOver = ImageTransformer.getInstance( ).getScaledImage( arrowLeftOver, -1.0f, 1.0f );
        }
        
        previousPageDimension = new Dimension( arrowLeftNormal.getWidth( null ), arrowLeftNormal.getHeight( null ) );
        nextPageDimension = new Dimension( arrowRightNormal.getWidth( null ), arrowRightNormal.getHeight( null ) );
        
        currentArrowLeft = arrowLeftNormal;
        currentArrowRight = arrowRightNormal;
    }
    
    void loadDefaultArrows( ){
        arrowLeftNormal = MultimediaManager.getInstance( ).loadImageFromZip( SpecialAssetPaths.ASSET_DEFAULT_ARROW_NORMAL, MultimediaManager.IMAGE_SCENE );
        arrowRightNormal = ImageTransformer.getInstance( ).getScaledImage( arrowLeftNormal, 1.0f, 1.0f );
        arrowLeftOver = MultimediaManager.getInstance( ).loadImageFromZip( SpecialAssetPaths.ASSET_DEFAULT_ARROW_OVER, MultimediaManager.IMAGE_SCENE );
        arrowRightOver = ImageTransformer.getInstance( ).getScaledImage( arrowLeftOver, 1.0f, 1.0f );
    }
    
    void setDefaultArrowsPosition( ){
        int margin = 20;
        int xLeft = margin;
        int yLeft = background.getHeight( null ) - (int) previousPageDimension.getHeight( ) - margin;
        int xRight = background.getWidth( null ) - (int) nextPageDimension.getWidth( ) - margin;
        int yRight = background.getHeight( null ) - (int) nextPageDimension.getHeight( ) - margin;
        
        previousPage = new Point( xLeft, yLeft );
        nextPage = new Point( xRight, yRight );
    }
    
    /**
     * Creates the current resource block to be used
     */
    protected Resources createResourcesBlock( Book b ) {

        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < b.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( b.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = b.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if( newResources == null ) {
            newResources = new Resources( );
            newResources.addAsset( new Asset( Book.RESOURCE_TYPE_BACKGROUND, ResourceHandler.DEFAULT_BACKGROUND ) );
        }
        return newResources;
    }
    
    public void draw( Graphics g ){
        g.drawImage( background, 0, 0, background.getWidth( null ), background.getHeight( null ), null );
        
        if ( !isInFirstPage( ) )
            g.drawImage( currentArrowLeft, previousPage.x, previousPage.y, previousPageDimension.width, previousPageDimension.height, null );
        
        if ( !isInLastPage( ) )
            g.drawImage( currentArrowRight, nextPage.x, nextPage.y, nextPageDimension.width, nextPageDimension.height, null );
    }

    public void mouseOverPreviousPage( boolean mouseOverPreviousPage ) {
        if ( !mouseOverPreviousPage )
            currentArrowLeft = arrowLeftNormal;
        else
            currentArrowLeft = arrowLeftOver;
        
    }

    public void mouseOverNextPage( boolean mouseOverNextPage ) {
        if ( !mouseOverNextPage )
            currentArrowRight = arrowRightNormal;
        else
            currentArrowRight = arrowRightOver;
        
    }
    
}
