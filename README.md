# skyline-service

Skyline Hotel Management System Backend (RESTful service)    

https://manish0890.github.io/skyline-service/   
More goodies coming up.....   

### System Requirements
- Unix based system
- Java 12
- Docker

### Scripts/Commands to run application

Start Local MongoDB Repository
```bash
./bin/docker-run-mongodb.sh
```

Pull project dependencies 
```bash
mvn clean install -DskipTests=true
```

Run Unit Tests
```bash
mvn clean test
```

Run Integration Tests
```bash
mvn clean -P integration-test test
```

Start Application
```bash
mvn clean spring-boot:run
```

Once the application is running the REST documentation and tester is available via Swagger at 
[http://localhost:8080/api/v1/skyline/swaggerui.html](http://localhost:8080/api/v1/smash-v2/swaggerui.html)

### Guidelines for markdown
```markdown
Syntax highlighted code block

# Header 1
## Header 2
### Header 3

- Bulleted
- List

1. Numbered
2. List

**Bold** and _Italic_ and `Code` text

[Link](url) and ![Image](src)
```