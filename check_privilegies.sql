
SELECT
      r.rolname,
      ARRAY(SELECT b.rolname
            FROM pg_catalog.pg_auth_members m
            JOIN pg_catalog.pg_roles b ON (m.roleid = b.oid)
            WHERE m.member = r.oid) as memberof
FROM pg_catalog.pg_roles r
WHERE r.rolname NOT IN ('pg_signal_backend','rds_iam',
                        'rds_replication','rds_superuser',
                        'rdsadmin','rdsrepladmin')
ORDER BY 1;



-----------------------------
SELECT grantee, privilege_type
FROM information_schema.role_table_grants
WHERE table_name='turno'

----------------------drop user -----------------
REASSIGN OWNED BY <old> TO postgres;
DROP OWNED BY <old>;
drop user <old>;

-------grant all privilegies
GRANT USAGE ON SCHEMA public TO my_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO my_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO my_user;
