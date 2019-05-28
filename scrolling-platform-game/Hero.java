import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * This is the class for the "main character" in the action.
 * 
 * @author R. Gordon
 * @version May 8, 2019
 */
public class Hero extends Actor
{
    /**
     * Instance variables
     * 
     * These are available for use in any method below.
     */    
    // Horizontal speed (change in horizontal position, or delta X)
    private int deltaX = 4;

    // Vertical speed (change in vertical position, or delta Y)
    private int deltaY = 4;

    // Acceleration for falls
    private int acceleration = 2;

    // Strength of a jump
    private int jumpStrength = -25;

    // Track current theoretical position in wider "scrollable" world
    private int currentScrollableWorldYPosition;

    // Track whether game is over or not
    private boolean isGameOver;

    // Constants to track vertical direction
    private static final String JUMPING_UP = "up";
    private static final String JUMPING_DOWN = "down";
    private String verticalDirection;

    // Constants to track vertical  direction
    private static final String FACING_UP = "up";
    private static final String FACING_DOWN = "down";
    private String horizontalDirection;

    // For walking animation
    private GreenfootImage walkingRightImages[];
    private GreenfootImage walkingLeftImages[];
    private static final int WALK_ANIMATION_DELAY = 8;
    private static final int COUNT_OF_WALKING_IMAGES = 2;
    private int walkingFrames;

    /**
     * Constructor
     * 
     * This runs once when the Hero object is created.
     */
    Hero(int startingY)
    {
        // Set where hero begins horizontally
        currentScrollableWorldYPosition = startingY;

        // Game on
        isGameOver = false;

        // First jump will be in 'down' direction
        verticalDirection = JUMPING_DOWN;

        // Facing right to start
        horizontalDirection = FACING_UP;

        // Set image
        setImage("PopoNana-fall-right.png");

        // Initialize the 'walking' arrays
        walkingRightImages = new GreenfootImage[COUNT_OF_WALKING_IMAGES];
        walkingLeftImages = new GreenfootImage[COUNT_OF_WALKING_IMAGES];

        // Load walking images from disk
        for (int i = 0; i < walkingRightImages.length; i++)
        {
            walkingRightImages[i] = new GreenfootImage("PopoNana-walk-right-" + i + ".png");

            // Create left-facing images by mirroring horizontally
            walkingLeftImages[i] = new GreenfootImage(walkingRightImages[i]);
            walkingLeftImages[i].mirrorHorizontally();
        }

        // Track animation frames for walking
        walkingFrames = 0;
    }

    /**
     * Act - do whatever the Hero wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkKeys();
        checkFall();
        if (!isGameOver)
        {
            checkGameOver();
        }
    }

    /**
     * Respond to keyboard action from the user.
     */
    private void checkKeys()
    {
        // Walking keys
        if (Greenfoot.isKeyDown("up") && !isGameOver)
        {
            moveDown();
        }
        else if (Greenfoot.isKeyDown("down") && !isGameOver)
        {
            moveUp();
        }
        else
        {
            // Standing still; reset walking animation
            walkingFrames = 0;
        }

        // Jumping
        if (Greenfoot.isKeyDown("space") && !isGameOver)
        {
            //Only able to jump when on a solid object
            if (onPlatform())
            {
                jump();
            }
        }
    }

    /**
     * Should the hero be falling right now?
     */
    public void checkFall()
    {
        if (onPlatform())
        {
            // Stop falling
            deltaY = 0;

            // Set image
            if (horizontalDirection == FACING_UP && Greenfoot.isKeyDown("right") == false)
            {
                setImage("PopoNana-right.png");
            }
            else if (horizontalDirection == FACING_DOWN && Greenfoot.isKeyDown("left") == false)
            {
                setImage("PopoNana-left.png");
            }

            // Get a reference to any object that's created from a subclass of Platform,
            // that is below (or just below in front, or just below behind) the hero
            Actor directlyUnder = getOneObjectAtOffset(0, getImage().getHeight() / 2, Platform.class);
            Actor frontUnder = getOneObjectAtOffset(getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);
            Actor rearUnder = getOneObjectAtOffset(0 - getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);

            // Bump the hero back up so that they are not "submerged" in a platform object
            if (directlyUnder != null)
            {
                int correctedYPosition = directlyUnder.getY() - directlyUnder.getImage().getHeight() / 2 - this.getImage().getHeight() / 2;
                setLocation(getX(), correctedYPosition);
            }
            if (frontUnder != null)
            {
                int correctedYPosition = frontUnder.getY() - frontUnder.getImage().getHeight() / 2 - this.getImage().getHeight() / 2;
                setLocation(getX(), correctedYPosition);
            }
            if (rearUnder != null)
            {
                int correctedYPosition = rearUnder.getY() - rearUnder.getImage().getHeight() / 2 - this.getImage().getHeight() / 2;
                setLocation(getX(), correctedYPosition);
            }
        }
        else
        {
            fall();
        }
    }

