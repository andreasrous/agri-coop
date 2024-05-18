# Start postgres db as container

```bash
docker run --name ds --rm \
-e POSTGRES_PASSWORD=pass123 \
-e POSTGRES_USER=dbuser \
-e POSTGRES_DB=farmers \
-d --net=host \
-v mypgdata:/var/lib/postgresql/data \
postgres:14

```

# Links
* [Postgres Docker Hub](https://hub.docker.com/_/postgres)
* [JPA EntityManager example in Spring Boot](https://www.bezkoder.com/jpa-entitymanager-spring-boot/)
* [JPA/Hibernate Persistence Context](https://www.baeldung.com/jpa-hibernate-persistence-context)