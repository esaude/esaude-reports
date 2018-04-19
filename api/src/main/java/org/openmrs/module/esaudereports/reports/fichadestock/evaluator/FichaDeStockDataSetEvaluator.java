package org.openmrs.module.esaudereports.reports.fichadestock.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.esaudereports.reports.fichadestock.definition.FichaDeStockDataSetDefinition;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.MapDataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.evaluator.DataSetEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

@Handler(supports = FichaDeStockDataSetDefinition.class)
public class FichaDeStockDataSetEvaluator implements DataSetEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	@Override
	public DataSet evaluate(DataSetDefinition dataSetDefinition, EvaluationContext evalContext) throws EvaluationException {
		
		MapDataSet dataSet = new MapDataSet(dataSetDefinition, evalContext);
		
		FichaDeStockDataSetDefinition definition = (FichaDeStockDataSetDefinition) dataSetDefinition;
		
		DataSetRow row = new DataSetRow();
		
		row.addColumnValue(new DataSetColumn("DRUG_NAME", "DRUG_NAME", String.class), definition.getDrug().getName());
		
		row.addColumnValue(new DataSetColumn("LOCATION_NAME", "LOCATION_NAME", String.class), Context.getLocationService()
		        .getDefaultLocation().getName());
		
		dataSet.addRow(row);
		
		return dataSet;
	}
	
}
