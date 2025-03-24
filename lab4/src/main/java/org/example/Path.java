package org.example;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Path {
    @NonNull List<Location> locations;
    List<Pair<Location,Location>> path;
}
