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
    private static final int VISIBLE_WIDTH = 480;
    public static final int VISIBLE_HEIGHT = 640;

    // Additional useful constants based on world size
    public static final int HALF_VISIBLE_WIDTH = VISIBLE_WIDTH / 2;
    public static final int HALF_VISIBLE_HEIGHT = VISIBLE_HEIGHT / 2;

    // Defining the boundaries of the scrollable world
    // TO STUDENTS: Modify SCROLLABLE_WIDTH if you wish to have a longer level
    public static final int SCROLLABLE_WIDTH = VISIBLE_WIDTH ;
    public static final int SCROLLABLE_HEIGHT = VISIBLE_HEIGHT * 3 ;

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
        // addLeftGround();
        // addFences();
        // addIceSteps();
        // addClouds();
        // addRightGround();

        // add base level 
        for (int  i = 0; i <= 14; i += 1)
        { 
            int x = HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 19 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }

        // second level 
        for (int  i = 0; i <= 3; i += 1)
        { 
            int x = HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 15 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        for (int  i = 0; i <= 7; i += 1)
        { 
            int x = 7 * TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 15 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }

        // tird level 
        for (int  i = 0; i <= 7; i += 1)
        { 
            int x = HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 11 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }

        for (int  i = 0; i <= 3; i += 1)
        { 
            int x = 11 * TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 11 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }

        // fourth level 
        for (int  i = 0; i <= 4; i += 1)
        { 
            int x = HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 7 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        for (int  i = 0; i <= 2; i += 1)
        { 
            int x = 8 * TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 7 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        for (int  i = 0; i <= 1; i += 1)
        { 
            int x = 13 * TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 7 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }

        // fith level 
        for (int  i = 0; i <= 2; i += 1)
        { 
            int x = HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 3 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        for (int  i = 0; i <= 1; i += 1)
        { 
            int x = 6 * TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 3 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        for (int  i = 0; i <= 2; i += 1)
        { 
            int x = 12 * TILE_SIZE + HALF_TILE_SIZE + i * TILE_SIZE;
            int y = 3 * TILE_SIZE + HALF_TILE_SIZE;

            Ice plate = new Ice(x, y);
            addObject(plate, x, y);
        }
        addHero();
    }

    /**
     * Add blocks to create the ground to walk on at bottom-left of scrollable world.
     */
    private void addLeftGround()
    {
        // How many tiles will cover the bottom of the initial visible area of screen?
        final int tilesToCreate = getWidth() / TILE_SIZE;

        // Loop to create and add the tile objects
        for (int i = 0; i < tilesToCreate; i += 1)
        {
            // Add ground objects at bottom of screen
            // NOTE: Actors are added based on their centrepoint, so the math is a bit trickier.
            int x = i * TILE_SIZE + HALF_TILE_SIZE;
            int y = getHeight() - HALF_TILE_SIZE;

            // Create a ground tile
            Ground groundTile = new Ground(x, y);

            // Add the objects
            addObject(groundTile, x, y);
        }
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
        addObject(theHero, initialX, 18 * TILE_SIZE);
    }

    /**
     * Add blocks to create the ground to walk on at top-right of scrollable world.
     */
    private void addRightGround()
    {
        // Constants to control dimensions of the ground at end of world
        final int COUNT_OF_GROUND = 8;
        final int GROUND_BELOW_COLUMNS = COUNT_OF_GROUND;
        final int GROUND_BELOW_ROWS = 6;
        final int COUNT_OF_GROUND_BELOW = GROUND_BELOW_COLUMNS * GROUND_BELOW_ROWS;

        // 1. Make ground at end of level (top layer)
        for (int i = 0; i < COUNT_OF_GROUND; i += 1)
        {
            // Position in wider scrollable world
            int x = SCROLLABLE_WIDTH - HALF_TILE_SIZE - i * TILE_SIZE;
            int y = HALF_VISIBLE_HEIGHT + TILE_SIZE;

            // Create object and add it
            Ground ground = new Ground(x, y);
            addObject(ground, x, y);
        }

        // 2. Make sub-ground at end of level (below top layer)
        for (int i = 0; i < GROUND_BELOW_COLUMNS; i += 1)
        {
            for (int j = 0; j < GROUND_BELOW_ROWS; j += 1)
            {
                // Position in wider scrollable world
                int x = SCROLLABLE_WIDTH - HALF_TILE_SIZE - i * TILE_SIZE;
                int y = HALF_VISIBLE_HEIGHT + TILE_SIZE + TILE_SIZE * (j + 1);

                // Create object and add it
                GroundBelow groundBelow = new GroundBelow(x, y);
                addObject(groundBelow, x, y);
            }
        }
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

