// Kanchan Kaushik
// 5/19/22
// LexiconLaunch.java
/* Game Description: 
 * To play the game, begin by navigating 
 * back to the home page and clicking the "Start Game" button. 
 * This will lead you to the actual game screen where you can play. 
 * The game screen will have a rocket on the left side with an abstruse 
 * (synonym: difficult to understand) word on it, and the there will be 
 * three asteroids on the right side with words as well. One of the words 
 * on the asteroids is a synonym of the word on the rocket, and the other 
 * two asteroids contain random words. Now, these asteroids move fairly 
 * quickly towards the left side of the screen, so you must work quickly! 
 * Your mission is to correctly drag the rocket onto the asteroid with the
 * synonym of the word on the rocket before the asteroids move offscreen. 
 * If you drag the rocket onto the wrong asteroid, an explosion will occur, 
 * but you will be able to continue playing. You will be directed to a panel 
 * that corrects all your mistakes and shows you what you got wrong and how to
 * improve. Then, you must take the quiz. The quiz will be on all the same words
 * you played the game on, this time with different synonym options. Once you 
 * finish the quiz, a high score board will be shown (if you enter your information),
 * and if you did well enough, your name might just make it onto the board! 
 * Good luck and have fun on your synonym spaceflight!
 */
/* Working On:
 * GUI graphics
 * layouts (all types)
 * control structures
 * specifically card layout
 * components/listeners
 * Timers/animations
 * Reading and Writing to Text Files
 * New components such as popups, different versions of components (ex. ImageIcon)
 */

// image, layout, and graphics imports
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

// frame and panel imports
import javax.swing.JFrame;	
import javax.swing.JPanel;

// Component imports
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JScrollBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import java.awt.Insets;
import javax.swing.Popup;
import javax.swing.PopupFactory;

// Event and listener methods
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseListener;	
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

// File IO imports
import java.io.File;		
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

// Timer import
import javax.swing.Timer;

// Thread Imports
import java.lang.Thread;
import java.lang.InterruptedException;

// frame class
public class LexiconLaunch
{	
    // empty constructor (no FVs)				
	public LexiconLaunch()
	{
	}
	
    // creates instance of itself and uses it to call start()
    // and create the frame
	public static void main(String[] args)
	{
		LexiconLaunch lL = new LexiconLaunch();
		lL.start();
	}
	
    /* creates the frame and sets its specifics, like
     * size, location, resizable, etc. It adds the FullGameHolder
     * panel to it and sets visible
     */
	public void start()
	{
		JFrame frame = new JFrame("LexiconLaunch");
		frame.setSize(1000, 700);				
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE); 
		frame.setLocation(0,0);
		frame.setResizable(false);
		FullGameHolder fgh = new FullGameHolder(); 		
		frame.getContentPane().add( fgh );		
		frame.setVisible(true);		
	}
}

/* This class is the holder class for the card layout,
 * which holds all of the different panels for the game
 */
class FullGameHolder extends JPanel
{
	private CardLayout panelCards; // FV for card layout of this panel class
	private AllGameInfo agi; // FV instance of information class
	private Scanner fileReader; // FV scanner reads in files
	private String tacticsName; // FV stores tactics text file name
    private String starsName; // stores name of stars image
    private Image starsImg; // stores the stars image used in mult. panels
    private Color blue; // blue color used inn all panels
	
    /* constructor sets background color, initializes field variables,
     * sets layout to card layout, calls methods that read in text files, creates 
     * instances of all other panel classes, and adds them to card layout. Most instances
     * have passed in instances of this class, the CardLayout, and the info class (and
     * other params. if applicable).
     */
	public FullGameHolder()
	{
		setBackground(Color.BLACK);
		panelCards = new CardLayout();
		setLayout(panelCards);
		
		agi = new AllGameInfo();
		
		fileReader = null;
		tacticsName = new String("tactics.txt");

        blue = new Color(7,28,124);

        starsName = new String("longStars.png");
        starsImg = agi.getTheImage(starsName);
	
		String tacticsString = new String(getStringFromFile(tacticsName));
		
		SettingsPagePanel spp = new SettingsPagePanel(this, panelCards, agi);
		InstructionsPanel ipp = new InstructionsPanel(this, panelCards, agi, blue);
		GenericTextAreaPanel hsp = new GenericTextAreaPanel(this, panelCards,
            "    High Scores", "" + agi.getHighScores(), agi);
        ResultsPagePanel rpp = new ResultsPagePanel(this, panelCards, agi, hsp);
        HomePagePanel hpp = new HomePagePanel(this, panelCards, agi, starsImg, rpp);
		GenericTextAreaPanel tp = new GenericTextAreaPanel(this, panelCards, 
			"Tactics & Tips", tacticsString, agi);
		QuizScreenPanel qsp = new QuizScreenPanel(this, panelCards, agi, blue);
		TeachingPagePanel tpp = new TeachingPagePanel(this, panelCards, agi, qsp, blue, tp);
        StartGamePanel sgp = new StartGamePanel(this, panelCards, agi, tpp, starsImg, blue);
		
		add(hpp, "Home");
		add(spp, "Settings");
		add(ipp, "Instructions");
		add(hsp, "High Scores");
		add(tp, "Tactics");
		add(sgp, "Game");
		add(tpp, "Teaches");
		add(qsp, "Quiz");
		add(rpp, "Results");
	}
	
    /* this method reads in the tactics text files.
     * It uses a variable passed in for the file name
     * ti read from. It initializes the fileReader 
     * Scanner FV to the call of the method in AGI class
     * that creates scanners. It runs through whole file
     * and saves as string, returning it.
     */
	public String getStringFromFile(String inFileName)
	{
        fileReader = agi.readAFile(inFileName);

        String storingString = new String("");
        
        while(fileReader.hasNext())
        {
			storingString += fileReader.nextLine() + "\n";
		}
		
		return storingString;
	}
}

/* This panel class holds the buttons that lead
 * to all the other panels in the card layout.
 * The card layout starts out on this panel.
 */
class HomePagePanel extends JPanel
{
	private FullGameHolder fgh; // FV instance of card holder class to show other cards
	private CardLayout panelCards; // FV instance of card layout to show other cards
    private AllGameInfo agi; // FV instance of information class

    private Image logoPic; // FV instance of the logo image drawn onto panel
    private String logoName; // FV for name of logo image to get 

    private String[] picNames; // FV string array of all picNames for icon images
	
    private JButton[] allButtons; // FV JButton array to place components on screen

    private ImageIcon[] iconPics; // FV ImageIcon array stores all button icons

    private String[] buttonNames; // FV array stores names of all buttons
    
    private Image starsImg; // image for background stars
    
    private ResultsPagePanel rpp; // used to clear info from page whenever home is reached

    /* Constructor intializes all field vars, including to the parameters passed in.
     * Background color set, layout set to null layout. All strings and images 
     * initialized and read in. picNames is initialized to an array of all the 
     * icon image names. allButtons is initialized to an array of the same length
     * as the pictures. The logo pic is initialized to a return value of a method 
     * that reads in the image.
     * A method that makes and adds the buttons and label to the panel is called.
     */
	public HomePagePanel(FullGameHolder fghIn, CardLayout panelCardsIn, AllGameInfo agiIn,
        Image starsImgIn, ResultsPagePanel rppIn)
	{
        setBackground(Color.BLACK);
		setLayout(null);

		fgh = fghIn;
		panelCards = panelCardsIn;
        agi = agiIn;
        rpp = rppIn;
		
		logoName = new String("gameLogo.png");
		logoPic = agi.getTheImage(logoName);
		
		starsImg = starsImgIn;

        picNames = new String[] {"settingsIcon.png", "instructionsIcon.png", 
			"rocketIconPic.png", "scoresIcon.png", "tacticIcon.png", "exitIcon.png"};
        allButtons = new JButton[picNames.length];

        iconPics = new ImageIcon[picNames.length];
        buttonNames = new String[]{"Settings", "Instructions", "Start Game", 
			"High Scores", "Tactics", "Exit"};
		
		makeHomeButtonsAndLabel();
	}	
	
    // Paint component draws on the background color and the image
    // at the specified location. It also draws the stars background
    // image and the logo image.
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(starsImg, 0, 0, 1000, 700, 0, 0, 1000, 700,  this);
	    g.drawImage(logoPic, 301, 0, 398, 278, this);
	}
	
    /* This method creates and adds the label that welcomes
     * the user to the game. It sets the specifics, and the
     * location with setBounds.
     * In a for loop incrementing the index of the arrays it initializes,
     * the icon pictures are initialized to the return of the method that
     * gets the icon images, and the buttons in the allButtons array are created
     * with values from the button names and icon pics arrays. The locations
     * (coordinates incremented) and colors are set. Handler class instances are
     * added to the buttons. The buttons are added to the panel.
     */
	public void makeHomeButtonsAndLabel()
	{
        JLabel gameWelcome = new JLabel("Welcome to Lexicon Launch!");
		gameWelcome.setForeground(Color.GRAY);
		gameWelcome.setFont(agi.getFont40());
		gameWelcome.setBounds(175, 230, 700, 40);
		add(gameWelcome);

        int xpos = 100;
        int ypos = 300;

        ButtonsClicked bc = new ButtonsClicked();

        // loop creates all buttons
        for(int i = 0; i < picNames.length; i++)
        {
            iconPics[i] = new ImageIcon(agi.getTheImage(picNames[i]));
            allButtons[i] = new JButton(buttonNames[i], iconPics[i]);
            allButtons[i].setBackground(Color.WHITE);
            allButtons[i].setFont(agi.getFont15());
            allButtons[i].setForeground(Color.RED);
            allButtons[i].setBounds(xpos, ypos, 200, 150);
            allButtons[i].addActionListener(bc);
            add(allButtons[i]);
            xpos +=300;

            if(i == 2)
            {
                ypos += 200;
                xpos = 100;
            }
        }
	}

    // nested handler class for the buttons 
    class ButtonsClicked implements ActionListener
    {
        /* action performed method gets the string of the 
         * button pressed, and based on which button is pressed,
         * it takes you to the respective panel in card layout (using show).
         * If exit is pressed, the game closes. If "Start game" is pressed,
         * the game is reset and new questions are grabbed based on the latest 
         * information before you play the game. Also, the Results page panel
         * is cleared of all its info.
         */
        public void actionPerformed(ActionEvent evt)
        {
            String command = evt.getActionCommand();
            if(command.equals(buttonNames[0]))
            {
                panelCards.show(fgh, "Settings");
            }
            else if(command.equals(buttonNames[1]))
            {
                panelCards.show(fgh, "Instructions"); 
            }
            else if(command.equals(buttonNames[2]))
            {
                agi.grabQuestionFromFile();
                agi.resetGame();
                rpp.resetFieldAndLabel();
                panelCards.show(fgh, "Game");
            }
            else if(command.equals(buttonNames[3]))
            {
                panelCards.show(fgh, "High Scores"); 
            }
            else if(command.equals(buttonNames[4]))
            {
                panelCards.show(fgh, "Tactics"); 
            }
            else if(command.equals(buttonNames[5]))
            {
                System.exit(1); 
            }
        }
    }
}

// class has the instructions to play the game
class InstructionsPanel extends JPanel
{
    private FullGameHolder fgh; // FV instance of card holder class to show other cards
	private CardLayout panelCards; // FV instance of card layout  to show other cards
    private JTextArea instructionArea; //FV instructions to change instructions at times
    private AllGameInfo agi; // FV game info class
    private Color blue; // blue used in many methods
    private Image[] screenshots; // stores all screenshot images for instructions
    private String[] screenshotNames; // stores names of all screenshots to read in
    private ImageIcon arrowRight; // icon for image to go right
    private ImageIcon arrowLeft; // icon for immage to go left
    private String right; // string name for right image
    private String left; // string name for left image
    private int imageNum; // based on what this number is, a certain text/image are printed
    private ImagePanel ip; // Used to repaint the image
    private JButton leftButton; // Button that can be clicked to go left
    private JButton rightButton; // Button can be clicked to go right
    private String instructionsName; // FV stores instructions text file name
    private Scanner readFile; // scanner to read file for instructions
    private String[] allInstructions; // stores all the instructions in parts
    private final int NUMLINES = 5; // how many lines each block of instructions is 
	
