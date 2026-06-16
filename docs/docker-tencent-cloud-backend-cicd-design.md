# 后端 Docker + MySQL + 腾讯云 TCR + GitHub Actions 自动部署设计

## 背景

前端 Vue 3 项目已经完成 Docker 化部署，并在前端部署文档中规划了通过容器内 nginx 将 `/api` 请求代理到 Docker 网络内的 `backend:8000`。

当前目标是把后端 Spring Boot 项目也容器化，并使用 GitHub Actions 实现 CI/CD：代码推送到 GitHub 后自动构建后端 Docker 镜像、推送到腾讯云 TCR，再 SSH 到腾讯云服务器拉取镜像并重启服务。

## 已确认信息

- 后端项目：Spring Boot 2.7.3，Java 8，Maven。
- 后端服务端口：`8000`。
- 当前数据库名：`gtStore`。
- 当前本地配置使用 MySQL：`127.0.0.1:3306/gtStore`。
- 生产部署选择：后端容器 + MySQL 容器，使用 Docker Compose 管理。
- 后端和前端部署在同一台腾讯云服务器。
- 后端镜像推送到腾讯云 TCR 仓库：`iblackboy-backend`。
- 当前没有 SQL 初始化脚本，MySQL 容器首次启动后是空库，表结构和初始数据后续手动导入。

## 推荐方案

采用“后端 + MySQL 用 Docker Compose 管理”的方案。

生产环境中会有三个核心容器：

- `frontend`：前端 nginx 容器，已经由前端项目部署。
- `backend`：后端 Spring Boot 容器。
- `mysql`：MySQL 数据库容器。

三个容器加入同一个 Docker 网络：`iblackboy-net`。

最终请求链路：

```text
浏览器
  -> http://服务器公网IP/api/...
  -> 前端 nginx 容器
  -> http://backend:8000/...
  -> 后端 Spring Boot 容器
  -> mysql:3306
  -> MySQL 容器
```

浏览器不直接访问 `backend:8000`，后端也不需要直接暴露给公网。公网只需要访问前端容器的 HTTP 端口。

## 部署架构

### Docker 网络

统一使用外部 Docker 网络：

```text
iblackboy-net
```

前端、后端、MySQL 都加入该网络。前端 nginx 可以通过服务名访问后端：

```text
http://backend:8000
```

后端可以通过服务名访问 MySQL：

```text
mysql:3306
```

### 后端容器

后端容器职责：

- 运行 Spring Boot jar。
- 监听容器内 `8000` 端口。
- 使用环境变量读取生产数据库配置。
- 不把数据库密码写死进镜像或 `application.properties`。

生产环境关键环境变量：

```text
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/gtStore?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&characterEncoding=utf8
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=<从服务器 .env 或 GitHub Secrets 注入>
```

Spring Boot 支持用环境变量覆盖配置文件中的 `spring.datasource.*` 配置，所以生产环境不需要修改 jar 包即可切换数据库地址和密码。

### MySQL 容器

MySQL 容器职责：

- 提供 `gtStore` 数据库。
- 数据持久化到 Docker volume。
- 只在 Docker 网络内给后端访问，不暴露到公网。

建议使用 Docker volume：

```text
iblackboy-mysql-data
```

这样重启或重建 MySQL 容器时不会丢失数据。

由于当前没有 SQL 初始化脚本，首次启动后数据库为空。后续需要通过手动导入 SQL 的方式创建表和数据。

## GitHub Actions CI/CD 流程

触发条件：

```text
push 到 main 分支
```

自动部署流程：

1. Checkout 后端代码。
2. 设置 JDK 8。
3. 使用 Maven 执行测试和打包。
4. 使用 Docker Buildx 构建后端 Docker 镜像。
5. 镜像打两个 tag：
   - 当前提交 SHA；
   - `latest`。
6. 登录腾讯云 TCR。
7. 推送镜像到：

```text
ccr.ccs.tencentyun.com/<命名空间>/iblackboy-backend:<git-sha>
```

8. 通过 SSH 登录腾讯云服务器。
9. 上传或更新 `docker-compose.prod.yml`。
10. 确保 `iblackboy-net` 网络存在。
11. 在服务器应用目录写入或更新 `.env`。
12. 执行：

```bash
docker compose -f docker-compose.prod.yml pull backend
docker compose -f docker-compose.prod.yml up -d mysql backend
```

13. 清理悬空镜像。

## 需要新增的文件

### 1. `Dockerfile`

用于构建后端生产镜像。

推荐使用多阶段构建：

- 第一阶段使用 Maven + JDK 8 构建 jar。
- 第二阶段使用 JRE 8 运行 jar。
- 容器工作目录使用 `/app`。
- 暴露 `8000` 端口。
- 启动命令使用 `java -jar app.jar`。

由于当前项目使用 Spring Boot 2.7，后续也可以进一步使用 Spring Boot layered jar 优化镜像缓存，但第一阶段先保持清晰可用。

### 2. `.dockerignore`

避免把无关文件复制进 Docker 构建上下文。

建议忽略：

- `.git`
- `.idea`
- `target`
- `.DS_Store`
- 日志文件
- 本地临时文件

### 3. `docker-compose.prod.yml`

生产 compose 文件定义：

- `backend` 服务。
- `mysql` 服务。
- `iblackboy-mysql-data` volume。
- 外部网络 `iblackboy-net`。

