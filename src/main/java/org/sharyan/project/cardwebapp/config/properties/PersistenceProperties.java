package org.sharyan.project.cardwebapp.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.jdbc")
public class PersistenceProperties {

    private String driverClassName;

    private String url;

    private String username;

    private String password;

    private Hibernate hibernate;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Hibernate getHibernate() {
        return hibernate;
    }

    public void setHibernate(Hibernate hibernate) {
        this.hibernate = hibernate;
    }

    public static class Hibernate {

        private String dialect;

        private boolean showSql;

        private HBM2DDL hbm2ddl;

        public String getDialect() {
            return dialect;
        }

        public void setDialect(String dialect) {
            this.dialect = dialect;
        }

        public boolean isShowSql() {
            return showSql;
        }

        public void setShowSql(boolean showSql) {
            this.showSql = showSql;
        }

        public HBM2DDL getHbm2ddl() {
            return hbm2ddl;
        }

        public void setHbm2ddl(HBM2DDL hbm2ddl) {
            this.hbm2ddl = hbm2ddl;
        }

        public static class HBM2DDL {
            private String auto;

            public String getAuto() {
                return auto;
            }

            public void setAuto(String auto) {
                this.auto = auto;
            }
        }
    }


}
