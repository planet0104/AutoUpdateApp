package run.ccfish.android.autoupdateapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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