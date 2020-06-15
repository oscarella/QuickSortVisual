import java.util.*;

import processing.core.PApplet;

/**
 * @author Ben Zeng, Oscar Han
 * Ms. Krasteva
 * DUE: 4/17/2020
 * This program is a visual demonstration on exactly how Quicksort works, with a variety of different markers used during the visualization to help better understand the algorithm.\
 * https://happycoding.io/tutorials/java/processing-in-java Used to help with converting processing to java
 */
public class Main extends PApplet
{
    private int operationsDone; /* details operations done */
    private QuickSort algorithm; /* Instance of QuickSort class, responsible for all the logic */
    private static final int START_SIZE = 512, LOG_SIZE = (int) Math.round(Math.log(START_SIZE) / Math.log(2)); /* Size of array and log2(size of array) */
    private static final int FRAME_RATE = 30; /* Constant for framerate */
    private boolean waitingForUser; /* Whether or not the program is waiting for a mouse press */
    private int screen; /* Screen the program is on */
    private boolean randomizeMenu; /* Whether or not the background of the menu needs to be "randomized" */

    /**
     * settings() used to set the size of the Console Window
     */
    @Override
    public void settings()
    {
        size(800, 500);
    }

    /**
     * setup() is used to set up the display
     */
    @Override
    public void setup()
    {
        frameRate(FRAME_RATE);
        screen = 0;
        randomizeMenu = true; // move
    }

    /**
     * draw() is continuously executed after setup()- calls nextMove()
     */
    @Override
    public void draw()
    {
        if(screen == 0) // main menu screen
        {
            if(randomizeMenu)
            {
                for(int x = 0; x <= 780; x += 20)
                {
                    for(int y = 0; y <= 480; y += 20)
                    {
                        noStroke();
                        fill(random(255), random(255), random(255));
                        rect(x, y, 20, 20);
                    }
                }
            }
            randomizeMenu = false;
            fill(255);
            rect(240, 20, 320, 80);
            fill(0);
            rect(245, 25, 310, 70);
            textSize(50);
            fill(255, 0, 0, 170);
            textAlign(CORNER, CORNER);
            text("QUICK SORT", 248, 73);
            fill(255, 255, 0, 170);
            text("QUICK SORT", 252, 77);
            fill(255);
            text("QUICK SORT", 250, 75);

            fill(255);
            rect(240, 170, 320, 80);
            for(int x = 245; x <= 545; x += 10)
            {
                for(int y = 175; y <= 235; y += 10)
                {
                    noStroke();
                    fill(random(255), random(255), random(255));
                    rect(x, y, 10, 10);
                }
            }
            textAlign(CORNER, CORNER);
            fill(0);
            text("RAINBOW", 283, 225);
            fill(255);
            text("RAINBOW", 285, 227);

            fill(255);
            rect(240, 320, 320, 80);
            for(int x = 245; x <= 545; x += 10)
            {
                for(int y = 325; y <= 385; y += 10)
                {
                    noStroke();
                    fill(random(255));
                    rect(x, y, 10, 10);
                }
            }
            fill(0);
            text("GREYSCALE", 258, 375);
            fill(255);
            text("GREYSCALE", 260, 377);
        }
        else if(screen == 1 || screen == 2) // Quick Sort screen
        {
            if(!waitingForUser)
            {
                int speedUpConstant = max(1, (int) Math.ceil(START_SIZE * LOG_SIZE / 600.0));
                for(int i = 0; i < speedUpConstant; i++)
                {
                    if(!algorithm.nextMove()) // calls nextMove()
                    {
                        waitingForUser = true; // stops calling nexrMove() if subarray/ array has finished partitioning (returns false)
                        break;
                    }
                    else
                        operationsDone++; // increments operationsDone if nextMove() returns true
                }
            }

            algorithm.drawArray(this, screen); // calls drawArray() in Quick Sort which draws the array

            textAlign(CORNER, CORNER);
            fill(255);
            textSize(12);
            text("Operations Done: " + operationsDone, 20, 20); // Displays operations done
            text("Array Size: " + START_SIZE, 20, 40); // Displays array size
            text("NlogN: " + START_SIZE * LOG_SIZE, 20, 60); // Displays time complexity
            text("Ben Zeng, Oscar Han", 20, 80);
            stroke(255);
            strokeWeight(5);
            line(175, 0, 175, height);
            // displays information while sorting the array
            if(waitingForUser)
            {
                textSize(15);
                fill(255);
                textAlign(CORNER, CORNER);
                text("CLICK TO CONTINUE", 10, 250);
            }
            fill(255);
            rect(10, 430, 150, 50);
            fill(0);
            rect(15, 435, 140, 40);
            textSize(20);
            textAlign(CORNER, CORNER);
            fill(255);
            text("MAIN MENU", 27, 462);
            fill(255);
            rect(10, 330, 150, 50);
            fill(0);
            if(waitingForUser && (mouseX > 15 && mouseX < 155 && mouseY > 335 && mouseY < 375)) // Button for auto-simulation
            {
                waitingForUser = false;
                fill(255, 0, 0);
            }
            rect(15, 335, 140, 40);
            textSize(15);
            textAlign(CORNER, CORNER);
            fill(255);
            text("Auto-Simulate", 32, 362);
        }
    }

