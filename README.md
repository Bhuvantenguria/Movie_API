# 🚀 **Spring Boot MovieAPI Backend**

---

The Spring Boot MovieAPI Backend is a robust Java 17 application designed to manage movie data efficiently. It incorporates a well-structured database schema backed by MySQL, along with essential REST APIs for CRUD operations, search, filtering, sorting, and pagination. The application also prioritizes security using JWT-based authentication. 🎥🍿

## 🚀 **Technology Stack**
---
- ☕ Java 17
- 🍃 Spring Boot
- 🌐 Spring MVC
- 🛢️ Spring Data JPA
- 📑 Pagination and Sorting
- 🔐 JWT Authentication
- 🐬 MySQL Database

## 🌟 **Key Features**
---

### **CRUD Operations:**
- Create, Read, Update, and Delete (CRUD) operations for movies.
- Ensures data integrity with efficient database interactions.

### **Search and Filtering:**
- 🔍 Search movies by **name**.
- 🎭 Filter movies by **director, cast, release year, and genre**.
- 📅 Sort movies by **release date or name**.

### **Pagination and Sorting:**
- 📑 Efficient pagination for large datasets.
- 📊 Sorting support for better data retrieval and organization.

### **JWT Authentication:**
- 🔐 Secure access to APIs using JSON Web Token (JWT) authentication.
- 🛡️ Ensures only authorized users can perform sensitive operations.

### **Three-Layered Architecture:**
- 🏗️ Separation of concerns with presentation, business logic, and data access layers.
- 📂 Enhances maintainability, scalability, and testing.

## 📌 **MovieAPI Endpoints**
---

### **Movie Management:**
- 🔗 **Add Movie:**
  - `POST http://localhost:8080/api/v1/movie/add-movie`

- 🔍 **Get All Movies:**
  - `GET http://localhost:8080/api/v1/movie/all`

- 🔍 **Get Movie by ID:**
  - `GET http://localhost:8080/api/v1/movie/{id}`

- 🔄 **Update Movie by ID:**
  - `PUT http://localhost:8080/api/v1/movie/update/{id}`

- ❌ **Delete Movie by ID:**
  - `DELETE http://localhost:8080/api/v1/movie/delete/{id}`

### **Search, Filter, and Sort:**
- 🔍 **Search Movies by Name:**
  - `GET http://localhost:8080/api/v1/movie/search?name={movieName}`

- 🎭 **Filter by Director, Cast, Year, or Genre:**
  - `GET http://localhost:8080/api/v1/movie/filter?director={directorName}`
  - `GET http://localhost:8080/api/v1/movie/filter?cast={castName}`
  - `GET http://localhost:8080/api/v1/movie/filter?year={releaseYear}`
  - `GET http://localhost:8080/api/v1/movie/filter?genre={genre}`

- 📊 **Sort by Release Date or Name:**
  - `GET http://localhost:8080/api/v1/movie/sort?sortBy=releaseDate`
  - `GET http://localhost:8080/api/v1/movie/sort?sortBy=name`

- 📑 **Get Movies with Pagination:**
  - `GET http://localhost:8080/api/v1/movie/allMoviesPage?pageNumber=0&pageSize=2`

### **User Authentication:**
- 📝 **User Registration:**
  - `POST http://localhost:8080/api/v1/auth/register`

- 🔒 **User Login:**
  - `POST http://localhost:8080/api/v1/auth/login`

## 🚀 **Cloning and Running the Project**
---
To get started with the MovieAPI project, follow these steps:

### **1. Clone the Repository:**
```bash
git clone https://github.com/Bhuvantenguria/Movie_Api_Backend.git
```

### **2. Navigate to the Project Directory:**
```bash
cd Movie_Api_Backend
```

### **3. Configure Application Properties:**
- Open `src/main/resources/application.properties`.
- Modify the database connection details to match your MySQL setup.

### **4. Create MySQL Database:**
- Execute the SQL script provided in `src/main/resources/sql-scripts` to set up the necessary database tables.
```bash
mysql -u your_username -p < create_database.sql
```

### **5. Build and Run the Application:**
- Using Maven:
```bash
./mvnw clean install
./mvnw spring-boot:run
```

### **6. Access API Endpoints:**
- Once the application is running, explore the MovieAPI by accessing the defined endpoints.

---

### 📢 **Contributions & Feedback:**
Feel free to contribute, suggest improvements, or report issues via GitHub!

---

Happy Coding! 🚀🎬

