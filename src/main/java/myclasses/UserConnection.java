package myclasses;

public class UserConnection {

    private String id;
    private String requesterId;
    private String receiverId;
    private String status;
    private String requesterName;
    private String connectionName;
    private String connectionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getRequesterName() {
    return requesterName;
}

public void setRequesterName(String requesterName) {
    this.requesterName = requesterName;
}

public String getConnectionName() {
    return connectionName;
}

public void setConnectionName(String connectionName) {
    this.connectionName = connectionName;
}

public String getConnectionId() {
    return connectionId;
}

public void setConnectionId(String connectionId) {
    this.connectionId = connectionId;
}
}