    /**
     * Is the hero currently touching a solid object? (any subclass of Platform)
     */
    public boolean onPlatform()
    {
        // Get an reference to a solid object (subclass of Platform) below the hero, if one exists
        Actor directlyUnder = getOneObjectAtOffset(0, getImage().getHeight() / 2, Platform.class);
        Actor frontUnder = getOneObjectAtOffset(getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);
        Actor rearUnder = getOneObjectAtOffset(0 - getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);

        // If there is no solid object below (or slightly in front of or behind) the hero...
        if (directlyUnder == null && frontUnder == null && rearUnder == null)
        {
            return false;   // Not on a solid object
        }
        else
        {
            return true;
        }
    }

    /**
     * Make the hero jump.
     */
    public void jump()
    {
        // Track vertical direction
        verticalDirection = JUMPING_UP;

        // Set image
        if (horizontalDirection == FACING_UP)
        {
            setImage("PopoNana-jump-up-right.png");
        }
        else
        {
            setImage("PopoNana-jump-up-left.png");
        }

        // Change the vertical speed to the power of the jump
        deltaY = jumpStrength;

        // Make the character move vertically 
        fall();
    }

    /**
     * Make the hero fall.
     */
    public void fall()
    {
        // See if direction has changed
        if (deltaY > 0)
        {
            verticalDirection = JUMPING_DOWN;

            // Set image
            if (horizontalDirection == FACING_DOWN)
            {
                setImage("PopoNana-fall-right.png");
            }
            else
            {
                setImage("PopoNana-fall-left.png");
            }
        }

        // Fall (move vertically)
        int newVisibleWorldYPosition = getY() + deltaY;
        setLocation(getX(), newVisibleWorldYPosition );

        // Accelerate (fall faster next time)
        deltaY = deltaY + acceleration;
    }

    /**
     * Animate walking
     */
    private void animateWalk(String direction)
    {
        // Track walking animation frames
        walkingFrames += 1;

        // Get current animation stage
        int stage = walkingFrames / WALK_ANIMATION_DELAY;

        // Animate
        if (stage < walkingRightImages.length)
        {
            // Set image for this stage of the animation
            if (direction == FACING_UP)
            {
                setImage(walkingRightImages[stage]);
            }
            else
            {
                setImage(walkingLeftImages[stage]);
            }
        }
        else
        {
            // Start animation loop from beginning
            walkingFrames = 0;
        }
    }

    /**
     * Move the hero to the right.
     */
    public void moveDown()
    {
        // Track direction
        verticalDirection = FACING_DOWN;

        // Set image 
        if (onPlatform())
        {
            animateWalk(horizontalDirection);
        }
        else
        {
            // Set appropriate jumping image
            if (verticalDirection == JUMPING_UP)
            {
                setImage("PopoNana-jump-up-right.png");
            }
            else
            {
                setImage("PopoNana-fall-right.png");
            }
        }

        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        // Decide whether to actually move, or make world's tiles move
        if (currentScrollableWorldYPosition < world.HALF_VISIBLE_HEIGHT)
        {
            // HERO IS WITHIN EXTREME LEFT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Move to right in visible world
            int newVisibleWorldYPosition = getY() + deltaY;
            setLocation(getY(), newVisibleWorldYPosition);

            // Track position in wider scrolling world
            currentScrollableWorldYPosition = getX();
        }
        else if (currentScrollableWorldYPosition + deltaX * 2 > world.SCROLLABLE_HEIGHT - world.HALF_VISIBLE_HEIGHT)
        {
            // HERO IS WITHIN EXTREME RIGHT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Allow movement only when not at edge of world
            if (currentScrollableWorldYPosition < world.SCROLLABLE_HEIGHT - this.getImage().getHeight() / 2)
            {
                // Move to right in visible world
                int newVisibleWorldYPosition = getX() + deltaY;
                setLocation(getY(), newVisibleWorldYPosition);

                // Track position in wider scrolling world
                currentScrollableWorldYPosition += deltaY;
            }
            else
            {
                isGameOver = true;
                world.setGameOver();

                // Tell the user game is over
                world.showText("LEVEL COMPLETE", world.getWidth() / 2, world.getHeight() / 2);
            }

        }
        else
        {
            // HERO IS BETWEEN LEFT AND RIGHT PORTIONS OF SCROLLABLE WORLD
            // So... we move the other objects to create illusion of hero moving

            // Track position in wider scrolling world
            currentScrollableWorldYPosition += deltaY;

            // Get a list of all platforms (objects that need to move
            // to make hero look like they are moving)
            List<Platform> platforms = world.getObjects(Platform.class);

            // Move all the platform objects to make it look like hero is moving
            //for (Platform platform : platforms)
            //{
            //    // Platforms move left to make hero appear to move right
            //    platform.moveUp(deltaY);
            //}

            // Get a list of all decorations (objects that need to move
            // to make hero look like  they are moving)
            //List<Decoration> decorations = world.getObjects(Decoration.class);

            // Move all the decoration objects to make it look like hero is moving
            //for (Decoration decoration: decorations)
            //{
            //    // Platforms move left to make hero appear to move right
            //    decoration.moveUp(deltaY);
            //}

            // Get a list of all farAwayItems (objects that need to move
            // to make hero look like they are moving)
            //List<FarAwayItem> farAwayItems = world.getObjects(FarAwayItem.class);

            // Move all the tile objects to make it look like hero is moving

        }   
    }

