# Cool Thing
A fun side-project I'm doing while taking summer/fall college courses. My (almost) first attempt at making a (sort of) game. 

*May or may not be renamed to the following:*
 - Ping
 - Boing
 - Bong <br>^ *this one is my favorite - might use the 🅱️ emoji*
 - Bing Bong

## About
**History**
Originally, I wanted to attempt at making a game, and I thought, hey, *Pong* could be easy to remake in Java. This was, in fact, not that easy. With no prior experience in any kind of game development, I went full force, got to a point where it was too messy/buggy that I scrapped the project and started over again from new. This happened at least two other times (don't judge). After that I completely scrapped the idea in general, had a *very* long hiatus, and decided it was time to stop being a waste of space, since after all, I was going to go for a bachelor's in computer science. I didn't know what was going to come out of this project, so I named it *Cool Thing* as a placeholder. I don't know if it's necessarily "cool" yet, so we'll just have to wait and see.

**The Actual "Game"**
*Cool Thing* is going to be some sort of *Pong* clone with a twist. My ideas are vague this early in development, but I was thinking along the lines of things such as gamemodes (*classic w/ ranging difficulties*, *some kind of mode where multiple ping pong balls are introduced*, *a mode where the background, paddles, & text change colors at a speed depending on how fast the ball is moving* (**yes, there will be an epilepsy warning**), *etc*). The game will be running at/around 30 frames per second and will be locked at that frame-rate because why would you go higher than that.

**The Assets**
The assets (other than some music/sounds) are made by me, but you are free to use them, no credit needed. The `theme_song.wav` is a no-copyright piece of work and so are most of the sounds, except a few of which I made on my own using Garage Band (I'm sure you could figure out which). The individual characters in the `src/lang` folder are made using Photoshop (very overkill for 16-bit characters) and have a height of 16 pixels and a varying width. The characters can have up to 4 layers of depth, but I've made my characters with a max of 3. The grey can then programmatically be color shifted using a thing called [relative luminance](https://en.wikipedia.org/wiki/Relative_luminance). These layers have a difference of 64 in their red, green, and blue layers and are sorted by their "greyness":

 1. `R: 0, G: 0, B: 0` This is used as the background and the alpha is set to 0, making it transparent.
 2. `R: 64, G: 64, B: 64` The darkest grey.
 3. `R: 128, G: 128, B: 128` This is the second darkest grey and is the middle ground.
 4. `R: 192, G: 192, B: 192` This is the second lightest grey.
 5. `R: 255, G: 255, B: 255` This is white and will be replaced with the desired color.

![The old characters (now reformatted) with the colors of the rainbow](https://cdn.discordapp.com/attachments/339868251288764417/606309076053524481/screenshot7550816017838778848.png)

**Dependencies**

 - [Jansi v1.17.1](https://github.com/fusesource/jansi)
 - [Google's FindBugs v3.0.2](https://github.com/findbugsproject/findbugs)
 - [Google's GSON v2.8.0](https://github.com/google/gson) *- not currently used, but will eventually*
 - [Jansi v3.10.0](https://github.com/jline/jline3)
