// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.t2;

import jogamp.graph.font.typecast.ot.table.CharstringType2;
import jogamp.graph.font.typecast.ot.Point;
import java.util.ArrayList;

public class T2Interpreter
{
    private static final int ARGUMENT_STACK_LIMIT = 48;
    private static final int SUBR_STACK_LIMIT = 10;
    private static final int TRANSIENT_ARRAY_ELEMENT_COUNT = 32;
    private final Number[] _argStack;
    private int _argStackIndex;
    private final int[] _subrStack;
    private int _subrStackIndex;
    private final Number[] _transientArray;
    private ArrayList<Point> _points;
    
    public T2Interpreter() {
        this._argStack = new Number[48];
        this._argStackIndex = 0;
        this._subrStack = new int[10];
        this._subrStackIndex = 0;
        this._transientArray = new Number[32];
    }
    
    private void _rmoveto() {
        final int intValue = this.popArg().intValue();
        final int intValue2 = this.popArg().intValue();
        this.clearArg();
        final Point lastPoint = this.getLastPoint();
        this.moveTo(lastPoint.x + intValue2, lastPoint.y + intValue);
    }
    
    private void _hmoveto() {
        final int intValue = this.popArg().intValue();
        this.clearArg();
        final Point lastPoint = this.getLastPoint();
        this.moveTo(lastPoint.x + intValue, lastPoint.y);
    }
    
    private void _vmoveto() {
        final int intValue = this.popArg().intValue();
        this.clearArg();
        final Point lastPoint = this.getLastPoint();
        this.moveTo(lastPoint.x, lastPoint.y + intValue);
    }
    
    private void _rlineto() {
        final int n = this.getArgCount() / 2;
        final int[] array = new int[n];
        final int[] array2 = new int[n];
        for (int i = 0; i < n; ++i) {
            array2[n - i - 1] = this.popArg().intValue();
            array[n - i - 1] = this.popArg().intValue();
        }
        for (int j = 0; j < n; ++j) {
            final Point lastPoint = this.getLastPoint();
            this.lineTo(lastPoint.x + array[j], lastPoint.y + array2[j]);
        }
        this.clearArg();
    }
    
    private void _hlineto() {
        final int argCount = this.getArgCount();
        final Number[] array = new Number[argCount];
        for (int i = 0; i < argCount; ++i) {
            array[argCount - i - 1] = this.popArg();
        }
        for (int j = 0; j < argCount; ++j) {
            final Point lastPoint = this.getLastPoint();
            if (j % 2 == 0) {
                this.lineTo(lastPoint.x + array[j].intValue(), lastPoint.y);
            }
            else {
                this.lineTo(lastPoint.x, lastPoint.y + array[j].intValue());
            }
        }
        this.clearArg();
    }
    
    private void _vlineto() {
        final int argCount = this.getArgCount();
        final Number[] array = new Number[argCount];
        for (int i = 0; i < argCount; ++i) {
            array[argCount - i - 1] = this.popArg();
        }
        for (int j = 0; j < argCount; ++j) {
            final Point lastPoint = this.getLastPoint();
            if (j % 2 == 0) {
                this.lineTo(lastPoint.x, lastPoint.y + array[j].intValue());
            }
            else {
                this.lineTo(lastPoint.x + array[j].intValue(), lastPoint.y);
            }
        }
        this.clearArg();
    }
    
