package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JEditorPane;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.engine.core.gui.GUI;

public class FunctionalTextBook extends FunctionalBook{

    /**
     * X position of the first column of text
     */
    public static final int TEXT_X_1 = 110;
    
    /**
     * X position for the second column of text
     */
    public static final int TEXT_X_2 = 445;

    /**
     * Y position for both columns of text
     */
    public static final int TEXT_Y = 75;

    /**
     * Width of each column of text
     */
    public static final int TEXT_WIDTH = 250;
    
    /**
     * Width of each column of the bullet text
     */
    public static final int TEXT_WIDTH_BULLET = 225;

    /**
     * Height of each column of text
     */
    public static final int PAGE_TEXT_HEIGHT = 400;

    /**
     * Height of each line of text
     */
    public static final int LINE_HEIGHT = 25;
    
    /**
     * Height of each line of a title
     */
    public static final int TITLE_HEIGHT = 40;

    /**
     * Number of lines per column
     */
    public static final int TEXT_LINES = PAGE_TEXT_HEIGHT / LINE_HEIGHT;

    /**
     * Background image of the book.
     */
    protected Image imgBook;

    /**
     * Total height of the book.
     */
    private int totalHeight;
    

    /**
     * List of functional paragraphs.
     */
    private ArrayList<FunctionalBookParagraph> functionalParagraphs;
    
    
    /**
     * Creates a new FunctionalBook
     * @param book the book's data
     */
    public FunctionalTextBook( Book book ) {
               
        this.book = book;

            // Create the list of paragraphs and add it
            functionalParagraphs = new ArrayList<FunctionalBookParagraph>();
            for( BookParagraph paragraph : book.getParagraphs() ) {
                switch( paragraph.getType( ) ) {
                    case BookParagraph.TITLE:
                        functionalParagraphs.add( new FunctionalBookTitle( paragraph ) );
                        break;
                    case BookParagraph.BULLET:
                        functionalParagraphs.add( new FunctionalBookBullet( paragraph ) );
                        break;
                    case BookParagraph.IMAGE:
                        functionalParagraphs.add( new FunctionalBookImage( paragraph ) );
                        break;
                    case BookParagraph.TEXT:
                        functionalParagraphs.add( new FunctionalBookText( paragraph ) );
                        break;
                }
            }
    
            // Calculate the height of the book and the number of pages
            totalHeight = 0;
            currentPage = 0;
            for( FunctionalBookParagraph functionalParagraph : functionalParagraphs ) 
                totalHeight += functionalParagraph.getHeight( );
            numPages = (int)Math.ceil( (double)totalHeight/(double)PAGE_TEXT_HEIGHT );
            
            // Create the image of the book and extract the graphics
            int y=0;
            imgBook = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( TEXT_WIDTH, totalHeight+20, Transparency.BITMASK );
            Graphics2D g = (Graphics2D) imgBook.getGraphics( );
            g.setFont( new Font( "Dialog", Font.PLAIN, 18 ) );
            g.setColor( Color.DARK_GRAY );
            
            // Paint each paragraph in the image
            for( FunctionalBookParagraph functionalParagraph : functionalParagraphs ) {
                
                // If the paragraph can't be splitted and doesn't fit in the current page, jump to the next one
                if( !functionalParagraph.canBeSplitted( ) && ( y % PAGE_TEXT_HEIGHT ) + functionalParagraph.getHeight( ) > PAGE_TEXT_HEIGHT ) 
                    y += ( PAGE_TEXT_HEIGHT - ( y % PAGE_TEXT_HEIGHT ) );
    
                // Paint the entire paragraph
                functionalParagraph.draw( g, 0, y );
                y += functionalParagraph.getHeight( );
            }
            g.dispose( );
        }
        


    /**
     * Returns whether the book is in its last page
     * @return true if the book is in its last page, false otherwise 
     */
    public boolean isInLastPage( ) {
        return (currentPage==numPages-1 || currentPage==numPages-2);
    }



    /**
     * Displays the next page
     */
    public void nextPage( ) {

            // Jump two pages (each book has to columns)
            if( currentPage < numPages - 2 ) {
                currentPage += 2;
            }
    }

    /**
     * Displays the previous page
     */
    public void previousPage( ) {
            // Jump two pages (each book has to columns)
            if( currentPage > 1 ) {
                currentPage -= 2;
            }
    }

    /**
     * Draws the text of the book
     * @param g the graphics where the book must be painted
     */
    public void draw( Graphics2D g ) {
        g.setColor( Color.DARK_GRAY );

        // Draw the first page
        g.drawImage( 
                imgBook,
                TEXT_X_1,
                TEXT_Y + 5,
                TEXT_X_1 + TEXT_WIDTH,
                TEXT_Y + PAGE_TEXT_HEIGHT + 5,
                0,
                currentPage * PAGE_TEXT_HEIGHT + 5,
                TEXT_WIDTH,
                ( currentPage + 1 ) * PAGE_TEXT_HEIGHT + 5,
                null );
        
        // If there is second page, draw it
        if( currentPage < numPages - 1 ) {
            g.drawImage(
                    imgBook,
                    TEXT_X_2,
                    TEXT_Y + 5,
                    TEXT_X_2 + TEXT_WIDTH,
                    TEXT_Y + PAGE_TEXT_HEIGHT + 5,
                    0,
                    ( currentPage + 1 ) * PAGE_TEXT_HEIGHT + 5,
                    TEXT_WIDTH,
                    ( currentPage + 2 ) * PAGE_TEXT_HEIGHT + 5,
                    null);
        }
    }
}