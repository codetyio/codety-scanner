# Set up environment
#### Install nodejs and npm in your laptop
#### Install different versions of eslint:

* Eslint v9 ``` npm install --prefix **/usr/local/eslint9/** eslint@9.x eslint-plugin-jsdoc eslint-plugin-react eslint-plugin-vue @html-eslint/parser @html-eslint/eslint-plugin ```

* Eslint v8: ```npm install --prefix **/usr/local/eslint8/** eslint@8.x eslint-plugin-jsdoc eslint-plugin-react eslint-plugin-vue @html-eslint/parser @html-eslint/eslint-plugin ```
  codety.eslint.path

## Test locally:
```
export CODETY_HOST=http://localhost:8081/
export CODETY_RUNNER_DEBUG=true
export CODETY_TOKEN=4b1c1ae135a74c23bb08ebd31bda2ff3c2e140ad2ce34f3da39c550d5017e90f
export CODETY_REPORT_ALL_ISSUES=false
java -jar -Dspring.profiles.active=dev build/libs/app.jar .

```


## Test locally via compiled jar:
* Go to the based source code path:
  * build: ` ./gradlew clean :scanner:bootJar  -x test -Dspring.profiles.active=dev `
  * run: `java -Dspring.profiles.active=dev -jar scanner/build/libs/scanner-0.1.jar .scanner/`
