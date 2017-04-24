package com.liferay.test.web.portlet.action;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalServiceUtil;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Yasuyuki Takeo
 */
public class ActionUtil {

    /**
     * Get Record
     *
     * @param primaryKey Primary key
     * @param idReset    true to reset primary key to 0
     * @return SampleSB object
     * @throws Exception
     */
    public static SampleSB getRecord(long primaryKey, boolean idReset) throws Exception {

        SampleSB record = null;

        if (primaryKey > 0) {
            //A recode has existed
            record = SampleSBLocalServiceUtil.getSampleSB(primaryKey);
        } else {
            //A recode doesn't exist
            primaryKey = CounterLocalServiceUtil.increment();
            record = SampleSBLocalServiceUtil.createSampleSB(primaryKey);
            if (true == idReset) {
                //Reset primary key
                record.setPrimaryKey(0);
            }
        }

        return record;
    }

    /**
     * Get Record
     *
     * @param request HttpServletRequest
     * @param idReset true to reset primary key to 0
     * @return SampleSB object
     * @throws Exception
     */
    public static SampleSB getRecord(HttpServletRequest request, boolean idReset) throws Exception {

        long primaryKey = ParamUtil.getLong(request, "samplesbId");

        SampleSB record = getRecord(primaryKey, idReset);

        request.setAttribute("sampleSB", record);

        return record;
    }

    /**
     * Get Record
     *
     * @param portletRequest PortletRequest
     * @param idReset        true to reset primary key to 0
     * @return SampleSB object
     * @throws Exception
     */
    public static SampleSB getRecord(PortletRequest portletRequest, boolean idReset)
        throws Exception {

        HttpServletRequest request = PortalUtil.getHttpServletRequest(
            portletRequest);

        return getRecord(request, idReset);
    }

}
