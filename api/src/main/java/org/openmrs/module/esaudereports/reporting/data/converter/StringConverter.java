package org.openmrs.module.esaudereports.reporting.data.converter;

import org.openmrs.module.reporting.data.converter.DataConverter;

public class StringConverter implements DataConverter {
	
	@Override
	public Object convert(Object original) {
		
		return original.toString();
	}
	
	@Override
	public Class<?> getInputDataType() {
		return String.class;
	}
	
	@Override
	public Class<?> getDataType() {
		return String.class;
	}
	
}