    private void _rrcurveto() {
        final int n = this.getArgCount() / 6;
        final int[] array = new int[n];
        final int[] array2 = new int[n];
        final int[] array3 = new int[n];
        final int[] array4 = new int[n];
        final int[] array5 = new int[n];
        final int[] array6 = new int[n];
        for (int i = 0; i < n; ++i) {
            array6[n - i - 1] = this.popArg().intValue();
            array5[n - i - 1] = this.popArg().intValue();
            array4[n - i - 1] = this.popArg().intValue();
            array3[n - i - 1] = this.popArg().intValue();
            array2[n - i - 1] = this.popArg().intValue();
            array[n - i - 1] = this.popArg().intValue();
        }
        for (int j = 0; j < n; ++j) {
            final Point lastPoint = this.getLastPoint();
            final int n2 = lastPoint.x + array[j];
            final int n3 = lastPoint.y + array2[j];
            final int n4 = n2 + array3[j];
            final int n5 = n3 + array4[j];
            this.curveTo(n2, n3, n4, n5, n4 + array5[j], n5 + array6[j]);
        }
        this.clearArg();
    }
    
    private void _hhcurveto() {
        final int n = this.getArgCount() / 4;
        int intValue = 0;
        final int[] array = new int[n];
        final int[] array2 = new int[n];
        final int[] array3 = new int[n];
        final int[] array4 = new int[n];
        for (int i = 0; i < n; ++i) {
            array4[n - i - 1] = this.popArg().intValue();
            array3[n - i - 1] = this.popArg().intValue();
            array2[n - i - 1] = this.popArg().intValue();
            array[n - i - 1] = this.popArg().intValue();
        }
        if (this.getArgCount() == 1) {
            intValue = this.popArg().intValue();
        }
        for (int j = 0; j < n; ++j) {
            final Point lastPoint = this.getLastPoint();
            final int n2 = lastPoint.x + array[j];
            final int n3 = lastPoint.y + ((j == 0) ? intValue : false);
            final int n4 = n2 + array2[j];
            final int n5 = n3 + array3[j];
            this.curveTo(n2, n3, n4, n5, n4 + array4[j], n5);
        }
        this.clearArg();
    }
    
    private void _hvcurveto() {
        if (this.getArgCount() % 8 <= 1) {
            final int n = this.getArgCount() / 8;
            final int[] array = new int[n];
            final int[] array2 = new int[n];
            final int[] array3 = new int[n];
            final int[] array4 = new int[n];
            final int[] array5 = new int[n];
            final int[] array6 = new int[n];
            final int[] array7 = new int[n];
            final int[] array8 = new int[n];
            int intValue = 0;
            if (this.getArgCount() % 8 == 1) {
                intValue = this.popArg().intValue();
            }
            for (int i = 0; i < n; ++i) {
                array8[n - i - 1] = this.popArg().intValue();
                array7[n - i - 1] = this.popArg().intValue();
                array6[n - i - 1] = this.popArg().intValue();
                array5[n - i - 1] = this.popArg().intValue();
                array4[n - i - 1] = this.popArg().intValue();
                array3[n - i - 1] = this.popArg().intValue();
                array2[n - i - 1] = this.popArg().intValue();
                array[n - i - 1] = this.popArg().intValue();
            }
            for (int j = 0; j < n; ++j) {
                final Point lastPoint = this.getLastPoint();
                final int n2 = lastPoint.x + array[j];
                final int y = lastPoint.y;
                final int n3 = n2 + array2[j];
                final int n4 = y + array3[j];
                final int n5 = n3;
                final int n6 = n4 + array4[j];
                final int n7 = n5;
                final int n8 = n6 + array5[j];
                final int n9 = n7 + array6[j];
                final int n10 = n8 + array7[j];
                final int n11 = n9 + array8[j];
                final int n12 = n10 + intValue;
                this.curveTo(n2, y, n3, n4, n5, n6);
                this.curveTo(n7, n8, n9, n10, n11, n12);
            }
        }
        else {
            final int n13 = this.getArgCount() / 8;
            final int[] array9 = new int[n13];
            final int[] array10 = new int[n13];
            final int[] array11 = new int[n13];
            final int[] array12 = new int[n13];
            final int[] array13 = new int[n13];
            final int[] array14 = new int[n13];
            final int[] array15 = new int[n13];
            final int[] array16 = new int[n13];
            int intValue2 = 0;
            if (this.getArgCount() % 8 == 1) {
                intValue2 = this.popArg().intValue();
            }
            for (int k = 0; k < n13; ++k) {
                array16[n13 - k - 1] = this.popArg().intValue();
                array15[n13 - k - 1] = this.popArg().intValue();
                array14[n13 - k - 1] = this.popArg().intValue();
                array13[n13 - k - 1] = this.popArg().intValue();
                array12[n13 - k - 1] = this.popArg().intValue();
                array11[n13 - k - 1] = this.popArg().intValue();
                array10[n13 - k - 1] = this.popArg().intValue();
                array9[n13 - k - 1] = this.popArg().intValue();
            }
            this.popArg();
            this.popArg();
            this.popArg();
            this.popArg();
            for (int l = 0; l < n13; ++l) {
                final Point lastPoint2 = this.getLastPoint();
                final int x = lastPoint2.x;
                final int n14 = lastPoint2.y + array9[l];
                final int n15 = x + array10[l];
                final int n16 = n14 + array11[l];
                final int n17 = n15 + array12[l];
                final int n18 = n16;
                final int n19 = n17 + array13[l];
                final int n20 = n18;
                final int n21 = n19 + array14[l];
                final int n22 = n20 + array15[l];
                final int n23 = n21 + intValue2;
                final int n24 = n22 + array16[l];
                this.curveTo(x, n14, n15, n16, n17, n18);
                this.curveTo(n19, n20, n21, n22, n23, n24);
            }
        }
        this.clearArg();
    }
    
