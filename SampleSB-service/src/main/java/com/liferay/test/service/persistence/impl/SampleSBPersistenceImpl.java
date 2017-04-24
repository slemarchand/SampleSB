/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.test.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.test.exception.NoSuchSampleSBException;
import com.liferay.test.model.SampleSB;
import com.liferay.test.model.impl.SampleSBImpl;
import com.liferay.test.model.impl.SampleSBModelImpl;
import com.liferay.test.service.persistence.SampleSBPersistence;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the sample sb service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SampleSBPersistence
 * @see com.liferay.test.service.persistence.SampleSBUtil
 * @generated
 */
@ProviderType
public class SampleSBPersistenceImpl extends BasePersistenceImpl<SampleSB>
	implements SampleSBPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SampleSBUtil} to access the sample sb persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SampleSBImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			SampleSBModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the sample sbs where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUuid(String uuid, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUuid(String uuid, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if (!Objects.equals(uuid, sampleSB.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByUuid_First(String uuid,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByUuid_First(uuid, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByUuid_First(String uuid,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByUuid_Last(String uuid,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByUuid_Last(uuid, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByUuid_Last(String uuid,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where uuid = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByUuid_PrevAndNext(long samplesbId, String uuid,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByUuid_PrevAndNext(session, sampleSB, uuid,
					orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByUuid_PrevAndNext(session, sampleSB, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByUuid_PrevAndNext(Session session,
		SampleSB sampleSB, String uuid,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SampleSB sampleSB : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "sampleSB.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "sampleSB.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(sampleSB.uuid IS NULL OR sampleSB.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			SampleSBModelImpl.UUID_COLUMN_BITMASK |
			SampleSBModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the sample sb where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchSampleSBException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByUUID_G(String uuid, long groupId)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByUUID_G(uuid, groupId);

		if (sampleSB == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSampleSBException(msg.toString());
		}

		return sampleSB;
	}

	/**
	 * Returns the sample sb where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the sample sb where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof SampleSB) {
			SampleSB sampleSB = (SampleSB)result;

			if (!Objects.equals(uuid, sampleSB.getUuid()) ||
					(groupId != sampleSB.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<SampleSB> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					SampleSB sampleSB = list.get(0);

					result = sampleSB;

					cacheResult(sampleSB);

					if ((sampleSB.getUuid() == null) ||
							!sampleSB.getUuid().equals(uuid) ||
							(sampleSB.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, sampleSB);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (SampleSB)result;
		}
	}

	/**
	 * Removes the sample sb where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the sample sb that was removed
	 */
	@Override
	public SampleSB removeByUUID_G(String uuid, long groupId)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByUUID_G(uuid, groupId);

		return remove(sampleSB);
	}

	/**
	 * Returns the number of sample sbs where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "sampleSB.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "sampleSB.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(sampleSB.uuid IS NULL OR sampleSB.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "sampleSB.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			SampleSBModelImpl.UUID_COLUMN_BITMASK |
			SampleSBModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the sample sbs where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<SampleSB> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if (!Objects.equals(uuid, sampleSB.getUuid()) ||
							(companyId != sampleSB.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByUuid_C_PrevAndNext(long samplesbId, String uuid,
		long companyId, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, sampleSB, uuid,
					companyId, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByUuid_C_PrevAndNext(session, sampleSB, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByUuid_C_PrevAndNext(Session session,
		SampleSB sampleSB, String uuid, long companyId,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SampleSB sampleSB : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "sampleSB.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "sampleSB.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(sampleSB.uuid IS NULL OR sampleSB.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "sampleSB.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SampleSBModelImpl.COMPANYID_COLUMN_BITMASK |
			SampleSBModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the sample sbs where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByC_S(long companyId, int status) {
		return findByC_S(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByC_S(long companyId, int status, int start,
		int end) {
		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByC_S(long companyId, int status, int start,
		int end, OrderByComparator<SampleSB> orderByComparator) {
		return findByC_S(companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByC_S(long companyId, int status, int start,
		int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] { companyId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] {
					companyId, status,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((companyId != sampleSB.getCompanyId()) ||
							(status != sampleSB.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByC_S_First(long companyId, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByC_S_First(companyId, status,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByC_S_First(long companyId, int status,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByC_S(companyId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByC_S_Last(long companyId, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByC_S_Last(companyId, status, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByC_S_Last(long companyId, int status,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByC_S(companyId, status);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByC_S(companyId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByC_S_PrevAndNext(long samplesbId, long companyId,
		int status, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByC_S_PrevAndNext(session, sampleSB, companyId,
					status, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByC_S_PrevAndNext(session, sampleSB, companyId,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByC_S_PrevAndNext(Session session, SampleSB sampleSB,
		long companyId, int status,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long companyId, int status) {
		for (SampleSB sampleSB : findByC_S(companyId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByC_S(long companyId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_S;

		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "sampleSB.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_STATUS_2 = "sampleSB.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SampleSBModelImpl.GROUPID_COLUMN_BITMASK |
			SampleSBModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the sample sbs where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_S(long groupId, int status) {
		return findByG_S(groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the sample sbs where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_S(long groupId, int status, int start, int end) {
		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_S(long groupId, int status, int start,
		int end, OrderByComparator<SampleSB> orderByComparator) {
		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_S(long groupId, int status, int start,
		int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] { groupId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] {
					groupId, status,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((groupId != sampleSB.getGroupId()) ||
							(status != sampleSB.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByG_S_First(long groupId, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByG_S_First(groupId, status, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByG_S_First(long groupId, int status,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByG_S(groupId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByG_S_Last(long groupId, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByG_S_Last(groupId, status, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByG_S_Last(long groupId, int status,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByG_S(groupId, status);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByG_S(groupId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByG_S_PrevAndNext(long samplesbId, long groupId,
		int status, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByG_S_PrevAndNext(session, sampleSB, groupId, status,
					orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByG_S_PrevAndNext(session, sampleSB, groupId, status,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByG_S_PrevAndNext(Session session, SampleSB sampleSB,
		long groupId, int status,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		for (SampleSB sampleSB : findByG_S(groupId, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_S;

		Object[] finderArgs = new Object[] { groupId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "sampleSB.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STATUS_2 = "sampleSB.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_U_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SampleSBModelImpl.COMPANYID_COLUMN_BITMASK |
			SampleSBModelImpl.USERID_COLUMN_BITMASK |
			SampleSBModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the sample sbs where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByC_U_S(long companyId, long userId, int status) {
		return findByC_U_S(companyId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByC_U_S(long companyId, long userId, int status,
		int start, int end) {
		return findByC_U_S(companyId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByC_U_S(long companyId, long userId, int status,
		int start, int end, OrderByComparator<SampleSB> orderByComparator) {
		return findByC_U_S(companyId, userId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByC_U_S(long companyId, long userId, int status,
		int start, int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S;
			finderArgs = new Object[] { companyId, userId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_U_S;
			finderArgs = new Object[] {
					companyId, userId, status,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((companyId != sampleSB.getCompanyId()) ||
							(userId != sampleSB.getUserId()) ||
							(status != sampleSB.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_S_USERID_2);

			query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(status);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByC_U_S_First(long companyId, long userId, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByC_U_S_First(companyId, userId, status,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByC_U_S_First(long companyId, long userId, int status,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByC_U_S(companyId, userId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByC_U_S_Last(long companyId, long userId, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByC_U_S_Last(companyId, userId, status,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByC_U_S_Last(long companyId, long userId, int status,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByC_U_S(companyId, userId, status);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByC_U_S(companyId, userId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByC_U_S_PrevAndNext(long samplesbId, long companyId,
		long userId, int status, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByC_U_S_PrevAndNext(session, sampleSB, companyId,
					userId, status, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByC_U_S_PrevAndNext(session, sampleSB, companyId,
					userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByC_U_S_PrevAndNext(Session session,
		SampleSB sampleSB, long companyId, long userId, int status,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_U_S_USERID_2);

		query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where companyId = &#63; and userId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 */
	@Override
	public void removeByC_U_S(long companyId, long userId, int status) {
		for (SampleSB sampleSB : findByC_U_S(companyId, userId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByC_U_S(long companyId, long userId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_U_S;

		Object[] finderArgs = new Object[] { companyId, userId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_S_USERID_2);

			query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_U_S_COMPANYID_2 = "sampleSB.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_S_USERID_2 = "sampleSB.userId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_S_STATUS_2 = "sampleSB.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SampleSBModelImpl.GROUPID_COLUMN_BITMASK |
			SampleSBModelImpl.USERID_COLUMN_BITMASK |
			SampleSBModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the sample sbs where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_U_S(long groupId, long userId, int status) {
		return findByG_U_S(groupId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_U_S(long groupId, long userId, int status,
		int start, int end) {
		return findByG_U_S(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_U_S(long groupId, long userId, int status,
		int start, int end, OrderByComparator<SampleSB> orderByComparator) {
		return findByG_U_S(groupId, userId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_U_S(long groupId, long userId, int status,
		int start, int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S;
			finderArgs = new Object[] { groupId, userId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_S;
			finderArgs = new Object[] {
					groupId, userId, status,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((groupId != sampleSB.getGroupId()) ||
							(userId != sampleSB.getUserId()) ||
							(status != sampleSB.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByG_U_S_First(long groupId, long userId, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByG_U_S_First(groupId, userId, status,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByG_U_S_First(long groupId, long userId, int status,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByG_U_S(groupId, userId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByG_U_S_Last(long groupId, long userId, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByG_U_S_Last(groupId, userId, status,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByG_U_S_Last(long groupId, long userId, int status,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByG_U_S(groupId, userId, status);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByG_U_S(groupId, userId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByG_U_S_PrevAndNext(long samplesbId, long groupId,
		long userId, int status, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByG_U_S_PrevAndNext(session, sampleSB, groupId,
					userId, status, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByG_U_S_PrevAndNext(session, sampleSB, groupId,
					userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByG_U_S_PrevAndNext(Session session,
		SampleSB sampleSB, long groupId, long userId, int status,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where groupId = &#63; and userId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 */
	@Override
	public void removeByG_U_S(long groupId, long userId, int status) {
		for (SampleSB sampleSB : findByG_U_S(groupId, userId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByG_U_S(long groupId, long userId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_S;

		Object[] finderArgs = new Object[] { groupId, userId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_U_S_GROUPID_2 = "sampleSB.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_S_USERID_2 = "sampleSB.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_S_STATUS_2 = "sampleSB.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SampleSBModelImpl.USERID_COLUMN_BITMASK |
			SampleSBModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_S = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the sample sbs where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByU_S(long userId, int status) {
		return findByU_S(userId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the sample sbs where userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByU_S(long userId, int status, int start, int end) {
		return findByU_S(userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByU_S(long userId, int status, int start,
		int end, OrderByComparator<SampleSB> orderByComparator) {
		return findByU_S(userId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByU_S(long userId, int status, int start,
		int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S;
			finderArgs = new Object[] { userId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_S;
			finderArgs = new Object[] {
					userId, status,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((userId != sampleSB.getUserId()) ||
							(status != sampleSB.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_U_S_USERID_2);

			query.append(_FINDER_COLUMN_U_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByU_S_First(long userId, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByU_S_First(userId, status, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByU_S_First(long userId, int status,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByU_S(userId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByU_S_Last(long userId, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByU_S_Last(userId, status, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByU_S_Last(long userId, int status,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByU_S(userId, status);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByU_S(userId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where userId = &#63; and status = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByU_S_PrevAndNext(long samplesbId, long userId,
		int status, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByU_S_PrevAndNext(session, sampleSB, userId, status,
					orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByU_S_PrevAndNext(session, sampleSB, userId, status,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByU_S_PrevAndNext(Session session, SampleSB sampleSB,
		long userId, int status, OrderByComparator<SampleSB> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_U_S_USERID_2);

		query.append(_FINDER_COLUMN_U_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where userId = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param status the status
	 */
	@Override
	public void removeByU_S(long userId, int status) {
		for (SampleSB sampleSB : findByU_S(userId, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where userId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByU_S(long userId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_S;

		Object[] finderArgs = new Object[] { userId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_U_S_USERID_2);

			query.append(_FINDER_COLUMN_U_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_S_USERID_2 = "sampleSB.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_S_STATUS_2 = "sampleSB.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_UT_ST = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			SampleSBModelImpl.GROUPID_COLUMN_BITMASK |
			SampleSBModelImpl.URLTITLE_COLUMN_BITMASK |
			SampleSBModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT_ST = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UT_ST",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the sample sbs where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_UT_ST(long groupId, String urlTitle,
		int status) {
		return findByG_UT_ST(groupId, urlTitle, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_UT_ST(long groupId, String urlTitle,
		int status, int start, int end) {
		return findByG_UT_ST(groupId, urlTitle, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_UT_ST(long groupId, String urlTitle,
		int status, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findByG_UT_ST(groupId, urlTitle, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByG_UT_ST(long groupId, String urlTitle,
		int status, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST;
			finderArgs = new Object[] { groupId, urlTitle, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_UT_ST;
			finderArgs = new Object[] {
					groupId, urlTitle, status,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((groupId != sampleSB.getGroupId()) ||
							!Objects.equals(urlTitle, sampleSB.getUrlTitle()) ||
							(status != sampleSB.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
			}

			query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByG_UT_ST_First(long groupId, String urlTitle,
		int status, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByG_UT_ST_First(groupId, urlTitle, status,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", urlTitle=");
		msg.append(urlTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByG_UT_ST_First(long groupId, String urlTitle,
		int status, OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByG_UT_ST(groupId, urlTitle, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByG_UT_ST_Last(long groupId, String urlTitle,
		int status, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByG_UT_ST_Last(groupId, urlTitle, status,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", urlTitle=");
		msg.append(urlTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByG_UT_ST_Last(long groupId, String urlTitle,
		int status, OrderByComparator<SampleSB> orderByComparator) {
		int count = countByG_UT_ST(groupId, urlTitle, status);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByG_UT_ST(groupId, urlTitle, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByG_UT_ST_PrevAndNext(long samplesbId, long groupId,
		String urlTitle, int status,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByG_UT_ST_PrevAndNext(session, sampleSB, groupId,
					urlTitle, status, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByG_UT_ST_PrevAndNext(session, sampleSB, groupId,
					urlTitle, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByG_UT_ST_PrevAndNext(Session session,
		SampleSB sampleSB, long groupId, String urlTitle, int status,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

		boolean bindUrlTitle = false;

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
		}
		else if (urlTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
		}

		query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindUrlTitle) {
			qPos.add(urlTitle);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where groupId = &#63; and urlTitle = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 */
	@Override
	public void removeByG_UT_ST(long groupId, String urlTitle, int status) {
		for (SampleSB sampleSB : findByG_UT_ST(groupId, urlTitle, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByG_UT_ST(long groupId, String urlTitle, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_UT_ST;

		Object[] finderArgs = new Object[] { groupId, urlTitle, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_G_UT_ST_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_UT_ST_URLTITLE_2);
			}

			query.append(_FINDER_COLUMN_G_UT_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_UT_ST_GROUPID_2 = "sampleSB.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_1 = "sampleSB.urlTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_2 = "sampleSB.urlTitle = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_URLTITLE_3 = "(sampleSB.urlTitle IS NULL OR sampleSB.urlTitle = '') AND ";
	private static final String _FINDER_COLUMN_G_UT_ST_STATUS_2 = "sampleSB.status = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_UT = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_UT",
			new String[] { Long.class.getName(), String.class.getName() },
			SampleSBModelImpl.GROUPID_COLUMN_BITMASK |
			SampleSBModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the sample sb where groupId = &#63; and urlTitle = &#63; or throws a {@link NoSuchSampleSBException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByG_UT(long groupId, String urlTitle)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByG_UT(groupId, urlTitle);

		if (sampleSB == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSampleSBException(msg.toString());
		}

		return sampleSB;
	}

	/**
	 * Returns the sample sb where groupId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByG_UT(long groupId, String urlTitle) {
		return fetchByG_UT(groupId, urlTitle, true);
	}

	/**
	 * Returns the sample sb where groupId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByG_UT(long groupId, String urlTitle,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, urlTitle };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_UT,
					finderArgs, this);
		}

		if (result instanceof SampleSB) {
			SampleSB sampleSB = (SampleSB)result;

			if ((groupId != sampleSB.getGroupId()) ||
					!Objects.equals(urlTitle, sampleSB.getUrlTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				List<SampleSB> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_UT,
						finderArgs, list);
				}
				else {
					SampleSB sampleSB = list.get(0);

					result = sampleSB;

					cacheResult(sampleSB);

					if ((sampleSB.getGroupId() != groupId) ||
							(sampleSB.getUrlTitle() == null) ||
							!sampleSB.getUrlTitle().equals(urlTitle)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_UT,
							finderArgs, sampleSB);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_UT, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (SampleSB)result;
		}
	}

	/**
	 * Removes the sample sb where groupId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the sample sb that was removed
	 */
	@Override
	public SampleSB removeByG_UT(long groupId, String urlTitle)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByG_UT(groupId, urlTitle);

		return remove(sampleSB);
	}

	/**
	 * Returns the number of sample sbs where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByG_UT(long groupId, String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_UT;

		Object[] finderArgs = new Object[] { groupId, urlTitle };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_UT_GROUPID_2 = "sampleSB.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_1 = "sampleSB.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_2 = "sampleSB.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_3 = "(sampleSB.urlTitle IS NULL OR sampleSB.urlTitle = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_URLTITLE = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByURLTitle",
			new String[] { String.class.getName() },
			SampleSBModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_URLTITLE = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByURLTitle",
			new String[] { String.class.getName() });

	/**
	 * Returns the sample sb where urlTitle = &#63; or throws a {@link NoSuchSampleSBException} if it could not be found.
	 *
	 * @param urlTitle the url title
	 * @return the matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByURLTitle(String urlTitle)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByURLTitle(urlTitle);

		if (sampleSB == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("urlTitle=");
			msg.append(urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSampleSBException(msg.toString());
		}

		return sampleSB;
	}

	/**
	 * Returns the sample sb where urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param urlTitle the url title
	 * @return the matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByURLTitle(String urlTitle) {
		return fetchByURLTitle(urlTitle, true);
	}

	/**
	 * Returns the sample sb where urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param urlTitle the url title
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByURLTitle(String urlTitle, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { urlTitle };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_URLTITLE,
					finderArgs, this);
		}

		if (result instanceof SampleSB) {
			SampleSB sampleSB = (SampleSB)result;

			if (!Objects.equals(urlTitle, sampleSB.getUrlTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_URLTITLE_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_URLTITLE_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_URLTITLE_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				List<SampleSB> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_URLTITLE,
						finderArgs, list);
				}
				else {
					SampleSB sampleSB = list.get(0);

					result = sampleSB;

					cacheResult(sampleSB);

					if ((sampleSB.getUrlTitle() == null) ||
							!sampleSB.getUrlTitle().equals(urlTitle)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_URLTITLE,
							finderArgs, sampleSB);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_URLTITLE,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (SampleSB)result;
		}
	}

	/**
	 * Removes the sample sb where urlTitle = &#63; from the database.
	 *
	 * @param urlTitle the url title
	 * @return the sample sb that was removed
	 */
	@Override
	public SampleSB removeByURLTitle(String urlTitle)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByURLTitle(urlTitle);

		return remove(sampleSB);
	}

	/**
	 * Returns the number of sample sbs where urlTitle = &#63;.
	 *
	 * @param urlTitle the url title
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByURLTitle(String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_URLTITLE;

		Object[] finderArgs = new Object[] { urlTitle };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_URLTITLE_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_URLTITLE_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_URLTITLE_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_URLTITLE_URLTITLE_1 = "sampleSB.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_URLTITLE_URLTITLE_2 = "sampleSB.urlTitle = ?";
	private static final String _FINDER_COLUMN_URLTITLE_URLTITLE_3 = "(sampleSB.urlTitle IS NULL OR sampleSB.urlTitle = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SampleSBModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the sample sbs where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByGroupId(long groupId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByGroupId(long groupId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((groupId != sampleSB.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByGroupId_First(long groupId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByGroupId_First(groupId, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByGroupId_First(long groupId,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByGroupId_Last(long groupId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByGroupId_Last(groupId, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByGroupId_Last(long groupId,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where groupId = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByGroupId_PrevAndNext(long samplesbId, long groupId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, sampleSB, groupId,
					orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByGroupId_PrevAndNext(session, sampleSB, groupId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByGroupId_PrevAndNext(Session session,
		SampleSB sampleSB, long groupId,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SampleSB sampleSB : findByGroupId(groupId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "sampleSB.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERIDGROUPID =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserIdGroupId",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserIdGroupId",
			new String[] { Long.class.getName(), Long.class.getName() },
			SampleSBModelImpl.USERID_COLUMN_BITMASK |
			SampleSBModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERIDGROUPID = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserIdGroupId",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the sample sbs where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUserIdGroupId(long userId, long groupId) {
		return findByUserIdGroupId(userId, groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUserIdGroupId(long userId, long groupId,
		int start, int end) {
		return findByUserIdGroupId(userId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUserIdGroupId(long userId, long groupId,
		int start, int end, OrderByComparator<SampleSB> orderByComparator) {
		return findByUserIdGroupId(userId, groupId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUserIdGroupId(long userId, long groupId,
		int start, int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID;
			finderArgs = new Object[] { userId, groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERIDGROUPID;
			finderArgs = new Object[] {
					userId, groupId,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((userId != sampleSB.getUserId()) ||
							(groupId != sampleSB.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_USERIDGROUPID_USERID_2);

			query.append(_FINDER_COLUMN_USERIDGROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByUserIdGroupId_First(long userId, long groupId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByUserIdGroupId_First(userId, groupId,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByUserIdGroupId_First(long userId, long groupId,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByUserIdGroupId(userId, groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByUserIdGroupId_Last(long userId, long groupId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByUserIdGroupId_Last(userId, groupId,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByUserIdGroupId_Last(long userId, long groupId,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByUserIdGroupId(userId, groupId);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByUserIdGroupId(userId, groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByUserIdGroupId_PrevAndNext(long samplesbId,
		long userId, long groupId, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByUserIdGroupId_PrevAndNext(session, sampleSB,
					userId, groupId, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByUserIdGroupId_PrevAndNext(session, sampleSB,
					userId, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByUserIdGroupId_PrevAndNext(Session session,
		SampleSB sampleSB, long userId, long groupId,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_USERIDGROUPID_USERID_2);

		query.append(_FINDER_COLUMN_USERIDGROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where userId = &#63; and groupId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUserIdGroupId(long userId, long groupId) {
		for (SampleSB sampleSB : findByUserIdGroupId(userId, groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByUserIdGroupId(long userId, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERIDGROUPID;

		Object[] finderArgs = new Object[] { userId, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_USERIDGROUPID_USERID_2);

			query.append(_FINDER_COLUMN_USERIDGROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERIDGROUPID_USERID_2 = "sampleSB.userId = ? AND ";
	private static final String _FINDER_COLUMN_USERIDGROUPID_GROUPID_2 = "sampleSB.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			SampleSBModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the sample sbs where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUserId(long userId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByUserId(long userId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((userId != sampleSB.getUserId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByUserId_First(long userId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByUserId_First(userId, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByUserId_First(long userId,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByUserId_Last(long userId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByUserId_Last(userId, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByUserId_Last(long userId,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where userId = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByUserId_PrevAndNext(long samplesbId, long userId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByUserId_PrevAndNext(session, sampleSB, userId,
					orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByUserId_PrevAndNext(session, sampleSB, userId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByUserId_PrevAndNext(Session session,
		SampleSB sampleSB, long userId,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (SampleSB sampleSB : findByUserId(userId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "sampleSB.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			SampleSBModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the sample sbs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the sample sbs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((companyId != sampleSB.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByCompanyId_First(long companyId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByCompanyId_First(companyId, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByCompanyId_First(long companyId,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByCompanyId_Last(long companyId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByCompanyId_Last(companyId, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByCompanyId_Last(long companyId,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where companyId = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByCompanyId_PrevAndNext(long samplesbId,
		long companyId, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, sampleSB, companyId,
					orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByCompanyId_PrevAndNext(session, sampleSB, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByCompanyId_PrevAndNext(Session session,
		SampleSB sampleSB, long companyId,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SampleSB sampleSB : findByCompanyId(companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "sampleSB.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBID =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySamplesbId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBID =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySamplesbId",
			new String[] { Long.class.getName() },
			SampleSBModelImpl.SAMPLESBID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SAMPLESBID = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySamplesbId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the sample sbs where samplesbId = &#63;.
	 *
	 * @param samplesbId the samplesb ID
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbId(long samplesbId) {
		return findBySamplesbId(samplesbId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where samplesbId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbId the samplesb ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbId(long samplesbId, int start, int end) {
		return findBySamplesbId(samplesbId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbId the samplesb ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbId(long samplesbId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findBySamplesbId(samplesbId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbId the samplesb ID
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbId(long samplesbId, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBID;
			finderArgs = new Object[] { samplesbId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBID;
			finderArgs = new Object[] { samplesbId, start, end, orderByComparator };
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((samplesbId != sampleSB.getSamplesbId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_SAMPLESBID_SAMPLESBID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(samplesbId);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbId = &#63;.
	 *
	 * @param samplesbId the samplesb ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbId_First(long samplesbId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbId_First(samplesbId,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbId=");
		msg.append(samplesbId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbId = &#63;.
	 *
	 * @param samplesbId the samplesb ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbId_First(long samplesbId,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findBySamplesbId(samplesbId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbId = &#63;.
	 *
	 * @param samplesbId the samplesb ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbId_Last(long samplesbId,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbId_Last(samplesbId, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbId=");
		msg.append(samplesbId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbId = &#63;.
	 *
	 * @param samplesbId the samplesb ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbId_Last(long samplesbId,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countBySamplesbId(samplesbId);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findBySamplesbId(samplesbId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the sample sbs where samplesbId = &#63; from the database.
	 *
	 * @param samplesbId the samplesb ID
	 */
	@Override
	public void removeBySamplesbId(long samplesbId) {
		for (SampleSB sampleSB : findBySamplesbId(samplesbId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where samplesbId = &#63;.
	 *
	 * @param samplesbId the samplesb ID
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countBySamplesbId(long samplesbId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SAMPLESBID;

		Object[] finderArgs = new Object[] { samplesbId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_SAMPLESBID_SAMPLESBID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(samplesbId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SAMPLESBID_SAMPLESBID_2 = "sampleSB.samplesbId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TITLE = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTitle",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TITLE = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTitle",
			new String[] { String.class.getName() },
			SampleSBModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TITLE = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTitle",
			new String[] { String.class.getName() });

	/**
	 * Returns all the sample sbs where title = &#63;.
	 *
	 * @param title the title
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByTitle(String title) {
		return findByTitle(title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where title = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param title the title
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByTitle(String title, int start, int end) {
		return findByTitle(title, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where title = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param title the title
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByTitle(String title, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findByTitle(title, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where title = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param title the title
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByTitle(String title, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TITLE;
			finderArgs = new Object[] { title };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TITLE;
			finderArgs = new Object[] { title, start, end, orderByComparator };
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if (!Objects.equals(title, sampleSB.getTitle())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_TITLE_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TITLE_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_TITLE_TITLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindTitle) {
					qPos.add(title);
				}

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where title = &#63;.
	 *
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByTitle_First(String title,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByTitle_First(title, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("title=");
		msg.append(title);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where title = &#63;.
	 *
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByTitle_First(String title,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByTitle(title, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where title = &#63;.
	 *
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByTitle_Last(String title,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByTitle_Last(title, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("title=");
		msg.append(title);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where title = &#63;.
	 *
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByTitle_Last(String title,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByTitle(title);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByTitle(title, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where title = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByTitle_PrevAndNext(long samplesbId, String title,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByTitle_PrevAndNext(session, sampleSB, title,
					orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByTitle_PrevAndNext(session, sampleSB, title,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByTitle_PrevAndNext(Session session,
		SampleSB sampleSB, String title,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		boolean bindTitle = false;

		if (title == null) {
			query.append(_FINDER_COLUMN_TITLE_TITLE_1);
		}
		else if (title.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_TITLE_TITLE_3);
		}
		else {
			bindTitle = true;

			query.append(_FINDER_COLUMN_TITLE_TITLE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindTitle) {
			qPos.add(title);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where title = &#63; from the database.
	 *
	 * @param title the title
	 */
	@Override
	public void removeByTitle(String title) {
		for (SampleSB sampleSB : findByTitle(title, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where title = &#63;.
	 *
	 * @param title the title
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByTitle(String title) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TITLE;

		Object[] finderArgs = new Object[] { title };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_TITLE_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_TITLE_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_TITLE_TITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindTitle) {
					qPos.add(title);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_TITLE_TITLE_1 = "sampleSB.title IS NULL";
	private static final String _FINDER_COLUMN_TITLE_TITLE_2 = "sampleSB.title = ?";
	private static final String _FINDER_COLUMN_TITLE_TITLE_3 = "(sampleSB.title IS NULL OR sampleSB.title = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STARTDATE =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStartDate",
			new String[] {
				Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STARTDATE =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStartDate",
			new String[] { Date.class.getName() },
			SampleSBModelImpl.STARTDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STARTDATE = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStartDate",
			new String[] { Date.class.getName() });

	/**
	 * Returns all the sample sbs where startDate = &#63;.
	 *
	 * @param startDate the start date
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByStartDate(Date startDate) {
		return findByStartDate(startDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the sample sbs where startDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param startDate the start date
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByStartDate(Date startDate, int start, int end) {
		return findByStartDate(startDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where startDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param startDate the start date
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByStartDate(Date startDate, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findByStartDate(startDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where startDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param startDate the start date
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByStartDate(Date startDate, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STARTDATE;
			finderArgs = new Object[] { startDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STARTDATE;
			finderArgs = new Object[] { startDate, start, end, orderByComparator };
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if (!Objects.equals(startDate, sampleSB.getStartDate())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindStartDate = false;

			if (startDate == null) {
				query.append(_FINDER_COLUMN_STARTDATE_STARTDATE_1);
			}
			else {
				bindStartDate = true;

				query.append(_FINDER_COLUMN_STARTDATE_STARTDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindStartDate) {
					qPos.add(new Timestamp(startDate.getTime()));
				}

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where startDate = &#63;.
	 *
	 * @param startDate the start date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByStartDate_First(Date startDate,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByStartDate_First(startDate, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("startDate=");
		msg.append(startDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where startDate = &#63;.
	 *
	 * @param startDate the start date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByStartDate_First(Date startDate,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByStartDate(startDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where startDate = &#63;.
	 *
	 * @param startDate the start date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByStartDate_Last(Date startDate,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByStartDate_Last(startDate, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("startDate=");
		msg.append(startDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where startDate = &#63;.
	 *
	 * @param startDate the start date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByStartDate_Last(Date startDate,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByStartDate(startDate);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByStartDate(startDate, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where startDate = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param startDate the start date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByStartDate_PrevAndNext(long samplesbId,
		Date startDate, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByStartDate_PrevAndNext(session, sampleSB, startDate,
					orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByStartDate_PrevAndNext(session, sampleSB, startDate,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByStartDate_PrevAndNext(Session session,
		SampleSB sampleSB, Date startDate,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		boolean bindStartDate = false;

		if (startDate == null) {
			query.append(_FINDER_COLUMN_STARTDATE_STARTDATE_1);
		}
		else {
			bindStartDate = true;

			query.append(_FINDER_COLUMN_STARTDATE_STARTDATE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindStartDate) {
			qPos.add(new Timestamp(startDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where startDate = &#63; from the database.
	 *
	 * @param startDate the start date
	 */
	@Override
	public void removeByStartDate(Date startDate) {
		for (SampleSB sampleSB : findByStartDate(startDate, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where startDate = &#63;.
	 *
	 * @param startDate the start date
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByStartDate(Date startDate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_STARTDATE;

		Object[] finderArgs = new Object[] { startDate };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindStartDate = false;

			if (startDate == null) {
				query.append(_FINDER_COLUMN_STARTDATE_STARTDATE_1);
			}
			else {
				bindStartDate = true;

				query.append(_FINDER_COLUMN_STARTDATE_STARTDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindStartDate) {
					qPos.add(new Timestamp(startDate.getTime()));
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_STARTDATE_STARTDATE_1 = "sampleSB.startDate IS NULL";
	private static final String _FINDER_COLUMN_STARTDATE_STARTDATE_2 = "sampleSB.startDate = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ENDDATE = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEndDate",
			new String[] {
				Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENDDATE =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEndDate",
			new String[] { Date.class.getName() },
			SampleSBModelImpl.ENDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ENDDATE = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEndDate",
			new String[] { Date.class.getName() });

	/**
	 * Returns all the sample sbs where endDate = &#63;.
	 *
	 * @param endDate the end date
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findByEndDate(Date endDate) {
		return findByEndDate(endDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where endDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param endDate the end date
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByEndDate(Date endDate, int start, int end) {
		return findByEndDate(endDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where endDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param endDate the end date
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByEndDate(Date endDate, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findByEndDate(endDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where endDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param endDate the end date
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findByEndDate(Date endDate, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENDDATE;
			finderArgs = new Object[] { endDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ENDDATE;
			finderArgs = new Object[] { endDate, start, end, orderByComparator };
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if (!Objects.equals(endDate, sampleSB.getEndDate())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindEndDate = false;

			if (endDate == null) {
				query.append(_FINDER_COLUMN_ENDDATE_ENDDATE_1);
			}
			else {
				bindEndDate = true;

				query.append(_FINDER_COLUMN_ENDDATE_ENDDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindEndDate) {
					qPos.add(new Timestamp(endDate.getTime()));
				}

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where endDate = &#63;.
	 *
	 * @param endDate the end date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByEndDate_First(Date endDate,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByEndDate_First(endDate, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("endDate=");
		msg.append(endDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where endDate = &#63;.
	 *
	 * @param endDate the end date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByEndDate_First(Date endDate,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findByEndDate(endDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where endDate = &#63;.
	 *
	 * @param endDate the end date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findByEndDate_Last(Date endDate,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByEndDate_Last(endDate, orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("endDate=");
		msg.append(endDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where endDate = &#63;.
	 *
	 * @param endDate the end date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchByEndDate_Last(Date endDate,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countByEndDate(endDate);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findByEndDate(endDate, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where endDate = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param endDate the end date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findByEndDate_PrevAndNext(long samplesbId, Date endDate,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getByEndDate_PrevAndNext(session, sampleSB, endDate,
					orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getByEndDate_PrevAndNext(session, sampleSB, endDate,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getByEndDate_PrevAndNext(Session session,
		SampleSB sampleSB, Date endDate,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		boolean bindEndDate = false;

		if (endDate == null) {
			query.append(_FINDER_COLUMN_ENDDATE_ENDDATE_1);
		}
		else {
			bindEndDate = true;

			query.append(_FINDER_COLUMN_ENDDATE_ENDDATE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindEndDate) {
			qPos.add(new Timestamp(endDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where endDate = &#63; from the database.
	 *
	 * @param endDate the end date
	 */
	@Override
	public void removeByEndDate(Date endDate) {
		for (SampleSB sampleSB : findByEndDate(endDate, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where endDate = &#63;.
	 *
	 * @param endDate the end date
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countByEndDate(Date endDate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ENDDATE;

		Object[] finderArgs = new Object[] { endDate };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindEndDate = false;

			if (endDate == null) {
				query.append(_FINDER_COLUMN_ENDDATE_ENDDATE_1);
			}
			else {
				bindEndDate = true;

				query.append(_FINDER_COLUMN_ENDDATE_ENDDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindEndDate) {
					qPos.add(new Timestamp(endDate.getTime()));
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ENDDATE_ENDDATE_1 = "sampleSB.endDate IS NULL";
	private static final String _FINDER_COLUMN_ENDDATE_ENDDATE_2 = "sampleSB.endDate = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBBOOLEANSTAT =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySamplesbBooleanStat",
			new String[] {
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBBOOLEANSTAT =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySamplesbBooleanStat",
			new String[] { Boolean.class.getName() },
			SampleSBModelImpl.SAMPLESBBOOLEANSTAT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SAMPLESBBOOLEANSTAT = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamplesbBooleanStat",
			new String[] { Boolean.class.getName() });

	/**
	 * Returns all the sample sbs where samplesbBooleanStat = &#63;.
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbBooleanStat(boolean samplesbBooleanStat) {
		return findBySamplesbBooleanStat(samplesbBooleanStat,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where samplesbBooleanStat = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbBooleanStat(
		boolean samplesbBooleanStat, int start, int end) {
		return findBySamplesbBooleanStat(samplesbBooleanStat, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbBooleanStat = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbBooleanStat(
		boolean samplesbBooleanStat, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findBySamplesbBooleanStat(samplesbBooleanStat, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbBooleanStat = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbBooleanStat(
		boolean samplesbBooleanStat, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBBOOLEANSTAT;
			finderArgs = new Object[] { samplesbBooleanStat };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBBOOLEANSTAT;
			finderArgs = new Object[] {
					samplesbBooleanStat,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((samplesbBooleanStat != sampleSB.getSamplesbBooleanStat())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_SAMPLESBBOOLEANSTAT_SAMPLESBBOOLEANSTAT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(samplesbBooleanStat);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbBooleanStat = &#63;.
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbBooleanStat_First(
		boolean samplesbBooleanStat,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbBooleanStat_First(samplesbBooleanStat,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbBooleanStat=");
		msg.append(samplesbBooleanStat);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbBooleanStat = &#63;.
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbBooleanStat_First(
		boolean samplesbBooleanStat,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findBySamplesbBooleanStat(samplesbBooleanStat, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbBooleanStat = &#63;.
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbBooleanStat_Last(
		boolean samplesbBooleanStat,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbBooleanStat_Last(samplesbBooleanStat,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbBooleanStat=");
		msg.append(samplesbBooleanStat);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbBooleanStat = &#63;.
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbBooleanStat_Last(
		boolean samplesbBooleanStat,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countBySamplesbBooleanStat(samplesbBooleanStat);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findBySamplesbBooleanStat(samplesbBooleanStat,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where samplesbBooleanStat = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param samplesbBooleanStat the samplesb boolean stat
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findBySamplesbBooleanStat_PrevAndNext(long samplesbId,
		boolean samplesbBooleanStat,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getBySamplesbBooleanStat_PrevAndNext(session, sampleSB,
					samplesbBooleanStat, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getBySamplesbBooleanStat_PrevAndNext(session, sampleSB,
					samplesbBooleanStat, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getBySamplesbBooleanStat_PrevAndNext(Session session,
		SampleSB sampleSB, boolean samplesbBooleanStat,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_SAMPLESBBOOLEANSTAT_SAMPLESBBOOLEANSTAT_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(samplesbBooleanStat);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where samplesbBooleanStat = &#63; from the database.
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat
	 */
	@Override
	public void removeBySamplesbBooleanStat(boolean samplesbBooleanStat) {
		for (SampleSB sampleSB : findBySamplesbBooleanStat(
				samplesbBooleanStat, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where samplesbBooleanStat = &#63;.
	 *
	 * @param samplesbBooleanStat the samplesb boolean stat
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countBySamplesbBooleanStat(boolean samplesbBooleanStat) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SAMPLESBBOOLEANSTAT;

		Object[] finderArgs = new Object[] { samplesbBooleanStat };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_SAMPLESBBOOLEANSTAT_SAMPLESBBOOLEANSTAT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(samplesbBooleanStat);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SAMPLESBBOOLEANSTAT_SAMPLESBBOOLEANSTAT_2 =
		"sampleSB.samplesbBooleanStat = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBDATETIME =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySamplesbDateTime",
			new String[] {
				Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDATETIME =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySamplesbDateTime", new String[] { Date.class.getName() },
			SampleSBModelImpl.SAMPLESBDATETIME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SAMPLESBDATETIME = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamplesbDateTime", new String[] { Date.class.getName() });

	/**
	 * Returns all the sample sbs where samplesbDateTime = &#63;.
	 *
	 * @param samplesbDateTime the samplesb date time
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDateTime(Date samplesbDateTime) {
		return findBySamplesbDateTime(samplesbDateTime, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where samplesbDateTime = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDateTime the samplesb date time
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDateTime(Date samplesbDateTime,
		int start, int end) {
		return findBySamplesbDateTime(samplesbDateTime, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbDateTime = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDateTime the samplesb date time
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDateTime(Date samplesbDateTime,
		int start, int end, OrderByComparator<SampleSB> orderByComparator) {
		return findBySamplesbDateTime(samplesbDateTime, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbDateTime = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDateTime the samplesb date time
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDateTime(Date samplesbDateTime,
		int start, int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDATETIME;
			finderArgs = new Object[] { samplesbDateTime };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBDATETIME;
			finderArgs = new Object[] {
					samplesbDateTime,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if (!Objects.equals(samplesbDateTime,
								sampleSB.getSamplesbDateTime())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindSamplesbDateTime = false;

			if (samplesbDateTime == null) {
				query.append(_FINDER_COLUMN_SAMPLESBDATETIME_SAMPLESBDATETIME_1);
			}
			else {
				bindSamplesbDateTime = true;

				query.append(_FINDER_COLUMN_SAMPLESBDATETIME_SAMPLESBDATETIME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamplesbDateTime) {
					qPos.add(new Timestamp(samplesbDateTime.getTime()));
				}

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbDateTime = &#63;.
	 *
	 * @param samplesbDateTime the samplesb date time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbDateTime_First(Date samplesbDateTime,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbDateTime_First(samplesbDateTime,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbDateTime=");
		msg.append(samplesbDateTime);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbDateTime = &#63;.
	 *
	 * @param samplesbDateTime the samplesb date time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbDateTime_First(Date samplesbDateTime,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findBySamplesbDateTime(samplesbDateTime, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbDateTime = &#63;.
	 *
	 * @param samplesbDateTime the samplesb date time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbDateTime_Last(Date samplesbDateTime,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbDateTime_Last(samplesbDateTime,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbDateTime=");
		msg.append(samplesbDateTime);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbDateTime = &#63;.
	 *
	 * @param samplesbDateTime the samplesb date time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbDateTime_Last(Date samplesbDateTime,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countBySamplesbDateTime(samplesbDateTime);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findBySamplesbDateTime(samplesbDateTime,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where samplesbDateTime = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param samplesbDateTime the samplesb date time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findBySamplesbDateTime_PrevAndNext(long samplesbId,
		Date samplesbDateTime, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getBySamplesbDateTime_PrevAndNext(session, sampleSB,
					samplesbDateTime, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getBySamplesbDateTime_PrevAndNext(session, sampleSB,
					samplesbDateTime, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getBySamplesbDateTime_PrevAndNext(Session session,
		SampleSB sampleSB, Date samplesbDateTime,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		boolean bindSamplesbDateTime = false;

		if (samplesbDateTime == null) {
			query.append(_FINDER_COLUMN_SAMPLESBDATETIME_SAMPLESBDATETIME_1);
		}
		else {
			bindSamplesbDateTime = true;

			query.append(_FINDER_COLUMN_SAMPLESBDATETIME_SAMPLESBDATETIME_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSamplesbDateTime) {
			qPos.add(new Timestamp(samplesbDateTime.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where samplesbDateTime = &#63; from the database.
	 *
	 * @param samplesbDateTime the samplesb date time
	 */
	@Override
	public void removeBySamplesbDateTime(Date samplesbDateTime) {
		for (SampleSB sampleSB : findBySamplesbDateTime(samplesbDateTime,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where samplesbDateTime = &#63;.
	 *
	 * @param samplesbDateTime the samplesb date time
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countBySamplesbDateTime(Date samplesbDateTime) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SAMPLESBDATETIME;

		Object[] finderArgs = new Object[] { samplesbDateTime };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindSamplesbDateTime = false;

			if (samplesbDateTime == null) {
				query.append(_FINDER_COLUMN_SAMPLESBDATETIME_SAMPLESBDATETIME_1);
			}
			else {
				bindSamplesbDateTime = true;

				query.append(_FINDER_COLUMN_SAMPLESBDATETIME_SAMPLESBDATETIME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamplesbDateTime) {
					qPos.add(new Timestamp(samplesbDateTime.getTime()));
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SAMPLESBDATETIME_SAMPLESBDATETIME_1 =
		"sampleSB.samplesbDateTime IS NULL";
	private static final String _FINDER_COLUMN_SAMPLESBDATETIME_SAMPLESBDATETIME_2 =
		"sampleSB.samplesbDateTime = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBDOCUMENT =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySamplesbDocument",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENT =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySamplesbDocument", new String[] { Long.class.getName() },
			SampleSBModelImpl.SAMPLESBDOCUMENT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SAMPLESBDOCUMENT = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamplesbDocument", new String[] { Long.class.getName() });

	/**
	 * Returns all the sample sbs where samplesbDocument = &#63;.
	 *
	 * @param samplesbDocument the samplesb document
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDocument(long samplesbDocument) {
		return findBySamplesbDocument(samplesbDocument, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where samplesbDocument = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDocument the samplesb document
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDocument(long samplesbDocument,
		int start, int end) {
		return findBySamplesbDocument(samplesbDocument, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbDocument = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDocument the samplesb document
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDocument(long samplesbDocument,
		int start, int end, OrderByComparator<SampleSB> orderByComparator) {
		return findBySamplesbDocument(samplesbDocument, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbDocument = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDocument the samplesb document
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDocument(long samplesbDocument,
		int start, int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENT;
			finderArgs = new Object[] { samplesbDocument };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBDOCUMENT;
			finderArgs = new Object[] {
					samplesbDocument,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((samplesbDocument != sampleSB.getSamplesbDocument())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_SAMPLESBDOCUMENT_SAMPLESBDOCUMENT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(samplesbDocument);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbDocument = &#63;.
	 *
	 * @param samplesbDocument the samplesb document
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbDocument_First(long samplesbDocument,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbDocument_First(samplesbDocument,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbDocument=");
		msg.append(samplesbDocument);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbDocument = &#63;.
	 *
	 * @param samplesbDocument the samplesb document
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbDocument_First(long samplesbDocument,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findBySamplesbDocument(samplesbDocument, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbDocument = &#63;.
	 *
	 * @param samplesbDocument the samplesb document
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbDocument_Last(long samplesbDocument,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbDocument_Last(samplesbDocument,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbDocument=");
		msg.append(samplesbDocument);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbDocument = &#63;.
	 *
	 * @param samplesbDocument the samplesb document
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbDocument_Last(long samplesbDocument,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countBySamplesbDocument(samplesbDocument);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findBySamplesbDocument(samplesbDocument,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where samplesbDocument = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param samplesbDocument the samplesb document
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findBySamplesbDocument_PrevAndNext(long samplesbId,
		long samplesbDocument, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getBySamplesbDocument_PrevAndNext(session, sampleSB,
					samplesbDocument, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getBySamplesbDocument_PrevAndNext(session, sampleSB,
					samplesbDocument, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getBySamplesbDocument_PrevAndNext(Session session,
		SampleSB sampleSB, long samplesbDocument,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_SAMPLESBDOCUMENT_SAMPLESBDOCUMENT_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(samplesbDocument);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where samplesbDocument = &#63; from the database.
	 *
	 * @param samplesbDocument the samplesb document
	 */
	@Override
	public void removeBySamplesbDocument(long samplesbDocument) {
		for (SampleSB sampleSB : findBySamplesbDocument(samplesbDocument,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where samplesbDocument = &#63;.
	 *
	 * @param samplesbDocument the samplesb document
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countBySamplesbDocument(long samplesbDocument) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SAMPLESBDOCUMENT;

		Object[] finderArgs = new Object[] { samplesbDocument };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_SAMPLESBDOCUMENT_SAMPLESBDOCUMENT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(samplesbDocument);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SAMPLESBDOCUMENT_SAMPLESBDOCUMENT_2 =
		"sampleSB.samplesbDocument = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBDOCUMENTLIBRARY =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySamplesbDocumentLibrary",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENTLIBRARY =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySamplesbDocumentLibrary",
			new String[] { String.class.getName() },
			SampleSBModelImpl.SAMPLESBDOCUMENTLIBRARY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SAMPLESBDOCUMENTLIBRARY = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamplesbDocumentLibrary",
			new String[] { String.class.getName() });

	/**
	 * Returns all the sample sbs where samplesbDocumentLibrary = &#63;.
	 *
	 * @param samplesbDocumentLibrary the samplesb document library
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDocumentLibrary(
		String samplesbDocumentLibrary) {
		return findBySamplesbDocumentLibrary(samplesbDocumentLibrary,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where samplesbDocumentLibrary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDocumentLibrary the samplesb document library
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDocumentLibrary(
		String samplesbDocumentLibrary, int start, int end) {
		return findBySamplesbDocumentLibrary(samplesbDocumentLibrary, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbDocumentLibrary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDocumentLibrary the samplesb document library
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDocumentLibrary(
		String samplesbDocumentLibrary, int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findBySamplesbDocumentLibrary(samplesbDocumentLibrary, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbDocumentLibrary = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDocumentLibrary the samplesb document library
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDocumentLibrary(
		String samplesbDocumentLibrary, int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENTLIBRARY;
			finderArgs = new Object[] { samplesbDocumentLibrary };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBDOCUMENTLIBRARY;
			finderArgs = new Object[] {
					samplesbDocumentLibrary,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if (!Objects.equals(samplesbDocumentLibrary,
								sampleSB.getSamplesbDocumentLibrary())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindSamplesbDocumentLibrary = false;

			if (samplesbDocumentLibrary == null) {
				query.append(_FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_1);
			}
			else if (samplesbDocumentLibrary.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_3);
			}
			else {
				bindSamplesbDocumentLibrary = true;

				query.append(_FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamplesbDocumentLibrary) {
					qPos.add(samplesbDocumentLibrary);
				}

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbDocumentLibrary = &#63;.
	 *
	 * @param samplesbDocumentLibrary the samplesb document library
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbDocumentLibrary_First(
		String samplesbDocumentLibrary,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbDocumentLibrary_First(samplesbDocumentLibrary,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbDocumentLibrary=");
		msg.append(samplesbDocumentLibrary);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbDocumentLibrary = &#63;.
	 *
	 * @param samplesbDocumentLibrary the samplesb document library
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbDocumentLibrary_First(
		String samplesbDocumentLibrary,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findBySamplesbDocumentLibrary(samplesbDocumentLibrary,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbDocumentLibrary = &#63;.
	 *
	 * @param samplesbDocumentLibrary the samplesb document library
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbDocumentLibrary_Last(
		String samplesbDocumentLibrary,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbDocumentLibrary_Last(samplesbDocumentLibrary,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbDocumentLibrary=");
		msg.append(samplesbDocumentLibrary);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbDocumentLibrary = &#63;.
	 *
	 * @param samplesbDocumentLibrary the samplesb document library
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbDocumentLibrary_Last(
		String samplesbDocumentLibrary,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countBySamplesbDocumentLibrary(samplesbDocumentLibrary);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findBySamplesbDocumentLibrary(samplesbDocumentLibrary,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where samplesbDocumentLibrary = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param samplesbDocumentLibrary the samplesb document library
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findBySamplesbDocumentLibrary_PrevAndNext(
		long samplesbId, String samplesbDocumentLibrary,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getBySamplesbDocumentLibrary_PrevAndNext(session,
					sampleSB, samplesbDocumentLibrary, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getBySamplesbDocumentLibrary_PrevAndNext(session,
					sampleSB, samplesbDocumentLibrary, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getBySamplesbDocumentLibrary_PrevAndNext(
		Session session, SampleSB sampleSB, String samplesbDocumentLibrary,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		boolean bindSamplesbDocumentLibrary = false;

		if (samplesbDocumentLibrary == null) {
			query.append(_FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_1);
		}
		else if (samplesbDocumentLibrary.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_3);
		}
		else {
			bindSamplesbDocumentLibrary = true;

			query.append(_FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSamplesbDocumentLibrary) {
			qPos.add(samplesbDocumentLibrary);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where samplesbDocumentLibrary = &#63; from the database.
	 *
	 * @param samplesbDocumentLibrary the samplesb document library
	 */
	@Override
	public void removeBySamplesbDocumentLibrary(String samplesbDocumentLibrary) {
		for (SampleSB sampleSB : findBySamplesbDocumentLibrary(
				samplesbDocumentLibrary, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where samplesbDocumentLibrary = &#63;.
	 *
	 * @param samplesbDocumentLibrary the samplesb document library
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countBySamplesbDocumentLibrary(String samplesbDocumentLibrary) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SAMPLESBDOCUMENTLIBRARY;

		Object[] finderArgs = new Object[] { samplesbDocumentLibrary };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindSamplesbDocumentLibrary = false;

			if (samplesbDocumentLibrary == null) {
				query.append(_FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_1);
			}
			else if (samplesbDocumentLibrary.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_3);
			}
			else {
				bindSamplesbDocumentLibrary = true;

				query.append(_FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamplesbDocumentLibrary) {
					qPos.add(samplesbDocumentLibrary);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_1 =
		"sampleSB.samplesbDocumentLibrary IS NULL";
	private static final String _FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_2 =
		"sampleSB.samplesbDocumentLibrary = ?";
	private static final String _FINDER_COLUMN_SAMPLESBDOCUMENTLIBRARY_SAMPLESBDOCUMENTLIBRARY_3 =
		"(sampleSB.samplesbDocumentLibrary IS NULL OR sampleSB.samplesbDocumentLibrary = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBDOUBLE =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySamplesbDouble",
			new String[] {
				Double.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOUBLE =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySamplesbDouble",
			new String[] { Double.class.getName() },
			SampleSBModelImpl.SAMPLESBDOUBLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SAMPLESBDOUBLE = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySamplesbDouble",
			new String[] { Double.class.getName() });

	/**
	 * Returns all the sample sbs where samplesbDouble = &#63;.
	 *
	 * @param samplesbDouble the samplesb double
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDouble(double samplesbDouble) {
		return findBySamplesbDouble(samplesbDouble, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where samplesbDouble = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDouble the samplesb double
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDouble(double samplesbDouble,
		int start, int end) {
		return findBySamplesbDouble(samplesbDouble, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbDouble = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDouble the samplesb double
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDouble(double samplesbDouble,
		int start, int end, OrderByComparator<SampleSB> orderByComparator) {
		return findBySamplesbDouble(samplesbDouble, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbDouble = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbDouble the samplesb double
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbDouble(double samplesbDouble,
		int start, int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOUBLE;
			finderArgs = new Object[] { samplesbDouble };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBDOUBLE;
			finderArgs = new Object[] {
					samplesbDouble,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((samplesbDouble != sampleSB.getSamplesbDouble())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_SAMPLESBDOUBLE_SAMPLESBDOUBLE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(samplesbDouble);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbDouble = &#63;.
	 *
	 * @param samplesbDouble the samplesb double
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbDouble_First(double samplesbDouble,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbDouble_First(samplesbDouble,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbDouble=");
		msg.append(samplesbDouble);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbDouble = &#63;.
	 *
	 * @param samplesbDouble the samplesb double
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbDouble_First(double samplesbDouble,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findBySamplesbDouble(samplesbDouble, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbDouble = &#63;.
	 *
	 * @param samplesbDouble the samplesb double
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbDouble_Last(double samplesbDouble,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbDouble_Last(samplesbDouble,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbDouble=");
		msg.append(samplesbDouble);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbDouble = &#63;.
	 *
	 * @param samplesbDouble the samplesb double
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbDouble_Last(double samplesbDouble,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countBySamplesbDouble(samplesbDouble);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findBySamplesbDouble(samplesbDouble, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where samplesbDouble = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param samplesbDouble the samplesb double
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findBySamplesbDouble_PrevAndNext(long samplesbId,
		double samplesbDouble, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getBySamplesbDouble_PrevAndNext(session, sampleSB,
					samplesbDouble, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getBySamplesbDouble_PrevAndNext(session, sampleSB,
					samplesbDouble, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getBySamplesbDouble_PrevAndNext(Session session,
		SampleSB sampleSB, double samplesbDouble,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_SAMPLESBDOUBLE_SAMPLESBDOUBLE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(samplesbDouble);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where samplesbDouble = &#63; from the database.
	 *
	 * @param samplesbDouble the samplesb double
	 */
	@Override
	public void removeBySamplesbDouble(double samplesbDouble) {
		for (SampleSB sampleSB : findBySamplesbDouble(samplesbDouble,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where samplesbDouble = &#63;.
	 *
	 * @param samplesbDouble the samplesb double
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countBySamplesbDouble(double samplesbDouble) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SAMPLESBDOUBLE;

		Object[] finderArgs = new Object[] { samplesbDouble };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_SAMPLESBDOUBLE_SAMPLESBDOUBLE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(samplesbDouble);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SAMPLESBDOUBLE_SAMPLESBDOUBLE_2 = "sampleSB.samplesbDouble = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBINTEGER =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySamplesbInteger",
			new String[] {
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBINTEGER =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySamplesbInteger",
			new String[] { Integer.class.getName() },
			SampleSBModelImpl.SAMPLESBINTEGER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SAMPLESBINTEGER = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamplesbInteger", new String[] { Integer.class.getName() });

	/**
	 * Returns all the sample sbs where samplesbInteger = &#63;.
	 *
	 * @param samplesbInteger the samplesb integer
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbInteger(int samplesbInteger) {
		return findBySamplesbInteger(samplesbInteger, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where samplesbInteger = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbInteger the samplesb integer
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbInteger(int samplesbInteger, int start,
		int end) {
		return findBySamplesbInteger(samplesbInteger, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbInteger = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbInteger the samplesb integer
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbInteger(int samplesbInteger, int start,
		int end, OrderByComparator<SampleSB> orderByComparator) {
		return findBySamplesbInteger(samplesbInteger, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbInteger = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbInteger the samplesb integer
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbInteger(int samplesbInteger, int start,
		int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBINTEGER;
			finderArgs = new Object[] { samplesbInteger };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBINTEGER;
			finderArgs = new Object[] {
					samplesbInteger,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if ((samplesbInteger != sampleSB.getSamplesbInteger())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_SAMPLESBINTEGER_SAMPLESBINTEGER_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(samplesbInteger);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbInteger = &#63;.
	 *
	 * @param samplesbInteger the samplesb integer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbInteger_First(int samplesbInteger,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbInteger_First(samplesbInteger,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbInteger=");
		msg.append(samplesbInteger);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbInteger = &#63;.
	 *
	 * @param samplesbInteger the samplesb integer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbInteger_First(int samplesbInteger,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findBySamplesbInteger(samplesbInteger, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbInteger = &#63;.
	 *
	 * @param samplesbInteger the samplesb integer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbInteger_Last(int samplesbInteger,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbInteger_Last(samplesbInteger,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbInteger=");
		msg.append(samplesbInteger);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbInteger = &#63;.
	 *
	 * @param samplesbInteger the samplesb integer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbInteger_Last(int samplesbInteger,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countBySamplesbInteger(samplesbInteger);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findBySamplesbInteger(samplesbInteger, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where samplesbInteger = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param samplesbInteger the samplesb integer
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findBySamplesbInteger_PrevAndNext(long samplesbId,
		int samplesbInteger, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getBySamplesbInteger_PrevAndNext(session, sampleSB,
					samplesbInteger, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getBySamplesbInteger_PrevAndNext(session, sampleSB,
					samplesbInteger, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getBySamplesbInteger_PrevAndNext(Session session,
		SampleSB sampleSB, int samplesbInteger,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		query.append(_FINDER_COLUMN_SAMPLESBINTEGER_SAMPLESBINTEGER_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(samplesbInteger);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where samplesbInteger = &#63; from the database.
	 *
	 * @param samplesbInteger the samplesb integer
	 */
	@Override
	public void removeBySamplesbInteger(int samplesbInteger) {
		for (SampleSB sampleSB : findBySamplesbInteger(samplesbInteger,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where samplesbInteger = &#63;.
	 *
	 * @param samplesbInteger the samplesb integer
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countBySamplesbInteger(int samplesbInteger) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SAMPLESBINTEGER;

		Object[] finderArgs = new Object[] { samplesbInteger };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			query.append(_FINDER_COLUMN_SAMPLESBINTEGER_SAMPLESBINTEGER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(samplesbInteger);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SAMPLESBINTEGER_SAMPLESBINTEGER_2 =
		"sampleSB.samplesbInteger = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBRICHTEXT =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySamplesbRichText",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBRICHTEXT =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySamplesbRichText", new String[] { String.class.getName() },
			SampleSBModelImpl.SAMPLESBRICHTEXT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SAMPLESBRICHTEXT = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamplesbRichText", new String[] { String.class.getName() });

	/**
	 * Returns all the sample sbs where samplesbRichText = &#63;.
	 *
	 * @param samplesbRichText the samplesb rich text
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbRichText(String samplesbRichText) {
		return findBySamplesbRichText(samplesbRichText, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where samplesbRichText = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbRichText the samplesb rich text
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbRichText(String samplesbRichText,
		int start, int end) {
		return findBySamplesbRichText(samplesbRichText, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbRichText = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbRichText the samplesb rich text
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbRichText(String samplesbRichText,
		int start, int end, OrderByComparator<SampleSB> orderByComparator) {
		return findBySamplesbRichText(samplesbRichText, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbRichText = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbRichText the samplesb rich text
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbRichText(String samplesbRichText,
		int start, int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBRICHTEXT;
			finderArgs = new Object[] { samplesbRichText };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBRICHTEXT;
			finderArgs = new Object[] {
					samplesbRichText,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if (!Objects.equals(samplesbRichText,
								sampleSB.getSamplesbRichText())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindSamplesbRichText = false;

			if (samplesbRichText == null) {
				query.append(_FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_1);
			}
			else if (samplesbRichText.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_3);
			}
			else {
				bindSamplesbRichText = true;

				query.append(_FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamplesbRichText) {
					qPos.add(samplesbRichText);
				}

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbRichText = &#63;.
	 *
	 * @param samplesbRichText the samplesb rich text
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbRichText_First(String samplesbRichText,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbRichText_First(samplesbRichText,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbRichText=");
		msg.append(samplesbRichText);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbRichText = &#63;.
	 *
	 * @param samplesbRichText the samplesb rich text
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbRichText_First(String samplesbRichText,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findBySamplesbRichText(samplesbRichText, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbRichText = &#63;.
	 *
	 * @param samplesbRichText the samplesb rich text
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbRichText_Last(String samplesbRichText,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbRichText_Last(samplesbRichText,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbRichText=");
		msg.append(samplesbRichText);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbRichText = &#63;.
	 *
	 * @param samplesbRichText the samplesb rich text
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbRichText_Last(String samplesbRichText,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countBySamplesbRichText(samplesbRichText);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findBySamplesbRichText(samplesbRichText,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where samplesbRichText = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param samplesbRichText the samplesb rich text
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findBySamplesbRichText_PrevAndNext(long samplesbId,
		String samplesbRichText, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getBySamplesbRichText_PrevAndNext(session, sampleSB,
					samplesbRichText, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getBySamplesbRichText_PrevAndNext(session, sampleSB,
					samplesbRichText, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getBySamplesbRichText_PrevAndNext(Session session,
		SampleSB sampleSB, String samplesbRichText,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		boolean bindSamplesbRichText = false;

		if (samplesbRichText == null) {
			query.append(_FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_1);
		}
		else if (samplesbRichText.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_3);
		}
		else {
			bindSamplesbRichText = true;

			query.append(_FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSamplesbRichText) {
			qPos.add(samplesbRichText);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where samplesbRichText = &#63; from the database.
	 *
	 * @param samplesbRichText the samplesb rich text
	 */
	@Override
	public void removeBySamplesbRichText(String samplesbRichText) {
		for (SampleSB sampleSB : findBySamplesbRichText(samplesbRichText,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where samplesbRichText = &#63;.
	 *
	 * @param samplesbRichText the samplesb rich text
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countBySamplesbRichText(String samplesbRichText) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SAMPLESBRICHTEXT;

		Object[] finderArgs = new Object[] { samplesbRichText };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindSamplesbRichText = false;

			if (samplesbRichText == null) {
				query.append(_FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_1);
			}
			else if (samplesbRichText.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_3);
			}
			else {
				bindSamplesbRichText = true;

				query.append(_FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamplesbRichText) {
					qPos.add(samplesbRichText);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_1 =
		"sampleSB.samplesbRichText IS NULL";
	private static final String _FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_2 =
		"sampleSB.samplesbRichText = ?";
	private static final String _FINDER_COLUMN_SAMPLESBRICHTEXT_SAMPLESBRICHTEXT_3 =
		"(sampleSB.samplesbRichText IS NULL OR sampleSB.samplesbRichText = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBTEXT =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySamplesbText",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBTEXT =
		new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, SampleSBImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySamplesbText",
			new String[] { String.class.getName() },
			SampleSBModelImpl.SAMPLESBTEXT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SAMPLESBTEXT = new FinderPath(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySamplesbText",
			new String[] { String.class.getName() });

	/**
	 * Returns all the sample sbs where samplesbText = &#63;.
	 *
	 * @param samplesbText the samplesb text
	 * @return the matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbText(String samplesbText) {
		return findBySamplesbText(samplesbText, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs where samplesbText = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbText the samplesb text
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbText(String samplesbText, int start,
		int end) {
		return findBySamplesbText(samplesbText, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbText = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbText the samplesb text
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbText(String samplesbText, int start,
		int end, OrderByComparator<SampleSB> orderByComparator) {
		return findBySamplesbText(samplesbText, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the sample sbs where samplesbText = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param samplesbText the samplesb text
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sample sbs
	 */
	@Override
	public List<SampleSB> findBySamplesbText(String samplesbText, int start,
		int end, OrderByComparator<SampleSB> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBTEXT;
			finderArgs = new Object[] { samplesbText };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SAMPLESBTEXT;
			finderArgs = new Object[] {
					samplesbText,
					
					start, end, orderByComparator
				};
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SampleSB sampleSB : list) {
					if (!Objects.equals(samplesbText, sampleSB.getSamplesbText())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMPLESB_WHERE);

			boolean bindSamplesbText = false;

			if (samplesbText == null) {
				query.append(_FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_1);
			}
			else if (samplesbText.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_3);
			}
			else {
				bindSamplesbText = true;

				query.append(_FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SampleSBModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamplesbText) {
					qPos.add(samplesbText);
				}

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbText = &#63;.
	 *
	 * @param samplesbText the samplesb text
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbText_First(String samplesbText,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbText_First(samplesbText,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbText=");
		msg.append(samplesbText);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the first sample sb in the ordered set where samplesbText = &#63;.
	 *
	 * @param samplesbText the samplesb text
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbText_First(String samplesbText,
		OrderByComparator<SampleSB> orderByComparator) {
		List<SampleSB> list = findBySamplesbText(samplesbText, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbText = &#63;.
	 *
	 * @param samplesbText the samplesb text
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb
	 * @throws NoSuchSampleSBException if a matching sample sb could not be found
	 */
	@Override
	public SampleSB findBySamplesbText_Last(String samplesbText,
		OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchBySamplesbText_Last(samplesbText,
				orderByComparator);

		if (sampleSB != null) {
			return sampleSB;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("samplesbText=");
		msg.append(samplesbText);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSampleSBException(msg.toString());
	}

	/**
	 * Returns the last sample sb in the ordered set where samplesbText = &#63;.
	 *
	 * @param samplesbText the samplesb text
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sample sb, or <code>null</code> if a matching sample sb could not be found
	 */
	@Override
	public SampleSB fetchBySamplesbText_Last(String samplesbText,
		OrderByComparator<SampleSB> orderByComparator) {
		int count = countBySamplesbText(samplesbText);

		if (count == 0) {
			return null;
		}

		List<SampleSB> list = findBySamplesbText(samplesbText, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sample sbs before and after the current sample sb in the ordered set where samplesbText = &#63;.
	 *
	 * @param samplesbId the primary key of the current sample sb
	 * @param samplesbText the samplesb text
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB[] findBySamplesbText_PrevAndNext(long samplesbId,
		String samplesbText, OrderByComparator<SampleSB> orderByComparator)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = findByPrimaryKey(samplesbId);

		Session session = null;

		try {
			session = openSession();

			SampleSB[] array = new SampleSBImpl[3];

			array[0] = getBySamplesbText_PrevAndNext(session, sampleSB,
					samplesbText, orderByComparator, true);

			array[1] = sampleSB;

			array[2] = getBySamplesbText_PrevAndNext(session, sampleSB,
					samplesbText, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SampleSB getBySamplesbText_PrevAndNext(Session session,
		SampleSB sampleSB, String samplesbText,
		OrderByComparator<SampleSB> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMPLESB_WHERE);

		boolean bindSamplesbText = false;

		if (samplesbText == null) {
			query.append(_FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_1);
		}
		else if (samplesbText.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_3);
		}
		else {
			bindSamplesbText = true;

			query.append(_FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SampleSBModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSamplesbText) {
			qPos.add(samplesbText);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sampleSB);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SampleSB> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sample sbs where samplesbText = &#63; from the database.
	 *
	 * @param samplesbText the samplesb text
	 */
	@Override
	public void removeBySamplesbText(String samplesbText) {
		for (SampleSB sampleSB : findBySamplesbText(samplesbText,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs where samplesbText = &#63;.
	 *
	 * @param samplesbText the samplesb text
	 * @return the number of matching sample sbs
	 */
	@Override
	public int countBySamplesbText(String samplesbText) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SAMPLESBTEXT;

		Object[] finderArgs = new Object[] { samplesbText };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMPLESB_WHERE);

			boolean bindSamplesbText = false;

			if (samplesbText == null) {
				query.append(_FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_1);
			}
			else if (samplesbText.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_3);
			}
			else {
				bindSamplesbText = true;

				query.append(_FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamplesbText) {
					qPos.add(samplesbText);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_1 = "sampleSB.samplesbText IS NULL";
	private static final String _FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_2 = "sampleSB.samplesbText = ?";
	private static final String _FINDER_COLUMN_SAMPLESBTEXT_SAMPLESBTEXT_3 = "(sampleSB.samplesbText IS NULL OR sampleSB.samplesbText = '')";

	public SampleSBPersistenceImpl() {
		setModelClass(SampleSB.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the sample sb in the entity cache if it is enabled.
	 *
	 * @param sampleSB the sample sb
	 */
	@Override
	public void cacheResult(SampleSB sampleSB) {
		entityCache.putResult(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBImpl.class, sampleSB.getPrimaryKey(), sampleSB);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { sampleSB.getUuid(), sampleSB.getGroupId() }, sampleSB);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_UT,
			new Object[] { sampleSB.getGroupId(), sampleSB.getUrlTitle() },
			sampleSB);

		finderCache.putResult(FINDER_PATH_FETCH_BY_URLTITLE,
			new Object[] { sampleSB.getUrlTitle() }, sampleSB);

		sampleSB.resetOriginalValues();
	}

	/**
	 * Caches the sample sbs in the entity cache if it is enabled.
	 *
	 * @param sampleSBs the sample sbs
	 */
	@Override
	public void cacheResult(List<SampleSB> sampleSBs) {
		for (SampleSB sampleSB : sampleSBs) {
			if (entityCache.getResult(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
						SampleSBImpl.class, sampleSB.getPrimaryKey()) == null) {
				cacheResult(sampleSB);
			}
			else {
				sampleSB.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all sample sbs.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SampleSBImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the sample sb.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SampleSB sampleSB) {
		entityCache.removeResult(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBImpl.class, sampleSB.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SampleSBModelImpl)sampleSB, true);
	}

	@Override
	public void clearCache(List<SampleSB> sampleSBs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SampleSB sampleSB : sampleSBs) {
			entityCache.removeResult(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
				SampleSBImpl.class, sampleSB.getPrimaryKey());

			clearUniqueFindersCache((SampleSBModelImpl)sampleSB, true);
		}
	}

	protected void cacheUniqueFindersCache(SampleSBModelImpl sampleSBModelImpl) {
		Object[] args = new Object[] {
				sampleSBModelImpl.getUuid(), sampleSBModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			sampleSBModelImpl, false);

		args = new Object[] {
				sampleSBModelImpl.getGroupId(), sampleSBModelImpl.getUrlTitle()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_UT, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_UT, args,
			sampleSBModelImpl, false);

		args = new Object[] { sampleSBModelImpl.getUrlTitle() };

		finderCache.putResult(FINDER_PATH_COUNT_BY_URLTITLE, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_URLTITLE, args,
			sampleSBModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SampleSBModelImpl sampleSBModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					sampleSBModelImpl.getUuid(), sampleSBModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((sampleSBModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					sampleSBModelImpl.getOriginalUuid(),
					sampleSBModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					sampleSBModelImpl.getGroupId(),
					sampleSBModelImpl.getUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_UT, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_UT, args);
		}

		if ((sampleSBModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_UT.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					sampleSBModelImpl.getOriginalGroupId(),
					sampleSBModelImpl.getOriginalUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_UT, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_UT, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] { sampleSBModelImpl.getUrlTitle() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_URLTITLE, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_URLTITLE, args);
		}

		if ((sampleSBModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_URLTITLE.getColumnBitmask()) != 0) {
			Object[] args = new Object[] { sampleSBModelImpl.getOriginalUrlTitle() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_URLTITLE, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_URLTITLE, args);
		}
	}

	/**
	 * Creates a new sample sb with the primary key. Does not add the sample sb to the database.
	 *
	 * @param samplesbId the primary key for the new sample sb
	 * @return the new sample sb
	 */
	@Override
	public SampleSB create(long samplesbId) {
		SampleSB sampleSB = new SampleSBImpl();

		sampleSB.setNew(true);
		sampleSB.setPrimaryKey(samplesbId);

		String uuid = PortalUUIDUtil.generate();

		sampleSB.setUuid(uuid);

		sampleSB.setCompanyId(companyProvider.getCompanyId());

		return sampleSB;
	}

	/**
	 * Removes the sample sb with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samplesbId the primary key of the sample sb
	 * @return the sample sb that was removed
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB remove(long samplesbId) throws NoSuchSampleSBException {
		return remove((Serializable)samplesbId);
	}

	/**
	 * Removes the sample sb with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the sample sb
	 * @return the sample sb that was removed
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB remove(Serializable primaryKey)
		throws NoSuchSampleSBException {
		Session session = null;

		try {
			session = openSession();

			SampleSB sampleSB = (SampleSB)session.get(SampleSBImpl.class,
					primaryKey);

			if (sampleSB == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSampleSBException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(sampleSB);
		}
		catch (NoSuchSampleSBException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected SampleSB removeImpl(SampleSB sampleSB) {
		sampleSB = toUnwrappedModel(sampleSB);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(sampleSB)) {
				sampleSB = (SampleSB)session.get(SampleSBImpl.class,
						sampleSB.getPrimaryKeyObj());
			}

			if (sampleSB != null) {
				session.delete(sampleSB);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (sampleSB != null) {
			clearCache(sampleSB);
		}

		return sampleSB;
	}

	@Override
	public SampleSB updateImpl(SampleSB sampleSB) {
		sampleSB = toUnwrappedModel(sampleSB);

		boolean isNew = sampleSB.isNew();

		SampleSBModelImpl sampleSBModelImpl = (SampleSBModelImpl)sampleSB;

		if (Validator.isNull(sampleSB.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			sampleSB.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (sampleSB.getCreateDate() == null)) {
			if (serviceContext == null) {
				sampleSB.setCreateDate(now);
			}
			else {
				sampleSB.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!sampleSBModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				sampleSB.setModifiedDate(now);
			}
			else {
				sampleSB.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (sampleSB.isNew()) {
				session.save(sampleSB);

				sampleSB.setNew(false);
			}
			else {
				sampleSB = (SampleSB)session.merge(sampleSB);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SampleSBModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { sampleSBModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					sampleSBModelImpl.getUuid(),
					sampleSBModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					sampleSBModelImpl.getCompanyId(),
					sampleSBModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
				args);

			args = new Object[] {
					sampleSBModelImpl.getGroupId(),
					sampleSBModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
				args);

			args = new Object[] {
					sampleSBModelImpl.getCompanyId(),
					sampleSBModelImpl.getUserId(), sampleSBModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_U_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S,
				args);

			args = new Object[] {
					sampleSBModelImpl.getGroupId(),
					sampleSBModelImpl.getUserId(), sampleSBModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S,
				args);

			args = new Object[] {
					sampleSBModelImpl.getUserId(), sampleSBModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S,
				args);

			args = new Object[] {
					sampleSBModelImpl.getGroupId(),
					sampleSBModelImpl.getUrlTitle(),
					sampleSBModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_UT_ST, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST,
				args);

			args = new Object[] { sampleSBModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					sampleSBModelImpl.getUserId(),
					sampleSBModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_USERIDGROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID,
				args);

			args = new Object[] { sampleSBModelImpl.getUserId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
				args);

			args = new Object[] { sampleSBModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] { sampleSBModelImpl.getSamplesbId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBID,
				args);

			args = new Object[] { sampleSBModelImpl.getTitle() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_TITLE, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TITLE,
				args);

			args = new Object[] { sampleSBModelImpl.getStartDate() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_STARTDATE, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STARTDATE,
				args);

			args = new Object[] { sampleSBModelImpl.getEndDate() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ENDDATE, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENDDATE,
				args);

			args = new Object[] { sampleSBModelImpl.getSamplesbBooleanStat() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBBOOLEANSTAT,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBBOOLEANSTAT,
				args);

			args = new Object[] { sampleSBModelImpl.getSamplesbDateTime() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDATETIME, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDATETIME,
				args);

			args = new Object[] { sampleSBModelImpl.getSamplesbDocument() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDOCUMENT, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENT,
				args);

			args = new Object[] { sampleSBModelImpl.getSamplesbDocumentLibrary() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDOCUMENTLIBRARY,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENTLIBRARY,
				args);

			args = new Object[] { sampleSBModelImpl.getSamplesbDouble() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDOUBLE, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOUBLE,
				args);

			args = new Object[] { sampleSBModelImpl.getSamplesbInteger() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBINTEGER, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBINTEGER,
				args);

			args = new Object[] { sampleSBModelImpl.getSamplesbRichText() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBRICHTEXT, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBRICHTEXT,
				args);

			args = new Object[] { sampleSBModelImpl.getSamplesbText() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBTEXT, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBTEXT,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { sampleSBModelImpl.getOriginalUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { sampleSBModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalUuid(),
						sampleSBModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						sampleSBModelImpl.getUuid(),
						sampleSBModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalCompanyId(),
						sampleSBModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);

				args = new Object[] {
						sampleSBModelImpl.getCompanyId(),
						sampleSBModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalGroupId(),
						sampleSBModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);

				args = new Object[] {
						sampleSBModelImpl.getGroupId(),
						sampleSBModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalCompanyId(),
						sampleSBModelImpl.getOriginalUserId(),
						sampleSBModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_U_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S,
					args);

				args = new Object[] {
						sampleSBModelImpl.getCompanyId(),
						sampleSBModelImpl.getUserId(),
						sampleSBModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_U_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalGroupId(),
						sampleSBModelImpl.getOriginalUserId(),
						sampleSBModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S,
					args);

				args = new Object[] {
						sampleSBModelImpl.getGroupId(),
						sampleSBModelImpl.getUserId(),
						sampleSBModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalUserId(),
						sampleSBModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S,
					args);

				args = new Object[] {
						sampleSBModelImpl.getUserId(),
						sampleSBModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_S,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalGroupId(),
						sampleSBModelImpl.getOriginalUrlTitle(),
						sampleSBModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_UT_ST, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST,
					args);

				args = new Object[] {
						sampleSBModelImpl.getGroupId(),
						sampleSBModelImpl.getUrlTitle(),
						sampleSBModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_UT_ST, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_UT_ST,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { sampleSBModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalUserId(),
						sampleSBModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERIDGROUPID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID,
					args);

				args = new Object[] {
						sampleSBModelImpl.getUserId(),
						sampleSBModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERIDGROUPID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERIDGROUPID,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { sampleSBModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { sampleSBModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalSamplesbId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBID,
					args);

				args = new Object[] { sampleSBModelImpl.getSamplesbId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBID,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TITLE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TITLE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TITLE,
					args);

				args = new Object[] { sampleSBModelImpl.getTitle() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TITLE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TITLE,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STARTDATE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalStartDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_STARTDATE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STARTDATE,
					args);

				args = new Object[] { sampleSBModelImpl.getStartDate() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_STARTDATE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STARTDATE,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENDDATE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalEndDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENDDATE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENDDATE,
					args);

				args = new Object[] { sampleSBModelImpl.getEndDate() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENDDATE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENDDATE,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBBOOLEANSTAT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalSamplesbBooleanStat()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBBOOLEANSTAT,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBBOOLEANSTAT,
					args);

				args = new Object[] { sampleSBModelImpl.getSamplesbBooleanStat() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBBOOLEANSTAT,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBBOOLEANSTAT,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDATETIME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalSamplesbDateTime()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDATETIME,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDATETIME,
					args);

				args = new Object[] { sampleSBModelImpl.getSamplesbDateTime() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDATETIME,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDATETIME,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalSamplesbDocument()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDOCUMENT,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENT,
					args);

				args = new Object[] { sampleSBModelImpl.getSamplesbDocument() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDOCUMENT,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENT,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENTLIBRARY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalSamplesbDocumentLibrary()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDOCUMENTLIBRARY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENTLIBRARY,
					args);

				args = new Object[] {
						sampleSBModelImpl.getSamplesbDocumentLibrary()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDOCUMENTLIBRARY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOCUMENTLIBRARY,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOUBLE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalSamplesbDouble()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDOUBLE,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOUBLE,
					args);

				args = new Object[] { sampleSBModelImpl.getSamplesbDouble() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBDOUBLE,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBDOUBLE,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBINTEGER.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalSamplesbInteger()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBINTEGER,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBINTEGER,
					args);

				args = new Object[] { sampleSBModelImpl.getSamplesbInteger() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBINTEGER,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBINTEGER,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBRICHTEXT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalSamplesbRichText()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBRICHTEXT,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBRICHTEXT,
					args);

				args = new Object[] { sampleSBModelImpl.getSamplesbRichText() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBRICHTEXT,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBRICHTEXT,
					args);
			}

			if ((sampleSBModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBTEXT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sampleSBModelImpl.getOriginalSamplesbText()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBTEXT, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBTEXT,
					args);

				args = new Object[] { sampleSBModelImpl.getSamplesbText() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SAMPLESBTEXT, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SAMPLESBTEXT,
					args);
			}
		}

		entityCache.putResult(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
			SampleSBImpl.class, sampleSB.getPrimaryKey(), sampleSB, false);

		clearUniqueFindersCache(sampleSBModelImpl, false);
		cacheUniqueFindersCache(sampleSBModelImpl);

		sampleSB.resetOriginalValues();

		return sampleSB;
	}

	protected SampleSB toUnwrappedModel(SampleSB sampleSB) {
		if (sampleSB instanceof SampleSBImpl) {
			return sampleSB;
		}

		SampleSBImpl sampleSBImpl = new SampleSBImpl();

		sampleSBImpl.setNew(sampleSB.isNew());
		sampleSBImpl.setPrimaryKey(sampleSB.getPrimaryKey());

		sampleSBImpl.setUuid(sampleSB.getUuid());
		sampleSBImpl.setSamplesbId(sampleSB.getSamplesbId());
		sampleSBImpl.setTitle(sampleSB.getTitle());
		sampleSBImpl.setStartDate(sampleSB.getStartDate());
		sampleSBImpl.setEndDate(sampleSB.getEndDate());
		sampleSBImpl.setSamplesbBooleanStat(sampleSB.isSamplesbBooleanStat());
		sampleSBImpl.setSamplesbDateTime(sampleSB.getSamplesbDateTime());
		sampleSBImpl.setSamplesbDocument(sampleSB.getSamplesbDocument());
		sampleSBImpl.setFolderDLId(sampleSB.getFolderDLId());
		sampleSBImpl.setSamplesbDocumentLibrary(sampleSB.getSamplesbDocumentLibrary());
		sampleSBImpl.setSamplesbDouble(sampleSB.getSamplesbDouble());
		sampleSBImpl.setSamplesbInteger(sampleSB.getSamplesbInteger());
		sampleSBImpl.setSamplesbRichText(sampleSB.getSamplesbRichText());
		sampleSBImpl.setSamplesbText(sampleSB.getSamplesbText());
		sampleSBImpl.setGroupId(sampleSB.getGroupId());
		sampleSBImpl.setCompanyId(sampleSB.getCompanyId());
		sampleSBImpl.setUserId(sampleSB.getUserId());
		sampleSBImpl.setUserName(sampleSB.getUserName());
		sampleSBImpl.setCreateDate(sampleSB.getCreateDate());
		sampleSBImpl.setModifiedDate(sampleSB.getModifiedDate());
		sampleSBImpl.setUrlTitle(sampleSB.getUrlTitle());
		sampleSBImpl.setSamplesbTitleName(sampleSB.getSamplesbTitleName());
		sampleSBImpl.setSamplesbSummaryName(sampleSB.getSamplesbSummaryName());
		sampleSBImpl.setStatus(sampleSB.getStatus());
		sampleSBImpl.setStatusByUserId(sampleSB.getStatusByUserId());
		sampleSBImpl.setStatusByUserName(sampleSB.getStatusByUserName());
		sampleSBImpl.setStatusDate(sampleSB.getStatusDate());

		return sampleSBImpl;
	}

	/**
	 * Returns the sample sb with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the sample sb
	 * @return the sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSampleSBException {
		SampleSB sampleSB = fetchByPrimaryKey(primaryKey);

		if (sampleSB == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSampleSBException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return sampleSB;
	}

	/**
	 * Returns the sample sb with the primary key or throws a {@link NoSuchSampleSBException} if it could not be found.
	 *
	 * @param samplesbId the primary key of the sample sb
	 * @return the sample sb
	 * @throws NoSuchSampleSBException if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB findByPrimaryKey(long samplesbId)
		throws NoSuchSampleSBException {
		return findByPrimaryKey((Serializable)samplesbId);
	}

	/**
	 * Returns the sample sb with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the sample sb
	 * @return the sample sb, or <code>null</code> if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
				SampleSBImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		SampleSB sampleSB = (SampleSB)serializable;

		if (sampleSB == null) {
			Session session = null;

			try {
				session = openSession();

				sampleSB = (SampleSB)session.get(SampleSBImpl.class, primaryKey);

				if (sampleSB != null) {
					cacheResult(sampleSB);
				}
				else {
					entityCache.putResult(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
						SampleSBImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
					SampleSBImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return sampleSB;
	}

	/**
	 * Returns the sample sb with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samplesbId the primary key of the sample sb
	 * @return the sample sb, or <code>null</code> if a sample sb with the primary key could not be found
	 */
	@Override
	public SampleSB fetchByPrimaryKey(long samplesbId) {
		return fetchByPrimaryKey((Serializable)samplesbId);
	}

	@Override
	public Map<Serializable, SampleSB> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SampleSB> map = new HashMap<Serializable, SampleSB>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SampleSB sampleSB = fetchByPrimaryKey(primaryKey);

			if (sampleSB != null) {
				map.put(primaryKey, sampleSB);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
					SampleSBImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (SampleSB)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SAMPLESB_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (SampleSB sampleSB : (List<SampleSB>)q.list()) {
				map.put(sampleSB.getPrimaryKeyObj(), sampleSB);

				cacheResult(sampleSB);

				uncachedPrimaryKeys.remove(sampleSB.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SampleSBModelImpl.ENTITY_CACHE_ENABLED,
					SampleSBImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the sample sbs.
	 *
	 * @return the sample sbs
	 */
	@Override
	public List<SampleSB> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sample sbs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @return the range of sample sbs
	 */
	@Override
	public List<SampleSB> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the sample sbs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sample sbs
	 */
	@Override
	public List<SampleSB> findAll(int start, int end,
		OrderByComparator<SampleSB> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sample sbs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SampleSBModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sample sbs
	 * @param end the upper bound of the range of sample sbs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of sample sbs
	 */
	@Override
	public List<SampleSB> findAll(int start, int end,
		OrderByComparator<SampleSB> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<SampleSB> list = null;

		if (retrieveFromCache) {
			list = (List<SampleSB>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SAMPLESB);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SAMPLESB;

				if (pagination) {
					sql = sql.concat(SampleSBModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SampleSB>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the sample sbs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SampleSB sampleSB : findAll()) {
			remove(sampleSB);
		}
	}

	/**
	 * Returns the number of sample sbs.
	 *
	 * @return the number of sample sbs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SAMPLESB);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SampleSBModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the sample sb persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SampleSBImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SAMPLESB = "SELECT sampleSB FROM SampleSB sampleSB";
	private static final String _SQL_SELECT_SAMPLESB_WHERE_PKS_IN = "SELECT sampleSB FROM SampleSB sampleSB WHERE samplesbId IN (";
	private static final String _SQL_SELECT_SAMPLESB_WHERE = "SELECT sampleSB FROM SampleSB sampleSB WHERE ";
	private static final String _SQL_COUNT_SAMPLESB = "SELECT COUNT(sampleSB) FROM SampleSB sampleSB";
	private static final String _SQL_COUNT_SAMPLESB_WHERE = "SELECT COUNT(sampleSB) FROM SampleSB sampleSB WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "sampleSB.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SampleSB exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SampleSB exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SampleSBPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}