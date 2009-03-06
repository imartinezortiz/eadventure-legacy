package es.eucm.eadventure.editor.control.writer.domwriters.lom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.lom.LOMTechnical;


public class LOMTechnicalDOMWriter extends LOMSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMTechnicalDOMWriter( ) {}

	public static Node buildDOM( LOMTechnical technical ,boolean scorm) {
		Element technicalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			technicalElement = doc.createElement( "imsmd:technical" );
			
			//Create the requirement node
			Element requirement = doc.createElement( "imsmd:requirement" );
			//Create the orComposite node
			Element orComposite = doc.createElement( scorm?"imsmd:orComposite":"imsmd:orcomposite" );
			
			//Create the minimum version node
			if (isStringSet(technical.getMinimumVersion( ))){
				Element minVer = doc.createElement( scorm?"imsmd:minimumVersion":"imsmd:minimumversion" );
				minVer.setTextContent( technical.getMinimumVersion( ));
				orComposite.appendChild( minVer );
			}
			
			//Create the maximum version node
			if (isStringSet(technical.getMinimumVersion( ))){
				Element maxVer = doc.createElement( scorm?"imsmd:maximumVersion":"imsmd:maximumversion" );
				maxVer.setTextContent( technical.getMaximumVersion( ));
				orComposite.appendChild( maxVer );
			}
			
			requirement.appendChild( orComposite );
			technicalElement.appendChild( requirement );
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return technicalElement;
	}
}
