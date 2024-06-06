- # Overview
The Assignment Management Application is a comprehensive solution for managing assignments in an educational environment. 
This application provides distinct functionalities for three types of users: Admin, Teacher, and Student.

**1) Admin:** Configures and manages all functionalities.  
**2) Teacher:** Uploads assignments which are then stored in Amazon S3, with a QR code generated for easy access.  
**3) Student:** Downloads assignments using the provided QR codes.  

- # Technology Stack
**Backend**: Spring Boot  
**Storage**: Amazon S3  
**QR Code Generation**: ZXing Spring Library  
**Database**: PostgreSQL


- # Prerequisites
Java 11 or higher  
Maven  
AWS Account with S3 bucket access  
Git  

- # Configuration

# 1) Application Properties
Ensure you have the following properties configured in your application.properties or application.yml file:  

server.port=8080  

- *AWS Configuration*  
aws.accessKeyId=YOUR_ACCESS_KEY_ID  
aws.secretKey=YOUR_SECRET_KEY  
aws.s3.bucket=YOUR_S3_BUCKET_NAME  

- *Database Configuration*  
spring.datasource.url=jdbc:postgresql://localhost:5432/assignmentapp  
spring.datasource.username=your_db_username  
spring.datasource.password=your_db_password  
spring.jpa.hibernate.ddl-auto=update


# 2) Database Setup  
Ensure your database is set up correctly. You can create the database as follows (for PostgreSQL):  
CREATE DATABASE assignmentapp;  



- For any queries or issues, please reach out to [animity0407@gmail.com]
