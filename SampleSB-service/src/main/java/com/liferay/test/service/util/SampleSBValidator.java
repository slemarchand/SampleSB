package com.liferay.test.service.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.ModelValidator;
import com.liferay.test.exception.SampleSBValidateException;
import com.liferay.test.model.SampleSB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SampleSBValidator
	implements ModelValidator<SampleSB> {

	protected List<String> _errors = new ArrayList<>();

	/**
	 * Field title Validation
	 *
	 * @param field title
	 * @return boolean
	 */
	protected void validateTitle(String field) {
		if (!StringUtils.isNotEmpty(field)) {
			_errors.add("samplesb-title-required");
		}
	}

	/**
	 * Field startDate Validation
	 *
	 * @param field startDate
	 * @return boolean
	 */
	protected void validateStartDate(Date field) {
	}

	protected void validateSamplesbText(String field) {

	}

	protected void validateSamplesbRichText(String field) {

	}

	protected void validateSamplesbInteger(int field) {

	}

	protected void validateSamplesbDouble(double field) {

	}

	protected void validateSamplesbDocumentLibrary(String field) {

	}

	protected void validateSamplesbDateTime(Date field) {

	}

	protected void validateSamplesbBooleanStat(boolean field) {

	}

	protected void validateEndDate(Date field) {

	}

	@Override
	public void validate(SampleSB entry) throws PortalException {

		// Field title

		validateTitle(entry.getTitle());

		// Field startDate

		validateStartDate(entry.getStartDate());

		// Field endDate

		validateEndDate(entry.getEndDate());

		// Field samplesbBooleanStat

		validateSamplesbBooleanStat(entry.getSamplesbBooleanStat());

		// Field samplesbDateTime

		validateSamplesbDateTime(entry.getSamplesbDateTime());

		// Field samplesbDocumentLibrary

		validateSamplesbDocumentLibrary(entry.getSamplesbDocumentLibrary());

		// Field samplesbDouble

		validateSamplesbDouble(entry.getSamplesbDouble());

		// Field samplesbInteger

		validateSamplesbInteger(entry.getSamplesbInteger());

		// Field samplesbRichText

		validateSamplesbRichText(entry.getSamplesbRichText());

		// Field samplesbText

		validateSamplesbText(entry.getSamplesbText());

		if (0 < _errors.size()) {
			throw new SampleSBValidateException(_errors);
		}
	}

}
