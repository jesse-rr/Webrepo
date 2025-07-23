package org.project.readingplatform.models.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Metadata {

    private int chapterQuantity;
    private int viewsQuantity;
    private int bookmarksQuantity;
    private int reviewQuantity;
    private int readLaterQuantity;
    private int commentQuantity;
}