    /* Constructor initializes all FVs to parameters passed in. It initializes
     * the string arrays and strings to image names; it uses loops to read in
     * the screenshots per name, and also to read in instructions per "slide.
     * It sets border layout,
     * sets background color, and instantiates instance of the common header panel
     * to set in BL NORTH. Instructions Panel with a JTextArea is set to BL SOUTH.
     * Center panel with a border layout (right/left buttons added to EAST/WEST) is
     * added to BL CENTER. The ImagePanel is int he center of the center of the BL, 
     * button panels have flow layout, buttons have action listener class
     */
	public InstructionsPanel(FullGameHolder fghIn, CardLayout panelCardsIn,
        AllGameInfo agiIn, Color blueIn)
	{
		fgh = fghIn;
		panelCards = panelCardsIn;
        agi = agiIn;
        blue = blueIn;

        screenshots = new Image[8];
        screenshotNames = new String[]{"homeImage.png", "settingsImage.png",
            "gameImage.png", "dragImage.png", "wrongImage.png", "quizImage.png",
            "correctImage.png", "infoImage.png"};

        for(int i = 0; i < screenshots.length; i++)
        {
            screenshots[i] = agi.getTheImage(screenshotNames[i]);
        }
        right = new String("rightImg.png");
        left = new String("leftImg.png");
        imageNum = 0;
        arrowRight = new ImageIcon(agi.getTheImage(right));
        arrowLeft = new ImageIcon(agi.getTheImage(left));

        instructionsName = new String("instructions.txt");
        readFile = null;
        readFile = agi.readAFile(instructionsName);
        allInstructions = new String[8];
        for(int i = 0; i < allInstructions.length; i++)
        {
            String tempString = new String("");
            for(int j = 0; j < NUMLINES; j++)
            {
                tempString += readFile.nextLine() + " ";
            }
            allInstructions[i] = tempString;
        }

        setLayout(new BorderLayout());
        Color yellow = new Color(253,211,76);
        setBackground(yellow);

		RepeatingTopPanel rtp = new RepeatingTopPanel(fgh, panelCards, "  Instructions", agi);
		add(rtp, BorderLayout.NORTH);

        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new BorderLayout());
        instructionsPanel.setPreferredSize(new Dimension( 1000, 250));
        instructionArea = new JTextArea(allInstructions[imageNum]);
        Font montFont = new Font("Montserrat", Font.BOLD, 28);
        instructionArea.setFont(montFont);
        instructionArea.setBackground(Color.BLACK);
        instructionArea.setForeground(Color.GRAY);
        instructionArea.setLineWrap(true);
        instructionArea.setEditable(false);
        instructionArea.setWrapStyleWord(true);
        instructionArea.setMargin(new Insets(15, 15, 15, 15));
        instructionsPanel.add(instructionArea, BorderLayout.CENTER);
        add(instructionsPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        JPanel rightArrow = new JPanel();
        rightArrow.setBackground(Color.BLACK);
        rightArrow.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 200));
        JPanel leftArrow = new JPanel();
        leftArrow.setBackground(Color.BLACK);
        leftArrow.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 200));

        RightLeftHandler rlh = new RightLeftHandler();

        leftButton = new JButton(arrowLeft);
        leftButton.addActionListener(rlh);
        leftButton.setEnabled(false);
        leftArrow.add(leftButton);
        centerPanel.add(leftArrow, BorderLayout.WEST);

        rightButton = new JButton(arrowRight);
        rightButton.addActionListener(rlh);
        rightArrow.add(rightButton);
        centerPanel.add(rightArrow, BorderLayout.EAST);

        ip = new ImagePanel();
        centerPanel.add(ip, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
	}	

    // class that is the center panel of the center panel of the class
    class ImagePanel extends JPanel
    {
        // constructor sets bg color
        public ImagePanel()
        {
            setBackground(Color.BLACK);
        }

        // paint component draws appropriate screenshot for each instruction,
        // instruction area's text is also updated as per appropriate instruction.
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(screenshots[imageNum], 129, 0, 510, 341, this);
            instructionArea.setText(allInstructions[imageNum]);
        }
    }  

    // if right/left buttons pressed to gottle between
    // instructions
    class RightLeftHandler implements ActionListener
    {
        // based on whether the right or left image was 
        // clicked, imageNum increments or decrements
        // (and if there are no more to the left or 
        // the right, they are disabled) before it 
        // repaints to do so with the right image
        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource() == leftButton)
            {
                imageNum--;
            }
            else if(evt.getSource() == rightButton)
            {
                imageNum++;
            }

            if(imageNum > 0)
            {
                leftButton.setEnabled(true);
            }
            else
            {
                leftButton.setEnabled(false);
            }

            if(imageNum < 7)
            {
                rightButton.setEnabled(true);
            }
            else
            {
                rightButton.setEnabled(false);
            }

            ip.repaint();
        }
    }
}

/* This class has the code to make the common header with
 * the home button and panel name that multiple different panels have,
 * so it uses polymorphism and is used for many different panels to 
 * decrease code length.
 */
class RepeatingTopPanel extends JPanel
{
	private FullGameHolder fgh; // FV instance of card holder class to show other cards
	private CardLayout panelCards; // FV instance of card layout  to show other cards
	private String panelName; // FV string is used to set JLabel to correct panel name
    private JButton homeButton; // FV HomeButton so that text can be changed when needed
    private AllGameInfo agi; // FV instance of info class
	
    // Constructor initializes all FVs to parameters passed in, calls method to make
    // the common header/top panel. Sets preferred size of panel to be same.
	public RepeatingTopPanel(FullGameHolder fghIn, CardLayout panelCardsIn, 
		String panelNameIn, AllGameInfo agiIn)
	{
		fgh = fghIn;
		panelCards = panelCardsIn;
		panelName = panelNameIn;
        agi = agiIn;
        setPreferredSize(new Dimension(1000, 81));
		makeTopPanel();
	}
	
    /* This method creates a blue color, sets it as the background,
     * sets a border layout to the panel. A generic JPanel is created
     * that has a blue background and a flow layout to hold the Home 
     * button, and it is set to the WEST of the BL. An instance of an 
     * action listener class is added to the button. Another generic JPanel
     * is created with a blue background and border layout to hold the 
     * JLabel whose text is the name of the panel, set to the BL CENTER.
     */
	public void makeTopPanel()
    { 
        Color colorBlue = new Color(7,28,124);
        setBackground(colorBlue);
        setLayout(new BorderLayout());

        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(colorBlue);
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 75, 10));
        homeButton = new JButton("Home");
        homeButton.setForeground(Color.RED);
        homeButton.setFont(agi.getFont30());
        HomePressed hp = new HomePressed();
        homeButton.addActionListener(hp);
        homeButtonPanel.add(homeButton);
        add(homeButtonPanel, BorderLayout.WEST);

        JPanel panelNameLabelPanel = new JPanel();
        panelNameLabelPanel.setBackground(colorBlue);
        panelNameLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel panelNameLabel = new JLabel(panelName);
        panelNameLabel.setForeground(Color.RED);
        panelNameLabel.setFont(agi.getFont50());
        panelNameLabelPanel.add(panelNameLabel);
        add(panelNameLabelPanel, BorderLayout.CENTER);
    }

    // method resets text of homebutton for that instance of this class
    public void changeButtonText(String newText)
    {
        homeButton.setText(newText);
    }
	
    /* This handler class takes the user back to the home page
     * in the card layout if the home button is pressed, by 
     * checking what the string of the button pressed was. If
     * the text was home, user is taken home. If text was "back",
     * user is taken back to the corrections page
     */
	class HomePressed implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            String command = evt.getActionCommand();
            if(command.equals("Home"))
            {
                panelCards.show(fgh, "Home");
            }
            else if(command.equals("Back"))
            {
                changeButtonText("Home");
                panelCards.show(fgh, "Teaches");
            }
        }
    }
}

/* This class holds the user settings, including the difficulty,
 * meteorite speed, icon color, and number of rounds to play that 
 * the user chooses
 */
class SettingsPagePanel extends JPanel
{
	private FullGameHolder fgh; // FV instance of card holder class to show other cards
	private CardLayout panelCards; // FV instance of card layout  to show other cards
	private AllGameInfo agi; // FV instance of information class to change FVs in that class

	private Font mediumFont; // FV font ued throughout many methods
    private Color darkerBlue; // FV color used throughout many methods

    private JSlider speedSlider; // FV JSlider to get speed value in handler class
    private JSlider roundSlider; // FV JSlider to get # rounds value  in handler class

    private JMenuItem easy; // FV menu item to set to a certain color if selected in handler
    private JMenuItem medium; // FV menu item to set to a certain color if selected in handler
    private JMenuItem hard; // FV menu item to set to a certain color if selected in handler

    private JMenuItem classic; // FV menu item to set to a certain color if selected in handler
    private JMenuItem pink; // FV menu item to set to a certain color if selected in handler
    private JMenuItem blue; // FV menu item to set to a certain color if selected in handler

    private String classicName; // FV name of image for the classic red rocket icon
    private Image classicImg; // FV image of the classic red rocket icon
    private String pinkName; // FV name of image for the pink rocket icon
    private Image pinkImg; // FV image of the pink rocket icon
    private String blueName; // FV name of image for the blue rocket icon
    private Image blueImg; // FV image of the blue rocket icon

    private DrawIcon di; // FV for rocket icon to be repainted when option changed
	
