package dto;

public class TagsPet {
    private Integer id;
    private String name;

    public TagsPet(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagsPet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