    private void _rcurveline() {
        final int n = (this.getArgCount() - 2) / 6;
        final int[] array = new int[n];
        final int[] array2 = new int[n];
        final int[] array3 = new int[n];
        final int[] array4 = new int[n];
        final int[] array5 = new int[n];
        final int[] array6 = new int[n];
        final int intValue = this.popArg().intValue();
        final int intValue2 = this.popArg().intValue();
        for (int i = 0; i < n; ++i) {
            array6[n - i - 1] = this.popArg().intValue();
            array5[n - i - 1] = this.popArg().intValue();
            array4[n - i - 1] = this.popArg().intValue();
            array3[n - i - 1] = this.popArg().intValue();
            array2[n - i - 1] = this.popArg().intValue();
            array[n - i - 1] = this.popArg().intValue();
        }
        int n2 = 0;
        int n3 = 0;
        for (int j = 0; j < n; ++j) {
            final Point lastPoint = this.getLastPoint();
            final int n4 = lastPoint.x + array[j];
            final int n5 = lastPoint.y + array2[j];
            final int n6 = n4 + array3[j];
            final int n7 = n5 + array4[j];
            n2 = n6 + array5[j];
            n3 = n7 + array6[j];
            this.curveTo(n4, n5, n6, n7, n2, n3);
        }
        this.lineTo(n2 + intValue2, n3 + intValue);
        this.clearArg();
    }
    
    private void _rlinecurve() {
        final int n = (this.getArgCount() - 6) / 2;
        final int[] array = new int[n];
        final int[] array2 = new int[n];
        final int intValue = this.popArg().intValue();
        final int intValue2 = this.popArg().intValue();
        final int intValue3 = this.popArg().intValue();
        final int intValue4 = this.popArg().intValue();
        final int intValue5 = this.popArg().intValue();
        final int intValue6 = this.popArg().intValue();
        for (int i = 0; i < n; ++i) {
            array2[n - i - 1] = this.popArg().intValue();
            array[n - i - 1] = this.popArg().intValue();
        }
        int n2 = 0;
        int n3 = 0;
        for (int j = 0; j < n; ++j) {
            final Point lastPoint = this.getLastPoint();
            n2 = lastPoint.x + array[j];
            n3 = lastPoint.y + array2[j];
            this.lineTo(n2, n3);
        }
        final int n4 = n2 + intValue6;
        final int n5 = n3 + intValue5;
        final int n6 = n4 + intValue4;
        final int n7 = n5 + intValue3;
        this.curveTo(n4, n5, n6, n7, n6 + intValue2, n7 + intValue);
        this.clearArg();
    }
    
