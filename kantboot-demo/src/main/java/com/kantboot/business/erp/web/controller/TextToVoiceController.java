package com.kantboot.business.erp.web.controller;

import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-erp-web/erpEnterprise")
public class TextToVoiceController {

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();



    @SneakyThrows
    public String getAccessToken() {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token?client_id=UuW7VBWuoBIaUvrUWfMnkB2g&client_secret=NdXHaRSyQKw0bkaUmJzBJcxrFeifJMsU&grant_type=client_credentials")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String jsonStr = response.body().string();
//        {"refresh_token":"25.245c9f6cde31c8cfca2a56ca8b955a2f.315360000.2052702309.282335-117126133","expires_in":2592000,"session_key":"9mzdDoPxzmL2rnwG6D+O7ZmEYvMh0JMfvE4X9b9r3hbDlQ5wDobgD0H\/K1zjdJEjDKB8aF2ZBTPUA7yRX\/KEfoZl5wR4z7g=","access_token":"24.51fc27ffd110d982534582b8570daa8c.2592000.1739934309.282335-117126133","scope":"public brain_all_scope brain_bus_ticket brain_ccount_opening brain_divorce_certificate_scope brain_document_restructure brain_doc_analysis brain_doc_classify brain_ferry_ticket brain_food_business_license brain_food_product_license brain_formula brain_form_table brain_general_struct brain_numbers brain_ocr_ doc_crop_enhance brain_ocr_ multiple_invoice brain_ocr_accurate brain_ocr_accurate_basic brain_ocr_air_ticket brain_ocr_bank_receipt_new brain_ocr_birth_certificate brain_ocr_brain_shopping_receipt brain_ocr_business_card brain_ocr_business_license brain_ocr_doc_analysis_office brain_ocr_driving_license brain_ocr_facade brain_ocr_general brain_ocr_general_basic brain_ocr_handwriting brain_ocr_health_report brain_ocr_HK_Macau_pass brain_ocr_hk_macau_taiwan_exitentrypermit brain_ocr_household_register brain_ocr_idcard brain_ocr_insurance_doc brain_ocr_invoice brain_ocr_marriage_certificate brain_ocr_medical_detail brain_ocr_medical_paper brain_ocr_medical_report_detection brain_ocr_medical_summary brain_ocr_meter brain_ocr_mixed_multi_vehicle brain_ocr_multi_idcard brain_ocr_online_taxi_itinerary brain_ocr_passport brain_ocr_pen brain_ocr_plate_number brain_ocr_quota_invoice brain_ocr_real_estate_certificate brain_ocr_receipt brain_ocr_remove_handwriting brain_ocr_road_transport_certificate brain_ocr_scope brain_ocr_social_security_card brain_ocr_taiwan_pass brain_ocr_taxi_receipt brain_ocr_three_factors_verification brain_ocr_train_ticket brain_ocr_two_factors_verification brain_ocr_used_vehicle_invoice brain_ocr_vat_invoice brain_ocr_vehicle_certificate brain_ocr_vehicle_invoice brain_ocr_vehicle_license brain_ocr_vin brain_ocr_waybill brain_ocr_webimage brain_ocr_webimage_loc brain_ocr_weigth_note brain_overseas_passport brain_qrcode brain_seal brain_solution_iocr brain_textmind_textdiff brain_textmind_textreview brain_toll_invoice brain_vat_invoice_verification brain_vehicle_registration_certificate brain_xmind_extract brain_xmind_parser_rest vis-ocr_business_license vis-ocr_household_register vis-ocr_ocr vis-ocr_plate_number vis-ocr_vis-classify_birth_certificate vis-ocr_\u4fdd\u5355\u8bc6\u522b vis-ocr_\u53f0\u6e7e\u901a\u884c\u8bc1 vis-ocr_\u5b9a\u989d\u53d1\u7968\u8bc6\u522b vis-ocr_\u673a\u52a8\u8f66\u68c0\u9a8c\u5408\u683c\u8bc1\u8bc6\u522b vis-ocr_\u673a\u52a8\u8f66\u8d2d\u8f66\u53d1\u7968\u8bc6\u522b vis-ocr_\u673a\u6253\u53d1\u7968\u8bc6\u522b vis-ocr_\u6e2f\u6fb3\u901a\u884c\u8bc1 vis-ocr_\u884c\u7a0b\u5355\u8bc6\u522b vis-ocr_\u8f66\u8f86vin\u7801\u8bc6\u522b wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test\u6743\u9650 vis-classify_flower lpq_\u5f00\u653e cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base smartapp_mapp_dev_manage iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_\u5f00\u653eScope vis-ocr_\u865a\u62df\u4eba\u7269\u52a9\u7406 idl-video_\u865a\u62df\u4eba\u7269\u52a9\u7406 smartapp_component smartapp_search_plugin avatar_video_test b2b_tp_openapi b2b_tp_openapi_online smartapp_gov_aladin_to_xcx","session_secret":"a79bd5cdb64eeb271e55f67f26f7f411"}
        JSONObject json = JSONObject.parse(jsonStr);
        // 获取access_token
        String accessToken = json.getString("access_token");
        return accessToken;
    }

}
