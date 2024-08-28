# Run this script under the {source}/
#        e.g. ./scanner/publish_analyzer.sh 0.01
DOCKER_BUILD_VERSION=$1
BUILD_JAR=$2

if [ "$BUILD_JAR" != "0" ]; then
  echo " ========= Build application start  ========":
  java --version
  echo ""-- Build uses proguard, if the jar build failed, please check the proguard config in build.gradle ---""
  ./gradlew :scanner:clean :scanner:build -info
  if [ $? -ne 0 ]; then
      echo "Failed to build Java project. "
      exit 1;
  fi
fi

echo " ========= Build application end  ========":
ls scanner/build/libs/app*

echo " ========= Local container test start  ========":
DOCKER_IMG_TEST=codetyio/codety-scanner-test:local
export CODETY_TOKEN=
echo "Building test image $DOCKER_IMG_TEST "
docker build --platform=linux/amd64 -t $DOCKER_IMG_TEST -f image/Dockerfile ./
echo "Running test image $DOCKER_IMG_TEST "
docker run -e CODETY_RUNNER_DEBUG=true -v ./code-issue-examples:/src --platform=linux/amd64 "$DOCKER_IMG_TEST"
#  --entrypoint "/bin/sleep"

# remove exited containers
docker rm $(docker ps -a -f status=exited -q)

# clean up image
docker image rm "$DOCKER_IMG_TEST"
if [ $? -ne 0 ]; then
    echo "[Test]Failed to start the test image $DOCKER_IMG_TEST "
    exit 1;
fi

echo " ========= Local container test end  ========":

