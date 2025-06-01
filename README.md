# CommandBlocker
[CommandBlocker](https://github.com/YF-Eternal/CommandBlocker) 是一个 Minecraft 服务器(Spigot)的命令拦截插件。它工作在白名单模式下。
* 本插件基于 Bukkit API 开发，向下兼容到 Minecraft 1.8.x，向上兼容到最新的 1.21.x 版本。
* 推荐和 [CommandHider](https://github.com/YF-Eternal/CommandHider) 搭配使用。

## 特性
* `- '/aa'`代表除了`/aa`以外的全部拦截，子命令也拦截。
* `- '/cc dd *'`代表可以随意填写一段命令。 例如: `/cc dd abcd` 或 `/cc dd 1112` 都不会被拦截，但是 `/cc dd` 和 `/cc dd abcd 123` 都会被拦截
* `- /help [all]` 代表允许所有子命令执行。
```diff
- 此插件只会拦截命令，不会禁止玩家使用TAB补全此命令。
```

## 命令

`/commandblocker help` 查看帮助<br/>
`/commandblocker version` 查看当前插件版本<br/>
`/commandblocker reload` 重载配置文件


### 别名

`/cb` 是 `/commandblocker` 的别名。

## 配置文件(config.yml)
```yaml
  # 此插件运作于白名单模式
  # * 代表可以随意填写一段 例如: '/cc dd abcd' 或 '/cc dd 1112' 都不会被拦截，但是 '/cc dd' 和 '/cc dd abcd 123'都会被拦截
  # [all] 代表允许所有子命令

  # 提示信息
  # 支持 MiniMessage
  Message: "<red><bold>Error</bold> <dark_gray>» <red>您无权访问该命令!"

  # 白名单命令列表
  Commands:
    - /aa bb
    - /cc dd *
    - /ee ff [all]
