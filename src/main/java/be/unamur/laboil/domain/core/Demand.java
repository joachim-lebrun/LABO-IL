package be.unamur.laboil.domain.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Demand {


    private String demandID;
    private Service service;
    private String name;
    private String communalName;
    private Citizen creator;
    private Employee verificator;
    private TreeSet<Event> history;
    private List<File> documents;
    private Map<String, String> information;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("demandID", demandID)
                .append("name", name)
                .append("communalName", communalName)
                .toString();
    }
}

