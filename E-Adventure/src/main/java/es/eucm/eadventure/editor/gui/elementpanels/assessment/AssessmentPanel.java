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
package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class AssessmentPanel extends ElementPanel {

    private static final long serialVersionUID = 1L;

    public AssessmentPanel( AssessmentProfileDataControl dataControl ) {

        addTab( new AssessmentEditionPanelTab( dataControl ) );
    }

    private class AssessmentEditionPanelTab extends PanelTab {

        private AssessmentProfileDataControl sDataControl;

        public AssessmentEditionPanelTab( AssessmentProfileDataControl sDataControl ) {

            super( TC.get( "AssessmentProfile.Title" ), sDataControl );
            this.setHelpPath( "assessment/assessmentProfile.html" );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new AssessmentEditionPanel( sDataControl );
        }
    }
}
