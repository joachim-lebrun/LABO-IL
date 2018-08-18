package be.unamur.laboil.domain.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
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
}

