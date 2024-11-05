# Build project

## Docker compose

Before all, open a terminal in root path of project.

If you do not have docker elements that can create conflicts, you can simply build the docker-compose file with the following command :
`docker-compose build --no-cache && docker-compose up -d`

In the other case, you can use this command to delete all docker data before building build docker-compose file :
`if [ "$(docker ps -q)" ]; then docker stop $(docker ps -aq); fi && docker system prune -af && docker-compose build --no-cache && docker-compose up -d`

## Kubernetes (`WORK-IN-PROGRESS`)

Build docker image with Dockerfile :
`docker build -t university-parking:latest -f springboot-dockerfile .`
Build project infrastructure in Kubernetes with manifest file :
`kubectl create -f kubernetes-manifest.yaml`

Delete all element built with manifest file :
`kubectl delete -f kubernetes-manifest.yaml`
Delete all deployments and services in local :
`kubectl delete deployments --all`
`kubectl delete services --all`
`kubectl delete configmaps --all`

List all kubernetes pods :
`kubectl get pods -o wide`
View all details about a kubernetes pod :
`kubectl describe pod app-8679cf4766-xj89m`

Build l'image et la push sur ttl (docker registry temporaire) :
`docker build --tag ttl.sh/university-parking:latest --file springboot-dockerfile . --push`
Exposer à l'hôte pour tester en local :
`kubectl port-forward services/database 40003:27017`







`docker build --platform linux/amd64 --tag 192.168.1.100:30095/university-parking . --file springboot-dockerfile --push`
