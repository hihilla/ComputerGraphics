// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.tt.engine;

import jogamp.graph.font.typecast.ot.Point;

public class Interpreter
{
    private Parser parser;
    private final GraphicsState gs;
    private final Point[][] zone;
    private int[] stack;
    private int[] store;
    private final int[] cvt;
    private int[] functionMap;
    private int stackIndex;
    private boolean inFuncDef;
    
    public Interpreter(final int n, final int n2, final int n3) {
        this.parser = null;
        this.gs = new GraphicsState();
        this.zone = new Point[2][];
        this.stack = null;
        this.store = null;
        this.cvt = new int[256];
        this.functionMap = null;
        this.stackIndex = 0;
        this.inFuncDef = false;
        this.zone[0] = new Point[256];
        this.zone[1] = new Point[256];
        this.stack = new int[n];
        this.store = new int[n2];
        this.functionMap = new int[n3];
    }
    
    private void _abs() {
        final int pop = this.pop();
        if (pop >= 0) {
            this.push(pop);
        }
        else {
            this.push(-pop);
        }
    }
    
    private void _add() {
        this.push(this.pop() + this.pop());
    }
    
    private void _alignpts() {
        this.pop();
        this.pop();
    }
    
    private void _alignrp() {
        while (this.gs.loop-- > 0) {
            this.pop();
        }
        this.gs.loop = 1;
    }
    
    private void _and() {
        final int pop = this.pop();
        this.push((this.pop() != 0 && pop != 0) ? 1 : 0);
    }
    
    private void _call() {
        this.execute(this.functionMap[this.pop()]);
    }
    
    private void _ceiling() {
        final int pop = this.pop();
        if (pop >= 0) {
            this.push((pop & 0xFFC0) + (((pop & 0x3F) != 0x0) ? 64 : 0));
        }
        else {
            this.push(pop & 0xFFC0);
        }
    }
    
    private void _cindex() {
        this.push(this.stack[this.stackIndex - this.pop()]);
    }
    
    private void _clear() {
        this.stackIndex = 0;
    }
    
    private void _debug() {
        this.pop();
    }
    
    private void _deltac1() {
        for (int pop = this.pop(), i = 0; i < pop; ++i) {
            this.pop();
            this.pop();
        }
    }
    
    private void _deltac2() {
        for (int pop = this.pop(), i = 0; i < pop; ++i) {
            this.pop();
            this.pop();
        }
    }
    
    private void _deltac3() {
        for (int pop = this.pop(), i = 0; i < pop; ++i) {
            this.pop();
            this.pop();
        }
    }
    
    private void _deltap1() {
        for (int pop = this.pop(), i = 0; i < pop; ++i) {
            this.pop();
            this.pop();
        }
    }
    
    private void _deltap2() {
        for (int pop = this.pop(), i = 0; i < pop; ++i) {
            this.pop();
            this.pop();
        }
    }
    
    private void _deltap3() {
        for (int pop = this.pop(), i = 0; i < pop; ++i) {
            this.pop();
            this.pop();
        }
    }
    
    private void _depth() {
        this.push(this.stackIndex);
    }
    
    private void _div() {
        this.push(this.pop() / this.pop() >> 6);
    }
    
    private void _dup() {
        final int pop = this.pop();
        this.push(pop);
        this.push(pop);
    }
    
    private int _else(final int n) {
        return this.parser.handleElse(n);
    }
    
    private void _eq() {
        this.push((this.pop() == this.pop()) ? 1 : 0);
    }
    
    private void _even() {
        this.pop();
        this.push(0);
    }
    
    private void _fdef(final int n) {
        this.functionMap[this.pop()] = n;
        this.inFuncDef = true;
    }
    
    private void _flipoff() {
        this.gs.auto_flip = false;
    }
    
    private void _flipon() {
        this.gs.auto_flip = true;
    }
    
    private void _flippt() {
        while (this.gs.loop-- > 0) {
            final int pop = this.pop();
            this.zone[this.gs.zp0][pop].onCurve = !this.zone[this.gs.zp0][pop].onCurve;
        }
        this.gs.loop = 1;
    }
    
