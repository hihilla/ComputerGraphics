// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.glsl;

import com.jogamp.common.util.PropertyAccess;
import jogamp.opengl.Debug;
import com.jogamp.common.os.Platform;
import java.util.Iterator;
import com.jogamp.opengl.GL;
import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLUniformData;
import java.util.ArrayList;
import com.jogamp.opengl.GLArrayData;
import java.util.HashMap;

public class ShaderState
{
    public static final boolean DEBUG;
    private boolean verbose;
    private ShaderProgram shaderProgram;
    private final HashMap<String, Boolean> activedAttribEnabledMap;
    private final HashMap<String, Integer> activeAttribLocationMap;
    private final HashMap<String, GLArrayData> activeAttribDataMap;
    private final ArrayList<GLArrayData> managedAttributes;
    private final HashMap<String, Integer> activeUniformLocationMap;
    private final HashMap<String, GLUniformData> activeUniformDataMap;
    private final ArrayList<GLUniformData> managedUniforms;
    private final HashMap<String, Object> attachedObjectsByString;
    private boolean resetAllShaderData;
    
    public ShaderState() {
        this.verbose = ShaderState.DEBUG;
        this.shaderProgram = null;
        this.activedAttribEnabledMap = new HashMap<String, Boolean>();
        this.activeAttribLocationMap = new HashMap<String, Integer>();
        this.activeAttribDataMap = new HashMap<String, GLArrayData>();
        this.managedAttributes = new ArrayList<GLArrayData>();
        this.activeUniformLocationMap = new HashMap<String, Integer>();
        this.activeUniformDataMap = new HashMap<String, GLUniformData>();
        this.managedUniforms = new ArrayList<GLUniformData>();
        this.attachedObjectsByString = new HashMap<String, Object>();
        this.resetAllShaderData = false;
    }
    
    public boolean verbose() {
        return this.verbose;
    }
    
    public void setVerbose(final boolean b) {
        this.verbose = (ShaderState.DEBUG || b);
    }
    
    public final Object getAttachedObject(final String s) {
        return this.attachedObjectsByString.get(s);
    }
    
    public final Object attachObject(final String s, final Object o) {
        return this.attachedObjectsByString.put(s, o);
    }
    
    public final Object detachObject(final String s) {
        return this.attachedObjectsByString.remove(s);
    }
    
    public synchronized void useProgram(final GL2ES2 allAttributes, final boolean b) throws GLException {
        if (null == this.shaderProgram) {
            throw new GLException("No program is attached");
        }
        if (b) {
            if (this.shaderProgram.linked()) {
                this.shaderProgram.useProgram(allAttributes, true);
                if (this.resetAllShaderData) {
                    this.resetAllAttributes(allAttributes);
                    this.resetAllUniforms(allAttributes);
                }
            }
            else {
                if (this.resetAllShaderData) {
                    this.setAllAttributes(allAttributes);
                }
                if (!this.shaderProgram.link(allAttributes, System.err)) {
                    throw new GLException("could not link program: " + this.shaderProgram);
                }
                this.shaderProgram.useProgram(allAttributes, true);
                if (this.resetAllShaderData) {
                    this.resetAllUniforms(allAttributes);
                }
            }
            this.resetAllShaderData = false;
        }
        else {
            this.shaderProgram.useProgram(allAttributes, false);
        }
    }
    
    public boolean linked() {
        return null != this.shaderProgram && this.shaderProgram.linked();
    }
    
    public boolean inUse() {
        return null != this.shaderProgram && this.shaderProgram.inUse();
    }
    
