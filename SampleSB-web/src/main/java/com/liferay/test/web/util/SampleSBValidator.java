
package com.liferay.test.web.util;

import com.google.common.collect.Lists;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * Validator
 *
 * @author yasuflatland
 */
public class SampleSBValidator {

	public static List<String> validateSampleSB(ActionRequest request) throws IOException {

		List<String> errors = Lists.newArrayList();

		// Field title

		if (!validateTitle(ParamUtil.getString(request, "title"))) {
			errors.add("error : title");
		}
		if (Validator.isNull(ParamUtil.getString(request, "title"))) {
			errors.add("samplesb-title-required");
		}		

		// Field startDate

		if (!validateStartDate(ParamUtil.getString(request, "startDate"))) {
			errors.add("error : startDate");
		}

		// Field endDate

		if (!validateEndDate(ParamUtil.getString(request, "endDate"))) {
			errors.add("error : endDate");
		}

		// Field samplesbBooleanStat

		if (!validateSamplesbBooleanStat(ParamUtil.getString(request, "samplesbBooleanStat"))) {

			errors.add("error : samplesbBooleanStat");
		}

		// Field samplesbDateTime

		if (!validateSamplesbDateTime(ParamUtil.getString(request, "samplesbDateTime"))) {

			errors.add("error : samplesbDateTime");
		}

		// Field samplesbDocumentLibrary
		
		if (!validateSamplesbDocumentLibrary(ParamUtil.getString(request, "samplesbDocumentLibrary"))) {

			errors.add("error : samplesbDocumentLibrary");
		}

		// Field samplesbDouble

		if (!validateSamplesbDouble(ParamUtil.getString(request, "samplesbDouble"))) {

			errors.add("error_number_format : samplesbDouble");
		}

		// Field samplesbInteger

		if (!validateSamplesbInteger(ParamUtil.getString(request, "samplesbInteger"))) {

			errors.add("error_number_format : samplesbInteger");
		}

		// Field samplesbRichText

		if (!validateSamplesbRichText(ParamUtil.getString(request, "samplesbRichText"))) {

			errors.add("error : samplesbRichText");
		}

		// Field samplesbText

		if (!validateSamplesbText(ParamUtil.getString(request, "samplesbText"))) {

			errors.add("error : samplesbText");
		}

		return errors;
	}

	/**
	 * Field endDate Validation
	 *
	 * @param field
	 *            endDate
	 * @return boolean
	 */
	private static boolean validateEndDate(String field) {
		boolean valid = true;

		return valid;
	}

	/**
	 * Field samplesbBooleanStat Validation
	 *
	 * @param field
	 *            samplesbBooleanStat
	 * @return boolean
	 */
	private static boolean validateSamplesbBooleanStat(String field) {
		boolean valid = true;

		return valid;
	}

	/**
	 * Field samplesbDateTime Validation
	 *
	 * @param field
	 *            samplesbDateTime
	 * @return boolean
	 */
	private static boolean validateSamplesbDateTime(String field) {
		boolean valid = true;

		return valid;
	}

	/**
	 * Field samplesbDocumentLibrary Validation
	 *
	 * @param field
	 *            samplesbDocumentLibrary
	 * @return boolean
	 */
	private static boolean validateSamplesbDocumentLibrary(String field) {
		boolean valid = true;

		return valid;
	}

	/**
	 * Field samplesbDouble Validation
	 *
	 * @param field
	 *            samplesbDouble
	 * @return boolean
	 */
	private static boolean validateSamplesbDouble(String field) {
		boolean valid = true;
		try {
			Double.parseDouble(field);
		} catch (NumberFormatException nfe) {
			valid = false;
		}

		return valid;
	}

	/**
	 * Field samplesbId Validation
	 *
	 * @param field
	 *            samplesbId
	 * @return boolean
	 */
	private static boolean validateSamplesbId(String field) {
		boolean valid = true;
		try {
			Double.parseDouble(field);
		} catch (NumberFormatException nfe) {
			valid = false;
		}

		return valid;
	}

	/**
	 * Field samplesbInteger Validation
	 *
	 * @param field
	 *            samplesbInteger
	 * @return boolean
	 */
	private static boolean validateSamplesbInteger(String field) {
		boolean valid = true;
		try {
			Double.parseDouble(field);
		} catch (NumberFormatException nfe) {
			valid = false;
		}

		return valid;
	}

	/**
	 * Field samplesbRichText Validation
	 *
	 * @param field
	 *            samplesbRichText
	 * @return boolean
	 */
	private static boolean validateSamplesbRichText(String field) {
		boolean valid = true;

		return valid;
	}

	/**
	 * Field samplesbText Validation
	 *
	 * @param field
	 *            samplesbText
	 * @return boolean
	 */
	private static boolean validateSamplesbText(String field) {
		boolean valid = true;

		return valid;
	}

	/**
	 * Field startDate Validation
	 *
	 * @param field
	 *            startDate
	 * @return boolean
	 */
	private static boolean validateStartDate(String field) {
		boolean valid = true;

		return valid;
	}

	/**
	 * Field title Validation
	 *
	 * @param field
	 *            title
	 * @return boolean
	 */
	private static boolean validateTitle(String field) {

		return StringUtils.isNotEmpty(field);
	}

	/**
	 * Validate Preference
	 * 
	 * @param dateFormat
	 *            Date Format
	 * @param datetimeFormat
	 *            Date Time Format
	 * @param errors
	 * @return boolean
	 */
	public static boolean validateEditSampleSB(String dateFormat, String datetimeFormat, List<String> errors) {
		boolean valid = true;

		if (Validator.isNull(dateFormat)) {
			errors.add("date-format-required");
			valid = false;
		} else if (Validator.isNull(datetimeFormat)) {
			errors.add("datetime-format.required");
			valid = false;
		}
		return valid;
	}
}