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
package es.eucm.eadventure.editor.data.meta;

import es.eucm.eadventure.editor.control.Controller;

public class Vocabulary {

    public static final String[] ED_INTERACTIVITY_TYPE_5_1 = new String[] { "active", "expositive", "mixed" };

    public static final String[] ED_LEARNING_RESOURCE_TYPE_5_2 = new String[] { "exercise", "simulation", "questionnaire", "diagram", "figure", "graph", "index", "slide", "table", "narrative text", "exam", "experiment", "problem statement", "self assessment", "lecture" };

    public static final String[] ED_INTERACTIVITY_LEVEL_5_3 = new String[] { "very low", "low", "medium", "high", "very high" };

    public static final String[] ED_SEMANTIC_DENSITY_5_4 = new String[] { "very low", "low", "medium", "high", "very high" };

    public static final String[] ED_INTENDED_END_USER_ROLE_5_5 = new String[] { "teacher", "author", "learner", "manager" };

    public static final String[] ED_CONTEXT_5_6 = new String[] { "school", "higher education", "training", "other" };

    public static final String[] ED_DIFFICULTY_5_8 = new String[] { "very easy", "easy", "medium", "difficult", "very difficult" };

    public static final String[] TE_TYPE_4_4_1_1 = new String[] { "operating system", "browser" };

    // For IMS
    public static final String[] LC_STAUS_VALUE_2_2 = new String[] { "draft", "final", "revised", "unavailable" };

    public static final String[] IMS_CL_PURPOSE_9_1 = new String[] { "discipline", "idea", "prerequisite", "eduactional objetive", "accesibility restrictions", "eduacational level", "skill level", "security level" };

    public static final String[] IMS_ED_INTERACTIVITY_TYPE_5_1 = new String[] { "active", "expositive", "mixed", "undefined" };

    public static final String[] IMS_ED_LEARNING_RESOURCE_TYPE_5_2 = new String[] { "exercise", "simulation", "questionnaire", "diagram", "figure", "graph", "index", "slide", "table", "narrative text", "exam", "experiment", "problem statement", "self assessment" };

    public static final String[] IMS_ED_CONTEXT_5_6 = new String[] { "primary education", "secondary education", "higher education", "university first cycle", "university second cycle", "university postgrade", "profesional formation", "continuous formation", "vocational training" };

    public static final String[] IMS_YES_NO = new String[] { "yes", "no" };

    private static final String DEFAULT_SOURCE = "LOMv1.0";

    public static final String LOM_ES_SOURCE = "LOM-ESv1.0";

    // For LOM-ES
    public static final String[] GE_AGGREGATION_LEVEL_1_8 = new String[] { "1", "2", "3", "4" };

    public static final String[] MD_CONTRIBUTION_TYPE_2_3_1 = new String[] { "creator", "validator" };

    public static final String[] LC_CONTRIBUTION_TYPE_2_3_1 = new String[] { "author", "publisher", "initiator", "terminator", "validator", "editor", "graphical designer", "technical implementer", "content provider", "technical validator", "educational validator", "script writer", "instructional designer", "subject matter expert" };

    //public static final String[] LC_STATUS_2_2 = new String[] {"draft","final","revised","unavailable"};

    public static final String[] TE_NAME_4_4_1_2 = new String[] { "pc-dos", "ms-windows", "macos", "linux", "unix", "multi-os", "none", "any", "mozilla firefox", "netscape communicator", "ms-internet explorer", "opera", "amaya" };

    public static final String[] LOMES_ED_LEARNING_RESOURCE_TYPE_5_2 = new String[] { "photograph", "illustration", "video", "animation", "music", "sound effect", "voice-over", "compound audio", "narrative text", "hypertext", "computer graphics", "integrated media", "multimedia presentation", "tutorial", "guided reading", "master class", "textual-image analysis", "discussion activity", "closed exercise or problem", "contextualized case problem", "open problem", "real or virtual learning environment", "didactic game", "webquest", "experiment", "real project", "simulation", "questionnaire", "exam", "self assessment" };

