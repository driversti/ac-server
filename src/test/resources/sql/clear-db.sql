DO
$$
    BEGIN
        EXECUTE
            (SELECT 'truncate table ' || string_agg(format('%I.%I', schemaname, tablename), ',') ||
                    ' cascade'
             FROM pg_tables
             WHERE schemaname = 'public'
               AND tablename != 'flyway_schema_history'
            );
    END;
$$