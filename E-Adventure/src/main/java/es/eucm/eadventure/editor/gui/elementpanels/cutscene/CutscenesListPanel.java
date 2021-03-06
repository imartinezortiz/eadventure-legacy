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
package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutscenesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class CutscenesListPanel extends ElementPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param cutscenesListDataControl
     *            Cutscenes list controller
     */
    public CutscenesListPanel( CutscenesListDataControl cutscenesListDataControl ) {

        addTab( new CutscenesListPanelTab( cutscenesListDataControl ) );
    }

    private class CutscenesListPanelTab extends PanelTab {

        private CutscenesListDataControl sDataControl;

        public CutscenesListPanelTab( CutscenesListDataControl sDataControl ) {

            super( TC.get( "CutscenesList.Title" ), sDataControl );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            JPanel cutscenesListPanel = new JPanel( );
            cutscenesListPanel.setLayout( new BorderLayout( ) );
            List<DataControl> dataControlList = new ArrayList<DataControl>( );
            for( CutsceneDataControl item : sDataControl.getCutscenes( ) ) {
                dataControlList.add( item );
            }
            ResizeableCellRenderer renderer = new CutsceneCellRenderer( );
            cutscenesListPanel.add( new ResizeableListPanel( dataControlList, renderer, "CutscenesListPanel" ), BorderLayout.CENTER );
            return cutscenesListPanel;
        }
    }

}