    /* Constructor initializes fgh, panelcards, agi to passed in parameters.
     * Also, it initializes fonts and color. It also sets the layout as a 
     * border layout, sets background color, and adds a created instance of the 
     * repeated header panel to BL NORTH. A generic panel is added to BL CENTER.
     * This generic panel has a grid layout, and 4 methods are called to fill up
     * the border layout and one instance of another class (for image) is added.
     * Uses AGI method to iniitalize images from image names.
     */
	public SettingsPagePanel(FullGameHolder fghIn, CardLayout panelCardsIn, 
		AllGameInfo agiIn)
	{
		fgh = fghIn;
		panelCards = panelCardsIn;
		agi = agiIn;
		mediumFont = new Font("Montserrat", Font.BOLD, 20);
        darkerBlue = new Color(7,28,124);
		
		setLayout(new BorderLayout());
        Color yellow = new Color(253,211,76);
        setBackground(yellow);
        
        RepeatingTopPanel rtp = new RepeatingTopPanel(fgh, panelCards, "     Settings", agi);
		add(rtp, BorderLayout.NORTH);
		
		JPanel centerGrid = new JPanel();
		add(centerGrid, BorderLayout.CENTER);
		centerGrid.setLayout(new GridLayout(1,2));
		centerGrid.setBackground(Color.BLACK);
		
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 1));
        centerGrid.add(menuPanel);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(2, 1));
        centerGrid.add(sliderPanel);

        
        classicName = new String("classic.png");
        classicImg = agi.getTheImage(classicName);
        pinkName = new String("pinkIcon.png"); 
        pinkImg = agi.getTheImage(pinkName);
        blueName = new String("blueIcon.png");
        blueImg = agi.getTheImage(blueName);

        di = new DrawIcon();
        menuPanel.add(di);
        makeIconChoices(menuPanel);
        makeDifficulties(menuPanel);
        makeSpeeds(sliderPanel);
        makeRounds(sliderPanel);
	}	
	
    /* This method makes a generic JPanel that is added to the
     * grid layout. It has a flow layout. It holds the JMenu 
     * that allows the user to choose which difficulty for the
     * game they would like. Action listeners are added to all the
     * JMenu items. The menu is added to the grid layout.
     */
	public void makeDifficulties(JPanel theGrid)
	{
		JPanel difficultyPanel = new JPanel();	
		difficultyPanel.setBackground(Color.BLACK);	
		difficultyPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		JMenuBar difficultyBar = new JMenuBar();
		JMenu difficulties = new JMenu("Difficulties ▾");
		difficulties.setFont(agi.getFont30());
		easy = new JMenuItem("Easy");
		easy.setFont(agi.getFont30());
        medium = new JMenuItem("Medium");
		medium.setFont(agi.getFont30());
        medium.setOpaque(true);
        medium.setBackground(Color.GRAY);
		hard = new JMenuItem("Hard");
		hard.setFont(agi.getFont30());
		TwoMenuHandler bmh = new TwoMenuHandler();
		easy.addActionListener(bmh);
        medium.addActionListener(bmh);
		hard.addActionListener(bmh);	
		difficulties.add(easy);
        difficulties.add(medium);
		difficulties.add(hard);
		difficultyBar.add(difficulties);
		difficultyPanel.add(difficultyBar);
		theGrid.add(difficultyPanel);
	}

    /* This method makes a generic JPanel that is added to the
     * grid layout. It has a flow layout. It holds the JMenu 
     * that allows the user to choose which icon color/style
     * they would like for the game. Action listeners are added
     * to all the JMenu items. The menu is added to the grid layout. 
     */
    public void makeIconChoices(JPanel theGrid)
    {
        JPanel choicePanel = new JPanel();	
		choicePanel.setBackground(Color.BLACK);	
		choicePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
        JMenuBar choiceBar = new JMenuBar();
        JMenu choices = new JMenu("Icon Color ▾");
        choices.setFont(agi.getFont30());
        classic = new JMenuItem("Classic (Red)");
        classic.setFont(agi.getFont30());
        classic.setOpaque(true);
        classic.setBackground(Color.GRAY);
        pink = new JMenuItem("Pink");
        pink.setFont(agi.getFont30());
        blue = new JMenuItem("Blue");
        blue.setFont(agi.getFont30());
        TwoMenuHandler bmh = new TwoMenuHandler();
        classic.addActionListener(bmh);
        pink.addActionListener(bmh);	
        blue.addActionListener(bmh);
        choices.add(classic);
        choices.add(pink);
        choices.add(blue);
        choiceBar.add(choices);
        choicePanel.add(choiceBar);
		theGrid.add(choicePanel);
    }

    // This class draws on the icon
    class DrawIcon extends JPanel
	{
        // sets background color to match rest
        public DrawIcon()
        {
            setBackground(Color.BLACK);
        }
        // paintComponent draws the image chosen by the user
        // (stored in AGI setIcon method)
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(agi.getIcon(), 150, 0, 200, 200, this);
        }
	}

	/* This method makes a generic JPanel that is added to the
     * grid layout. It has a flow layout. It holds the JLabel that
     * labels that the slider is for the meteorite's speed, and it 
     * also holds the JSlider that allows the user to choose 
     * how fast they want the meteorites to travel in the game.
     * Action listener class instance is added to the slider. The 
     * slider and label are added to the grid layout in the panel.
     */
	public void makeSpeeds(JPanel theGrid)
	{
		JPanel speedPanel = new JPanel();	
		speedPanel.setBackground(Color.BLACK);	
		speedPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 66));
		
		JLabel speedLabel = new JLabel("Meteorite Speed (in px/sec)");
		speedLabel.setFont(agi.getFont30());
        speedLabel.setBackground(Color.GRAY);
        speedLabel.setForeground(Color.RED);
        speedPanel.add(speedLabel);

		speedSlider = new JSlider(JSlider.HORIZONTAL, 50, 250, 100);
        speedSlider.setMajorTickSpacing(25);	
		speedSlider.setPaintTicks(true);
        speedSlider.setLabelTable(speedSlider.createStandardLabels(50) ); 
		speedSlider.setFont(mediumFont);
        speedSlider.setBackground(Color.YELLOW);
        speedSlider.setOpaque(true);
        speedSlider.setForeground(darkerBlue);
        speedSlider.setPaintLabels(true);
        speedSlider.setPreferredSize(new Dimension(300, 60));
        TwoSliderListener slistener1 = new TwoSliderListener();
        speedSlider.addChangeListener(slistener1);
        speedPanel.add(speedSlider);
        theGrid.add(speedPanel);
	}

    /* This method makes a generic JPanel that is added to the
     * grid layout. It has a flow layout. It holds the JLabel that
     * labels that the slider is for the # of game rounds, and it 
     * also holds the JSlider that allows the user to choose 
     * how many rounds of the game they would like to paly.
     * Action listener class instance is added to the slider. The 
     * slider and label are added to the grid layout in the panel.
     */
    public void makeRounds(JPanel theGrid)
	{
		JPanel roundPanel = new JPanel();	
		roundPanel.setBackground(Color.BLACK);	
		roundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 66));
		
		JLabel roundLabel = new JLabel("Number of Rounds");
		roundLabel.setFont(agi.getFont30());
        roundLabel.setBackground(Color.GRAY);
        roundLabel.setForeground(Color.RED);
        roundPanel.add(roundLabel);
		
		roundSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 10);
        roundSlider.setMajorTickSpacing(1);	
		roundSlider.setPaintTicks(true);
        roundSlider.setPreferredSize(new Dimension(300, 60));
        roundSlider.setLabelTable( roundSlider.createStandardLabels(1) ); 
        roundSlider.setFont(mediumFont);
		roundSlider.setBackground(Color.YELLOW);
        roundSlider.setOpaque(true);
        roundSlider.setForeground(darkerBlue);
        roundSlider.setPaintLabels(true);
        TwoSliderListener slistener1 = new TwoSliderListener();
        roundSlider.addChangeListener(slistener1);
        roundPanel.add(roundSlider);
        theGrid.add(roundPanel);
	}
	
    // This nested handler class handles when any menu item is clicked
	class TwoMenuHandler implements ActionListener
	{
       
        /*Based on which menu item is chosen (using evt.getActionCommand and the FV
        * JMenuItems), that menu item is set a darker gray as the background
        * to show the user that that item is the currently chosen one. The
        * difficulty chosen is set to the corresponding boolean FV in the 
        * information class. The icon chosen is set to the corresponding 
        * int FV in the information class, and repaint of the image class is called. 
        * The corresponding name of the text file to be read in from is 
        * initilized to the file name FV in the information class
        */
		public void actionPerformed(ActionEvent evt)
		{
            String command = evt.getActionCommand();
            if(command.equals("Easy"))
            {
                easy.setOpaque(true);
                easy.setBackground(Color.GRAY);
                medium.setBackground(Color.WHITE);
                hard.setBackground(Color.WHITE);
                agi.setDifficulty(1);
                agi.setFileName("easyWords.txt");
            }
            else if(command.equals("Medium"))
            {
                medium.setOpaque(true);
                medium.setBackground(Color.GRAY);
                easy.setBackground(Color.WHITE);
                hard.setBackground(Color.WHITE);
                agi.setDifficulty(2);
                agi.setFileName("mediumWords.txt");
            }
            else if(command.equals("Hard"))
            {
                hard.setOpaque(true);
                hard.setBackground(Color.GRAY);
                easy.setBackground(Color.WHITE);
                medium.setBackground(Color.WHITE);
                agi.setDifficulty(3);
                agi.setFileName("hardWords.txt");
            }

            if(command.equals("Classic (Red)"))
            {
                classic.setOpaque(true);
                classic.setBackground(Color.GRAY);
                pink.setBackground(Color.WHITE);
                blue.setBackground(Color.WHITE);
                agi.setIcon(classicImg);
                di.repaint();
            }
            else if(command.equals("Pink"))
            {
                pink.setOpaque(true);
                pink.setBackground(Color.GRAY);
                classic.setBackground(Color.WHITE);
                blue.setBackground(Color.WHITE);
                agi.setIcon(pinkImg);
                di.repaint();
            }
            else if(command.equals("Blue"))
            {
                blue.setOpaque(true);
                blue.setBackground(Color.GRAY);
                classic.setBackground(Color.WHITE);
                pink.setBackground(Color.WHITE);
                agi.setIcon(blueImg);
                di.repaint();
            }
		}
	}

    /* This nested handler class handles when any slider
     * value is changed*/
    class TwoSliderListener implements ChangeListener
    {
       /* When the values are changed, 
        * (using getValue() and the FV slider instances),
        * the new values are reinitialized to the corresponding
        * int FVs for the speed amount and the round amount in the
        * info class. For speed amount, the int is converted to 
        * number of px/second first.
        */
        public void stateChanged(ChangeEvent evt)
        {
            int speedAmt = speedSlider.getValue();
            speedAmt = (int)((double)(1000)/speedAmt);
            agi.setSpeed(speedAmt);

            int rounds = roundSlider.getValue();
            agi.changeNumRounds(rounds);
        }
    }
}

// This class has all of the instructions to play the game, and is used
// polymorphically to also show the game tactics
class GenericTextAreaPanel extends JPanel
{
	private FullGameHolder fgh; // FV instance of card holder class to show other cards
	private CardLayout panelCards; // FV instance of card layout  to show other cards
	private String panelName; // FV instance of information class to change FVs in that class
	private String tAreaText; // FV String for what is put into the text area
    private JTextArea gameInstructions;
    private RepeatingTopPanel rtp; // instance of repeating top panel to change text of button
    private AllGameInfo agi; // instance of AGI information class
	
    /* Constructor initializes all FVs to parameters passed in, sets border layout,
     * sets background color, and instantiates instance of the common header panel
     * to set in BL NORTH. It calls the method to make the center panel
     */
	public GenericTextAreaPanel(FullGameHolder fghIn, CardLayout panelCardsIn, 
		String panelNameIn, String tAreaTextIn, AllGameInfo agiIn)
	{
		fgh = fghIn;
		panelCards = panelCardsIn;
		panelName = panelNameIn;
		tAreaText = tAreaTextIn;
        agi = agiIn;

        setLayout(new BorderLayout());
        Color yellow = new Color(253,211,76);
        setBackground(yellow);

		rtp = new RepeatingTopPanel(fgh, panelCards, panelName, agi);
		add(rtp, BorderLayout.NORTH);
		
		makeCenterPanel();
	}	

    /* This method instantiates a JTextArea that holds all the information/
     * tactics to play the game. Many specifics are set, including the font,
     * colors, etc. It is added to a scroll pane, and the scroll pane is added
     * to the panel's BL NORTH.
     */
    public void makeCenterPanel()
    {
        gameInstructions = new JTextArea(tAreaText);
		gameInstructions.setEditable(false);
        gameInstructions.setForeground(Color.GRAY);
        gameInstructions.setBackground(Color.BLACK);
        gameInstructions.setOpaque(true);
		gameInstructions.setLineWrap(true);  
		gameInstructions.setWrapStyleWord(true);  
		gameInstructions.setFont(agi.getFont30());
        gameInstructions.setMargin(new Insets(15,15,15,15));
		JScrollPane instructionScroller = new JScrollPane(gameInstructions);
		add(instructionScroller, BorderLayout.CENTER);
    }

    /* This method gets the high scores string passed in and resets that as
     * the JTextArea's text
     */
    public void resetHighScores(String highScores)
    {
		gameInstructions.setText(highScores);
    } 

    /* this method gets the button name string passed in and resets the
     * name of the button of any particualr instance of the cass.
     */
    public void resetButtonname(String buttonName)
    {
        rtp.changeButtonText(buttonName);
    }
}

// This panel contains the functioning game
class StartGamePanel extends JPanel
{
	private FullGameHolder fgh; // FV instance of card holder class to show other cards
	private CardLayout panelCards; // FV instance of card layout  to show other cards
	private AllGameInfo agi; // FV instance of information class to change FVs in that class
    private TeachingPagePanel tpp; // FV TeachingPagePanel instance to change text area text
    private GamePanel gp; // Used to repaint when timer paused or restarted
    private RoundsPanel rsp; // Used to repaint circles based on whether user was wrong/right

    private Timer timer; // FV timer for the game's animations
    private JButton pauseButton; // FV JButton for the user to pause the game

    private Timer timer1; // FV timer to control when to print the explosion

    private boolean exploding; // FV boolen to check if it should currently be exploding

    private boolean movable; // FV boolean to check if the rocket should be movable
    private boolean paused; // FV boolean to check if the game is currently paused

    private Timer backgroundTimer; // FV Timer used to print the stars BG moving

    private Image longStars; // image stores image to draw from
    private Color blue; // blue used everywhere, passed in from param

    private JLabel questionLabel; // FV label for QuestionPanel class with the question
	
    /* Constructor initializes FVS to the parameters passed in, and other FVs. 
     * Layout set to a Border Layout. The panel that holds the actual game (GamePanel) 
     * is added to the BL CENTER. An instance of QuestionPanel, the header, is added
     * to BL NORTH, instance of RoundsPanel added to SOUTH.
     */
	public StartGamePanel(FullGameHolder fghIn, CardLayout panelCardsIn, 
		AllGameInfo agiIn, TeachingPagePanel tppIn, Image longStarsIn, Color blueIn)
	{
		fgh = fghIn;
		panelCards = panelCardsIn;
		agi = agiIn;
        tpp = tppIn;
        longStars = longStarsIn;
        blue = blueIn;

        paused = false;

        exploding = false;

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        QuestionPanel qp = new QuestionPanel();
        add(qp, BorderLayout.NORTH);

        gp = new GamePanel();
        add(gp, BorderLayout.CENTER);

        rsp = new RoundsPanel();
		add(rsp, BorderLayout.SOUTH);
	}	

    /* Class has the accuracy (wrong/right) per round
     * printed as circles
     */
    class RoundsPanel extends JPanel 
    {
        /* Constructor sets preferred size of panel, Border
         * layout, sets background color. It creates a generic panel to hold
         * the start/pause/restart JButton, and a generic panel to hold the
         * JLabel with the question on it, added to BL WEST and CENTER of this
         * panel respectively */
        public RoundsPanel()
        {
            setPreferredSize(new Dimension(1000, 81));
            setLayout(new BorderLayout());
            setBackground(blue);
            JPanel gP = new JPanel();
            gP.setBackground(blue);
            gP.setPreferredSize(new Dimension(300, 81));
            add(gP, BorderLayout.WEST);
            JLabel glabel = new JLabel("Accuracy:");
            glabel.setFont(agi.getFont40());
            glabel.setBackground(blue);
            glabel.setForeground(Color.RED);
            gP.add(glabel);
        }

