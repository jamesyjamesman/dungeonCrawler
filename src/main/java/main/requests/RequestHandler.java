package main.requests;

import main.Game;

public class RequestHandler {
    public static void handler() {
        GameRequests.game();
        Rooms.requestHandler();
    }
}
