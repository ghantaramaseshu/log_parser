DROP TABLE log_data IF EXISTS;

create table log_data (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 loged_time TIMESTAMP NOT NULL,
 ip VARCHAR(15) NOT NULL,
 method VARCHAR(25),
 protocol VARCHAR(25),
 status_code int NOT NULL,
 user_aggent TEXT 
);