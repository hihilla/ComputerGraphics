// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.tt.engine;

class GraphicsState
{
    public boolean auto_flip;
    public int control_value_cut_in;
    public int delta_base;
    public int delta_shift;
    public int dual_projection_vectors;
    public int[] freedom_vector;
    public int zp0;
    public int zp1;
    public int zp2;
    public int instruction_control;
    public int loop;
    public int minimum_distance;
    public int[] projection_vector;
    public int round_state;
    public int rp0;
    public int rp1;
    public int rp2;
    public int scan_control;
    public int single_width_cut_in;
    public int single_width_value;
    
    GraphicsState() {
        this.auto_flip = true;
        this.control_value_cut_in = 0;
        this.delta_base = 9;
        this.delta_shift = 3;
        this.freedom_vector = new int[2];
        this.zp0 = 1;
        this.zp1 = 1;
        this.zp2 = 1;
        this.instruction_control = 0;
        this.loop = 1;
        this.minimum_distance = 1;
        this.projection_vector = new int[2];
        this.round_state = 1;
        this.rp0 = 0;
        this.rp1 = 0;
        this.rp2 = 0;
        this.scan_control = 0;
        this.single_width_cut_in = 0;
        this.single_width_value = 0;
    }
}
