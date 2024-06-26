# Technikon Project

Welcome to the Technikon project repository! This project is a full-stack web application designed to streamline property repair/renovation processes for both managers and customers.

## Description

The Technikon project leverages modern web technologies to provide a robust and scalable solution for property repair/renovation management. It utilizes Spring Boot for the backend, React for the frontend, and PostgreSQL for the database, offering a seamless user experience and efficient data management.


## Features
## Key Features

- **Spring Boot Backend**: The backend of the Technikon project is built using Spring Boot, a powerful framework for building Java-based applications. Spring Boot provides a lightweight and efficient platform for developing RESTful APIs, managing data persistence, and implementing business logic.

- **React Frontend**: The frontend of the Technikon project is developed using React, a popular JavaScript library for building user interfaces. React's component-based architecture, virtual DOM, and declarative programming model enable the creation of dynamic and interactive user interfaces.

- **PostgreSQL Database**: The Technikon project utilizes PostgreSQL, a robust and feature-rich relational database management system, for storing and managing project data. PostgreSQL offers ACID compliance, extensibility, and support for complex queries, making it suitable for managing large volumes of data in a multi-user environment.

- **RESTful APIs**: The backend of the Technikon project exposes RESTful APIs that allow the frontend to communicate with the server. These APIs enable CRUD (Create, Read, Update, Delete) operations on project data, facilitating seamless interaction between the frontend and backend components.

- **Authentication and Authorization**: The Technikon project implements authentication and authorization mechanisms to ensure secure access to project data. It utilizes industry-standard authentication protocols such as JWT (JSON Web Tokens) to authenticate users and authorize access to protected resources.


## Technologies Used
- Backend: Spring Boot
- Frontend: React
- Database: PostgreSQL
- API Design: RESTful APIs
- Authentication: JWT (JSON Web Tokens)

## Collaborators

The following individuals have contributed to the development of the Technikon project:

1. [Alexandros Xirouchakis](https://github.com/alexxirou)
2. [Elentina Grispou](https://github.com/eledinaGr)
3. [Stella Kokolaki](https://github.com/stelakokolaki)
4. [Meletios Pogkas](https://github.com/meletisp)
5. [Vasilis Lionas](https://github.com/Vln1991)

## Setup Instructions

Follow these steps to set up and run the Technikon project locally on your machine:

1. **Clone the Repository**: 
   ```bash
   git clone https://github.com/alexxirou/Technikon_app_Spring-React-PostgreSQL-.git
   ```
2. **Install Docker Desktop**: Download and install Docker Desktop for your operating system from the official Docker website.
   ```bash
   docker pull postgres
    ```

3. **Pull PostgreSQL Docker Image**: Open a terminal or command prompt and run the following command to pull the official PostgreSQL Docker image:
   ```bash
   docker run --name my-postgres-db -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_USER=myuser -e POSTGRES_DB=mydatabase -d -p 5432:5432 postgres
   ```
   Update the DB information in the application.properties file or in the .env file if you are using one.


4. **Install maven**:

   **For linux debian distros**:
   
   ```bash
   sudo apt install maven

   ```

   **For windows**:

   1. ****Download Maven****:
   Visit the official Apache Maven website to download the latest version of Maven: Apache Maven Download Page.

   2. ****Extract Maven****:
   After downloading the Maven archive (a .zip file), extract it to a directory on your computer where you want Maven to be installed. For example, you can extract it to C:\Program Files\Apache\.

   3. ****Set Environment Variables****: JAVA_HOME: If not already set, set the JAVA_HOME environment variable to the location of your JDK installation. For example, C:\Program Files\Java\jdk1.8.0_291.
    M2_HOME: Set the M2_HOME environment variable to the directory where you extracted Maven. For example, C:\Program Files\Apache\apache-maven-3.8.4.
    Path: Add %M2_HOME%\bin to your system's PATH variable. This step allows you to run Maven commands from the command prompt without specifying the full path to Maven's bin directory.

   4. ****Verify Installation****:
   Open a command prompt and type mvn -v. This command will display the Maven version and other relevant information if Maven is installed correctly.

5. **Start the application**:
   ```bash
      cd Technikon-App-BackEnd/
   ``` 

   ```bash
      mvn exec:java
   ```

6. **Install Node.js and npm**:

   You can download and install Node.js from the [official website](https://nodejs.org/).   


7. **Navigate to Front-End react App and use npm install.**
   ```bash
      cd Technikon-FrontEnd
      npm install
   ```
8. **Run the vite development server.** 

    ```bash
      npm run dev
   ```

   This will open the app on localhost:5417

9. **Build for production.**
   ```bash
      npm run build
   ```

   This will create a static file to serve with a server.

## Usage

### Registration and Login

1. **Registration**: 
   - Navigate to the registration page by clicking on the "Register" button or link.
   - Fill out the registration form with your desired username, email, and password.
   - Click on the "Register" button to create your account.

2. **Login**: 
   - After registering, you'll be redirected to the login page.
   - Enter your username and password in the login form.
   - Click on the "Login" button to authenticate yourself.

### Update User Information

1. **Update Information**: 
   - Once logged in, navigate to the "Profile" or "Settings" section of the application.
   - You should see options to update your username, email, password, or any other relevant information.
   - Make the necessary changes and click on the "Save" or "Update" button to apply the changes.

### Property Management

1. **Add Property**: 
   - To add a new property, navigate to the "Add Property" section.
   - Fill out the required details such as property name, address, description, etc.
   - Click on the "Add Property" button to add the property to your list.

2. **Remove Property**: 
   - If you want to remove a property, navigate to the "Manage Properties" section.
   - You should see a list of properties associated with your account.
   - Find the property you want to remove and click on the "Remove" or "Delete" button next to it.

3. **Check Property Details**: 
   - To view details of a specific property, navigate to the "Properties" section.
   - You'll see a list of properties associated with your account.
   - Click on the property name or details link to view more information about that property.

4. **View Property Summary**: 
   - In the "Properties" section, you can view a summary of all your properties, including their names, addresses, and other relevant information.

### Repair Jobs Management

1. **Add Repair Job**: 
   - To add a new repair job, navigate to the "Add Repair Job" section.
   - Fill out the required details such as job title, description, assigned contractor, etc.
   - Click on the "Add Repair Job" button to create the repair job.

2. **Remove Repair Job**: 
   - If you want to remove a repair job, navigate to the "Manage Repair Jobs" section.
   - You should see a list of repair jobs associated with your account.
   - Find the repair job you want to remove and click on the "Remove" or "Delete" button next to it.

3. **Check Repair Job Details**: 
   - To view details of a specific repair job, navigate to the "Repair Jobs" section.
   - You'll see a list of repair jobs associated with your account.
   - Click on the job title or details link to view more information about that repair job.

4. **View Repair Job Summary**: 
   - In the "Repair Jobs" section, you can view a summary of all your repair jobs, including their titles, descriptions, status, etc.

### Session Management

- **Persistent Login**: 
   - Your login session will persist even if you refresh the page or close the browser.
   - You can continue using the application without having to log in again.

- **Automatic Logout**: 
   - If you remain inactive for 30 minutes, you'll be automatically logged out of your session.
   - Additionally, if you attempt to log in or sign up again while already logged in, you'll be automatically logged out of your current session.

### Navigation

- Use the navigational buttons or links provided in the application to move between different sections such as profile, properties, repair jobs, etc.
- Explore the various features and functionalities offered by the application to manage your properties and repair jobs efficiently.



## License
The Technikon project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

