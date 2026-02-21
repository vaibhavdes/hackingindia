# hackingindia

Spring Boot Project Build:
./gradlew clean build

DockerHub Stages:
1) Build
docker build -t blk-hacking-ind-vaibhav-kurkute .
docker tag blk-hacking-ind-vaibhav-kurkute vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest

2) Verify Image
docker login

3) Push to DockerHub
docker push vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest

4) Pull Image 
docker pull vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest

5) Run Image
docker run -d -p 5477:5477 --name blk-hacking-ind-vaibhav-kurkute