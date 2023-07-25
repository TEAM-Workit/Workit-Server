INSERT INTO users (email, nickname, social_type, social_id)
VALUES ('only_read@example.com', 'only read', 'KAKAO', 'kakao1');
INSERT INTO users (email, nickname, social_type, social_id)
VALUES ('only_create@example.com', 'only create', 'KAKAO', 'kakao2');
INSERT INTO users (email, nickname, social_type, social_id)
VALUES ('apple@example.com', 'apple', 'APPLE', 'apple');

INSERT INTO ability (ability_type, name)
VALUES ('SOFT', '소프트역량1');
INSERT INTO ability (ability_type, name)
VALUES ('SOFT', '소프트역량2');
INSERT INTO ability (ability_type, name)
VALUES ('HARD', '하드역량1');
INSERT INTO ability (ability_type, name)
VALUES ('HARD', '하드역량2');

INSERT INTO project (title, user_id)
VALUES ('읽기전용 프로젝트 1', 1);
INSERT INTO project (title, user_id)
VALUES ('읽기전용 프로젝트 2', 1);
INSERT INTO project (title, user_id)
VALUES ('읽기전용 프로젝트 3', 1);
INSERT INTO project (title, user_id)
VALUES ('수정가능 프로젝트', 2);
INSERT INTO project (title, user_id)
VALUES ('애플전용 프로젝트', 3);

INSERT INTO work (date, description, title, project_id)
VALUES (now(), '설명', '읽기전용 프로젝트 1의 작업 1', 1);
INSERT INTO work (date, description, title, project_id)
VALUES (now(), '설명', '읽기전용 프로젝트 1의 작업 2', 1);
INSERT INTO work (date, description, title, project_id)
VALUES (now(), '설명', '읽기전용 프로젝트 1의 작업 3', 1);
INSERT INTO work (date, description, title, project_id)
VALUES (now(), '설명', '읽기전용 프로젝트 2의 작업 1', 2);
INSERT INTO work (date, description, title, project_id)
VALUES (now(), '설명', '읽기전용 프로젝트 2의 작업 2', 2);
INSERT INTO work (date, description, title, project_id)
VALUES (now(), '설명', '읽기전용 프로젝트 3의 작업 1', 3);
INSERT INTO work (date, description, title, project_id)
VALUES (now(), '설명', '수정가능 프로젝트의 작업', 4);
INSERT INTO work (date, description, title, project_id)
VALUES (now(), '설명', '애플전용 프로젝트의 작업', 5);

INSERT INTO work_ability (ability_id, work_id)
VALUES (1, 1);
INSERT INTO work_ability (ability_id, work_id)
VALUES (2, 1);
INSERT INTO work_ability (ability_id, work_id)
VALUES (3, 2);
INSERT INTO work_ability (ability_id, work_id)
VALUES (4, 2);
INSERT INTO work_ability (ability_id, work_id)
VALUES (1, 3);
INSERT INTO work_ability (ability_id, work_id)
VALUES (2, 4);
INSERT INTO work_ability (ability_id, work_id)
VALUES (3, 5);
INSERT INTO work_ability (ability_id, work_id)
VALUES (4, 6);
INSERT INTO work_ability (ability_id, work_id)
VALUES (1, 7);
INSERT INTO work_ability (ability_id, work_id)
VALUES (2, 8);
