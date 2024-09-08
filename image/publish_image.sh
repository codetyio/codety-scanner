# Run this script under the {source}/
#        e.g. ./scanner/publish_analyzer.sh 0.01
DOCKER_BUILD_VERSION=$1
BUILD_JAR=$2
DOCKER_IMG=codetyio/codety


if [ "$BUILD_JAR" != "0" ]; then
  echo " ========= Build application start  ========":
  java --version
  echo ""-- Build uses proguard, if the jar build failed, please check the proguard config in build.gradle ---""
  ./gradlew :scanner:clean :scanner:build -x test -Dspring.profiles.active=prod
  if [ $? -ne 0 ]; then
      echo "Failed to build Java project. "
      exit 1;
  fi
fi

echo " ========= Build application end  ========":

#--platform=linux/amd64,linux/arm64
echo " ========= Build and publish images start  ========":
docker buildx build --platform=linux/amd64 -t "$DOCKER_IMG":$DOCKER_BUILD_VERSION  -t "$DOCKER_IMG":latest -f image/Dockerfile . --push
if [ $? -ne 0 ]; then
    echo "Failed to build multi-platform container, create a new builder may fix the issue: '$> docker buildx create --name mybuilder --use ' "
    exit 1;
fi


git tag -a "$DOCKER_BUILD_VERSION" -m "tag version $DOCKER_BUILD_VERSION"
git push origin "$DOCKER_BUILD_VERSION"

echo " ========= Build and publish images end  ========":

echo "====User below command to test the container: ====="
echo "docker run -v $(pwd):/src codetyio/codety:$1"
