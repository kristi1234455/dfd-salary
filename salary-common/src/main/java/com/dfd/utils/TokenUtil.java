package com.dfd.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yy
 * @date 2023/5/11 11:19
 */
public class TokenUtil {
//    private static Map<String, User> tokenMap = new HashMap<>();
//
//
//    public static String generateToken(User user){
//        //生成唯一不重复的字符串
//        String token = UUID.randomUUID().toString();
//        tokenMap.put(token,user);
//        return token;
//    }
//
//    /**
//     * 验证token是否合法
//     * @param token
//     * @return
//     */
//    public static  boolean verify(String token){
//        return tokenMap.containsKey(token);
//    }
//
//    public static User gentUser(String token){
//        return tokenMap.get(token);
//    }
//
//    public static void main(String[] args) {
//        for (int i = 0; i < 20; i++){
//            System.out.println(UUID.randomUUID().toString());
//        }
//    }

    //设置过期时间
//    private static final long EXPIRE_DATE=1000*60*5; //5分钟
//    private static final long EXPIRE_DATE=1000*60*120; //2小时
    private static final long EXPIRE_DATE=1000*60*1440; //24小时
    //token秘钥
    private static final String TOKEN_SECRET = "ZCfasfhuaUUHufguGuwu2020BQWf";

    /**
     * 生成token
     * @param username
     * @param password
     * @return
     */
    public static String token (String username,String password){
        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            //携带username，password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username",username)
                    .withClaim("password",password)
                    .withExpiresAt(date)
                    .sign(algorithm);

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return token;
    }

    /**
     * @desc   验证token，通过返回true
     * @params [token]需要校验的串
     **/
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            System.out.println("校验失败");
            return  false;
        }
    }

    public static void main(String[] args) {
        String username ="13419876445";
        String password = "123";
        String token = token(username,password);
        System.out.println(token);

        boolean b = verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6IjEyMyIsImV4cCI6MTY4NzY4NTcyOSwidXNlcm5hbWUiOiIxMzQxOTg3NjQ0NSJ9.Q4JGrkAPlxqp7SQz9ZvTr3z4xuQNpm-ml1zy0No3B5Y");
        System.out.println("校验结果： "+b);

        Claim username1 = JWT.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6IjEyMyIsImV4cCI6MTY4OTE0ODM0NywidXNlcm5hbWUiOiIxMzQxOTg3NjQ0NSJ9.juIiB0Y02bWLT2SXFcKnZ0UkqmhQ4SZv2BLpk59-o6o")
                .getClaim("username");
        System.out.println("我是从token中获取的信息： "+username1.asString());


    }
}
