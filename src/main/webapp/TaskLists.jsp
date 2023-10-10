<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Konfiguracja XTB i DL4J</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0; /* Tło na szare */
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh; /* Wycentrowanie zawartości na stronie */
        }
        .navbar {
            background-color: #007BFF;
            color: #fff;
            padding: 10px 0;
            text-align: center;
        }
        .container {
            background-color: #fff;
            border: 1px solid #ccc;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            margin: 10px;
            padding: 20px;
            width: 80%;
            max-width: 1700px; /* Maksymalna szerokość kontenera */
            display: flex; /* Używamy flexboxa */
        }
        .config-section {
            width: 30%; /* Szerokość sekcji konfiguracji */
            padding: 10px; /* Dodajemy wypełnienie dla wyglądu */
        }
        .config-section input[type="text"],
        .config-section input[type="password"],
        .config-section select {
            width: 100%;
            max-width: 300px; /* Maksymalna szerokość dla inputów i selectów */
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .config-section label {
            display: block; /* Label jako blok, aby były nad inputami */
            font-weight: bold;
        }
        button {
            background-color: #007BFF;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
            margin-top: 10px; /* Dodatkowy margines odstępu od inputów */
        }
        .config-table {
            width: 70%; /* Szerokość sekcji tabeli */
            padding: 10px; /* Dodajemy wypełnienie dla wyglądu */
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            overflow-x: auto; /* Dodaj przewijanie poziome dla tabeli */
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center; /* Wycentrowanie tekstu w tabeli */
        }
        th {
            background-color: #007BFF;
            color: #fff;
        }
        #saved-configs-container {
            width: 100%;
        }
       /* dddddddddddddddddddddddddddddddddd */
       #layers {
        width: 70%; /* Szerokość na 50% */
        padding: 5px; /* Dodaj wypełnienie */
        font-size: 16px; /* Dostosuj rozmiar czcionki */
        border: 1px solid #ccc; /* Dodaj obramowanie */
        border-radius: 4px; /* Zaokrągl rogi */
    }

    /* Styl dla kontenera na pola "Ilość Neuronów" */
    #neuron-inputs {
        margin-top: 10px; /* Dodaj odstęp od pola "Liczba warstw" */
    }

    /* Styl dla pojedynczego pola "Ilość Neuronów" */
    #neuron-inputs label {
        display: block; /* Każde pole na nowej linii */
        margin-bottom: 5px; /* Odstęp między polami */
    }

    /* Styl dla inputów "Ilość Neuronów" */
    #neuron-inputs input {
        width: 50%; /* Szerokość na 50% */
        padding: 5px; /* Dodaj wypełnienie */
        font-size: 16px; /* Dostosuj rozmiar czcionki */
        border: 1px solid #ccc; /* Dodaj obramowanie */
        border-radius: 4px; /* Zaokrągl rogi */
    }
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
    </style>
</head>
<body>






    <div class="navbar">
        <a href="#">Strona 1</a> | <a href="#">Strona 2</a> | <a href="#">Strona 3</a>
    </div>
    <div class="container">
        <div class="config-section">
            <h2>Konfiguracja XTB i DL4J</h2>
            <form id="config-form" action="KonfiguracjaServlet" method="post">
                <label for="config-name">Nazwa Konfiguracji:</label>
                <input type="text" id="config-name" name="config-name">

                <label for="xtb-username">Użytkownik XTB:</label>
                <input type="text" id="xtb-username" name="xtb-username" required>

                <label for="xtb-password">Hasło XTB:</label>
                <input type="password" id="xtb-password" name="xtb-password" required>

                <label for="symbol">Symbol:</label>
                <input type="text" id="symbol" name="symbol" required>

                <label for="period-code">Perdiod Code:</label>
                <select id="period-code" name="period-code">
                    <option value="1">PERIOD_M1</option>
                    <option value="5">PERIOD_M5</option>
                    <option value="15">PERIOD_M15</option>
                    <option value="30">PERIOD_M30</option>
                    <option value="60">PERIOD_H1</option>
                    <option value="240">PERIOD_H4</option>
                    <option value="1440">PERIOD_D1</option>
                    <option value="10080">PERIOD_W1</option>
                    <option value="43200">PERIOD_MN1</option>
                </select>

                <label for="layers">Liczba warstw:</label>
                <input type="number" id="layers" name="layers" min="1" value="1" onchange="createNeuronInputs()" required>

                <div id="neuron-inputs">
                    <!-- Dynamicznie generowane pola na ilość neuronów dla każdej warstwy -->
                </div>

                <label for="display-frequency">Częstotliwość wyświetlania wyników:</label>
                <input type="text" id="display-frequency" name="display-frequency" required>

                <button type="submit">Zapisz konfigurację</button>
            </form>
        </div>

        <div class="config-table">
                    <h2>Zapisane Konfiguracje:</h2>
                    <div id="saved-configs-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nazwa Konfiguracji</th>
                                    <th>Użytkownik XTB</th>
                                    <th>Symbol</th>
                                    <th>Perdiod Code</th>
                                    <th>Liczba warstw</th>
                                    <th>Ilość Neuronów</th>
                                    <th>Częstotliwość wyświetlania wyników</th>
                                    <th>Status</th>
                                    <th>     </th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listaKonfiguracji}" var="konfiguracja">
                                    <tr>
                                        <td>${konfiguracja.id}</td>
                                        <td>${konfiguracja.nazwaKonfiguracji}</td>
                                        <td>${konfiguracja.uzytkownikXtb}</td>
                                        <td>${konfiguracja.symbol}</td>
                                        <td>${konfiguracja.periodCode}</td>
                                        <td>${konfiguracja.liczbaWarstw}</td>
                                        <td>${konfiguracja.iloscNeuronow}</td>
                                        <td>${konfiguracja.czestotliwoscWyswietlaniaWynikow}</td>
                                        <td>${konfiguracja.status}</td>
                                        <td>
                                            <form action="Main" method="post">
                                              <input type="hidden" name="Open_Confinguration_button" value="${konfiguracja.id}@open">
                                              <input type="submit" value="Otwórz">
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
    </div>

    <script>

