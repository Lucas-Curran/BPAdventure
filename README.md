# BPAdventure
BPAdventure is an RPG dungeon crawler involving ten levels in which the user must make it to the end without dying once. 

This project was created with the collaboration of four Java programmers 
for the BPA Software Engineering event (2021-2022)

Compilation/Building directions:

	Specific IDE building instructions come from: https://libgdx.com/wiki/start/import-and-running
	Clone BPAdventure from the Github repository page
	Then import the files as a gradle project
	Follow your specific IDE's building instructions from the libgdx wiki to set the desktop launcher as the main class
	The project should be compiled and running!

Features:

	 RPG Dungeon crawler
	 Enemy system that includes
		-Steering behaviors, pathfinding, and aggro range
		-Jumpers
		-Shooters
		-Bosses
		-Dashers
		-Patrolers
		
	 NPC entities that have ability to talk and shop with
	 Ten levels with varied enemies, NPCs, obstacles, puzzles, and goals
	 Shop keeper sells tiers of gear for helmet, chest, legs, boots, shield, and weapon
	 Inventory with drag/drop capabilities, and equipment section
	 
         Java programming utilizing the LibGDX framework
         Collision and physics made using Box2D (wrapped by LibGDX)
         Entities managed with Ashley Entity Component System
         AI created using LibGDX's artificial intelligence framework
         Crash reporting available via JavaMail
         Database storing via SQLite
         Logging via log4j2
