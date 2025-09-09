# 讯智Agent后端服务 Dockerfile
# 多阶段构建，优化镜像大小和构建效率

# 构建阶段
FROM maven:3.9.4-openjdk-17-slim AS builder

# 设置工作目录
WORKDIR /app

# 复制pom文件，利用Docker缓存层优化依赖下载
COPY pom.xml .
COPY admin/pom.xml admin/

# 下载依赖（利用缓存层）
RUN mvn dependency:go-offline -B

# 复制源代码
COPY . .

# 编译打包，跳过测试
RUN mvn clean package -Dmaven.test.skip=true -B

# 运行阶段
FROM openjdk:17-jre-slim

# 设置维护者信息
LABEL maintainer="xunzhi-agent-team"
LABEL description="Xunzhi Agent Backend Service"
LABEL version="1.0.0"

# 安装必要的系统工具
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    && rm -rf /var/lib/apt/lists/*

# 创建应用用户（安全最佳实践）
RUN groupadd -r xunzhi && useradd -r -g xunzhi xunzhi

# 设置工作目录
WORKDIR /app

# 创建日志目录
RUN mkdir -p /app/logs && chown -R xunzhi:xunzhi /app

# 从构建阶段复制jar文件
COPY --from=builder /app/admin/target/xunzhi-agent-admin-*.jar app.jar

# 设置文件权限
RUN chown xunzhi:xunzhi app.jar

# 切换到非root用户
USER xunzhi

# 暴露端口
EXPOSE 8002

# 设置环境变量
ENV JAVA_OPTS="-Xms512m -Xmx2g -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/app/logs/"
ENV SPRING_PROFILES_ACTIVE=prod

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8002/actuator/health || exit 1

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# 可选：添加启动脚本
# COPY docker-entrypoint.sh /app/
# RUN chmod +x /app/docker-entrypoint.sh
# ENTRYPOINT ["/app/docker-entrypoint.sh"]