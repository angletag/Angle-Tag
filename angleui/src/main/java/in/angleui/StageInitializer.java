package in.angleui;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import in.angleui.ChartApplication.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final String applicationTitle;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle) {
        this.applicationTitle = applicationTitle;

    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        Resource res = new ClassPathResource("form.fxml");
        FXMLLoader fxmlLoader;
        try {
            fxmlLoader = new FXMLLoader(res.getURL());
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle(applicationTitle);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
