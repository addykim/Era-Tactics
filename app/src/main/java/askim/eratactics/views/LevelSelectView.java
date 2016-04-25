package askim.eratactics.views;

import android.view.View;

/**
 * Created by addykim on 4/21/16.
 */
public class LevelSelectView {

    private String levelTitle;
    private String completionThumbnail;

    public String getTitle() {
        return levelTitle;
    }

    public void setTitle(String title) {
        levelTitle = title;
    }

    public String getThumbnail() {
        return completionThumbnail;
    }

    public void setThumbnail(String thumbnail) {completionThumbnail = thumbnail;
    }
}