    public static final String[] LOMES_ED_INTENDED_END_USER_ROLE_5_5 = new String[] { "learner", "special needs learner", "gifted learner", "learners late integration into the education system", "learner with other specific educational support needs", "general public", "individual", "group", "teacher", "tutor", "family", "information scientist", "computer scientist", "manager", "Education expert", "subject matter expert" };

    public static final String[] LOMES_ED_CONTEXT_5_6 = new String[] { "classroom", "laboratory", "real environment", "home", "mixed", "teacher", "family", "tutor", "schoolmate", "independent", "blended", "presencial", "face to face", "distance" };

    public static final String[] LOMES_ED_COGNITIVE_PROCESS_5_12 = new String[] { "analyse", "implement", "collaborate", "compare", "share", "compete", "understand", "prove", "communicate", "contextualize", "control", "cooperate", "create", "decide", "define", "describe", "discuss", "design", "self assessment", "explain", "extrapolate", "innovate", "investigate", "judge", "motivate", "observe", "organize", "organize oneself", "plan", "practise", "produce", "recognize", "remember", "write up", "consider", "connect", "represent", "solve", "simulate", "summarize", "value" };

    public static final String[] LOMES_RG_COP_AND_OTHER_6_2 = new String[] { "propietary license", "free software license EUPL", "free software license GPL", "dual free content license GPL and EUPL", "other free software licenses", "public domain", "not appropriate", "intellectual property license", "creative commons: attribution", "creative commons: attribution - non derived work", "creative commons: attribution - non derived work - non commercial", "creative commons: attribution - non commercial", "creative commons: attribution - non commercial - share alike", "creative commons: attribution - share alike", "license GFDL" };

    public static final String[] LOMES_RG_ACCESS_TYPE_6_4_1 = new String[] { "universal", "non-universal" };

    public static final String[] LOMES_CL_PURPOSE_9_1 = new String[] { "discipline", "idea", "prerequisite", "eduactional objetive", "accesibility restrictions", "eduacational level", "skill level", "security level", "competency" };

    /** ************ ATTRIBUTES ***************** */
    private String[] values;

    private int currentValue;

    private String source;

    /** ************ CONSTRUCTORS ***************** */
    public Vocabulary( String[] values ) {

        this.values = values;
        this.currentValue = 0;
        if( Controller.getInstance( ).isLomEs( ) ) {
            this.source = LOM_ES_SOURCE;
        }
        else {
            this.source = DEFAULT_SOURCE;
        }
    }

    public Vocabulary( String[] values, int value ) {

        this.values = values;
        this.currentValue = value;
        if( Controller.getInstance( ).isLomEs( ) ) {
            this.source = LOM_ES_SOURCE;
        }
        else {
            this.source = DEFAULT_SOURCE;
        }
    }

    public Vocabulary( String[] values, String value ) {

        this( values, DEFAULT_SOURCE, value );
    }

    public Vocabulary( String[] values, String source, String value ) {

        this.values = values;

        for( int i = 0; i < values.length; i++ ) {
            if( values[i].equals( value ) ) {
                currentValue = i;
            }
        }
        this.source = source;
    }

    public Vocabulary( String[] values, String source, int value ) {

        this.values = values;
        currentValue = value;
        this.source = source;
    }

    /** ************ GETTERS ***************** */
    public int getValueIndex( ) {

        return currentValue;
    }

    public String[] getValues( ) {

        return values;
    }

    public String getValue( ) {

        return values[currentValue];
    }

    public String getSource( ) {

        return source;
    }

    /** ************ SETTERS ***************** */
    public void setValueIndex( int index ) {

        currentValue = index;
    }

    public void setValues( String[] values ) {

        this.values = values;
    }

    public void setValue( String value ) {

        for( int i = 0; i < values.length; i++ )
            if( values[i].equals( value ) )
                currentValue = i;
    }

    public void setSource( String source ) {

        this.source = source;
    }

}
