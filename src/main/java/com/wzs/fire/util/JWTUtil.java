package com.wzs.fire.util;


import com.wzs.fire.common.TokenVerificationResult;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class JWTUtil {

    // TOKEN的有效期一天（S）
    private static final int TOKEN_TIME_OUT = 3_6000;
    // 加密KEY
    private static final String TOKEN_ENCRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";
    // 最小刷新间隔(S)
    private static final int REFRESH_TIME = 300;

    // 生产ID
    public static String getToken(Long id,String roleName) {
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("userId", id);
        claimMaps.put("roleName",roleName);
        long currentTime = System.currentTimeMillis();
        return Jwts.builder().setId(UUID.randomUUID().toString()).setIssuedAt(new Date(currentTime))  //签发时间
                .setSubject("fire-system")  //说明
                .setIssuer("wzs") //签发者信息
                .setAudience("app")  //接收用户
                .compressWith(CompressionCodecs.GZIP)  //数据压缩方式
                .signWith(SignatureAlgorithm.HS512, generalKey()) //加密方式
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))  //过期时间戳
                .addClaims(claimMaps) //cla信息
                .compact();
    }

    /**
     * 获取token中的claims信息
     *
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     *
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    /**
     * 获取hearder body信息
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 是否过期
     *
     * @param claims
     * @return TokenVerificationResult
     *         VALID,
     *         EXPIRING,
     *         EXPIRED,
     *         INVALID
     */
    public static TokenVerificationResult verifyToken(Claims claims) {
        if (claims == null) {
            return TokenVerificationResult.EXPIRED;
        }
        try {
            claims.getExpiration().before(new Date());
            // 需要自动刷新TOKEN
            if ((claims.getExpiration().getTime() - System.currentTimeMillis()) > 0) {
                return TokenVerificationResult.VALID;
            } else {
                return TokenVerificationResult.EXPIRING;
            }
        } catch (ExpiredJwtException ex) {
            return TokenVerificationResult.EXPIRED;
        } catch (Exception e) {
            return TokenVerificationResult.EXPIRED;
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static void main(String[] args) {
//        System.out.println(AppJwtUtil.getToken(1L));
        Jws<Claims> jws = JWTUtil.getJws("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAADWLQQoCMQwA_5LzFpq2dM3-JqkRKiiFtKCIfzd78DbDMB-4zw4HNIp0ue0lNBUMhRMH0UShtpxQYhIsCht0nnBgpZwr5rhvYEv8trdNfZzdzHVZ56cbr6sbj-Gsr_E_C51n94bfH6o8fryAAAAA.4VIPCyU_GaTuH1CK7yxNpE-0V9bez38pYZcbpzqKcTe-_TtBClFKIUiHdEaY_NOvF8Qifq2Qe9pAOpQTtX0cCw");
        Claims claims = jws.getBody();
        System.out.println(claims.get("id"));
    }
}

