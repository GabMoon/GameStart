Note: This is one part of the project. To get a fully functional project, you need to set up the front end as well. Here is a link to that front end with its own README: https://github.com/GabMoon/GameStartWeb. This is a fully functional API that that has endpoints that can be hit. The paths for those endpoints are inside the controllers directory of the project.
# GameStart

## Project Description

GameStart is a web application that provides users with a platform to search and review a list of
games. This web application uses a custom api to allow commuication between a user and a PostgreSQL database. It provides
a list of the top 10 rated games based on users' ratings and a personal list of saved games.

## Technologies Used

* Java - version 1.8
* SQL - version 2.0
* PostgreSQL - version 12.5
* JUnit - version 3.0
* Maven - version 4.0.0
* Mockito - 2.10.0
* Git - 2.31.0
* Spring Boot - version 2.4.3
* Spring Data
* S3 Bucket
* Spring Security
* Tomcat - Located inside Spring Boot
* JavaDoc

## Features

List of features ready and TODOs for future development
* Password Encryption
* Ability to search by 1 or more letters for games
* Store a review and grab reviews for a game

To-do list:
* Allow more than 1 user to log in on the website
* Look up other users
* Search a game by developer(s), publisher(s), or platform(s)

## Getting Started
   
1. git clone https://github.com/GabMoon/GameStart.git
2. Set up your PostgreSQL Database with the Table creator SQL file included in the resources folder. DO NOT RUN THE INSERTS INSIDE OF THIS FILE. THE DATABASE WILL NOT BE ABLE TO BE SET UP CORRECTLY. JUST CREATE THE TABLES.
3. Create an application.properties file inside the resources folder that contains three fields, 1. db.url which is set to the link to your database you made in the step prior. 2. db.username which is your username to that database. 3. db.password which is your password to that database.

![image](https://user-images.githubusercontent.com/77693248/112165015-be732480-8bc4-11eb-80c8-62e1e218e6bc.png)

4. Go to the RawgApi Java class that is inside the api directory. Scroll down until you see a line that has @PostConstruct. Uncomment that entire section. It helps grab the data from the API and insert it into the database you made.
5. Run the program once. It will take a while because it is inserting about 2,000 different games and its data. Once it is finished inserting, stop the program and comment out the @PostConstruct code that was previously uncommented in the last step. You don't want this to run more than once. It will not work because of the primary key and foreign key constraints that was put on the tables.
6. You now have your business logic and tables set up.
7. This backend runs on port 5000. If you would like to change the port that it runs on, you need to go to the application.yml file and change the port number at the top of the file. Make sure to choose a port that you know is not being used.
8. Note: This is one part of the project. To get a fully functional project, you need to set up the front end as well. Here is a link to that front end with its own README: https://github.com/GabMoon/GameStartWeb. This is a fully functional API that that has endpoints that can be hit. The paths for those endpoints are inside the controllers directory of the project.

## Usage
* This project is meant to be used in conjunction with a front end that was created and linked in step 8 on the Getting Started section.
* This project is used as an RESTFUL API to create users, display games from the Rawg API, favorite games, write reviews rate games, and grab your favorite games.
*  Inside the controllers directory, there are three Java classes. Within each class are paths to each endpoint that you can hit.
## Contributors

> Gabrielle Luna, Calvin Zheng, Tuan Mai, and Daniel Skwarcha
