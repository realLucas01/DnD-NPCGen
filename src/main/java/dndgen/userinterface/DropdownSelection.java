package dndgen.userinterface;

import java.io.Serial;
import java.io.Serializable;

/**
 * Contains the parameters for the dropdown selection and the previously selected values.
 */
public class DropdownSelection implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String race;
    private String genClass;
    private String job;
    private String level;
    private final String abilisc;
    private String gender;

    public DropdownSelection(String name, String race, String genClass, String job, String level, String abilisc, String gender) {
        this.name = name;
        this.race = race;
        this.genClass = genClass;
        this.job = job;
        this.level = level;
        this.abilisc = abilisc;
        this.gender = gender;
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGenClass() {
        return genClass;
    }

    public void setGenClass(String genClass) {
        this.genClass = genClass;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAbilisc() {
        return abilisc;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}