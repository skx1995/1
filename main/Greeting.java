package hello;



public class Greeting {

    private long id;
    private String content;
    private String createdTime;

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Greeting(long id, String content, String createdTime) {
        this.id = id;
        this.content = content;
        this.createdTime = createdTime;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedTime(){return createdTime;}

    public void setCreatedTime(String time) {this.createdTime=time;
    }
}
