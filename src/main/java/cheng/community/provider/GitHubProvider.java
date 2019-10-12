package cheng.community.provider;

import cheng.community.dto.AccessTokenDTO;
import cheng.community.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * @author cheng
 * @date 2019/9/28 0028 上午 11:29
 */
@Component
public class GitHubProvider {

        public String getAccessToken(AccessTokenDTO accessTokenDTO){
            MediaType mediaType = MediaType.get("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
                Request request = new Request.Builder()
                        .url("https://github.com/login/oauth/access_token")
                        .post(body)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    String[] split = response.body().string().split("&");
                    String tokenstr = split[0];
                    String token = tokenstr.split("=")[1];
                    System.out.println(token);
                    return token;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

        }
        public GithubUser getuser(String accessToken){
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String string = response.body().string();
                GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                return githubUser;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        }

