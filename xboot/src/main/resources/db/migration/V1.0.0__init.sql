/*
 Navicat Premium Data Transfer

 Source Server         : postgres
 Source Server Type    : PostgreSQL
 Source Server Version : 100005
 Source Host           : localhost:5432
 Source Catalog        : postgres
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100005
 File Encoding         : 65001

 Date: 15/11/2018 21:11:21
*/


-- ----------------------------
-- Table structure for pom_object
-- ----------------------------
DROP TABLE IF EXISTS "pom_object";
CREATE TABLE "pom_object" (
  "uid" varchar(19) COLLATE "pg_catalog"."default" NOT NULL,
  "is_retired" bit(1),
  "tags" varchar(256) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "pom_object" OWNER TO "postgres";

-- ----------------------------
-- Records of pom_object
-- ----------------------------
BEGIN;
INSERT INTO "pom_object" VALUES ('123456789qwertyuiop', '1', '1qwwqqqqqqqqqqqqqqq,3333333333333333333');
COMMIT;

-- ----------------------------
-- Table structure for workspace
-- ----------------------------
DROP TABLE IF EXISTS "workspace";
CREATE TABLE "workspace" (
  "uid" varchar(19) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(32) COLLATE "pg_catalog"."default",
  "age" int2
)
;
ALTER TABLE "workspace" OWNER TO "postgres";

-- ----------------------------
-- Records of workspace
-- ----------------------------
BEGIN;
INSERT INTO "workspace" VALUES ('123456789qwertyuiop', 'haha', 18);
COMMIT;

-- ----------------------------
-- Primary Key structure for table pom_object
-- ----------------------------
ALTER TABLE "pom_object" ADD CONSTRAINT "pom_object_pkey" PRIMARY KEY ("uid");

-- ----------------------------
-- Primary Key structure for table workspace
-- ----------------------------
ALTER TABLE "workspace" ADD CONSTRAINT "workspace_pkey" PRIMARY KEY ("uid");

-- ----------------------------
-- Foreign Keys structure for table workspace
-- ----------------------------
ALTER TABLE "workspace" ADD CONSTRAINT "workspace_pom_pk1" FOREIGN KEY ("uid") REFERENCES "pom_object" ("uid") ON DELETE CASCADE ON UPDATE NO ACTION;
