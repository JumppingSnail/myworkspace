INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('a2cea832-c20d-454b-975d-fd41b3e46412', '新安项目', null, null, 1, 0, null, null);

--------------------------------------
INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('114a283d-3664-405f-a2fb-39cbf97ed85b', '前端页面', '', 'fa fa-mobile', 1, 1, 'a2cea832-c20d-454b-975d-fd41b3e46412', '手机端页面配置在此下');

--------------------------------------
INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('6b4f2f79-8026-4918-86f5-84446b71ddf2', '账号管理', '', 'fa fa-user', 2, 1, 'a2cea832-c20d-454b-975d-fd41b3e46412', '');

INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('336d2a88-e746-4238-87b7-a53c968dd4ff', '账号管理', 'sys/user-list.html', '', 1, 2, '6b4f2f79-8026-4918-86f5-84446b71ddf2', '');

INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('a49175dc-261c-47fc-af3a-f3ff4722ebaf', '角色管理', 'sys/role-list.html', '', 2, 2, '6b4f2f79-8026-4918-86f5-84446b71ddf2', '');



INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('a5ef3c7d-abc3-4558-9ff8-6e8acc214809', '机构管理', '', 'fa fa-sitemap', 3, 1, 'a2cea832-c20d-454b-975d-fd41b3e46412', '');

INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('10ddff99-8095-40e7-9580-1ee05ba83207', '医院管理', 'base/hospital-list.html', '', 1, 2, 'a5ef3c7d-abc3-4558-9ff8-6e8acc214809', '');




INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('44525850-be7b-46c0-9099-a3988a591b22', '知识库', '', 'fa fa-book', 4, 1, 'a2cea832-c20d-454b-975d-fd41b3e46412', '');

INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('833a477d-07e7-4671-90a5-69a204b3e2a0', '栏目管理', 'repos/catagory-list.html', '', 1, 2, '44525850-be7b-46c0-9099-a3988a591b22', '');

INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('a125f40f-f8d8-456d-a3dc-2d8442441f53', '知识库管理', 'repos/repository-list.html', '', 2, 2, '44525850-be7b-46c0-9099-a3988a591b22', '');

INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('b3ec4dfd-79a3-4308-901f-33f1ef176229', '我发布的知识', 'repos/my-knowledge-list.html', '', 3, 2, '44525850-be7b-46c0-9099-a3988a591b22', '');




INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('939656e8-8f35-40dd-a8e6-4cd02a902e53', '培训管理', '', 'fa fa-id-card-o', 5, 1, 'a2cea832-c20d-454b-975d-fd41b3e46412', '');

INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('cdda0926-3fbb-4927-a211-e219e855f53e', '培训管理', 'train/train-list.html', '', 1, 2, '939656e8-8f35-40dd-a8e6-4cd02a902e53', '');




INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('be450970-eed8-4321-8460-d75e7e0eb129', '其他功能', '', 'fa fa-th-large', 6, 1, 'a2cea832-c20d-454b-975d-fd41b3e46412', '');

INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('5bfd7bd5-358f-4da9-ad1c-20e81dff11cf', '个人信息维护', 'other/user-info.html', '', 1, 2, 'be450970-eed8-4321-8460-d75e7e0eb129', '');

INSERT INTO xinandb.sys_menu (id, menu_name, menu_url, menu_icon, menu_sort, menu_level, p_menu_id, remark)
VALUES('eeb35326-f2a3-4223-8b0a-3b391bd7109a', '用户反馈', 'other/feedback-list.html', '', 2, 2, 'be450970-eed8-4321-8460-d75e7e0eb129', '');




--------------------------------------
INSERT INTO xinandb.sys_user (id, user_code, user_passwd, user_real_name, user_type, user_state, create_date)
VALUES('617a66e2-6ab7-4bfc-b1ed-9ba9a2d67a28', 'super-admin', 'c4ca4238a0b923820dcc509a6f75849b', '超级管理员', '0'::bpchar, '1'::bpchar, '2030-01-01');



INSERT INTO xinandb.sys_role (id, role_name, role_type, remark)
VALUES('8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', '超级管理员', '0'::bpchar, '超管角色，拥有系统所有操作权限');

INSERT INTO xinandb.sys_role (id, role_name, role_type, remark)
VALUES('9f23ebca-7bde-490a-8842-a3691cdd9707', '医生', '0'::bpchar, '医生角色，主要登陆前端H5界面');


