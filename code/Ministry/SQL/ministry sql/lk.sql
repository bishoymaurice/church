select * from (select a.minister_id, a.ministered_id from (select minister_id, ministered_id, max(active_date) as l_assign_date from minister_ministered_assigned group by minister_id, ministered_id) a,
(select ministered_id, max(active_date) as l_d_assign_date from minister_ministered_assigned group by ministered_id) b
where a.l_assign_date=b.l_d_assign_date) a where a.minister_id = 5

/* Formatted on 11/21/2014 11:52:17 AM (QP5 v5.139.911.3011) */
  SELECT name
    FROM MEMBER
   WHERE id IN
            (SELECT a.ministered_id
               FROM (SELECT a.minister_id, a.ministered_id
                       FROM (  SELECT minister_id,
                                      ministered_id,
                                      MAX (active_date) AS l_assign_date
                                 FROM minister_ministered_assigned
                             GROUP BY minister_id, ministered_id) a,
                            (  SELECT ministered_id,
                                      MAX (active_date) AS l_d_assign_date
                                 FROM minister_ministered_assigned
                             GROUP BY ministered_id) b
                      WHERE a.l_assign_date = b.l_d_assign_date) a
              WHERE a.minister_id =
                       (SELECT id
                          FROM MEMBER
                         WHERE name = 'minister4'
                               AND TYPE = (SELECT id
                                             FROM member_type
                                            WHERE des = 'minister')))
         AND TYPE = (SELECT id
                       FROM member_type
                      WHERE des = 'ministered')
ORDER BY name

select ministered_id, max(active_date) from minister_ministered_assigned where active_date <= to_date ('21/11/2014', 'dd/mm/yyyy') or active_date like to_date ('21/11/2014', 'dd/mm/yyyy')
group by ministered_id

edit minister_ministered_assigned order by active_date desc