        /* Paint component has a default X starting position
         * to the right of the end of the JLabel, and for each round
         * chosen, the enmpty circle is drawn. For each round already 
         * played, the method gets whether or not the suer was correct
         * for that round and chooses to fill the circle red isntead of
         * yello
         */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			int xCoordinate = 310;
			g.setColor(Color.YELLOW);
			for(int i = 0; i < agi.getNumRounds(); i++)
			{
				g.drawOval(xCoordinate, 15, 50, 50);
				xCoordinate += 60;
			}
			xCoordinate = 310;
			for(int i = 0; i < agi.getRoundNum(); i++)
			{
				if(agi.getIsCorrect(i) == false)
				{
					g.setColor(Color.RED);
				}
				else
				{
					g.setColor(Color.GREEN);
				}
				g.fillOval(xCoordinate, 15, 50, 50);
				xCoordinate += 60;
			}
		}
    }

    // class that is the header for this start game panel. Contains the current 
    // rocket word, except in larger font than it is on the rocket. 
    class QuestionPanel extends JPanel 
    {
        /* No FVs. Constructor sets preferred size of panel, Border
         * layout, sets background color. It creates a generic panel to hold
         * the start/pause/restart JButton, and a generic panel to hold the
         * JLabel with the question on it, added to BL WEST and CENTER of this
         * panel respectively */
        public QuestionPanel()
        {
            setPreferredSize(new Dimension(1000, 81));
            setLayout(new BorderLayout());
            Color blue = new Color(7,28,124);
            setBackground(blue);
            
            JPanel bPanel = new JPanel();
            bPanel.setBackground(blue);
			bPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 75, 10));
			bPanel.setPreferredSize(new Dimension(300, 81));
            pauseButton = new JButton("Start");
            pauseButton.setForeground(Color.RED);
            pauseButton.setFont(agi.getFont30());
            pauseButton.setPreferredSize(new Dimension(180, 50));
            StartedGame sg = new StartedGame();
            pauseButton.addActionListener(sg);
            bPanel.add(pauseButton);
            add(bPanel, BorderLayout.WEST);
            
            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            questionPanel.setBackground(blue);
            questionLabel = new JLabel("");
            questionLabel.setFont(agi.getFont50());
            questionLabel.setForeground(Color.RED);
            questionPanel.add(questionLabel);
            add(questionPanel, BorderLayout.CENTER);
        }
        
        // handler class for the start/pause/restart button
        class StartedGame implements ActionListener 
        {   
            /* If any of the different texts on the button is 
             * pressed, somehting will happen (String of button
             * checked with evt.getActionCommand()). If start was
             * pressed, the panel's timer is started, the rocket is 
             * set to movable, and the text is set to pause. If the 
             * pause button is pressed, the timer is stopped, and if
             * exploding is currently true, the explosion timer is 
             * stopped. The rocket is no longer movable, and the panel is
             * repainted to display changes of pausing. Button text set to
             * restart. If restart is pressued, it is repainted again, the
             * rocket now movable, and either the explosion or regular timer
             * restarting
             */
            public void actionPerformed(ActionEvent evt)
            {
                String command = evt.getActionCommand();
                if(command.equals("Start"))
                {
                    timer.start();
                    backgroundTimer.start();
                    movable = true;
                    pauseButton.setText("Pause");
                }
                else if(command.equals("Pause"))
                {
                    timer.stop();
                    backgroundTimer.stop();
                    if(exploding == true)
                    {
                        timer1.stop();
                    }
                    paused = true;
                    movable = false;
                    pauseButton.setText("Restart");
                    gp.repaint();
                }
                else if(command.equals("Restart"))
                {
                    paused = false;
                    gp.repaint();
                    movable = true;
                    if(exploding == true)
                    {
                        timer1.start();
                    }
                    else
                    {
                        timer.start();
                        backgroundTimer.start();
                    }
                    pauseButton.setText("Pause");
                }
            }
        }
    }

    // The game panel actually contains the game functioning itself,
    // and it implements mouse and mouse motion listeners
    class GamePanel extends JPanel implements MouseMotionListener, MouseListener,
		KeyListener
    {
        private GifTimer gt; //FV instance of GifTimer class used as action listener for
                             // explosion timer
        private int bgXCoord; // FV X coordinate to start printing the backgorund image

        private final int LOOPFOUR = 4; // FV constant for how many times to loop through array

        private String asteroidName; // FV name of the image of the asteroid for the game
        private Image asteroidImg; // FV image of the asteroid
        private String explosionName; // FV name of the explosion image
        private Image explosionImage; // FV image of the explosion, holds all "frames" 

        /* constructor initalizes all FVs. The game's timer is created with
         * the delay set by the user in the settings panel, the bg color 
         * is set, and mouse, mouse motion, key listeners are added to the class.
         * Images initialized to call of getTheImage method in AGI class.
         * GifTimer instance is initialized and used as action listener for 
         * initialized explosion timer. BackgroundTimer instance is intialzied
         * and used as an action Listener class for the intialized explosion
         */
        public GamePanel()
        {
            // the timer is created with an instance of MoverTimer as the listener class
            MoverTimer moverTimer = new MoverTimer();
            timer = new Timer(agi.getMeteoriteSpeed(), moverTimer); 

            setBackground(Color.BLACK);

            addMouseMotionListener(this);
            addMouseListener(this);
            addKeyListener(this);

            asteroidName = new String("asteroid.png");
            asteroidImg = agi.getTheImage(asteroidName);
            
            explosionName = new String("explosion.png");
            explosionImage = agi.getTheImage(explosionName);

            bgXCoord = 0;

            gt = new GifTimer();
            timer1 = new Timer(20, gt);

            BackgroundTimer bgt = new BackgroundTimer();
            backgroundTimer = new Timer(7, bgt);
        }

        // class for printing background stars moving
        class BackgroundTimer implements ActionListener
        {
            // If the timer even occurs, the xCoordinage
            // incrmenets by 1 so that the image is moving
            // right. If the x coord is the end of the image,
            // it is set back to the beginning
            public void actionPerformed(ActionEvent evt)
            {
                bgXCoord += 1;

                if(bgXCoord == 3000)
                {
                    bgXCoord = 0;
                }
            }
        }

        // This class is instantiated to be the action listener class for the game's timer
        class MoverTimer implements ActionListener
        {
            /* ActionListener class decided when to repaint and what to do based on certain actions
             * every time an action of the timer occurs, the x default coordinates of the asteroids,
             * the xDefault FV in the info class, is decremented by 1. Then, there are if statements 
             * to decide what happened on that action of the Timer. If the last asteroid passes off the screen,
             * (which one is last determined by the max of them all and seeing which has the farthest right xCoord)
             * the locations of the rocket and asteroid are reset, and it is repainted by calling repaint().
             * Or, if the coordinates of the front of the rocket are within the coordinates/boundaries of 
             * any of the four asteroids, the int value of the selected asteroid is set to its corresponding
             * field variable in the info class, the checkIfCorrect method is called, and setExplode is set to
             * true. Later on, this will print out the explosion. Then, repaint is called to repaint it once before
             * the positions are reset (so that it can pause). Then, positions are reset and it repainted again.
             * If the round number is equal to the total number of rounds, the timer stops, the text area in the 
             * teaching panel is updated, and the panel is changed to the teaching panel.
             */
            public void actionPerformed(ActionEvent evt)
			{
				agi.setX(agi.getX()-1);

                int max = 0;

                for(int i = 0; i < LOOPFOUR; i++)
                {
                    if(agi.getAsteroidLocationAdded(i) > agi.getAsteroidLocationAdded(max))
                        max = i;
                }

				if((agi.getX() + 200 + agi.getAsteroidLocationAdded(max) == 0))
				{
                    rsp.repaint();
                    agi.resetAll();
				}
				else if(agi.getRocketY() + 100 >= 10 && agi.getRocketY() + 100 <= 125
					&& agi.getRocketX() + 200 >= agi.getX() + agi.getAsteroidLocationAdded(0)
					&& agi.getRocketX() + 200 <= agi.getX() + agi.getAsteroidLocationAdded(0) + 200)
				{
				   agi.setChosenOne(0);
				   checkIfCorrect();
				   agi.setExplode(true);
				}
				else if(agi.getRocketY() + 100 >= 135 && agi.getRocketY() + 100 <= 250
					&& agi.getRocketX() + 200 >= agi.getX() + agi.getAsteroidLocationAdded(1)
					&& agi.getRocketX() + 200 <= agi.getX() + agi.getAsteroidLocationAdded(1) + 200)
				{
				   agi.setChosenOne(1);
				   checkIfCorrect();
				   agi.setExplode(true);
				}
				else if(agi.getRocketY() + 100 >= 260 && agi.getRocketY() + 100 <= 375
					&& agi.getRocketX() + 200 >= agi.getX() + agi.getAsteroidLocationAdded(2)
					&& agi.getRocketX() + 200 <= agi.getX() + agi.getAsteroidLocationAdded(2) + 200)
				{
				   agi.setChosenOne(2);
				   checkIfCorrect();
				   agi.setExplode(true);
				}
				else if(agi.getRocketY() + 100 >= 385 && agi.getRocketY() + 100 <= 500
					&& agi.getRocketX() + 200 >= agi.getX() + agi.getAsteroidLocationAdded(3)
					&& agi.getRocketX() + 200 <= agi.getX() + agi.getAsteroidLocationAdded(3) + 200)
				{
				   agi.setChosenOne(3);
				   checkIfCorrect();
				   agi.setExplode(true);
				}
				rsp.repaint();
                repaint();
				requestFocusInWindow(); 
            }
            
            /* This method checks if the asteroid chosen by the user is the same 
             * as the correct asteroid that should have been selected for that round,
             * based on the chosenOne FV and the array of correct answers (and the index
             * for it of the round number), all in the info class. If it is correct, the
             * correctCount FV in the info class is incremented, and the isCorrect array that 
             * stores if the user got the round right is initialized to true for that round. 
             * If the user gets it wrong, the index of the correctCount array for that round
             * is intialized to false
             */
            public void checkIfCorrect()
            {
                if(agi.getChosenOne() == agi.getCorrectAnswer(agi.getRoundNum()))
                {
                    agi.setCorrectCount(agi.getCorrectCount()+1);
                    agi.setIsCorrect(agi.getRoundNum(),true);
                }
                else
                {
                    agi.setIsCorrect(agi.getRoundNum(),false);
                }
			}
        }

        // This class is instantiated to be the action listener class for explosion timer
        class GifTimer implements ActionListener
        {
            private int count; // FV decides after how many iterations/times to stop explosion
            private int xC; // FV x-coordinate of part of explosion image collection to be printed
            private int yC; // FV y-coordinate of part of explosion image collection to be printed
            private final int INCREMENT = 240; // final constant used to increment

            // FVs initialized to initial values
            public GifTimer()
            {
                xC = 0;
                yC = 0;
                count = 1;
            }

            /* When timer event occurs, the count is increased. The explosion section's 
             * x coordinate also increments, and after every row of explosion frames are 
             * done with, the y coordinate is incremented and the x coordinate is set back
             * to 0. This is repainted every timer event. If the count is 34, the explosion
             * timer stops (stops printing explosion from image), exploding is set to false,
             * the rocket is movable again, and agi.resetAll is called to reset the positions
             * of the rocket and asteroids. The correct/wrong colored circle is also filled
             * for that game by repainting rsp. The game timer is restarted and panel is repainted 
             */
            public void actionPerformed(ActionEvent evt)
            {
                count ++;
                xC += INCREMENT;

                if(xC == 8*INCREMENT)
                {
                    xC = 0;
                    yC+=INCREMENT;
                }

                if(yC == 6*INCREMENT)
                {
                    xC = 0;
                    yC = 0;
                }

                rsp.repaint();
                repaint();

                if(count == 34)
                {
                    timer1.stop();
                    exploding = false;
                    movable = true;
                    agi.resetAll();
                    timer.start();
                    backgroundTimer.start();
                    rsp.repaint();
                    repaint();
                }
            }

            // returns FV xC, xcoord of part of explosions pic to take
            public int getxC()
            {
                return xC;
            }
            
            // returns FV yC, ycoord of part of explosions pic to take
            public int getyC()
            {
                return yC;
            }

            /* returns FV count, the iteration number of how many times
             * images were printed with the imter
             */
            public int getCount()
            {
                return count;
            }
        }

        /* PaintComponent paints everything onto the panel of this class.
         * If the rocket x and y are not within the range of the sizes of the
         * panel, they are set to the minimum (or maximum) values the rocket 
         * can reach before going offscreen ad not being able to get back rocket.
         * If the current roundNum in the agi class is equal to the num rounds,
         * the user is moved onto the teaching panel and out of game panel, the
         * timer stopping and the teaching panel JTA being updated. Movable is 
         * set to false for the next round, and the pause button's text is reset
         * to start (to start the next round). The program (with Thread.sleep) is
         * also paused to sleep for a little time before you get moved onto the next panel.
         * If screen is paused, a no cheating message is printed to the screen! Otherwise,
         * the most recently set delay of the timer is set for the panel, 
         * and randomized stars are painted onto the panel. The asteroid
         * images are also drawn. The corresponding words on the asteroids
         * are drawn. The rocket image as well as the word on it are also drawn.
         * If the explode FV in the information class is true, the explosion timer
         * is reinstantiated, and started, the game timer being stopped. explode in 
         * agi is set to false, and every time exploding is true, the explosion image
         * is printed onto the asteroid location. The rounds panel repaint is called
         * to repaint hte filled/open circles
         */
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            
            if(agi.getRocketX() <= 0)
            {
                agi.setRocketX(0);
            }
            else if(agi.getRocketX() >= 800)
            {
                agi.setRocketX(800); 
            }
            else if(agi.getRocketY() <= -47)
            {
                agi.setRocketY(-47);
            }
            else if(agi.getRocketY() >= 363)
            {
                agi.setRocketY(363);
            }

            if(agi.getRoundNum() == agi.getNumRounds())
			{
                try
                {
                    Thread.sleep(800);
                }
                catch(InterruptedException e)
                {
                    System.out.println(e);
                    System.exit(1);
                }    
				timer.stop();
                backgroundTimer.stop();
				tpp.updateTextArea();
                pauseButton.setText("Start");
                movable = false;
				panelCards.show(fgh, "Teaches");
			}
            else if(paused == true)
            {
                questionLabel.setText("Word: " + "Hidden!");
                g.setColor(Color.GRAY);
                g.setFont(agi.getFont40());
                g.drawString("We know you just paused to cheat!", 100, 200);
                g.drawString("Ha! Don't try that with us!",200, 300);
            }
            else
            {
                questionLabel.setText("Word: " + agi.getQOf(agi.getRoundNum()));
                timer.setDelay(agi.getMeteoriteSpeed());

                if(bgXCoord >= 2000)
                {
                    g.drawImage(longStars, 0, 0, 3000-bgXCoord, 700, 
                        bgXCoord, 0, 3000, 700, this);

                    g.drawImage(longStars, 3000-bgXCoord, 0, 1000, 700, 
                        0, 0, 1000-(3000-bgXCoord), 700, this);
                }
                else
                {
                    g.drawImage(longStars, 0, 0, 1000, 700, bgXCoord, 0, bgXCoord + 1000, 700, this);
                }

                g.setColor(Color.BLACK);
                g.setFont(agi.getFont15());
                int astrSize = 115;
                int astrAdd = 125;

                for(int i = 0; i < LOOPFOUR; i++)
                {
                    g.drawImage(asteroidImg, agi.getX() + agi.getAsteroidLocationAdded(i),
                        agi.getY() + astrAdd*i, astrSize, astrSize, this);
                    g.drawString(agi.getStringOf(agi.getRoundNum(), i), 
                    agi.getX() + 10 + agi.getAsteroidLocationAdded(i),
                    agi.getY() + 57 + i * astrAdd);
                }

                g.drawImage(agi.getIcon(), agi.getRocketX(), agi.getRocketY(),
					200, 200, this);
                g.setColor(Color.WHITE);
                g.fillRect(agi.getRocketX() + 72, agi.getRocketY() + 85, 105, 20);
                g.setColor(Color.BLACK);
                g.drawString(agi.getQOf(agi.getRoundNum()), agi.getRocketX() 
					+ 72, agi.getRocketY() + 100);
                
                if(agi.getExplode() == true)
                {
                    timer.stop();
                    backgroundTimer.stop();
                    movable = false;
                    gt = new GifTimer();
                    timer1 = new Timer(20, gt);
                    timer1.start();
                    exploding = true;
                    agi.setExplode(false);
                }

                if(exploding == true)
                {
                    g.drawImage(explosionImage, agi.getX() + agi.getAsteroidLocationAdded(agi.getChosenOne()) - 63,
                        agi.getY() + 125 * agi.getChosenOne() -63,
                        agi.getX() + agi.getAsteroidLocationAdded(agi.getChosenOne()) + 177,
                        agi.getY() + 125 * agi.getChosenOne() +177,
                        gt.getxC(), gt.getyC(), gt.getxC() + 240, gt.getyC() + 240, this);
                }
                rsp.repaint();
                requestFocusInWindow();
            }
        }
        
        /* When the mouse is pressed within the coordinates/boundaries of the rocket image
         * that is drawn onto the panel, the boolean dragging in the info class is set to true,
         * and the panel is repainted as it is being "dragged"
         */
        public void mousePressed ( MouseEvent evt )
        {
			requestFocusInWindow();
            if(movable == true)
            {
                agi.setXMouse(evt.getX());
                agi.setYMouse(evt.getY());
                
                if(agi.getRocketX() < agi.getXMouse() && agi.getXMouse() < (agi.getRocketX() + 200) 
                    && agi.getRocketY() < agi.getYMouse() && agi.getYMouse() < (agi.getRocketY() + 200) )
                    agi.setDragging(true);
                repaint();
            }
        }
        
        /* This method, if the user breaks their dragging by releasing the mouse, 
         * sets the dragging boolean in the info class to false to signify that the
         * mouse is no longer being dragged
         */
        public void mouseReleased ( MouseEvent evt ) 
        {
            if(movable == true)
            {
                agi.setDragging(false);
            }
        }
        
        /* If the dragging boolean in the info class is true (the cursor is 
         * currently being dragged), then the difference between the coordinates
         * of the location on the screen where the mouse was pressed and the 
         * coordinates of the current cursor being dragged is added to the x
         * and y coordinates of the rocket, and the panel is repainted, chaning
         * the location of the rocket
         */
        public void mouseDragged(MouseEvent evt) 
        {
            if(movable == true)
            {
                if(agi.getDragging() == true)
                {
                    agi.setRocketX(agi.getRocketX() + (evt.getX() - agi.getXMouse()));
                    agi.setRocketY(agi.getRocketY() + (evt.getY() - agi.getYMouse()));
                    agi.setXMouse(evt.getX());
                    agi.setYMouse(evt.getY());

                    repaint();
                }
            }
        }
        
        /* The user can also use the up, down, right, and left arrow keys
         * to move around the rocket. Based on which one they press, the 
         * x and y coordinates of the rocket will increment or decrement and 
         * then it gets repainted.
         */
        public void keyPressed(KeyEvent evt)
        {
			requestFocusInWindow();
			if(movable == true)
			{
				int code = evt.getKeyCode();
				if(code == KeyEvent.VK_UP)
				{
					agi.setRocketY(agi.getRocketY() - 20);
				}
				else if(code == KeyEvent.VK_DOWN)
				{
					agi.setRocketY(agi.getRocketY() + 20);
				}
				else if(code == KeyEvent.VK_RIGHT)
				{
					agi.setRocketX(agi.getRocketX() + 20);
				}
				else if(code == KeyEvent.VK_LEFT)
				{
					agi.setRocketX(agi.getRocketX() - 20);
				}

				repaint();
			}
		}
        
        /* The user can also use the W, A, S, D keys as well,
         * to move around the rocket. Based on which one they press, the 
         * x and y coordinates of the rocket will increment or decrement and 
         * then it gets repainted.
         */
        public void keyTyped(KeyEvent evt)
        {
			requestFocusInWindow();
			if(movable == true)
			{
				char letter = evt.getKeyChar();
				if(letter == 'w')
				{
					agi.setRocketY(agi.getRocketY() - 20);
				}
				else if(letter == 's')
				{
					agi.setRocketY(agi.getRocketY() + 20);
				}
				else if(letter == 'd')
				{
					agi.setRocketX(agi.getRocketX() + 20);
				}
				else if(letter == 'a')
				{
					agi.setRocketX(agi.getRocketX() - 20);
				}

				repaint();
			}
		}
        
        // unused methods for key, mouse, and mouse motion listeners
        public void keyReleased(KeyEvent evt) {}
        public void mouseClicked ( MouseEvent evt ) {}
        public void mouseEntered ( MouseEvent evt ) {}
        public void mouseExited ( MouseEvent evt ) {}
        public void mouseMoved(MouseEvent evt) {}
    }
}

