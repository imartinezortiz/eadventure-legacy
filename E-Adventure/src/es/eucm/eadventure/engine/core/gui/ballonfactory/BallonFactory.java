package es.eucm.eadventure.engine.core.gui.ballonfactory;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Random;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;

public class BallonFactory {

    public static int marginTop = 2;

    public static int padding = 15;

    private static int maxLineWidth = 0;

    private static int linesHeight = 0;

    private static ConversationLine.Type type = ConversationLine.Type.NORMAL;

    private static int roundRadius = 10;

    private static AffineTransform normal = new AffineTransform( );

    private static AffineTransform whisper;

    private static final int VERTICAL_MARGIN = 50;

    private static final int HORIZONTAL_MARGIN = 60;

    private static Position position;

    private enum Position {
        NORTH, SOUTH, EAST, WEST
    };

    /**
     * 
     * @param f
     * @param text
     * @param aspectFactor
     * @return
     */
    public static ArrayList<String> getLines( FontMetrics f, String text, double aspectFactor, int maxWidth ) {

        text = processType( text );

        maxLineWidth = 0;
        linesHeight = 0;

        int text_width = f.stringWidth( text );
        int line_height = f.getHeight( ) + marginTop;

        // Calculate line width. Aspect factor determines how text is put on
        // screen. If aspectFactor = 0.5, text would be put in a square
        // if aspectFactor > 0.5 in a rectangle wider tan taller, and if
        // aspectFactor < 0.5, in a rectangle taller than wider
        double lineWidth = Math.pow( text_width / line_height, aspectFactor ) * line_height;
        lineWidth = lineWidth > maxWidth ? maxWidth : lineWidth;
        lineWidth = lineWidth < maxWidth / 3 ? maxWidth / 3 : lineWidth;

        // List with final lines
        ArrayList<String> lines = new ArrayList<String>( );

        String[] words = text.split( " " );

        // Current line
        String line = "";
        int contWord = 0;

        int currentLineWidth = 0;

        while( contWord < words.length ) {

            int nextWordWidth = f.stringWidth( words[contWord] + " " );

            if( currentLineWidth + nextWordWidth <= lineWidth ) {
                currentLineWidth += nextWordWidth;
                line += words[contWord++] + " ";
            }
            else if( line != "" ) {
                lines.add( line );

                maxLineWidth = maxLineWidth < currentLineWidth ? currentLineWidth : maxLineWidth;

                currentLineWidth = 0;
                line = "";
            }
            else {
                line = splitLongWord( f, lines, words[contWord++], lineWidth );
            }
        }

        if( line != "" ) {
            lines.add( line );
            maxLineWidth = maxLineWidth < currentLineWidth ? currentLineWidth : maxLineWidth;
        }
        linesHeight += ( ( f.getHeight( ) + marginTop ) * lines.size( ) ) - marginTop;
        return lines;

    }

    private static String processType( String text ) {

        if( text != null && !text.equals( "" ) && text.charAt( 0 ) == '#' ) {
            String tag = text.substring( 0, text.indexOf( ' ' ) );
            type = ConversationLine.Type.getType( tag );
            if( tag != "" ) {
                return text.substring( tag.length( ) );
            }
            else
                return text;
        }
        type = ConversationLine.Type.NORMAL;
        return text;

    }

    private static String splitLongWord( FontMetrics f, ArrayList<String> lines, String word, double lineWidth ) {

        boolean finished = false;
        String currentLine = "";

        int i = 0;
        while( !finished ) {
            currentLine = "";

            while( i < word.length( ) && f.stringWidth( currentLine ) < lineWidth ) {
                currentLine += word.charAt( i++ );

            }

            if( i == word.length( ) ) {
                finished = true;
            }
            else {
                lines.add( currentLine );
                int currentLineWidth = f.stringWidth( currentLine );
                maxLineWidth = maxLineWidth < currentLineWidth ? currentLineWidth : maxLineWidth;
            }

        }
        return currentLine;

    }

