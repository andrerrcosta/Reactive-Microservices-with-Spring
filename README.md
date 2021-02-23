# Reactive Microservices With Spring-Cloud
Game Analytics with WebFlux and Angular 10

This is an example of how a server can detect cheating movements in a game.

Libraries used for this project:
Gateway with SpringCloud. Message broker with SpringAmqp and RabbitMQ. Service discovery
with Consul. Load Balancer with Consul. Config Server with consul.
Logger with AMQP and spring sleuth. Spring WebFlux and MongoDB. OpenJDK 14.
Front-end: Angular 10, RxJs, Node ~14.

You may want to use the docker compose. so i have some notes:
- Spring-boot buildpacks are not supporting Java14. For this reason I put the dockerfiles
inside the services. You can build the images and run the docker-compose.
- Java14 is not allowing the behaviour of blockhound by default. I will not worry about that right now.

Bugs:
Problems on Game-Cheater (front-end). SOLVED.

andrecosta@nobblecrafts.com

