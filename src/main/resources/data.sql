
INSERT INTO board (name) VALUES('QNA 게시판');
INSERT INTO board (name) VALUES('자유 게시판');
INSERT INTO users(email,nickname,roles) VALUES ('dlalstn1234@naver.com','테스트 유저','USER');
INSERT INTO users(email,password,nickname,roles) VALUES ('dlalstn1234@gmail.com','$2a$10$CX7JV6UO1MAswEAqPBxx3uiGrW2sZBGMbx1grw4mA5nRtkw5CSc4m','관리자','ADMIN');

INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('첫번째글', '내용', NOW(), NOW(),2,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('두번째글', '내용', NOW(), NOW(),2,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('세번째글', '내용', NOW(), NOW(),2,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('네번째글', '내용', NOW(), NOW(),1,2);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('10번째글', '내용', NOW(), NOW(),2,2);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,1);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('여섯번째글', '내용', NOW(), NOW(),1,2);
INSERT INTO post (title, content, created_at, updated_at,board_id,user_id) VALUES ('다섯번째글', '내용', NOW(), NOW(),1,2);
INSERT INTO comments (post_id, content, created_at) VALUES (1,'댓글',NOW());

INSERT INTO category (name) VALUES('의류');
INSERT INTO category (name) VALUES('전자제품');
INSERT INTO category (name) VALUES('뷰티');
INSERT INTO category (name) VALUES('키즈');
INSERT INTO category (name) VALUES('신발');
INSERT INTO category (name) VALUES('라이프');

INSERT INTO address (name) Values('부산 덕천동');
INSERT INTO address (name) Values('서울 강남구');
INSERT INTO address (name) Values('서울 노원구');
INSERT INTO address (name) Values('서울 관악구');
INSERT INTO address (name) Values('서울 은평구');
INSERT INTO address (name) Values('서울 강동구');
INSERT INTO address (name) Values('서울 양천구');
INSERT INTO address (name) Values('서울 성북구');
INSERT INTO address (name) Values('서울 서초구');
INSERT INTO address (name) Values('서울 구로구');
INSERT INTO address (name) Values('부산 북구동');

INSERT INTO item(title,brand,price,user_id,created_at,updated_at,content,category_id) VALUES ('아디다스 반팔티','아디다스','40000',2,NOW(),NOW(),'내고 불가',1);
INSERT INTO item(title,brand,price,user_id,created_at,updated_at,content,category_id) VALUES ('아디다스 슬리퍼','아디다스','20000',2,NOW(),NOW(),'내고 불가',1);
INSERT INTO item(title,brand,price,user_id,created_at,updated_at,content,category_id) VALUES ('나이키 반팔티','나이키','30000',2,NOW(),NOW(),'내고 불가',1);
INSERT INTO item(title,brand,price,user_id,created_at,updated_at,content,category_id) VALUES ('나이키 슬리퍼','나이키','25000',2,NOW(),NOW(),'내고 불가',1);
INSERT INTO item(title,brand,price,user_id,created_at,updated_at,content,category_id) VALUES ('뉴발란스 반팔티','뉴발란스','20000',2,NOW(),NOW(),'내고 불가',1);
INSERT INTO item(title,brand,price,user_id,created_at,updated_at,content,category_id) VALUES ('뉴발란스 슬리퍼','뉴발란스','15000',1,NOW(),NOW(),'내고 불가',1);
INSERT INTO item(title,brand,price,user_id,created_at,updated_at,content,category_id) VALUES ('아이폰16','애플','120만원',1,NOW(),NOW(),'내고 불가',2);
INSERT INTO item(title,brand,price,user_id,created_at,updated_at,content,category_id) VALUES ('플립4','삼성','100만원',2,NOW(),NOW(),'내고 불가',2);
INSERT INTO item(title,brand,price,user_id,created_at,updated_at,content,category_id) VALUES ('갤럭시 s21','삼성','80만원',1,NOW(),NOW(),'내고 불가',2);
INSERT INTO item(title,brand,price,user_id,created_at,updated_at,content,category_id) VALUES ('갤럭시 폴드4','삼성','150만원',1,NOW(),NOW(),'내고 불가',2);

INSERT INTO item_address(item_id, address_id) VALUES (1, 1);
INSERT INTO item_address(item_id, address_id) VALUES (2, 1);
INSERT INTO item_address(item_id, address_id) VALUES (3, 1);
INSERT INTO item_address(item_id, address_id) VALUES (4, 1);
INSERT INTO item_address(item_id, address_id) VALUES (5, 2);
INSERT INTO item_address(item_id, address_id) VALUES (6, 2);
INSERT INTO item_address(item_id, address_id) VALUES (7, 2);
INSERT INTO item_address(item_id, address_id) VALUES (8, 2);
INSERT INTO item_address(item_id, address_id) VALUES (9, 2);
INSERT INTO item_address(item_id, address_id) VALUES (10, 2);



