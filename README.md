# AutoUpdateApp (rk3399-android-8.1)

## 官网文档

wiki.friendlyarm.com/wiki/index.php/NanoPC-T4/zh

## Android 8.1源码

https://gitlab.com/friendlyelec/rk3399-android-8.1

## 下载系统签名文件

rk3399 android8.1系统签名文件位置：

https://gitlab.com/friendlyelec/rk3399-android-8.1/-/tree/master/build/make/target/product/security

emulator-android8.1系统签名位置：

http://androidxref.com/8.1.0_r33/xref/build/target/product/security/

在这里找到 singapk.jar：http://androidxref.com/8.1.0_r33/xref/prebuilts/sdk/tools/lib/signapk.jar

在这里找到lib64：http://androidxref.com/8.1.0_r33/xref/prebuilts/sdk/tools/linux/lib64/

## 打包好的apk进行系统签名
> autoSign任务参考: https://blog.csdn.net/a_zhon/article/details/85065047

在windows系统中，安装wsl
在gradle-> Tasks -> other中执行 autoSign任务，签名成功后在rk3399-android-8.1目录下生成前面后的apk
```shell
# 系统中需要java8版本，如果没有请安装
sudo apt-get install openjdk-8-jre-headless
```
```shell
sudo apt-get update
sudo apt-get install software-properties-common
sudo apt-add-repository 'deb http://security.debian.org/debian-security stretch/updates main'
sudo apt-get update
sudo apt-get install openjdk-8-jdk
```

## 通过 gradle 任务 进行签名

Gradle -> Tasks -> other:

AutoSignDebug

AutoSignDebugRun

AutoSignRelease

AutoSignReleaseRun

Linux下Gradle安装教程: https://gradle.org/install/

## TODO

1. Linux下的gradle编译测试
2. 非系统签名使用辅助模式安装 [Android-Accessibility(辅助功能/无障碍,自动安装APP)](https://www.jianshu.com/p/04ebe2641290)