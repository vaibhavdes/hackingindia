# hackingindia

Spring Boot Project Build:
./gradlew clean build

DockerHub Stages:
1) Build
docker build -t vaibhavkurkute/hackingInda-service:1.0 .

2) Verify Image
docker login

3) Push to DockerHub
docker push vaibhavkurkute/hackingInda-service:1.0