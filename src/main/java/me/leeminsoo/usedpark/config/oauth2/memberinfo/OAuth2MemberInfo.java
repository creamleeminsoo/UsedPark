package me.leeminsoo.usedpark.config.oauth2.memberinfo;

public interface OAuth2MemberInfo {
    String getProviderId();
    String getProvider();
    String getName();
    String getEmail();
}