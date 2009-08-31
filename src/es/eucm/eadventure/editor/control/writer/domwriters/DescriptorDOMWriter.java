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
package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.common.data.adventure.CustomArrow;
import es.eucm.eadventure.common.data.adventure.CustomButton;
import es.eucm.eadventure.common.data.adventure.CustomCursor;
import es.eucm.eadventure.common.data.adventure.DescriptorData;

public class DescriptorDOMWriter {

    /**
     * Private constructor.
     */
    private DescriptorDOMWriter( ) {

    }

    /**
     * Returns the DOM element for the descriptor of the adventure.
     * 
     * @param adventureData
     *            Adventure from which the descriptor will be taken
     * @param valid
     *            True if the adventure is valid (can be executed in the
     *            engine), false otherwise
     * @return DOM element with the descriptor data
     */
    public static Node buildDOM( AdventureDataControl adventureData, boolean valid ) {

        Node descriptorNode = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            descriptorNode = doc.createElement( "game-descriptor" );
            ( (Element) descriptorNode ).setAttribute( "versionNumber", adventureData.getAdventureData( ).getVersionNumber( ) );

            // Create and append the title
            Node adventureTitleNode = doc.createElement( "title" );
            adventureTitleNode.appendChild( doc.createTextNode( adventureData.getTitle( ) ) );
            descriptorNode.appendChild( adventureTitleNode );

            // Create and append the description
            Node adventureDescriptionNode = doc.createElement( "description" );
            adventureDescriptionNode.appendChild( doc.createTextNode( adventureData.getDescription( ) ) );
            descriptorNode.appendChild( adventureDescriptionNode );

            // Create and append the "invalid" tag (if necessary)
            if( !valid )
                descriptorNode.appendChild( doc.createElement( "invalid" ) );
            if( adventureData.isCommentaries( ) ) {
                descriptorNode.appendChild( doc.createElement( "automatic-commentaries" ) );
            }

            // Create and append the configuration
            Node configurationNode = doc.createElement( "configuration" );
            //GUI Element
            Element guiElement = doc.createElement( "gui" );
            if( adventureData.getGUIType( ) == DescriptorData.GUI_TRADITIONAL )
                guiElement.setAttribute( "type", "traditional" );
            else if( adventureData.getGUIType( ) == DescriptorData.GUI_CONTEXTUAL )
                guiElement.setAttribute( "type", "contextual" );

            switch( adventureData.getInventoryPosition( ) ) {
                case DescriptorData.INVENTORY_NONE:
                    guiElement.setAttribute( "inventoryPosition", "none" );
                    break;
                case DescriptorData.INVENTORY_TOP_BOTTOM:
                    guiElement.setAttribute( "inventoryPosition", "top_bottom" );
                    break;
                case DescriptorData.INVENTORY_TOP:
                    guiElement.setAttribute( "inventoryPosition", "top" );
                    break;
                case DescriptorData.INVENTORY_BOTTOM:
                    guiElement.setAttribute( "inventoryPosition", "bottom" );
                    break;
            }

            if( adventureData.getCursors( ).size( ) > 0 ) {
                Node cursorsNode = doc.createElement( "cursors" );
                for( CustomCursor cursor : adventureData.getCursors( ) ) {
                    Element currentCursor = doc.createElement( "cursor" );
                    currentCursor.setAttribute( "type", cursor.getType( ) );
                    currentCursor.setAttribute( "uri", cursor.getPath( ) );
                    cursorsNode.appendChild( currentCursor );
                }
                guiElement.appendChild( cursorsNode );
            }

            if( adventureData.getButtons( ).size( ) > 0 ) {
                Node buttonsNode = doc.createElement( "buttons" );
                for( CustomButton button : adventureData.getButtons( ) ) {
                    Element currentButton = doc.createElement( "button" );
                    currentButton.setAttribute( "action", button.getAction( ) );
                    currentButton.setAttribute( "type", button.getType( ) );
                    currentButton.setAttribute( "uri", button.getPath( ) );
                    buttonsNode.appendChild( currentButton );
                }
                guiElement.appendChild( buttonsNode );
            }

            if( adventureData.getArrows( ).size( ) > 0 ) {
                Node arrowNode = doc.createElement( "arrows" );
                for( CustomArrow arrow : adventureData.getArrows( ) ) {
                    Element currentArrow = doc.createElement( "arrow" );
                    currentArrow.setAttribute( "type", arrow.getType( ) );
                    currentArrow.setAttribute( "uri", arrow.getPath( ) );
                    arrowNode.appendChild( currentArrow );
                }
                guiElement.appendChild( arrowNode );
            }

            configurationNode.appendChild( guiElement );

            //Player mode element
            Element playerModeElement = doc.createElement( "mode" );
            if( adventureData.getPlayerMode( ) == DescriptorData.MODE_PLAYER_1STPERSON )
                playerModeElement.setAttribute( "playerTransparent", "yes" );
            else if( adventureData.getPlayerMode( ) == DescriptorData.MODE_PLAYER_3RDPERSON )
                playerModeElement.setAttribute( "playerTransparent", "no" );
            configurationNode.appendChild( playerModeElement );

            //Graphic config element
            Element graphicConfigElement = doc.createElement( "graphics" );
            if( adventureData.getGraphicConfig( ) == DescriptorData.GRAPHICS_WINDOWED )
                graphicConfigElement.setAttribute( "mode", "windowed" );
            else if( adventureData.getGraphicConfig( ) == DescriptorData.GRAPHICS_FULLSCREEN )
                graphicConfigElement.setAttribute( "mode", "fullscreen" );
            else if( adventureData.getGraphicConfig( ) == DescriptorData.GRAPHICS_BLACKBKG )
                graphicConfigElement.setAttribute( "mode", "blackbkg" );
            configurationNode.appendChild( graphicConfigElement );

            //Append configurationNode
            descriptorNode.appendChild( configurationNode );

            // Create and add the contents with the chapters
            Node contentsNode = doc.createElement( "contents" );
            int chapterIndex = 1;
            for( Chapter chapter : adventureData.getChapters( ) ) {
                // Create the chapter and add the path to it
                Element chapterElement = doc.createElement( "chapter" );
                chapterElement.setAttribute( "path", "chapter" + chapterIndex++ + ".xml" );

                // Create and append the title
                Node chapterTitleNode = doc.createElement( "title" );
                chapterTitleNode.appendChild( doc.createTextNode( chapter.getTitle( ) ) );
                chapterElement.appendChild( chapterTitleNode );

                // Create and append the description
                Node chapterDescriptionNode = doc.createElement( "description" );
                chapterDescriptionNode.appendChild( doc.createTextNode( chapter.getDescription( ) ) );
                chapterElement.appendChild( chapterDescriptionNode );

                // Create and append the adaptation configuration
                /*if( !chapter.getAdaptationPath( ).equals( "" ) ) {
                	Element adaptationPathElement = doc.createElement( "adaptation-configuration" );
                	adaptationPathElement.setAttribute( "path", chapter.getAdaptationPath( ) );
                	chapterElement.appendChild( adaptationPathElement );
                }

                // Create and append the assessment configuration
                if( !chapter.getAssessmentPath( ).equals( "" ) ) {
                	Element assessmentPathElement = doc.createElement( "assessment-configuration" );
                	assessmentPathElement.setAttribute( "path", chapter.getAssessmentPath( ) );
                	chapterElement.appendChild( assessmentPathElement );
                }*/

                // Store the node
                contentsNode.appendChild( chapterElement );
            }
            // Store the chapters in the descriptor
            descriptorNode.appendChild( contentsNode );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return descriptorNode;
    }
}
