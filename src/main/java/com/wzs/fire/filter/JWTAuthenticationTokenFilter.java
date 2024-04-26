package com.wzs.fire.filter;

import com.mysql.cj.util.StringUtils;
import com.wzs.fire.common.TokenVerificationResult;
import com.wzs.fire.pojo.dto.UserLoginDto;
import com.wzs.fire.pojo.entity.Users;
import com.wzs.fire.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
//        String token = request.getHeader("token");
//        String token = request.getHeader("Authorization");
//        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String token = null;
        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7);
        }
        if (StringUtils.isNullOrEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        Integer userId = null;
        String roleName = null;
        //解析token
        try {
            Claims claimsBody = JWTUtil.getClaimsBody(token);
            TokenVerificationResult result = JWTUtil.verifyToken(claimsBody);
            switch (result) {
                case VALID:
                    userId = (Integer) claimsBody.get("userId");
                    roleName = (String) claimsBody.get("roleName");
                    break;
                case EXPIRING:
                    userId = (Integer) claimsBody.get("userId");
                    roleName = (String) claimsBody.get("roleName");
                    break;
                case EXPIRED:
                case INVALID:
                    throw new RuntimeException("Token is invalid or expired");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Token is invalid or expired");
        }
        //写入SecurityContextHolder
        UserLoginDto userLoginDto = new UserLoginDto(new Users());
        userLoginDto.getUser().setUserId(userId);
        List<String> authorities = Arrays.asList(roleName);
        userLoginDto.setPermission(authorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDto, null, userLoginDto.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}