    public static Rectangle getTextBounds( int showX, int showY, int maxWidth, int maxHeight ) {

        // Try to put it in NORTH
        Rectangle r = new Rectangle( showX - maxLineWidth / 2, showY - linesHeight, maxLineWidth, linesHeight );

        if( correctHorizontally( r, maxWidth, maxHeight ) ) {
            if( !r.contains( showX, showY ) ) {
                position = Position.NORTH;
                return addPadding( r );
            }
        }
        // Try to put it in WEST
        r = new Rectangle( showX - r.width - HORIZONTAL_MARGIN, showY, maxLineWidth, linesHeight );

        if( correctVertically( r, maxWidth, maxHeight ) ) {
            if( !r.contains( showX, showY ) ) {
                position = Position.WEST;
                return addPadding( r );
            }

        }

        // Try to put it in EAST
        r = new Rectangle( showX + HORIZONTAL_MARGIN, showY, maxLineWidth, linesHeight );
        if( correctVertically( r, maxWidth, maxHeight ) ) {
            if( !r.contains( showX, showY ) ) {
                position = Position.EAST;
                return addPadding( r );
            }

        }

        // If we get here, we put it un SOUTH
        r = new Rectangle( showX - maxLineWidth / 2, showY + linesHeight + VERTICAL_MARGIN, maxLineWidth, linesHeight );
        position = Position.SOUTH;
        correctHorizontally( r, maxWidth, maxHeight );
        return addPadding( r );
    }

    private static Rectangle addPadding( Rectangle r ) {

        r.x -= padding;
        r.y -= padding;
        r.height += padding * 2;
        r.width += padding * 2;
        return r;
    }

    private static boolean correctHorizontally( Rectangle r, int maxWidth, int maxHeight ) {

        if( r.x < 0 ) {
            r.x = 2;
        }

        if( r.x + r.width > maxWidth ) {
            r.x = maxWidth - r.width - 2;
        }

        return isInsideBounds( r, maxWidth, maxHeight );
    }

    private static boolean correctVertically( Rectangle r, int maxWidth, int maxHeight ) {

        if( r.y < 0 ) {
            r.y = 2;
        }

        if( r.y + r.height > maxHeight ) {
            r.y = maxHeight - r.height - 2;
        }

        return isInsideBounds( r, maxWidth, maxHeight );
    }

    private static boolean isInsideBounds( Rectangle r, int maxWidth, int maxHeight ) {

        return r.x > 0 && r.y > 0 && r.x + r.width <= maxWidth && r.height <= maxHeight;
    }

    public static Shape getPath( Rectangle r, int step, int amplitude ) {

        switch( type ) {
            case YELL:
            case THOUGHT:
                return getCirclePath( r );
            case WHISPER:
                return getNormalPath( r );
            default:
                return getNormalPath( r );
        }
    }

