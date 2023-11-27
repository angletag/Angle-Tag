package in.angleui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;

@SpringBootApplication
@ComponentScan(basePackages="in.angle*")
public class StockUiApplication {

    public static void main(String[] args) {
        Application.launch(ChartApplication.class, args);
    }

}
