FROM  amazoncorretto:17-alpine-jdk


RUN apk add --no-cache python3 py3-pip go
RUN apk add --no-cache bash openssl ca-certificates git libc6-compat libstdc++ curl cppcheck nodejs npm py3-pylint

RUN npm install --prefix /usr/local/eslint9/ eslint@9.x eslint-plugin-jsdoc eslint-plugin-react eslint-plugin-vue @html-eslint/parser @html-eslint/eslint-plugin
RUN npm install --prefix /usr/local/eslint8/ eslint@8.x  typescript @typescript-eslint/parser @typescript-eslint/eslint-plugin

# !!!!!!checkov build will be failed in arm64 environment, you need to append `--platform=linux/amd64` during docker build or run if you use ARM64 environment(e.g. M1+ chips MacBook)
RUN pip3 install checkov --break-system-packages

# install golang
# COPY --from=golang:1.17-alpine /usr/local/go/ /usr/local/go/
# ENV PATH="/usr/local/go/bin:${PATH}"

#golang lint
RUN wget -O- -nv https://raw.githubusercontent.com/golangci/golangci-lint/master/install.sh | sh -s v1.60.3
RUN golangci-lint --version


RUN apk add --no-cache ruby-dev cmake clang build-base
#RUN export CC=clang
#RUN export CXX=clang++
RUN gem install -V rubocop
RUN rubocop --version

#RUN curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sh -s -- -b /usr/local/bin v0.18.3

COPY scanner/codety.sh /usr/bin/codety
COPY scanner/tools/ /tools/
COPY scanner/build/libs/app.jar /usr/app.jar
RUN chmod +x /usr/bin/codety
RUN curl -sfL https://oss.sonatype.org/content/repositories/releases/org/scalastyle/scalastyle_2.12/1.0.0/scalastyle_2.12-1.0.0-batch.jar > /tools/scalastyle.jar

WORKDIR /src
ENTRYPOINT ["codety"]

#use root permission by default as some(e.g. GitHub Actions) CI platforms requires.