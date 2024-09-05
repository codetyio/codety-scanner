 export DOCKER_BUILD_VERSION=0.1 && export DOCKER_IMG=codetyio/codety-scanner \
 && docker build --platform=linux/amd64 -t "$DOCKER_IMG":$DOCKER_BUILD_VERSION  -f image/Dockerfile . \
 && docker run --platform=linux/amd64 -p 7777:7777  -e A='A'  -e CODETY_TOKEN='a' --entrypoint "/bin/sleep"  codetyio/codety-scanner:0.1 1d
