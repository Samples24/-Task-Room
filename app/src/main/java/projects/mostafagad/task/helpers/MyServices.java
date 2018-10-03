package projects.mostafagad.task.helpers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import projects.mostafagad.task.models.PostModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MyServices {


    private static Retrofit retrofit;

    public ApiInterface getAPI() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(Constants.Services_readTimeout, TimeUnit.SECONDS)
                .connectTimeout(Constants.Services_connectTimeout, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .client(getUnsafeOkHttpClient().build())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public interface ApiInterface {

        @GET("posts")
        Call<List<PostModel>> GetPosts();
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ApiInterface getDistanceAPI() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(Constants.Services_readTimeout, TimeUnit.SECONDS)
                .connectTimeout(Constants.Services_connectTimeout, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(Constants.Google_Map_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiInterface.class);
    }

}
