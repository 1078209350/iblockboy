# 后端 Docker + MySQL + 腾讯云 TCR + GitHub Actions 自动部署步骤

## 目标

把当前 Spring Boot 后端部署为 Docker 容器，并使用 GitHub Actions 实现自动部署到腾讯云服务器。

最终链路：

```text
浏览器 -> 前端 nginx /api -> backend:8000 -> mysql:3306
```

## 一、部署架构

服务器上使用同一个 Docker 网络：

```text
iblackboy-net
```

容器：

- `iblackboy-frontend`：前端容器，来自前端项目。
- `iblackboy-backend`：后端 Spring Boot 容器。
- `iblackboy-mysql`：MySQL 容器。

后端不直接暴露公网端口，前端 nginx 通过 Docker 网络访问：

```text
http://backend:8000
```

后端通过 Docker 网络访问数据库：

```text
mysql:3306
```

## 二、腾讯云 TCR 准备

在腾讯云容器镜像服务 TCR 中准备：

- Registry，例如：`ccr.ccs.tencentyun.com`
- Namespace，例如：你的命名空间
- Repository：`iblackboy-backend`
- 用户名和密码/访问凭证

最终镜像格式：

```text
ccr.ccs.tencentyun.com/<命名空间>/iblackboy-backend:<git-sha>
```

## 三、GitHub Secrets 配置

进入 GitHub 仓库：

```text
Settings -> Secrets and variables -> Actions -> New repository secret
```

添加以下 Secrets：

### TCR 相关

```text
TCR_REGISTRY=ccr.ccs.tencentyun.com
TCR_NAMESPACE=<你的命名空间>
TCR_REPOSITORY=iblackboy-backend
TCR_USERNAME=<TCR 用户名>
TCR_PASSWORD=<TCR 密码或访问凭证>
```

### 服务器 SSH 相关

```text
SERVER_HOST=<腾讯云服务器公网 IP>
SERVER_PORT=22
SERVER_USER=<服务器用户名，例如 root 或 ubuntu>
SERVER_SSH_KEY=<能登录服务器的 SSH 私钥>
SERVER_APP_DIR=/opt/iblackboy/backend
```

### 数据库和后端环境变量

```text
MYSQL_ROOT_PASSWORD=<MySQL root 密码>
MYSQL_DATABASE=gtStore
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=<与 MYSQL_ROOT_PASSWORD 相同，或后续改成独立业务账号密码>
JAVA_OPTS=
```

如果 GitHub 不允许创建空值 Secret，可以先把 `JAVA_OPTS` 设置为：

```text
-Xms128m -Xmx256m
```

## 四、腾讯云服务器准备

SSH 登录服务器后执行。

### 1. 确认 Docker 可用

```bash
docker --version
docker compose version
```

如果命令不存在，需要先安装 Docker 和 Docker Compose 插件。

### 2. 创建部署目录

```bash
sudo mkdir -p /opt/iblackboy/backend
sudo chown -R <SERVER_USER>:<SERVER_USER> /opt/iblackboy/backend
```

把 `<SERVER_USER>` 替换成 GitHub Secrets 中的 `SERVER_USER`。如果 GitHub Actions 使用 `root` 登录，可以不执行 `chown`。

### 3. 创建共享 Docker 网络

```bash
docker network inspect iblackboy-net >/dev/null 2>&1 || docker network create iblackboy-net
```

前端、后端、MySQL 都要加入这个网络。

### 4. 检查安全组

腾讯云安全组至少开放：

- `22/tcp`：GitHub Actions SSH 部署。
- `80/tcp`：用户访问前端。

不建议开放：

- `8000/tcp`：后端端口。
- `3306/tcp`：MySQL 端口。

## 五、本地验证

### 1. Maven 构建

```bash
./mvnw clean package -DskipTests
```

成功后应生成：

```text
target/helloworld-0.0.1-SNAPSHOT.jar
```

### 2. Docker 构建

```bash
docker build -t iblackboy-backend:local .
```

### 3. Compose 配置检查

```bash
BACKEND_IMAGE=iblackboy-backend:local MYSQL_ROOT_PASSWORD=local-password SPRING_DATASOURCE_PASSWORD=local-password docker compose -f docker-compose.prod.yml config
```

如果能打印完整配置，说明 compose 文件语法正确。

## 六、触发自动部署

推送代码到 `main`：

```bash
git push origin main
```

然后进入 GitHub Actions 页面查看 workflow：

```text
Deploy Backend to Tencent Cloud
```

成功后，腾讯云 TCR 应出现两个 tag：

- `latest`
- 当前 commit SHA

## 七、服务器验证

SSH 登录腾讯云服务器。

### 1. 查看容器

```bash
docker ps
```

应看到：

```text
iblackboy-backend
iblackboy-mysql
```

### 2. 查看日志

```bash
docker logs iblackboy-mysql
docker logs iblackboy-backend
```

后端日志中应看到 Spring Boot 启动成功，并监听 `8000`。

### 3. 检查网络

```bash
docker network inspect iblackboy-net
```

确认前端容器、后端容器、MySQL 容器都在同一个网络中。

### 4. 验证前后端链路

浏览器访问前端页面，然后在页面中触发接口请求。

最终请求链路应为：

```text
浏览器 -> /api/... -> 前端 nginx -> backend:8000 -> mysql:3306
```

## 八、MySQL 数据说明

当前项目还没有 SQL 初始化脚本，所以 MySQL 容器首次启动后只有空的 `gtStore` 数据库。

如果后端接口访问数据库时报：

```text
Table 'gtStore.xxx' doesn't exist
```

说明需要先导入数据库表结构和初始数据。

可以把 SQL 文件上传到服务器后执行：

```bash
docker exec -i iblackboy-mysql mysql -uroot -p<MYSQL_ROOT_PASSWORD> gtStore < your-file.sql
```

注意：命令里的 `<MYSQL_ROOT_PASSWORD>` 替换成真实密码，`your-file.sql` 替换成服务器上的 SQL 文件路径。

## 九、常见问题

### 1. 前端请求 `/api` 返回 502

检查：

```bash
docker ps
docker logs iblackboy-backend
docker network inspect iblackboy-net
```

确认后端容器运行中，且前端和后端在同一个网络。

### 2. 后端启动失败，提示数据库连接失败

检查：

```bash
docker logs iblackboy-mysql
docker logs iblackboy-backend
```

确认 `.env` 中：

```text
MYSQL_ROOT_PASSWORD
SPRING_DATASOURCE_PASSWORD
```

一致，且 MySQL 容器健康。

### 3. GitHub Actions SSH 失败

检查 Secrets：

```text
SERVER_HOST
SERVER_PORT
SERVER_USER
SERVER_SSH_KEY
```

确认本地可以用同一把私钥 SSH 登录服务器。

### 4. TCR 登录失败

检查 Secrets：

```text
TCR_REGISTRY
TCR_NAMESPACE
TCR_REPOSITORY
TCR_USERNAME
TCR_PASSWORD
```

确认仓库 `iblackboy-backend` 已经在腾讯云 TCR 中创建。

## 十、后续升级方向

当前方案适合学习和中小型部署。更企业级的生产环境可以把 MySQL 容器替换成腾讯云数据库 TencentDB。

迁移时主要修改：

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

前端 nginx `/api` 代理、后端 Docker 镜像、GitHub Actions 部署流程基本不需要大改。
