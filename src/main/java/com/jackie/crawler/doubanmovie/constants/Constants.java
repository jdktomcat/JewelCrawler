package com.jackie.crawler.doubanmovie.constants;

/**
 * 常量类
 *
 * @author Administrator
 */
public class Constants {

    public static String MAIN_URL = "https://movie.douban.com/";

    public static int maxCycle = 150000;

    public static final String ENCODING = "UTF-8";

    public static final String BLANK_SPACE = " ";

    public static final String MOVIE_REGULAR_EXP = "https://movie.douban.com/subject/\\d{8}";

    public static final String COMMENT_REGULAR_EXP = "https://movie.douban.com/subject/\\d{8}/comments";

    public static final String CREATE_DATABASE_SQL = "CREATE DATABASE IF NOT EXISTS douban_crawler";

    public static final String USE_DATABASE_SQL = "USE douban_crawler";

    public static final String CREATE_RECORD_TABLE_SQL = "CREATE TABLE IF NOT EXISTS record (recordId INT(5) NOT NULL auto_increment, URL TEXT NOT NULL, crawled TINYINT(1) NOT NULL, PRIMARY KEY (recordId)) engine=InnoDB DEFAULT CHARSET=utf8";

    public static final String CREATE_MOVIE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS movie (movieId INT(4) NOT NULL auto_increment, director TEXT NOT NULL, scenarist TEXT NOT NULL,actors TEXT NOT NULL,type TEXT NOT NULL,country TEXT NOT NULL,language TEXT NOT NULL, releaseDate TEXT NOT NULL,runtime TEXT NOT NULL,ratingNum TEXT NOT NULL, PRIMARY KEY (movieId)) engine=InnoDB DEFAULT CHARSET=utf8";

    public static final String CREATE_COMMENTS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS comments (commentId INT(4) NOT NULL auto_increment, commentInfo TEXT NOT NULL, commentAuthor TEXT NOT NULL,commentAuthorImgUrl TEXT NOT NULL,commentVote TEXT NOT NULL,recordId TEXT NOT NULL, PRIMARY KEY (commentId)) engine=InnoDB DEFAULT CHARSET=utf8";
}
