# hackingindia

DockerHub URL: https://hub.docker.com/repository/docker/vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute

Spring Boot Project Build:
./gradlew clean build

DockerHub:

--- To Run & Testing ---

4) Pull Image 
docker pull vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest

5) Run Image
docker run -d -p 5477:5477 --name blk-hacking-ind-vaibhav-kurkute vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest

6) Check Logs
docker logs blk-hacking-ind-vaibhav-kurkute

7) Stop Container
docker stop blk-hacking-ind-vaibhav-kurkute

---- Only For Dev ----

1) Build

docker build -t blk-hacking-ind-vaibhav-kurkute .

docker tag blk-hacking-ind-vaibhav-kurkute vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest

2) Verify Image
docker login

3) Push to DockerHub
docker push vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest
