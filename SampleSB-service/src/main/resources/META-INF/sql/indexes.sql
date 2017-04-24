create index IX_16734D69 on SampleSB_SampleSB (companyId, status);
create index IX_E8F098A3 on SampleSB_SampleSB (companyId, userId, status);
create index IX_6692E834 on SampleSB_SampleSB (endDate);
create index IX_B8BDF6AB on SampleSB_SampleSB (groupId, status);
create unique index IX_6BD8962 on SampleSB_SampleSB (groupId, urlTitle[$COLUMN_LENGTH:75$]);
create index IX_826DFE5 on SampleSB_SampleSB (groupId, userId, status);
create index IX_559F606E on SampleSB_SampleSB (samplesbBooleanStat);
create index IX_AA7DDE6D on SampleSB_SampleSB (samplesbDateTime);
create index IX_591D1FED on SampleSB_SampleSB (samplesbDocument);
create index IX_43173F2 on SampleSB_SampleSB (samplesbDocumentLibrary[$COLUMN_LENGTH:75$]);
create index IX_AF358D23 on SampleSB_SampleSB (samplesbDouble);
create index IX_86C23ED on SampleSB_SampleSB (samplesbId);
create index IX_84E3FE90 on SampleSB_SampleSB (samplesbInteger);
create index IX_6131177B on SampleSB_SampleSB (samplesbRichText[$COLUMN_LENGTH:75$]);
create index IX_B0EB077F on SampleSB_SampleSB (samplesbText[$COLUMN_LENGTH:75$]);
create index IX_7475003B on SampleSB_SampleSB (startDate);
create index IX_4E7CA5E3 on SampleSB_SampleSB (title[$COLUMN_LENGTH:75$]);
create unique index IX_9C8ADA22 on SampleSB_SampleSB (urlTitle[$COLUMN_LENGTH:75$]);
create index IX_202B4ACB on SampleSB_SampleSB (userId, groupId);
create index IX_EF64A0A5 on SampleSB_SampleSB (userId, status);
create index IX_FCCF9839 on SampleSB_SampleSB (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_93A2D8FB on SampleSB_SampleSB (uuid_[$COLUMN_LENGTH:75$], groupId);