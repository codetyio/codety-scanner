package io.codety.scanner.util;

import io.codety.scanner.service.dto.AnalyzerRequest;

import java.util.Map;

public class DistributionRenderUtil {
    public static String getTitleBannerMarkdown(AnalyzerRequest analyzerRequest) {
        int accountType = analyzerRequest.getCodetyAccountType();
        Map<Integer, String> bannerMap = Map.of(
                -1, "banner.svg",
                0, "banner.svg"
                ,1, "banner-adv.svg"
                ,2, "banner-ent.svg"
        );

        String banner = bannerMap.get(1);
        if(bannerMap.containsKey(accountType)){
            banner = bannerMap.get(accountType);
        }
        return "[![Codety](https://www.codety.io/dist/img/"+banner+"?v=" + System.currentTimeMillis() + ")](https://codety.io)";
    }
}
