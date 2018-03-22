# Nasscom E-Invoicing System

Build a Fully-Fledged Application with Spring Boot & MySql in the Backend and Angular 4 in the frontend.

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. MySql - 5.x.x

4. Redis-x64-3.2.100 

## Steps to Setup

**1. Clone the application with your globallogic credential**

```bash
git clone https://portal-ua.globallogic.com/gitlab/Nasscom/e-Invoice.git
```

**2. Build and run the backend app using maven**

```bash
cd e-Invoice
mvn package
java -jar target/einvoice-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The backend server will start at <http://localhost:8080>.

**3. Take clone of client project in same dir of backend code**

```bash
git clone https://portal-ua.globallogic.com/gitlab/Nasscom/einvoice-client.git
```

**4. Run the frontend app using npm**

```bash
cd einvoice-client
npm install
```

```bash
npm start
or 
ng serve
```

Frontend server will run on <http://localhost:4200>

Note: For development you have to run seperate server. And for integration, both apps will be in single application server; below are the steps to do:

  1. First step, build the client app with command "ng build"
  2. Second step, build the backend with command "mvn clean install"
  3. Run executable jar in target folder  "java -jar <jar name>
  4. The integrated app will start at <http://localhost:8080>.

