package com.datapipe.jenkins.vault.credentials.common;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import java.io.IOException;
import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.credentialsbinding.Binding;
import org.jenkinsci.plugins.credentialsbinding.BindingDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

public class VaultStringCredentialBinding extends Binding<VaultStringCredential> {


    @DataBoundConstructor
    public VaultStringCredentialBinding(String variable, String credentialsId) {
        super(variable, credentialsId);
    }

    @Override protected Class<VaultStringCredential> type() {
        return VaultStringCredential.class;
    }

    @Override public SingleEnvironment bindSingle(@NonNull Run<?,?> build,
                                                  @Nullable FilePath workspace,
                                                  @Nullable Launcher launcher,
                                                  @NonNull TaskListener listener) throws IOException, InterruptedException {
        return new SingleEnvironment(getCredentials(build).getSecret().getPlainText());
    }

    @Symbol("vaultString")
    @Extension
    public static class DescriptorImpl extends BindingDescriptor<VaultStringCredential> {

        @Override public boolean requiresWorkspace() {
            return false;
        }

        @Override protected Class<VaultStringCredential> type() {
            return VaultStringCredential.class;
        }

        @Override public String getDisplayName() {
            return "Vault Secret Text Credential";
        }

    }
}