后端镜像通过环境变量传入：

```text
BACKEND_IMAGE=ccr.ccs.tencentyun.com/<命名空间>/iblackboy-backend:<git-sha>
```

MySQL 密码通过环境变量传入：

```text
MYSQL_ROOT_PASSWORD=<生产密码>
```

### 4. `.github/workflows/deploy.yml`

后端 CI/CD workflow。

职责：

- Maven 构建。
- Docker 镜像构建。
- 推送腾讯云 TCR。
- SSH 部署到腾讯云服务器。

### 5. 部署说明文档

最终还需要在项目中整理一份实施文档，记录：

- 本地验证步骤。
- 腾讯云 TCR 准备步骤。
- GitHub Secrets 配置。
- 腾讯云服务器准备步骤。
- 首次部署步骤。
- MySQL 手动导入说明。
- 前后端联通验证方式。

## GitHub Secrets 设计

需要在 GitHub 仓库 Settings → Secrets and variables → Actions 中配置：

### 腾讯云 TCR

- `TCR_REGISTRY`
  - 示例：`ccr.ccs.tencentyun.com`
- `TCR_NAMESPACE`
  - 腾讯云 TCR 命名空间
- `TCR_REPOSITORY`
  - 固定为：`iblackboy-backend`
- `TCR_USERNAME`
  - 腾讯云 TCR 用户名
- `TCR_PASSWORD`
  - 腾讯云 TCR 密码或访问凭证

### 腾讯云服务器 SSH

- `SERVER_HOST`
  - 腾讯云服务器公网 IP 或域名
- `SERVER_PORT`
  - 通常是 `22`
- `SERVER_USER`
  - 例如 `ubuntu` 或 `root`
- `SERVER_SSH_KEY`
  - 可登录服务器的 SSH 私钥
- `SERVER_APP_DIR`
  - 推荐：`/opt/iblackboy/backend`

### 后端生产环境

- `MYSQL_ROOT_PASSWORD`
  - MySQL root 密码
- `MYSQL_DATABASE`
  - 推荐：`gtStore`
- `SPRING_DATASOURCE_USERNAME`
  - 推荐：`root`
- `SPRING_DATASOURCE_PASSWORD`
  - 与 `MYSQL_ROOT_PASSWORD` 保持一致，或后续改成独立业务账号

## 腾讯云服务器准备

首次部署前需要准备：

1. 安装 Docker。
2. 安装 Docker Compose 插件。
3. 确认命令可用：

```bash
docker --version
docker compose version
```

4. 创建部署目录，例如：

```bash
sudo mkdir -p /opt/iblackboy/backend
```

5. 确保 GitHub Actions 使用的 `SERVER_USER` 对目录有写权限。
6. 确保 `SERVER_USER` 可以执行 Docker 命令。
7. 创建共享 Docker 网络，或者交给 workflow 自动创建：

```bash
docker network create iblackboy-net
```

8. 腾讯云安全组至少开放：

- `22/tcp`：SSH。
- `80/tcp`：前端 HTTP。

后端 `8000` 和 MySQL `3306` 不建议开放到公网。

## 前后端联通方式

前端生产环境应该请求：

```text
/api/...
```

前端 nginx 将 `/api` 请求代理到：

```text
http://backend:8000
```

后端 Controller 不需要感知 `/api` 前缀，因为前端 nginx 会去掉 `/api` 后再转发。

例如：

```text
浏览器请求：/api/user/list
nginx 转发：http://backend:8000/user/list
```

## 验证方式

### 本地验证

1. 本地构建后端镜像。
2. 本地启动 `mysql` 和 `backend`。
3. 检查后端容器日志。
4. 调用后端接口确认应用启动。
5. 如果没有表结构，数据库相关接口可能报表不存在，这是预期现象，需要导入 SQL 后再验证完整业务。

### 服务器验证

1. push 到 `main`。
2. 查看 GitHub Actions 是否成功。
3. 登录腾讯云 TCR，确认后端镜像 tag 存在。
4. SSH 登录服务器。
5. 执行：

```bash
docker ps
docker logs iblackboy-backend
docker logs iblackboy-mysql
```

6. 在服务器内验证 Docker 网络访问：

```bash
docker exec iblackboy-backend sh -c 'echo ok'
```

7. 浏览器访问前端页面。
8. 在前端页面触发接口请求，确认请求链路为：

```text
浏览器 -> /api -> frontend nginx -> backend -> mysql
```

## 暂不处理的内容

第一阶段先把后端容器化和 CI/CD 跑通，暂不加入：

- HTTPS 证书。
- 蓝绿发布或滚动发布。
- Kubernetes。
- 腾讯云数据库 TencentDB。
- MySQL 自动备份脚本。
- SQL 初始化脚本。
- 独立数据库业务账号。

这些可以在基础部署成功后逐步补充。

## 后续企业级升级方向

当前 B 方案适合学习完整 Docker 化部署和中小型项目部署。更企业级的生产架构通常会把 MySQL 容器替换为腾讯云数据库 TencentDB 或独立 MySQL 集群。

升级时主要修改后端环境变量：

```text
SPRING_DATASOURCE_URL=jdbc:mysql://腾讯云数据库内网地址:3306/gtStore?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8
```

后端镜像、GitHub Actions、前端 nginx `/api` 代理基本不需要大改。