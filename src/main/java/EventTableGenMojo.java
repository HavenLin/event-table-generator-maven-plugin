import markdown.table.Table;
import markdown.table.TableRow;
import markdown.util.MarkdownConverter;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "errorCodeGen", defaultPhase = LifecyclePhase.COMPILE,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class EventTableGenMojo extends AbstractMojo {
    @Parameter(required = true)
    private String eventClass;

    @Parameter(defaultValue = "EVENTMESSAGE.md")
    private String outputPath;

    @Parameter(required = true)
    private String idGetter;

    @Parameter(required = true)
    private String messageGetter;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            ClassLoader newClassLoader = getProjectClassLoader();
            Class clazz = newClassLoader.loadClass(eventClass);
            Object[] clazzEnumConstants = clazz.getEnumConstants();
            List<TableRow> tableRows = new ArrayList<>();
            for (int i = 0; i < clazzEnumConstants.length; i++) {
                Object item = clazzEnumConstants[i];
                Method idGetterMethod = clazz.getMethod(idGetter);
                Object errorCode = idGetterMethod.invoke(item);
                Method messageGetterMethod = clazz.getMethod(messageGetter);
                Object errorMessage = messageGetterMethod.invoke(item);

                TableRow tableRow = new TableRow();
                List columns = tableRow.getColumns();
                columns.add(errorCode);
                columns.add(errorMessage);
                tableRow.setColumns(columns);
                tableRows.add(tableRow);
            }
            Table table = new Table();
            table.setRows(tableRows);
            String tableWithMd = MarkdownConverter.serializedTable(table);
            Files.write(Paths.get(outputPath), tableWithMd.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ClassLoader getProjectClassLoader() throws DependencyResolutionRequiredException, MalformedURLException {
        List<URL> listUrl = new ArrayList<>();
        List<String> classpathElements = project.getRuntimeClasspathElements();
        for (String item : classpathElements) {
            listUrl.add(new File(item).toURI().toURL());
        }

        URL[] urls = listUrl.toArray(new URL[listUrl.size()]);
        return new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
    }
}
