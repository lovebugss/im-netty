
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for CHANNELS
-- ----------------------------
DROP TABLE IF EXISTS CHANNELS;
CREATE TABLE CHANNELS (
  ID bigint NOT NULL AUTO_INCREMENT,
  CHANNEL_ID char(11) NOT NULL COMMENT '频道ID',
  CHANNEL_NAME varchar(50) DEFAULT NULL,
  FILTER_TYPE int DEFAULT '0' COMMENT '消息过滤类型, 0: 自动, 1: 黑词过滤',
  EVENT_NOTIFY int DEFAULT NULL COMMENT '是否需要时间通知',
  CHANNEL_LIMIT int DEFAULT '1000' COMMENT '频道限制人数',
  CREATE_TIME timestamp NULL DEFAULT NULL,
  CREATE_USER varchar(255) DEFAULT NULL,
  UPDATE_TIME timestamp NULL DEFAULT NULL,
  UPDATE_USER varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY CHANNEL_ID_INDEX (CHANNEL_ID) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of CHANNELS
-- ----------------------------
BEGIN;
INSERT INTO CHANNELS (ID, CHANNEL_ID, CHANNEL_NAME, FILTER_TYPE, EVENT_NOTIFY, CHANNEL_LIMIT, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER) VALUES (1, 'ch_00000001', NULL, 0, NULL, 1000, '2022-12-26 23:59:06', 'admin', NULL, NULL);
INSERT INTO CHANNELS (ID, CHANNEL_ID, CHANNEL_NAME, FILTER_TYPE, EVENT_NOTIFY, CHANNEL_LIMIT, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER) VALUES (2, 'ch_00000002', NULL, 0, NULL, 1000, '2022-12-26 23:59:11', 'admin', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for CHANNEL_whitelist
-- ----------------------------
DROP TABLE IF EXISTS CHANNEL_WHITELIST;
CREATE TABLE CHANNEL_WHITELIST (
  ID int NOT NULL,
  CHANNEL_ID char(11) DEFAULT NULL,
  USER_ID int DEFAULT NULL,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of CHANNEL_whitelist
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for USER
-- ----------------------------
DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
  ID int NOT NULL,
  USER_NAME varchar(255) DEFAULT NULL,
  USER_ID int DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY USER_USER_ID_INDEX (USER_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of USER
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for WORKER_NODE
-- ----------------------------
DROP TABLE IF EXISTS WORKER_NODE;
CREATE TABLE WORKER_NODE (
  ID bigint NOT NULL AUTO_INCREMENT COMMENT 'auto increment id',
  HOST_NAME varchar(64) NOT NULL COMMENT 'host name',
  PORT varchar(64) NOT NULL COMMENT 'port',
  TYPE int NOT NULL COMMENT 'node type: ACTUAL or CONTAINER',
  LAUNCH_DATE date NOT NULL COMMENT 'launch date',
  MODIFIED timestamp NOT NULL COMMENT 'modified time',
  CREATED timestamp NOT NULL COMMENT 'created time',
  PRIMARY KEY (ID)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='DB WorkerID Assigner for UID Generator';


-- ----------------------------
-- Table structure for channel_blacklist
-- ----------------------------
DROP TABLE IF EXISTS CHANNEL_BLACKLIST;
CREATE TABLE CHANNEL_BLACKLIST (
  ID int NOT NULL,
  CHANNEL_ID char(8) DEFAULT NULL,
  USER_ID int DEFAULT NULL,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of channel_blacklist
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
