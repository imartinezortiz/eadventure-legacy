package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMContribute;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMRequirement;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMTaxon;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMTaxonPath;
import es.eucm.eadventure.editor.data.meta.ims.IMSClassification;
import es.eucm.eadventure.editor.data.meta.ims.IMSEducational;
import es.eucm.eadventure.editor.data.meta.ims.IMSGeneral;
import es.eucm.eadventure.editor.data.meta.ims.IMSLifeCycle;
import es.eucm.eadventure.editor.data.meta.ims.IMSMetaMetaData;
import es.eucm.eadventure.editor.data.meta.ims.IMSRights;
import es.eucm.eadventure.editor.data.meta.ims.IMSTechnical;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESClassification;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESEducational;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESGeneral;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESLifeCycle;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESMetaMetaData;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESRights;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESTechnical;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESDataControl {

	public static final String DEFAULT_LANGUAGE="en";
	
	private LOMESGeneralDataControl general;
	
	private LOMESLifeCycleDataControl lifeCycle;
	
	private LOMESTechnicalDataControl technical;
	
	private LOMESEducationalDataControl educational;
	
	private LOMESMetaMetaDataControl metametadata;
	
	private LOMESRightsDataControl rights;
	
	private LOMESClassificationDataControl classification;
	
	public LOMESDataControl () {
		LOMESGeneral generalData = new LOMESGeneral();
		generalData.setTitle( new LangString("Default") );
		generalData.addIdentifier("Default","Default");
		generalData.setDescription( new LangString("Default") );
		generalData.setKeyword( new LangString("Default") );
		generalData.setLanguage( DEFAULT_LANGUAGE );
		generalData.setAggregationLevel(0);
		general = new LOMESGeneralDataControl(generalData);
		
		LOMESLifeCycle lifeCycle = new LOMESLifeCycle();
		lifeCycle.setVersion( new LangString("Default") );
		lifeCycle.addStatus(0);
		lifeCycle.setContribute(new LOMContribute(Vocabulary.LC_CONTRIBUTION_TYPE_2_3_1));
		this.lifeCycle = new LOMESLifeCycleDataControl(lifeCycle);
		
		LOMESTechnical tech = new LOMESTechnical();
		tech.setRequirement(new LOMRequirement());
		this.technical = new LOMESTechnicalDataControl(tech);
		
		LOMESEducational educ = new LOMESEducational();
		educ.setTypicalLearningTime( new String("") );
		educ.setLanguage( DEFAULT_LANGUAGE );
		educ.setDescription( new LangString("Default") );
		educ.setTypicalAgeRange( new LangString("Default") );
		educ.setContext( 0 );
		educ.setIntendedEndUserRole( 0 );
		educ.setLearningResourceType( 0 );
		educ.setCognitiveProcess(0);
		educ.setTypicalLearningTime("PT1H30M");
		educational = new LOMESEducationalDataControl(educ);
		
		LOMESMetaMetaData metametad = new LOMESMetaMetaData();
		metametad.addIdentifier("Default","Default");
		metametad.setContribute(new LOMContribute(Vocabulary.MD_CONTRIBUTION_TYPE_2_3_1));
		metametad.setDescription("Empty");
		metametad.setLanguage("en");
		metametad.setMetadatascheme(new String("Empty"));
		metametadata = new LOMESMetaMetaDataControl(metametad);
		
		LOMESRights right = new LOMESRights();
		right.setCopyrightandotherrestrictions(0);
		right.setCost(0);
		right.setDescription(new LangString("Empty"));
		right.setAccessType(0);
		right.setAccessDescription(new LangString("None"));
		
		rights = new LOMESRightsDataControl(right);
		
		LOMESClassification classif = new LOMESClassification();
		classif.setDescription(new LangString("Default"));
		classif.setKeyword(new LangString("Default"));
		classif.setPurpose(0);
		classif.setTaxonPath(new LOMTaxonPath(new LangString("Default"),new LOMTaxon(new String("Default"),new LangString("Default"))));
		classification = new LOMESClassificationDataControl(classif);
		
		
	}

	public void updateLanguage(){
		String language = general.getData( ).getLanguage( );
		LangString.updateLanguage( language );
	}
	
	/**
	 * @return the general
	 */
	public LOMESGeneralDataControl getGeneral( ) {
		return general;
	}

	/**
	 * @param general the general to set
	 */
	public void setGeneral( LOMESGeneralDataControl general ) {
		this.general = general;
	}

	/**
	 * @return the lifeCycle
	 */
	public LOMESLifeCycleDataControl getLifeCycle( ) {
		return lifeCycle;
	}

	/**
	 * @param lifeCycle the lifeCycle to set
	 */
	public void setLifeCycle( LOMESLifeCycleDataControl lifeCycle ) {
		this.lifeCycle = lifeCycle;
	}

	/**
	 * @return the technical
	 */
	public LOMESTechnicalDataControl getTechnical( ) {
		return technical;
	}

	/**
	 * @param technical the technical to set
	 */
	public void setTechnical( LOMESTechnicalDataControl technical ) {
		this.technical = technical;
	}

	/**
	 * @return the educational
	 */
	public LOMESEducationalDataControl getEducational( ) {
		return educational;
	}

	/**
	 * @param educational the educational to set
	 */
	public void setEducational( LOMESEducationalDataControl educational ) {
		this.educational = educational;
	}

	/**
	 * 
	 * @return the meta meta data
	 */
	public LOMESMetaMetaDataControl getMetametadata() {
		return metametadata;
	}

	/**
	 * 
	 * @param metametadata to be set
	 */
	public void setMetametadata(LOMESMetaMetaDataControl metametadata) {
		this.metametadata = metametadata;
	}

	/**
	 * 
	 * @return
	 */
	public LOMESRightsDataControl getRights() {
		return rights;
	}

	/**
	 * 
	 * @param rights
	 */
	public void setRights(LOMESRightsDataControl rights) {
		this.rights = rights;
	}

	/**
	 * 
	 * @return
	 */
	public LOMESClassificationDataControl getClassification() {
		return classification;
	}

	/**
	 * 
	 * @param classification
	 */
	public void setClassification(LOMESClassificationDataControl classification) {
		this.classification = classification;
	}
}
