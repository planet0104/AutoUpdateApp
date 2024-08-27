# AutoUpdateApp (rk3399-android-8.1)

# 自动升级流程

## 1.下载apk安装包

## 2.调用shell pm install安装apk

```java
String cmd_install = "pm install -r -i "+context.getPackageName()+" "+apk.getAbsolutePath();
ShellUtil.CommandResult result = ShellUtil.execCommand(cmd_install, false);
```

## 监听apk安装完成广播，重新启动程序

```java
public class PackageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {
            Toast.makeText(context, "升级成功", Toast.LENGTH_LONG).show();
            //升级成功后启动app
            Intent appIntent = new Intent(context, MainActivity.class);
            context.startActivity(appIntent);
        }
    }
}
```

# 配置System UID权限 和 apk安装完成广播

## 修改 app/src/main/AndroidManifest.xml

1. 添加安装包权限
```xml
<uses-permission android:name="android.permission.INSTALL_PACKAGES" />
```

2. 【经过Android8.0系统测试，不添加 system uid仍然可以升级成功！】 添加Sytem UID权限
```xml
<manifest android:sharedUserId="android.uid.system">
```

3. 添加apk安装完成广播
```xml
<application>
    <receiver android:name=".PackageReceiver"
        android:enabled="true"
        android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.MY_PACKAGE_REPLACED" />
        </intent-filter>
    </receiver>
</application>
```

# 签名说明

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

## Android系统签名转换成keyStore文件，这样搞可以使用AndroidStudio自动签名了

https://blog.csdn.net/wang_qian_kun/article/details/117994589

RK3399 Android 10 源码：https://gitlab.com/friendlyelec/rk3399-android-10

```shell
# 在linux控制台 中输入指令将platform.pk8生成key.pem
openssl pkcs8 -inform DER -nocrypt -in platform.pk8 -out key.pem
# 在linux控制台 platform.x509.pem生成platform.p12。 platform.p12 密码是 password。alias别名是abcd，密码是1234567
openssl pkcs12 -export -in platform.x509.pem -inkey key.pem -out platform.p12 -password pass:1234567 -name abcd
# 在windows控制台 通过指令生成keystore：
keytool -importkeystore -deststorepass 1234567 -destkeystore rk3399_android10.keystore -srckeystore platform.p12 -srcstoretype  PKCS12 -srcstorepass 1234567

# Warning:
# JKS 密钥库使用专用格式。建议使用 "keytool -importkeystore -srckeystore rk3399_android10.keystore -destkeystore rk3399_android10.keystore -deststoretype pkcs12" 迁移到行业标准格式 PKCS12。
```

## 打包好的apk进行系统签名

在windows系统中，需要安装安装wsl才能进行编译

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
