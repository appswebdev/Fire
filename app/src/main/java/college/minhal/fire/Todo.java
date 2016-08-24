package college.minhal.fire;

/**
 * Created by master on 24/08/16.
 */
public class Todo {
    private String title;
    private String description;

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }
    //POJO: Default constructor:
    public Todo() {}
    //POJO: Getters and Setters:
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
}
