FROM azul/zulu-openjdk-alpine:21-latest
MAINTAINER Bill Yu
LABEL authors="Bill Yu"
LABEL version="1.0.0"

# 配置
ENV PARAMS=""
ENV JAVA_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/sun.net.util=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED"
# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 添加应用
ADD target/geekapi-gateway-engine-*.jar /geekapi-gateway-engine.jar
# 执行镜像
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /geekapi-gateway-engine.jar $PARAMS"]