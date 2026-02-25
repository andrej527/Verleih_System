
public class App {
    public static void main(String[] args) throws Exception {
        DataBaseControl.runDatabase();
        DataBaseControl.setBuch(14102003, "Think and Grow rich", 0);
        DataBaseControl.setBuch(21052001, "Sapiens", 0);
        new Gui();
    }
}
