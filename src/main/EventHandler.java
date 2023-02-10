package main;

public class EventHandler {

    GamePanel gp;
    EventRect[][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent() {
        // CHECK IF PLAYER MOVED AWAY
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);

        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) canTouchEvent = true;

        if (canTouchEvent){
            if(hit(47, 41, 0, "any", false)) switchMap(20, 27, 1);
            if(hit(48, 40, 0, "up", true)) switchMap(20, 27, 1);
            if(hit(53, 40, 0, "up", true)) switchMap(20, 27, 1);
            if(hit(54, 40, 0, "up", true)) switchMap(20, 27, 1);




        }

    }

    public boolean hit(int col, int row, int currentMap, String reqDirection, boolean keyPressed){
        boolean needKey = false;
        if(gp.currentMap != currentMap) return false;
        if(keyPressed){
            needKey = true;
            if (gp.keyH.dialoguePressed) needKey = false;
        }
        boolean hit = false;
        if(!needKey){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
            eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

            if(gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    canTouchEvent = false;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;

                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
            eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;



        }
        return hit;
    }


    public void healingPool(int col, int row, int gameState) {
        if(gp.keyH.dialoguePressed){
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.ui.currentDialogue = "You drink from the water!";
        }
    }

    public void switchMap(int col, int row, int map) {
        setPreviousPos((int) Math.floor(gp.player.worldX/gp.tileSize), (int) Math.floor(gp.player.worldY/gp.tileSize));
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize*col;
        gp.player.worldY = gp.tileSize*row-50;

    }

    public void setPreviousPos(int col,int row){
        switch(gp.player.direction){
            case "up" -> {
                gp.player.previousX = col;
                gp.player.previousY = row+1;
            }
            case "down" -> {
                gp.player.previousX = col;
                gp.player.previousY = row;
            }
            case "left" -> {
                gp.player.previousX = col+1;
                gp.player.previousY = row;
            }
            case "right" -> {
                gp.player.previousX = col-1;
                gp.player.previousY = row;
            }

        }
    }

    public void teleport(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You got Teleported";
        gp.player.worldX = gp.tileSize*23;
        gp.player.worldY = gp.tileSize*23;
    }
}
