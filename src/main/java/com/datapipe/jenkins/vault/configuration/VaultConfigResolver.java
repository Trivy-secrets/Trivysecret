package com.datapipe.jenkins.vault.configuration;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.ExtensionPoint;
import hudson.model.Item;

public abstract class VaultConfigResolver implements ExtensionPoint {

    public abstract @NonNull
    VaultConfiguration forJob(@NonNull Item job);
}
