
document.addEventListener("DOMContentLoaded", function () {
    createMap();
});

let mapId = 1;
let roverId = 6;
let coordinateId = 18;
let commandString = '';

async function createMap() {
    try {
        const response = await fetch(`/api/map/${mapId}`);
        if (!response.ok) {
            throw new Error('Map server request error');
        }
        
        const data = await response.json();
        
        const width = data.width;
        const height = data.height;
        
        createMapWithDimensions(width, height);

 
         const obstaclesResponse = await fetch('/api/obstacle');
         if (!obstaclesResponse.ok) {
             throw new Error('Obstacle server request error');
         }
 
         const obstaclesData = await obstaclesResponse.json();
 
   
         displayObstacles(obstaclesData);


         const roverInitialPositionResponse = await fetch(`/api/rover/${roverId}`);
         if (!roverInitialPositionResponse.ok) {
             throw new Error('Rover initial position server request error');
         }
 
         const roverInitialPositionData = await roverInitialPositionResponse.json();
 
         assignInitialPositionToRobot(roverInitialPositionData);
 
    } catch (error) {
        console.error('Error:', error);
    }
}

function createMapWithDimensions(width, height) {

    const mapElement = document.getElementById('map');
    mapElement.style.width = (width*100) + 'px';
    mapElement.style.height = (height*100) + 'px';
}

function displayObstacles(obstacles) {
    const container = document.getElementById('container');

    obstacles.forEach(obstacle => {
        const obstacleImage = document.createElement('img');
        obstacleImage.setAttribute('src', 'images/rock.png');
        obstacleImage.setAttribute('class', 'rock');
        obstacleImage.style.left = (obstacle.coordinateX*100) + 'px';
        obstacleImage.style.top = (obstacle.coordinateY*100) + 'px';
        container.appendChild(obstacleImage);
    });
}

function assignInitialPositionToRobot(roverPositionData) {
    const roverElement = document.getElementById('rover');
    roverElement.style.position = 'absolute';
    roverElement.style.width = '100px'; 
    roverElement.style.left = (roverPositionData.newPositionX * 100) + 'px'; 
    roverElement.style.top = (roverPositionData.newPositionY * 100) + 'px'; 
}


function addCommandToConsole(command) {
    commandString += command;
    const commandTextarea = document.getElementById('command-textarea');
    commandTextarea.value = commandString;
}

function moveForward() {

    addCommandToConsole('F');
}


function clickBtnRotateLeft() {

    addCommandToConsole('L');
}

function clickBtnRotateRight() {

    addCommandToConsole('R');
}

function clearConsole() {
    commandString = '';
    const commandTextarea = document.getElementById('command-textarea');
    commandTextarea.value = '';
}

async function sendCommands() {

    const commandTextarea = document.getElementById('command-textarea');
    const commandString = commandTextarea.value;

    try {

        const response = await fetch(`/api/coordinates/${coordinateId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'text/plain',
            },
            body: commandString, 
        });

        if (!response.ok) {
            throw new Error('Commands server error request');
        }

        clearConsole();

        moveRover();

    } catch (error) {
        console.error('Error:', error);
    }
}


async function moveRover() {
    try {
        // Realiza una solicitud PUT al endpoint moveRover de tu backend
        const response = await fetch(`/api/rover/${roverId}/map/${mapId}/coordinates/${coordinateId}`, {
            method: 'PUT', // Utiliza el método PUT para enviar los comandos
        });

        if (!response.ok) {
            throw new Error('Rover movement error');
        }

        // Actualiza la posición del rover en el frontend
        updateRoverPosition();

    } catch (error) {
        console.error('Rover movement error:', error);
    }
}

// Define la función para actualizar la posición del rover en el frontend
async function updateRoverPosition() {
    try {
        // Realiza una solicitud GET para obtener la posición actual del rover
        const roverPositionResponse = await fetch(`/api/rover/${roverId}`); // Reemplaza '1' con el ID correcto del rover
        if (!roverPositionResponse.ok) {
            throw new Error('Rover position server error request');
        }

        const roverPositionData = await roverPositionResponse.json();

        // Actualiza la posición del rover en el mapa
        assignRoverPositionToRobot(roverPositionData);

    } catch (error) {
        console.error('Error al actualizar la posición del rover:', error);
    }
}

// Define la función para asignar la posición del rover al robot en el mapa
function assignRoverPositionToRobot(roverPositionData) {
    const roverElement = document.getElementById('rover');
    roverElement.style.left = (roverPositionData.newPositionX * 100) + 'px'; // Actualiza la posición en base a los datos del backend
    roverElement.style.top = (roverPositionData.newPositionY * 100) + 'px'; // Actualiza la posición en base a los datos del backend
}
