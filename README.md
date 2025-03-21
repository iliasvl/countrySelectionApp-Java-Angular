![Capture](https://github.com/user-attachments/assets/76879d4b-7270-48e5-a876-c055805eaf47)
![Capture 99](https://github.com/user-attachments/assets/4d6f4eef-a60d-43f7-9b94-9818214e445d)
![Capture 88](https://github.com/user-attachments/assets/8b98886a-7ef6-43e8-b19e-dc433cdb5784)
![Capture 77](https://github.com/user-attachments/assets/b2b0b456-5075-47d8-a1db-68a46089d566)
![Capture 66](https://github.com/user-attachments/assets/df711cde-6521-414e-be92-efe2f032ac62)
![Capture55](https://github.com/user-attachments/assets/60972492-bb51-4d33-9f12-270960bcde44)

# 🌍 Traveled Countries Tracker
This is a Java Spring Boot and Angular application that allows users to track the countries they have traveled to. Users can register, log in, and manage their traveled country list, while admins have additional privileges to manage travelers.

## 🏗️ Tech Stack
Backend: Java, Spring Boot, Spring Security, JPA, MySQL, JWT
Frontend: Angular, TypeScript, Bootstrap
Database: MySQL

Authentication: JWT-based authentication & authorization
API Documentation: Swagger

## 🚀 Features

### ✅ User Features
Register and log in securely
View a dashboard displaying:
The list of traveled countries (name, code, flag)
Total traveled & untraveled countries
Edit their traveled country list (add/remove countries)

###🛠️ Admin Features
Manage all travelers (view traveler list)
Perform all user functions

### 🔒 Security
JWT-based authentication and authorization
Role-based access (Admin vs. Traveler) 




## 📌 Installation Instructions

### 🗄️ 1. Database Setup
Create the database schema in mysql with the name: countrytickerappdb, the username: userdb6 and the password: 12345 and the rest is automatically generated when you run the backend for the first two times. More in depth explanation follows.


### ⚙️ 2. Backend Setup (Spring Boot)
Clone the repository:
git clone https://github.com/iliasvl/countrySelectionApp-Java-Angular.git
cd countrySelectionApp-Java-Angular/Back-end/country-ticker-app/country-ticker-app

Configure Database
Edit application-test.properties in src/main/resources:

spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DB:countrytickerappdb}?serverTimezone=UTC
spring.datasource.username=${MYSQL_USER:userdb6}
spring.datasource.password=${MYSQL_PASSWORD:12345}
spring.jpa.hibernate.ddl-auto=update

##UNCOMMENT AT FIRST UPDATE, COMMENT AT CREATE
#spring.sql.init.mode=always
##spring.sql.init.data-locations=classpath:sql/countries.sql
spring.sql.init.encoding=UTF-8
spring.sql.init.platform=mysql


Create application.properties in src/main/resources:
spring.profiles.active=test
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.globally_quoted_identifiers=true
spring.data.jpa.repositories.enabled=true


Run the Backend:
./gradlew bootRun

The backend will start at http://localhost:8080.


### 🎨 3. Frontend Setup (Angular)
Navigate to the Frontend Directory:
cd ./Front-end/country-travel-tracker


Install Dependencies:
npm install

Run the Angular App:
ng serve

The frontend will start at http://localhost:4200.

1️⃣ Ensure MySQL is Running
Make sure you have a MySQL database server running. You can use:
Local MySQL installation

2️⃣ Configure Database Connection
Edit the backend properties in application-test.properties if needed:
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DB:countrytickerappdb}?serverTimezone=UTC
spring.datasource.username=${MYSQL_USER:userdb6}
spring.datasource.password=${MYSQL_PASSWORD:12345}
spring.jpa.hibernate.ddl-auto=update
✅ Tables will be created automatically on first startup!

3️⃣ Add Country Data
In order to populate the database with country data, uncomment these lines in application-test.properties and restart:
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:sql/countries.sql
👉 Then comment these lines once more.

### 📊 API Documentation (Swagger)
Once the backend is running, access the API documentation at:
📄 http://localhost:8080/swagger-ui/index.html



# 📜 License
This project is open-source under the MIT License.
