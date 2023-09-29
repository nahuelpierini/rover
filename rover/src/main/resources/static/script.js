
let mapId = 1;
let roverId = 6;
let commandString = '';

async function createMap() {
    try {
        const response = await fetch(`/api/map/${mapId}`);
        if (!response.ok) {
            throw new Error('Error en la solicitud al servidor');
        }
        
        const data = await response.json();
        

        const width = data.width;
        const height = data.height;
        

        createMapWithDimensions(width, height);

 
         const obstaclesResponse = await fetch('/api/obstacle');
         if (!obstaclesResponse.ok) {
             throw new Error('Error en la solicitud de obstáculos al servidor');
         }
 
         const obstaclesData = await obstaclesResponse.json();
 
   
         displayObstacles(obstaclesData);


         const roverInitialPositionResponse = await fetch(`/api/rover/${roverId}`);
         if (!roverInitialPositionResponse.ok) {
             throw new Error('Error en la solicitud de posición inicial al servidor');
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
        obstacleImage.style.position = 'absolute';
        obstacleImage.style.width = '100px';
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


document.addEventListener("DOMContentLoaded", function () {
    createMap();
});






// Función para agregar comandos a la cadena y mostrar en la consola
function addCommandToConsole(command) {
    commandString += command;
    const commandTextarea = document.getElementById('command-textarea');
    commandTextarea.value = commandString;
}

// Función para enviar los comandos al backend (puedes implementar esto más tarde)
function sendCommands() {
    // Aquí puedes enviar 'commandString' al backend a través de una solicitud AJAX o como prefieras.
    console.log('Comandos enviados al backend:', commandString);
}

// Modifica tus funciones de control para agregar comandos a la consola
function moveForward() {
    // Lógica para mover hacia adelante
    // ...

    // Agrega el comando 'F' a la consola
    addCommandToConsole('F');
}


function clickBtnRotateLeft() {
    // Lógica para girar a la izquierda
    // ...

    // Agrega el comando 'L' a la consola
    addCommandToConsole('L');
}

function clickBtnRotateRight() {
    // Lógica para girar a la derecha
    // ...

    // Agrega el comando 'R' a la consola
    addCommandToConsole('R');
}

function clearConsole() {
    // Limpia la cadena de comandos y la consola
    commandString = '';
    const commandTextarea = document.getElementById('command-textarea');
    commandTextarea.value = '';
}


async function sendCommands() {
    // Obtiene los comandos de la consola
    const commandTextarea = document.getElementById('command-textarea');
    const commandString = commandTextarea.value;

    try {
        // Realiza una solicitud POST al backend para guardar los comandos
        const response = await fetch('/api/coordinates/18', {
            method: 'PUT',
            headers: {
                'Content-Type': 'text/plain', // Cambia el tipo de contenido a "text/plain"
            },
            body: commandString, // Envia directamente la cadena de comandos
        });

        if (!response.ok) {
            throw new Error('Error al enviar los comandos al servidor');
        }

        // Limpia la consola después de enviar los comandos
        clearConsole();

        // Después de enviar los comandos y actualizar las coordenadas, mueve el rover
        moveRover();

    } catch (error) {
        console.error('Error:', error);
    }
}


async function moveRover() {
    try {
        // Realiza una solicitud GET al backend para obtener la posición actual del rover
        const roverPositionResponse = await fetch(`/api/rover/6`);
        if (!roverPositionResponse.ok) {
            throw new Error('Error al obtener la posición del rover');
        }

        const roverPositionData = await roverPositionResponse.json();
        
        // Actualiza la posición del rover en el frontend
        updateRoverPosition(roverPositionData);

    } catch (error) {
        console.error('Error:', error);
    }
}

function updateRoverPosition(roverPositionData) {
    const roverElement = document.getElementById('rover');
    roverElement.style.left = (roverPositionData.newPositionX * 100) + 'px';
    roverElement.style.top = (roverPositionData.newPositionY * 100) + 'px';
    // También puedes ajustar la orientación del rover aquí si es necesario
}












// Define la función para mover el rover
async function moveRover() {
    try {
        // Realiza una solicitud PUT al endpoint moveRover de tu backend
        const response = await fetch('/api/rover/6/map/1/coordinates/18', {
            method: 'PUT', // Utiliza el método PUT para enviar los comandos
        });

        if (!response.ok) {
            throw new Error('Error al mover el rover');
        }

        // Actualiza la posición del rover en el frontend
        updateRoverPosition();

    } catch (error) {
        console.error('Error al mover el rover:', error);
    }
}

// Define la función para actualizar la posición del rover en el frontend
async function updateRoverPosition() {
    try {
        // Realiza una solicitud GET para obtener la posición actual del rover
        const roverPositionResponse = await fetch('/api/rover/6'); // Reemplaza '1' con el ID correcto del rover
        if (!roverPositionResponse.ok) {
            throw new Error('Error al obtener la posición del rover');
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
