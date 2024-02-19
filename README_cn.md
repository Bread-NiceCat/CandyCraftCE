# 糖果世界社区版

<hr>

## 语言

[[English]](README.md)[简体中文]

<hr>

## 开源许可证

模组通过 [LGPLv3](LICENSE) 进行分发。

<hr>

## 下载

- 自动构建版([工作流](https://github.com/Bread-NiceCat/CandyCraftCE/actions/workflows/autobuild.yml))
- ~~通过[一些步骤](#编译)自己编译~~

<hr>

## 编译

1. 在根目录下运行命令:`./gradlew build`
2. 编译好的mod文件将输出在中`${EngineName}\build\libs\candycraft-x.x.x.jar`
***注意***: 如果遇到 `BUILD FAILED` 或者 *无法下载wrapper*，请参阅[个性化](#个性化)

<hr>

## 个性化

由于地区差异，你也许需要改变某些选项

1. [gradle-wrapper.properties](gradle/wrapper/gradle-wrapper.properties) `distributionUrl`
2. [gradle.properties](gradle.properties) `enable_jbr`,`enable_cn_repo`


