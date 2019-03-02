/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Datapipe, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.datapipe.jenkins.vault.model;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.ListBoxModel;
import hudson.util.ListBoxModel.Option;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import java.util.List;

/**
 * Represents a Vault secret.
 *
 * @author Peter Tierno {@literal <}ptierno{@literal @}datapipe.com{@literal >}
 */
public class VaultSecret extends AbstractDescribableImpl<VaultSecret> {

  /**
   * Default prefix is used to differentiate values having the same keys across
   * multiple Vault paths. In this way, the different secrets can both be stored
   * in Jenkins' environment under different names.
   * The default remains to NOT include a prefix so as to retain current behaviour.
   */
  private static final String NO_PREFIX = "";

  private String path;
  private String envPrefix;
  private Integer engineVersion;
  private List<VaultSecretValue> secretValues;

//  @DataBoundConstructor
  public VaultSecret(String path, List<VaultSecretValue> secretValues) {
    this(path, secretValues, NO_PREFIX);
  }

  @DataBoundConstructor
  public VaultSecret(String path, List<VaultSecretValue> secretValues, String envPrefix) {
    this.path = path;
    this.secretValues = secretValues;
    if (envPrefix != null) {
      this.envPrefix = envPrefix;
    }
    else this.envPrefix = NO_PREFIX;
  }

  @DataBoundSetter
  public void setEngineVersion(Integer engineVersion) {
    this.engineVersion = engineVersion;
  }

  public String getPath() {
    return this.path;
  }

  public Integer getEngineVersion() {
    return this.engineVersion;
  }

  public List<VaultSecretValue> getSecretValues() {
    return this.secretValues;
  }

  public String getEnvPrefix() {
    return envPrefix;
  }

  @Extension
  public static final class DescriptorImpl extends Descriptor<VaultSecret> {

    private Integer engineVersion;

    public Integer getEngineVersion() {
      return this.engineVersion;
    }

    @Override
    public String getDisplayName() {
      return "Vault Secret";
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
      this.engineVersion = Integer.parseInt(formData.getString("engineVersion"));

      save();
      return false;
    }

    public ListBoxModel doFillEngineVersionItems() {
      return new ListBoxModel(
              new Option("2", "2"),
              new Option("1", "1")
      );
    }

  }

}