    private void _vhcurveto() {
        if (this.getArgCount() % 8 <= 1) {
            final int n = this.getArgCount() / 8;
            final int[] array = new int[n];
            final int[] array2 = new int[n];
            final int[] array3 = new int[n];
            final int[] array4 = new int[n];
            final int[] array5 = new int[n];
            final int[] array6 = new int[n];
            final int[] array7 = new int[n];
            final int[] array8 = new int[n];
            int intValue = 0;
            if (this.getArgCount() % 8 == 1) {
                intValue = this.popArg().intValue();
            }
            for (int i = 0; i < n; ++i) {
                array8[n - i - 1] = this.popArg().intValue();
                array7[n - i - 1] = this.popArg().intValue();
                array6[n - i - 1] = this.popArg().intValue();
                array5[n - i - 1] = this.popArg().intValue();
                array4[n - i - 1] = this.popArg().intValue();
                array3[n - i - 1] = this.popArg().intValue();
                array2[n - i - 1] = this.popArg().intValue();
                array[n - i - 1] = this.popArg().intValue();
            }
            for (int j = 0; j < n; ++j) {
                final Point lastPoint = this.getLastPoint();
                final int x = lastPoint.x;
                final int n2 = lastPoint.y + array[j];
                final int n3 = x + array2[j];
                final int n4 = n2 + array3[j];
                final int n5 = n3 + array4[j];
                final int n6 = n4;
                final int n7 = n5 + array5[j];
                final int n8 = n6;
                final int n9 = n7 + array6[j];
                final int n10 = n8 + array7[j];
                final int n11 = n9 + intValue;
                final int n12 = n10 + array8[j];
                this.curveTo(x, n2, n3, n4, n5, n6);
                this.curveTo(n7, n8, n9, n10, n11, n12);
            }
        }
        this.clearArg();
    }
    
    private void _vvcurveto() {
        this.clearArg();
    }
    
    private void _flex() {
        this.clearArg();
    }
    
    private void _hflex() {
        this.clearArg();
    }
    
    private void _hflex1() {
        this.clearArg();
    }
    
    private void _flex1() {
        this.clearArg();
    }
    
    private void _endchar() {
        this.endContour();
        this.clearArg();
    }
    
    private void _hstem() {
        this.clearArg();
    }
    
    private void _vstem() {
        this.clearArg();
    }
    
    private void _hstemhm() {
        this.clearArg();
    }
    
    private void _vstemhm() {
        this.clearArg();
    }
    
    private void _hintmask() {
        this.clearArg();
    }
    
    private void _cntrmask() {
        this.clearArg();
    }
    
    private void _abs() {
        this.pushArg(Math.abs(this.popArg().doubleValue()));
    }
    
    private void _add() {
        this.pushArg(this.popArg().doubleValue() + this.popArg().doubleValue());
    }
    
    private void _sub() {
        this.pushArg(this.popArg().doubleValue() - this.popArg().doubleValue());
    }
    
    private void _div() {
        this.pushArg(this.popArg().doubleValue() / this.popArg().doubleValue());
    }
    
    private void _neg() {
        this.pushArg(-this.popArg().doubleValue());
    }
    
    private void _random() {
        this.pushArg(1.0 - Math.random());
    }
    
    private void _mul() {
        this.pushArg(this.popArg().doubleValue() * this.popArg().doubleValue());
    }
    
    private void _sqrt() {
        this.pushArg(Math.sqrt(this.popArg().doubleValue()));
    }
    
    private void _drop() {
        this.popArg();
    }
    
    private void _exch() {
        final Number popArg = this.popArg();
        final Number popArg2 = this.popArg();
        this.pushArg(popArg);
        this.pushArg(popArg2);
    }
    
