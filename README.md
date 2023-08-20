# GRIME
A Java GUI based Image Editing tool which supports variety of functions. 

# Introduction:

This project is a Java Swing-based GUI tool that provides a variety of image processing and editing functionalities via Graphical User Interface and also supports ways to run the application in Script mode or in an interactive text mode using the Jar File.
This tool allows users to execute a range of operations that are specifically tailored to their unique image processing needs. 
The operations can be conveniently performed via GUI or can be performed via the command line or alternatively uploaded through a script file. Currently, 
the tool supports the processing of Conventional image files like (png, bmp, jpg) and ascii ppm image files. However, the project is designed with extensibility in mind, 
with the capacity to support other image file types in the future by simply extending the existing MVC design.

# How to use GRIME GUI java application
GRIME GUI application is a simple image editing program that allows you to perform basic image manipulations using the GUI interface created using the java swing library. To use the program, follow these steps:
1.	Open the GRIME GUI application using the above-mentioned steps via JAR file.
2.	To Load an image into the application. You can do this by clicking the "File" Menu button in the top left corner of the frame in the Menu bar and then click on “Load” button -> this will display an Open Dialog Box inside the application window frame -> now navigate to the file you want to Load or just type the image file location in the File Name text area and then click on “Open” button on the Dialog Box. Click on “Cancel” button instead of “Open” button to cancel the Load Operation.
3.	If you load the correct image file which is supported by the GRIME application then you will see the image that you loaded in the image panel which is below the Menu Bar. You will also see the Histogram of that image which is used to visualize the distribution of color or intensities in an image.
4.	Now After Loading the Image ->Choose the operation you want to perform on the image. You can do this by clicking the appropriate button on the button panel which is displayed in the bottom of the GUI window. There are total 8 buttons which handles all the operations supported by GRIME application.
5.	The buttons are “Flip”, “Greyscale-Component”, “Filter”, “Color Transform”, “Dither”, “Brighten”, “RGB-Split”, RGB-Combine.
6.	The detailed information of what buttons support what all operations is provided below.
7.	To Save the image file after performing all your desired IME operations,
8.	Follow these steps->
1.	Click on the "File" Menu button in the top left corner of the frame in the Menu bar and then click on “Save” button.
2.	This will display a Save Dialog Box inside the application window frame -> now navigate to the location where you want to save your file or just type the image file location in the File Name text area and then click on “Save” button on the Dialog Box to Save the image in that location.
3.	Or you can Click on “Cancel” button instead of “Save” button to cancel the Save Operation.
8.	If you want to exit the program you can either click on “File”->” Exit” button on the Menu Bar or you can just click on “X” button on the top left corner of the GRIME application window.
9.	You can also Resize the GRIME application window.
10.	The image panel is also scrollable to support large images.

# GUI Operations Supported:

Operations Supported by Different Buttons -:
•	Load: Loads an image file into the application.
•	Brighten: Brightens the image by the specified value.
•	Flip: Apply the specified flip type (horizontal, vertical) to the image that is being worked on.
•	Greyscale: Converts the image to the choice of components offered by Greyscale Components Offered are ->” red-component, green-component, blue-component, value-component, intensity-component, luma-component”.
•	RGB split: Splits the image into its red, green, and blue components.
•	RGB combine: Combines the red, green, and blue components into a single image.
•	Filter: Apply the specified filter type (blur, sharpen) to the image.
•	Transformation: Applies the specified transformation type (sepia, greyscale) to the image.
•	Dithering: Applies a dithering effect to the image.
Save: Saves the image to a file