// This panel teaches the user their mistakes from the game. 
class TeachingPagePanel extends JPanel
{
	private FullGameHolder fgh; // FV instance of card holder class to show other cards
	private CardLayout panelCards; // FV instance of card layout  to show other cards
	private AllGameInfo agi; // FV instance of information class to change FVs in that class
    private JTextArea jtaNew; // FV instance of motivation JTA so it can be changed in other panels
    private JTextArea jtaNew2; // FV instance of word JTA so it can be changed in other panels
    private JTextArea jtaNew3; // FV instance of synonym JTA so it can be changed in other panels
    private Color blue; // FV instance of blue color used throughout many different panels
    private QuizScreenPanel qsp2; // FV instance of quiz screen panel to call method to create
                                  // quiz before moving onto the quiz panel
    private GenericTextAreaPanel tp; // FV instance of text area to keep changing text after every round
	
    /* Constructor initializes all FVS to the parameters passed in to be used throughout
     * the class. Border Layout is set. Generic JPanel created to hold mistakes JLabel 
     * header, added to BL NORTH. Another generic JPanel has a border layout to hold the different
     * JTextAreas for the motivation, answer, and question. Three JTA's are instantiated, and 
     * the generic panel's north has another grid layout in order to hold the question and answer
     * JTA's side by side, the motivation one in the center. The string for the question and answer
     * are added to based on whether or not the user got the answer in the game correct or wrong at a 
     * certain index in the array that stores this information, boolean array isCorrect in the AGI class.
     * If the user got the answer wrong, it is added to the string. If they got it right, it is not added.
     * Then the appropriate JTextArea's text is set to the appropriate string. 
     */
	public TeachingPagePanel(FullGameHolder fghIn, CardLayout panelCardsIn, 
		AllGameInfo agiIn, QuizScreenPanel qspIn, Color blueIn, GenericTextAreaPanel tpIn)
	{
		fgh = fghIn;
		panelCards = panelCardsIn;
		agi = agiIn;
        qsp2 = qspIn;
        blue = blueIn;
        tp = tpIn;
        setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        JLabel title = new JLabel("Your Mistakes");
		title.setFont(agi.getFont50());
		titlePanel.setBackground(blue);
		title.setForeground(Color.RED);
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        JPanel jtaPanel = new JPanel();
        jtaPanel.setLayout(new BorderLayout());
        jtaPanel.setBackground(Color.BLACK);
        add(jtaPanel, BorderLayout.CENTER);

        jtaNew = new JTextArea("");
        jtaNew.setFont(agi.getFont30());
        jtaNew.setEditable(false);
        jtaNew.setLineWrap(true);
        jtaNew.setWrapStyleWord(true);
        jtaNew.setBackground(Color.BLACK);
        jtaNew.setForeground(Color.GRAY);
        jtaNew.setMargin(new Insets(15,15,15,15));
        jtaPanel.add(jtaNew, BorderLayout.CENTER);

        JPanel jtaPanelSmall = new JPanel();
        jtaPanelSmall.setLayout(new GridLayout(1, 2));
        jtaPanel.add(jtaPanelSmall, BorderLayout.NORTH);

        jtaNew2 = new JTextArea("");
        jtaNew2.setFont(agi.getFont30());
        jtaNew2.setEditable(false);
        jtaNew2.setLineWrap(true);
        jtaNew2.setWrapStyleWord(true);
        jtaNew2.setBackground(Color.BLACK);
        jtaNew2.setForeground(Color.GRAY);
        jtaNew2.setMargin(new Insets(15,15,15,15));
        jtaPanelSmall.add(jtaNew2);

        jtaNew3 = new JTextArea("");
        jtaNew3.setFont(agi.getFont30());
        jtaNew3.setEditable(false);
        jtaNew3.setLineWrap(true);
        jtaNew3.setWrapStyleWord(true);
        jtaNew3.setBackground(Color.BLACK);
        jtaNew3.setForeground(Color.GRAY);
        jtaNew3.setMargin(new Insets(15,15,15,15));
        jtaPanelSmall.add(jtaNew3);

        String mistakes = new String("Words:\n");
        String corrections = new String("Synonyms:\n");

        for(int i = 0; i < agi.getNumRounds(); i++)
        {
            if(agi.getIsCorrect(i) == false)
            {
                mistakes += agi.getQOf(i) + "\n"; 
                corrections+= agi.getStringOf(i, agi.getCorrectAnswer(i)) + "\n";
            }
        }
        jtaNew2.setText(mistakes);
        jtaNew3.setText(corrections);
		makeBottomPanel();
	}	

    /* Generic panel for buttons created, layout set to grid layout. 
     * Generic panels made each for the tactics and quiz button. 
     * Instance of ButtonChanger action listener/handler added to both 
     * button instances. Larger generic panel added to BL SOUTH, rest
     * of panels added to larger generic panel in grid layout
     */
	public void makeBottomPanel()
	{
		ButtonChanger bc = new ButtonChanger();
        JPanel otherPlaces = new JPanel();
        otherPlaces.setBackground(blue);
        otherPlaces.setLayout(new GridLayout(1, 2));
        
        JPanel part1 = new JPanel();
        part1.setBackground(blue);
        JButton tacticsButton = new JButton("See Tactics");
        tacticsButton.setFont(agi.getFont30());
        tacticsButton.addActionListener(bc);
        tacticsButton.setForeground(Color.RED);
        part1.add(tacticsButton);
        otherPlaces.add(part1);
        
        JPanel part2 = new JPanel();
        part2.setBackground(blue);
        JButton quizButton = new JButton("Take Quiz");
        quizButton.setFont(agi.getFont30());
        quizButton.addActionListener(bc);
        quizButton.setForeground(Color.RED);
        part2.add(quizButton);
        otherPlaces.add(part2);
        
        add(otherPlaces, BorderLayout.SOUTH);
	}
	
