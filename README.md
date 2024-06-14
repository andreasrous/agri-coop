# Agricultural Cooperative Management System

An application for managing agricultural cooperatives, utilizing Spring Boot, Spring Security (JWT), Vue.js, PostgreSQL, Docker, and more.

## Project Structure

- `backend`: Contains the Spring Boot application.
- `frontend`: Contains the Vue.js application.

## Prerequisites

- Java 17 or higher
- Node.js and npm
- Docker

## Optional

- Maven

## Setting Up and Running the Application

### Backend

1. **Start PostgreSQL Database as a Container**

   ```bash
   docker run --name ds-lab-pg --rm \
   -e POSTGRES_PASSWORD=pass123 \
   -e POSTGRES_USER=dbuser \
   -e POSTGRES_DB=appdb \
   -d \
   -p 5432:5432 \
   -v ds-lab-vol:/var/lib/postgresql/data \
   postgres:14
   ```

2. **Start the Spring Boot Application**

   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

3. **Package the Application**

   ```bash
   ./mvnw package -Dmaven.test.skip
   ```

4. **Start the Application using Docker Compose**

   ```bash
   docker-compose up
   ```

### Frontend

1. **Project Setup**

   ```bash
   cd frontend
   npm install
   cp ./env.example ./env
   ```

2. **Compiles and Hot-Reloads for Development**

   ```bash
   npm run dev
   ```

3. **Compiles and Minifies for Production**

   ```bash
   npm run build
   ```

4. **Lint with [ESLint](https://eslint.org/)**

   ```sh
   npm run lint
   ```

## Running the Full-Stack Application

1. **Start the Backend** as described in the Backend section above.
2. **Start the Frontend** as described in the Frontend section above.
3. Open your browser and navigate to `http://localhost:8080` to access the application.

## Links

- [Postgres Docker Hub](https://hub.docker.com/_/postgres)
- [JPA EntityManager example in Spring Boot](https://www.bezkoder.com/jpa-entitymanager-spring-boot/)
- [JPA/Hibernate Persistence Context](https://www.baeldung.com/jpa-hibernate-persistence-context)
- [Vue.js Documentation](https://vuejs.org/v2/guide/)
- [Vite Documentation](https://vitejs.dev/guide/)

### Notice

For any issues or further details, refer to the individual READMEs in the `backend` and `frontend` directories.
