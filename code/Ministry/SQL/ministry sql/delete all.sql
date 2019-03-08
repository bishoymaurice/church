--delete all members
BEGIN
delete MINISTER;
delete MINISTERED;
delete FATHER;
delete CONTACT;
delete PROFILE;
delete MEMBER;
commit;
EXCEPTION
   WHEN NO_DATA_FOUND THEN
      NULL;
   WHEN OTHERS THEN
      NULL;
END;

