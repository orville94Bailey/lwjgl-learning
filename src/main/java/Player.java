import Interfaces.IDrawable;
import Rendering.Renderer;
import org.javatuples.Pair;
import org.javatuples.Triplet;

public class Player implements IDrawable {
    public int xPos=0;
    public int yPos=0;

    @Override
    public void DrawMe() {
        Renderer.DrawQuad(
                new Pair<>((xPos * Tile.TILE_WIDTH)+(Tile.TILE_WIDTH_HALF),
                        (yPos * Tile.TILE_WIDTH)+Tile.TILE_WIDTH_HALF),
                Renderer.TileConstant,
                new Triplet<>(1f,1f,1f)
        );
    }

    public String DebugInfo() {
        return Integer.toString(xPos) + "," + Integer.toString(yPos);
    }
}