    // button handler class for tactics and quiz buttons
	class ButtonChanger implements ActionListener
	{
        /* If either button pressed, text of button is taken
         * and stored with evt.getActionCommand(). If it says 
         * tactics, user taken to tactics panel. If it says 
         * quiz, round num, correct num both set to 0 in AGI class, 
         * quiz made in quiz panel, quiz panel shown.
         */
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("See Tactics"))
			{
                tp.resetButtonname("Back");
				panelCards.show(fgh, "Tactics");
			}
			else if(command.equals("Take Quiz"))
			{
				agi.setRoundNum(0);
				agi.setCorrectCount(0);
                qsp2.resetQuestion();
				panelCards.show(fgh, "Quiz");
			}
		}
	}

    /* This method updates the JTextArea after the game is complete and the isCorrect array actually
     * holds the real values of which questions the user got right and wrong. Again, if the user got 
     * the word right, it is not added to the list of wrong words, but if the user got the
     * word wrong, the word gotten wrong is added to the string list, and the correct synonym
     * is also added to another string. If the user gets nothing wrong, a congratulations is added to 
     * a motivation string (if they mate mistakes, then a word of encouragement is added).
     * The text of the appropriate jtext area is set to the appropriate string.
     */
    public void updateTextArea() 
    {
        String mistakes = new String("Words:\n");
        String corrections = new String("Synonyms:\n");
        String motivation = new String("");

        for(int i = 0; i < agi.getNumRounds(); i++)
        {
            if(agi.getIsCorrect(i) == false)
            {
                mistakes += agi.getQOf(i) + "\n"; 
                corrections+= agi.getStringOf(i, agi.getCorrectAnswer(i)) + "\n";
            }
        }

        if(mistakes.equals("Words:\n") && corrections.equals("Synonyms:\n"))
        {
            mistakes += "none";
            corrections += "none";
            motivation += "Congrats!";
        }
        else
        {
            motivation += "\nIt's ok! You don't have to be perfect!\nMistakes leave room for improvement!";
        }

        motivation += "\nTime to take the quiz to test your knowledge!!! Let's go!";

        jtaNew2.setText(mistakes);
        jtaNew3.setText(corrections);
        jtaNew.setText(motivation);
    }
}

// This panel gives the user the quiz after the game. 
class QuizScreenPanel extends JPanel
{
	private FullGameHolder fgh; // FV instance of card holder class to show other cards
	private CardLayout panelCards; // FV instance of card layout  to show other cards
	private AllGameInfo agi; // FV instance of information class to change FVs in that class
	
	private ButtonGroup group; // FV button group used to get selecion and add JRBs to group
    private JTextArea questionArea; // JTextArea FV to be changed every round to what question is
    private JRadioButton [] answer; // FV answer[] JRB array to use loops for repetetive tasks
    private JButton submit, nextQuestion, nextPanel, hint; // FV JButtons enabled/not in other methods 
    private Color blue; // Blue color used in a lot of places

    private PopupFactory pf; // popup factory used to create hint popup
    private Popup hintPop; // instance of popup, text changed every round
    private JTextArea jtaOne; // JText area for hint 

    private final int LOOPFOUR = 4; // FV for constant to loop 4 times
	
    /* Constructor initializes all FVS to the parameters passed in to be used throughout
     * the class. Other FVs initialized to default components. Border Layout is set. 
     * Popup factory instance created. Jtextarea jtaOne created, specifics set, insets set, 
     * FV instanc of popup initialized with this jTextArea. Bacground set, layout set to 
     * border layout. method to create quiz is called.
     */
	public QuizScreenPanel(FullGameHolder fghIn, CardLayout panelCardsIn, 
		AllGameInfo agiIn, Color blueIn)
	{
		fgh = fghIn;
		panelCards = panelCardsIn;
		agi = agiIn;
        blue = blueIn;

        pf = new PopupFactory();
        jtaOne = new JTextArea("", 5, 10);
        jtaOne.setBackground(blue);
        jtaOne.setForeground(Color.RED);
        jtaOne.setEditable(false);
        jtaOne.setLineWrap(true);
        jtaOne.setWrapStyleWord(true);
        jtaOne.setFont(agi.getFont50());
        jtaOne.setMargin(new Insets(15,15,15,15));
        hintPop = pf.getPopup(null, jtaOne, 425, 250);

        setBackground(Color.BLACK);
        setLayout(new BorderLayout(10, 10));

        makeQuiz();
	}	
    
    /* This is the mthod that remakes the quiz every time you enter the panel. All FVs,
     * buttongroup, questionArea, answer array, and all thre buttons, are initialized.
     * A generic panel with BL is created for the question JTextArea to be added to it,
     * using agi.getQOf() for the question text area text. text area is added to BL Center, 
     * and this panel is added to BL center. The generic panel for answers has a grid 
     * layout, and added to BL CENTER of "big" panel. Radio buttons in array instantiated
     * in loop, added to answers JPanel. Generic panel created for submit, next question, 
     * and next panel buttons, added to BL SOUTH, buttons instantiated.
     * Buttons are set correspondingly enabled/disabled. Instance of Action listener class, bbl,
     * added to buttons (and same was added as action listener for radio buttons). Hint panel
     * created, layout set to flow layout, hint button is added to generic panel, which is added 
     * to middle panel (grid layout).
     */
    public void makeQuiz()
    {
        group = new ButtonGroup();
        questionArea = new JTextArea("Quiz Round Word: " + agi.getQOf(agi.getRoundNum()), 3, 30);
        answer = new JRadioButton[4];
        submit = new JButton("SUBMIT");
        nextQuestion = new JButton("NEXT QUESTION");
        nextPanel = new JButton("HIGH SCORES");

        JPanel question = new JPanel();
        question.setPreferredSize(new Dimension(1000, 81));
        question.setBackground(blue);
        question.setLayout(new BorderLayout());
        add(question, BorderLayout.NORTH);
        
        questionArea.setFont(agi.getFont50());
        questionArea.setBackground(blue);
        questionArea.setForeground(Color.RED);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setOpaque(false);
        questionArea.setEditable(false);
        questionArea.setMargin(new Insets(15,15,15,15));

        question.add(questionArea, BorderLayout.CENTER);

        JPanel middle = new JPanel();
        middle.setLayout(new GridLayout(1, 2));
        JPanel answers = new JPanel();
        answers.setBackground(Color.BLACK);
        answers.setLayout(new GridLayout(4, 1));
        middle.add(answers);
        add(middle, BorderLayout.CENTER);

        BottomButtonListener bbl = new BottomButtonListener();

        for(int i = 0; i < answer.length; i++)
        {
            char letter = (char)(i + 65);
            answer[i] = new JRadioButton(letter + ". " + agi.getStringOf2(agi.getRoundNum(), i));
            group.add(answer[i]);
            answer[i].setOpaque(true);
            answer[i].setBackground(new Color(230,230,230)); 
            answer[i].setFont(agi.getFont30());
            answer[i].addActionListener(bbl);
            answer[i].setBackground(Color.BLACK);
            answer[i].setForeground(Color.GRAY);
            answers.add(answer[i]);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(blue);
        buttonPanel.setPreferredSize(new Dimension(1000, 90));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
        add(buttonPanel, BorderLayout.SOUTH);

        submit.setFont(agi.getFont30());
        submit.setForeground(Color.RED);
        submit.addActionListener(bbl);
        submit.setEnabled(false);
        buttonPanel.add(submit);

        nextQuestion.setFont(agi.getFont30());
        nextQuestion.setForeground(Color.RED);
        nextQuestion.addActionListener(bbl);
        nextQuestion.setEnabled(false);
        buttonPanel.add(nextQuestion);
        
        nextPanel.setFont(agi.getFont30());
        nextPanel.setForeground(Color.RED);
        nextPanel.addActionListener(bbl);
        nextPanel.setEnabled(false);
        buttonPanel.add(nextPanel);

        JPanel hintPanel = new JPanel();
        hintPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20));
        hintPanel.setBackground(Color.BLACK);
        hint = new JButton("Show Hint");
        hint.setFont(agi.getFont50());
        hint.setForeground(Color.RED);
        hint.addActionListener(bbl);
        hint.setOpaque(true);
        hint.setPreferredSize(new Dimension(400, 60));
        hintPanel.add(hint);
        middle.add(hintPanel);

    }
    
    // action listener class for JRadioButtons and JButtons
    class BottomButtonListener implements ActionListener
    {
        /* In action performed, evt.getActionCommand() is used
         * to get the string command. If something in the group is 
         * selected, submit button is enabled. If submit pressed,
         * correct answer for round's (taken from agi.getCorrectAnswer())
         * radio button background is set to green. If the wrong one was 
         * pressed, it is set to background red. Correct count is
         * accordingly incremented. If submitted, selection cleared and 
         * either next question or next panel enabled based on round number,
         * hint disabled and button's text is reset to "show hint" and hidden.
         * If next question pressed, question and texts of radio buttons 
         * changed by resetQuestion() in agi class, next question button disabled,
         * round num incremented, popup hidden, new instance created, text set to
         * show hint. If high scores pressed, popup hidden, text set back to show hint,
         * new popup instance created, most recent high scores saved, high scores 
         * panel repainted, high score panel shown. If show hint pressed, hint popup shown,
         * text set to hide hint, if hide hint pressed, popup hidden, text reset to show hint, 
         * create new instance of popup.
         */
        public void actionPerformed(ActionEvent evt) 
        {
            String command = evt.getActionCommand();
            
            if(group.getSelection() != null)
            {
                submit.setEnabled(true);
            }
            
            if(command.equals("SUBMIT"))
            {	
                hint.setEnabled(false);
                hintPop.hide();
                hint.setText("Show Hint");
                hintPop = pf.getPopup(null, jtaOne, 425, 250);
                answer[agi.getCorrectAnswer2(agi.getRoundNum())].setBackground(Color.GREEN);
                for(int i = 0; i < answer.length; i++)
                {
                    if(answer[i].isSelected())
                    {
                        if(i != agi.getCorrectAnswer2(agi.getRoundNum()))
                        {
                            answer[i].setBackground(Color.RED);
                        }
                        else
                        {
                            agi.setCorrectCount(agi.getCorrectCount() + 1);
                        }
                    }
                }
                group.clearSelection();
                for(int i = 0; i < answer.length; i++)
                {
                    answer[i].setEnabled(false);
                }
                submit.setEnabled(false);
                if(agi.getRoundNum() == agi.getNumRounds()-1)
                {
                    nextPanel.setEnabled(true);
                }
                else
                {
                    nextQuestion.setEnabled(true);
                }
            }
            else if(command.equals("NEXT QUESTION"))
            {
                agi.setRoundNum(agi.getRoundNum()+1);
                hintPop.hide();
                hint.setText("Show Hint");
                hintPop = pf.getPopup(null, jtaOne, 425, 250);
                resetQuestion();
                nextQuestion.setEnabled(false);
            }
            else if(command.equals("HIGH SCORES"))
            {
                hintPop.hide();
                hint.setText("Show Hint");
                hintPop = pf.getPopup(null, jtaOne, 425, 250);
                nextPanel.setEnabled(false);
                panelCards.show(fgh, "Results");
            }
            else if(command.equals("Show Hint"))
            {
                hint.setText("Hide Hint");
                hintPop.show();
            }
            else if(command.equals("Hide Hint"))
            {
                hint.setText("Show Hint");
                hintPop.hide();
                hintPop = pf.getPopup(null, jtaOne, 425, 250);
            }
        }
    }
    
    /* In reset question, the question and answers change to be for the index
     * of the new/current roundNum. The hint is enabled, text set to show hint, 
     * and the text area of the hint is updated for hte new round word. 
     * Backgrounds, foregrounds, enabled, and text of all JRadioButtons are set 
     * in a for loop.
     */
    public void resetQuestion ( )
    {
        hint.setEnabled(true);
        jtaOne.setText("The antonym of " + agi.getQOf(agi.getRoundNum())
            + " is: " + agi.getHintOf(agi.getRoundNum()));
        questionArea.setText("Quiz Round Word: " + agi.getQOf(agi.getRoundNum()));
        hint.setText("Show Hint");
        for(int i = 0; i < LOOPFOUR; i++)
        {
            answer[i].setForeground(Color.YELLOW);
            answer[i].setEnabled(true);
            answer[i].setBackground(Color.BLACK);
            char letter = (char)(i + 65);
            answer[i].setText(letter + ". " + agi.getStringOf2(agi.getRoundNum(), i));
        }
    }
}

// This panel shows the user's quiz results. 
class ResultsPagePanel extends JPanel
{
	private FullGameHolder fgh; // FV instance of card holder class to show other cards
	private CardLayout panelCards; // FV instance of card layout  to show other cards
	private AllGameInfo agi; // FV instance of information class to change FVs in that class
	
	private GenericTextAreaPanel hsp2; // FV to call repaint in high scores panel to update scores
	
	private JTextField nameField; // FV to receive text from field in action listener class
	private JLabel scoreLabel; // FV to set text in action listener class
	private JButton scoreButton; // FV to set enabled in action listener class
	
