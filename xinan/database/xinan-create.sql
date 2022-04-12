create table sys_menu (
   id                   CHAR(36)             not null,
   menu_name            VARCHAR(100)         null,
   menu_url             VARCHAR(500)         null,
   menu_icon            VARCHAR(50)          null,
   menu_sort            INT4                 null default 99,
   menu_level           INT4                 null default 1,
   p_menu_id            CHAR(36)             null,
   remark               VARCHAR(500)         null,
   constraint PK_SYS_MENU primary key (id)
);


create table sys_role_menu_rel (
   id                   CHAR(36)             not null,
   role_id              CHAR(36)             null,
   menu_id              CHAR(36)             null,
   constraint PK_SYS_ROLE_MENU_REL primary key (id)
);


create table sys_role (
   id                   CHAR(36)             not null,
   role_name            VARCHAR(100)         null,
   role_code            VARCHAR(100)         null,
   role_type            char(1)              null default '1',
   remark               VARCHAR(500)         null,
   constraint PK_SYS_ROLE primary key (id)
);

create table sys_user_role_rel (
   id                   CHAR(36)             not null,
   user_id              CHAR(36)             null,
   role_id              CHAR(36)             null,
   constraint PK_SYS_USER_ROLE_REL primary key (id)
);

create table sys_user (
   id                   CHAR(36)             not null,
   user_code            VARCHAR(100)         null,
   user_passwd          VARCHAR(100)         null,
   user_real_name       VARCHAR(100)         null,
   user_type            char(1)              null default '3',
   user_state           char(1)              null default '1',
   create_date          DATE                 null,
   remark               VARCHAR(500)         null,
   doctor_title         VARCHAR(100)         null,
   doctor_flag          char(1)              null default '1',
   constraint PK_SYS_USER primary key (id)
);

comment on table sys_user is
'由于医生表的信息很少，也保存到此处';

comment on column sys_user.user_passwd is
'从SSO获取，同步方式待定';

comment on column sys_user.user_type is
'用户名称';

create table base_user_org_rel (
   id                   CHAR(36)             not null,
   user_id              CHAR(36)             null,
   org_id               CHAR(36)             null,
   constraint PK_BASE_USER_ORG_REL primary key (id)
);

create table base_org (
   id                   CHAR(36)             not null,
   org_name             VARCHAR(100)         null,
   org_simple_name      VARCHAR(100)         null,
   org_phone            VARCHAR(100)         null,
   org_address          VARCHAR(500)         null,
   create_date          DATE                 null,
   remark               VARCHAR(500)         null,
   constraint PK_BASE_ORG primary key (id)
);

comment on table base_org is
'存储医院信息';



create table base_knowledge_catagory (
   id                   CHAR(36)             not null,
   cat_name             VARCHAR(100)         null,
   cat_sort             INT4                 null,
   cat_icon_url         VARCHAR(200)         null,
   cat_description      VARCHAR(500)         null,
   cat_type             CHAR(1)              null default '1',
   cat_state            CHAR(1)              null default '1',
   create_date          DATE                 null,
   remark               VARCHAR(500)         null,
   constraint PK_BASE_KNOWLEDGE_CATAGORY primary key (id)
);

comment on column base_knowledge_catagory.cat_type is
'0-特殊不可删除；1-普通';



create table biz_train (
   id                   CHAR(36)             not null,
   train_subject        VARCHAR(200)         null,
   train_place          VARCHAR(200)         null,
   start_time           TIMESTAMP            null,
   end_time             TIMESTAMP            null,
   train_state          CHAR(1)              null default '1',
   object_type          CHAR(1)              null default '1',
   train_content        TEXT                 null,
   update_time          TIMESTAMP            null,
   create_date          DATE                 null,
   create_user_name     VARCHAR(100)         null,
   create_user_id       CHAR(36)             null,
   remark               VARCHAR(500)         null,
   constraint PK_BIZ_TRAIN primary key (id)
);

comment on column biz_train.train_state is
'1-草稿、2-进行中、3-已结束、4-已取消';

comment on column biz_train.object_type is
'1-全公开，2-指定医院，3-指定医生';

create table biz_train_user (
   id                   CHAR(36)             not null,
   train_id             CHAR(36)             null,
   user_id              CHAR(36)             null,
   remark               VARCHAR(500)         null,
   constraint PK_BIZ_TRAIN_USER primary key (id)
);

comment on table biz_train_user is
'记录该培训所涉及的医生';


create table biz_train_hospital (
   id                   CHAR(36)             not null,
   train_id             CHAR(36)             null,
   org_id               CHAR(36)             null,
   remark               VARCHAR(500)         null,
   constraint PK_BIZ_TRAIN_HOSPITAL primary key (id)
);

