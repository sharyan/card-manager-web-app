Payment Card Manager
---

### Setup

Require postgres9.x to run the application, best approach would be to run a docker container

> ```docker run --name postgres-web -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=password --rm -p 5433:5432 -d postgres```

If you update the username+password pair, update the credentials in the `application.properties` file.