    /* Constructor initializes all FVS to the parameters passed in to be used throughout
     * the class. layout set to border layout, color of backgorund set, instance of 
     * repeating top panel created and added to BL NORTH with parameter of 'results' as 
     * panel name. Method called to create middle panel.
     */
	public ResultsPagePanel(FullGameHolder fghIn, CardLayout panelCardsIn, 
		AllGameInfo agiIn, GenericTextAreaPanel hspIn)
	{
		fgh = fghIn;
		panelCards = panelCardsIn;
		agi = agiIn;
		hsp2 = hspIn;

		setLayout(new BorderLayout());
        Color yellow = new Color(253,211,76);
        setBackground(yellow);

		String panelName = new String("     Results");
		RepeatingTopPanel rtp = new RepeatingTopPanel(fgh, panelCards, panelName, agi);
		add(rtp, BorderLayout.NORTH);
		
		makeMiddlePanel();
    }

    // paint component resets scorebutton to not enabled every time this panel is opened,
    // and name field set to enabled every time panel opened.
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        scoreButton.setEnabled(false);
        nameField.setEnabled(true);
    }
    
    /* Method creates generic JPanel middlepanel, adds to border layout, sets background,
     * has a layout of grid layout. JPanel (flow layout) created for explanation label (explains
     * entering information, uses HTML code provided by PutItTogether instructions in order
     * to center & put text on 2 lines), explanation label created & added to explanation panel, 
     * added to middle panel. Instance of action listener class NameButtonListener (nbl) created.
     * JPanel created for text field, layout flow layout, text field created, adds nbl as action 
     * listener and gets added to its panel, which is added to middle panel. JPanel for score label
     * created, flow layout set, label created + added to panel, panel added to middle panel. JPanel   
     * for high score button created, with flow layout, button created + added to panel, adds action
     * listener nbl, panel added to middle panel.
     */
    public void makeMiddlePanel()
    {
		JPanel middlePanel = new JPanel();
		add(middlePanel, BorderLayout.CENTER);
		middlePanel.setBackground(Color.BLACK);
		middlePanel.setLayout(new GridLayout(4, 1));

        JPanel expP = new JPanel();
		expP.setBackground(Color.BLACK);
		expP.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
		JLabel expLabel = new JLabel("<html> <center> Type your name below and <br>" +	
            "press enter to save your score </center> </html>");
		expLabel.setFont(agi.getFont30());
        expLabel.setForeground(Color.GRAY);
		expP.add(expLabel);
		middlePanel.add(expP);

		NameButtonListener nbl = new NameButtonListener();
		
		JPanel pOne = new JPanel();
		pOne.setBackground(Color.BLACK);
		pOne.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
		nameField = new JTextField("");
		nameField.setFont(agi.getFont30());
		nameField.addActionListener(nbl);
        nameField.setPreferredSize(new Dimension(500, 50));
		pOne.add(nameField);
		middlePanel.add(pOne);
		
		JPanel pTwo = new JPanel();
		pTwo.setBackground(Color.BLACK);
		pTwo.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
		scoreLabel = new JLabel("");
		scoreLabel.setFont(agi.getFont40());
        scoreLabel.setForeground(Color.GRAY);
		pTwo.add(scoreLabel);
		middlePanel.add(pTwo);
		
		JPanel pThree = new JPanel();
		pThree.setBackground(Color.BLACK);
		scoreButton = new JButton("See High Score Board");
		scoreButton.setForeground(Color.RED);
		scoreButton.setEnabled(false);
        scoreButton.setOpaque(true);
		scoreButton.setFont(agi.getFont30());
		scoreButton.addActionListener(nbl);
		pThree.add(scoreButton);
		middlePanel.add(pThree);
	}
	
    // class listens to action in buttons and text fields of this panel
	class NameButtonListener implements ActionListener
	{
        /* If nameField has something entered, text is received, set 
         * not enabled. Name FV in AGI class set to read in name.
         * Score label sets text to score with name attached. Score
         * Button enabled true, information saved to high scores, scores
         * reset in hsp2 class. If scoreButton pressed and something 
         * previously entered into text field, score label set to empty 
         * string (and name field), score button and nameField return to 
         * original enabled, high scores panel shown, game reset (AGI method)
         */
		public void actionPerformed(ActionEvent evt)
		{
			if(evt.getSource() == nameField)
			{
				String name = nameField.getText();
                nameField.setEnabled(false);
				agi.setName(name);
				scoreLabel.setText(agi.getName() + ", your score was: " 
					+ agi.getCorrectCount() + "/" + agi.getNumRounds());
				scoreButton.setEnabled(true);
                agi.saveToHighScores();
                hsp2.resetHighScores(agi.getHighScores());
			}
			else if(evt.getSource() == scoreButton 
				&& !nameField.getText().equals(""))
			{
                scoreLabel.setText("");
                nameField.setText("");
                scoreButton.setEnabled(false);
                nameField.setEnabled(true);
                panelCards.show(fgh, "High Scores");
                agi.resetGame();
			}
		}
	}

    // This method is called by the home page panel whenever
    // the game button is pressed in order to make sure that all of 
    // the user's info entered (if not fully entered or entere correctly)
    // is cleared before the next round played.
    public void resetFieldAndLabel()
    {
        scoreLabel.setText("");
        nameField.setText("");
        scoreButton.setEnabled(false);
        nameField.setEnabled(true);
    }
}

/* This class contains all the important information in the game.
 * Currently, it only contains the user-chosen settings. Eventually,
 * it will contain other info such as the user's score, right/wrong answers,
 * chosen questions for the user, etc.
 */
class AllGameInfo
{
    private int meteoriteSpeed; // stores the user-chosen meteorite speed 
    private int numRounds; // stores the user-chosen number of rounds
    private int numRoundsLeft; // stores the number of rounds left in the game
    private int diff; // stores the user-chosen difficulty
    private Image icon; // stores the user-chosen rocket icon
    private String classicName; // stores the original name of the rocket icon

    private int xDefault; // stores the timer-decremented default coordinates of the asteroids
    private int[] asteroidLocationAdded; // stores randomized amount added to default coords of asteroids
    private int yDefault; // stores the y coord of the default asteroid
    private int rocketX; // stores the current x coord of the rocket's location
    private int rocketY; // stores the current y coord of the rocket's location
    private boolean explode; // stores whether or not the explosion should print

    private int xMouse; // x location of the mouse
    private int yMouse;  // y location of the mouse
    private boolean dragging;// stores if the rectangle is being dragged

    private String[] questions; // array stores all questions read in from text file
	private String[][] answerSet; // stores all answer choices for all questions read in, for game 
	private int[] correctAnswer; // stores the correct answer number for each question
	private boolean[] chosenQuestions; // stores if each question has been chosen or not
	private int correctCount; // stores number of synonyms user got correct
    private boolean[] isCorrect; // stores whether user got synonym correct for any rounds
    private int chosenOne; // stores which asteroid the user chose for any given round
    private int roundNum; // stores which round the user is on
    private boolean[] chosenNonAnswers; // stores if each random/extra word was chosen yet or not
    private String[] allNonAnswers; // stores all random words to choose extra non-answers from
    private String[][] answerSet2; // stores all answer choices for all questions read in, for quiz
    private int[] correctAnswer2; // stores quiz correct answer number for each question
    private String[] hint; // stores hint for each question


    private String fileName; // stores the name of the file holding all the read-in info
    private Scanner inFile; // scanner to read in the file holding all the questions and answers
    private Scanner inFile2; // scanner to read in whichever text file answers are read from
    private Scanner inFile3; // scanner to read in high scores from all 3 high score files
    private String highScores1; // stores name of file to write high scores to (easy level)
    private String highScores2; // stores name of file to write high scores to (medium level)
    private String highScores3; // stores name of file to write high scores to (hard level)
    private String chosenScores;
    private PrintWriter outFile; // pritnwriter to write high scores to the correct file
    private String allExtraWords; // name of file to read in extra words from
    private Scanner inFile4; // scanner to read in all extra/random words
    
    private String userName; // string stores user-entered username

    private Font font15; // font size 15
    private Font font30; // font size 30
    private Font font40; // font size 40
    private Font font50; // font size 50

    private final int SCORESCOUNT = 5; // final FV for how many times to read for 
                                       // high score board in loop

    // initializes all FVs to default values
    // arrays are initialized to default value of numRounds
    // asteroidLocationAdded array values are randomized
    // All non answers are read into array
    public AllGameInfo()
    {
        meteoriteSpeed = 10;
        numRounds = 10;
        numRoundsLeft = numRounds;
        diff = 1;
        classicName = new String("classic.png");
        icon = getTheImage(classicName);
        xDefault = 1000;
        yDefault = 10;
        rocketX = 10;
        rocketY = 156;
        roundNum = 0;
        xMouse = yMouse = 0;
		dragging = false;
        correctCount = 0;
        chosenOne = -1;
        explode = false;
        userName = new String("");

        font15 = new Font("Montserrat", Font.BOLD, 15);
        font30 = new Font("Montserrat", Font.BOLD, 30);
        font40 = new Font("Montserrat", Font.BOLD, 40);
        font50 = new Font("Montserrat", Font.BOLD, 50);

        fileName = new String("mediumWords.txt");
        inFile = inFile2 = inFile3 = inFile4 = null;
        highScores1 = new String("highestScores1.txt");
        highScores2 = new String("highestScores2.txt");
        highScores3 = new String("highestScores3.txt");
        chosenScores = new String("highestScores2.txt");
        allExtraWords = new String("extraWords.txt");
        inFile4 = readAFile(allExtraWords);
        outFile = null;
        
        allNonAnswers = new String[885];
        for(int i = 0; i < allNonAnswers.length; i++)
        {
            allNonAnswers[i] = inFile4.nextLine();
        }

        chosenNonAnswers = new boolean[1000];
        chosenQuestions = new boolean[60];

        questions = new String[numRounds];

        answerSet = new String[numRounds][4];
        answerSet2 = new String[numRounds][4];

        correctAnswer = new int[numRounds];
        correctAnswer2 = new int[numRounds];
        
        isCorrect = new boolean[numRounds];
        hint = new String[numRounds];
        
        
        asteroidLocationAdded = new int[4];
        for(int i = 0; i < asteroidLocationAdded.length; i++)
		{
			asteroidLocationAdded[i] = (int)(Math.random()*300+0);
		}
    }

    /* This method resets almost all variables at the end of the 
     * full game and quiz taken by the user. All arrays reinitialized,
     * new questions grabbed, x and y coords of rocket and asteroids reset 
     * by calling the reset all method, round num, rounds left, exploding,
     * dragging, correct, all reset. Asteroid added location re-randomized.
     */
    public void resetGame()
    {
        fileName = getFileName();
        explode = false;
        chosenNonAnswers = new boolean[885];
        chosenQuestions = new boolean[60];

        questions = new String[numRounds];

        answerSet = new String[numRounds][4];
        answerSet2 = new String[numRounds][4];

        correctAnswer = new int[numRounds];
        correctAnswer2 = new int[numRounds];
        
        isCorrect = new boolean[numRounds];

        hint = new String[numRounds];
		
		correctCount = 0;
        chosenOne = -1;
		
		resetAll();        
        numRoundsLeft = numRounds;
        roundNum = 0;
        grabQuestionFromFile();
    }

    /* This method is called whenever the user changes
     * the number of rounds for the game. This resizes
     * all of the arrays to the newly reset numRounds
     */
    public void changeNumRounds(int numRoundsChosen)
    {
        numRounds = numRoundsChosen;
        questions = new String[numRounds];

        answerSet = new String[numRounds][4];
        answerSet2 = new String[numRounds][4];

        correctAnswer = new int[numRounds];
        correctAnswer2 = new int[numRounds];
        
        isCorrect = new boolean[numRounds];

        hint = new String[numRounds];
    }

    /* instantiates a scanner for the name of the file
     * specified by fileString passed in as a parameter.
     * temporary scanner initialized, temporary file name
     * is the parameter added to files/ to receive the file
     * from a folder. In try, instantiates scanner for file
     * of filename, catches file not found exception and prints
     * error. Else, returns scanner.
     */
    public Scanner readAFile(String fileString)
    {
        Scanner tempScanner = null;
        String tempName2 = "files/" + fileString;       
        File inputFile = new File(tempName2);
        try
        {
            tempScanner = new Scanner(inputFile);
        }
        catch(FileNotFoundException e)
        {
            System.err.printf("ERROR: Cannot open %s\n",
                fileString);
            System.out.println(e);
            System.exit(1);
        }
        return tempScanner;
    }

    /* instantiates a temp image name for the name of the file
     * specified by fileString passed in as a parameter. 
     ( with pictures/ added to read from the folder).
     * temporary image initialized, In try, ImageIO reads in
     * file with image name,, catches IOException and prints
     * error. Else, returns image.
     */
    public Image getTheImage(String imageNameIn)
	{
        Image tempImage = null;
        String tempName = "pictures/" + imageNameIn;
		File thePic = new File(tempName);
		try
		{
			tempImage = ImageIO.read(thePic);
		}
		catch(IOException e)
		{
			System.err.println("\n\n" + imageNameIn + " can't be found.\n\n");
			e.printStackTrace();
			System.exit(1);
		}
		return tempImage;
	}

