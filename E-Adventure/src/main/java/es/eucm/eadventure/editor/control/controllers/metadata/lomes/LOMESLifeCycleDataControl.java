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
package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESLifeCycle;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESLifeCycleDataControl {

    private LOMESLifeCycle data;

    public LOMESLifeCycleDataControl( LOMESLifeCycle data ) {

        this.data = data;
    }

    public LOMESTextDataControl getVersionController( ) {

        return new LOMESTextDataControl( ) {

            public String getText( ) {

                return data.getVersion( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setVersion( new LangString( text ) );
            }

        };
    }

    public LOMESOptionsDataControl getStatusController( ) {

        return new LOMESOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getStatus( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "IMS.LifeCycle.Status" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getStatus( ).setValueIndex( option );
            }

            public int getSelectedOption( ) {

                return data.getStatus( ).getValueIndex( );
            }

        };
    }

    public LOMESContainer getContribute( ) {

        return data.getContribute( );
    }

    /**
     * @return the data
     */
    public LOMESLifeCycle getData( ) {

        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( LOMESLifeCycle data ) {

        this.data = data;
    }

}
