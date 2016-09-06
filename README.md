
#Logger System
####initial time: 2016.9.5
####author: frankGao
#### inspried by Udacity's python course. But I need a more customized program for self-using.
**Project objective:** Build a log system to promote the working efficiency.

**Project basic idea:** Break the working schedule into time intervals. At the end of each time interval, a window jumps out and music plays. User need to record what he or she did in the previous time intervals. The program will automatic record the information into log, which is represented as text file named by the current date.

In brief, the program will work as following steps:

1. start the program.

1. pick up a song for each break to play

1. set up how long the intervals should be

1. start to run.

Then each time the window jumps out. User need to record what he or she has done in the prevous passed intervel. The program will write those into the log.

Language intended to use to implement: Java

Music format: MP3

Log format: text file

##Design notes:

###Use cases

1. at the beginning of the program, a setting up window shows to set up three parameters:
*interval length
*music file
*today's work objective

2. begin from the start of the program, remindering window will jump out at the end of each time intervals.

3. each remindering window give 4 options:

*continue work
*record work progress.
*change interval length
*change music

so 4 button will shows and a music play window shows.

####Setting up window

1. one text field for setting how long the interval should be

2. one button to chooce the music file.

3. one test field for list today's goal.

####Remindering window

* Continue work  					->	Button

* record Work progress 		->	Button

* modify interval					->	Button

* modify music						->	Button

#####Record Work Progress

*Progress text						->	Text Field
*Save											->	Button

#####Modify interval

*interval length setting	->	Text Field
*save setting							->	Button

######Modify Music

*music choose							->	Dir Button
*save setting							->	Button

####Logic
```
-> set up time and music
	-> break
		-> continue
		-> record note
		-> change interval
		-> change music
```

##progress: 2016.9.5
first time to implement a swing GUI program. not very familiar with it. Spend some time do the initial learning. Get the first window as follow

![enter image description here](https://raw.githubusercontent.com/PosFrank/Logger/master/2016.9.5.png)