INSERT INTO xinandb.sys_user_role_rel(id, user_id, role_id)
VALUES('119bb997-84c5-41f0-b0a6-36298f70d85d', '617a66e2-6ab7-4bfc-b1ed-9ba9a2d67a28', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9');


---------------------------------------

INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('df160dfb-8b5d-45ed-8a83-9b27f380a32a', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', 'a2cea832-c20d-454b-975d-fd41b3e46412');

INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('6aab2bc2-e93d-4af6-904e-6cf5d87cccab', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', '6b4f2f79-8026-4918-86f5-84446b71ddf2');


INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('ebc625a9-b5fc-43f7-ba47-9f84fd31f900', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', '336d2a88-e746-4238-87b7-a53c968dd4ff');

INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('d54c2b9b-14c4-4cbe-9737-b5aedf7ee046', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', 'a49175dc-261c-47fc-af3a-f3ff4722ebaf');


INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('3261691c-c1c8-4712-82c5-01d3193716a7', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', 'a5ef3c7d-abc3-4558-9ff8-6e8acc214809');

INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('f8e4a5e9-7df4-457d-953b-9da9835d4cba', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', '10ddff99-8095-40e7-9580-1ee05ba83207');


INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('ec50f167-eaad-443e-ad6c-276eef47d63a', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', '44525850-be7b-46c0-9099-a3988a591b22');

INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('0295c678-a282-4702-bc35-3687592d415c', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', '833a477d-07e7-4671-90a5-69a204b3e2a0');


INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('f9c076e9-ece6-44c6-8d9c-834c27d5ac9f', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', 'a125f40f-f8d8-456d-a3dc-2d8442441f53');

INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('1051c2ff-f9cc-46ac-a83b-7b289940d490', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', 'b3ec4dfd-79a3-4308-901f-33f1ef176229');


INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('d04a3f49-6cf6-4538-a9de-9d6966e9b2ec', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', '939656e8-8f35-40dd-a8e6-4cd02a902e53');

INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('d2603489-42f4-453a-8e2a-134cbb1e65d8', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', 'cdda0926-3fbb-4927-a211-e219e855f53e');

INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('ee6e7726-5be2-43cc-b064-8dd10e4d54a2', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', 'be450970-eed8-4321-8460-d75e7e0eb129');

INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('3f43bcfc-44a0-4269-9dc0-243f433a1ace', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', 'eeb35326-f2a3-4223-8b0a-3b391bd7109a');

INSERT INTO xinandb.sys_role_menu_rel (id, role_id, menu_id)
VALUES('92bf7ac7-408a-41f3-a506-42d8bf96506e', '8240ccd4-675b-4e8e-aa09-5f9de51c5eb9', '5bfd7bd5-358f-4da9-ad1c-20e81dff11cf');





INSERT INTO xinandb.base_param_dict (id, param_category, param_name, param_value, param_sort, remark)
VALUES('3def6bc3-ab07-406e-8f2e-de999e6146c8', 'doctor_title', 'normal-doctor', '医士、医师或住院医师', 1, '医生职称');

INSERT INTO xinandb.base_param_dict (id, param_category, param_name, param_value, param_sort, remark)
VALUES('5eecf0f3-1378-41c5-9e57-ba3082a9bc79', 'doctor_title', 'senior-doctor', '主治医师', 2, '医生职称');

INSERT INTO xinandb.base_param_dict (id, param_category, param_name, param_value, param_sort, remark)
VALUES('4ada28a7-e64e-4fd4-97b9-680e0c18101e', 'doctor_title', 'sub-director-doctor', '副主任医师', 3, '医生职称');

INSERT INTO xinandb.base_param_dict (id, param_category, param_name, param_value, param_sort, remark)
VALUES('656900e1-07a4-4acb-b41c-772a4c00de97', 'doctor_title', 'director-doctor', '主任医师', 4, '医生职称');





INSERT INTO xinandb.base_knowledge_catagory (id, cat_name, cat_sort, cat_icon_url, cat_description, cat_state, create_date, cat_type)
VALUES('444e1ae2-a424-452a-ab2d-bd91e5ba7292', '书籍', 0, '', '', '1'::bpchar, now(), '0'::bpchar);