    /**
     * mousePressed() detects if the user presses the mouse
     */
    @Override
    public void mousePressed()
    {
        if(waitingForUser) // waitingForUser becomes false
            waitingForUser = false;
        if(mouseX > 240 && mouseX < 240 + 320 && mouseY > 170 && mouseY < 170 + 80 && screen == 0) // Menu screen- Rainbow
        {
            screen = 1;
            waitingForUser = true;
            operationsDone = 0;
            algorithm = new QuickSort(START_SIZE);
        }
        else if(mouseX > 240 && mouseX < 240 + 320 && mouseY > 320 && mouseY < 320 + 80 && screen == 0) // Menu screen- Greyscale
        {
            screen = 2;
            waitingForUser = true;
            operationsDone = 0;
            algorithm = new QuickSort(START_SIZE);
        }
        else if(mouseX > 15 && mouseX < 15 + 140 && mouseY > 435 && mouseY < 435 + 40 && (screen == 1 || screen == 2)) // Returns to main menu from sort screen
        {
            screen = 0;
            randomizeMenu = true;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        PApplet.main(Main.class);
    }
}

/**
 * QuickSort sorts and draws an array
 */
class QuickSort
{
    private int[] array; /* Array that will be sorted */
    private SubArray currentSubArray; /* Stores start (inclusive) and end (exclusive) indexes of the current subarray being partitioned */
    private Queue<SubArray> pending; /* Stores all the pending subarrays that need to be sorted eventually */
    private int pivotValue; /* Pivot value- used to partition the sub array */
    private Integer pointer; /* Last element smaller than pivot value in the sub array */
    private Integer comparingTo; /* Starting (compared) index of the sub array; used to loop through the elements of a sub array */

    /**
     * Constructor that creates and initializes an array that will be sorted
     *
     * @param size Size of the array- Elements: 1~ array.size
     */
    public QuickSort(int size)
    {
        array = new int[size];
        pending = new LinkedList<SubArray>(); // Linked list of all sub arrays that need to be partitioned
        pending.add(new SubArray(0, size)); // Entire array is added to pending
        for(int i = 0; i < size; i++) // Elements (1~ array.length) are created and shuffled
            array[i] = i + 1;
        shuffle(array);
    }