    private void _fliprgoff() {
        for (int pop = this.pop(), i = this.pop(); i <= pop; ++i) {
            this.zone[1][i].onCurve = false;
        }
    }
    
    private void _fliprgon() {
        for (int pop = this.pop(), i = this.pop(); i <= pop; ++i) {
            this.zone[1][i].onCurve = true;
        }
    }
    
    private void _floor() {
        final int pop = this.pop();
        if (pop >= 0) {
            this.push(pop & 0xFFC0);
        }
        else {
            this.push((pop & 0xFFC0) - (((pop & 0x3F) != 0x0) ? 64 : 0));
        }
    }
    
    private void _gc(final short n) {
        this.pop();
        this.push(0);
    }
    
    private void _getinfo() {
        this.pop();
        this.push(0);
    }
    
    private void _gfv() {
        this.push(this.gs.freedom_vector[0]);
        this.push(this.gs.freedom_vector[1]);
    }
    
    private void _gpv() {
        this.push(this.gs.projection_vector[0]);
        this.push(this.gs.projection_vector[1]);
    }
    
    private void _gt() {
        this.push((this.pop() > this.pop()) ? 1 : 0);
    }
    
    private void _gteq() {
        this.push((this.pop() >= this.pop()) ? 1 : 0);
    }
    
    private void _idef() {
        this.pop();
        this.inFuncDef = true;
    }
    
    private int _if(final int n) {
        return this.parser.handleIf(this.pop() != 0, n);
    }
    
    private void _instctrl() {
        final int pop = this.pop();
        final int pop2 = this.pop();
        if (pop == 1) {
            final GraphicsState gs = this.gs;
            gs.instruction_control |= pop2;
        }
        else if (pop == 2) {
            final GraphicsState gs2 = this.gs;
            gs2.instruction_control |= pop2;
        }
    }
    
    private void _ip() {
        this.pop();
    }
    
    private void _isect() {
        this.pop();
        this.pop();
        this.pop();
        this.pop();
        this.pop();
    }
    
    private void _iup(final short n) {
    }
    
    private int _jmpr(final int n) {
        return n + (this.pop() - 1);
    }
    
    private int _jrof(int n) {
        final boolean b = this.pop() != 0;
        final int pop = this.pop();
        if (!b) {
            n += pop - 1;
        }
        return n;
    }
    
    private int _jrot(int n) {
        final boolean b = this.pop() != 0;
        final int pop = this.pop();
        if (b) {
            n += pop - 1;
        }
        return n;
    }
    
    private void _loopcall() {
        this.pop();
        for (int pop = this.pop(), i = 0; i < pop; ++i) {
            this.execute(this.functionMap[i]);
        }
    }
    
    private void _lt() {
        this.push((this.pop() < this.pop()) ? 1 : 0);
    }
    
    private void _lteq() {
        this.push((this.pop() <= this.pop()) ? 1 : 0);
    }
    
    private void _max() {
        final int pop = this.pop();
        final int pop2 = this.pop();
        this.push((pop > pop2) ? pop : pop2);
    }
    
    private void _md(final short n) {
        this.pop();
        this.pop();
        this.push(0);
    }
    
    private void _mdap(final short n) {
        this.pop();
    }
    
    private void _mdrp(final short n) {
        this.pop();
    }
    
    private void _miap(final short n) {
        this.pop();
        this.pop();
    }
    
    private void _min() {
        final int pop = this.pop();
        final int pop2 = this.pop();
        this.push((pop < pop2) ? pop : pop2);
    }
    
    private void _mindex() {
        final int pop = this.pop();
        final int n = this.stack[this.stackIndex - pop];
        for (int i = this.stackIndex - pop; i < this.stackIndex - 1; ++i) {
            this.stack[i] = this.stack[i + 1];
        }
        this.stack[this.stackIndex - 1] = n;
    }
    
    private void _mirp(final short n) {
        this.pop();
        this.pop();
    }
    
    private void _mppem() {
        this.push(0);
    }
    
    private void _mps() {
        this.push(0);
    }
    
    private void _msirp(final short n) {
        this.pop();
        this.pop();
    }
    
    private void _mul() {
        this.push(this.pop() * this.pop() >> 6);
    }
    