    private static Shape getCirclePath( Rectangle r ) {

        Random random = new Random( );
        random.setSeed( 0 );

        int maxStep = 5;
        int minStep = 20;
        int maxAmplitude = 5;
        int minAmplitude = 20;
        int maxFactor = 2;
        GeneralPath path = new GeneralPath( );
        path.moveTo( r.x, r.y );

        int step = 0, amplitude = 0, factor = 0;
        float x1 = 0.0f, x2 = 0.0f, y1 = 0.0f, y2 = 0.0f;
        boolean done = false;
        int currentX, currentY;

        // Drawing top side        
        currentX = r.x;
        currentY = r.y;

        while( !done ) {
            step = random.nextInt( maxStep ) + minStep;
            amplitude = random.nextInt( maxAmplitude ) + minAmplitude;
            factor = type == ConversationLine.Type.THOUGHT ? random.nextInt( maxFactor ) + 1 : 2;

            if( currentX + step > r.x + r.width ) {
                done = true;
            }
            else {
                x1 = currentX + step / factor;
                y1 = currentY - amplitude;
                x2 = currentX + step;
                y2 = currentY;
                if( type == ConversationLine.Type.THOUGHT ) {
                    path.quadTo( x1, y1, x2, y2 );
                }
                else if( type == ConversationLine.Type.YELL ) {
                    path.lineTo( x1, y1 );
                    path.lineTo( x2, y2 );
                }
                currentX += step;
            }
        }

        // Finishing the top side
        currentX = r.x + r.width;
        x1 = currentX + amplitude;
        y1 = r.y;
        x2 = currentX;
        y2 = currentY + step;
        if( type == ConversationLine.Type.THOUGHT ) {
            path.quadTo( x1, y1, x2, y2 );
        }
        else if( type == ConversationLine.Type.YELL ) {
            path.lineTo( x1, y1 );
            path.lineTo( x2, y2 );
        }
        currentY += step;

        // Drawing right side
        done = false;
        while( !done ) {
            step = random.nextInt( maxStep ) + minStep;
            amplitude = random.nextInt( maxAmplitude ) + minAmplitude;
            factor = type == ConversationLine.Type.THOUGHT ? random.nextInt( maxFactor ) + 1 : 2;

            if( currentY + step > r.y + r.height ) {
                done = true;
            }
            else {
                x1 = currentX + amplitude;
                y1 = currentY + step / factor;
                x2 = currentX;
                y2 = currentY + step;
                if( type == ConversationLine.Type.THOUGHT ) {
                    path.quadTo( x1, y1, x2, y2 );
                }
                else if( type == ConversationLine.Type.YELL ) {
                    path.lineTo( x1, y1 );
                    path.lineTo( x2, y2 );
                }
                currentY += step;
            }
        }

        // Finishing the right side
        currentY = r.y + r.height;
        x1 = currentX;
        y1 = currentY + amplitude;
        x2 = currentX - step;
        y2 = currentY;
        if( type == ConversationLine.Type.THOUGHT ) {
            path.quadTo( x1, y1, x2, y2 );
        }
        else if( type == ConversationLine.Type.YELL ) {
            path.lineTo( x1, y1 );
            path.lineTo( x2, y2 );
        }
        currentX -= step;

        // Drawing bottom side
        done = false;
        while( !done ) {
            step = random.nextInt( maxStep ) + minStep;
            amplitude = random.nextInt( maxAmplitude ) + minAmplitude;
            factor = type == ConversationLine.Type.THOUGHT ? random.nextInt( maxFactor ) + 1 : 2;

            if( currentX - step < r.x ) {
                done = true;
            }
            else {

                x1 = currentX - step / factor;
                y1 = currentY + amplitude;
                x2 = currentX - step;
                y2 = currentY;
                if( type == ConversationLine.Type.THOUGHT ) {
                    path.quadTo( x1, y1, x2, y2 );
                }
                else if( type == ConversationLine.Type.YELL ) {
                    path.lineTo( x1, y1 );
                    path.lineTo( x2, y2 );
                }
                currentX -= step;
            }
        }

        // Finishing botoom side
        currentX = r.x;
        x1 = currentX - amplitude;
        y1 = r.y + r.height;
        x2 = currentX;
        y2 = currentY - step;
        if( type == ConversationLine.Type.THOUGHT ) {
            path.quadTo( x1, y1, x2, y2 );
        }
        else if( type == ConversationLine.Type.YELL ) {
            path.lineTo( x1, y1 );
            path.lineTo( x2, y2 );
        }
        currentY -= step;

        // Drawing left side
        done = false;
        while( !done ) {
            step = random.nextInt( maxStep ) + minStep;
            amplitude = random.nextInt( maxAmplitude ) + minAmplitude;
            factor = type == ConversationLine.Type.THOUGHT ? random.nextInt( maxFactor ) + 1 : 2;

            if( currentY - step < r.y ) {
                done = true;
            }
            else {
                x1 = currentX - amplitude;
                y1 = currentY - step / factor;
                x2 = currentX;
                y2 = currentY - step;
                if( type == ConversationLine.Type.THOUGHT ) {
                    path.quadTo( x1, y1, x2, y2 );
                }
                else if( type == ConversationLine.Type.YELL ) {
                    path.lineTo( x1, y1 );
                    path.lineTo( x2, y2 );
                }
                currentY -= step;
            }
        }

        // Finishing left side
        x1 = r.x - amplitude;
        y1 = r.y;
        x2 = r.x;
        y2 = r.y;
        if( type == ConversationLine.Type.THOUGHT ) {
            path.quadTo( x1, y1, x2, y2 );
        }
        else if( type == ConversationLine.Type.YELL ) {
            path.lineTo( x1, y1 );
            path.lineTo( x2, y2 );
        }
        path.closePath( );

        return path;
    }

