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
package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.OptionConversationNode;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.tools.conversation.AddConversationNodeTool;
import es.eucm.eadventure.editor.control.tools.conversation.AddNodeLineTool;
import es.eucm.eadventure.editor.control.tools.conversation.DeleteNodeLineTool;
import es.eucm.eadventure.editor.control.tools.conversation.DeleteNodeLinkTool;
import es.eucm.eadventure.editor.control.tools.conversation.DeleteNodeOptionTool;
import es.eucm.eadventure.editor.control.tools.conversation.MoveNodeLineTool;
import es.eucm.eadventure.editor.control.tools.conversation.OptionsPositionTool;
import es.eucm.eadventure.editor.control.tools.conversation.SelectLineAudioPathTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeNameTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeStringValueTool;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;

public abstract class ConversationDataControl extends DataControl {

    /**
     * Returns the type of the contained conversation.
     * 
     * @return Type of the contained conversation
     */
    public abstract int getType( );

    /**
     * Returns the id of the contained conversation.
     * 
     * @return Id of the contained conversation
     */
    public abstract String getId( );

    /**
     * Returns the root node of the conversation.
     * 
     * @return Root node
     */
    public abstract ConversationNodeView getRootNode( );

    /**
     * Returns the number of lines that has the conversation.
     * 
     * @return Number of lines of the conversation
     */
    public abstract int getConversationLineCount( );

    /**
     * Returns the types of nodes which can be added to the given node
     * 
     * @param nodeView
     *            Node which we want to know what kind of node can be added
     * @return Array of node types that can be added
     */
    public abstract int[] getAddableNodes( ConversationNodeView nodeView );

    /**
     * Returns if it is possible to add a child to the given node
     * 
     * @param nodeView
     *            Node which we want to know if a child is addable
     * @param nodeType
     *            The type of node that we want to add
     * @return True if a child can be added (get NodeTypes with
     *         getAddeableNodes( ConversationalNode )), false otherwise
     */
    public abstract boolean canAddChild( ConversationNodeView nodeView, int nodeType );

    /**
     * Returns if it is possible to link the given node
     * 
     * @param nodeView
     *            Node to be linked
     * @return True if the node initially can be linked to another one, false
     *         otherwise
     */
    public abstract boolean canLinkNode( ConversationNodeView nodeView );

    public abstract boolean canDeleteLink( ConversationNodeView nodeView );

    /**
     * Returns if it is possible to link a given node with another one
     * 
     * @param fatherView
     *            Node to act as the father
     * @param childView
     *            Node to act as the child
     * @return True if node2 can be attached to node1, false otherwise
     */
    public abstract boolean canLinkNodeTo( ConversationNodeView fatherView, ConversationNodeView childView );

    /**
     * Returns if it is possible to delete the given node
     * 
     * @param nodeView
     *            Node to be deleted
     * @return True if the node can be deleted, false otherwise
     */
    public abstract boolean canDeleteNode( ConversationNodeView nodeView );

    /**
     * Returns if it is possible to move the given node
     * 
     * @param nodeView
     *            Node to be moved
     * @return True if the node initially can be moved, false otherwise
     */
    public abstract boolean canMoveNode( ConversationNodeView nodeView );

    /**
     * Returns if it is possible to move the given node to a child position of
     * the given host node
     * 
     * @param nodeView
     *            Node to be moved
     * @param hostNodeView
     *            Node that will act as host
     * @return True if node can be moved as a child of host node, false
     *         otherwise
     */
    public abstract boolean canMoveNodeTo( ConversationNodeView nodeView, ConversationNodeView hostNodeView );

    /**
     * Adds a new child of the indicated type, to the given node
     * 
     * @param nodeView
     *            Node in which the child will be placed
     * @param nodeType
     *            Type of node to be added
     * @return True if a node was added, false otherwise
     */
    public boolean addChild( ConversationNodeView nodeView, int nodeType, Map<ConversationNodeView, List<ConditionsController>> allConditions ) {

        return controller.addTool( new AddConversationNodeTool( nodeView, nodeType, allConditions ) );
    }

    /**
     * Links the two given nodes, as father and child
     * 
     * @param fatherView
     *            Father node (first selected node)
     * @param childView
     *            Child node (second selected node)
     * @return True if the nodes had been successfully linked, false otherwise
     */
    public abstract boolean linkNode( ConversationNodeView fatherView, ConversationNodeView childView );

