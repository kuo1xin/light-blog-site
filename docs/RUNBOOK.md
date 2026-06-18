# 运行与验收清单

## 环境

- JDK 1.8 或更高
- Maven 3.6 或更高
- MySQL 8.0 或更高
- Node.js 14 或更高

## 启动顺序

1. MySQL 执行 `database/schema.sql`。
2. 修改 `backend/src/main/resources/application.yml` 的数据库连接。
3. `cd backend && mvn spring-boot:run`。
4. `cd frontend && npm install && npm run dev`。
5. 打开 `http://localhost:5173`。

npm 官方源连接失败时，可执行：

```bash
npm install --registry=https://registry.npmmirror.com
```

## 验收路径

1. 游客打开首页，查看公开文章和文章评论。
2. 注册新会员并登录。
3. 在个人中心修改资料、创建分类、设置博客空间。
4. 发布文章，分别验证公开/不公开、允许/禁止评论。
5. 用会员账号发表评论、关注文章、关注作者。
6. 用 `admin / 123456` 登录，进入后台管理用户、文章、评论和在线用户。
