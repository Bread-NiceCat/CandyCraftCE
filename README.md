# Candycraft Community Edition

<hr>

## Languages

[English][[简体中文]](README_cn.md)

<hr>

## Licence

The mod is distributed under [LGPLv3](LICENSE).

<hr>

## Download

- Auto-built versions([Workflow](https://github.com/Bread-NiceCat/CandyCraftCE/actions/workflows/autobuild.yml))
- ~~Compile yourself by following some steps [here](#compilation)~~

<hr>

## Compilation

1. Simply execute the following command in project root directory:
    1. `./gradlew build`
    2. `./gradlew drawJars`
2. Check products in `build/buildJars/modJars`

***NOTE***: if `BUILD FAILED` or *Unable to download wrapper*, please refer to [Customized](#Customized)
<hr>

## Customized

Due to the difference of location,you'll need to change some options.

1. [gradle-wrapper.properties](gradle/wrapper/gradle-wrapper.properties) `distributionUrl`
2. [gradle.properties](gradle.properties) `enable_jbr`,`enable_cn_repo`

