// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.nurbs;

public class Renderhints
{
    public int errorchecking;
    public int maxsubdivisions;
    private int subdivisions;
    int display_method;
    int wiretris;
    int wirequads;
    
    public Renderhints() {
        this.display_method = 1;
        this.errorchecking = 1;
        this.subdivisions = 6;
    }
    
    public void setProperty(final Property property) {
        switch (property.type) {
            case 3: {
                this.display_method = (int)property.value;
                break;
            }
            case 4: {
                this.errorchecking = (int)property.value;
                break;
            }
            case 5: {
                this.subdivisions = (int)property.value;
                break;
            }
        }
    }
    
    public void init() {
        this.maxsubdivisions = this.subdivisions;
        if (this.maxsubdivisions < 0) {
            this.maxsubdivisions = 0;
        }
        if (this.display_method == 1) {
            this.wiretris = 0;
            this.wirequads = 0;
        }
        else if (this.display_method == 3) {
            this.wiretris = 1;
            this.wirequads = 0;
        }
        else if (this.display_method == 4) {
            this.wiretris = 0;
            this.wirequads = 1;
        }
        else {
            this.wiretris = 1;
            this.wirequads = 1;
        }
    }
}
