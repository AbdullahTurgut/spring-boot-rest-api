package in.abdllahtrgt.restapi.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService implements ITokenBlacklistService {

    private Set<String> blacklist = new HashSet<>();

    @Override
    public void addTokenToBlacklist(String token) {
        blacklist.add(token);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }
}