    public synchronized boolean attachShaderProgram(final GL2ES2 gl2ES2, final ShaderProgram shaderProgram, final boolean b) throws GLException {
        if (this.verbose) {
            System.err.println("ShaderState: attachShaderProgram: " + ((null != this.shaderProgram) ? this.shaderProgram.id() : -1) + " -> " + ((null != shaderProgram) ? shaderProgram.id() : -1) + " (enable: " + b + ")\n\t" + this.shaderProgram + "\n\t" + shaderProgram);
            if (ShaderState.DEBUG) {
                ExceptionUtils.dumpStack(System.err);
            }
        }
        if (null != this.shaderProgram) {
            if (this.shaderProgram.equals(shaderProgram)) {
                if (b) {
                    this.useProgram(gl2ES2, true);
                }
                if (this.verbose) {
                    System.err.println("ShaderState: attachShaderProgram: No switch, equal id: " + this.shaderProgram.id() + ", enabling " + b);
                }
                return false;
            }
            if (this.shaderProgram.inUse()) {
                if (null != shaderProgram && b) {
                    this.shaderProgram.notifyNotInUse();
                }
                else {
                    this.useProgram(gl2ES2, false);
                }
            }
            this.resetAllShaderData = true;
        }
        this.shaderProgram = shaderProgram;
        if (null != this.shaderProgram && (this.resetAllShaderData || b)) {
            this.useProgram(gl2ES2, true);
            if (!b) {
                this.useProgram(gl2ES2, false);
            }
        }
        if (ShaderState.DEBUG) {
            System.err.println("Info: attachShaderProgram: END");
        }
        return true;
    }
    
    public ShaderProgram shaderProgram() {
        return this.shaderProgram;
    }
    
    public synchronized void destroy(final GL2ES2 gl2ES2) {
        this.release(gl2ES2, true, true, true);
        this.attachedObjectsByString.clear();
    }
    
    public synchronized void releaseAllData(final GL2ES2 gl2ES2) {
        this.release(gl2ES2, false, false, false);
    }
    
    public synchronized void release(final GL2ES2 gl2ES2, final boolean b, final boolean b2, final boolean b3) {
        if (null != this.shaderProgram && this.shaderProgram.linked()) {
            this.shaderProgram.useProgram(gl2ES2, false);
        }
        if (b) {
            final Iterator<GLArrayData> iterator = this.managedAttributes.iterator();
            while (iterator.hasNext()) {
                iterator.next().destroy(gl2ES2);
            }
        }
        this.releaseAllAttributes(gl2ES2);
        this.releaseAllUniforms(gl2ES2);
        if (null != this.shaderProgram && b2) {
            this.shaderProgram.release(gl2ES2, b3);
        }
    }
    
    public int getCachedAttribLocation(final String s) {
        final Integer n = this.activeAttribLocationMap.get(s);
        return (null != n) ? n : -1;
    }
    
    public GLArrayData getAttribute(final String s) {
        return this.activeAttribDataMap.get(s);
    }
    
    public boolean isActiveAttribute(final GLArrayData glArrayData) {
        return glArrayData == this.activeAttribDataMap.get(glArrayData.getName());
    }
    
    public void ownAttribute(final GLArrayData glArrayData, final boolean b) {
        if (b) {
            final int cachedAttribLocation = this.getCachedAttribLocation(glArrayData.getName());
            if (0 <= cachedAttribLocation) {
                glArrayData.setLocation(cachedAttribLocation);
            }
            this.managedAttributes.add(this.managedAttributes.size(), glArrayData);
        }
        else {
            this.managedAttributes.remove(glArrayData);
        }
        glArrayData.associate(this, b);
    }
    
    public boolean ownsAttribute(final GLArrayData glArrayData) {
        return this.managedAttributes.contains(glArrayData);
    }
    
    public void bindAttribLocation(final GL2ES2 gl2ES2, final int n, final String s) {
        if (null == this.shaderProgram) {
            throw new GLException("No program is attached");
        }
        if (this.shaderProgram.linked()) {
            throw new GLException("Program is already linked");
        }
        this.activeAttribLocationMap.put(s, n);
        gl2ES2.glBindAttribLocation(this.shaderProgram.program(), n, s);
    }
    
    public void bindAttribLocation(final GL2ES2 gl2ES2, final int n, final GLArrayData glArrayData) {
        if (null == this.shaderProgram) {
            throw new GLException("No program is attached");
        }
        if (this.shaderProgram.linked()) {
            throw new GLException("Program is already linked");
        }
        this.activeAttribLocationMap.put(glArrayData.getName(), n);
        glArrayData.setLocation(gl2ES2, this.shaderProgram.program(), n);
        this.activeAttribDataMap.put(glArrayData.getName(), glArrayData);
    }
    
