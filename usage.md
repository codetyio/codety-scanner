# Set up environment
#### Install nodejs and npm in your laptop
#### Install different versions of eslint:

* Eslint v9 ``` npm install --prefix **/usr/local/eslint9/** eslint@9.x eslint-plugin-jsdoc eslint-plugin-react eslint-plugin-vue @html-eslint/parser @html-eslint/eslint-plugin ```

* Eslint v8: ```npm install --prefix **/usr/local/eslint8/** eslint@8.x eslint-plugin-jsdoc eslint-plugin-react eslint-plugin-vue @html-eslint/parser @html-eslint/eslint-plugin ```
codety.eslint.path


## Test Locally:
* Go to the based source code path:
  * build: ` ./gradlew clean :analyzer:bootJar  -x test -Dspring.profiles.active=dev `
  * run: `java -Dspring.profiles.active=dev -jar scanner/build/libs/analyzer-0.1.jar .scanner/`

## Publish:
  * build: `./gradlew clean :analyzer:bootJar  -x test -Dspring.profiles.active=prod`

## How to debug Analyzer container:
Run docker and hang the docker run:
```
 export DOCKER_BUILD_VERSION=0.1 && export DOCKER_IMG=codetyio/codety \
 && docker build --platform=linux/amd64 \
      -t "$DOCKER_IMG":$DOCKER_BUILD_VERSION \ -f scanner/Dockerfile . \
 && docker run --platform=linux/amd64 -p 7777:7777 \
      -e A='A'  -e CODETY_TOKEN='a' --entrypoint "/bin/sleep" \
      codetyio/codety:0.1 1d

```

#### How to build and publish docker image:
Check Dockerfile 

