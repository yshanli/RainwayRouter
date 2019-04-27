package route.impl;


import route.interfaces.SiteFileLoaderInterface;
import route.module.Route;
import route.module.RouteContainer;
import route.utils.RouteBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SiteFileLoader implements SiteFileLoaderInterface {
    private final RouteBuilder routeBuilder;
    private RouteContainer routeContainer;

    public SiteFileLoader(RouteContainer routeContainer, RouteBuilder routeBuilder) {
        this.routeContainer = routeContainer;
        this.routeBuilder = routeBuilder;
    }
    private static final String DEFAULT_INPUT_FILE_PATH  = System.getProperty("user.dir") + "/example_input.txt";

    public void loadSiteFile(String inputFilePath) {
        String targetFilePath = DEFAULT_INPUT_FILE_PATH;
        if (inputFilePath != null && !inputFilePath.isEmpty()) {
            targetFilePath = inputFilePath;
        }

        File inputFile = new File(targetFilePath);
        try{
            BufferedReader br = new BufferedReader(new FileReader(inputFile));//构造一个BufferedReader类来读取文件
            String str;
            while((str = br.readLine())!=null){//使用readLine方法，一次读一行
                this.handleLine(str);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void handleLine(String line) {
        System.out.println("handle line: " + line);
        String[] inputs = line.split(",");
        for (int i = 0; i < inputs.length; i++) {
            Route route = routeBuilder.build(inputs[i]);
            if (route != null && !this.routeContainer.find(route.from, route.to)) {
                this.routeContainer.add(route);
            }
        }
    }
}
