SELECT * FROM MEMBER;

select nvl(max(id)+1, 1) from member

------------------------------------------------------------------------------------------------

select * from FATHER;

SELECT * FROM MINISTER;

SELECT * FROM CHILD;

------------------------------------------------------------------------------------------------

SELECT * FROM MINISTER_CHILD_ASSIGNED;

------------------------------------------------------------------------------------------------

SELECT * FROM SECTION;

SELECT * FROM FAMILY;

SELECT * FROM YEAR;

SELECT * FROM CLASS;

------------------------------------------------------------------------------------------------

SELECT * FROM CHILD_MINISTRY_ATTENDANCE;

delete CHILD_MINISTRY_ATTENDANCE where action_date = to_date ('06-11-2015', 'dd-mm-yyyy')

delete CHILD_MINISTRY_ATTENDANCE where action_date = to_date ('13-11-2015', 'dd-mm-yyyy')

SELECT * FROM CHILD_MASS_ATTENDANCE;

SELECT * FROM CHILD_FOLLOWUP;

delete CHILD_FOLLOWUP where action_date = to_date ('06-11-2015', 'dd-mm-yyyy')

delete CHILD_FOLLOWUP where action_date = to_date ('13-11-2015', 'dd-mm-yyyy')

SELECT * FROM MINISTER_EVALUATION;

update MINISTER_EVALUATION set attendants_count = 0, followup_count = 0, attendants_followup_count = 0 where action_date = to_date ('06-11-2015', 'dd-mm-yyyy')

update MINISTER_EVALUATION set attendants_count = 0, followup_count = 0, attendants_followup_count = 0 where action_date = to_date ('13-11-2015', 'dd-mm-yyyy')

------------------------------------------------------------------------------------------------

SELECT * FROM CONTACT;

SELECT * FROM PROFILE;

------------------------------------------------------------------------------------------------

SELECT * FROM MARITAL_STATUS;

SELECT * FROM SPECIALIZATION;

SELECT * FROM FOLLOWUP_METHODS;

SELECT * FROM FOLLOWUP_COMMENT;

SELECT * FROM MEMBER_TYPE;

------------------------------------------------------------------------------------------------

SELECT * FROM IMAGES;

------------------------------------------------------------------------------------------------

SELECT * FROM DISTRICTS;

SELECT * FROM REGIONS;

SELECT * FROM STREETS;

------------------------------------------------------------------------------------------------

SELECT * FROM USERS;

SELECT * FROM USERS_PROFILE;

------------------------------------------------------------------------------------------------

SELECT * FROM NVPAIR;

------------------------------------------------------------------------------------------------