<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<meta charset="UTF-8">
    <title>Strona główna</title>
    <style>
        /* Resetowanie stylów */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Stylizacja całej strony */
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        /* Stylizacja głównego kontenera */
        .container {
            width: 80%;
            max-width: 800px;
            background-color: #fff;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
            padding: 20px;
        }

        /* Stylizacja nagłówka */
        h1 {
            color: #333;
        }

        /* Stylizacja przycisków */
        .button-row {
            display: table;
            width: 100%;
            margin-top: 20px;
            text-align: center;
        }

        .button-cell {
            display: table-cell;
            padding: 10px;
        }

        button {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
        }

        /* Stylizacja pola logera */
        .loger-box {
            margin-top: 20px;
            border: 1px solid #ccc;
            padding: 10px;
        }

        textarea {
            width: 100%;
            height: 100px;
            resize: vertical;
        }

        /* Stylizacja wykresu */
        #myChart {
            margin-top: 20px;
        }
    </style>
</head>
<body>


    <div class="container">
       <h1>Konfiguracja</h1>
       <p>ID: ${konfiguracja.id}</p>
       <p>Nazwa Konfiguracji: ${konfiguracja.nazwaKonfiguracji}</p>
       <p>Użytkownik XTB: ${konfiguracja.uzytkownikXtb}</p>
       <p>Hasło XTB: ${konfiguracja.hasloXtb}</p>
       <p>Symbol: ${konfiguracja.symbol}</p>
       <p>Period Code: ${konfiguracja.periodCode}</p>
       <p>Liczba Warstw: ${konfiguracja.liczbaWarstw}</p>
       <p>Ilość Neuronów: ${konfiguracja.iloscNeuronow}</p>
       <p>Częstotliwość Wyświetlania Wyników: ${konfiguracja.czestotliwoscWyswietlaniaWynikow}</p>
       <p>Status: ${konfiguracja.status}</p>

        <!-- Dodaj pola dla ilości pobranych danych, danych treningowych i danych testowych -->
        <p>Ilość Pobranych Danych: 1000</p>
        <p>Dane Treningowe: [1, 2, 3, 4, 5]</p>
        <p>Dane Testowe: [6, 7, 8, 9, 10]</p>

        <div class="loger-box">
            <h3>Loger</h3>
            <textarea id="update" name="update"></textarea>
        </div>

        <!-- Użyj tabeli do wyśrodkowania przycisków w jednej linii -->
        <form>
            <div class="button-row">
                <div class="button-cell">
                    <button type="button" id="sendButton" value="start@${konfiguracja.id}" >Start</button>
                </div>
                <div class="button-cell">
                    <button type="button" id="sendButton" value="stop@${konfiguracja.id}" >Stop</button>
                </div>
                <div class="button-cell">
                    <button type="button" id="sendButton" value="delete@${konfiguracja.id}" >Usuń</button>
                </div>
            </div>
        </form>

        <!-- Dodaj div dla wykresu -->
        <div id="myChart">
            <canvas id="myCanvas"></canvas>
        </div>
    </div>

   



 <!-- Skrypt JavaScript dla wykresu -->
 <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
 <script>
     const ctx = document.getElementById('myCanvas').getContext('2d');
     const data = {
         labels: ['Label 1', 'Label 2', 'Label 3', 'Label 4', 'Label 5'],
         datasets: [{
             label: 'Dane Treningowe',
             data: [1, 2, 3, 4, 5],
             backgroundColor: 'rgba(75, 192, 192, 0.2)',
             borderColor: 'rgba(75, 192, 192, 1)',
             borderWidth: 1,
             fill: false
         }, {
             label: 'Dane Testowe',
             data: [6, 7, 8, 9, 10],
             backgroundColor: 'rgba(255, 99, 132, 0.2)',
             borderColor: 'rgba(255, 99, 132, 1)',
             borderWidth: 1,
             fill: false
         }]
     };
     const myChart = new Chart(ctx, {
         type: 'line',
         data: data,
         options: {
             scales: {
                 y: {
                     beginAtZero: true
                 }
             }
         }
     });

     //dopisywanie do logera
      const socket = new WebSocket("ws://localhost:8080/WebNxtb/logWebSocket");
               socket.onmessage = function (event) {
                   const logMessage = event.data;
                   const textarea = document.getElementById("update");
                   textarea.innerHTML += logMessage;
                   textarea.scrollTop = textarea.scrollHeight;
               };
   //---------------------------------


 </script>

<script>
document.querySelectorAll('.button-cell').forEach(function(buttonCell) {
    buttonCell.addEventListener('click', function() {
        var buttonValues = Array.from(buttonCell.querySelectorAll('button')).map(function(button) {
            return button.value;
        })[0];
        
        const idButton = buttonValues;

        const data = {
            idButton: idButton,
        };

        // Wyślij żądanie Fetch
        fetch('http://localhost:8080/WebNxtb/Action', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .catch(error => {
            console.error('Błąd:', error);
        });
    });
});

</script>

</body>
</html>
