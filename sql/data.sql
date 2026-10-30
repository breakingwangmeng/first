-- 种子数据（MySQL 8）：admin / demo 的初始密码均为 123456（仅限开发环境，上线前务必修改）
USE basic_framework;

INSERT INTO admin_user (id, username, password, nickname, status) VALUES
    (1, 'admin', '$2a$10$EeXaB9TNwS0DY6iVQdwun.pqnc4TG6zwfV/BmOVlMHVACuXS6YZv6', '管理员', 1),
    (2, 'demo', '$2a$10$EeXaB9TNwS0DY6iVQdwun.pqnc4TG6zwfV/BmOVlMHVACuXS6YZv6', '演示用户', 1);

INSERT INTO admin_role (id, code, name) VALUES
    (1, 'admin', '管理员'),
    (2, 'user', '普通用户');

INSERT INTO admin_user_role (user_id, role_id) VALUES
    (1, 1),
    (2, 2);

INSERT INTO admin_menu (id, parent_id, name, path, sort) VALUES
    (1, 0, '用户管理', '/users', 1);

INSERT INTO admin_role_menu (role_id, menu_id) VALUES
    (1, 1);
