package com.github.mgurov.jdbcplayground;

import org.junit.Ignore;

/**
 * Test talking directly to a postgress database
 */
@Ignore
public class LogDaoHibernateIT extends LogDaoIT {

    @Override
    protected LogDao dao() {
        return new LogDaoHiberJpa();
    }
}