    public int getAttribLocation(final GL2ES2 gl2ES2, final String s) {
        if (null == this.shaderProgram) {
            throw new GLException("No program is attached");
        }
        int n = this.getCachedAttribLocation(s);
        if (0 > n) {
            if (!this.shaderProgram.linked()) {
                throw new GLException("Program is not linked");
            }
            n = gl2ES2.glGetAttribLocation(this.shaderProgram.program(), s);
            if (0 <= n) {
                this.activeAttribLocationMap.put(s, n);
                if (ShaderState.DEBUG) {
                    System.err.println("ShaderState: glGetAttribLocation: " + s + ", loc: " + n);
                }
            }
            else if (this.verbose) {
                System.err.println("ShaderState: glGetAttribLocation failed, no location for: " + s + ", loc: " + n);
                if (ShaderState.DEBUG) {
                    ExceptionUtils.dumpStack(System.err);
                }
            }
        }
        return n;
    }
    
    public int getAttribLocation(final GL2ES2 gl2ES2, final GLArrayData glArrayData) {
        if (null == this.shaderProgram) {
            throw new GLException("No program is attached");
        }
        final String name = glArrayData.getName();
        int location = this.getCachedAttribLocation(name);
        if (0 <= location) {
            glArrayData.setLocation(location);
        }
        else {
            if (!this.shaderProgram.linked()) {
                throw new GLException("Program is not linked");
            }
            location = glArrayData.setLocation(gl2ES2, this.shaderProgram.program());
            if (0 <= location) {
                this.activeAttribLocationMap.put(name, location);
                if (ShaderState.DEBUG) {
                    System.err.println("ShaderState: glGetAttribLocation: " + name + ", loc: " + location);
                }
            }
            else if (this.verbose) {
                System.err.println("ShaderState: glGetAttribLocation failed, no location for: " + name + ", loc: " + location);
                if (ShaderState.DEBUG) {
                    ExceptionUtils.dumpStack(System.err);
                }
            }
        }
        this.activeAttribDataMap.put(glArrayData.getName(), glArrayData);
        return location;
    }
    
    public final boolean isVertexAttribArrayEnabled(final String s) {
        final Boolean b = this.activedAttribEnabledMap.get(s);
        return null != b && b;
    }
    
    public final boolean isVertexAttribArrayEnabled(final GLArrayData glArrayData) {
        return this.isVertexAttribArrayEnabled(glArrayData.getName());
    }
    
    private boolean enableVertexAttribArray(final GL2ES2 gl2ES2, final String s, int attribLocation) {
        this.activedAttribEnabledMap.put(s, Boolean.TRUE);
        if (0 > attribLocation) {
            attribLocation = this.getAttribLocation(gl2ES2, s);
            if (0 > attribLocation) {
                if (this.verbose) {
                    System.err.println("ShaderState: glEnableVertexAttribArray failed, no index for: " + s);
                    if (ShaderState.DEBUG) {
                        ExceptionUtils.dumpStack(System.err);
                    }
                }
                return false;
            }
        }
        if (ShaderState.DEBUG) {
            System.err.println("ShaderState: glEnableVertexAttribArray: " + s + ", loc: " + attribLocation);
        }
        gl2ES2.glEnableVertexAttribArray(attribLocation);
        return true;
    }
    
    public boolean enableVertexAttribArray(final GL2ES2 gl2ES2, final String s) {
        return this.enableVertexAttribArray(gl2ES2, s, -1);
    }
    
    public boolean enableVertexAttribArray(final GL2ES2 gl2ES2, final GLArrayData glArrayData) {
        if (0 > glArrayData.getLocation()) {
            this.getAttribLocation(gl2ES2, glArrayData);
        }
        else {
            this.activeAttribDataMap.put(glArrayData.getName(), glArrayData);
        }
        return this.enableVertexAttribArray(gl2ES2, glArrayData.getName(), glArrayData.getLocation());
    }
    
