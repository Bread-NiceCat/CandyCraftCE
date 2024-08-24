# 糖果世界社区版 1.21

<hr>

## 语言

[[English]](README.md)[简体中文]

<hr>

## 开源许可证

模组通过 [LGPLv3](LICENSE) 进行分发。

<hr>

## 下载

- 自动构建版([Gitee流水线](.docs/autobuild_gitee.md))
- ~~通过[亿些步骤](#编译)自己编译~~

<hr>

## 编译

1. 在根目录下运行命令:`./gradlew build`
2. 编译好的mod文件将输出在中`${引擎名}\build\libs\candycraft-x.x.x.jar`
   ***注意***: 如果遇到 *无法下载wrapper*
   请修改[gradle-wrapper.properties](gradle/wrapper/gradle-wrapper.properties):
    - `distributionUrl` 为 `https\://services.gradle.org/distributions/gradle-8.6-bin.zip`

<hr>
