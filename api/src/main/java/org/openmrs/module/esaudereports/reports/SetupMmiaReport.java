package org.openmrs.module.esaudereports.reports;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openmrs.module.esaudereports.EsaudeDataExportManager;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
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
		rd.addParameters(getParameters());
		rd.addDataSetDefinition("mmia", Mapped.mapStraightThrough(mmiaDataSetDefinition()));
		rd.addDataSetDefinition("mmia2", Mapped.mapStraightThrough(mmia2DataSetDefinition()));
		return rd;
	}
	
	@Override
	public String getDescription() {
		
		return "Test creating MMIA Report";
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
		props.put("repeatingSections", "sheet:1,row:10,dataSet:mmia");
		props.put("sortWeigth", "5000");
		rd.setProperties(props);
		
		return rd;
	}
	
	private DataSetDefinition mmiaDataSetDefinition() {
		SqlDataSetDefinition patientDataSetDefinition = new SqlDataSetDefinition();
		patientDataSetDefinition.setName("mmia");
		patientDataSetDefinition.setSqlQuery(MmiaSqlSource.SQL_FIND_BALANCETE_BY_PERIOD);
		
		patientDataSetDefinition.addParameter(new Parameter("startDate", "", Date.class));
		patientDataSetDefinition.addParameter(new Parameter("endDate", "", Date.class));
		
		return patientDataSetDefinition;
	}
	
	private DataSetDefinition mmia2DataSetDefinition() {
		SqlDataSetDefinition patientDataSetDefinition = new SqlDataSetDefinition();
		patientDataSetDefinition.setName("mmia2");
		patientDataSetDefinition.setSqlQuery("SELECT name as 'DROGA' from drug where name like'%DD%'");
		patientDataSetDefinition.addParameter(new Parameter("startDate", "", Date.class));
		patientDataSetDefinition.addParameter(new Parameter("endDate", "", Date.class));
		
		return patientDataSetDefinition;
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