//--------------------------------
        var configCounter = 1;

        function createNeuronInputs() {
            var layersInput = document.getElementById("layers");
            var neuronInputs = document.getElementById("neuron-inputs");
            neuronInputs.innerHTML = "";

            var numLayers = parseInt(layersInput.value);

            for (var i = 1; i <= numLayers; i++) {
                var label = document.createElement("label");
                label.innerHTML = "Ilość neuronów w warstwie " + i + ":";
                var input = document.createElement("input");
                input.type = "number";
                input.name = "neurons-layer-" + i;
                input.id = "neurons-layer-" + i;
                input.min = "1";
                neuronInputs.appendChild(label);
                neuronInputs.appendChild(input);
            }
        }

        function saveConfig() {
            var configNameInput = document.getElementById("config-name");
            var configName = configNameInput.value;

            // Jeśli pole "Nazwa Konfiguracji" jest puste, ustaw aktualną datę
            if (configName.trim() === '') {
                var currentDate = new Date();
                configName = currentDate.toISOString().substring(0, 10); // Format YYYY-MM-DD
            }

            var config = {
                'Nr': configCounter++,
                'Nazwa Konfiguracji': configName,
                'Użytkownik XTB': document.getElementById("xtb-username").value,
                'Hasło XTB': document.getElementById("xtb-password").value,
                'Symbol': document.getElementById("symbol").value,
                'Perdiod Code': document.getElementById("period-code").value,
                'Liczba warstw': document.getElementById("layers").value,
                'Ilość Neuronów': getNeuronValues(),
                'Częstotliwość wyświetlania wyników': document.getElementById("display-frequency").value,
                'Status': 'Nieaktywna'
            };

            //var savedConfigs = document.getElementById("saved-configs");
            var newRow = savedConfigs.insertRow();
            for (var key in config) {
                if (config.hasOwnProperty(key)) {
                    var cell = newRow.insertCell();
                    cell.innerHTML = config[key];
                }
            }

            var loadButtonCell = newRow.insertCell();
            var loadButton = document.createElement("button");
            loadButton.innerHTML = "Start";
            loadButton.onclick = function() {
                // Tutaj możesz dodać kod do uruchamiania konfiguracji
            };
            loadButtonCell.appendChild(loadButton);

            // Wyczyść pola po zapisaniu konfiguracji
            configNameInput.value = '';
            document.getElementById("xtb-username").value = '';
            document.getElementById("xtb-password").value = '';
            document.getElementById("symbol").value = '';
            document.getElementById("period-code").value = '';
            document.getElementById("layers").value = '1';
            document.getElementById("neuron-inputs").innerHTML = '';
            document.getElementById("display-frequency").value = '';
        }

        function getNeuronValues() {
            var numLayers = parseInt(document.getElementById("layers").value);
            var neuronValues = [];

            for (var i = 1; i <= numLayers; i++) {
                var input = document.getElementById("neurons-layer-" + i);
                neuronValues.push(input.value);
            }

            return neuronValues.join(', ');
        }

        // Wywołujemy funkcję przy ładowaniu strony, aby utworzyć pola na ilość neuronów
        createNeuronInputs();
    </script>
</body>
</html>