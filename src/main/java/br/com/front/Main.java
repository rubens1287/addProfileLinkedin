package br.com.front;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static java.awt.Toolkit.getDefaultToolkit;

public class Main extends JFrame{
    private JButton button;
    private JPanel panel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    private JLabel jResults;
    public static WebDriver driver;


    public Main() {

        super("Linkedin Add profiles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        getContentPane().add(panel);
        //setResizable(false);
        //pack();
        //setLocationRelativeTo(null);
        setVisible(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    String decodedPath = null;
                    int perfiladd = 0;
                    int pages;

                    jResults.setText("0");
                    //System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\drivers\\chromedriver\\win32\\75.0.3770.140\\chromedriver.exe");
                    WebDriverManager.chromedriver().config().setTargetPath("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Google\\Chrome\\Application");
                    WebDriverManager.chromedriver().setup();

                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("debuggerAddress", "127.0.0.1:9014");
                    driver = new ChromeDriver(options);
                    System.out.println(driver.getTitle());

                    if (!textField2.getText().isEmpty()) {
                        searchAnyThing(textField2.getText());
                        esperar(2);
                        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Pessoas']")));
                        driver.findElement(By.xpath("//span[text()='Pessoas']")).click();
                    }


                    Actions action = new Actions(driver);


                    if (textField1.getText().isEmpty()) {
                        pages = 1;
                    } else {
                        pages = Integer.parseInt(textField1.getText());
                    }

                    esperar(1);

                    for (int l = 0; l < pages; l++) {

                        List<WebElement> conexoes;

                        endOfPage(driver);

                        esperar(1);

                        endOfPage(driver);

                        esperar(1);

                        conexoes = conexoes = driver.findElements(By.xpath("//button[@data-control-name='srp_profile_actions']"));

                        for (int j = 0; j < conexoes.size(); j++) {
                            esperar(1);
                            if (conexoes.size() != 0) {
                                if (conexoes.get(j).getText().contains("Conectar")) {

                                    action.moveToElement(conexoes.get(j)).perform();

                                    if (conexoes.get(j).isEnabled()) {
                                        esperar(1);
                                        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(conexoes.get(j)));
                                        conexoes.get(j).click();
                                        esperar(1);
                                        driver.findElement(By.xpath("//div[contains(@class,'send-invite__actions')]//button[2]")).click();
                                        perfiladd++;
                                        jResults.setText(String.valueOf(perfiladd));
                                    }
                                }
                            }
                        }
                        esperar(1);
                        action.moveToElement(driver.findElement(By.xpath("//span[text()='Avançar']"))).perform();
                        endOfPage(driver);
                        esperar(1);
                        driver.findElement(By.xpath("//span[text()='Avançar']")).click();
                    }


                    JOptionPane.showMessageDialog(null, "Processamento finalizado!");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage() + " \n\n ENTRE EM CONTATO COM RUBENS LOBO");
                }
            }

        });


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                ProcessBuilder builder = new ProcessBuilder(
                        "cmd.exe", "/c", "cd \"C:\\Users\\"+System.getProperty("user.name")+"\\AppData\\Local\\Google\\Chrome\\Application\" && " +
                        "chrome.exe -remote-debugging-port=9014 --user-data-dir=\"C:\\Users\\"+System.getProperty("user.name")+"\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Default\" ");
                builder.redirectErrorStream(true);
                Process p = null;

                    p = builder.start();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage() +" \n\n ENTRE EM CONTATO COM RUBENS LOBO");
                }

            }
        });
    }

    public static void main(String[] args){
         new Main();
    }



    public void searchAnyThing(String value){
        driver.findElement(By.xpath("//input[@placeholder='Pesquisar']")).clear();
        driver.findElement(By.xpath("//input[@placeholder='Pesquisar']")).sendKeys(value);
        driver.findElement(By.xpath("//input[@placeholder='Pesquisar']")).sendKeys(Keys.ENTER);
    }

    public List<WebElement> endOfPage(WebDriver driver){
        List<WebElement> conexoes = null;
        for (int i = 0; i < 4; i++) {
            driver.findElement(By.xpath("//body")).sendKeys(Keys.PAGE_DOWN);
        }
        return conexoes;
    }

    public static void esperar(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
