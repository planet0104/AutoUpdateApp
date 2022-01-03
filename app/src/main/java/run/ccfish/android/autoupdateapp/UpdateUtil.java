package run.ccfish.android.autoupdateapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.util.Log;

import com.litesuits.common.utils.ShellUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 更新程序
 */
public class UpdateUtil {
    static final String TAG = UpdateUtil.class.getSimpleName();

    public static void updateApp(Context context, String newApkUrl) throws IOException {
        final File installDir = Environment.getExternalStorageDirectory();
        Log.i(TAG, "下载apk: "+newApkUrl);
        String apkName = context.getPackageName().replace(".", "_")+".apk";
        File new_apk = new File(installDir,  apkName);
        downloadFile(newApkUrl, new_apk.getAbsolutePath());
        Log.i(TAG, "安装包下载成功: "+new_apk.getAbsolutePath());
        installApk(context, new_apk);
        //参考 https://developer.android.google.cn/about/versions/oreo/background?hl=zh-cn
        Log.i(TAG, "apk安装成功，将会收到ACTION_MY_PACKAGE_REPLACED");
    }

    /**
     * 下载网络文件
     * @param url
     * @param file
     * @return
     */
    public static void downloadFile(String url, String file) throws IOException {
        int bytesum = 0;
        int byteread = 0;
        URL httpURL = new URL(url);
        URLConnection conn = httpURL.openConnection();
        InputStream inStream = conn.getInputStream();
        FileOutputStream fs = new FileOutputStream(file);

        byte[] buffer = new byte[1204];
        int length;
        while ((byteread = inStream.read(buffer)) != -1) {
            bytesum += byteread;
            fs.write(buffer, 0, byteread);
        }
        fs.flush();
        fs.close();
    }

    public static void installApk(Context context, File apk){
        String cmd_install = "pm install -r -i "+context.getPackageName()+" "+apk.getAbsolutePath();
        Log.i(TAG, "安装命令: "+cmd_install);
        ShellUtil.CommandResult result = ShellUtil.execCommand(cmd_install, false);
        Log.i(TAG, "安装 result="+result.responseMsg);
        Log.i(TAG, "安装 error="+result.errorMsg);
    }

    public static void startApp(Context context, File apk){
        //获取要安装的apk包名，用来启动它
        PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(apk.getAbsolutePath(), 0);
        String cmd_start = "am start "+packageInfo.packageName+"/.MainActivity";
        Log.i(TAG, "启动命令: "+cmd_start);
        ShellUtil.CommandResult result = ShellUtil.execCommand(cmd_start, false);
        Log.i(TAG, "启动 result="+result.responseMsg);
        Log.i(TAG, "启动 error="+result.errorMsg);
    }

    public static void copyFile(InputStream from, File to) throws IOException {
        OutputStream outputStream = new FileOutputStream(to);
        int length;
        byte[] bytes = new byte[1024];
        while ((length = from.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
    }
}