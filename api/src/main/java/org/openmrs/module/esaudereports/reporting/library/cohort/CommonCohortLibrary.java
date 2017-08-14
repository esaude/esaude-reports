package org.openmrs.module.esaudereports.reporting.library.cohort;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Program;
import org.openmrs.api.PatientSetService;
import org.openmrs.module.esaudereports.reporting.cohort.definition.DateObsValueBetweenCohortDefinition;
import org.openmrs.module.esaudereports.reporting.metadata.Dictionary;
import org.openmrs.module.esaudereports.reporting.metadata.Metadata;
import org.openmrs.module.esaudereports.reporting.utils.CoreUtils;
import org.openmrs.module.esaudereports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CodedObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.EncounterCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.GenderCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.NumericObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.ProgramEnrollmentCohortDefinition;
import org.openmrs.module.reporting.common.RangeComparator;
import org.openmrs.module.reporting.common.SetComparator;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Nicholas Ingosi on 6/20/17. Library of common cohort definitions
 */
@Component
public class CommonCohortLibrary {
	
	//concept declaration
	EncounterType ADULTO_INICIAL_A = CoreUtils.getEncounterType(Metadata._EncounterType.ADULTO_INICIAL_A_5);
	
	EncounterType ADULTO_SEGUIMENTO = CoreUtils.getEncounterType(Metadata._EncounterType.ADULTO_SEGUIMENTO_6);
	
	Concept pregnant = Dictionary.getConcept(Dictionary.PREGNANT_1982);
	
	Concept gestation = Dictionary.getConcept(Dictionary.GESTATION_44);
	
	Concept pregnancyDueDate = Dictionary.getConcept(Dictionary.PREGNANCY_DUE_DATE_1600);
	
	Concept numberOfWeeks = Dictionary.getConcept(Dictionary.NUMBER_OF_WEEKS_PREGNANT_1279);
	
	/**
	 * Patients who are female
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition females() {
		GenderCohortDefinition cd = new GenderCohortDefinition();
		cd.setName("females");
		cd.setFemaleIncluded(true);
		return cd;
	}
	
	/**
	 * Patients who are male
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition males() {
		GenderCohortDefinition cd = new GenderCohortDefinition();
		cd.setName("males");
		cd.setMaleIncluded(true);
		return cd;
	}
	
	/**
	 * Patients who at most maxAge years old on ${effectiveDate}
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition agedAtMost(int maxAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged at most " + maxAge);
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMaxAge(maxAge);
		return cd;
	}
	
	/**
	 * Patients who are at least minAge years old on ${effectiveDate}
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition agedAtLeast(int minAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged at least " + minAge);
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMinAge(minAge);
		return cd;
	}
	
	/**
	 * patients who are at least minAge years old and at most years old on ${effectiveDate}
	 * 
	 * @return CohortDefinition
	 */
	public CohortDefinition agedAtLeastAgedAtMost(int minAge, int maxAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged between " + minAge + " and " + maxAge + " years");
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMinAge(minAge);
		cd.setMaxAge(maxAge);
		return cd;
	}
	
	/**
	 * Patients who have an encounter between ${onOrAfter} and ${onOrBefore}
	 * 
	 * @param types the encounter types
	 * @return the cohort definition
	 */
	public CohortDefinition hasEncounter(EncounterType... types) {
		EncounterCohortDefinition cd = new EncounterCohortDefinition();
		cd.setName("has encounter between dates");
		cd.setTimeQualifier(TimeQualifier.ANY);
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		if (types.length > 0) {
			cd.setEncounterTypeList(Arrays.asList(types));
		}
		return cd;
	}
	
	/**
	 * Patients who have an obs between ${onOrAfter} and ${onOrBefore}
	 * 
	 * @param question the question concept
	 * @param answers the answers to include
	 * @return the cohort definition
	 */
	public CohortDefinition hasObs(Concept question, Concept... answers) {
		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.setName("has obs between dates");
		cd.setQuestion(question);
		cd.setOperator(SetComparator.IN);
		cd.setTimeModifier(PatientSetService.TimeModifier.ANY);
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		if (answers.length > 0) {
			cd.setValueList(Arrays.asList(answers));
		}
		return cd;
	}
	
	/**
	 * Patients who were enrolled on the given programs between ${enrolledOnOrAfter} and
	 * ${enrolledOnOrBefore}
	 * 
	 * @param programs the programs
	 * @return the cohort definition
	 */
	public CohortDefinition enrolled(Program... programs) {
		ProgramEnrollmentCohortDefinition cd = new ProgramEnrollmentCohortDefinition();
		cd.setName("enrolled in program between dates");
		cd.addParameter(new Parameter("enrolledOnOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("enrolledOnOrBefore", "Before Date", Date.class));
		if (programs.length > 0) {
			cd.setPrograms(Arrays.asList(programs));
		}
		return cd;
	}
	
	/**
	 * Genearl finding of numeric obs
	 * 
	 * @return CohortDefinition
	 */
	public CohortDefinition hasNumericObs(Concept q, double lower, double upper) {
		NumericObsCohortDefinition cd = new NumericObsCohortDefinition();
		cd.setName("Has numeric ranging between " + lower + " and " + upper);
		cd.addParameter(new Parameter("onOrAfter", "Start Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "End Date", Date.class));
		cd.setQuestion(q);
		cd.setTimeModifier(PatientSetService.TimeModifier.ANY);
		cd.setOperator1(RangeComparator.GREATER_THAN);
		cd.setValue1(lower);
		cd.setOperator2(RangeComparator.LESS_EQUAL);
		cd.setValue2(upper);
		return cd;
	}
	
	/**
	 * Pregnant women
	 * 
	 * @return CohortDefinition
	 */
	public CohortDefinition pregnant() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("location", "Location", Location.class));
		
		DateObsValueBetweenCohortDefinition dcd = new DateObsValueBetweenCohortDefinition();
		dcd.setName("Pregnant between dates");
		dcd.setQuestion(pregnancyDueDate);
		dcd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		dcd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		
		cd.addSearch("inProgram", ReportUtils.map(enrolled(CoreUtils.getProgram(Metadata._Program.PTV_ETV_8)),
		    "enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("hasEncounter5and6", ReportUtils.map(hasEncounter(ADULTO_INICIAL_A, ADULTO_SEGUIMENTO),
		    "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore},location=${location}"));
		cd.addSearch("hasPregnantObs",
		    ReportUtils.map(hasObs(pregnant, gestation), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("dcd", ReportUtils.map(dcd, "onOrAfter=${OnOrAfter},onOrBefore=${OnOrBefore}"));
		cd.addSearch("weeks",
		    ReportUtils.map(hasNumericObs(numberOfWeeks, 0, 44), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("inProgram OR (hasEncounter5and6 AND hasPregnantObs) OR (hasEncounter5and6 AND dcd) OR (hasEncounter5and6 AND weeks)");
		return cd;
	}
}
