-- --------------------
-- 1. 用户表/学生表
-- --------------------
drop table if exists gc_user;
create table gc_user(
    user_id     bigint(20)    not null auto_increment       comment '用户ID',
  inst_id     bigint(20)    not null                      comment '学院ID',
    login_name  varchar(20)   not null                      comment '登录名',
    user_name   varchar(30)   not null                      comment '用户名',
    user_type   char(1)       not null                      comment '用户类别,0=管理员,1=学生',
    email       varchar(50)   default ''                    comment '邮箱',
    phone       varchar(11)   default ''                    comment '手机号',
    sex         char(1)       not null                      comment '0-男,1-女',
    avatar      varchar(255)  default ''                    comment '头像路径',
    password    varchar(100)  not null                      comment '加密密码串',
    salt        varchar(50)   default ''                    comment '盐值',
    status      char(1)       default '0'                   comment '0=停用,1=正常',
    del_flag    char(1)       default '0'                   comment '0=存在,1=删除',
    login_ip    varchar(16)   default ''                    comment '最后登录IP地址',
    login_date  datetime      default CURRENT_TIMESTAMP     comment '最后登录时间',
    create_time datetime      default CURRENT_TIMESTAMP     comment '用户创建时间',
  primary key (user_id)
) engine=innodb auto_increment=1 charset=utf8 comment='用户表';

insert into gc_user values(1, 1, '16251104235', '卢恒勋', '0', 'xxx@163.com', '15688888888', '0', '', '29c67a30398638269fe600f73a054934', '111111', '1', '0', '127.0.0.1', '2020-02-13 16:07:35', '2020-02-13 16:07:35');
insert into gc_user values(2, 2, '16251104299', '小默', '1', 'xxx@163.com', '15688888888', '1', '', '29c67a30398638269fe600f73a054934', '111111', '1', '0', '127.0.0.1', '2020-02-13 16:07:35', '2020-02-13 16:07:35');

-- --------------------
-- 2. 学院表
-- --------------------
drop table if exists gc_institue;
create table gc_institue(
  inst_id     bigint(20)    not null auto_increment       comment '学院ID',
  inst_name   varchar(50)   default ''                    comment '学院名',
  inst_table  varchar(50)   not null                      comment '学院对应的已选课表名',
  create_time datetime      default CURRENT_TIMESTAMP     comment '创建时间',
  primary key (inst_id)
) engine=innodb auto_increment=1 charset=utf8 comment='学院表';

insert into gc_institue values(1, '信息学院', 'gc_sc_xx', '2020-02-13 16:07:35');
insert into gc_institue values(2, '国际商学院', 'gc_sc_gjs', '2020-02-13 16:07:35');

-- --------------------
-- 3. 课程表 + 课程类型表
-- --------------------
drop table if exists gc_course;
create table gc_course(
  course_id     bigint(20)    not null auto_increment      comment '课程ID',
  course_name   varchar(100)  default ''                   comment '课程名',
  course_type   char(1)       default '0'                  comment '课程类型,0=通识课,1=专业必修课',
  course_credit int(1)        not null                     comment '课程学分',
  course_hours  int(2)        not null                     comment '课程学时',
  primary key (course_id)
) engine=innodb auto_increment=1 charset=utf8 comment='课程表';

insert into gc_course values(1, '大学英语', '1', 3, 32);
insert into gc_course values(2, '大学语文', '1', 2, 32);
insert into gc_course values(3, '数据库原理', '2', 4, 32);
insert into gc_course values(4, '数据结构', '2', 3, 32);
insert into gc_course values(5, 'JavaEE', '3', 4, 32);
insert into gc_course values(6, '计算机系统基础', '3', 2, 32);
insert into gc_course values(7, '项目管理', '4', 4, 32);
insert into gc_course values(8, 'c语言程序设计', '4', 2, 32);
insert into gc_course values(9, '操作系统', '4', 3, 32);
insert into gc_course values(10, '云计算体系架构', '5', 3, 32);
insert into gc_course values(11, '毕业实习', '5', 4, 32);
insert into gc_course values(12, '毕业设计', '5', 5, 32);

drop table if exists gc_course_type;
create table gc_course_type(
  type_id      bigint(20)      not null auto_increment          comment '课程类型ID',
  type_name    varchar (20)    default ''                       comment '类型名',
  create_time  datetime        default CURRENT_TIMESTAMP        comment '创建时间',
  primary key (type_id)
) engine=innodb auto_increment=1 charset=utf8 comment='课程类型表';

insert into gc_course_type (type_id, type_name) values (1, '通识必修课');
insert into gc_course_type (type_id, type_name) values (2, '通识选修课');
insert into gc_course_type (type_id, type_name) values (3, '专业必修课');
insert into gc_course_type (type_id, type_name) values (4, '专业选修课');
insert into gc_course_type (type_id, type_name) values (5, '学科基础课');

-- --------------------
-- 4. 教师表
-- --------------------
drop table if exists gc_teacher;
create table gc_teacher(
  t_id        bigint(20)    not null auto_increment        comment '教师ID',
  t_name      varchar(20)   default ''                     comment '教师姓名',
  inst_id     bigint(20)    not null                       comment '所属学院',
  t_sex       char(1)       default '0'                    comment '0=男',
  t_phone     varchar(11)   default ''                     comment '教师电话',
  t_email     varchar(50)   default ''                     comment '教师邮箱',
  t_education varchar(20)   default ''                     comment '教师学历',
  t_position  varchar(20)   default ''                     comment '教师职位',
  primary key (t_id)
) engine=innodb auto_increment=1 charset=utf8 comment='教师表';

