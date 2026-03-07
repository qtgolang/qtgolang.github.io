# 配置说明（无后台，全部由 JSON 配置）

## project.json - 项目介绍
- title, subtitle, description, features[], highlight, license, licenseText[], copyright
- 修改后刷新页面即可生效

## versions.json - 版本下载
- **latest**: 最新版本 { version, date, downloadUrl, **downloadPassword**（可选）, description }
- **history**: 历史版本数组，每项同上
- **downloadPassword**：下载密码（可选），有则页面上显示并可复制；为空则不显示该行
- 将 downloadUrl 改为你的实际发布地址（如 GitHub Releases）

## examples.json - 示例代码（支持多语言、多文件）
- **examples**: 数组，每项为：
  - **id**: 示例唯一标识，对应目录 `public/examples/<id>/`
  - **name**: 展示名称
  - **description**: 简短说明
  - **languages**: 对象，key 为语言名（支持：C、CSharp、Java、Android、Python、易语言），value 为该语言下的**文件名数组**
- 示例文件路径：`public/examples/<id>/<语言名>/<文件名>`
- 例如：id 为 `http-request`，Java 配置了 `["Main.java","HttpClient.java"]`，则文件放在：
  - `public/examples/http-request/Java/Main.java`
  - `public/examples/http-request/Java/HttpClient.java`

## friend-links.json - 友情链接
- **links**: 数组，每项 { name, url, description? }

## tutorial.json - 教程资源（按语言分类）
- **title**: 页面标题
- **description**: 页面说明
- **languages**: 对象，key 为语言分类名（如 C语言、Java、易语言），value 为该语言下的教程数组
  - 每项：{ title, url?, description? }
  - **若某语言对应数组为空，则该分类不显示**
- 示例：`"languages": { "C语言": [...], "Java": [], "易语言": [...] }` 中 Java 为空，页面上只显示 C语言、易语言 两个分类

## contact.json - 联系我们
- **title**: 页面标题
- **description**: 可选说明
- **items**: 数组，每项：
  - **label**: 显示名称（如「SunnyNet QQ交流群(一群）」）
  - **type**: `"qq"` 或 `"link"`。qq 显示 value 并提供复制；link 显示 url 并提供打开链接
  - **value**: type 为 qq 时的群号
  - **url**: type 为 link 时的链接地址

## donate.json - 打赏
- title, description, **methods**: [{ name, description, imageUrl? }]
