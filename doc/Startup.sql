create database test charset=utf8;
use test;
create table Account(
	id integer primary key auto_increment comment 'ID',
	name varchar(20) not null comment 'ユーザ名',
	pass varchar(20) not null comment 'ログインパス',
	tid integer not null default 1 comment 'TeamID',
	lid varchar(20) unique not null default '' comment 'ログインID'
)charset=utf8
comment="日報アカウント";
create table TCategory(
	id integer primary key auto_increment comment 'ID',
	caption varchar(20) not null unique comment 'カテゴリ名'
)charset=utf8
comment="作業カテゴリー";
create table Team(
	id integer primary key auto_increment comment 'ID',
	caption varchar(20) not null unique comment 'プロジェクト名'
)charset=utf8
comment="開発チーム";
create table responsible(
	aId integer not null comment 'アカウントID',
	tdId integer not null comment 'チーム別タスク詳細ID'
)charset=utf8
comment='タスク担当者';
create table Report(
	id integer primary key auto_increment comment 'ID',
	aid integer not null comment 'アカウントID',
	day date not null comment '日付',
	tcatid integer not null default 0 comment 'タスクカテゴリID',
	todayProg integer not null default 0 comment '本日内容の進捗度',
	finalProg integer not null default 0 comment '完成までの進捗度',
	doing varchar(2000) not null comment '本日の作業内容',
	complete varchar(2000) not null default '' comment '完成内容',
	remains  varchar(2000) not null default '' comment '残作業',
	comments varchar(4000) not null default '' comment '雑感' 
)charset=utf8
comment="日報ログ";
create table taskDetails(
	id integer primary key auto_increment,
	teamid integer not null comment '開発チームID',
	taskid integer not null comment '作業項目ID',
	caption varchar(24) not null comment '見出し',
	discription varchar(1000) not null default '' comment '詳細',
	priority integer not null default 0 comment '優先順位'
)charset=utf8
comment = 'チーム別タスク詳細';

-- 登録
insert into team (caption) values( '試作プロジェクト' );
insert into tcategory (caption) values( '企画立案' );
insert into tcategory (caption) values( '企画設計' );
insert into tcategory (caption) values( '環境設定' );
insert into tcategory (caption) values( 'データベース設計' );
insert into tcategory (caption) values( 'リソース準備' );
insert into tcategory (caption) values( '実装' );
insert into tcategory (caption) values( '試験' );
insert into task (caption, tcid) values( '要求分析', 1 );
insert into task (caption, tcid) values( 'システム分析', 1 );
insert into task (caption, tcid) values( 'データ分析', 1 );
insert into taskdetails (teamid, taskid, caption, discription)
values( 1, 1, 'ターゲット分析', '今回のシステムを使用するシチュエーションを分析し、適切な設計のための資料を準備する' );