insert into gc_teacher values(1, '陈老师', 1, '0', '17988888888', 'xxx@163.com', '博士研究生', '教授');
insert into gc_teacher values(2, '张老师', 1, '1', '13488888888', 'xxx@163.com', '海归硕士研究生', '副教授');


-- --------------------
-- 5. 排课表
-- --------------------
drop table if exists gc_schedule;
create table gc_schedule(
  sche_id       bigint(20)    not null auto_increment        comment '排课ID',
  teacher_id    bigint(20)    not null                       comment '教师ID',
  teacher_name  varchar(50)   not null                       comment '教师姓名',
  course_id     bigint(20)    not null                       comment '课程ID',
  course_name   varchar(100)  not null                       comment '课程名称',
  tech_time_loc     varchar(100)  default ''                     comment '上课时间',
  class_size    int(3)        not null                       comment '课堂人数',
  selected_size int(3)        not null                       comment '已选人数',
  create_time   datetime      default CURRENT_TIMESTAMP      comment '课程创建时间',
  note          varchar(20)   default ''                     comment '备注',
  primary key (sche_id)
) engine=innodb auto_increment=1 charset=utf8 comment='排课表';

-- insert into gc_schedule values(1, 1, 1, '["w1s2":"1,2,3,4,5,6,7,8,14,15,16", "w4s3":"1,14,15,16"]', '["w1s2":"2-506", "w4s3":"3-207"]', 150, 0, '2020-02-13 16:07:35', '');
-- insert into gc_schedule values(2, 2, 2, '["w1s2":"1,2,3,4,5,6,7,8,14,15,16", "w4s3":"1,14,15,16"]', '["w1s2":"2-506", "w4s3":"3-207"]', 150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(1, 1, '陈老师', 1, '大学英语', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(2, 2, '张老师', 2, '大学语文', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(3, 2, '张老师', 3, '数据库原理', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(4, 1, '陈老师', 4, '数据结构', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(5, 2, '张老师', 5, 'JavaEE', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(6, 1, '陈老师', 6, '计算机系统基础', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(7, 1, '陈老师', 7, '项目管理', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(8, 2, '张老师', 8, 'c语言程序设计', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(9, 1, '陈老师', 9, '操作系统', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(10, 2, '张老师', 10, '云计算体系架构', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(11, 1, '陈老师', 11, '毕业实习', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');
   insert into gc_schedule values(12, 2, '张老师', 12, '毕业设计', '{"info": [{"week":"1,2,3,4,5,6,7,8,14,15,16", "point":"1-2", "loc":"2-506"}, {"week":"1,14,15,16", "point":"4-3", "loc":"3-207"}]}',150, 0, '2020-02-13 16:07:35', '');


-- --------------------
-- 6. 已选课表[信息学院]
-- --------------------
drop table if exists gc_sc_xx;
create table gc_sc_xx(
  sc_id        bigint(20)    not null auto_increment         comment '已选课程ID',
  user_id      bigint(20)    not null                        comment '学生ID',
  sche_id      bigint(20)    not null                        comment '排课ID',
  voluntary    int(1)        not null default '1'            comment '选课志愿', 
  create_time  datetime      default CURRENT_TIMESTAMP       comment '选课时间',
  primary key (sc_id)
) engine=innodb auto_increment=1 charset=utf8 comment='已选课表-信息学院';

insert into gc_sc_xx values(1, 1, 1, 1, '2020-02-13 16:07:35');
insert into gc_sc_xx values(2, 1, 2, 1, '2020-02-13 16:07:35');


-- --------------------
--7. 已选课表[国际商学院]
-- --------------------
drop table if exists gc_sc_gjs;
create table gc_sc_gjs(
  sc_id        bigint(20)    not null auto_increment         comment '已选课程ID',
  user_id      bigint(20)    not null                        comment '学生ID',
  sche_id      bigint(20)    not null                        comment '排课ID',
  voluntary    int(1)        not null default '1'            comment '选课志愿',
  create_time  datetime      default CURRENT_TIMESTAMP       comment '选课时间',
  primary key (sc_id)
) engine=innodb auto_increment=1 charset=utf8 comment='已选课表-国际商学院';

insert into gc_sc_gjs values(1, 2, 1, 1, '2020-02-13 16:07:35');
insert into gc_sc_gjs values(2, 2, 2, 1, '2020-02-13 16:07:35');

-- --------------------
--8. 排课时间表
-- --------------------
drop table if exits gc_time_manage;
create table gc_time_manage(
    id              bigint(20)      not null auto_increment             comment 'ID',
    start_time      datetime        not null                            comment '选课开始时间',
    end_time        datetime        not null                            comment '选课结束时间',
    draw_flag       int             not null                            comment '已选标志',
    note            varchar(100)    default ''                          comment '备注',
    update_time     datetime        not null default CURRENT_TIMESTAMP  comment '更新时间',
    primary key (id)
) engine=innodb auto_increment=1 charset=utf8 comment='排课时间表';