    private boolean disableVertexAttribArray(final GL2ES2 gl2ES2, final String s, int attribLocation) {
        this.activedAttribEnabledMap.put(s, Boolean.FALSE);
        if (0 > attribLocation) {
            attribLocation = this.getAttribLocation(gl2ES2, s);
            if (0 > attribLocation) {
                if (this.verbose) {
                    System.err.println("ShaderState: glDisableVertexAttribArray failed, no index for: " + s);
                    if (ShaderState.DEBUG) {
                        ExceptionUtils.dumpStack(System.err);
                    }
                }
                return false;
            }
        }
        if (ShaderState.DEBUG) {
            System.err.println("ShaderState: glDisableVertexAttribArray: " + s);
        }
        gl2ES2.glDisableVertexAttribArray(attribLocation);
        return true;
    }
    
    public boolean disableVertexAttribArray(final GL2ES2 gl2ES2, final String s) {
        return this.disableVertexAttribArray(gl2ES2, s, -1);
    }
    
    public boolean disableVertexAttribArray(final GL2ES2 gl2ES2, final GLArrayData glArrayData) {
        if (0 > glArrayData.getLocation()) {
            this.getAttribLocation(gl2ES2, glArrayData);
        }
        return this.disableVertexAttribArray(gl2ES2, glArrayData.getName(), glArrayData.getLocation());
    }
    
    public boolean vertexAttribPointer(final GL2ES2 gl2ES2, final GLArrayData glArrayData) {
        int n = glArrayData.getLocation();
        if (0 > n) {
            n = this.getAttribLocation(gl2ES2, glArrayData);
        }
        if (0 <= n) {
            if (ShaderState.DEBUG) {
                System.err.println("ShaderState: glVertexAttribPointer: " + glArrayData);
            }
            gl2ES2.glVertexAttribPointer(glArrayData);
            return true;
        }
        return false;
    }
    
    public void releaseAllAttributes(final GL2ES2 gl2ES2) {
        if (null != this.shaderProgram) {
            final Iterator<GLArrayData> iterator = this.activeAttribDataMap.values().iterator();
            while (iterator.hasNext()) {
                this.disableVertexAttribArray(gl2ES2, iterator.next());
            }
            final Iterator<String> iterator2 = this.activedAttribEnabledMap.keySet().iterator();
            while (iterator2.hasNext()) {
                this.disableVertexAttribArray(gl2ES2, iterator2.next());
            }
        }
        this.activeAttribDataMap.clear();
        this.activedAttribEnabledMap.clear();
        this.activeAttribLocationMap.clear();
        this.managedAttributes.clear();
    }
    
    public void disableAllVertexAttributeArrays(final GL2ES2 gl2ES2, final boolean b) {
        for (final String s : this.activedAttribEnabledMap.keySet()) {
            if (b) {
                this.activedAttribEnabledMap.remove(s);
            }
            final int attribLocation = this.getAttribLocation(gl2ES2, s);
            if (0 <= attribLocation) {
                gl2ES2.glDisableVertexAttribArray(attribLocation);
            }
        }
    }
    
    private final void relocateAttribute(final GL2ES2 gl2ES2, final GLArrayData glArrayData) {
        final String name = glArrayData.getName();
        final int setLocation = glArrayData.setLocation(gl2ES2, this.shaderProgram.program());
        if (0 <= setLocation) {
            this.activeAttribLocationMap.put(name, setLocation);
            if (ShaderState.DEBUG) {
                System.err.println("ShaderState: relocateAttribute: " + name + ", loc: " + setLocation);
            }
            if (this.isVertexAttribArrayEnabled(name)) {
                gl2ES2.glEnableVertexAttribArray(setLocation);
            }
            if (glArrayData.isVBO()) {
                gl2ES2.glBindBuffer(34962, glArrayData.getVBOName());
                gl2ES2.glVertexAttribPointer(glArrayData);
                gl2ES2.glBindBuffer(34962, 0);
            }
            else {
                gl2ES2.glVertexAttribPointer(glArrayData);
            }
        }
    }
    