    private void _neg() {
        this.push(-this.pop());
    }
    
    private void _neq() {
        this.push((this.pop() != this.pop()) ? 1 : 0);
    }
    
    private void _not() {
        this.push((this.pop() == 0) ? 1 : 0);
    }
    
    private void _nround(final short n) {
        this.pop();
        this.push(0);
    }
    
    private void _odd() {
        this.pop();
        this.push(0);
    }
    
    private void _or() {
        final int pop = this.pop();
        this.push((this.pop() != 0 || pop != 0) ? 1 : 0);
    }
    
    private void _push(final int[] array) {
        for (int i = 0; i < array.length; ++i) {
            this.push(array[i]);
        }
    }
    
    private void _rcvt() {
        this.push(this.cvt[this.pop()]);
    }
    
    private void _rdtg() {
        this.gs.round_state = 3;
    }
    
    private void _roff() {
        this.gs.round_state = 5;
    }
    
    private void _roll() {
        final int pop = this.pop();
        final int pop2 = this.pop();
        final int pop3 = this.pop();
        this.push(pop2);
        this.push(pop);
        this.push(pop3);
    }
    
    private void _round(final short n) {
        this.pop();
        this.push(0);
    }
    
    private void _rs() {
        this.push(this.store[this.pop()]);
    }
    
    private void _rtdg() {
        this.gs.round_state = 2;
    }
    
    private void _rtg() {
        this.gs.round_state = 1;
    }
    
    private void _rthg() {
        this.gs.round_state = 0;
    }
    
    private void _rutg() {
        this.gs.round_state = 4;
    }
    
    private void _s45round() {
        this.pop();
    }
    
    private void _scanctrl() {
        this.gs.scan_control = this.pop();
    }
    
    private void _scantype() {
        this.pop();
    }
    
    private void _scfs() {
        this.pop();
        this.pop();
    }
    
    private void _scvtci() {
        this.gs.control_value_cut_in = this.pop();
    }
    
    private void _sdb() {
        this.gs.delta_base = this.pop();
    }
    
    private void _sdpvtl(final short n) {
        this.pop();
        this.pop();
    }
    
    private void _sds() {
        this.gs.delta_shift = this.pop();
    }
    
    private void _sfvfs() {
        this.gs.freedom_vector[1] = this.pop();
        this.gs.freedom_vector[0] = this.pop();
    }
    
    private void _sfvtca(final short n) {
        if (n == 1) {
            this.gs.freedom_vector[0] = 16384;
            this.gs.freedom_vector[1] = 0;
        }
        else {
            this.gs.freedom_vector[0] = 0;
            this.gs.freedom_vector[1] = 16384;
        }
    }
    
    private void _sfvtl(final short n) {
        this.pop();
        this.pop();
        this.gs.freedom_vector[0] = 0;
        this.gs.freedom_vector[1] = 0;
    }
    
    private void _sfvtpv() {
        this.gs.freedom_vector[0] = this.gs.projection_vector[0];
        this.gs.freedom_vector[1] = this.gs.projection_vector[1];
    }
    
    private void _shc(final short n) {
        this.pop();
    }
    
    private void _shp(final short n) {
        while (this.gs.loop-- > 0) {
            this.pop();
            if (n == 0) {
                continue;
            }
        }
        this.gs.loop = 1;
    }
    
    private void _shpix() {
        this.pop();
        while (this.gs.loop-- > 0) {
            this.pop();
        }
        this.gs.loop = 1;
    }
    
    private void _shz(final short n) {
        this.pop();
    }
    
    private void _sloop() {
        this.gs.loop = this.pop();
    }
    
    private void _smd() {
        this.gs.minimum_distance = this.pop();
    }
    
    private void _spvfs() {
        this.gs.projection_vector[1] = this.pop();
        this.gs.projection_vector[0] = this.pop();
    }
    
    private void _spvtca(final short n) {
        if (n == 1) {
            this.gs.projection_vector[0] = 16384;
            this.gs.projection_vector[1] = 0;
        }
        else {
            this.gs.projection_vector[0] = 0;
            this.gs.projection_vector[1] = 16384;
        }
    }
    
