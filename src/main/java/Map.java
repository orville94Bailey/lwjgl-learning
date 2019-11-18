import Interfaces.IDrawable;
import Rendering.Renderer;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.lwjgl.glfw.GLFW.*;

public class Map implements IDrawable{
    public Map(int height, int width){
        this.height = height;
        this.width = width;
        tiles = Stream.generate(Tile::new).limit(getCellColumns()*getCellRows()).collect(Collectors.toList());
        player = new Player();
    }

    @Override
    public void DrawMe() {
        for (int i = 0; i < tiles.size(); i++) {
            Renderer.DrawQuad(
                    getRenderCoordsFromIndex(i),
                    tileWidthHolder,
                    colors.get(tiles.get(i).getActivations()%colors.size())
                    );
        }
        player.DrawMe();
    }

    private List<Tile> tiles;

    private int height;
    private int width;
    private Pair<Float,Float> tileWidthHolder = new Pair<>((float)Tile.TILE_WIDTH, (float)Tile.TILE_WIDTH);
    private ArrayList<Triplet<Float,Float,Float>> colors;
    {
        colors = new ArrayList<>();
        colors.add(new Triplet<>(1f,0f,0f));
        colors.add(new Triplet<>(0f,1f,0f));
        colors.add(new Triplet<>(0f,0f,1f));
        colors.add(new Triplet<>(0f,1f,1f));
        colors.add(new Triplet<>(1f,0f,1f));
    }
    private Random random;
    {random = new Random();}
    private Player player;

    private int getCellRows(){
        return height / Tile.TILE_WIDTH;
    }
    private int getCellColumns(){
        return  width/ Tile.TILE_WIDTH;
    }
    private Pair<Integer, Integer> getRenderCoordsFromIndex(int index) {
        return new Pair<>(index%getCellColumns()*Tile.TILE_WIDTH+(Tile.TILE_WIDTH/2),index/getCellColumns()*Tile.TILE_WIDTH+(Tile.TILE_WIDTH/2));
    }

    private Pair<Integer,Integer> getCoordsFromIndex(int index){
        return new Pair<>(index%getCellColumns(), index/getCellColumns());
    }

    public void UpdatePlayer(int key) {
        ArrayList<Tile> applicableTiles;
        switch (key)
        {
            case GLFW_KEY_LEFT:
                applicableTiles = GetRemainingCells(player.xPos,player.yPos,direction.LEFT);
                player.xPos = getCoordsFromIndex(tiles.indexOf(applicableTiles.stream().findFirst().get())).getValue0();
                applicableTiles.forEach(tile -> tile.Activate());
                break;
            case GLFW_KEY_UP:
                applicableTiles = GetRemainingCells(player.xPos,player.yPos,direction.UP);
                player.yPos = getCoordsFromIndex(tiles.indexOf(applicableTiles.stream().findFirst().get())).getValue1();
                applicableTiles.forEach(tile -> tile.Activate());
                break;
            case GLFW_KEY_RIGHT:
                applicableTiles = GetRemainingCells(player.xPos,player.yPos,direction.RIGHT);
                System.out.println(applicableTiles);
                player.xPos = getCoordsFromIndex(tiles.indexOf(applicableTiles.stream().findFirst().get())).getValue0();
                applicableTiles.forEach(tile -> tile.Activate());
                break;
            case GLFW_KEY_DOWN:
                applicableTiles = GetRemainingCells(player.xPos,player.yPos,direction.DOWN);
                player.yPos = getCoordsFromIndex(tiles.indexOf(applicableTiles.stream().findFirst().get())).getValue1();
                applicableTiles.forEach(tile -> tile.Activate());
                break;
        }
    }

    //get cells in column
    private ArrayList<Tile> GetTilesInColumn(int column) {
        ArrayList<Tile> holder = new ArrayList<>();

        for (int i = column; i < tiles.size(); i+= getCellColumns()) {
            holder.add(tiles.get(i));
        }
        return holder;
    }
    //get cells in row
    private ArrayList<Tile> GetTilesInRow(int row) {
        ArrayList<Tile> holder = new ArrayList<>();

        for (int i = row * getCellColumns(); i < ((row*getCellColumns())+getCellColumns()); i++) {
            holder.add(tiles.get(i));
        }
        return holder;
    }
    private int CoordsToIndex(int x, int y) {
        return (y*getCellColumns())+x;
    }
    private ArrayList<Tile> GetRemainingCells(int x, int y, direction dir ){
        List<Tile> holder;
        long index;
        switch (dir) {
            case UP:
                holder = GetTilesInColumn(x);
                Collections.reverse(holder);
                index=holder.indexOf(tiles.get(CoordsToIndex(x,y)));
                return (ArrayList<Tile>) holder.stream().limit(index).takeWhile(tile -> tile.canBeMovedThrough()).collect(Collectors.toList());
            case DOWN:
                holder = GetTilesInColumn(x);
                index=holder.indexOf(tiles.get(CoordsToIndex(x,y)));
                return (ArrayList<Tile>) holder.stream().limit(index).takeWhile(tile -> tile.canBeMovedThrough()).collect(Collectors.toList());
            case LEFT:
                holder = GetTilesInRow(y);
                index=holder.indexOf(tiles.get(CoordsToIndex(x,y)));
                return (ArrayList<Tile>) holder.stream().limit(index).takeWhile(tile -> tile.canBeMovedThrough()).collect(Collectors.toList());
            case RIGHT:
                holder = GetTilesInRow(y);
                Collections.reverse(holder);
                index=holder.indexOf(tiles.get(CoordsToIndex(x,y)));
                return (ArrayList<Tile>) holder.stream().limit(index).takeWhile(tile -> tile.canBeMovedThrough()).collect(Collectors.toList());
            default:
                return null;
        }
    }

    private enum direction{
        LEFT,RIGHT,UP,DOWN
    }
}