    /**
     * Move the hero to the left.
     */
    public void moveUp()
    {
        // Track direction
        verticalDirection = FACING_UP;

        // Set image 
        if (onPlatform())
        {
            animateWalk(horizontalDirection);
        }
        else
        {
            // Set appropriate jumping image
            if (verticalDirection == JUMPING_UP)
            {
                setImage("PopoNana-jump-up-left.png");
            }
            else
            {
                setImage("PopoNana-fall-left.png");
            }
        }

        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        // Decide whether to actually move, or make world's tiles move
        if (currentScrollableWorldYPosition - deltaY < world.HALF_VISIBLE_WIDTH)
        {
            // HERO IS WITHIN EXTREME LOWER PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Don't let hero go off left edge of scrollable world 
            // (Allow movement only when not at left edge)
            if (currentScrollableWorldYPosition < world.VISIBLE_HEIGHT)
            {
                // Move left in visible world
                int newVisibleWorldYPosition = getY() - deltaY;
                setLocation(getX(), newVisibleWorldYPosition);

                // Track position in wider scrolling world
                currentScrollableWorldYPosition = getY();
            }            
        }
        else if (currentScrollableWorldYPosition + deltaY * 2 > world.SCROLLABLE_HEIGHT - world.HALF_VISIBLE_HEIGHT)
        {
            // HERO IS WITHIN EXTREME RIGHT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Move left in visible world
            int newVisibleWorldYPosition = getY() - deltaY;
            setLocation(getX(), newVisibleWorldYPosition);

            // Track position in wider scrolling world
            currentScrollableWorldYPosition -= deltaY;
        }        
        else
        {
            // HERO IS BETWEEN LEFT AND RIGHT PORTIONS OF SCROLLABLE WORLD
            // So... we move the other objects to create illusion of hero moving

            // Track position in wider scrolling world
            currentScrollableWorldYPosition -= deltaY;

            // Get a list of all platforms (objects that need to move
            // to make hero look like they are moving)
            // List<Platform> platforms = world.getObjects(Platform.class);

            // Move all the platform objects at same speed as hero
            // for (Platform platform : platforms)
            // {
            // Platforms move right to make hero appear to move left
            // platform.moveDown(deltaY);
            // }

            // Get a list of all decorations (objects that need to move
            // to make hero look like they are moving)
            // List<Decoration> decorations = world.getObjects(Decoration.class);

            // Move all the decoration objects to make it look like hero is moving
            // for (Decoration decoration: decorations)
            // {
            // // Platforms move right to make hero appear to move left
            // decoration.moveDown(deltaY);
            // }

            // Get a list of all items that are in the distance (far away items)
            // List<FarAwayItem> farAwayItems = world.getObjects(FarAwayItem.class);

            // Move all the FarAwayItem objects at one quarter speed as hero to create depth illusion
            // for (FarAwayItem farAwayItem : farAwayItems)
            // {
            // // FarAwayItems move right to make hero appear to move left
            // farAwayItem.moveDown(deltaY / 4);
            // }

        } 

    }

    /**
     * When the hero falls off the bottom of the screen,
     * game is over. We must remove them.
     */
    public void checkGameOver()
    {
        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        // Vertical position where hero no longer visible
        int offScreenVerticalPosition = (world.getHeight() + this.getImage().getHeight() / 2);

        // Off bottom of screen?
        if (this.getY() > offScreenVerticalPosition)
        {
            // Remove the hero
            isGameOver = true;
            world.setGameOver();
            world.removeObject(this);

            // Tell the user game is over
            world.showText("GAME OVER", world.getWidth() / 2, world.getHeight() / 2);
        }
    }
}
