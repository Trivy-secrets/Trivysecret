package com.datapipe.jenkins.vault.credentials;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.api.Auth;
import com.bettercloud.vault.response.AuthResponse;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.datapipe.jenkins.vault.exception.VaultPluginException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbstractAuthenticatingVaultTokenCredentialTest {

    private Vault vault;
    private Auth auth;
    private AuthResponse authResponse;

    @Before
    public void setUp() throws Exception {
        vault = mock(Vault.class);
        auth = mock(Auth.class);
        authResponse = mock(AuthResponse.class);
        when(vault.auth()).thenReturn(auth);
        when(auth.withNameSpace(anyString())).thenReturn(auth);
        when(auth.loginByCert()).thenReturn(authResponse);
        when(authResponse.getAuthClientToken()).thenReturn("12345");
    }

    @Test
    public void nonRootNamespace() {
        ExampleVaultTokenCredential cred = new ExampleVaultTokenCredential();
        cred.setNamespace("foo");
        assertEquals("12345", cred.getToken(vault));
        verify(auth).withNameSpace("foo");
    }

    static class ExampleVaultTokenCredential extends AbstractAuthenticatingVaultTokenCredential {

        protected ExampleVaultTokenCredential() {
            super(CredentialsScope.GLOBAL, "test", "");
        }

        @Override
        protected String getToken(Auth auth) {
            try {
                return auth.loginByCert().getAuthClientToken();
            } catch (VaultException ve) {
                throw new VaultPluginException("failed", ve);
            }
        }
    }
}
