package file;
import controller.GameEngine;
public class MapLoader {

    GameEngine d_GameEngine;

    public MapLoader(){
    }

    public MapLoader(GameEngine p_GameEngine){
        this.d_GameEngine = p_GameEngine;
    }

    public String loadMap(String p_mapfile) throws Exception{
        return this.d_GameEngine.getMapController().loadMap(p_mapfile);
    }

    public String saveMap(String p_mapfile) throws Exception{
        return this.d_GameEngine.getMapController().saveMap(p_mapfile);
    }
}
