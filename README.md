# hackingindia

Technology : Java-Spring Boot / HTML

DockerHub Repository: https://hub.docker.com/repository/docker/vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute

----

Access UI : http://localhost:5477/investment

API: 
1) To Parse Transaction : http://localhost:5477/blackrock/challenge/v1/transactions:parse
2) To Validate Transaction : http://localhost:5477/blackrock/challenge/v1/transactions:validator
3) To Filter Transaction : http://localhost:5477/blackrock/challenge/v1/transactions:filter
4) NPS Return : http://localhost:5477/blackrock/challenge/v1/returns:nps
4) INDEX Return : http://localhost:5477/blackrock/challenge/v1/returns:index
4) FD Return : http://localhost:5477/blackrock/challenge/v1/returns:fd
5) Best Plan Recommendation : http://localhost:5477/blackrock/challenge/v1/returns:recommend
6) App Performance : http://localhost:5477/blackrock/challenge/v1/performance

-------

Steps to Run the Project on Local: 

1. Spring Boot Project Build:
./gradlew clean build

--------

Step to Run the Project on Docker Container :

1) Pull Image 
docker pull vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest

2) Run Image
docker run -d -p 5477:5477 --name blk-hacking-ind-vaibhav-kurkute vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest

3) Check Logs
docker logs blk-hacking-ind-vaibhav-kurkute

4) Stop Container
docker start blk-hacking-ind-vaibhav-kurkute

docker stop blk-hacking-ind-vaibhav-kurkute

5) Remove Container
docker rm blk-hacking-ind-vaibhav-kurkute
---------

Steps Only for Dev :

1) Build

docker build -t blk-hacking-ind-vaibhav-kurkute .

docker tag blk-hacking-ind-vaibhav-kurkute vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest

2) Verify Image
docker login

3) Push to DockerHub
docker push vaibhavkurkute/blk-hacking-ind-vaibhav-kurkute:latest


------
