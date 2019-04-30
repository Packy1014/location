FROM mysql:5.7.26

COPY conf/my.cnf /etc/mysql/conf.d
COPY sql/init.sql /var/lib/mysql
COPY script/init_db.sh /docker-entrypoint-initdb.d/