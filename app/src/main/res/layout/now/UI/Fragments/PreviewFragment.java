package com.example.now.UI.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.now.Adapter.ImagesAdapter;
import com.example.now.GeneralClasses.Global;
import com.example.now.GeneralClasses.PreferencesHandler;
import com.example.now.Networking.ApiInterface;
import com.example.now.Networking.NetworkConstants;
import com.example.now.Networking.RetrofitClientInstance;
import com.example.now.R;
import com.example.now.UI.MainActivity;
import com.example.now.UI.MyOffersActivity;
import com.example.now.UI.SignInActivity;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.imaginativeworld.whynotimagecarousel.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;


public class PreviewFragment extends Fragment implements View.OnClickListener {

    ArrayList<Uri> uriArrayList;
    ImagesAdapter imagesAdapter;
    private RecyclerView rv_images;
    private LinearLayout back_button;
    private ImageView img_add;
    private TextView tv_next;
    private Button btn_publish;
    private TextView tv_brand_name;
    private TextView tv_product_name;
    private TextView tv_offerName;
    private TextView tv_moreDetails;
    private String brand = "";
    private String product = "";
    private String model_number = "";
    private String offerName = "";
    private String moreDetails = "";
    ApiInterface service;
    private double lat = 0;
    private double lng = 0;
    private String web_link = "";
    private String youtube_link = "";
    private String store_location = "";
    private JSONArray imageList;
    private PreferencesHandler preferencesHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        preferencesHandler = new PreferencesHandler(getActivity());
        service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        img_add = view.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        tv_next = view.findViewById(R.id.tv_next);
        tv_next.setVisibility(View.GONE);
        btn_publish = view.findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(this);
        imageList = new JSONArray();

        tv_brand_name = view.findViewById(R.id.tv_brand_name);
        tv_product_name = view.findViewById(R.id.tv_product_name);
        tv_offerName = view.findViewById(R.id.tv_offerName);
        tv_moreDetails = view.findViewById(R.id.tv_moreDetails);

