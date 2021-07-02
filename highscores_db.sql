drop database if exists highscores;
create database Highscores;
create table Highscores.highscores (
timestamp timestamp,
name varchar(255) PRIMARY KEY,
score int
);
use highscores;