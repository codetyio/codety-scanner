FROM  amazoncorretto:17-alpine-jdk

RUN apk add --no-cache python3 py3-pip
RUN apk add --no-cache bash openssl ca-certificates git libc6-compat libstdc++ curl cppcheck nodejs npm py3-pylint

RUN npm install --prefix /usr/local/eslint9/ eslint@9.x eslint-plugin-jsdoc eslint-plugin-react eslint-plugin-vue @html-eslint/parser @html-eslint/eslint-plugin
RUN npm install --prefix /usr/local/eslint8/ eslint@8.x  typescript @typescript-eslint/parser @typescript-eslint/eslint-plugin

# !!!!!!checkov build will be failed in arm64 environment, you need to append `--platform=linux/amd64` during docker build or run if you use ARM64 environment(e.g. M1+ chips MacBook)
RUN pip3 install checkov --break-system-packages

#golang lint
RUN wget -O- -nv https://raw.githubusercontent.com/golangci/golangci-lint/master/install.sh | sh -s v1.60.3
RUN golangci-lint --version

#RUN curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sh -s -- -b /usr/local/bin v0.18.3

COPY scanner/codety.sh /usr/bin/codety
COPY scanner/tools/ /tools/
COPY scanner/build/libs/app.jar /usr/app.jar
RUN chmod +x /usr/bin/codety

WORKDIR /src
ENTRYPOINT ["codety"]

#use root permission by default as some(e.g. GitHub Actions) CI platforms requires.