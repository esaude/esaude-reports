package org.openmrs.module.esaudereports.reports;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openmrs.module.esaudereports.EsaudeDataExportManager;
import org.openmrs.module.esaudereports.reports.mmia.definition.MmiaArtDrugBalanceteDataSetDefinition;
import org.openmrs.module.esaudereports.reports.mmia.definition.MmiaArtRegimenBalanceteDataSetDefinition;
import org.openmrs.module.esaudereports.reports.mmia.definition.MmiaPatientBalanceteDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

@Component
public class SetupMmiaReport extends EsaudeDataExportManager {
	
	@Override
	public ReportDefinition constructReportDefinition() {
		
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.setParameters(getParameters());
		
		// Added dataSet for Drugs Balancets
		MmiaArtDrugBalanceteDataSetDefinition mmiaDataSetDefinition = new MmiaArtDrugBalanceteDataSetDefinition();
		mmiaDataSetDefinition.setName("arvDrugDetails");
		mmiaDataSetDefinition.setParameters(getParameters());
		rd.addDataSetDefinition("drugBalancete", Mapped.mapStraightThrough(mmiaDataSetDefinition));
		
		// Added dataSet for Regimens Balancets
		MmiaArtRegimenBalanceteDataSetDefinition artRegimenDataSetDefinition = new MmiaArtRegimenBalanceteDataSetDefinition();
		artRegimenDataSetDefinition.setName("regimemDetails");
		artRegimenDataSetDefinition.setParameters(getParameters());
		rd.addDataSetDefinition("regimenBalancete", Mapped.mapStraightThrough(artRegimenDataSetDefinition));
		
		// Added dataSet for Patients balancets
		MmiaPatientBalanceteDataSetDefinition patientBalanceteDataSetDefinition = new MmiaPatientBalanceteDataSetDefinition();
		patientBalanceteDataSetDefinition.setName("patientDetails");
		patientBalanceteDataSetDefinition.setParameters(getParameters());
		rd.addDataSetDefinition("patientBalancete", Mapped.mapStraightThrough(patientBalanceteDataSetDefinition));
		
		return rd;
	}
	
	@Override
	public String getDescription() {
		
		return "Report Balance for ART Drugs, Regimens and Patients Enrolled to ART Program";
	}
	
	@Override
	public String getName() {
		
		return "MMIA Report";
	}
	
	@Override
	public String getUuid() {
		
		return "070708b2-2dcf-11e8-afc6-cfac93f9ecaa";
	}
	
	@Override
	public String getVersion() {
		return "0.1";
	}
	
	@Override
	public String getExcelDesignUuid() {
		return "e6d1b8b2-2dce-11e8-9c34-6fe694d670c1";
	}
	
	@Override
	public ReportDesign buildReportDesign(ReportDefinition reportDefinition) {
		
		ReportDesign rd = createExcelTemplateDesign(getExcelDesignUuid(), reportDefinition, "MmiaReport.xls");
		rd.setName("MMIA");
		Properties props = new Properties();
		props.put("repeatingSections",
		    "sheet:1,row:7,dataSet:drugBalancete|sheet:2,row:7,dataSet:regimenBalancete|sheet:3,row:8-10,dataSet:patientBalancete");
		props.put("sortWeigth", "5000");
		rd.setProperties(props);
		
		return rd;
	}
	
	@Override
	public List<Parameter> getParameters() {
		
		List<Parameter> pList = new ArrayList<Parameter>();
		pList.add(new Parameter("startDate", "Start Date", Date.class));
		pList.add(new Parameter("endDate", "End Date", Date.class));
		
		return pList;
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		
		List<ReportDesign> reportDesigns = new ArrayList<ReportDesign>();
		
		reportDesigns.add(buildReportDesign(reportDefinition));
		
		return reportDesigns;
	}
	
}