    /**
     * Deletes the given node in the conversation
     * 
     * @param nodeView
     *            Node to be deleted
     * @return True if the node was successfully deleted, false otherwise
     */
    public abstract boolean deleteNode( ConversationNodeView nodeView );

    /**
     * Moves the given node to a child position of the given host node
     * 
     * @param nodeView
     *            Node to be moved
     * @param hostNodeView
     *            Node that will act as host
     * @return True if the node was succesfully moved, false otherwise
     */
    public abstract boolean moveNode( ConversationNodeView nodeView, ConversationNodeView hostNodeView );

    /**
     * Default getter for the data contained
     * 
     * @return The conversation
     */
    public abstract Conversation getConversation( );

    /**
     * Default setter
     * 
     * @param conversation
     * @return
     */
    public abstract void setConversation( Conversation conversation );

    public abstract void updateAllConditions( );

    /**
     * Adds a line in the given node, with the given name and a default text.
     * 
     * @param nodeView
     *            Node in which the line must be placed
     * @param lineIndex
     *            Index in which the line will be placed
     * @param name
     *            Name of the line
     * @param node
     *            The list with the conditions controllers for current node
     * 
     */
    public void addNodeLine( ConversationNodeView nodeView, int lineIndex, String name, List<ConditionsController> node ) {

        controller.addTool( new AddNodeLineTool( nodeView, lineIndex, name, node ) );
    }

    /**
     * Sets a new name in the given line of the node.
     * 
     * @param nodeView
     *            Node in which the line is placed
     * @param lineIndex
     *            Index of the line to modify
     * @param name
     *            New name for the line
     */
    public void setNodeLineName( ConversationNodeView nodeView, int lineIndex, String name ) {

        // Take the complete node
        ConversationNode node = (ConversationNode) nodeView;

        controller.addTool( new ChangeNameTool( node.getLine( lineIndex ), name ) );
    }

    /**
     * Sets a new text in the given line of the node.
     * 
     * @param nodeView
     *            Node in which the line is placed
     * @param lineIndex
     *            Index of the line to modify
     * @param text
     *            New text for the line
     */
    public void setNodeLineText( ConversationNodeView nodeView, int lineIndex, String text ) {

        // Take the complete node
        ConversationNode node = (ConversationNode) nodeView;
        controller.addTool( new ChangeStringValueTool( node.getLine( lineIndex ), text, "getText", "setText" ) );
    }

    /**
     * Sets a new audio path in the given line of the node.
     * 
     * @param nodeView
     *            Node in which the line is placed
     * @param lineIndex
     *            Index of the line to modify
     * @param audioPath
     *            New audio path for the line
     */
    public void setNodeLineAudioPath( ConversationNodeView nodeView, int lineIndex, String audioPath ) {

        // Take the complete node
        ConversationNode node = (ConversationNode) nodeView;

        // Set the new text for the line if the value has changed
        if( !node.hasAudioPath( lineIndex ) || !node.getAudioPath( lineIndex ).equals( audioPath ) ) {
            controller.addTool( new ChangeStringValueTool( node.getLine( lineIndex ), audioPath, "getAudioPath", "setAudioPath" ) );
        }
    }

    /**
     * Moves a line up in the list of the given node.
     * 
     * @param nodeView
     *            Node which holds the line
     * @param lineIndex
     *            Index of the line to move
     * @return True if the line was moved, false otherwise
     */
    public boolean moveNodeLineUp( ConversationNodeView nodeView, int lineIndex ) {

        return controller.addTool( new MoveNodeLineTool( nodeView, lineIndex, MoveNodeLineTool.UP ) );
    }

    /**
     * Moves a line down in the list of the given node.
     * 
     * @param nodeView
     *            Node which holds the line
     * @param lineIndex
     *            Index of the line to move
     * @return True if the line was moved, false otherwise
     */
    public boolean moveNodeLineDown( ConversationNodeView nodeView, int lineIndex ) {

        return controller.addTool( new MoveNodeLineTool( nodeView, lineIndex, MoveNodeLineTool.DOWN ) );
    }

    /**
     * Deletes a line in the given node.
     * 
     * @param nodeView
     *            Node in which the line will be deleted
     * @param lineIndex
     *            Index of the line to delete
     * @param node
     *            The list with the conditions controllers of the given node
     * 
     */
    public void deleteNodeLine( ConversationNodeView nodeView, int lineIndex, List<ConditionsController> node ) {

        controller.addTool( new DeleteNodeLineTool( nodeView, lineIndex, node ) );
    }

