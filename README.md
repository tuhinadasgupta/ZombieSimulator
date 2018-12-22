# ZombieSimulator
zombie apocalypse

Zombie Rules:

When the simulation starts, a single zombie should be created in a random location.
If a zombie sees a Human within 10 squares of the direction it is facing (i.e., up, down, left, or right), then it moves towards that human
Otherwise, a Zombie has a 20% chance of turning in a random direction. The zombie moves forward whether it turns or not.
If a Zombie is adjacent to a human (not including diagonals), then it infects the human. The human then becomes a new zombie.

Updated Human Rules:

If a Human sees a Zombie within 10 squares of the direction it is facing, then the Human faces the opposite direction and attempts to run two squares away (subsequent moves will only be one square, unless the Human sees another zombie).
Otherwise the Human acts as before (10% random chance of turning, then move forward).
Humans that are adjacent to zombies get turned into new zombies as described above.

GUI Improvements:

Pressing the space bar should cause the zombie simulator to reset (regenerate buildings and humans, and add one zombie).
Clicking the mouse on the map should add a zombie at that location.
