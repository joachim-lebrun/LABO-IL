SELECT *
FROM OFFICIAL_DOC o
       JOIN DEMAND d on d.DEMAND_ID = o.DEMAND_ID
where d.CREATOR = ?;


SELECT *
FROM DEMAND d
where SERVICE_ID = 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'
  AND NOT EXISTS(SELECT * FROM EVENT e WHERE e.DEMAND_ID = d.DEMAND_ID
                                         AND (e.STATUS like 'ACCEPTED' OR e.STATUS like 'REFUSED'));
