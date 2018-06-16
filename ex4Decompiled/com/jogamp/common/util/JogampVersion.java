// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.util;

import com.jogamp.common.os.Platform;
import java.util.Iterator;
import jogamp.common.os.AndroidUtils;
import java.util.Set;
import java.util.jar.Manifest;
import java.util.jar.Attributes;

public class JogampVersion
{
    public static final Attributes.Name IMPLEMENTATION_BUILD;
    public static final Attributes.Name IMPLEMENTATION_BRANCH;
    public static final Attributes.Name IMPLEMENTATION_COMMIT;
    private static final String packageNameFAT = "com.jogamp";
    private final String packageName;
    private final Manifest mf;
    private final int hash;
    private final Attributes mainAttributes;
    private final Set<?> mainAttributeNames;
    private final String androidPackageVersionName;
    
    protected JogampVersion(final String s, final Manifest mf) {
        if (null != mf) {
            this.mf = mf;
            this.packageName = s;
        }
        else {
            final Manifest manifest = VersionUtil.getManifest(JogampVersion.class.getClassLoader(), "com.jogamp");
            if (null != manifest) {
                this.mf = manifest;
                this.packageName = "com.jogamp";
            }
            else {
                this.mf = new Manifest();
                this.packageName = s;
            }
        }
        this.hash = this.mf.hashCode();
        this.mainAttributes = this.mf.getMainAttributes();
        this.mainAttributeNames = this.mainAttributes.keySet();
        this.androidPackageVersionName = AndroidUtils.getPackageInfoVersionName(this.packageName);
    }
    
    @Override
    public final int hashCode() {
        return this.hash;
    }
    
    @Override
    public final boolean equals(final Object o) {
        return o instanceof JogampVersion && this.mf.equals(((JogampVersion)o).getManifest());
    }
    
    public final Manifest getManifest() {
        return this.mf;
    }
    
    public final String getPackageName() {
        return this.packageName;
    }
    
    public final String getAttribute(final Attributes.Name name) {
        return (null != name) ? ((String)this.mainAttributes.get(name)) : null;
    }
    
    public final String getAttribute(final String s) {
        return this.getAttribute(this.getAttributeName(s));
    }
    
    public final Attributes.Name getAttributeName(final String s) {
        for (final Attributes.Name name : this.mainAttributeNames) {
            if (name.toString().equals(s)) {
                return name;
            }
        }
        return null;
    }
    
    public final Set<?> getAttributeNames() {
        return this.mainAttributeNames;
    }
    
    public final String getExtensionName() {
        if (null != this.androidPackageVersionName) {
            return this.packageName;
        }
        return this.getAttribute(Attributes.Name.EXTENSION_NAME);
    }
    
    public final String getImplementationBuild() {
        return this.getAttribute(JogampVersion.IMPLEMENTATION_BUILD);
    }
    
    public final String getImplementationBranch() {
        return this.getAttribute(JogampVersion.IMPLEMENTATION_BRANCH);
    }
    
    public final String getImplementationCommit() {
        return this.getAttribute(JogampVersion.IMPLEMENTATION_COMMIT);
    }
    
    public final String getImplementationTitle() {
        return this.getAttribute(Attributes.Name.IMPLEMENTATION_TITLE);
    }
    
    public final String getImplementationVendor() {
        return this.getAttribute(Attributes.Name.IMPLEMENTATION_VENDOR);
    }
    
    public final String getImplementationVendorID() {
        return this.getAttribute(Attributes.Name.IMPLEMENTATION_VENDOR_ID);
    }
    
    public final String getImplementationURL() {
        return this.getAttribute(Attributes.Name.IMPLEMENTATION_URL);
    }
    
    public final String getImplementationVersion() {
        return this.getAttribute(Attributes.Name.IMPLEMENTATION_VERSION);
    }
    
    public final String getAndroidPackageVersionName() {
        return this.androidPackageVersionName;
    }
    
    public final String getSpecificationTitle() {
        return this.getAttribute(Attributes.Name.SPECIFICATION_TITLE);
    }
    
    public final String getSpecificationVendor() {
        return this.getAttribute(Attributes.Name.SPECIFICATION_VENDOR);
    }
    
    public final String getSpecificationVersion() {
        return this.getAttribute(Attributes.Name.SPECIFICATION_VERSION);
    }
    
    public final StringBuilder getFullManifestInfo(final StringBuilder sb) {
        return VersionUtil.getFullManifestInfo(this.getManifest(), sb);
    }
    
    public StringBuilder getManifestInfo(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        final String newline = Platform.getNewline();
        sb.append("Package: ").append(this.getPackageName()).append(newline);
        sb.append("Extension Name: ").append(this.getExtensionName()).append(newline);
        sb.append("Specification Title: ").append(this.getSpecificationTitle()).append(newline);
        sb.append("Specification Vendor: ").append(this.getSpecificationVendor()).append(newline);
        sb.append("Specification Version: ").append(this.getSpecificationVersion()).append(newline);
        sb.append("Implementation Title: ").append(this.getImplementationTitle()).append(newline);
        sb.append("Implementation Vendor: ").append(this.getImplementationVendor()).append(newline);
        sb.append("Implementation Vendor ID: ").append(this.getImplementationVendorID()).append(newline);
        sb.append("Implementation URL: ").append(this.getImplementationURL()).append(newline);
        sb.append("Implementation Version: ").append(this.getImplementationVersion()).append(newline);
        sb.append("Implementation Build: ").append(this.getImplementationBuild()).append(newline);
        sb.append("Implementation Branch: ").append(this.getImplementationBranch()).append(newline);
        sb.append("Implementation Commit: ").append(this.getImplementationCommit()).append(newline);
        if (null != this.getAndroidPackageVersionName()) {
            sb.append("Android Package Version: ").append(this.getAndroidPackageVersionName()).append(newline);
        }
        return sb;
    }
    
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("-----------------------------------------------------------------------------------------------------").append(Platform.getNewline());
        this.getManifestInfo(sb);
        sb.append("-----------------------------------------------------------------------------------------------------");
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    static {
        IMPLEMENTATION_BUILD = new Attributes.Name("Implementation-Build");
        IMPLEMENTATION_BRANCH = new Attributes.Name("Implementation-Branch");
        IMPLEMENTATION_COMMIT = new Attributes.Name("Implementation-Commit");
    }
}
