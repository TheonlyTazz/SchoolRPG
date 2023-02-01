package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Skill extends Entity {

    public OBJ_Skill(GamePanel gp) {
        super(gp);
        name = "Skill";
        image1 = setup("/gui/ProgressBar/maxSkill");
        image2 = setup("/gui/ProgressBar/curSkill");
        image3 = setup("/gui/ProgressBar/curSkill_half");
        image4 = setup("/gui/ProgressBar/startOverlay");
        image5 = setup("/gui/ProgressBar/overlay");
    }
}
