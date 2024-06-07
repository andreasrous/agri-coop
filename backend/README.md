# Distributed Systems Lab 2023 - Backend

## Start Spring

```bash
mvn spring-boot:run
```

## Start PostgreSQL Database as a Container

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

## Dockerize

1. **Package the Application**

   ```bash
   ./mvnw package -Dmaven.test.skip
   ```

2. **Start the Application using Docker Compose**

   ```bash
   docker-compose up
   ```

## Notice

If you use the git protocol in the frontend build, run:

```bash
export DOCKER_BUILDKIT=0
export COMPOSE_DOCKER_CLI_BUILD=0

docker-compose up --build
```

## Links

- [Postgres Docker Hub](https://hub.docker.com/_/postgres)
- [JPA EntityManager example in Spring Boot](https://www.bezkoder.com/jpa-entitymanager-spring-boot/)
- [JPA/Hibernate Persistence Context](https://www.baeldung.com/jpa-hibernate-persistence-context)