    private void _index() {
        final int intValue = this.popArg().intValue();
        final Number[] array = new Number[intValue];
        for (int i = 0; i < intValue; ++i) {
            array[i] = this.popArg();
        }
        for (int j = intValue - 1; j >= 0; --j) {
            this.pushArg(array[j]);
        }
        this.pushArg(array[intValue]);
    }
    
    private void _roll() {
        final int intValue = this.popArg().intValue();
        final int intValue2 = this.popArg().intValue();
        final Number[] array = new Number[intValue2];
        for (int i = 0; i < intValue2; ++i) {
            array[i] = this.popArg();
        }
        for (int j = intValue2 - 1; j >= 0; --j) {
            this.pushArg(array[(intValue2 + j + intValue) % intValue2]);
        }
    }
    
    private void _dup() {
        final Number popArg = this.popArg();
        this.pushArg(popArg);
        this.pushArg(popArg);
    }
    
    private void _put() {
        this._transientArray[this.popArg().intValue()] = this.popArg();
    }
    
    private void _get() {
        this.pushArg(this._transientArray[this.popArg().intValue()]);
    }
    
    private void _and() {
        final double doubleValue = this.popArg().doubleValue();
        this.pushArg((int)((this.popArg().doubleValue() != 0.0 && doubleValue != 0.0) ? 1 : 0));
    }
    
    private void _or() {
        final double doubleValue = this.popArg().doubleValue();
        this.pushArg((int)((this.popArg().doubleValue() != 0.0 || doubleValue != 0.0) ? 1 : 0));
    }
    
    private void _not() {
        this.pushArg((int)((this.popArg().doubleValue() == 0.0) ? 1 : 0));
    }
    
    private void _eq() {
        this.pushArg((int)((this.popArg().doubleValue() == this.popArg().doubleValue()) ? 1 : 0));
    }
    
    private void _ifelse() {
        final double doubleValue = this.popArg().doubleValue();
        final double doubleValue2 = this.popArg().doubleValue();
        final Number popArg = this.popArg();
        final Number popArg2 = this.popArg();
        this.pushArg((doubleValue2 <= doubleValue) ? popArg2 : popArg);
    }
    
    private void _callsubr() {
    }
    
    private void _callgsubr() {
    }
    
    private void _return() {
    }
    
