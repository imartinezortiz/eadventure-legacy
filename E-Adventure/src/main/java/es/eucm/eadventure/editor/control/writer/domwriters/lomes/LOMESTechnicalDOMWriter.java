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
package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMOrComposite;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESTechnical;

public class LOMESTechnicalDOMWriter extends LOMESSimpleDataWriter {

    /**
     * Private constructor.
     */
    private LOMESTechnicalDOMWriter( ) {

    }

    public static Node buildDOM( LOMESTechnical technical ) {

        Element technicalElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            technicalElement = doc.createElement( "lomes:technical" );
            technicalElement.setAttribute( "vocElement", "technical" );

            // Create the format node
            /*Element format = doc.createElement("format");
            format.setTextContent(technical.getFormat());
            technicalElement.appendChild(format);
            
            //Create the location node
            Element location = doc.createElement("location");
            String loc = technical.getLocation();
            if (loc.startsWith("0")){
            	location.setAttribute("type", "URI");
            } else if (loc.startsWith("1")){
            	location.setAttribute("type", "TEXT");
            }
            location.setTextContent(loc.substring(2));
            technicalElement.appendChild(location);
            */

            //Create the requirement node
            Element requirement = doc.createElement( "lomes:requirement" );

            for( int i = 0; i < technical.getRequirement( ).getSize( ); i++ ) {

                Element orComposite = doc.createElement( "lomes:orComposite" );

                orComposite.appendChild( buildVocabularyNode( doc, "lomes:type", ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getType( ) ) );
                orComposite.appendChild( buildVocabularyNode( doc, "lomes:name", ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getName( ) ) );

                //Create the minimum version node
                if( isStringSet( ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getMinimumVersion( ) ) ) {
                    Element minVer = doc.createElement( "lomes:minimumVersion" );
                    minVer.setAttribute( "vocElement", "minimumVersion" );
                    minVer.setTextContent( ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getMinimumVersion( ) );
                    orComposite.appendChild( minVer );
                }
                //Create the maximum version node
                if( isStringSet( ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getMaximumVersion( ) ) ) {
                    Element maxVer = doc.createElement( "lomes:maximumVersion" );
                    maxVer.setAttribute( "vocElement", "maximumVersion" );
                    maxVer.setTextContent( ( (LOMOrComposite) technical.getRequirement( ).get( i ) ).getMaximumVersion( ) );
                    orComposite.appendChild( maxVer );
                }

                requirement.appendChild( orComposite );
            }
            technicalElement.appendChild( requirement );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return technicalElement;
    }
}