    /**
     * Deletes the link with the child node. This method should only be used
     * with dialogue nodes (to delete the link with their only child). For
     * option nodes, the method <i>deleteNodeOption</i> should be used instead.
     * 
     * @param nodeView
     *            Dialogue node to delete the link
     * @return True if the link was deleted, false otherwise
     */
    public boolean deleteNodeLink( ConversationNodeView nodeView ) {

        return controller.addTool( new DeleteNodeLinkTool( nodeView ) );
    }

    /**
     * Deletes the given option in the node. This method should only be used
     * with option nodes, for it deletes the child node along the option. To
     * delete the links on dialogue nodes, use the method <i>deleteNodeLink</i>
     * instead.
     * 
     * @param nodeView
     *            Option node to delete the option
     * @param optionIndex
     *            Index of the option to be deleted
     * @return True if the option was deleted, false otherwise
     */
    public boolean deleteNodeOption( ConversationNodeView nodeView, int optionIndex ) {

        return controller.addTool( new DeleteNodeOptionTool( nodeView, optionIndex ) );
    }

    /**
     * Shows the GUI to edit the effects of the node.
     * 
     * @param nodeView
     *            Node whose effects we want to modify
     */
    public void editNodeEffects( ConversationNodeView nodeView ) {

        // Take the complete node
        ConversationNode node = (ConversationNode) nodeView;

        // Show a edit effects dialog with a new effects controller
        new EffectsDialog( new EffectsController( node.getEffects( ) ) );
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] {};
    }

    @Override
    public boolean canAddElement( int type ) {

        return false;
    }

    @Override
    public boolean canBeDeleted( ) {

        return true;
    }

    @Override
    public boolean canBeMoved( ) {

        return true;
    }

    @Override
    public boolean canBeRenamed( ) {

        return true;
    }

    @Override
    public boolean addElement( int type, String id ) {

        return false;
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        return false;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        return false;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        return false;
    }

    public boolean editLineAudioPath( ConversationNodeView selectedNode, int selectedRow ) {

        try {
            return controller.addTool( new SelectLineAudioPathTool( ( (ConversationNode) selectedNode ).getLine( selectedRow ) ) );
        }
        catch( CloneNotSupportedException e ) {
            ReportDialog.GenerateErrorReport( new Exception( "Could not clone resources" ), false, TC.get( "Error.Title" ) );
            return false;
        }
    }

    /**
     * Change the randomly in the selected node.
     * 
     * @param selectedNode
     *            The node in which will be the actions
     * 
     */
    public void setRandomlyOptions( ConversationNodeView selectedNode ) {

        ConversationNode node = (ConversationNode) selectedNode;
        //Change the randomly of showing of options
        controller.addTool( new ChangeBooleanValueTool( node, !( (OptionConversationNode) node ).isRandom( ), "isRandom", "setRandom" ) );
    }
    
    /**
     * Change the keep showing option in the selected node, for option conversation node
     * 
     * @param selectedNode
     *            The node in which will be the actions
     * 
     */
    public void setKeepShowingOptionNodeOptions( ConversationNodeView selectedNode ) {

        ConversationNode node = (ConversationNode) selectedNode;
        controller.addTool( new ChangeBooleanValueTool( node, !( (OptionConversationNode) node ).isKeepShowing( ), "isKeepShowing", "setKeepShowing" ) );
    }
    
    /**
     * Select the position of the options node (top or bottom)
     * 
     * @param selectedNode
     * @param bottomPosition
     *          if true, the option will appear at the bottom, if false, the options will appear at the top
     */
    public void setOptionPositions(ConversationNodeView selectedNode, boolean bottomPosition){
        ConversationNode node = (ConversationNode) selectedNode;
        controller.addTool(new OptionsPositionTool(( (OptionConversationNode) node ), bottomPosition) );
    }
    
    /**
     * Change the show user option in the selected node.
     * 
     * @param selectedNode
     *            The node in which will be the actions
     * 
     */
    public void setShowUserOptionOptions( ConversationNodeView selectedNode ) {

        ConversationNode node = (ConversationNode) selectedNode;
        controller.addTool( new ChangeBooleanValueTool( node, !( (OptionConversationNode) node ).isShowUserOption( ), "isShowUserOption", "setShowUserOption" ) );
    }
    
    /**
     * Change the wait user interaction option in the selected node, for dialogue node
     * 
     * @param selectedNode
     *            The node in which will be the actions
     * 
     */
    public void setKeepShowingDialogueOptions( ConversationNodeView selectedNode ) {

        ConversationNode node = (ConversationNode) selectedNode;
        controller.addTool( new ChangeBooleanValueTool( node, !( (DialogueConversationNode) node ).isKeepShowing( ), "isKeepShowing", "setKeepShowing" ) );
    }

    
    /**
     * Change the pre-hearing option in the selected option node
     * 
     * @param selectedNode
     *            The node in which will be the actions
     * 
     */
    public void setPreListeningOptions( ConversationNodeView selectedNode ) {

        ConversationNode node = (ConversationNode) selectedNode;
        controller.addTool( new ChangeBooleanValueTool( node, !( (OptionConversationNode) node ).isPreListening( ), "isPreListening", "setPreListening" ) );
    }

    
    /**
     * Check if in selectedNode is active the random option
     * 
     * @param selectedNode
     *            The node in which will it ask
     * @return
     */
    public boolean isRandomActivate( ConversationNodeView selectedNode ) {

        ConversationNode node = (ConversationNode) selectedNode;
        return ( (OptionConversationNode) node ).isRandom( );
    }
    
    /**
     * Check if in selectedNode is active the keep showing the previous conversation line, for option node
     * 
     * @param selectedNode
     *            The node in which will it ask
     * @return
     */
    public boolean isKeepShowingOptionsNodeActivate( ConversationNodeView selectedNode ) {

        ConversationNode node = (ConversationNode) selectedNode;
        return ( (OptionConversationNode) node ).isKeepShowing( );
    }

    
    /**
     * Check if in selectedNode is active the show user response option
     * 
     * @param selectedNode
     *            The node in which will it ask
     * @return
     */
    public boolean isShowUserOptionActivate( ConversationNodeView selectedNode ) {

        ConversationNode node = (ConversationNode) selectedNode;
        return ( (OptionConversationNode) node ).isShowUserOption( );
    }
    
    /**
     * Check if in selectedNode is active the pre-hearing option
     * 
     * @param selectedNode
     *            The node in which will it ask
     * @return
     */
    public boolean isPreListeningActivate( ConversationNodeView selectedNode ) {

        ConversationNode node = (ConversationNode) selectedNode;
        return ( (OptionConversationNode) node ).isPreListening( );
    }
    
    /**
     * Check if in selectedNode is active the show user response option, for dialogue node
     * 
     * @param selectedNode
     *            The node in which will it ask
     * @return
     */
    public boolean isKeepShowingDialogueActivate( ConversationNodeView selectedNode ) {

        ConversationNode node = (ConversationNode) selectedNode;
        return ( (DialogueConversationNode) node ).isKeepShowing( );
    }
    
    public int getEditorX( ConversationNodeView selectedNode ){
        ConversationNode node = (ConversationNode) selectedNode;
        return node.getEditorX( );
    }
    
    public int getEditorY( ConversationNodeView selectedNode ){
        ConversationNode node = (ConversationNode) selectedNode;
        return node.getEditorY( );
    }
    
    public void setEditorX( ConversationNodeView selectedNode, int x ){
        ConversationNode node = (ConversationNode) selectedNode;
        
        if (x!=node.getEditorX( )){
            node.setEditorX( x );
            Controller.getInstance( ).dataModified( );
        }
        
    }
    
    public void setEditorY( ConversationNodeView selectedNode, int y ){
        ConversationNode node = (ConversationNode) selectedNode;
        if (y!=node.getEditorY( )){
            node.setEditorY( y );
            Controller.getInstance( ).dataModified( );
        }
    }


    /**
     * An options node cannot be empty
     * 
     * @param node
     * @param currentPath
     * @param incidences
     * @return
     */
    protected static boolean isValidNode( ConversationNode node, String currentPath, List<String> incidences, List<ConversationNode> visitedNodes ) {

        boolean valid = true;

        if( visitedNodes == null )
            visitedNodes = new ArrayList<ConversationNode>( );

        if( !visitedNodes.contains( node ) ) {
            visitedNodes.add( node );
            if( node.getType( ) == ConversationNodeView.OPTION && node.getLineCount( ) == 0 ) {
                if( incidences != null && currentPath != null )
                    incidences.add( currentPath + " >> " + TC.get( "Operation.AdventureConsistencyErrorEmptyOptionsNode" ) );
                valid = false;
            }
            else {
                for( int i = 0; i < node.getChildCount( ); i++ ) {
                    valid &= isValidNode( node.getChild( i ), currentPath, incidences, visitedNodes );
                    if( !valid )
                        break;
                }
            }
        }
        return valid;
    }

    public abstract List<ConversationNodeView> getAllNodes( );

}
