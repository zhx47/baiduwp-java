package top.zhx47.baiduwp.common.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT 服务类，负责生成、解析和验证 JWT 令牌。
 */
@Service
public class JwtService {

    // 从配置文件中读取的 JWT 密钥
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    // 从配置文件中读取的 JWT 令牌过期时间
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    /**
     * 从 JWT 令牌中提取用户名（即令牌的主题）。
     *
     * @param token JWT 令牌
     * @return 用户名
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 提取指定的声明信息（Claims）。
     *
     * @param token          JWT 令牌
     * @param claimsResolver 处理 Claims 的函数接口
     * @param <T>            返回的声明类型
     * @return 提取的声明信息
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 生成不包含额外声明的 JWT 令牌。
     *
     * @param userDetails 用户详细信息
     * @return JWT 令牌
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * 生成包含额外声明的 JWT 令牌。
     *
     * @param extraClaims 额外的声明信息
     * @param userDetails 用户详细信息
     * @return JWT 令牌
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * 构建 JWT 令牌。
     *
     * @param extraClaims 额外的声明信息
     * @param userDetails 用户详细信息
     * @param expiration  过期时间
     * @return JWT 令牌
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder().claims(extraClaims) // 设置声明信息
                .subject(userDetails.getUsername()) // 设置主题（用户名）
                .issuedAt(new Date(System.currentTimeMillis())) // 设置签发时间
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 设置过期时间
                .signWith(getSignInKey(), Jwts.SIG.HS256) // 使用签名密钥和算法签名
                .compact(); // 压缩生成 JWT 字符串
    }

    /**
     * 验证 JWT 令牌是否有效。
     *
     * @param token       JWT 令牌
     * @param userDetails 用户详细信息
     * @return 如果令牌有效且与用户匹配，则返回 true
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * 检查 JWT 令牌是否已过期。
     *
     * @param token JWT 令牌
     * @return 如果令牌已过期，则返回 true
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 提取 JWT 令牌的过期时间。
     *
     * @param token JWT 令牌
     * @return 过期时间
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 提取 JWT 令牌中的所有声明（Claims）。
     *
     * @param token JWT 令牌
     * @return 所有声明
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey())// 设置签名密钥
                .build().parseSignedClaims(token)// 解析 JWT 并返回声明主体
                .getPayload();
    }

    /**
     * 获取用于签名 JWT 的密钥。
     *
     * @return 密钥
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);// 将 Base64 编码的密钥解码
        return Keys.hmacShaKeyFor(keyBytes);// 使用解码后的字节生成 HMAC-SHA 密钥
    }
}