    public Point[] execute(final CharstringType2 charstringType2) {
        this._points = new ArrayList<Point>();
        charstringType2.resetIP();
        while (charstringType2.moreBytes()) {
            while (charstringType2.isOperandAtIndex()) {
                this.pushArg(charstringType2.nextOperand());
            }
            final int nextByte = charstringType2.nextByte();
            if (nextByte == 12) {
                switch (charstringType2.nextByte()) {
                    case 3: {
                        this._and();
                        continue;
                    }
                    case 4: {
                        this._or();
                        continue;
                    }
                    case 5: {
                        this._not();
                        continue;
                    }
                    case 9: {
                        this._abs();
                        continue;
                    }
                    case 10: {
                        this._add();
                        continue;
                    }
                    case 11: {
                        this._sub();
                        continue;
                    }
                    case 12: {
                        this._div();
                        continue;
                    }
                    case 14: {
                        this._neg();
                        continue;
                    }
                    case 15: {
                        this._eq();
                        continue;
                    }
                    case 18: {
                        this._drop();
                        continue;
                    }
                    case 20: {
                        this._put();
                        continue;
                    }
                    case 21: {
                        this._get();
                        continue;
                    }
                    case 22: {
                        this._ifelse();
                        continue;
                    }
                    case 23: {
                        this._random();
                        continue;
                    }
                    case 24: {
                        this._mul();
                        continue;
                    }
                    case 26: {
                        this._sqrt();
                        continue;
                    }
                    case 27: {
                        this._dup();
                        continue;
                    }
                    case 28: {
                        this._exch();
                        continue;
                    }
                    case 29: {
                        this._index();
                        continue;
                    }
                    case 30: {
                        this._roll();
                        continue;
                    }
                    case 34: {
                        this._hflex();
                        continue;
                    }
                    case 35: {
                        this._flex();
                        continue;
                    }
                    case 36: {
                        this._hflex1();
                        continue;
                    }
                    case 37: {
                        this._flex1();
                        continue;
                    }
                    default: {
                        return null;
                    }
                }
            }
            else {
                switch (nextByte) {
                    case 1: {
                        this._hstem();
                        continue;
                    }
                    case 3: {
                        this._vstem();
                        continue;
                    }
                    case 4: {
                        this._vmoveto();
                        continue;
                    }
                    case 5: {
                        this._rlineto();
                        continue;
                    }
                    case 6: {
                        this._hlineto();
                        continue;
                    }
                    case 7: {
                        this._vlineto();
                        continue;
                    }
                    case 8: {
                        this._rrcurveto();
                        continue;
                    }
                    case 10: {
                        this._callsubr();
                        continue;
                    }
                    case 11: {
                        this._return();
                        continue;
                    }
                    case 14: {
                        this._endchar();
                        continue;
                    }
                    case 18: {
                        this._hstemhm();
                        continue;
                    }
                    case 19: {
                        this._hintmask();
                        continue;
                    }
                    case 20: {
                        this._cntrmask();
                        continue;
                    }
                    case 21: {
                        this._rmoveto();
                        continue;
                    }
                    case 22: {
                        this._hmoveto();
                        continue;
                    }
                    case 23: {
                        this._vstemhm();
                        continue;
                    }
                    case 24: {
                        this._rcurveline();
                        continue;
                    }
                    case 25: {
                        this._rlinecurve();
                        continue;
                    }
                    case 26: {
                        this._vvcurveto();
                        continue;
                    }
                    case 27: {
                        this._hhcurveto();
                        continue;
                    }
                    case 29: {
                        this._callgsubr();
                        continue;
                    }
                    case 30: {
                        this._vhcurveto();
                        continue;
                    }
                    case 31: {
                        this._hvcurveto();
                        continue;
                    }
                    default: {
                        return null;
                    }
                }
            }
        }
        final Point[] array = new Point[this._points.size()];
        this._points.toArray(array);
        return array;
    }
    
    private int getArgCount() {
        return this._argStackIndex;
    }
    
    private Number popArg() {
        final Number[] argStack = this._argStack;
        final int argStackIndex = this._argStackIndex - 1;
        this._argStackIndex = argStackIndex;
        return argStack[argStackIndex];
    }
    
    private void pushArg(final Number n) {
        this._argStack[this._argStackIndex++] = n;
    }
    
    private int popSubr() {
        final int[] subrStack = this._subrStack;
        final int subrStackIndex = this._subrStackIndex - 1;
        this._subrStackIndex = subrStackIndex;
        return subrStack[subrStackIndex];
    }
    
    private void pushSubr(final int n) {
        this._subrStack[this._subrStackIndex++] = n;
    }
    
    private void clearArg() {
        this._argStackIndex = 0;
    }
    
    private Point getLastPoint() {
        final int size = this._points.size();
        if (size > 0) {
            return this._points.get(size - 1);
        }
        return new Point(0, 0, true, false);
    }
    
    private void moveTo(final int n, final int n2) {
        this.endContour();
        this._points.add(new Point(n, n2, true, false));
    }
    
    private void lineTo(final int n, final int n2) {
        this._points.add(new Point(n, n2, true, false));
    }
    
    private void curveTo(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this._points.add(new Point(n, n2, false, false));
        this._points.add(new Point(n3, n4, false, false));
        this._points.add(new Point(n5, n6, true, false));
    }
    
    private void endContour() {
        final Point lastPoint = this.getLastPoint();
        if (lastPoint != null) {
            lastPoint.endOfContour = true;
        }
    }
}
