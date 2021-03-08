import com.revature.gameStart.api.RawgApi;
import com.revature.gameStart.api.RawgGame;
import com.revature.gameStart.models.Game;

import java.util.Arrays;

public class RawgApiTest {
    public static void main(String[] args) {
        RawgApi api = new RawgApi();

        RawgGame[] games = api.getGames();

        for (RawgGame game:
             games) {
            System.out.println(game.toString());
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        RawgGame portal = api.getGame("portal-2");
        System.out.println(portal.toString());
    }
}
