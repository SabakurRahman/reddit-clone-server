package com.sabakurreddit.reddit.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
@Service
public class JwtService {
    private  static  final String SECRATE_KEY = "782F413F4428472B4B6250655367566B5970337336763979244226452948404D";
    private static final Set<String> activeTokens = new HashSet<>();
    public String exterctUsername(String token) {
        return  extractClaims(token,Claims::getSubject);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(
            Map<String,Object> extraClims,
            UserDetails userDetails
    ){
       String token = Jwts
                .builder()
                .setClaims(extraClims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plusMillis(exPirationTime())))
                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                .compact();



        activeTokens.add(token);
        return token;

    }
    public void invalidateToken(String token) {
        activeTokens.remove(token);
    }

    public Long exPirationTime() {
      return System.currentTimeMillis()+100*60*24;
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=exterctUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpaired(token);

    }

    private boolean isTokenExpaired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInkey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRATE_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
