package org.openmrs.module.esaudereports.reporting.data.converter;

import org.openmrs.Drug;
import org.openmrs.module.reporting.data.converter.DataConverter;

public class DrugConverter implements DataConverter {
	
	@Override
	public Object convert(Object original) {
		
		Drug object = (Drug) original;
		return object;
	}
	
	@Override
	public Class<?> getInputDataType() {
		return Drug.class;
	}
	
	@Override
	public Class<?> getDataType() {
		return Drug.class;
	}
	
}
