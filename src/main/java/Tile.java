import Interfaces.IDrawable;

public class Tile {
    public Tile(boolean canBeMovedThrough) {this.canBeMovedThrough = canBeMovedThrough;}
    public void Activate() {
        ++ActivatedCount;
    }
    public int getActivations() { return ActivatedCount; }
    public boolean canBeMovedThrough() {return canBeMovedThrough;}

    private int ActivatedCount = 0;
    private boolean canBeMovedThrough = true;
    public static final int TILE_WIDTH = 10;
    public static int TILE_WIDTH_HALF = TILE_WIDTH/2;
}
