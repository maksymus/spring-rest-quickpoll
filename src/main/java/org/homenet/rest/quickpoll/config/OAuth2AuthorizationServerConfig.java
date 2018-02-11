package org.homenet.rest.quickpoll.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/*
  curl -u quickpollClient:top_secret -X POST http://localhost:8080/oauth/token -H "Accept:application/json" -d "username=mickey&password=cheese&grant_type=password"

  curl http://localhost:8080/api/oauth2/v3/polls -H "Authorization: Bearer a9d6083b-07ee-49cc-871f-4108ed2830c7"
  curl -X DELETE http://localhost:8080/api/oauth2/v3/polls/11 -H "Authorization: Bearer a9d6083b-07ee-49cc-871f-4108ed2830c7"
*/

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("quickpollClient")
                .secret("top_secret")
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .resourceIds("QuickPoll_Resources");
    }
}
