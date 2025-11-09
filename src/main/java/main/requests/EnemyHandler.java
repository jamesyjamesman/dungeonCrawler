package main.requests;

import io.javalin.Javalin;
import main.App;
import main.entity.Player;
import main.entity.enemy.Enemy;
import main.room.EnemyRoom;

import java.util.UUID;

public class EnemyHandler {
    public static void enemyHandler() {
        Javalin server = App.INSTANCE.getServer();
        server.post("/enemy/takeDamage", ctx -> {
            record RoomEnemy(int roomID, UUID uuid) {}
            RoomEnemy getInfo = ctx.bodyAsClass(RoomEnemy.class);
            EnemyRoom room = (EnemyRoom) Rooms.getRoom(getInfo.roomID);
            UUID enemyUuid = getInfo.uuid();
            Player player = App.INSTANCE.getPlayer();
            Enemy attackedEnemy = null;

            for (Enemy enemy : room.getEnemies()) {
                if (enemy.getUuid().equals(enemyUuid)) {
                    attackedEnemy = enemy;
                    enemy.takeDamage(player.weakenAttack(player.calculateTotalAttack()));
                }
            }
            ctx.json(attackedEnemy);
        });
    }
}