    /* This method uses FileIO to read in
     * the file into the arrays to initialize them.
     * It uses a for loop so that it reads in as
     * many times as there are rounds, reading in 
     * the questions, answers, and using while loops
     * inside to skip past unneeded lines or to check
     * if the question has been chosen. It receives
     * the file name from getFileName(), then 
     * creats a scanner for it by calling readAFile().
     * It generates a random question number, and uses
     * a while loop to read until it gets to that question 
     * (unless the question has already been chosen, in whic
     * case it will be rerandomized). The next thre lines read
     * in are the question and 2 answers (game and quiz), and
     * these are randomly intializes to their answer set arrays
     * with random numbers generated for the correct answer number.
     * The rest of the empty indices in both answer set arrays are
     * filled up randomly by non-repeating (through while loop) random
     * words from the extraWords file.
     */
    public void grabQuestionFromFile ()
	{
        String line = new String("");
        for(int i = 0; i < numRounds; i++)
        {
            fileName = getFileName();
            inFile = readAFile(fileName);

            int counter = 0;

            int questionNumber = (int)(Math.random()*60 + 0);

            while(chosenQuestions[questionNumber] == true)
            {
                questionNumber = (int)(Math.random()*60 + 0);
            }

            chosenQuestions[questionNumber] = true;

            while(inFile.hasNext() && counter < answerSet[0].length * questionNumber)
            {
                line = inFile.nextLine();
                counter++;
            }

            questions[i] = inFile.nextLine();
            counter = 0;

            correctAnswer[i] = (int)(Math.random()*4 + 0);
            answerSet[i][correctAnswer[i]] = inFile.nextLine();
            correctAnswer2[i] = (int)(Math.random()*4 + 0);
            answerSet2[i][correctAnswer2[i]] = inFile.nextLine();
            hint[i] = inFile.nextLine();

            int wordNum = 0;
            for(int j = 0; j < answerSet[0].length; j++)
            {
                if(answerSet[i][j] == null)
                {
                    wordNum = (int)(Math.random()*885 + 0);
                    while(chosenNonAnswers[wordNum] == true)
                    {
                        wordNum = (int)(Math.random()*885 + 0);
                    }
                    answerSet[i][j] = allNonAnswers[wordNum];
                    chosenNonAnswers[wordNum] = true;
                }
                if(answerSet2[i][j] == null)
                {
                    wordNum = (int)(Math.random()*885 + 0);
                    while(chosenNonAnswers[wordNum] == true)
                    {
                        wordNum = (int)(Math.random()*885 + 0);
                    }
                    answerSet2[i][j] = allNonAnswers[wordNum];
                    chosenNonAnswers[wordNum] = true;
                }
            }
        }
	}

    /* Whenever the asteroids move past the screen
     * or the user drags the rocket onto an asteroid,
     * the positions of both the rocket and asteroids 
     * reset (thus these location variables being reset),
     * and the asteroid locations added are re-randomized
     */
    public void resetAll()
    {
        numRoundsLeft--;
        xDefault = 1000;
        yDefault = 10;
        rocketX = 10;
        rocketY = 156;
        roundNum ++;
        xMouse = yMouse = 0;
		dragging = false;
		
		for(int i = 0; i < asteroidLocationAdded.length; i++)
		{
			asteroidLocationAdded[i] = (int)(Math.random()*300+0);
		}
    }

    /* This method, called after the quiz is taken, saves the 
     * user's score to the high score text file. It instantiates
     * a scanner with readAFile() method call, then uses a while loop to
     * go through the whole text file (of the user's chosen level)
     * to check if the ratio of right to wrong answers is higher for the 
     * user/player than the person at the leaderboard for that spot, and if it is, 
     * user's score will be added to a string before others' (checked by parsing
     * integer before and after the / in the line, receiving the decimal value
     * for what percent of answers they got right).
     * If no one is worse than you, your score is added to the end.
     * It then instantiates a filereader for the highestScores file, and uses
     * a try-catch to do so. The temporary string from traversing through the
     * textfile to save the string of all of them gets set to the output file.
     * instantiate printwriter to writeAFile() method call, print string result to it.
     */
    public void saveToHighScores ( ) 
	{
        String result = new String("");
        boolean hasBeenAdded = false;
        inFile2 = readAFile(chosenScores);
        
        double tempVal3 = (double)(correctCount)/(double)(numRounds);
        int percentage = (int)(tempVal3*100);
        String line = new String("");
        while(inFile2.hasNext()) 
        {
            line = inFile2.nextLine();
            double tempVal1 = (double)(Integer.parseInt((line.substring(line.indexOf(':')+1, line.indexOf('/'))).trim()));
            double tempVal2 = (double)(Integer.parseInt(line.substring(line.indexOf("/")+1, line.indexOf(","))));

            double tempVal = tempVal1/tempVal2;
            if(!hasBeenAdded && tempVal <= tempVal3)
            {
                result += userName + ": " + correctCount + "/" + numRounds + ", " + percentage 
                    + "%\n";
                hasBeenAdded = true;
            }
            result += line + "\n";
        }
        if(!hasBeenAdded)
        {
            result += userName + ": " + correctCount + "/" + numRounds + ", " + percentage 
                    + "%\n";
        }
        
        outFile = writeAFile(chosenScores);
        outFile.print(result);
        outFile.close();
	}

    /* instantiates a printwriter for the name of the file
     * specified by writeFileName passed in as a parameter.
     * temporary pw initialized, temporary file name
     * is the parameter added to files/ to receive the file
     * from a folder. In try, instantiates pw for file
     * of filename, catches IOException and prints
     * error. Else, returns pw.
     */
    public PrintWriter writeAFile(String writeFileName)
    {
        PrintWriter tempPrint = null;
        String tempName3 = "files/" + writeFileName;
        File ioFile = new File(tempName3);
        try
        {
            tempPrint = new PrintWriter(ioFile);
        }
        catch(IOException e)
        {
            System.err.printf("ERROR: Cannot open %s\n", writeFileName);
            System.out.println(e);
            System.exit(1);
        }
        return tempPrint;
    }

    /* This method returns the string of all of the scores in the
     * highest scores text file by instantiating the scanner to 
     * the call of readAFile() method, and it creats one long string
     * with the top 5 scores from each of the three high scores text 
     * files. The title of the level is added to the string each time,
     * then in a loop of 5, the first 5 lines (scores) are read in and 
     * added to the string, and the string is returned to method's call.
     */ 
    public String getHighScores ( )
	{
		String result2 = new String("");

        int count = 0;
        inFile3 = readAFile(highScores1);

        result2 += "Easy Words:\n";
        String line = new String("");
		while(inFile3.hasNext() && count < SCORESCOUNT) 
		{
			line = inFile3.nextLine();
			result2 += line + "\n";
            count++;
		}

        count = 0;
        inFile3 = readAFile(highScores2);

        result2 += "\nMedium Words:\n";
		while(inFile3.hasNext() && count < SCORESCOUNT) 
		{
			line = inFile3.nextLine();
			result2 += line + "\n";
            count++;
		}

        count = 0;
        inFile3 = readAFile(highScores3);

        result2 += "\nHard Words:\n";
		while(inFile3.hasNext() && count < SCORESCOUNT) 
		{
			line = inFile3.nextLine();
			result2 += line + "\n";
            count++;
		}

		return result2;
	}
    
    /* returns the randomized int amount to add to the default
     * asteroid x coordinate, specified by parameter
     */
    public int getAsteroidLocationAdded(int index)
    {
		return asteroidLocationAdded[index];
	}

    // returns the name of file to read from
    public String getFileName()
    {
        return fileName;
    }

    /* sets the name of the file to read in from as 
     * the parameter (based on what user chooses in settings)
     */
    public void setFileName(String fileNameIn)
    {
        fileName = fileNameIn;

    }

    // returns the number of words the user got correct
    public int getCorrectCount()
    {
        return correctCount;
    }

    /* reinitializes the correctCount variable
     *of how many words the user got correct to
     * the parameter
     */
    public void setCorrectCount(int corrCount)
    {
        correctCount = corrCount;
    }

    /* returns the synonym option specified
     * by the round number passed in and the index
     * of which asteroid it is for
     */
    public String getStringOf(int index, int index2)
    {
        return answerSet[index][index2];
    }

    public String getStringOf2(int index, int index2)
    {
        return answerSet2[index][index2];
    }

    // returns question for round specified by the index
    public String getQOf(int index)
    {
        return questions[index];
    }

    public String getHintOf(int index)
    {
        return hint[index];
    }

    // returns the int of the asteroid the user chooses
    public int getChosenOne()
    {
        return chosenOne;
    }

    // initializes the asteroid user chooses to parameter
    public void setChosenOne(int chosen)
    {
        chosenOne = chosen;
    }
    
    // returns the number of the current round
    public int getRoundNum()
    {
        return roundNum;
    }
    
    // initializes the round number as passed in parameter
    public void setRoundNum(int inRound)
    {
		roundNum = inRound;
	}

    // returns the x default coordinate of the asteroids
    public int getX()
    {
        return xDefault;
    }

    // sets the x default coordinate of the asteroids
    public void setX(int xIn)
    {
        xDefault = xIn;
    }

    // returns the y default coordinate of the asteroids
    public int getY()
    {
        return yDefault;
    }

    // sets the y default coordinate of the asteroids
    public void setY(int yIn)
    {
        yDefault = yIn;
    }

    // returns the x coordinate of the rocket
    public int getRocketX()
    {
        return rocketX;
    }

    // sets the x coordinate of the rocket
    public void setRocketX(int rxIn)
    {
        rocketX = rxIn;
    }

    // returns the y coordinate of the rocket
    public int getRocketY()
    {
        return rocketY;
    }

    // sets the y coordinate of the rocket
    public void setRocketY(int ryIn)
    {
        rocketY = ryIn;
    }

    // returns the x coordinate of the mouse
    public int getXMouse()
    {
        return xMouse;
    }

    // sets the x coordinate of the mouse
    public void setXMouse(int xmIn)
    {
        xMouse = xmIn;
    }

    // returns the y coordinate of the mouse
    public int getYMouse()
    {
        return yMouse;
    }

    // sets the y coordinate of the mouse
    public void setYMouse(int ymIn)
    {
        yMouse = ymIn;
    }

    // returns the boolean value of whether the mouse is being dragged
    public boolean getDragging()
    {
        return dragging;
    }

    // sets the boolean value of whether the mouse is being dragged
    public void setDragging(boolean isDragging)
    {
        dragging = isDragging;
    }

    // returns the int of the difficulty chosen by the user
    public int getDifficulty()
    {
        return diff;
    }
    
    // sets the int of the difficulty chosen by the user
    public void setDifficulty(int diffIn)
    {
        diff = diffIn;
        chosenScores = "highestScores" + diff + ".txt";
    }

    // gets the meteoriteSpeed int
    public int getMeteoriteSpeed()
    {
        return meteoriteSpeed;
    }

    // reinitializes the meteoriteSpeed int
    public void setSpeed(int speedNum)
    {
        meteoriteSpeed = speedNum;
    }
	
    // gets the numRounds int
    public int getNumRounds()
    {
        return numRounds;
    }

    // reinitializes the numRounds int
    public void setRounds(int roundNum)
    {
        numRounds = roundNum;
    }

    // gets the iconNum int
    public Image getIcon()
    {
        return icon;
    }

    // reinitializes the iconNum int
    public void setIcon(Image iconIn)
    {
        icon = iconIn;
    }

    // returns the int of the correct answer for the round
    // specified by the parameter
    public int getCorrectAnswer(int index)
    {
        return correctAnswer[index];
    }

    public int getCorrectAnswer2(int index)
    {
        return correctAnswer2[index];
    }

    // returns the boolean of whether the user's chosen
    // answer was correct or not for the round
    public boolean getIsCorrect(int index)
    {
        return isCorrect[index];
    }

    // sets whether or not the user's answer is correct
    // for a specific round
    public void setIsCorrect(int index, boolean iscorr)
    {
        isCorrect[index] = iscorr;
    }

    // returns whether or not the explosion should be printed
	public boolean getExplode()
	{
		return explode;
	}
    
    // sets whether or not the explosion should be printed
    public void setExplode(boolean exp)
    {
		explode = exp;
	}
	
    // returns string of user's username
	public String getName()
	{
		return userName;
	}

    // sets the user's username as passed in param (entered in text field)
	public void setName(String tempName)
	{
		userName = tempName;
	}

    // returns size 15 font
    public Font getFont15()
    {
        return font15;
    }

    // returns size 30 font
    public Font getFont30()
    {
        return font30;
    }

    // returns size 40 font
    public Font getFont40()
    {
        return font40;
    }

    // returns size 50 font
    public Font getFont50()
    {
        return font50;
    }
}