    private void _spvtl(final short n) {
        this.pop();
        this.pop();
        this.gs.projection_vector[0] = 0;
        this.gs.projection_vector[1] = 0;
    }
    
    private void _sround() {
        this.pop();
    }
    
    private void _srp0() {
        this.gs.rp0 = this.pop();
    }
    
    private void _srp1() {
        this.gs.rp1 = this.pop();
    }
    
    private void _srp2() {
        this.gs.rp2 = this.pop();
    }
    
    private void _ssw() {
        this.gs.single_width_value = this.pop();
    }
    
    private void _sswci() {
        this.gs.single_width_cut_in = this.pop();
    }
    
    private void _sub() {
        this.push(this.pop() - this.pop());
    }
    
    private void _svtca(final short n) {
        if (n == 1) {
            this.gs.projection_vector[0] = 16384;
            this.gs.projection_vector[1] = 0;
            this.gs.freedom_vector[0] = 16384;
            this.gs.freedom_vector[1] = 0;
        }
        else {
            this.gs.projection_vector[0] = 0;
            this.gs.projection_vector[1] = 16384;
            this.gs.freedom_vector[0] = 0;
            this.gs.freedom_vector[1] = 16384;
        }
    }
    
    private void _swap() {
        final int pop = this.pop();
        final int pop2 = this.pop();
        this.push(pop);
        this.push(pop2);
    }
    
    private void _szp0() {
        this.gs.zp0 = this.pop();
    }
    
    private void _szp1() {
        this.gs.zp1 = this.pop();
    }
    
    private void _szp2() {
        this.gs.zp2 = this.pop();
    }
    
    private void _szps() {
        final GraphicsState gs = this.gs;
        final GraphicsState gs2 = this.gs;
        final GraphicsState gs3 = this.gs;
        final int pop = this.pop();
        gs3.zp2 = pop;
        gs2.zp1 = pop;
        gs.zp0 = pop;
    }
    
    private void _utp() {
        this.pop();
    }
    
    private void _wcvtf() {
        this.cvt[this.pop()] = this.pop();
    }
    
    private void _wcvtp() {
        this.cvt[this.pop()] = this.pop();
    }
    
    private void _ws() {
        this.store[this.pop()] = this.pop();
    }
    
