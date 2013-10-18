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
package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;

public class DeleteNodeOptionTool extends DeleteNodeLinkTool {

    protected ConversationLine deletedLine;

    public DeleteNodeOptionTool( ConversationNode parent, int optionIndex ) {

        super( parent );
        this.confirmText = TC.get( "Conversation.ConfirmationDeleteOption" );
        this.confirmTitle = TC.get( "Conversation.OperationDeleteOption" );
        this.linkIndex = optionIndex;
    }

    public DeleteNodeOptionTool( ConversationNodeView parent, int optionIndex ) {

        super( parent );
        this.confirmText = TC.get( "Conversation.ConfirmationDeleteOption" );
        this.confirmTitle = TC.get( "Conversation.OperationDeleteOption" );
        this.linkIndex = optionIndex;
    }

    @Override
    public boolean doTool( ) {

        boolean done = super.doTool( );
        if( done ) {
            deletedLine = parent.getLine( linkIndex );
            parent.removeLine( linkIndex );
        }
        return done;
    }

    @Override
    public boolean undoTool( ) {

        boolean done = super.undoTool( );
        if( done ) {
            parent.addLine( linkIndex, deletedLine );
        }
        return done;
    }

    @Override
    public boolean redoTool( ) {

        boolean done = super.redoTool( );
        if( done ) {
            parent.removeLine( linkIndex );
        }
        return done;
    }

}