    private static GeneralPath getNormalPath( Rectangle r ) {

        GeneralPath path = new GeneralPath( );
        path.moveTo( r.x + roundRadius, r.y );
        path.lineTo( r.x + r.width - roundRadius, r.y );
        path.quadTo( r.x + r.width, r.y, r.x + r.width, r.y + roundRadius );
        path.lineTo( r.x + r.width, r.y + r.height - roundRadius );
        path.quadTo( r.x + r.width, r.y + r.height, r.x + r.width - roundRadius, r.y + r.height );
        path.lineTo( r.x + roundRadius, r.y + r.height );
        path.quadTo( r.x, r.y + r.height, r.x, r.height + r.y - roundRadius );
        path.lineTo( r.x, r.y + roundRadius );
        path.quadTo( r.x, r.y, r.x + roundRadius, r.y );
        path.closePath( );
        return path;
    }

    public static Stroke getStroke( ) {

        switch( type ) {
            case WHISPER:
                return new BasicStroke( 1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 5.0f, new float[] { 5.0f }, 0 );
            case YELL:
                return new BasicStroke( 2 );
            default:
                return new BasicStroke( 1 );
        }
    }

    public static AffineTransform getTransformation( ) {

        switch( type ) {
            case WHISPER:
                if( whisper == null ) {
                    whisper = new AffineTransform( );
                    whisper.translate( 20.0f, 0 );
                    whisper.shear( -0.1f, 0 );
                }
                return whisper;
            default:
                return normal;
        }
    }

    public static Shape[] getArrow( int x, int y ) {

        switch( type ) {
            case THOUGHT:
                return new Shape[] { new Ellipse2D.Float( x, y + 25.0f, 12.0f, 12.0f ), new Ellipse2D.Float( x, y + 40.0f, 8.0f, 8.0f ) };
            case YELL:
                GeneralPath angryPath = new GeneralPath( );
                angryPath.moveTo( x - 20, y + 13 );
                angryPath.lineTo( x - 15, y + 45 );
                angryPath.lineTo( x - 7, y + 40 );
                angryPath.lineTo( x, y + 60 );
                angryPath.lineTo( x + 2, y + 25 );
                angryPath.lineTo( x - 5, y + 30 );
                angryPath.lineTo( x, y + 13 );
                transformPath( angryPath );
                return new Shape[] { angryPath };
            default:
                GeneralPath path = new GeneralPath( );
                path.moveTo( x - 15, y + 15 );
                path.lineTo( x - 3, y + 30 );
                path.lineTo( x, y + 15 );
                transformPath( path );
                return new Shape[] { path };
        }
    }

    private static void transformPath( GeneralPath path ) {

       /* switch( position ) {
            case WEST:
                
                path.transform( AffineTransform.getTranslateInstance( -HORIZONTAL_MARGIN, 0 ) );
                path.transform( AffineTransform.getQuadrantRotateInstance( 3 ) );
                break;
        }*/
    }
}