    private final void resetAllAttributes(final GL2ES2 gl2ES2) {
        if (!this.shaderProgram.linked()) {
            throw new GLException("Program is not linked");
        }
        this.activeAttribLocationMap.clear();
        for (int i = 0; i < this.managedAttributes.size(); ++i) {
            this.managedAttributes.get(i).setLocation(-1);
        }
        final Iterator<GLArrayData> iterator = this.activeAttribDataMap.values().iterator();
        while (iterator.hasNext()) {
            this.relocateAttribute(gl2ES2, iterator.next());
        }
    }
    
    private final void setAttribute(final GL2ES2 gl2ES2, final GLArrayData glArrayData) {
        final String name = glArrayData.getName();
        final int location = glArrayData.getLocation();
        if (0 <= location) {
            this.bindAttribLocation(gl2ES2, location, name);
            if (this.isVertexAttribArrayEnabled(name)) {
                gl2ES2.glEnableVertexAttribArray(location);
            }
            if (glArrayData.isVBO()) {
                gl2ES2.glBindBuffer(34962, glArrayData.getVBOName());
                gl2ES2.glVertexAttribPointer(glArrayData);
                gl2ES2.glBindBuffer(34962, 0);
            }
            else {
                gl2ES2.glVertexAttribPointer(glArrayData);
            }
        }
    }
    
    private final void setAllAttributes(final GL2ES2 gl2ES2) {
        final Iterator<GLArrayData> iterator = this.activeAttribDataMap.values().iterator();
        while (iterator.hasNext()) {
            this.setAttribute(gl2ES2, iterator.next());
        }
    }
    
    public final int getCachedUniformLocation(final String s) {
        final Integer n = this.activeUniformLocationMap.get(s);
        return (null != n) ? n : -1;
    }
    
    public void ownUniform(final GLUniformData glUniformData) {
        final int cachedUniformLocation = this.getCachedUniformLocation(glUniformData.getName());
        if (0 <= cachedUniformLocation) {
            glUniformData.setLocation(cachedUniformLocation);
        }
        this.activeUniformDataMap.put(glUniformData.getName(), glUniformData);
        this.managedUniforms.add(glUniformData);
    }
    
    public boolean ownsUniform(final GLUniformData glUniformData) {
        return this.managedUniforms.contains(glUniformData);
    }
    
    public final int getUniformLocation(final GL2ES2 gl2ES2, final String s) {
        if (!this.shaderProgram.inUse()) {
            throw new GLException("Program is not in use");
        }
        int n = this.getCachedUniformLocation(s);
        if (0 > n) {
            if (!this.shaderProgram.linked()) {
                throw new GLException("Program is not linked");
            }
            n = gl2ES2.glGetUniformLocation(this.shaderProgram.program(), s);
            if (0 <= n) {
                this.activeUniformLocationMap.put(s, n);
            }
            else if (this.verbose) {
                System.err.println("ShaderState: glUniform failed, no location for: " + s + ", index: " + n);
                if (ShaderState.DEBUG) {
                    ExceptionUtils.dumpStack(System.err);
                }
            }
        }
        return n;
    }
    
    public int getUniformLocation(final GL2ES2 gl2ES2, final GLUniformData glUniformData) {
        if (!this.shaderProgram.inUse()) {
            throw new GLException("Program is not in use");
        }
        final String name = glUniformData.getName();
        int location = this.getCachedUniformLocation(name);
        if (0 <= location) {
            glUniformData.setLocation(location);
        }
        else {
            if (!this.shaderProgram.linked()) {
                throw new GLException("Program is not linked");
            }
            location = glUniformData.setLocation(gl2ES2, this.shaderProgram.program());
            if (0 <= location) {
                this.activeUniformLocationMap.put(name, location);
            }
            else if (this.verbose) {
                System.err.println("ShaderState: glUniform failed, no location for: " + name + ", index: " + location);
                if (ShaderState.DEBUG) {
                    ExceptionUtils.dumpStack(System.err);
                }
            }
        }
        this.activeUniformDataMap.put(name, glUniformData);
        return location;
    }
    