        if (getArguments() != null) {
            uriArrayList = getArguments().getParcelableArrayList("images");
            brand = getArguments().getString("brand");
            product = getArguments().getString("product");
            model_number = getArguments().getString("model_number");
            offerName = getArguments().getString("offerName");
            moreDetails = getArguments().getString("moreDetails");
            lat = getArguments().getDouble("lat");
            lng = getArguments().getDouble("long");
            web_link = getArguments().getString("web_link");
            youtube_link = getArguments().getString("youtube_link");
            store_location = String.valueOf(lat) + "," + String.valueOf(lng);
            imagesAdapter = new ImagesAdapter(getActivity(), uriArrayList);
            rv_images = view.findViewById(R.id.rv_images);
            rv_images.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rv_images.setAdapter(imagesAdapter);

            tv_brand_name.setText(brand);
            tv_product_name.setText(product);
            tv_offerName.setText(offerName);
            tv_moreDetails.setText(moreDetails);
            encodeImages();
        }


    }

    private void encodeImages() {
        try {
            for (int i = 0; i < uriArrayList.size(); i++) {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriArrayList.get(i));
                Bitmap scaledBitmap = getScaledBitmap(bitmap, 250, 350);
                String base64String = getBase64String(scaledBitmap);
                Map<String, Object> itemJson = new ArrayMap<>();
                itemJson.put("b64", "data:image/png;base64," + base64String);
                itemJson.put("type", "1");
                imageList.put(new JSONObject(itemJson));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createOffer() {
//
//        JSONArray imageJSONArray = new JSONArray();
//        Map<String, Object> itemJson = new ArrayMap<>();
//        itemJson.put("b64","data:image/png;base64,"+"iVBORw0KGgoAAAANSUhEUgAAAMMAAAEECAIAAAAahxdFAAAAA3NCSVQICAjb4U/gAAAgAElEQVR4nGy925IkSZIddo6aeURWdXXPzM7uYBbEAgRAAI8kRMgHCn+BD/wJfjLf+ABZYi493VNd18yMcFM9fFA1c4+aDZlpycqM8HA308vRoxfj7/7N7wGjAIBBa5AEwMzC4VBrzRARYWYkI6K1hpArzBBEb5cOIggARgBAjDG+fH35+uWTmUXAzPKDFGD0odaaJCMlCSApiTRJ+WaSAVHovUeEWd9a/1//l//9//o//+8ffv33rV+2y5NdrPduZmYwxdOlfXc1UL0bm0lyKe/Zha/PL1++vEZEvzy9frldun73r37z/PK6ewBhW++0y+XiY2/WARvhf3n/S6BtW3/3dH1z3X71q+8R+uf/78fb7f7dd99t27Zt2y+fPrbeP3193sd4sz29vr7uQtxv795+t4+X//jv/+2bN1c0GETSzMzqGc0AgPMVEZKGQsPFFhGUA5ByZbj2BYDkkgeNpAmS0ExSa2xg0Iy9UQAiEIC7AwQkKSJcIcFceSeDUpCIuQsA6AoKZMvdaQaDALCT1t0dcgDwcHfJO4IwSOpmIgGZmbvXowbcHVQ+ABXdSAgGd0gE5e4hba0DUIgkm/Xewdi27X6/UwbUjiJEWEoMyZByNY0E6SGgRFkiBGuMiIggFYFPn3+5jXtIl9ZCY8O1QakGMnrEy47n59dfv7tu1611minCSdv3eP/x9aeffvn4y6df//rXnXja7IffvL3t9/t97PtNRrP+w3fv9v32+nz7/le/dvcvz/s+Xkjub9/4d09vn66Xy+V6vf7y/tPT9e2+P799+/br8zP7tu/71+fXZ76+fXrToMvT5Yd3T58/7+47eYFk1gkhBEPKE8mpRyaFQ5QaGGbukcsSkTusUNDYwIhBNgBkI5HLRZICaQiwkfkTSqtpNFlEYL5IQ4SMcA2FAFAE59UsRVYSKEggJQ6pNYshtgAANsWAnBQNPSIMRhJARBC1l2kVGiEDISBIQogINtv33aw3mkKiRI7w1DkhhZWX7en2cgeMzUJiqp9Rqq+TpGkzShFogElOMqZUkey9SzCz++vtdv8M/iv3vbcNCMrAAIwhtrYP/fX97cvn2z/+7vu3755IEs2B15fx8cPL//P//vzXP/3k/t/+t//53/3bf/q9Ao328vLSLts+YozXcPi+v//w4R5sfbvdbta2p6fr7XZ7Nfv69aW17d13b77/4Tt3H75H+G/+7tdj93j7NFy+j3/47W/M8Hp7efv2Te8ttYIwIYwGACFZPlf+EUCIjSLpEWFgMEwpHATAEAERQ0GjEA0cAQjM9y2hASQTFMYGpEAMRdq+WnOCAeQ/mwEiEBFgk7zTIpVZaGYjnGiEZACpKR8ADBAAmDRc0bfWgDKA1gxgCuOIaGRrtAYN9d7HGBLNzEf0fnkwuQDMFFG/hPvQ1q01ult6xrmmJGCkP6gIlyhLXvaPFFIfEAESw/3r188fPvz197/79+iQREFSZwNEM3fvvT/vt6+v8eZtvzxttuUd8vXuf/zx05//8vNP7//6RLu9Pl829mbk5fn5+fvLr/Z9D5hobNubN9+5QmP89re/JXW5bA3f0/1yufRu29Z++/e/vt/GGH278O3T0/02rLfb7RZ7XK7tet3evGkXtrdv3rCZtdxmiGg0sTzUenYAhgBCMCoiNwghSbn1ggiGYIyITgsFYOmrbBp4K21sDWjL6keYGYQhAYh0DnKRaa1LHMxCbqTS+KdFRJhZuJTGRjKwdjFtClv+wmQ9DUNrlBVYSTO4NbprBIUwcozRWkPQdYhFQAEZjSSkIEdEikXfDMHWGrlPK82IYGtAhIjTS0QoDDRjLl7ZJ5XChWRkaHy9v379/LlW30yENQi+tS2XrPf2m1+/GbcguYdf0a0Bqkd7913/x9/+h/02fvOrH77/4e127Zu1v/u7X8v4q19972Dv/Wr9cumXa2+2bdsGxuVy2bZt3O6XSzezbWvSdrn03m2MYWaXtxf27d/8/nfPz88/vL323snNQDD1CgYGBDGo3DmSqdL5LKmMgMNIIf0Om2kMAAFCQZKhTpufigaQ5lD6OR1Xw3KauVkuTwcmqdFg5gqmfXO0Rkkh0coJpmqHZKBRBovh3VgaHgVtE7WkeepAkHAPM0gw6xEBxBgy6+EOMyDl2gEzkKAYEscY6VRznyyvlY9BBrRt2+12k2LpnyQFzcqvkQQDwWnu12MwfVwJTSFB7vv+/v1fxrhfn94iBETIWkPAaU3yZvi7X72938bTtZnVzbjju7f9n/71b3744bI1vXnqby/96emptSbiH37391++fNn6pbUtGE9PT2+fertsl9YBWOO2ba1Rb68I0bBtW24PDX1rpfquN9et21Pv1lpCDlC19KEEmgVB8iOJnlVKJSsEO51+gg0yIn8TFFprCZthE2gDlNgMgIME+yFPUZ4zQRUU7o0mOJAGH1KGPg6U6Uy7ZkJAFAUlpEtc5+6tkzBJjXTfAVAIsee9khbhZpB8xRNpEzIkc899VcgJNmuJzSNdUjAIIFpray2MvbUNsMJYJ3suiSaIghM0O/DgCh/SBUR4yRustebuHz58uN/vbzTIrbZBRjQz83DKf3h7vXdsT1vrJAG3bni6tH/9+x9+7z+ExtunN6Sst5TTp2trfGfW55LZ9fveaDCaWaOZISNBTABhrWwnBaNFhBGQnq4XkiIiZFZxrCZ2Plx8Kp5ZhIsNQKOUTiOF71CvkYpL2yJGenlACooGjbWqQTSzhod1xgJQ7kbKWoQTTYglNClrEcMmlgIUPPA+wmVG0iUzkyJRkw7pIFydbNJorWWU7qKHb9YQar0zIt1Z7vRaiFK1UG9s7K4gzKx57A3N0ACCCGPrPePbZXXJWOgvJcZHSDIjwHl/6WQJ0Kyl6iDk2H/58Jfb+Cr9xhUNIR17TLQALlu/bBDhSIE2SL3hV+/e5L72ZlHshsAQsG0bWdalEWKCS7NCa0SIVvdGU8SBls8+OnHxeYkACDO2Emhcr4hozQC5GISVGAUKNi0860LA0zbAfZAUhIlTG7skAxNIJaQ/3BzN4XMH63vXjpDUxEkJOkkLBKJQPFmR0qHkMAAuGQBYRMZcUcxNutX7iDECsDEGgLRDjfXukZHqvJX8IEK7D2bYGSN1QvD84k5rdkRqy+SuqASAPKzhGzE9QlZLk0nJAx4Rz6+/3O7PKggFnULBikEsFfuQ1/S/raF3br21xq2ZzSjVcFA1019g3uphKQ+JkVTSltvmJceCYlEY9VofSYN3/iVZFsgQ01WlqUjm6ZBRUmwGY0BsJqbrKN0OKGHWglxmhlCyQQAg84kwFgY1M4BwUUGl0UnibdEHSu4GFQkJQCMblfu7IDUmj3NoTzdrrXVasy0XVB6SMoxQpFI2mzhdnru8FNTyT+njUhDTR6kwQKx9yr9OWQmaaALjrNAkDUyHmaLiipeXl+dPHyVPpTSzmHJA0mTufuyfLAoVHvArSb+6PjvRLHFDiGSAgFF2eq61oywIA0ypWMaVJNGwvqjcHJZUlowq8M3rLKbrN7lHBppZMyPKkKTAGWXTkRVLGTECI7BWdd3/Ws9GtGlgJJAMIm2M/Fj5XIf6Z8kKTWzzgps1ywWHzceJWvc0QgjRh7uHhuQjPDTSfpjZpdtmLSLcnTJDS866BC6ooI+MTCMiElK2ra+oHjDAcifMkgc63O35sSeTG3mxqejerb2+7J8/fyQ0ab1azTSf+1yFoE3Jjbyl2nk2wGB9mgRLFTrvqFKWxHyzCLEMHZm/jIUqJmQGAEVtTByArx6HM2TLgKdsmPwseeseUjJTXg2EzNhba+klAUAWkaGtUTAkY5N2Zdp1CAivpy5gF0uGyqu0EpQEQDgYCsENSKcOzBSGJeGXjJjWzUeE2QFNkDFa+RqamdF6lM32tV7pBQ6JIVPhIiIYvXfMxTBy61eSZv1Y7sRsaeSCirWSaWjPu87lJfK+XeFx//nnnytqQDSDUWUC8s");        itemJson.put("type","1");
//        imageJSONArray.put(new JSONObject(itemJson));
//


        Map<String, Object> jsonParam = new ArrayMap<>();
        jsonParam.put("type", offerName);
        jsonParam.put("description", "+" + moreDetails);
        jsonParam.put("brand_name", brand);
        jsonParam.put("product_name", product);
        jsonParam.put("model_number", model_number);
        jsonParam.put("latitude", lat);
        jsonParam.put("longitude", lng);
        jsonParam.put("video_link", youtube_link);
        jsonParam.put("store_location", store_location);
        jsonParam.put("web_link", web_link);
        jsonParam.put("images", imageList);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParam)).toString());

        Global.mKProgressHUD = KProgressHUD.create(getActivity()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.7f).setAnimationSpeed(2).setLabel("Please Wait").setCancellable(true);
        Global.mKProgressHUD.show();
        Call<ResponseBody> call = service.createOffer2(preferencesHandler.getApiToken(), body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Global.mKProgressHUD.dismiss();
                Log.d("hum", "success");
                response.body();
                removeAllFragments(getActivity().getSupportFragmentManager());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("hum", "error :: " + t.getMessage());
                Global.mKProgressHUD.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_publish:
                createOffer();
                break;
        }
    }

    private static void removeAllFragments(FragmentManager fragmentManager) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    private Bitmap getScaledBitmap(Bitmap b, int reqWidth, int reqHeight) {
        int bWidth = b.getWidth();
        int bHeight = b.getHeight();

        int nWidth = bWidth;
        int nHeight = bHeight;

        if (nWidth > reqWidth) {
            int ratio = bWidth / reqWidth;
            if (ratio > 0) {
                nWidth = reqWidth;
                nHeight = bHeight / ratio;
            }
        }

        if (nHeight > reqHeight) {
            int ratio = bHeight / reqHeight;
            if (ratio > 0) {
                nHeight = reqHeight;
                nWidth = bWidth / ratio;
            }
        }

        return Bitmap.createScaledBitmap(b, nWidth, nHeight, true);
    }

    private String getBase64String(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
    }

}