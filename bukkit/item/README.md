# Spatula/Bukkit/Item

该项目提供自定义物品的通用接口，充当一个中间件，以消除对自定义物品插件的直接依赖。

由于自定义物品的实现需要提前加载，因此这是一个独立插件，需要安装在 Bukkit 服务端。

## 安装

客户端需要在 `plugin.yml` 或 `paper-plugin.yml` 中添加对插件 `SpatulaItems` 的依赖。

## 模块

- `api`：供其他消费者直接依赖
- `impl`：每类自定义物品的实现
- `plugin`：独立 Bukkit 插件
