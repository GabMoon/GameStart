import com.revature.gameStart.api.RawgApi;
import com.revature.gameStart.models.Game;

public class RawgApiTest {
    public static void main(String[] args) {
        RawgApi api = new RawgApi();

        Game[] games = api.getGames();
    }
}
