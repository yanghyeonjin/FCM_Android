package com.yanghyeonjin.fcm_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Google Play 서비스 확인 */
        isGooglePlayServicesAvailable();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* Google Play 서비스 확인 */
        isGooglePlayServicesAvailable();
    }

    private void isGooglePlayServicesAvailable() {
        /*
         * SUCCESS: Google Play Service 정상 설치 상태
         * SERVICE_MISSING: Google Play Service 없는 상태
         * SERVICE_UPDATING: Google Play Service 업데이트 중
         * SERVICE_VERSION_REQUIRED: Google Play Service 버전이 오래되어 업데이트가 필요한 상태
         * SERVICE_DISABLED: Google Play Service 비활성화 된 상태
         * SERVICE_INVALID: Google Play Service 인증되지 않은 상태
         *
         * 출처: https://blog.codejun.space/32 [CodeJUN]
         */
        GoogleApiAvailabilityLight googleApiAvailability = GoogleApiAvailabilityLight.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            switch (status) {
                case ConnectionResult.SERVICE_MISSING:
                case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                    installPlayService();
                    break;
                case ConnectionResult.SERVICE_DISABLED:
                    Toast.makeText(this, "Google Play Service 활성화 필요.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case ConnectionResult.SERVICE_INVALID:
                    Toast.makeText(this, "Google Play Service 인증 실패.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    }

    private void installPlayService() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_PACKAGE));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.setPackage("com.android.vending");
        startActivity(intent);
    }
}
