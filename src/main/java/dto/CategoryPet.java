package dto;

public class CategoryPet {
    private Integer id;
    private String name;

    public CategoryPet(Integer id, String name) {
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
        return "CategoryPet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
