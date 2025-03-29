package shell;

import repository.Repository;

import freemarker.template.*;

import java.awt.Desktop;
import java.io.*;
import java.util.*;

public class ReportCommand implements Command {
    private static final String TEMPLATE_FILE = "report_template.ftl";
    private static final String OUTPUT_FILE = "report.html";
    
    public void execute(String[] args, Repository repository) {
        try {
            generateReport(repository);
            openReportInBrowser();
        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    private void generateReport(Repository repository) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");



        Template template = cfg.getTemplate(TEMPLATE_FILE);

        Map<String, Object> data = new HashMap<>();
        data.put("images", repository.getAllImages());


        try (Writer fileWriter = new FileWriter(OUTPUT_FILE)) {
            template.process(data, fileWriter);
        }

        System.out.println("Report generated: " + OUTPUT_FILE);
    }

    private void openReportInBrowser() {
        try {
            File htmlFile = new File(OUTPUT_FILE);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } else {
                System.out.println("Desktop is not supported. Open report manually: " + OUTPUT_FILE);
            }
        } catch (IOException e) {
            System.out.println("Error opening report in browser: " + e.getMessage());
        }
    }
}
