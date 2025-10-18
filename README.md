# shoppingProject (Spring Boot)

一个基于 Spring Boot 的简单购物项目示例，提供用户与商品的基本增删查与购物车状态管理接口，使用 MySQL 持久化存储，JPA 自动建表。

---

## 目录
- [项目简介](#项目简介)
- [技术栈](#技术栈)
- [快速开始](#快速开始)
  - [先决条件](#先决条件)
  - [安装依赖](#安装依赖)
  - [本地运行](#本地运行)
  - [生产构建与启动](#生产构建与启动)
- [环境变量](#环境变量)
- [常用脚本](#常用脚本)
- [项目结构](#项目结构)
- [API 与路由](#api-与路由)
- [数据库与迁移](#数据库与迁移)
- [测试与代码质量](#测试与代码质量)
- [部署与 Docker](#部署与-docker)
- [CI/CD 与状态徽章](#cicd-与状态徽章)
- [贡献指南](#贡献指南)
- [License](#license)

---

## 项目简介
shoppingProject 是一个演示性质的后端应用，聚焦于：
- 用户管理：查询、添加、删除
- 商品管理：按类型/名称/状态查询、添加、切换购物车状态、上传商品图片
- 使用 Spring Data JPA 连接 MySQL，自动建表

目标：帮助新成员在 5 分钟内拉起本地环境，了解主要接口与目录结构。

## 技术栈
- 语言：Java 17
- 框架：Spring Boot 3.2.x（Web、Data JPA）
- 构建/依赖：Maven（已提供 Maven Wrapper）
- 数据库：MySQL 8.x
- 其他：javax.validation API

可执行文件：使用 Spring Boot Maven Plugin 打包可执行 JAR。

## 快速开始

### 先决条件
- JDK 17（建议：Temurin/OpenJDK 17）
- MySQL 8.x（本地或远程均可）
- Git（用于拉取仓库）
- Maven 可选（仓库已包含 ./mvnw 和 mvnw.cmd）

### 安装依赖
无需手动安装，首次运行 Maven 命令会自动下载依赖。

### 本地运行
1) 准备数据库：创建名为 shopping 的数据库（见下文“数据库与迁移”）。
2) 配置数据库连接：
   - 方式 A：编辑 src/main/resources/application.yml，按需修改 spring.datasource.*（不建议提交敏感信息到仓库）。
   - 方式 B：使用环境变量覆盖（推荐，见下文“环境变量”一节）。
3) 启动开发服务：

```bash
# Mac/Linux
./mvnw spring-boot:run

# Windows (PowerShell/CMD)
mvnw.cmd spring-boot:run
```

服务默认运行在 http://localhost:8080。

### 生产构建与启动
```bash
# 构建（跳过测试示例）
./mvnw clean package -DskipTests

# 启动可执行 JAR
java -jar target/springboot-init-0.0.1-SNAPSHOT.jar
```

如需在生产/测试环境下覆盖数据库配置，请通过环境变量或外部化配置文件完成。

## 环境变量
Spring Boot 支持通过环境变量覆盖同名配置（以大写+下划线形式）。下列变量可用于替代 application.yml 内的同名配置：

- SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/shopping?useUnicode=true&characterEncoding=utf-8&useSSL=false
- SPRING_DATASOURCE_USERNAME=your_mysql_user
- SPRING_DATASOURCE_PASSWORD=your_mysql_password
- SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
- SPRING_JPA_PROPERTIES_HIBERNATE_HBM2DDL_AUTO=update
- SERVER_PORT=8080（可选）

注意：Spring Boot 不会自动读取 .env 文件；请在运行前导出环境变量，或在 IDE 运行配置/操作系统服务管理中设置。

仓库已提供 .env.example 作为参考，请勿将真实密码提交至仓库。

额外说明（需确认/改进）：
- 商品图片上传路径在 GoodsController#updateGoodsImage 中以绝对路径硬编码为 Windows 路径。建议改造为可配置项（如 FILE_UPLOAD_DIR），并在此处文档化。当前 README 仅标注该限制，尚未修改代码。

## 常用脚本
- 开发启动：./mvnw spring-boot:run
- 构建打包：./mvnw clean package [-DskipTests]
- 运行测试：./mvnw test

（本项目未配置代码格式化/静态检查插件，如需请在 Maven 中增配 Checkstyle/Spotless 等。）

## 项目结构
```text
.
├── pom.xml
├── mvnw / mvnw.cmd
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/itheima/springbootinit
│   │   │       ├── SpringbootInitApplication.java        # 启动类
│   │   │       ├── Goods
│   │   │       │   ├── Goods.java                        # 商品实体（含枚举 GoodsType）
│   │   │       │   ├── GoodsController.java              # 商品接口
│   │   │       │   └── GoodsDao.java                     # 商品 JPA 仓库
│   │   │       └── User
│   │   │           ├── User.java                         # 用户实体
│   │   │           ├── UserController.java               # 用户接口
│   │   │           └── UserDao.java                      # 用户 JPA 仓库
│   │   └── resources
│   │       ├── application.yml                           # 应用配置（含数据源配置）
│   │       └── static/images/                            # 静态资源目录（示例：商品图片）
│   └── test
│       └── java/com/itheima/springbootinit
│           └── SpringbootInitApplicationTests.java       # 基础上下文测试
└── .gitignore
```

## API 与路由
基础 URL：`http://localhost:8080`

- 用户（User）
  - GET /getOne?name=张三         按名称查询用户
  - GET /getAll                   查询所有用户
  - GET /add?name=张三&age=18&id=1&password=123456      新增用户（注意：使用 GET 添加仅为演示）
  - GET /deleteOne?name=张三      按名称删除用户
  - GET /deleteAll                删除全部用户

- 商品（Goods）
  - GET /getAllGoods                                  查询所有商品
  - GET /getByType?type=food|clothes|furnishing|study|electric|daily|medical|sport   按类型查询
  - GET /addGoods?name=苹果&description=好吃&price=10&type=food                     新增商品
  - GET /changeStatus?name=苹果                       切换是否加入购物车
  - GET /getByName?name=苹果                           按名称查询
  - GET /getByStatus?status=true|false                 按是否在购物车查询
  - POST /updateGoodsImage                             更新商品图片（multipart/form-data）
    - 参数：file（文件），goodsName（商品名，URL 编码）
    - 返回：images/{文件名}
    - 说明：当前保存路径硬编码为 Windows 路径，建议改造为配置项；静态资源访问路径形如 http://localhost:8080/images/{文件名}

示例（cURL）：
```bash
curl "http://localhost:8080/add?name=Alice&id=1&age=20&password=secret"
curl "http://localhost:8080/getAll"
curl "http://localhost:8080/addGoods?name=Apple&description=Red&price=5&type=food"
curl -X POST -F "file=@/path/to/apple.jpg" -F "goodsName=Apple" http://localhost:8080/updateGoodsImage
```

## 数据库与迁移
- 持久化：Spring Data JPA + MySQL
- 表结构：由实体 + 配置 spring.jpa.properties.hibernate.hbm2ddl.auto=update 自动维护（开发环境）
- 初始化步骤：
  1) 在 MySQL 中创建数据库：
     ```sql
     CREATE DATABASE IF NOT EXISTS shopping
       DEFAULT CHARACTER SET utf8mb4
       COLLATE utf8mb4_unicode_ci;
     ```
  2) 配置好数据源并启动应用，JPA 会自动创建/更新 `user`、`goods` 表。

生产环境建议：
- 避免使用 hbm2ddl.auto=update，改为受控迁移（如 Flyway/Liquibase），此处尚未集成（TODO）。

## 测试与代码质量
- 运行测试：`./mvnw test`
- 覆盖率：未配置（TODO 可集成 JaCoCo）
- Lint/格式化：未配置（TODO 可集成 Checkstyle/Spotless/Formatter）

## 部署与 Docker
- 直接运行可执行 JAR：`java -jar target/springboot-init-0.0.1-SNAPSHOT.jar`
- 通过环境变量覆盖数据源配置（见“环境变量”一节）。
- Docker：当前仓库未提供 Dockerfile / Compose（TODO：如需容器化部署，建议新增 Dockerfile 与 docker-compose.yml，并将数据库与上传目录抽为可配置挂载）。

## CI/CD 与状态徽章
- 当前仓库未提供 CI/CD 配置（TODO：可引入 GitHub Actions/Maven 构建流水线与质量检查）。

## 贡献指南
- 分支：以 feature/xxx、fix/xxx 命名；提交 PR 前确保构建与测试通过。
- 提交信息：建议遵循 Conventional Commits（feat/fix/docs/chore 等）。
- 代码约定：保持统一风格，避免在配置文件中提交真实凭据。
- 安全：请将 application.yml 中的数据库密码改为环境变量或使用外部化配置，避免将敏感信息提交到仓库。

## License
- 当前仓库未明确 License（TODO：确认后补充，如 MIT/Apache-2.0 等）。