    public boolean uniform(final GL2ES2 gl2ES2, final GLUniformData glUniformData) {
        if (!this.shaderProgram.inUse()) {
            throw new GLException("Program is not in use");
        }
        int n = glUniformData.getLocation();
        if (0 > n) {
            n = this.getUniformLocation(gl2ES2, glUniformData);
        }
        if (0 <= n) {
            if (ShaderState.DEBUG) {
                System.err.println("ShaderState: glUniform: " + glUniformData);
            }
            gl2ES2.glUniform(glUniformData);
            return true;
        }
        return false;
    }
    
    public GLUniformData getUniform(final String s) {
        return this.activeUniformDataMap.get(s);
    }
    
    public void releaseAllUniforms(final GL2ES2 gl2ES2) {
        this.activeUniformDataMap.clear();
        this.activeUniformLocationMap.clear();
        this.managedUniforms.clear();
    }
    
    private final void resetAllUniforms(final GL2ES2 gl2ES2) {
        if (!this.shaderProgram.inUse()) {
            throw new GLException("Program is not in use");
        }
        this.activeUniformLocationMap.clear();
        final Iterator<GLUniformData> iterator = this.managedUniforms.iterator();
        while (iterator.hasNext()) {
            iterator.next().setLocation(-1);
        }
        for (final GLUniformData glUniformData : this.activeUniformDataMap.values()) {
            final int setLocation = glUniformData.setLocation(gl2ES2, this.shaderProgram.program());
            if (0 <= setLocation) {
                this.activeUniformLocationMap.put(glUniformData.getName(), setLocation);
                if (ShaderState.DEBUG) {
                    System.err.println("ShaderState: resetAllUniforms: " + glUniformData);
                }
                gl2ES2.glUniform(glUniformData);
            }
        }
    }
    
    public StringBuilder toString(StringBuilder sb, final boolean b) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("ShaderState[ ");
        sb.append(Platform.getNewline()).append(" ");
        if (null != this.shaderProgram) {
            this.shaderProgram.toString(sb);
        }
        else {
            sb.append("ShaderProgram: null");
        }
        sb.append(Platform.getNewline()).append(" enabledAttributes [");
        final Iterator<String> iterator = this.activedAttribEnabledMap.keySet().iterator();
        final Iterator<Boolean> iterator2 = this.activedAttribEnabledMap.values().iterator();
        while (iterator.hasNext()) {
            sb.append(Platform.getNewline()).append("  ").append(iterator.next()).append(": ").append(iterator2.next());
        }
        sb.append(Platform.getNewline()).append(" ],").append(" activeAttributes [");
        for (final GLArrayData glArrayData : this.activeAttribDataMap.values()) {
            if (b || 0 <= glArrayData.getLocation()) {
                sb.append(Platform.getNewline()).append("  ").append(glArrayData);
            }
        }
        sb.append(Platform.getNewline()).append(" ],").append(" managedAttributes [");
        for (final GLArrayData glArrayData2 : this.managedAttributes) {
            if (b || 0 <= glArrayData2.getLocation()) {
                sb.append(Platform.getNewline()).append("  ").append(glArrayData2);
            }
        }
        sb.append(Platform.getNewline()).append(" ],").append(" activeUniforms [");
        for (final GLUniformData glUniformData : this.activeUniformDataMap.values()) {
            if (b || 0 <= glUniformData.getLocation()) {
                sb.append(Platform.getNewline()).append("  ").append(glUniformData);
            }
        }
        sb.append(Platform.getNewline()).append(" ],").append(" managedUniforms [");
        for (final GLUniformData glUniformData2 : this.managedUniforms) {
            if (b || 0 <= glUniformData2.getLocation()) {
                sb.append(Platform.getNewline()).append("  ").append(glUniformData2);
            }
        }
        sb.append(Platform.getNewline()).append(" ]").append(Platform.getNewline()).append("]");
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null, ShaderState.DEBUG).toString();
    }
    
    static {
        Debug.initSingleton();
        DEBUG = PropertyAccess.isPropertyDefined("jogl.debug.GLSLState", true);
    }
}
