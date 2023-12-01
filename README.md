Freeman back end project

To build the application

docker build -t api-freeman .

docker run -d -p 8080:8080 --name api-freeman -e MONGO_URI="mongodb://root:brutal@192.168.1.9:27017/freeman?authSource=admin" -e SPRING_PROFILES_ACTIVE=develop api-freeman

docker exec -it api-freeman sh