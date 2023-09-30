# MARS KATA ROVER
Mars Rover Game, it is an adapted version of Mars Rover Cata, where an API was developed that translates commands sent from Earth into instructions that the rover understands. As indicated in Cata, edge wrapping and obstacle detection have been implemented. As well as the movement and turn format of the rover.

# Your Task
You’re part of the team that explores Mars by sending remotely controlled vehicles to the surface of the planet. Develop an API that translates the commands sent from earth to instructions that are understood by the rover.

# Requirements
You are given the initial starting point (x,y) of a rover and the direction (N,S,E,W) it is facing.
The rover receives a character array of commands.
Implement commands that move the rover forward/backward (f,b).
Implement commands that turn the rover left/right (l,r).
Implement wrapping at edges. But be careful, planets are spheres.
Implement obstacle detection before each move to a new square. If a given sequence of commands encounters an obstacle, the rover moves up to the last possible point, aborts the sequence and reports the obstacle.

# Rules
Hardcore TDD. No Excuses!
Change roles (driver, navigator) after each TDD cycle.
No red phases while refactoring.
Be careful about edge cases and exceptions. We can not afford to lose a mars rover, just because the developers overlooked a null pointer.

# ERM 

![ERM - ROVER](https://github.com/nahuelpierini/rover/assets/101473902/af72f165-b7a4-4325-a39f-3869332a1c4b)

# ENDPOINTS

| METHOD | URL                                               | DESCRIPTION
|--------|---------------------------------------------------|----------------------------|
| GET    | /api/coordinates                                  | Get coordinates            |
| POST   | /api/coordinates                                  | create coordinates         |
| PUT    | /api/coordinates/{id}                             | Get coordinates by id      |
| GET    | /api/map/{id}                                     | Get map by id              |
| GET    | /api/obstacle                                     | Get obstacles              | 
| POST   | /api/obstacle/map/{mapId}                         | Create obstacles by map id |
| GET    | /api/rover/{id}                                   | Get rover                  |
| POST   | /api/obstacle/map/{mapId}                         | Create rover               |
| PUT    |/{roverId}/map/{mapId}/coordinates/{coordinatesId} | Update rover               |


