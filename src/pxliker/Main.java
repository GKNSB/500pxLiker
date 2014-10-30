/*
 * !!!Edit the options below!!!
 *
*/
package pxliker;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author Manos
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    // OPTIONS !!!
    static int min = 2000;
    static int max = 6000;
    static boolean likePhotos = true;
    static boolean favPhotos = true;
    static boolean commentPhotos = false;
    static int percentageOfLikes = 90;
    static int percentageOfFavs = 40;
    static int percentageOfComments = 20;
    static int numLikes = 0, numFavs = 0, numComments = 0, numDuplicates = 0, numInteracted = 0;
    static String pageCategory = "upcoming";
    static int numberOfPages = 2;
    static int startingPage = 1;
    static boolean extraDelay = false;
    static String[] good = {"great", "beautiful", "gorgeous", "brilliant", "nice", "cool", "magnificent", "fabulous", 
                        "pleasing", "lovely", "delightful", "stunning", "bewitching", "exquisite", "smashing", 
                        "excellent", "fascinating", "enticing", "lovely", "enchanting", "graceful", "pretty",
                        "tasteful", "nicely done"};
    static String[] photo = {"photo", "depiction", "image", "photograph", "picture", "shot", "capture"};
    static String[] compliment = {"congrats", "good job", "great job", "good work", "great work", "nicely done", "keep it up"};
    static String[] mark = {"!", "!!", "!!!"};

    public static void main(String[] args) throws AWTException, InterruptedException, IOException, NoSuchAlgorithmException, Exception {

        int pages=0;
        System.out.println("The robot will start in 10 seconds");
        Thread.sleep(10000);
        Robot robot = new Robot();
        //MAIN FUNCTION OF INTERACTING !!!
        for (int page=0; page<numberOfPages; page++){
            nextPage(robot,page);
            robot.delay(randomNumber(min,max));
            openTabs(robot);
            //interactWithPhotos(robot);
            doExtraDelay(robot);
            //System.out.println(takeHash(robot));
            //System.out.println(isPreviouslyInteractedWith(robot));
            //doComment(robot);
            //lala(robot);
            pages++;
        }

        System.out.println("Interacted with "+ pages + " pages of images");
        System.out.println("Interactions: " + numInteracted + " Duplicates: " + numDuplicates + " <<>> Likes: " + numLikes + " Favs: " + numFavs + " Comments: " + numComments);
    }

    private static void leftClick(Robot robot){
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(200);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(200);
    }
    private static void rightClick(Robot robot){
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.delay(200);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
        robot.delay(200);
    }
    private static void openInNewTab(Robot robot, int x, int y){
        robot.delay(randomNumber(1000,3000));
        rightClick(robot);
        robot.delay(1000);
        x=x+20;
        y=y+20;
        robot.mouseMove(x, y);
        robot.delay(1000);
        leftClick(robot);
        robot.delay(1000);
        try {
            robot.delay(1241);
            robot.mouseMove(10, 400);
            interactWithPhotos(robot);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void moveDownWheel(Robot robot, int steps){
        robot.delay(1000);
        robot.mouseWheel(steps);
        robot.delay(1000);
    }
    private static void openTabs(Robot robot){
        robot.delay(randomNumber(min,max));
        robot.mouseWheel(1);
        robot.delay(randomNumber(min,max));
        for(int i = 0; i <= 8; i++){
            if (i <= 1){
                robot.mouseMove(506, 189);
                openInNewTab(robot,506,189);
                robot.mouseMove(818, 194);
                openInNewTab(robot,818,194);
                robot.mouseMove(1120, 191);
                openInNewTab(robot,1120,191);
                moveDownWheel(robot, 3);
            }
            else {
                robot.mouseMove(200, 195);
                openInNewTab(robot,200,195);
                robot.mouseMove(506, 189);
                openInNewTab(robot,506,189);
                robot.mouseMove(818, 194);
                openInNewTab(robot,818,194);
                robot.mouseMove(1120, 191);
                openInNewTab(robot,1120,191);
                moveDownWheel(robot, 3);
            }
        }
    }
    private static void nextTab(Robot robot) {
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(40);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }
    private static void closeTab(Robot robot){
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(40);
        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }
    private static void nextPage(Robot robot, int toAdd){
        if (toAdd != 0) {
            robot.delay(2000);
            closeTab(robot);
            robot.delay(2000);
        }
        int newPage = startingPage + toAdd;
        try
        {
            Desktop.getDesktop().browse(new URL("http://500px.com/" + pageCategory + "?page=" + newPage).toURI());
        }
        catch (Exception e) {}

        robot.mouseMove(900, 653);
        robot.delay(500);
        leftClick(robot);
        robot.delay(1000);
    }
    private static void doLike(Robot robot){
        robot.mouseMove(1181, 181);
        robot.delay(500);
        leftClick(robot);
        robot.delay(500);
        //photoCounter++;
    }
    private static void doFav(Robot robot){
        robot.mouseMove(1245, 181);
        robot.delay(500);
        leftClick(robot);
        robot.delay(500);
        //photoCounter++;
    }
    private static void interactWithPhotos(Robot robot) throws IOException, NoSuchAlgorithmException, Exception{
        robot.delay(2000);
        nextTab(robot);
        Boolean isPreviously;
        for(int i = 0; i < 1; i++){
            robot.delay(1000);
            isPreviously = false;
            isPreviously = isPreviouslyInteractedWith(robot);
            if (isPreviously == false) {
                if(likePhotos == true){
                    if ((int)(Math.random() * 100) <= percentageOfLikes) {
                        doLike(robot);
                        numLikes++;
                    }
                }
                if(favPhotos == true){
                    if ((int)(Math.random() * 100) <= percentageOfFavs) {
                        doFav(robot);
                        numFavs++;
                    }
                }
                if(commentPhotos == true){
                    if ((int)(Math.random() * 100) <= percentageOfComments) {
                        doComment(robot);
                        numComments++;
                    }
                }
                numInteracted++;
            }
            else { numDuplicates++; }
            robot.delay(randomNumber(min,max));
            closeTab(robot);
        }
    }
    private static int randomNumber(int min, int max){
        Random rand = new Random();
        int result = rand.nextInt((max - min) + 1) + min;
        return result;
    }
    private static String createRandomComment() {
        Random random = new Random();
        String comment = "";
        String subString1 = "";
        String subString2 = "";
        subString1 = subString1 + good[(int)(Math.random() * good.length)] + " ";
        subString1 = subString1 + photo[(int)(Math.random() * photo.length)] + "";
        subString1 = subString1 + mark[(int)(Math.random() * mark.length)] + " ";
        subString1 = subString1.substring(0,1).toUpperCase() + subString1.substring(1);
        subString2 = subString2 + compliment[(int)(Math.random() * compliment.length)] + "";
        subString2 = subString2 + mark[(int)(Math.random() * mark.length)];
        subString2 = subString2.substring(0,1).toUpperCase() + subString2.substring(1);
        comment = subString1 + subString2;
        return comment;
    }
    /*static void type(Robot robot, String comment){

    }*/
    private static void typeMessage(Robot robot, String message){
        for (int i = 0; i < message.length(); i++){
            robot.delay(200);
            handleRepeatCharacter(robot, message, i);
            type(robot, message.charAt(i));
        }
    }
    private static void handleRepeatCharacter(Robot robot, String message, int i){
        if(i == 0)
            return;
        //The robot won't type the same letter twice unless we release a key.
        if(message.charAt(i) == message.charAt(i-1)){
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
    }
    private static void type(Robot robot, char character){
        handleSpecialCharacter(robot, character);

        if (Character.isLowerCase(character)){
            typeCharacter(robot, Character.toUpperCase(character));
        }
        if (Character.isUpperCase(character)){
            typeShiftCharacter(robot, character);
        }
        if (Character.isDigit(character)){
            typeCharacter(robot, character);
        }
    }
    private static void handleSpecialCharacter(Robot robot, char character){
        if (character == ' ')
            typeCharacter(robot, KeyEvent.VK_SPACE);
        if (character == '.')
            typeCharacter(robot, KeyEvent.VK_PERIOD);
        if (character == '!')
            typeShiftCharacter(robot, KeyEvent.VK_1);
        if (character == '?')
            typeShiftCharacter(robot, KeyEvent.VK_SLASH);
        if (character == ',')
            typeCharacter(robot, KeyEvent.VK_COMMA);

        //More specials here as needed
    }
    private static void typeCharacter(Robot robot, int character){
        robot.keyPress(character);
    }
    private static void typeShiftCharacter(Robot robot, int character){
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(character);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }
    private static void findCommentBox(Robot robot){
        moveDownWheel(robot, 5);
        int x = 540;
        int hashOfBox = -1118222;
        int screenHeight = GetScreenWorkingHeight();
        boolean found = false;
        while(found == false){
            moveDownWheel(robot,2);
            for(int y = 400; y <= screenHeight; y++){
                Color colorOfPoint = robot.getPixelColor(x, y);
                Color colorOfPoint2 = robot.getPixelColor(x, y+10);
                if ((hashOfBox == colorOfPoint.hashCode()) && (hashOfBox == colorOfPoint2.hashCode())) {
                    //System.out.println("Found the commentbox in coordinates "+x+", "+y+".");
                    moveDownWheel(robot,1);
                    robot.mouseMove(x, y -90);
                    found = true;
                    break;
                }
            }
        }
    }
    /*private static void lala(Robot robot){
        PointerInfo info = MouseInfo.getPointerInfo();
        Point location = info.getLocation();
        int x = (int) location.getX();
        int y = (int) location.getY();
        Color color = robot.getPixelColor(x,y);
        int alpha = color.getAlpha();
        System.out.println(color.hashCode());
        System.out.println("location " + x + ", " + y + " has color " + color + " and alpha " + alpha);
    }*/
    private static int GetScreenWorkingHeight() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }
    private static void doComment(Robot robot){
        findCommentBox(robot);
        leftClick(robot);
        typeMessage(robot, createRandomComment());
        robot.delay(500);
        robot.delay(500);
        moveToGreenPostButton(robot);
        leftClick(robot);
    }
    private static void moveToGreenPostButton(Robot robot) {
        PointerInfo info = MouseInfo.getPointerInfo();
        Point location = info.getLocation();
        int x = (int) location.getX();
        int y = (int) location.getY();
        robot.mouseMove(x +200, y +90);
    }
    private static void doExtraDelay(Robot robot) throws InterruptedException {
             if (extraDelay == true){
                int extraSleep = (int) (randomNumber(min,max) * 90);
                //System.out.println("Will now sleep for " + extraSleep/1000 + " seconds");
                Thread.sleep(extraSleep);
            }
    }
    private static String takeHash(Robot robot) throws IOException, NoSuchAlgorithmException, Exception{
        String finalHash = "";
        int x = 300;
        int y = 300;
        int width = 800;
        int height = 350;
        Rectangle area = new Rectangle(x,y,width,height);
        BufferedImage bufferedImage = robot.createScreenCapture(area);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        byte[] data = outputStream.toByteArray();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] hash = md.digest();
        finalHash = returnHex(hash);
        return finalHash;
    }
    private static String returnHex(byte[] inBytes) throws Exception {
        String hexString = "";
        for (int i=0; i < inBytes.length; i++) {
            hexString +=
            Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return hexString;
    }
    private static Boolean isPreviouslyInteractedWith(Robot robot) throws IOException, NoSuchAlgorithmException, Exception{
        Boolean check = false;
        String recentHash = takeHash(robot);
        List<String> previousHashes = readDB();
        for(String anOldHash : previousHashes){
            if (recentHash.equals(anOldHash)) {
                check = true;
            }
        }
        if (check == false) {
            writeDB(recentHash);
        }
        return check;
    }
    private static List<String> readDB() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("db.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        List<String> hashes = new ArrayList<String>();
        String strLine;
        while ((strLine = br.readLine()) != null)   {
            hashes.add(strLine);
        }
        return hashes;
    }
    private static void writeDB(String hash) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("db.txt", true));
        pw.println(hash);
        pw.close();
    }
}
