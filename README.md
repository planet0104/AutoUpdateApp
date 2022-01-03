# AutoUpdateApp (rk3399-android-8.1)

## 官网文档

wiki.friendlyarm.com/wiki/index.php/NanoPC-T4/zh

## Android 8.1源码

https://gitlab.com/friendlyelec/rk3399-android-8.1

## 下载系统签名文件

系统签名文件位置：
https://gitlab.com/friendlyelec/rk3399-android-8.1/-/tree/master/build/make/target/product/security

## 打包好的apk进行系统签名
> autoSign任务参考: https://blog.csdn.net/a_zhon/article/details/85065047

在windows系统中，安装wsl
在gradle-> Tasks -> other中执行 autoSign任务，签名成功后在rk3399-android-8.1目录下生成前面后的apk
```shell
# 系统中需要java8版本，如果没有请安装
sudo apt-get install openjdk-8-jre-headless
```

## TODO

1. autoSign写成函数，debug、release 参数自动判断
2. 判断是否windows系统，兼容linux系统生成
3. 非系统签名使用辅助模式安装 [Android-Accessibility(辅助功能/无障碍,自动安装APP)](https://www.jianshu.com/p/04ebe2641290)