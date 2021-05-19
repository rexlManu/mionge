package eu.miopowered.mionge.launch.utility.loader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Dependency informations for @see {@link DependencyLoader}
 */
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
public class Dependency {
    public static Dependency of(
            String groupId,
            String artifactId,
            String version,
            String maven
    ) {
        return new Dependency(groupId, artifactId, version, maven);
    }

    private String groupId, artifactId, version, maven;

    public String downloadUrl() {
        return String.format(
                "%s%s/%s/%s/",
                maven,
                groupId.replaceAll("\\.", "/"),
                this.artifactId,
                this.version
        );
    }

    public String jarName() {
        return String.format("%s-%s.jar", this.artifactId, this.version);
    }
}
