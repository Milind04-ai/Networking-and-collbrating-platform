package myclasses;

public class Project {

    private String id;
    private String ownerId;
    private String ownerName;

    private String title;
    private String description;
    private String emoji;
    private String status;
    private String tags;
    private String openRolesHtml;
    private String teamHtml;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

public String getOpenRolesHtml() {
    return openRolesHtml;
}

public void setOpenRolesHtml(String openRolesHtml) {
    this.openRolesHtml = openRolesHtml;
}

public String getTeamHtml() {
    return teamHtml;
}

public void setTeamHtml(String teamHtml) {
    this.teamHtml = teamHtml;
}
}