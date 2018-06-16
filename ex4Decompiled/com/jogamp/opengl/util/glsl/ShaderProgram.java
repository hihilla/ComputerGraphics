// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.glsl;

import com.jogamp.common.os.Platform;
import java.io.PrintStream;
import com.jogamp.opengl.GLException;
import java.util.Iterator;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
import java.util.HashSet;

public class ShaderProgram
{
    private boolean programLinked;
    private boolean programInUse;
    private int shaderProgram;
    private final HashSet<ShaderCode> allShaderCode;
    private final HashSet<ShaderCode> attachedShaderCode;
    private final int id;
    private static int nextID;
    
    public ShaderProgram() {
        this.programLinked = false;
        this.programInUse = false;
        this.shaderProgram = 0;
        this.allShaderCode = new HashSet<ShaderCode>();
        this.attachedShaderCode = new HashSet<ShaderCode>();
        this.id = getNextID();
    }
    
    public boolean linked() {
        return this.programLinked;
    }
    
    public boolean inUse() {
        return this.programInUse;
    }
    
    public int program() {
        return this.shaderProgram;
    }
    
    public int id() {
        return this.id;
    }
    
    public synchronized void destroy(final GL2ES2 gl2ES2) {
        this.release(gl2ES2, true);
    }
    
    public synchronized void release(final GL2ES2 gl2ES2) {
        this.release(gl2ES2, false);
    }
    
    public synchronized void release(final GL2ES2 gl2ES2, final boolean b) {
        if (this.programLinked) {
            this.useProgram(gl2ES2, false);
        }
        for (final ShaderCode shaderCode : this.allShaderCode) {
            if (this.attachedShaderCode.remove(shaderCode)) {
                ShaderUtil.detachShader(gl2ES2, this.shaderProgram, shaderCode.shader());
            }
            if (b) {
                shaderCode.destroy(gl2ES2);
            }
        }
        this.allShaderCode.clear();
        this.attachedShaderCode.clear();
        if (0 != this.shaderProgram) {
            gl2ES2.glDeleteProgram(this.shaderProgram);
            this.shaderProgram = 0;
        }
    }
    
    public synchronized void add(final ShaderCode shaderCode) throws GLException {
        this.allShaderCode.add(shaderCode);
    }
    
    public synchronized boolean contains(final ShaderCode shaderCode) {
        return this.allShaderCode.contains(shaderCode);
    }
    
    public synchronized ShaderCode getShader(final int n) {
        for (final ShaderCode shaderCode : this.allShaderCode) {
            if (shaderCode.id() == n) {
                return shaderCode;
            }
        }
        return null;
    }
    
    public final synchronized boolean init(final GL2ES2 gl2ES2) {
        if (0 == this.shaderProgram) {
            this.shaderProgram = gl2ES2.glCreateProgram();
        }
        return 0 != this.shaderProgram;
    }
    
    public synchronized boolean add(final GL2ES2 gl2ES2, final ShaderCode shaderCode, final PrintStream printStream) {
        if (!this.init(gl2ES2)) {
            return false;
        }
        if (this.allShaderCode.add(shaderCode)) {
            if (!shaderCode.compile(gl2ES2, printStream)) {
                return false;
            }
            if (this.attachedShaderCode.add(shaderCode)) {
                ShaderUtil.attachShader(gl2ES2, this.shaderProgram, shaderCode.shader());
            }
        }
        return true;
    }
    
    public synchronized boolean replaceShader(final GL2ES2 gl2ES2, final ShaderCode shaderCode, final ShaderCode shaderCode2, final PrintStream printStream) {
        if (!this.init(gl2ES2) || !shaderCode2.compile(gl2ES2, printStream)) {
            return false;
        }
        final boolean inUse = this.inUse();
        if (inUse) {
            this.useProgram(gl2ES2, false);
        }
        if (null != shaderCode && this.allShaderCode.remove(shaderCode) && this.attachedShaderCode.remove(shaderCode)) {
            ShaderUtil.detachShader(gl2ES2, this.shaderProgram, shaderCode.shader());
        }
        this.add(shaderCode2);
        if (this.attachedShaderCode.add(shaderCode2)) {
            ShaderUtil.attachShader(gl2ES2, this.shaderProgram, shaderCode2.shader());
        }
        gl2ES2.glLinkProgram(this.shaderProgram);
        this.programLinked = ShaderUtil.isProgramLinkStatusValid(gl2ES2, this.shaderProgram, printStream);
        if (this.programLinked && inUse) {
            this.useProgram(gl2ES2, true);
        }
        return this.programLinked;
    }
    
    public synchronized boolean link(final GL2ES2 gl2ES2, final PrintStream printStream) {
        if (!this.init(gl2ES2)) {
            return this.programLinked = false;
        }
        for (final ShaderCode shaderCode : this.allShaderCode) {
            if (!shaderCode.compile(gl2ES2, printStream)) {
                return this.programLinked = false;
            }
            if (!this.attachedShaderCode.add(shaderCode)) {
                continue;
            }
            ShaderUtil.attachShader(gl2ES2, this.shaderProgram, shaderCode.shader());
        }
        gl2ES2.glLinkProgram(this.shaderProgram);
        return this.programLinked = ShaderUtil.isProgramLinkStatusValid(gl2ES2, this.shaderProgram, printStream);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ShaderProgram && this.id() == ((ShaderProgram)o).id());
    }
    
    @Override
    public int hashCode() {
        return this.id;
    }
    
    public StringBuilder toString(StringBuilder sb) {
        if (null == sb) {
            sb = new StringBuilder();
        }
        sb.append("ShaderProgram[id=").append(this.id);
        sb.append(", linked=" + this.programLinked + ", inUse=" + this.programInUse + ", program: " + this.shaderProgram + ",");
        final Iterator<ShaderCode> iterator = this.allShaderCode.iterator();
        while (iterator.hasNext()) {
            sb.append(Platform.getNewline()).append("   ").append(iterator.next());
        }
        sb.append("]");
        return sb;
    }
    
    @Override
    public String toString() {
        return this.toString(null).toString();
    }
    
    public synchronized boolean validateProgram(final GL2ES2 gl2ES2, final PrintStream printStream) {
        return ShaderUtil.isProgramExecStatusValid(gl2ES2, this.shaderProgram, printStream);
    }
    
    public synchronized void useProgram(final GL2ES2 gl2ES2, boolean programInUse) {
        if (!this.programLinked) {
            throw new GLException("Program is not linked");
        }
        if (this.programInUse == programInUse) {
            return;
        }
        if (0 == this.shaderProgram) {
            programInUse = false;
        }
        gl2ES2.glUseProgram(programInUse ? this.shaderProgram : 0);
        this.programInUse = programInUse;
    }
    
    public synchronized void notifyNotInUse() {
        this.programInUse = false;
    }
    
    private static synchronized int getNextID() {
        return ShaderProgram.nextID++;
    }
    
    static {
        ShaderProgram.nextID = 1;
    }
}
