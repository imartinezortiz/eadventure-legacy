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
package es.eucm.eadventure.editor.gui.otherpanels.imagepanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.gui.auxiliar.JPositionedFrame;

public class TestLargeImages extends JPositionedFrame {

    private ImagePanel imagePanel;

    public TestLargeImages( ) {

        super( "Prueba" );

        imagePanel = new ImagePanel( );
        this.getContentPane( ).setLayout( new BorderLayout( ) );
        this.getContentPane( ).add( imagePanel, BorderLayout.CENTER );

        JPanel buttonsPanel = new JPanel( );
        JButton load = new JButton( "Load" );
        load.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                JFileChooser chooser = new JFileChooser( );
                chooser.showOpenDialog( null );
                if( chooser.getSelectedFile( ) != null )
                    imagePanel.loadImage( chooser.getSelectedFile( ).getAbsolutePath( ) );
            }

        } );
        buttonsPanel.add( load );
        this.addWindowListener( new WindowListener( ) {

            public void windowActivated( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

            public void windowClosed( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

            public void windowClosing( WindowEvent e ) {

                setVisible( false );
                dispose( );
            }

            public void windowDeactivated( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

            public void windowDeiconified( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

            public void windowIconified( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

            public void windowOpened( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

        } );
        this.getContentPane( ).add( buttonsPanel, BorderLayout.SOUTH );
        this.setSize( 800, 600 );
        this.setVisible( true );

    }

    public static void main( String[] args ) {

        TC.loadStrings( "english.xml" );
        new TestLargeImages( );
    }

}
