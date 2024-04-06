package file;
import controller.GameEngine;
public class Adapter extends MapLoader{
    private ConquestLoader d_ConquestLoader;
    private GameEngine d_GameEngine;

    public Adapter(GameEngine p_GameEngine){
        super(p_GameEngine);
    }
    public Adapter (ConquestLoader p_ConquestLoader,GameEngine p_GameEngine){
        super(p_GameEngine);
        this.d_ConquestLoader = p_ConquestLoader;
        this.d_GameEngine = p_GameEngine;
    }

    @Override
    public String loadMap(String p_mapfile){
        return this.d_ConquestLoader.loadConquestMap(p_mapfile, d_GameEngine);
    }

    @Override
    public String saveMap(String p_mapfile){
        return this.d_ConquestLoader.saveConquestMap(p_mapfile, d_GameEngine);
    }
}
