package in.abdllahtrgt.restapi.service;

public interface ITokenBlacklistService {
    void addTokenToBlacklist(String token);

    boolean isTokenBlacklisted(String token);
}
