package org.openmrs.module.esaudereports.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openmrs.Drug;
import org.openmrs.module.esaudereports.EsaudeDataExportManager;
import org.openmrs.module.esaudereports.reports.fichadestock.definition.FichaDeStockDataSetDefinition;
import org.openmrs.module.esaudereports.reports.fichadestock.definition.FichaDeStockDrugMovementDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

@Component
public class SetupFichaDeStockReport extends EsaudeDataExportManager {
	
	@Override
	public String getUuid() {
		return "62dfb1d4-43a0-11e8-a063-574ef02fa66f";
	}
	
	@Override
	public String getName() {
		return "FICHA DE STOCK";
	}
	
	@Override
	public String getDescription() {
		return "Show the Details of the movement for certain medicine";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		
		ReportDefinition reportDefinition = new ReportDefinition();
		reportDefinition.setUuid(getUuid());
		reportDefinition.setName(getName());
		reportDefinition.setDescription(getDescription());
		reportDefinition.setParameters(getParameters());
		
		FichaDeStockDataSetDefinition fichaDeStockDataSetDefinition = new FichaDeStockDataSetDefinition();
		fichaDeStockDataSetDefinition.setName("fichaDeStock");
		fichaDeStockDataSetDefinition.setParameters(getParameters());
		reportDefinition.addDataSetDefinition("fichaDeStock", Mapped.mapStraightThrough(fichaDeStockDataSetDefinition));
		
		FichaDeStockDrugMovementDataSetDefinition drugMovementDataSetDefinition = new FichaDeStockDrugMovementDataSetDefinition();
		drugMovementDataSetDefinition.setName("drugMovements");
		drugMovementDataSetDefinition.setParameters(getParameters());
		reportDefinition.addDataSetDefinition("drugDetails", Mapped.mapStraightThrough(drugMovementDataSetDefinition));
		
		return reportDefinition;
	}
	
	@Override
	public String getVersion() {
		
		return "0.1";
	}
	
	@Override
	public String getExcelDesignUuid() {
		
		return "a7002790-43a0-11e8-a8f4-df673cb13877";
	}
	
	@Override
	public ReportDesign buildReportDesign(ReportDefinition reportDefinition) {
		
		ReportDesign rd = createExcelTemplateDesign(getExcelDesignUuid(), reportDefinition, "FichaDeStockReport.xls");
		rd.setName("FICHA DE STOCK");
		Properties props = new Properties();
		props.put("repeatingSections", "sheet:1,row:9,dataSet:drugDetails");
		props.put("sortWeigth", "5000");
		rd.setProperties(props);
		
		return rd;
	}
	
	@Override
	public List<Parameter> getParameters() {
		
		List<Parameter> pList = new ArrayList<Parameter>();
		pList.add(new Parameter("drug", "Drug Name", Drug.class));
		
		return pList;
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		List<ReportDesign> reportDesigns = new ArrayList<ReportDesign>();
		reportDesigns.add(buildReportDesign(reportDefinition));
		return reportDesigns;
	}
}