    public void execute(int i) {
        while (i < ((i & 0xFFFF0000) | this.parser.getISLength(i >> 16))) {
            final short opcode = this.parser.getOpcode(i);
            if (this.inFuncDef) {
                if (opcode == 45) {
                    this.inFuncDef = false;
                }
                i = this.parser.advanceIP(i);
            }
            else {
                if (opcode >= 224) {
                    this._mirp((short)(opcode & 0x1F));
                }
                else if (opcode >= 192) {
                    this._mdrp((short)(opcode & 0x1F));
                }
                else if (opcode >= 184) {
                    this._push(this.parser.getPushData(i));
                }
                else if (opcode >= 176) {
                    this._push(this.parser.getPushData(i));
                }
                else if (opcode >= 142) {
                    this._instctrl();
                }
                else if (opcode >= 141) {
                    this._scantype();
                }
                else if (opcode >= 140) {
                    this._min();
                }
                else if (opcode >= 139) {
                    this._max();
                }
                else if (opcode >= 138) {
                    this._roll();
                }
                else if (opcode >= 137) {
                    this._idef();
                }
                else if (opcode >= 136) {
                    this._getinfo();
                }
                else if (opcode >= 134) {
                    this._sdpvtl((short)(opcode & 0x1));
                }
                else if (opcode >= 133) {
                    this._scanctrl();
                }
                else if (opcode >= 130) {
                    this._fliprgoff();
                }
                else if (opcode >= 129) {
                    this._fliprgon();
                }
                else if (opcode >= 128) {
                    this._flippt();
                }
                else if (opcode < 127) {
                    if (opcode < 126) {
                        if (opcode >= 125) {
                            this._rdtg();
                        }
                        else if (opcode >= 124) {
                            this._rutg();
                        }
                        else if (opcode >= 122) {
                            this._roff();
                        }
                        else if (opcode >= 121) {
                            i = this._jrof(i);
                        }
                        else if (opcode >= 120) {
                            i = this._jrot(i);
                        }
                        else if (opcode >= 119) {
                            this._s45round();
                        }
                        else if (opcode >= 118) {
                            this._sround();
                        }
                        else if (opcode >= 117) {
                            this._deltac3();
                        }
                        else if (opcode >= 116) {
                            this._deltac2();
                        }
                        else if (opcode >= 115) {
                            this._deltac1();
                        }
                        else if (opcode >= 114) {
                            this._deltap3();
                        }
                        else if (opcode >= 113) {
                            this._deltap2();
                        }
                        else if (opcode >= 112) {
                            this._wcvtf();
                        }
                        else if (opcode >= 108) {
                            this._nround((short)(opcode & 0x3));
                        }
                        else if (opcode >= 104) {
                            this._round((short)(opcode & 0x3));
                        }
                        else if (opcode >= 103) {
                            this._ceiling();
                        }
                        else if (opcode >= 102) {
                            this._floor();
                        }
                        else if (opcode >= 101) {
                            this._neg();
                        }
                        else if (opcode >= 100) {
                            this._abs();
                        }
                        else if (opcode >= 99) {
                            this._mul();
                        }
                        else if (opcode >= 98) {
                            this._div();
                        }
                        else if (opcode >= 97) {
                            this._sub();
                        }
                        else if (opcode >= 96) {
                            this._add();
                        }
                        else if (opcode >= 95) {
                            this._sds();
                        }
                        else if (opcode >= 94) {
                            this._sdb();
                        }
                        else if (opcode >= 93) {
                            this._deltap1();
                        }
                        else if (opcode >= 92) {
                            this._not();
                        }
                        else if (opcode >= 91) {
                            this._or();
                        }
                        else if (opcode >= 90) {
                            this._and();
                        }
                        else if (opcode < 89) {
                            if (opcode >= 88) {
                                i = this._if(i);
                            }
                            else if (opcode >= 87) {
                                this._even();
                            }
                            else if (opcode >= 86) {
                                this._odd();
                            }
                            else if (opcode >= 85) {
                                this._neq();
                            }
                            else if (opcode >= 84) {
                                this._eq();
                            }
                            else if (opcode >= 83) {
                                this._gteq();
                            }
                            else if (opcode >= 82) {
                                this._gt();
                            }
                            else if (opcode >= 81) {
                                this._lteq();
                            }
                            else if (opcode >= 80) {
                                this._lt();
                            }
                            else if (opcode >= 79) {
                                this._debug();
                            }
                            else if (opcode >= 78) {
                                this._flipoff();
                            }
                            else if (opcode >= 77) {
                                this._flipon();
                            }
                            else if (opcode >= 76) {
                                this._mps();
                            }
                            else if (opcode >= 75) {
                                this._mppem();
                            }
                            else if (opcode >= 73) {
                                this._md((short)(opcode & 0x1));
                            }
                            else if (opcode >= 72) {
                                this._scfs();
                            }
                            else if (opcode >= 70) {
                                this._gc((short)(opcode & 0x1));
                            }
                            else if (opcode >= 69) {
                                this._rcvt();
                            }
                            else if (opcode >= 68) {
                                this._wcvtp();
                            }
                            else if (opcode >= 67) {
                                this._rs();
                            }
                            else if (opcode >= 66) {
                                this._ws();
                            }
                            else if (opcode >= 65) {
                                this._push(this.parser.getPushData(i));
                            }
                            else if (opcode >= 64) {
                                this._push(this.parser.getPushData(i));
                            }
                            else if (opcode >= 62) {
                                this._miap((short)(opcode & 0x1));
                            }
                            else if (opcode >= 61) {
                                this._rtdg();
                            }
                            else if (opcode >= 60) {
                                this._alignrp();
                            }
                            else if (opcode >= 57) {
                                this._ip();
                            }
                            else if (opcode >= 58) {
                                this._msirp((short)(opcode & 0x1));
                            }
                            else if (opcode >= 56) {
                                this._shpix();
                            }
                            else if (opcode >= 54) {
                                this._shz((short)(opcode & 0x1));
                            }
                            else if (opcode >= 52) {
                                this._shc((short)(opcode & 0x1));
                            }
                            else if (opcode >= 50) {
                                this._shp((short)(opcode & 0x1));
                            }
                            else if (opcode >= 48) {
                                this._iup((short)(opcode & 0x1));
                            }
                            else if (opcode >= 46) {
                                this._mdap((short)(opcode & 0x1));
                            }
                            else {
                                if (opcode >= 45) {
                                    return;
                                }
                                if (opcode >= 44) {
                                    this._fdef(i + 1);
                                }
                                else if (opcode >= 43) {
                                    this._call();
                                }
                                else if (opcode >= 42) {
                                    this._loopcall();
                                }
                                else if (opcode >= 41) {
                                    this._utp();
                                }
                                else if (opcode >= 39) {
                                    this._alignpts();
                                }
                                else if (opcode >= 38) {
                                    this._mindex();
                                }
                                else if (opcode >= 37) {
                                    this._cindex();
                                }
                                else if (opcode >= 36) {
                                    this._depth();
                                }
                                else if (opcode >= 35) {
                                    this._swap();
                                }
                                else if (opcode >= 34) {
                                    this._clear();
                                }
                                else if (opcode >= 33) {
                                    this.pop();
                                }
                                else if (opcode >= 32) {
                                    this._dup();
                                }
                                else if (opcode >= 31) {
                                    this._ssw();
                                }
                                else if (opcode >= 30) {
                                    this._sswci();
                                }
                                else if (opcode >= 29) {
                                    this._scvtci();
                                }
                                else if (opcode >= 28) {
                                    i = this._jmpr(i);
                                }
                                else if (opcode >= 27) {
                                    i = this._else(i);
                                }
                                else if (opcode >= 26) {
                                    this._smd();
                                }
                                else if (opcode >= 25) {
                                    this._rthg();
                                }
                                else if (opcode >= 24) {
                                    this._rtg();
                                }
                                else if (opcode >= 23) {
                                    this._sloop();
                                }
                                else if (opcode >= 22) {
                                    this._szps();
                                }
                                else if (opcode >= 21) {
                                    this._szp2();
                                }
                                else if (opcode >= 20) {
                                    this._szp1();
                                }
                                else if (opcode >= 19) {
                                    this._szp0();
                                }
                                else if (opcode >= 18) {
                                    this._srp2();
                                }
                                else if (opcode >= 17) {
                                    this._srp1();
                                }
                                else if (opcode >= 16) {
                                    this._srp0();
                                }
                                else if (opcode >= 15) {
                                    this._isect();
                                }
                                else if (opcode >= 14) {
                                    this._sfvtpv();
                                }
                                else if (opcode >= 13) {
                                    this._gfv();
                                }
                                else if (opcode >= 12) {
                                    this._gpv();
                                }
                                else if (opcode >= 11) {
                                    this._sfvfs();
                                }
                                else if (opcode >= 10) {
                                    this._spvfs();
                                }
                                else if (opcode >= 8) {
                                    this._sfvtl((short)(opcode & 0x1));
                                }
                                else if (opcode >= 6) {
                                    this._spvtl((short)(opcode & 0x1));
                                }
                                else if (opcode >= 4) {
                                    this._sfvtca((short)(opcode & 0x1));
                                }
                                else if (opcode >= 2) {
                                    this._spvtca((short)(opcode & 0x1));
                                }
                                else if (opcode >= 0) {
                                    this._svtca((short)(opcode & 0x1));
                                }
                            }
                        }
                    }
                }
                i = this.parser.advanceIP(i);
            }
        }
    }
    
    public Point[][] getZones() {
        return this.zone;
    }
    
    private int pop() {
        final int[] stack = this.stack;
        final int stackIndex = this.stackIndex - 1;
        this.stackIndex = stackIndex;
        return stack[stackIndex];
    }
    
    private void push(final int n) {
        this.stack[this.stackIndex++] = n;
    }
    
    public void runCvtProgram() {
        this.execute(65536);
    }
    
    public void runFontProgram() {
        this.execute(0);
    }
    
    public void runGlyphProgram() {
        if ((this.gs.instruction_control & 0x1) == 0x0) {
            this.execute(131072);
        }
    }
    
    public void setParser(final Parser parser) {
        this.parser = parser;
    }
}
