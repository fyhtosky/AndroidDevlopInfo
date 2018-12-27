import android.app.Application;

import com.example.appsflyer.CommonConfig;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by fanyuanhua on 18/12/26.
 */

public class ProductFlavor {
    public static void initialize(Application application) {
        MobileAds.initialize(application, CommonConfig.sharedCommonConfig.appID4Admob);
    }
}
