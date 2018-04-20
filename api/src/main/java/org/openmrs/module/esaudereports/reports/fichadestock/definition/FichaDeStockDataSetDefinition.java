package org.openmrs.module.esaudereports.reports.fichadestock.definition;

import org.openmrs.Drug;
import org.openmrs.module.reporting.dataset.definition.BaseDataSetDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;

public class FichaDeStockDataSetDefinition extends BaseDataSetDefinition {
	
	public FichaDeStockDataSetDefinition() {
		super();
	}
	
	@ConfigurationProperty
	Drug drug;
	
	public Drug getDrug() {
		return drug;
	}
	
	public void setDrug(Drug drug) {
		this.drug = drug;
	}
	
}