    /**
     * drawArray() provides a visual demonstration of sorting the array
     *
     * @param p Processing Applet
     */
    public void drawArray(PApplet p, int screen)
    {
        if(screen == 1)
            p.background(0); // Background color set to black
        else
            p.background(255, 122, 0);
        float elementSize = (float) (p.width - 200) / array.length; // Determines the width of a single element
        float singleHeight = (float) (p.height - 50) / array.length; // Determines the vertical length of a single element
        if(currentSubArray != null) // If a sub array is being partitioned
        {
            p.strokeWeight(3);
            p.stroke(255);
            float left = currentSubArray.l * elementSize + 200, right = (currentSubArray.r - 1) * elementSize + 200;
            p.line(left, 15, right, 15);
            p.line(left, 15, left, 40);
            p.line(right, 15, right, 40);
            // Highlights sub array being partitioned
        }
        p.noStroke();
        for(int i = 0; i < array.length; i++) // Loops through all elements of array to assign colours and position for elements
        {
            if(pointer != null && pointer.equals(i))
            { // Red strip: pointer; last element smaller than element
                p.fill(255, 0, 0, 170);
                p.rect((float) Math.ceil(i * elementSize + 200 - Math.ceil(elementSize)), 75, (float) Math.ceil(elementSize) * 2, p.height - 75);
            }
            else if(comparingTo != null && comparingTo.equals(i))
            { // Yellow strip: current element being compared to pivot
                p.fill(255, 255, 0, 170);
                p.rect((float) Math.ceil(i * elementSize + 200 - Math.ceil(elementSize)), 75, (float) Math.ceil(elementSize) * 2, p.height - 75);
            }
            if(screen == 1)
            {
                int colorX = (int) Math.ceil((array[i] * 1.0) / (array.length / 4.0));
                int colorY;
                switch(colorX) // Color is assigned based on array value and array length; Forms a raindow gradient
                {
                    case 1:
                        colorY = (int) Math.ceil((array[i] * 1.0) / (array.length / 4.0) * 255.0);
                        p.fill(255, colorY, 0);
                        break;
                    case 2:
                        colorY = (int) Math.ceil((array[i] * 1.0 - (array.length / 4.0)) / (array.length / 4.0) * 255.0);
                        p.fill(255 - colorY, 255, 0);
                        break;
                    case 3:
                        colorY = (int) Math.ceil((array[i] * 1.0 - (array.length / 4.0 * 2)) / (array.length / 4.0) * 255.0);
                        p.fill(0, 255, colorY);
                        break;
                    default:
                        colorY = (int) Math.ceil((array[i] * 1.0 - (array.length / 4.0 * 3)) / (array.length / 4.0) * 255.0);
                        p.fill(0, 255 - colorY, 255);
                        break;
                }
            }
            else
            {
                p.fill((int) (255 - (array[i] * 1.0 / array.length * 255)));
            }
            // Draws the element as an ellipse- Height: represents index; Width: represents value (1~array.length)
            p.ellipse((float) i * elementSize + 200, p.height - (float) singleHeight * array[i], (float) elementSize * 4, (float) elementSize * 4);
        }
    }

    /**
     * nextMove() performs one step of quick sort every time it is called
     *
     * @return false if the current sub array/ array is finished partitioning
     */
    public boolean nextMove()
    {
        if(currentSubArray != null) // If a sub array is being partitioned
        {
            if(comparingTo < currentSubArray.r - 1) // If the compared index is not the last index (excluding pivot)
            {
                if(array[comparingTo] < pivotValue) // If the value of the compared index is smaller than pivot value
                {
                    pointer++; // increments smaller element
                    swap(array, pointer, comparingTo); // swap smaller element and compared index
                }
                comparingTo++; // increments compared index
            }
            else // If the starting index has reached the last index (excluding pivot)
            {
                swap(array, pointer + 1, currentSubArray.r - 1); // pivot is now in correct position in final array
                int mid = pointer + 1;
                if(mid + 1 < currentSubArray.r - 1) // right segment to pivot added to pending if length is greater than 1
                    pending.add(new SubArray(mid + 1, currentSubArray.r));
                if(currentSubArray.l < mid - 1) // left segment to pivot added to pending if length is greater than 1
                    pending.add(new SubArray(currentSubArray.l, mid));
                currentSubArray = null;
                pointer = null;
                comparingTo = null;
            }
            return true;
        }
        else // If a sub array is not being partitioned; occurs if the array/ a sub array is finished being sorted
        {
            if(pending.isEmpty()) // If the array is finished being sorted
                return false;
            currentSubArray = pending.poll(); // First element of pendign becomes currentSubArray; element is removed after
            int randomPivot = (int) (Math.random() * (currentSubArray.r - currentSubArray.l)) + currentSubArray.l;
            // Moving pivot to start of subarray
            swap(array, randomPivot, currentSubArray.r - 1);
            pointer = currentSubArray.l - 1; // Assigns pointer as one minus starting index of subarray
            pivotValue = array[currentSubArray.r - 1];
            comparingTo = currentSubArray.l;
            return false;
        }
    }

    /**
     * shuffle() randomly shuffles all elements of an int array at least once
     *
     * @param a array that is shuffled
     */
    private void shuffle(int[] a)
    {
        for(int i = 0; i < a.length; i++)
        {
            int randomIndex = (int) (Math.random() * a.length);
            swap(a, i, randomIndex);
        }
    }

    /**
     * swap() swaps the indices of two elements within an int array
     *
     * @param a      array that is used
     * @param first  element to be swapped with second
     * @param second element to be swapped with first
     */
    private void swap(int[] a, int first, int second)
    {
        int temp = a[first];
        a[first] = a[second];
        a[second] = temp;
    }

    /**
     * contains indices of a sub array
     */
    private class SubArray
    {
        public final int l, r;

        /**
         * SubArray constructor
         *
         * @param l starting index of the subarray
         * @param r ending index of the subarray
         */
        public SubArray(int l, int r)
        {
            this.l = l;
            this.r = r;
        }
    }
}