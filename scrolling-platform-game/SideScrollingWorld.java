import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Template for a side-scrolling platform game.
 * 
 * @author R. Gordon
 * @version May 8, 2019
 */
public class SideScrollingWorld extends World
{
    /**
     * Instance variables
     * 
     * These are available for use in any method below.
     */    
    // Tile size in pixels for world elements (blocks, clouds, etc)
    // TO STUDENTS: Modify if your game's tiles have different dimensions
    private static final int TILE_SIZE = 32;
    private static final int HALF_TILE_SIZE = TILE_SIZE / 2;

    // World size constants
    // TO STUDENTS: Modify only if you're sure
    //              Should be a resolution that's a multiple of TILE_SIZE
    private static final int VISIBLE_WIDTH = 640;
    public static final int VISIBLE_HEIGHT = 480;

    // Additional useful constants based on world size
    public static final int HALF_VISIBLE_WIDTH = VISIBLE_WIDTH / 2;
    public static final int HALF_VISIBLE_HEIGHT = VISIBLE_HEIGHT;

    // Defining the boundaries of the scrollable world
    // TO STUDENTS: Modify SCROLLABLE_WIDTH if you wish to have a longer level
    public static final int SCROLLABLE_WIDTH = VISIBLE_WIDTH * 3;
    public static final int SCROLLABLE_HEIGHT = VISIBLE_HEIGHT ;

    // Hero
    Hero theHero;

    // Track whether game is on
    private boolean isGameOver;

    /**
     * Constructor for objects of class SideScrollingWorld.
     */
    public SideScrollingWorld()
    {    
        // Create a new world with 480 x 640 cells with a cell size of 1x1 pixels.
        // Final argument of 'false' means that actors in the world are not restricted to the world boundary.
        // See: https://www.greenfoot.org/files/javadoc/greenfoot/World.html#World-int-int-int-boolean-
        super(VISIBLE_WIDTH, VISIBLE_HEIGHT, 1, false);

        // Set up the starting scene
        setup();

        // Game on
        isGameOver = false;
    }

    /**
     * Set up the entire world.
     */
    private void setup()
    {
        // TO STUDENTS: Add, revise, or remove methods as needed to define your own game's world

        // add starting platform 
        addPlatforms();

        

        
        addHero();
    }
    
    /**
     * Add all the small platforms 
     */
    private void addPlatforms()
    {
        // add starting plaform
        for (int  i = 0; i <= 5; i += 1)
        { 
            int x = HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 14 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        
        // Add second platform  
        for (int  i = 0; i <= 2; i += 1)
        { 
            int x = HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 10 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        
        // tird platform 
        for (int  i = 0; i <= 3; i += 1)
        { 
            int x = 2 * TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 6 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        
        // fourth platform
        for (int  i = 0; i <= 2; i += 1)
        { 
            int x = TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 2 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        for (int  i = 0; i <= 2; i += 1)
        { 
            int x = 5 * TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 2 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        
        // fith platform 
    }

    /**
     * Add steps made out of metal plates leading to end of world.
     */
    private void addIceSteps()
    {
        // How many Ice total?
        final int COUNT_OF_ICE = 20;
        final int PLATES_PER_GROUP = 4;

        // Add groups of plates
        for (int i = 0; i < COUNT_OF_ICE / PLATES_PER_GROUP; i += 1)
        {
            // Group of four metal plates all at same y position
            int y = VISIBLE_HEIGHT - HALF_TILE_SIZE * 3 - i * TILE_SIZE;

            // Add the individual plates in a given group
            for (int j = 0; j < PLATES_PER_GROUP; j += 1)
            {
                int x = VISIBLE_WIDTH + TILE_SIZE * 2 + TILE_SIZE * (i + j) + TILE_SIZE * 5 * i;
                Ice plate = new Ice(x, y);
                addObject(plate, x, y);
            }
        }
    }

    /**
     * Act
     * 
     * This method is called approximately 60 times per second.
     */
    public void act()
    {
    }

    /** 
     * Add the hero to the world.
     */
    private void addHero()
    {
        // Initial horizontal position
        int initialX = TILE_SIZE * 3;

        // Instantiate the hero object
        theHero = new Hero(initialX);

        // Add hero in bottom left corner of screen
        addObject(theHero, initialX, 13 * TILE_SIZE);
    }

    /**
     * Return an object reference to the hero.
     */
    public Hero getHero()
    {
        return theHero;
    }

    /**
     * Set game over
     */
    public void setGameOver()
    {
        isGameOver = true;
    }
}

