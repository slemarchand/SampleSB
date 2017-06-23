package com.liferay.test.web.social;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.test.constants.SampleSBPortletKeys;
import com.liferay.test.model.SampleSB;
import com.liferay.test.service.SampleSBLocalService;
import com.liferay.test.service.permission.SampleSBPermissionChecker;
import com.liferay.test.social.SampleSBActivityKeys;

import java.text.Format;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(
	property = {
		"javax.portlet.name=" + SampleSBPortletKeys.SAMPLESB 
	}, 
	service = SocialActivityInterpreter.class
)
public class SampleSBActivityInterpreter extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getPath(SocialActivity activity, ServiceContext serviceContext) {

		return SampleSBPortletKeys.SAMPLESB_FIND_ENTRY + "?resourcePrimKey=" + activity.getClassPK();
	}

	@Override
	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
	}

	@Override
	protected Object[] getTitleArguments(String groupName, SocialActivity activity, String link, String title,
			ServiceContext serviceContext) throws Exception {

		String creatorUserName = getUserName(activity.getUserId(), serviceContext);
		String receiverUserName = getUserName(activity.getReceiverUserId(), serviceContext);

		SampleSB entry = _sampleSBLocalService.getSampleSB(activity.getClassPK());

		String displayDate = StringPool.BLANK;

		if ((activity.getType() == SampleSBActivityKeys.ADD_SAMPLESB)
				&& (entry.getStatus() == WorkflowConstants.STATUS_SCHEDULED)) {

			link = null;

			Format dateFormatDate = FastDateFormatFactoryUtil.getSimpleDateFormat("MMMM d", serviceContext.getLocale(),
					serviceContext.getTimeZone());

			displayDate = dateFormatDate.format(entry.getModifiedDate());
		}

		return new Object[] { groupName, creatorUserName, receiverUserName, wrapLink(link, title), displayDate };
	}

	@Override
	protected String getTitlePattern(String groupName, SocialActivity activity) throws Exception {

		int activityType = activity.getType();

		if (activityType == SampleSBActivityKeys.ADD_SAMPLESB) {
			SampleSB entry = _sampleSBLocalService.getSampleSB(activity.getClassPK());

			if (entry.getStatus() == WorkflowConstants.STATUS_SCHEDULED) {
				if (Validator.isNull(groupName)) {
					return "activity-samplesb-schedule-entry";
				} else {
					return "activity-samplesb-schedule-entry-in";
				}
			} else {
				if (Validator.isNull(groupName)) {
					return "activity-samplesb-add-entry";
				} else {
					return "activity-samplesb-add-entry-in";
				}
			}
		} else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-samplesb-move-to-trash";
			} else {
				return "activity-samplesb-move-to-trash-in";
			}
		} else if (activityType == SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-samplesb-restore-from-trash";
			} else {
				return "activity-samplesb-restore-from-trash-in";
			}
		} else if (activityType == SampleSBActivityKeys.UPDATE_SAMPLESB) {
			if (Validator.isNull(groupName)) {
				return "activity-samplesb-update-entry";
			} else {
				return "activity-samplesb-update-entry-in";
			}
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		return SampleSBPermissionChecker.contains(
			permissionChecker, activity.getClassPK(), actionId);
	}
	
	@Reference(
		target = "(bundle.symbolic.name=com.liferay.test.web)", 
		unbind = "-"
	)
	protected void setResourceBundleLoader(ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(resourceBundleLoader,
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	@Reference(unbind = "-")
	protected void setSampleSBLocalService(SampleSBLocalService samplesblocalservice) {
		_sampleSBLocalService = samplesblocalservice;
	}

	private SampleSBLocalService _sampleSBLocalService;
	private ResourceBundleLoader _resourceBundleLoader;
	private static final String[] _CLASS_NAMES = { SampleSB.class.getName() };

}
