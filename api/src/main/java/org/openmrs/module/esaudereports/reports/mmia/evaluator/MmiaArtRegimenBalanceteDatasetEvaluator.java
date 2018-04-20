package org.openmrs.module.esaudereports.reports.mmia.evaluator;

import java.util.List;

import org.openmrs.annotation.Handler;
import org.openmrs.module.esaudereports.reports.mmia.definition.MmiaArtRegimenBalanceteDataSetDefinition;
import org.openmrs.module.esaudereports.reports.mmia.util.MmiaSqlSource;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.MapDataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.evaluator.DataSetEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

@Handler(supports = MmiaArtRegimenBalanceteDataSetDefinition.class)
public class MmiaArtRegimenBalanceteDatasetEvaluator implements DataSetEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	@Override
	public DataSet evaluate(DataSetDefinition dataSetDefinition, EvaluationContext evalContext) throws EvaluationException {
		
		MapDataSet dataSet = new MapDataSet(dataSetDefinition, evalContext);
		
		SqlQueryBuilder query = new SqlQueryBuilder(MmiaSqlSource.SQL_FIND_ART_REGIMENS);
		List<Object[]> results = evaluationService.evaluateToList(query, evalContext);
		
		for (Object[] o : results) {
			
			DataSetRow row = new DataSetRow();
			row.addColumnValue(new DataSetColumn("REGIMEN", "REGIMEN", String.class), String.valueOf(o[0]));
			row.addColumnValue(new DataSetColumn("NUM_PATIENTS", "NUM_PATIENTS", String.class), String.valueOf(o[1]));
			dataSet.addRow(row);
		}
		return dataSet;
	}
}