comment on table biz_train_hospital is
'记录该培训所涉及的医院';


create table biz_knowledge (
   id                   CHAR(36)             not null,
   repos_title          VARCHAR(100)         null,
   cat_id               CHAR(36)             null,
   repos_state          CHAR(1)              null default '1',
   commend_flag         CHAR(1)              null default '0',
   repos_url            VARCHAR(200)         null,
   publish_date         DATE                 null,
   repos_author         VARCHAR(100)         null,
   repos_digest         VARCHAR(2000)        null,
   repos_drug           VARCHAR(2000)        null,
   create_time          TIMESTAMP            null,
   create_user_name     VARCHAR(100)         null,
   create_user_id       CHAR(36)             null,
   update_time          TIMESTAMP            null,
   remark               VARCHAR(500)         null,
   constraint PK_BIZ_KNOWLEDGE primary key (id)
);

comment on column biz_knowledge.repos_state is
'0-未上架；1-已上架';

comment on column biz_knowledge.commend_flag is
'0-不推荐；1-推荐';


create table biz_knowledge_log (
   id                   CHAR(36)             not null,
   repos_id             CHAR(36)             null,
   change_content       VARCHAR(500)         null,
   create_time          TIMESTAMP            null,
   create_user_name     VARCHAR(100)         null,
   create_user_id       CHAR(36)             null,
   remark               VARCHAR(500)         null,
   constraint PK_BIZ_KNOWLEDGE_LOG primary key (id)
);


create table biz_knowledge_content (
   id                   CHAR(36)             not null,
   repos_id             CHAR(36)             null,
   section_name         VARCHAR(100)         null,
   section_level        INT4                 null default 1,
   p_section_id         CHAR(36)             null,
   section_sort         INT4                 null default 1,
   section_content      TEXT                 null,
   create_time          TIMESTAMP            null,
   create_user_name     VARCHAR(100)         null,
   create_user_id       CHAR(36)             null,
   update_time          TIMESTAMP            null,
   remark               VARCHAR(500)         null,
   constraint PK_BIZ_KNOWLEDGE_CONTENT primary key (id)
);

create table biz_knowledge_mark (
   id                   CHAR(36)             not null,
   section_id           CHAR(36)             null,
   create_user_id       CHAR(36)             null,
   create_user_name     VARCHAR(100)         null,
   create_time          TIMESTAMP            null,
   mark_content         VARCHAR(500)         null,
   mark_site            VARCHAR(100)         null,
   remark               VARCHAR(500)         null,
   constraint PK_BIZ_KNOWLEDGE_MARK primary key (id)
);


create table biz_knowledge_collect (
   id                   CHAR(36)             not null,
   repos_id             CHAR(36)             null,
   collect_user_id      CHAR(36)             null,
   collect_user_name    VARCHAR(100)         null,
   collect_time         TIMESTAMP            null,
   remark               VARCHAR(500)         null,
   constraint PK_BIZ_KNOWLEDGE_COLLECT primary key (id)
);

create table biz_feedback (
   id                   CHAR(36)             not null,
   repos_id             CHAR(36)             null,
   section_id           CHAR(36)             null,
   select_content       VARCHAR(500)         null,
   feedback_content     VARCHAR(500)         null,
   deal_state           CHAR(1)              null default '1',
   deal_content         VARCHAR(500)         null,
   feedback_user_id     CHAR(36)             null,
   feedback_user_name   VARCHAR(100)         null,
   feedback_time        TIMESTAMP            null,
   deal_user_id         CHAR(36)             null,
   deal_user_name       VARCHAR(100)         null,
   deal_time            TIMESTAMP            null,
   remark               VARCHAR(500)         null,
   constraint PK_BIZ_FEEDBACK primary key (id)
);

comment on column biz_feedback.deal_state is
'1-待处理；2-跟进中；3-已处理';


create table biz_drug (
   id                   CHAR(36)             not null,
   repos_id             CHAR(36)             null,
   section_id           CHAR(36)             null,
   select_content       VARCHAR(500)         null,
   feedback_content     VARCHAR(500)         null,
   deal_state           CHAR(1)              null default '1',
   deal_content         VARCHAR(500)         null,
   feedback_user_id     CHAR(36)             null,
   feedback_user_name   VARCHAR(100)         null,
   feedback_time        TIMESTAMP            null,
   deal_user_id         CHAR(36)             null,
   deal_user_name       VARCHAR(100)         null,
   deal_time            TIMESTAMP            null,
   remark               VARCHAR(500)         null,
   constraint PK_BIZ_FEEDBACK primary key (id)
);