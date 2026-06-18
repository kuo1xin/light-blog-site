# 接口说明

统一前缀：`/api`

统一返回：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

登录后在请求头携带：

```text
Authorization: Bearer <token>
```

## 用户

| 方法 | URL | 说明 | 角色 |
| --- | --- | --- | --- |
| POST | `/user/register` | 注册 | 游客 |
| POST | `/user/login` | 登录 | 所有 |
| POST | `/user/logout` | 退出 | 会员/管理员 |
| GET | `/user/info` | 当前用户信息 | 会员/管理员 |
| PUT | `/user/profile` | 修改资料 | 会员/管理员 |
| PUT | `/user/password` | 修改密码 | 会员/管理员 |
| GET | `/user/stats` | 个人统计 | 会员/管理员 |

## 文章与分类

| 方法 | URL | 说明 | 角色 |
| --- | --- | --- | --- |
| GET | `/article/list` | 公开文章分页 | 所有 |
| GET | `/article/{id}` | 文章详情 | 所有 |
| GET | `/article/mine` | 我的文章 | 会员/管理员 |
| POST | `/article` | 发布文章 | 会员/管理员 |
| PUT | `/article/{id}` | 编辑文章 | 作者/管理员 |
| DELETE | `/article/{id}` | 删除文章 | 作者/管理员 |
| GET | `/category` | 我的分类 | 会员/管理员 |
| POST | `/category` | 新增分类 | 会员/管理员 |
| PUT | `/category/{id}` | 修改分类 | 创建者 |
| DELETE | `/category/{id}` | 删除分类 | 创建者 |

## 评论、关注、空间

| 方法 | URL | 说明 | 角色 |
| --- | --- | --- | --- |
| GET | `/comment/list/{articleId}` | 评论列表 | 所有 |
| POST | `/comment` | 发表评论 | 会员/管理员 |
| DELETE | `/comment/{id}` | 删除评论 | 评论作者/文章作者/管理员 |
| POST | `/follow` | 关注/取消关注作者或文章 | 会员/管理员 |
| GET | `/follow/list` | 我的关注 | 会员/管理员 |
| GET | `/space/{userId}` | 空间信息 | 所有 |
| PUT | `/space` | 保存空间信息 | 会员/管理员 |
| GET | `/space/{userId}/articles` | 空间文章 | 所有 |

## 管理员

| 方法 | URL | 说明 |
| --- | --- | --- |
| GET | `/admin/users` | 用户列表 |
| PUT | `/admin/user/{id}` | 更新用户 |
| DELETE | `/admin/user/{id}` | 删除用户 |
| GET | `/admin/articles` | 全站文章 |
| DELETE | `/admin/article/{id}` | 删除文章 |
| GET | `/admin/comments` | 全站评论 |
| DELETE | `/admin/comment/{id}` | 删除评论 |
| GET | `/admin/online` | 在线用户 |
