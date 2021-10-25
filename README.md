# pp.mall

## 스프링 쇼핑몰 만들기
## Oracle 테이블

## 회원 테이블 
create table tbl_member (
    userId      varchar2(50)    not null,
    userPass    varchar2(100)   not null,
    userName    varchar2(30)    not null,
    userPhon    varchar2(20)    not null,
    userAddr1   varchar2(20)    null,
    userAddr2   varchar2(50)    null,
    userAddr3   varchar2(50)    null,
    regiDate    date            default sysdate,
    verify      number          default 0,
    primary key(userId)
);

## 상품 테이블
create table tbl_goods (
    gdsNum       number          not null,
    gdsName      varchar2(50)    not null,
    cateCode     varchar2(30)    not null,
    gdsPrice     number          not null,
    gdsStock     number          null,
    gdsDes       varchar(500)    null,
    gdsImg       varchar(200)    null,
    gdsDate      date            default sysdate,
    primary key(gdsNum)  
);

## 카테고리 테이블
create table goods_category (
    cateName     varchar2(20)    not null,
    cateCode     varchar2(30)    not null,
    cateCodeRef  varchar2(30)    null,
    primary key(cateCode),
    foreign key(cateCodeRef) references goods_category(cateCode)
);

## 테이블과 카테고리 테이블의 참조 코드
alter table tbl_goods add
    constraint fk_goods_category
    foreign key (cateCode)
        references goods_category(cateCode);

## 상품번호 시퀀스
create sequence tbl_goods_seq;

## 카테고리 테이블 임시 데이터 삽입 
insert into goods_category (catename, catecode) VALUES ('카테고리 1','100'); 
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('카테고리 1-1','101','100');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('카테고리 1-2','102','100');

insert into goods_category (catename, catecode) VALUES ('카테고리 2','200'); 
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('카테고리 2-1','201','200');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('카테고리 2-2','202','200');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('카테고리 2-3','203','200');

insert into goods_category (catename, catecode) VALUES ('카테고리 3','300'); 
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('카테고리 3-1','301','300');

// cateCodeRef의 값이 없다면(null) 그 카테고리는 최상위 카테고리이며, cateCodeRef의 값이 다른 cateCode와 같다면 그 카테고리는 cateCode의 하위 카테고리이다.

cateCodeRef는 cateCode를 참조(foreign)하기 때문에, cateCodeRef는 존재하지 않는 cateCode를 입력할 수 없다.

## 카테고리 임시데이터 (본프로젝트)
insert into goods_category (catename, catecode) VALUES ('여성의류','100'); 
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('티셔츠','101','100');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('후드티/후드집업','102','100');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('바지','103','100');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('치마','104','100');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('트레이닝','105','100');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('블라우스','106','100');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('가디건/니트','107','100');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('기타','108','100');

insert into goods_category (catename, catecode) VALUES ('남성의류','200'); 
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('티셔츠','201','200');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('바지','202','200');

insert into goods_category (catename, catecode) VALUES ('시게/쥬얼리','300'); 
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('시계','301','300');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('쥬얼리','302','300');

insert into goods_category (catename, catecode) VALUES ('패션/액세서리','400'); 
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('지갑/벨트/모자','401','400');
insert into goods_category(catename, cateCode, cateCodeRef) VALUES ('기타액세서리','402','400');


## 카테고리 상/하 위 레벨별 분류 
select level, cateName, cateCode, cateCodeRef from goods_category
start with cateCodeRef is null connect by prior cateCode = cateCodeRef;

## 상품테이블 데이터 임시 삽입
insert into tbl_goods (GDSNUM,GDSNAME,CATECODE,GDSPRICE,GDSSTOCK,GDSDES)
VALUES (tbl_goods_seq.nextval,'상품 이름',100,1000,30,'상품 설명');

## 상품수정 전 임시조회 쿼리 (카테고리와 같이 가져올수 있도록 JOIN문)
select 
g.gdsNum, g.gdsName, g.cateCode, c.cateCodeRef, c.cateName, gdsPrice, gdsStock, gdsDes, gdsImg, gdsDate
from tbl_goods g
inner join goods_category c
on g.cateCode = c.cateCode
where g.gdsnum = 1;

## tbl_goods 테이블 썸네일 컬럼 추가 (이미지저장시)
alter table tbl_goods add (gdsThumbImg varchar(200));

## 이지윅 에디터 ck에디터 다운로드 링크(현 프로젝트 버전4)
https://ckeditor.com/ckeditor-4/download/?null-addons=

## 사용자 화면 카테고리 출력 임시 테스트
select 
g.gdsnum,g.gdsname,g.catecode,c.catecoderef,c.catename,
gdsprice,gdsstock,gdsdes,gdsimg,gdsdate,g.gdsimg,g.gdsthumbimg
from tbl_goods g
inner join goods_category c
on g.catecode = c.catecode
where g.catecode = 101;

## 댓글(상품후기및 소감) 테이블 생성
---댓글(상품후기) 테이블 생성

-- 상품 소감 테이블(tbl_reply)의 기본키는 상품 번호(gdsNum)과 소감 번호(repNum)
-- 상품 번호는 소감이 어느 상품에 작성되었는지 구분하며, 소감 번호는 한 상품에 작성된 소감을 구분할 수 있다.

create table tbl_reply (
    gdsNum      number          not null,
    userId      varchar2(50)    not null,
    repNum      number          not null,
    repCon      varchar2(2000)  not null,
    repDate     date            default sysdate,
    primary key(gdsNum, repNum) 
);

## 상품번호(repNum) 자동생성 시퀀스
create sequence tbl_reply_seq;

## 상품테이블 참조 쿼리
-- 소감 테이블의 상품 번호와 유저 아이디는 다른 테이블에서 참조하며 테이블을 참조하게되면 
-- 참조하는 테이블에 값이 없는 경우 추가되지 않도록 막을 수 있다.

-- 이렇게 참조키를 사용하는 이유는, 데이터의 무결성을 위해서이며, 
-- 어떠한 이유가 생겨서 존재하지 않는 상품 번호나 유저 아이디가 데이터 베이스에 전달 될 경우, 
-- 참조키가 있다면 데이터가 입력되는걸 차단할수있다

alter table tbl_reply
    add constraint tbl_reply_gdsNum foreign key(gdsNum)
    references tbl_goods(gdsNum);
   
alter table tbl_reply
    add constraint tbl_reply_userId foreign key(userId)
    references tbl_member(userId);

